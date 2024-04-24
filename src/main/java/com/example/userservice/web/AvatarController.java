package com.example.userservice.web;

import com.example.userservice.domain.avatar.Avatar;
import com.example.userservice.domain.avatar.AvatarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AvatarController {

    private AvatarService avatarService;
    private static final Logger log =
            LoggerFactory.getLogger(AvatarController.class);

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/avatars")
    public Avatar get(@AuthenticationPrincipal Jwt jwt,
                      @RequestParam(value = "userId",required = false) String userId){
        log.info("get from {}", jwt.getSubject());
        if (userId != null) return avatarService.find(userId);
        return avatarService.find(jwt.getSubject());
    }

    /*@PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Avatar post(@AuthenticationPrincipal Jwt jwt,
                       @RequestParam("file") MultipartFile file){
        return avatarService.create(jwt.getSubject(),file);
    }*/

    @PutMapping("/avatars")
    public Avatar put(@AuthenticationPrincipal Jwt jwt,
                       @RequestParam("file") MultipartFile file){
        return avatarService.update(jwt.getSubject(),file);
    }
}
