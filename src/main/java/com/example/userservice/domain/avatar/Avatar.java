package com.example.userservice.domain.avatar;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("avatars")
public record Avatar (
        @Id Long id,
        String userId,
        String fileName,
        byte[] content
){
    public static Avatar Of(String userId, String fileName, byte[] content){
        return new Avatar(null,userId,fileName,content);
    }
}



