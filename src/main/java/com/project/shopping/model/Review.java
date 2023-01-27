package com.project.shopping.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Review_ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "Product_ID")
    private Product productId;


    // set builder 패턴 이용해 바꾸기
    @Setter
    @Column(nullable = false, length=255)
    private String title;

    @Setter
    @Lob
    private String content;

    @Setter
    @Column(nullable = true, length=255)
    private String imageUrl;


    @Column(nullable = false)
    @Setter
    private  String status;



    @Builder
    public Review(User userId, Product productId, String title, String content, String imageUrl, String status) {
        this.userId = userId;
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.status = status;
    }
}
