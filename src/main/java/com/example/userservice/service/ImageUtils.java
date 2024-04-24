package com.example.userservice.service;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static byte[]  resizeImage(MultipartFile file){
        BufferedImage resizedImage;
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int width = 100; // desired width of the avatar
        int height = 100; // desired height of the avatar
        resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return bufferedImageToByteArray(resizedImage,file.getContentType());
    }

    public static byte[] bufferedImageToByteArray(BufferedImage image, String format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, format, baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }
}
