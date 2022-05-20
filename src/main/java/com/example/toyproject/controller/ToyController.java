package com.example.toyproject.controller;

import com.example.toyproject.entity.*;
import com.example.toyproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequiredArgsConstructor
public class ToyController{

    private final PasswordEncoder passwordEncoder;
    private final ItemService itemService;
    private final MemberService memberService;
    private final PocketService pocketService;
    private final ReplyService replyService;
    private final MailService mailService;

    @GetMapping("/")
    public String main() {
        return "index";
    }


    @GetMapping("/search")
    public String topSearch(String keyword, Model model){
        List<Item> item = itemService.findByItemNameContaining(keyword);
        model.addAttribute("item",item);
        return "shirtClothes";
    }

    @GetMapping("/topClothes")
    public String top(Model model) {
        List<Item> items = itemService.findByCategory(ItemType.Top);
        model.addAttribute("item",itemService.findByCategory(ItemType.Top));
        return "topClothes";
    }
    @GetMapping("/bottomClothes")
    public String bottom(Model model) {
        model.addAttribute("item",itemService.findByCategory(ItemType.Bottom));
        return "bottomClothes";
    }
    @GetMapping("/shirtClothes")
    public String shirt(Model model) {
        model.addAttribute("item",itemService.findByCategory(ItemType.Shirt));
        return "shirtClothes";
    }
    @GetMapping("/shoesClothes")
    public String shoes(Model model) {
        model.addAttribute("item",itemService.findByCategory(ItemType.Shoes));
        return "shoesClothes";
    }
    @GetMapping("/accClothes")
    public String acc(Model model) {
        model.addAttribute("item",itemService.findByCategory(ItemType.Acc));
        return "accClothes";
    }


    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Member member, RedirectAttributes redirectAttributes) {
        Optional<Member> OptMember = memberService.findByUserId(member.getUserId().toLowerCase());
        if (OptMember.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "이미 있는 아이디 입니다.");
            return "redirect:/login";
        } else {
            member.setPassword(passwordEncoder.encode(member.getUserPassword()));
            String UserIdWord = member.getUserId().toLowerCase();
            if (UserIdWord.equals("admin")) {
                member.setRole(Role.ADMIN);
            } else {
                member.setRole(Role.MEMBER);
            }
            memberService.save(member);
        }
        redirectAttributes.addFlashAttribute("message", "회원가입 축하드립니다.");
        return "redirect:/login";
    }

    @GetMapping("/itemDetail/{id}")
    public String topDetail(@PathVariable(name = "id")Long id,Model model,RedirectAttributes redirectAttributes) throws Exception {
        Optional<Item> OptItem = itemService.findById(id);
        //내림차순 정렬 (댓글)
        List<Reply> replyList = replyService.findAllByItemId(id);
        if (OptItem.isPresent()) {
            model.addAttribute("item", OptItem.get());
            model.addAttribute("replyList", replyList);
            return "itemDetail";
        }else{
            return "error/404";
        }
    }
    @GetMapping("/login")
    public String login() throws Exception {
        return "login";
    }

    @GetMapping("/fail")
    public String fail(RedirectAttributes redirectAttributes) {


        redirectAttributes.addFlashAttribute("message", "다시 입력해주세요.");
        return "redirect:/login";
    }

    @GetMapping("/buyClothes")
    public String buyClothes(@AuthenticationPrincipal User user,HttpSession httpSession, Model model) {
        if(httpSession.getAttribute("userName") == null){
            Optional<Member> OptMember = memberService.findByUserId(user.getUsername());
            if(OptMember.isPresent()){
                List<Pocket> list = pocketService.findByMemberIdAndLocation(OptMember.get().getId(),Location.order);
                AtomicInteger totalPrice = new AtomicInteger();
                list.forEach(pocket -> {
                    int price = Integer.parseInt(pocket.getItem().getItemPrice());
                    int account = Integer.parseInt(pocket.getAccount());
                    totalPrice.addAndGet(price * account);
                    model.addAttribute("totalPrice",totalPrice);

                    //LOCATION = ORDER 인 요소만 뽑기
                    model.addAttribute("buyList",pocketService.findByMemberAndLocation(OptMember.get(),Location.order));
                });
                return "buyClothes";
            };
        }else{
            Optional<Member> OptMember = memberService.findByUserId((String) httpSession.getAttribute("userName"));
            if(OptMember.isPresent()){
                List<Pocket> list = pocketService.findByMemberIdAndLocation(OptMember.get().getId(),Location.order);
                AtomicInteger totalPrice = new AtomicInteger();
                list.forEach(pocket -> {
                    int price = Integer.parseInt(pocket.getItem().getItemPrice());
                    int account = Integer.parseInt(pocket.getAccount());
                    totalPrice.addAndGet(price * account);
                    model.addAttribute("totalPrice",totalPrice);

                    //LOCATION = ORDER 인 요소만 뽑기
                    model.addAttribute("buyList",pocketService.findByMemberAndLocation(OptMember.get(),Location.order));
                });
                return "buyClothes";
            };
        }

        return null;
    }


    @GetMapping("/myPage")
    public String myPage() {
        return "myPage";
    }
    @GetMapping("/myPocket")
    public String myPocket(@AuthenticationPrincipal User user,HttpSession httpSession,Model model) {
        if(httpSession.getAttribute("userName") == null){
            Optional<Member> OptMember = memberService.findByUserId(user.getUsername());
                //총 합계 구하는 식
                List<Pocket> list = pocketService.findByMemberIdAndLocation(OptMember.get().getId(),Location.pocket);
                AtomicInteger totalPrice = new AtomicInteger();
                list.forEach(pocket -> {
                    int price = Integer.parseInt(pocket.getItem().getItemPrice());
                    int account = Integer.parseInt(pocket.getAccount());
                    totalPrice.addAndGet(price * account);
                    model.addAttribute("pocketList", pocketService.findByMemberIdAndLocation(OptMember.get().getId(), Location.pocket));
                    model.addAttribute("totalPrice", totalPrice);
                });
        } else{
            Optional<Member> kakaomember = memberService.findByUserId((String) httpSession.getAttribute("userName"));
            //총 합계 구하는 식
            List<Pocket> list = pocketService.findByMemberIdAndLocation(kakaomember.get().getId(),Location.pocket);
            AtomicInteger totalPrice = new AtomicInteger();
            list.forEach(pocket -> {
                int price = Integer.parseInt(pocket.getItem().getItemPrice());
                int account = Integer.parseInt(pocket.getAccount());
                totalPrice.addAndGet(price * account);
            });
            model.addAttribute("pocketList",pocketService.findByMemberIdAndLocation(kakaomember.get().getId(),Location.pocket));
            model.addAttribute("totalPrice", totalPrice);
        }
        return "myPocket";
    }
    @GetMapping("/orderList")
    public String orderList() {
        return "orderList";
    }
    @GetMapping("/myPrivate")
    public String myPrivate(@AuthenticationPrincipal User user,HttpSession httpSession, Model model) {
        if(httpSession.getAttribute("name") != null){
            model.addAttribute("member",memberService.findByUserId((String)httpSession.getAttribute("userName")));
        }else{
            model.addAttribute("member",memberService.findByUserId(user.getUsername()));
        }
        return "myPrivate";
    }
    //장바구니 추가
    @PostMapping("/pocket")
    @ResponseBody
    public Pocket pocket(@AuthenticationPrincipal User user,HttpSession httpSession ,Long id, String size, String text){

        if(("사이즈 선택(필수)").equals(size)){
            return null;
        }
        if(httpSession.getAttribute("userName") == null){
            Optional<Member> OptMember = memberService.findByUserId(user.getUsername()); //아이디 = qwe인 사람의 정보
            if(OptMember.isPresent()){ //qwe인 사람의 정보가 존재한다면?
                Optional<Item> OptItem = itemService.findById(id); // id = 1 인 아이템의 정보
                if(OptItem.isPresent()){ //id = 1인 아이템이 존재한다면?
                    Pocket pocket = Pocket.builder()
                            .size(size)
                            .account(text)
                            .item(OptItem.get())
                            .member(OptMember.get())
                            .location(Location.pocket)
                            .build();
                    return pocketService.save(pocket);
                }
            }
        }else{
            Optional<Member> OptMember = memberService.findByUserId((String)httpSession.getAttribute("userName")); //아이디 = qwe인 사람의 정보
            if(OptMember.isPresent()){ //qwe인 사람의 정보가 존재한다면?
                Optional<Item> OptItem = itemService.findById(id); // id = 1 인 아이템의 정보
                if(OptItem.isPresent()){ //id = 1인 아이템이 존재한다면?
                    Pocket pocket = Pocket.builder()
                            .size(size)
                            .account(text)
                            .item(OptItem.get())
                            .member(OptMember.get())
                            .location(Location.pocket)
                            .build();
                    return pocketService.save(pocket);
                }
            }
        }
            return null;
    }
    //장바구니 삭제
    @PostMapping("/delete")
    @ResponseBody
    public void pocketDelete(Long id) {
        pocketService.deleteById(id);
        }

    //수정할코드

    //장바구니 전체 삭제
    @PostMapping("/deleteAll")
    @ResponseBody
    public void PocketDeleteAll(@AuthenticationPrincipal User user){
        Optional<Member> OptMember = memberService.findByUserId(user.getUsername());
        if(OptMember.isPresent()){
            pocketService.deleteByMemberAndLocation(OptMember.get(),Location.pocket);
        }
    }
    //물품 즉시구매
    @PostMapping("/dialectBuy")
    @ResponseBody
    public Pocket dialectBuy(@AuthenticationPrincipal User user,HttpSession httpSession, Long id, String size, String text){
        if(httpSession.getAttribute("name") == null){
            Optional<Member> OptMember = memberService.findByUserId(user.getUsername());
            if(OptMember.isPresent()){
                Optional<Item> OptItem = itemService.findById(id);
                if(OptItem.isPresent()){
                    Pocket pocket = Pocket.builder()
                            .size(size)
                            .account(text)
                            .item(OptItem.get())
                            .member(OptMember.get())
                            .location(Location.order)
                            .build();

                    return pocketService.save(pocket);
                }
            }
        }else{
            Optional<Member> OptMember = memberService.findByUserId((String) httpSession.getAttribute("name"));
            if(OptMember.isPresent()){
                Optional<Item> OptItem = itemService.findById(id);
                if(OptItem.isPresent()){
                    Pocket pocket = Pocket.builder()
                            .size(size)
                            .account(text)
                            .item(OptItem.get())
                            .member(OptMember.get())
                            .location(Location.order)
                            .build();

                    return pocketService.save(pocket);
                }
            }
        }

        return null;
    }
    //주문취소
    @PostMapping("/cancel")
    @ResponseBody
    public void cancel(@AuthenticationPrincipal User user){
        Optional<Member> OptMember = memberService.findByUserId(user.getUsername());
        if(OptMember.isPresent()){
            pocketService.deleteByMemberAndLocation(OptMember.get(),Location.order);
        }
    }
    //장바구니 체크한 아이템 장바구니 -> 주문하기
    @PostMapping("/orderAll")
    @ResponseBody
    public void orderAll(Long id){
        Optional<Pocket> OptPocket = pocketService.findById(id);
            if(OptPocket.isPresent()){
                OptPocket.get().setLocation(Location.order);
                pocketService.save(OptPocket.get());
            }
    }
    //댓글 달기
    @PostMapping("/reply")
    @ResponseBody
    public Reply reply(@AuthenticationPrincipal User user,HttpSession httpSession,Long itemId, String replyId, String replyPassword, String replyContent,String orignalPassword, RedirectAttributes redirectAttributes){
        if(httpSession.getAttribute("name") == null){
            Item item = itemService.findById(itemId).get();
            Member member = memberService.findByUserId(user.getUsername()).get();
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
                    return replyService.save(reply);
                }
        }
        }else{
            Item item = itemService.findById(itemId).get();
            Member member = memberService.findByUserId((String) httpSession.getAttribute("name")).get();
                if(replyContent == ""){
                    redirectAttributes.addFlashAttribute("message","내용을 입력해주세요.");
                    return null;
                }else{
                    Reply reply = Reply.builder()
                            .replyId(replyId)
                            .replyContent(replyContent)
                            .member(member)
                            .item(item)
                            .build();
                    return replyService.save(reply);
                }
        }
        return null;
    }
  @PostMapping("/passwordClean")
  @ResponseBody
    public String passwordClean(@AuthenticationPrincipal User user, String email){
      mailService.sendMail(user,email);
        return email;
  }

}
