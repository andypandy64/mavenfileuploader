package com.mavenupload.mavenuploader;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FileViewController {

    @GetMapping("/uploads")
    public String viewFiles(Model model) {
        model.addAttribute("uploads", UploadedFiles.uploadedFiles);
        return "uploads";
    }

    @GetMapping("/uploads/{filename}")
    public String serveFile(@PathVariable String filename, @ModelAttribute("fileEncoded") String fileEncoded, Model model, HttpServletResponse response) {
        for (UploadedFile uf : UploadedFiles.uploadedFiles) {
            if (uf.file.getOriginalFilename().contains(filename)) {
                model.addAttribute("fileEncoded", uf.bytecode);
                model.addAttribute("embed", uf.embed);
                if (uf.file.getOriginalFilename().contains(".pdf")) {

                    byte[] pdfContent = uf.bytes;
                    response.setContentType("application/pdf");
                    response.setContentLength(pdfContent.length);
                    try {
                        response.getOutputStream().write(pdfContent);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
            }
        }
        return "uploads";
    }
}
