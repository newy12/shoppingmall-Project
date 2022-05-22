package com.example.toyproject.controller;

import com.example.toyproject.entity.*;
import com.example.toyproject.repository.FilesRepository;
import com.example.toyproject.repository.ItemRepository;
import com.example.toyproject.repository.MemberRepository;
import com.example.toyproject.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class adminController {
    private final ItemService itemService;
    private final MemberService memberService;
    private final FilesService filesService;

    private final PocketService pocketService;

    @Value("${files.files-dir}")
    private String fileDir;

    @GetMapping("/adminPage")
    public String adminPage(){
        return "admin/adminPage";
    }
    @GetMapping("/enrollItem")
    public String enrollItem(){
        return "admin/enrollItem";
    }
    @PostMapping("/enrollItem")
    public String enrollItem(@AuthenticationPrincipal User user,Item item, @RequestPart MultipartFile files , RedirectAttributes redirectAttributes) throws IOException {
        Optional<Member> member = memberService.findByUserId(user.getUsername());
        if(member.isPresent()){
            Files file = new Files();
            String sourceFileName = files.getOriginalFilename();
            String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
            File destinationFile;
            String destinationFileName;
            String fileUrl = fileDir;
            do {
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
                destinationFile = new File(fileUrl + destinationFileName);
            } while (destinationFile.exists());
            destinationFile.getParentFile().mkdirs();
            files.transferTo(destinationFile);
            file.setFilename(destinationFileName);
            file.setFileOriName(sourceFileName);
            file.setFileurl(fileUrl);
            item.setFile(file);
            filesService.save(file);
            itemService.save(item);
            redirectAttributes.addFlashAttribute("message","등록되었습니다,");
            return "redirect:/adminPage";
        }
        return null;
    }
    @GetMapping("/memberIdentity")
    public String memberIdentity(Model model){
        model.addAttribute("member",memberService.findAll());

        return "admin/memberIdentity";
    }
    @GetMapping("/orderCheck")
    public String orderCheck(Model model) {
        List<Pocket> pocketList = pocketService.findAllByLocation(Location.orderComplete);
        model.addAttribute("pocketList",pocketList);
        return "admin/orderCheck";
    }
    //수정
}
