package com.example.userservice.domain.avatar;

import com.example.userservice.web.exc.AvatarNotFoundException;
import com.example.userservice.web.exc.UnsupportedMediaTypeExc;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class AvatarService {

    private static final Logger log =
            LoggerFactory.getLogger(AvatarService.class);
    private AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Avatar find(String userId){
        return avatarRepository.findByUserId(userId).orElseThrow(()->new AvatarNotFoundException(userId));
    }

    public Avatar create(String userId, MultipartFile file){
        // Check file type
        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            throw new UnsupportedMediaTypeExc();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Thumbnails.of(file.getInputStream())
                    .size(100, 100) // desired width and height
                    .outputFormat("JPEG")
                    .toOutputStream(baos);
        } catch (IOException e) {
            throw new RuntimeException("problem with file");
        }

        // Convert resized image to byte array
        byte[] content = baos.toByteArray();

        log.info("content length: {}", content.length);
        Avatar avatar = Avatar.Of(userId, file.getContentType(), content);
        return avatarRepository.save(avatar);
    }


    public Avatar update(String userId,MultipartFile file){
        Optional<Avatar> byUserId = avatarRepository.findByUserId(userId);
        if (byUserId.isPresent()){
            Avatar avatar = byUserId.get();
            byte[] bytes = new byte[0];
            try {
                bytes = file.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Avatar updated = new Avatar(avatar.id(), userId, file.getContentType(),bytes);
            return avatarRepository.save(updated);
        }
         else return create(userId, file);
    }

}
