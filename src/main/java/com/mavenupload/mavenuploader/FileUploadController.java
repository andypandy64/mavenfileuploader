package com.mavenupload.mavenuploader;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    //static ArrayList<MultipartFile> uploadedFiles = new ArrayList<>();

    @GetMapping("/")
    public String landingpage() {
        return "landingpage";
    }

    @PostMapping("/newfile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model, SessionStatus sessionStatus) {
        String[] parts = file.getOriginalFilename().split("\\.");
        String uploadName = parts[0];
        UploadedFiles.uploadedFiles.add(new UploadedFile(file,uploadName));
        for (UploadedFile f : UploadedFiles.uploadedFiles ) {
            f.file.getOriginalFilename();
        }

        sessionStatus.setComplete();
        return "redirect:/uploads/" + uploadName;

        // NEW UPLOAD METHOD
    }
}
