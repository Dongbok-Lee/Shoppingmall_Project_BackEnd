package com.project.shopping.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Cart_ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "Product_ID")
    private Product productId;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User userId;

    @CreationTimestamp
    private Timestamp createTime;

    // set builder 패턴으로 수정
    @Setter
    private long carttotal;
    @Builder
    public Cart(Product productId, User userId, Timestamp createTime, long carttotal) {
        this.productId = productId;
        this.userId = userId;
        this.createTime = createTime;
        this.carttotal = carttotal;
    }
}
