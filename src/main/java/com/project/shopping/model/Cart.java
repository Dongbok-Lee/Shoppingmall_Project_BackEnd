package com.project.shopping.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Cart extends BaseTimeEntity {
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

    // set builder 패턴으로 수정
    @Setter
    private long  productNum; // 상품 개수

    @Setter
    @Column(nullable = false)
    private  String status;

    @Builder
    public Cart(Product productId, User userId, long productNum, String status) {
        this.productId = productId;
        this.userId = userId;
        this.productNum = productNum;
        this.status = status;
    }
}
