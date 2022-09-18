package com.project.shopping.oauth;

import com.project.shopping.dto.UserDTO;
import com.project.shopping.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequstMapper {
    public User user(OAuth2User oAuth2User){
        return  User.builder().email((String) oAuth2User.getAttributes().get("email")).build();
    }
}
