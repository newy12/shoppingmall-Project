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
public class ToyController {

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
    public String topSearch(String keyword, Model model) {
        List<Item> item = itemService.findByItemNameContaining(keyword);
        model.addAttribute("item", item);
        return "topClothes";
    }
    @GetMapping("/Clothes")
    public String top(Model model, @RequestParam("type")String clothType) {

        switch(clothType) {
            case "Top":
                model.addAttribute("item", itemService.findByCategory(ItemType.Top));
                return "topClothes";
            case "Bottom":
                model.addAttribute("item", itemService.findByCategory(ItemType.Bottom));
                return "topClothes";
            case "Shirt":
                model.addAttribute("item", itemService.findByCategory(ItemType.Shirt));
                return "topClothes";
            case "Shoes":
                model.addAttribute("item", itemService.findByCategory(ItemType.Shoes));
                return "topClothes";
            case "Acc":
                model.addAttribute("item", itemService.findByCategory(ItemType.Acc));
                return "topClothes";
        }
        //ddddzzzz
        return null;
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Member member, RedirectAttributes redirectAttributes) {
        Optional<Member> OptMember = memberService.findByUserId(member.getUserId().toLowerCase());
        if (OptMember.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "?????? ?????? ????????? ?????????.");
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
        redirectAttributes.addFlashAttribute("message", "???????????? ??????????????????.");
        return "redirect:/login";
    }

    @GetMapping("/itemDetail/{id}")
    public String topDetail(@PathVariable(name = "id") Long id, Model model, RedirectAttributes redirectAttributes) throws Exception {
        Optional<Item> OptItem = itemService.findById(id);
        //???????????? ?????? (??????)
        List<Reply> replyList = replyService.findAllByItemId(id);
        if (OptItem.isPresent()) {
            model.addAttribute("item", OptItem.get());
            model.addAttribute("replyList", replyList);

            return "itemDetail";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/login")
    public String login() throws Exception {
        return "login";
    }

    @GetMapping("/fail")
    public String fail(RedirectAttributes redirectAttributes) {


        redirectAttributes.addFlashAttribute("message", "?????? ??????????????????.");
        return "redirect:/login";
    }

    @GetMapping("/buyClothes")
    public String buyClothes(@AuthenticationPrincipal User user, HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("userName") == null) {
            Optional<Member> OptMember = memberService.findByUserId(user.getUsername());
            if (OptMember.isPresent()) {
                List<Pocket> list = pocketService.findByMemberIdAndLocation(OptMember.get().getId(), Location.order);
                AtomicInteger totalPrice = new AtomicInteger();
                list.forEach(pocket -> {
                    int price = Integer.parseInt(pocket.getItem().getItemPrice());
                    int account = Integer.parseInt(pocket.getAccount());
                    totalPrice.addAndGet(price * account);
                    model.addAttribute("totalPrice", totalPrice);

                    //LOCATION = ORDER ??? ????????? ??????
                    model.addAttribute("buyList", pocketService.findByMemberAndLocation(OptMember.get(), Location.order));
                });
                return "buyClothes";
            }
            ;
        } else {
            Optional<Member> OptMember = memberService.findByUserId((String) httpSession.getAttribute("userName"));
            if (OptMember.isPresent()) {
                List<Pocket> list = pocketService.findByMemberIdAndLocation(OptMember.get().getId(), Location.order);
                AtomicInteger totalPrice = new AtomicInteger();
                list.forEach(pocket -> {
                    int price = Integer.parseInt(pocket.getItem().getItemPrice());
                    int account = Integer.parseInt(pocket.getAccount());
                    totalPrice.addAndGet(price * account);
                    model.addAttribute("totalPrice", totalPrice);

                    //LOCATION = ORDER ??? ????????? ??????
                    model.addAttribute("buyList", pocketService.findByMemberAndLocation(OptMember.get(), Location.order));
                });
                return "buyClothes";
            }
            ;
        }

        return null;
    }


    @GetMapping("/myPage")
    public String myPage() {
        return "myPage";
    }

    @GetMapping("/myPocket")
    public String myPocket(@AuthenticationPrincipal User user, HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("userName") == null) {
            Optional<Member> OptMember = memberService.findByUserId(user.getUsername());
            //??? ?????? ????????? ???
            List<Pocket> list = pocketService.findByMemberIdAndLocation(OptMember.get().getId(), Location.pocket);
            AtomicInteger totalPrice = new AtomicInteger();
            list.forEach(pocket -> {
                int price = Integer.parseInt(pocket.getItem().getItemPrice());
                int account = Integer.parseInt(pocket.getAccount());
                totalPrice.addAndGet(price * account);
                model.addAttribute("pocketList", pocketService.findByMemberIdAndLocation(OptMember.get().getId(), Location.pocket));
                model.addAttribute("totalPrice", totalPrice);
            });
        } else {
            Optional<Member> kakaomember = memberService.findByUserId((String) httpSession.getAttribute("userName"));
            //??? ?????? ????????? ???
            List<Pocket> list = pocketService.findByMemberIdAndLocation(kakaomember.get().getId(), Location.pocket);
            AtomicInteger totalPrice = new AtomicInteger();
            list.forEach(pocket -> {
                int price = Integer.parseInt(pocket.getItem().getItemPrice());
                int account = Integer.parseInt(pocket.getAccount());
                totalPrice.addAndGet(price * account);
            });
            model.addAttribute("pocketList", pocketService.findByMemberIdAndLocation(kakaomember.get().getId(), Location.pocket));
            model.addAttribute("totalPrice", totalPrice);
        }
        return "myPocket";
    }

