package com.khahnm04.shopco.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "note")
    private String note;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "receiver_name", nullable = false, length = 150)
    private String receiverName;

    @Column(name = "receiver_phone", nullable = false, length = 20)
    private String receiverPhone;

    @Column(name = "receiver_address", nullable = false)
    private String receiverAddress;

    @Column(name = "receiver_provinces", nullable = false, length = 100)
    private String receiverProvinces;

    @Column(name = "receiver_district", nullable = false, length = 100)
    private String receiverDistrict;

    @Column(name = "receiver_ward", nullable = false, length = 100)
    private String receiverWard;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderDetail> orderDetails = new ArrayList<>();

}
