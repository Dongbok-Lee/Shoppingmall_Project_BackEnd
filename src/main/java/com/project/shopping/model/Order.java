package com.project.shopping.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne

    @JoinColumn(name = "Product_Id")
    private Product productId;

    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User userId;

    @Column(nullable = false)
    private int productNum;

    @Column(nullable = false)
    private boolean orderComplete;

    @Column(nullable = true)
    private long amount;

    @CreationTimestamp
    private Timestamp orderTime;

    @Builder
    public Order(Product productId, User userId, int productNum, boolean orderComplete, long amount, Timestamp orderTime) {
        this.productId = productId;
        this.userId = userId;
        this.productNum = productNum;
        this.orderComplete = orderComplete;
        this.amount = amount;
        this.orderTime = orderTime;
    }
}
