package com.project.shopping.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Review {
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


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Setter
    private LocalDate modifieddate;


    @Column(nullable = false)
    @Setter
    private  String status;



    @Builder
    public Review(User userId, Product productId, String title, String content, String imageUrl, LocalDate reviewcreateTime, LocalDate modifieddate, String status) {
        this.userId = userId;
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createTime = reviewcreateTime;
        this.modifieddate = modifieddate;
        this.status = status;
    }
}
