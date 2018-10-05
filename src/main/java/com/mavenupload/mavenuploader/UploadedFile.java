package com.mavenupload.mavenuploader;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UploadedFile {
    public MultipartFile file;
    public String uploadName;
    public byte[] bytes;
    public String bytecode;
    public String embed;

    public UploadedFile(MultipartFile file, String uploadName) {
        this.file = file;
        this.uploadName = uploadName;
        try {
            bytes = file.getBytes();
            byte[] encodeBase64 = Base64.encodeBase64(bytes);
            this.bytecode = new String(encodeBase64, "UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setEmbed();
    }

    private void setEmbed() {
        String fileType = file.getOriginalFilename();
        if (fileType.contains(".jpg") || fileType.contains(".jpeg") || fileType.contains(".png")){
            embed = "<img alt=\"img\" src=\"data:image/jpeg;base64," + bytecode + "\"/>";
        } else if (fileType.contains(".pdf")) {
            embed = "<object data:\"application/pdf;base64," + bytecode + "\"/>";
        } else if (fileType.contains(".txt") || fileType.contains(".text")) {
            try {
                embed = "<div>";
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

                while ((line = br.readLine()) != null) {
                    embed += "<p>" + line + "</p>";
                }
                embed += "</div>";
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (fileType.contains(".java") || fileType.contains(".py") || fileType.contains(".cs")) {
            try {
                embed = "<pre><code>";
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

                while ((line = br.readLine()) != null) {
                    embed += line + "\n";
                }
                embed += "</code></pre>";
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            embed = "Filetype: " + file.getContentType() + "\n"
                    + "Size: " + file.getSize();
        }
    }
}
