package com.project.shopping.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Product_ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User userId;


    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Lob
    private String content;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private long price;

    @Setter
    @Column(nullable = false)
    private int total;

    @Setter
    @Column(nullable = false)
    private String imgUrl;

    @Setter
    @CreationTimestamp
    private Timestamp createDate;


    @OneToMany(mappedBy = "productId")
    private List<Review> reviews = new ArrayList<>(); // 일대 다 review와 연관 관계 맺기


    @OneToMany(mappedBy = "productId")
    private  List<Cart> carts = new ArrayList<>();

    @Builder
    public Product(User userId, String title, String content, String name, long price, int total, String imgUrl, Timestamp createDate) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.name = name;
        this.price = price;
        this.total = total;
        this.imgUrl = imgUrl;
        this.createDate = createDate;
    }
}
