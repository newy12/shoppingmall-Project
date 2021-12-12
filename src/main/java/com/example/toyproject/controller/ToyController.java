package com.example.toyproject.controller;

import com.example.toyproject.entity.*;
import com.example.toyproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequiredArgsConstructor
public class ToyController{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final PocketRepository pocketRepository;
    private final ReplyRepository replyRepository;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/topClothes")
    public String top(Model model) {
        model.addAttribute("item",itemRepository.findByCategory("Top"));
        return "topClothes";
    }
    @GetMapping("/bottomClothes")
    public String bottom(Model model) {
        model.addAttribute("item",itemRepository.findByCategory("Bottom"));
        return "bottomClothes";
    }
    @GetMapping("/shirtClothes")
    public String shirt(Model model) {
        model.addAttribute("item",itemRepository.findByCategory("Shirt"));
        return "shirtClothes";
    }
    @GetMapping("/shoesClothes")
    public String shoes(Model model) {
        model.addAttribute("item",itemRepository.findByCategory("Shoes"));
        return "shoesClothes";
    }
    @GetMapping("/accClothes")
    public String acc(Model model) {
        model.addAttribute("item",itemRepository.findByCategory("Acc"));
        return "accClothes";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Member member, RedirectAttributes redirectAttributes) {
        Optional<Member> OptMember = memberRepository.findByUserId(member.getUserId().toLowerCase());
        if (OptMember.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "이미 있는 아이디 입니다.");
            return "redirect:/login";
        } else {
            member.setPassword(passwordEncoder.encode(member.getUserPassword()));
            System.out.println("memberForm = " + member.getUserId().toLowerCase());
            System.out.println("memberFormType = " + member.getUserId().getClass().getName());
            String UserIdWord = member.getUserId().toLowerCase();
            if (UserIdWord.equals("admin")) {
                member.setRole(Role.ADMIN);
            } else {
                member.setRole(Role.MEMBER);
            }
            memberRepository.save(member);
        }
        redirectAttributes.addFlashAttribute("message", "회원가입 축하드립니다.");
        return "redirect:/login";
    }

    @GetMapping("/itemDetail/{id}")
    public String topDetail(@PathVariable(name = "id")Long id,Model model) {
        model.addAttribute("item",itemRepository.findById(id).get());
        return "itemDetail";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/fail")
    public String fail(RedirectAttributes redirectAttributes) {


        redirectAttributes.addFlashAttribute("message", "다시 입력해주세요.");
        return "redirect:/login";
    }

    @GetMapping("/buyClothes")
    public String buyClothes(@AuthenticationPrincipal User user, Model model) {
            Optional<Member> OptMember = memberRepository.findByUserId(user.getUsername());
            if(OptMember.isPresent()){
                List<Pocket> list = pocketRepository.findByMemberIdAndLocation(OptMember.get().getId(),Location.order);
                AtomicInteger totalPrice = new AtomicInteger();
                list.forEach(pocket -> {
                    int price = Integer.parseInt(pocket.getItem().getItemPrice());
                    int account = Integer.parseInt(pocket.getAccount());
                    totalPrice.addAndGet(price * account);
                    model.addAttribute("totalPrice",totalPrice);

                    //LOCATION = ORDER 인 요소만 뽑기
                    model.addAttribute("buyList",pocketRepository.findByMemberAndLocation(OptMember.get(),Location.order));
                });
            return "buyClothes";
        };

        return null;
    }

    @GetMapping("/shoppingBasket")
    public String shoppingBasket() {
        return "shoppingBasket";
    }

    @GetMapping("/myPage")
    public String myPage() {
        return "myPage";
    }
    @GetMapping("/myPocket")
    public String myPocket(@AuthenticationPrincipal User user,Model model) {
        Optional<Member> OptMember = memberRepository.findByUserId(user.getUsername());
        //총 합계 구하는 식
        List<Pocket> list = pocketRepository.findByMemberIdAndLocation(OptMember.get().getId(),Location.pocket);

        AtomicInteger totalPrice = new AtomicInteger();
        list.forEach(pocket -> {
            int price = Integer.parseInt(pocket.getItem().getItemPrice());
            int account = Integer.parseInt(pocket.getAccount());
            totalPrice.addAndGet(price * account);
        });

        if(OptMember.isPresent()){
            model.addAttribute("pocketList",pocketRepository.findByMemberIdAndLocation(OptMember.get().getId(),Location.pocket));
            model.addAttribute("totalPrice", totalPrice);
            return "myPocket";
        }
        return null;
    }
    @GetMapping("/orderList")
    public String orderList() {
        return "orderList";
    }
    @GetMapping("/myPrivate")
    public String myPrivate(@AuthenticationPrincipal User user,Model model) {
        model.addAttribute("member",memberRepository.findByUserId(user.getUsername()));
        return "myPrivate";
    }
    //장바구니 추가
    @PostMapping("/pocket")
    @ResponseBody
    public Pocket pocket(@AuthenticationPrincipal User user, Long id, String size, String text){
        Optional<Member> OptMember = memberRepository.findByUserId(user.getUsername()); //아이디 = qwe인 사람의 정보
        if(OptMember.isPresent()){ //qwe인 사람의 정보가 존재한다면?
            Optional<Item> OptItem = itemRepository.findById(id); // id = 1 인 아이템의 정보
            if(OptItem.isPresent()){ //id = 1인 아이템이 존재한다면?
                Pocket pocket = Pocket.builder()
                        .size(size)
                        .account(text)
                        .item(OptItem.get())
                        .member(OptMember.get())
                        .location(Location.pocket)
                        .build();
                return pocketRepository.save(pocket);
            }
        }
                return null;
    }
    //장바구니 삭제
    @PostMapping("/delete")
    @ResponseBody
    public void pocketDelete(Long id) {
            pocketRepository.deleteById(id);
        }

    //수정할코드

    //장바구니 전체 삭제
    @PostMapping("/deleteAll")
    @ResponseBody
    public void PocketDeleteAll(@AuthenticationPrincipal User user){
        Optional<Member> OptMember = memberRepository.findByUserId(user.getUsername());
        if(OptMember.isPresent()){
            pocketRepository.deleteByMemberAndLocation(OptMember.get(),Location.pocket);
        }
    }
    //물품 즉시구매
    @PostMapping("/dialectBuy")
    @ResponseBody
    public void dialectBuy(@AuthenticationPrincipal User user, Long id, String size, String text){
        Optional<Member> OptMember = memberRepository.findByUserId(user.getUsername());
        if(OptMember.isPresent()){
            Optional<Item> OptItem = itemRepository.findById(id);
            if(OptItem.isPresent()){
//                Pocket pocket = new Pocket();
//                pocket.setSize(size);
//                pocket.setAccount(text);
//                pocket.setMember(OptMember.get());
//                pocket.setItem(OptItem.get());
//                pocket.setLocation(Location.order);
                Pocket pocket = Pocket.builder()
                        .size(size)
                        .account(text)
                        .item(OptItem.get())
                        .member(OptMember.get())
                        .location(Location.order)
                        .build();

                pocketRepository.save(pocket);
            }
        }
    }
    //주문취소
    @PostMapping("/cancel")
    @ResponseBody
    public void cancel(@AuthenticationPrincipal User user){
        Optional<Member> OptMember = memberRepository.findByUserId(user.getUsername());
        if(OptMember.isPresent()){
            pocketRepository.deleteByMemberAndLocation(OptMember.get(),Location.order);
        }
    }
    //장바구니 체크한 아이템 장바구니 -> 주문하기
    @PostMapping("/orderAll")
    @ResponseBody
    public void orderAll(Long id){
        Optional<Pocket> OptPocket = pocketRepository.findById(id);
            if(OptPocket.isPresent()){
                OptPocket.get().setLocation(Location.order);
                pocketRepository.save(OptPocket.get());
            }
    }
    //댓글 달기
    @PostMapping("/reply")
    @ResponseBody
    public Reply reply(@AuthenticationPrincipal User user,Long itemId, String replyId, String replyPassword, String replyContent,String orignalPassword, RedirectAttributes redirectAttributes){

        Item item = itemRepository.findById(itemId).get();
        Member member = memberRepository.findByUserId(user.getUsername()).get();
        System.out.println("passwordEncoder.matches(user.getPassword(), replyPassword ) = " + passwordEncoder.matches(replyPassword,member.getUserPassword()));
        if(passwordEncoder.matches(replyPassword,member.getUserPassword())){
            if(replyContent == ""){
                redirectAttributes.addFlashAttribute("message","내용을 입력해주세요.");
                return null;
            }else{
                Reply reply = Reply.builder()
                        .replyPassword(passwordEncoder.encode(replyPassword))
                        .replyId(replyId)
                        .replyContent(replyContent)
                        .orignalPassword(member.getUserPassword())
                        .member(member)
                        .item(item)
                        .build();
                return replyRepository.save(reply);
            }
        }
        return null;
    }
}