    @GetMapping("/orderList")
    public String orderList(@AuthenticationPrincipal User user,HttpSession httpSession,Model model) {
        if(httpSession.getAttribute("userName") == null){
            Member member = memberService.findByUserId(user.getUsername()).get();
            List<Pocket> pocketList = pocketService.findByMemberAndLocation(member,Location.orderComplete);
            model.addAttribute("pocketList",pocketList);
            return "orderList";
        }else{
            Member member = memberService.findByUserId((String) httpSession.getAttribute("userName")).get();
            List<Pocket> pocketList = pocketService.findByMemberAndLocation(member,Location.orderComplete);
            model.addAttribute("pocketList",pocketList);
            return "orderList";
        }

    }

    @GetMapping("/myPrivate")
    public String myPrivate(@AuthenticationPrincipal User user, HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("name") != null) {
            model.addAttribute("member", memberService.findByUserId((String) httpSession.getAttribute("userName")));
        } else {
            model.addAttribute("member", memberService.findByUserId(user.getUsername()));
        }
        return "myPrivate";
    }

    //???????????? ??????
    @PostMapping("/pocket")
    @ResponseBody
    public Pocket pocket(@AuthenticationPrincipal User user, HttpSession httpSession, Long id, String size, String text) {

        if (("????????? ??????(??????)").equals(size)) {
            return null;
        }
        if (httpSession.getAttribute("userName") == null) {
            Optional<Member> OptMember = memberService.findByUserId(user.getUsername()); //????????? = qwe??? ????????? ??????
            if (OptMember.isPresent()) { //qwe??? ????????? ????????? ????????????????
                Optional<Item> OptItem = itemService.findById(id); // id = 1 ??? ???????????? ??????
                if (OptItem.isPresent()) { //id = 1??? ???????????? ????????????????
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
        } else {
            Optional<Member> OptMember = memberService.findByUserId((String) httpSession.getAttribute("userName")); //????????? = qwe??? ????????? ??????
            if (OptMember.isPresent()) { //qwe??? ????????? ????????? ????????????????
                Optional<Item> OptItem = itemService.findById(id); // id = 1 ??? ???????????? ??????
                if (OptItem.isPresent()) { //id = 1??? ???????????? ????????????????
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

    //???????????? ??????
    @PostMapping("/delete")
    @ResponseBody
    public void pocketDelete(Long id) {
        pocketService.deleteById(id);
    }

    //???????????????

    //???????????? ?????? ??????
    @PostMapping("/deleteAll")
    @ResponseBody
    public void PocketDeleteAll(@AuthenticationPrincipal User user) {
        Optional<Member> OptMember = memberService.findByUserId(user.getUsername());
        if (OptMember.isPresent()) {
            pocketService.deleteByMemberAndLocation(OptMember.get(), Location.pocket);
        }
    }

    //?????? ????????????
    @PostMapping("/dialectBuy")
    @ResponseBody
    public Pocket dialectBuy(@AuthenticationPrincipal User user, HttpSession httpSession, Long id, String size, String text) {
        if (httpSession.getAttribute("name") == null) {
            Optional<Member> OptMember = memberService.findByUserId(user.getUsername());
            if (OptMember.isPresent()) {
                Optional<Item> OptItem = itemService.findById(id);
                if (OptItem.isPresent()) {
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
        } else {
            Optional<Member> OptMember = memberService.findByUserId((String) httpSession.getAttribute("name"));
            if (OptMember.isPresent()) {
                Optional<Item> OptItem = itemService.findById(id);
                if (OptItem.isPresent()) {
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

    //????????????
    @PostMapping("/cancel")
    @ResponseBody
    public void cancel(@AuthenticationPrincipal User user) {
        Optional<Member> OptMember = memberService.findByUserId(user.getUsername());
        if (OptMember.isPresent()) {
            pocketService.deleteByMemberAndLocation(OptMember.get(), Location.order);
        }
    }

    //???????????? ????????? ????????? ???????????? -> ????????????
    @PostMapping("/orderAll")
    @ResponseBody
    public void orderAll(Long id) {
        Optional<Pocket> OptPocket = pocketService.findById(id);
        if (OptPocket.isPresent()) {
            OptPocket.get().setLocation(Location.order);
            pocketService.save(OptPocket.get());
        }
    }

    //?????? ??????
    @PostMapping("/reply")
    @ResponseBody
    public Reply reply(@AuthenticationPrincipal User user, HttpSession httpSession, Long itemId, String replyId, String replyPassword, String replyContent, String orignalPassword, RedirectAttributes redirectAttributes) {
        if (httpSession.getAttribute("name") == null) {
            Item item = itemService.findById(itemId).get();
            Member member = memberService.findByUserId(user.getUsername()).get();
            System.out.println("passwordEncoder.matches(user.getPassword(), replyPassword ) = " + passwordEncoder.matches(replyPassword, member.getUserPassword()));
            if (passwordEncoder.matches(replyPassword, member.getUserPassword())) {
                if (replyContent == "") {
                    redirectAttributes.addFlashAttribute("message", "????????? ??????????????????.");
                    return null;
                } else {
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
        } else {
            Item item = itemService.findById(itemId).get();
            Member member = memberService.findByUserId((String) httpSession.getAttribute("name")).get();
            if (replyContent == "") {
                redirectAttributes.addFlashAttribute("message", "????????? ??????????????????.");
                return null;
            } else {
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
    public String passwordClean(@AuthenticationPrincipal User user, String email) {
        mailService.sendMail(user, email);
        return email;
    }

    @PostMapping("/passwordChange")
    @ResponseBody
    public void passwordChange(@AuthenticationPrincipal User user, String password) {
        Member member = memberService.findByUserId(user.getUsername()).get();
        member.setPassword(passwordEncoder.encode(password));
        memberService.save(member);
    }
    @PostMapping("/orderComplete")
    @ResponseBody
    public void orderDelete(@AuthenticationPrincipal User user, HttpSession httpSession){
        if(httpSession.getAttribute("userName") == null){
            Member member = memberService.findByUserId(user.getUsername()).get();
            List<Pocket> pocket = pocketService.findByMemberAndLocation(member,Location.order);
            for (int i = 0; i < pocket.size(); i++) {
                pocket.get(i).setLocation(Location.orderComplete);
                pocketService.save(pocket.get(i));
            }
        }else{
            Member member = memberService.findByUserId((String) httpSession.getAttribute("userName")).get();
            List<Pocket> pocket = pocketService.findByMemberAndLocation(member,Location.order);
            for (int i = 0; i < pocket.size(); i++) {
                pocket.get(i).setLocation(Location.orderComplete);
                pocketService.save(pocket.get(i));
            }
        }
    }

}
