package com.project.shopping.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder
@Getter
public class OAuth2Attribute {

    private  Map<String, Object> attributes;
    private String attributeKey;
    private  String email;
    private  String name;
    private  String picture;
    public static OAuth2Attribute of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
    switch (registrationId){
        case "google":
            return ofGoogle(userNameAttributeName,attributes);
        case "kakao":
            return ofKakao(userNameAttributeName,attributes);
        default:
            throw  new RuntimeException();
    }

    }

    private static OAuth2Attribute ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String)kakaoProfile.get("profile_image_url"))
                .attributes(kakaoAccount)
                .attributeKey(userNameAttributeName)
                .build();

    }

    private static OAuth2Attribute ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attribute.builder().name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .attributeKey(userNameAttributeName).build();
    }

    public Map<String,Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id",attributeKey);
        map.put("key",attributeKey);
        map.put("name",name);
        map.put("email",email);
        map.put("picture",picture);
        return map;


    }
}
