package com.bootiful.web.util;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageUpload {

    private final String IMAGE_JPG_REGEX = "(.jpg|.jpeg)";

    private String outputName;

    private String uploadPath;

    private MultipartFile file;

    @Getter
    private String outputAbsolutePath;

    @Builder
    public ImageUpload(MultipartFile file, String uploadPath, String outputName) {
        this.file = file;
        this.uploadPath = uploadPath;
        this.outputName = outputName;
    }

    public void upload() throws Exception {

        if (file == null || file.isEmpty()
                || !StringUtils.hasText(uploadPath)
                || !StringUtils.hasText(outputName)) {

            throw new Exception("upload properties must be valid");

        }

        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());

        if (!ext.matches(IMAGE_JPG_REGEX)) {
            throw new Exception("file type must be image");
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File outputFile = new File(dir.getAbsolutePath() + File.separator + outputName + ".jpg");
            ImageIO.write(bufferedImage, "jpg", outputFile);

            this.outputAbsolutePath = outputFile.getAbsolutePath();

        } catch (Exception e) {

            throw new Exception();
        }
    }
}
