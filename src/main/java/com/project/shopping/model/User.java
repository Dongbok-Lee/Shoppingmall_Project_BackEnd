package com.project.shopping.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name ="User_ID")
    private String userId;


    @Setter
    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false)
    private String username;

    @Setter
    @Column(nullable = false)
    private String address;

    @Setter
    @Column(nullable = false)
    private int age;

    @Setter
    @Column(nullable = false,length = 20)
    private String nickname;

    @Setter
    @Column(nullable = false,length=13)
    private String phoneNumber;

    @Setter
    @Column(nullable = false)
    private String postCode;


    private String roles;


    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }

        return new ArrayList<>();
    }



    @Builder
    public User(String email,String password, String username, String address, int age, String nickname, String phoneNumber,String postCode ,String roles) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.address = address;
        this.age = age;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.postCode = postCode;
        this.roles = roles;
    }
}
