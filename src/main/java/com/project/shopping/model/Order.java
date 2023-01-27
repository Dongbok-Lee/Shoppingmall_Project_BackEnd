package com.project.shopping.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "`order`")
public class Order extends BaseTimeEntity{

    @Id
    @Column(name ="orderID")
    private String id;

    private static int count = 0;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User user;

    @Setter
    @Column(nullable = true, length=1000)
    private String orderComplete;

    @Setter
    @Column(nullable = true)
    private long amount;

    @CreatedDate
    private LocalDateTime orderTime;

    @Setter
    @Column
    private LocalDateTime  paymentCompleteDate;

    @Column(nullable = false)
    private String status;

    @Builder
    public Order(ArrayList<OrderDetail> products, User user, String orderComplete, long amount, LocalDateTime paymentCompleteDate, String status) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        this.id = "oid" + dtf.format(LocalDateTime.now()) + count++;
        this.products = products;
        this.user = user;
        this.orderComplete = orderComplete;
        this.amount = amount;
        this.paymentCompleteDate = paymentCompleteDate;
        this.status = status;
    }

    public void addProduct(OrderDetail orderDetail){
        products.add(orderDetail);
        orderDetail.setOrder(this);
    }

}
