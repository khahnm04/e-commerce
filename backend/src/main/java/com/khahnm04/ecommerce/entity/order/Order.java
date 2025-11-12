package com.khahnm04.ecommerce.entity.order;

import com.khahnm04.ecommerce.common.enums.OrderStatusEnum;
import com.khahnm04.ecommerce.entity.BaseEntity;
import com.khahnm04.ecommerce.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity<Long> {

    @Column(name = "order_code", nullable = false, unique = true)
    private String orderCode;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @ColumnDefault("'PENDING'")
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatusEnum orderStatus = OrderStatusEnum.PENDING;

    @Column(name = "shipping_name", nullable = false)
    private String shippingName;

    @Column(name = "shipping_phone", nullable = false, length = 20)
    private String shippingPhone;

    @Column(name = "shipping_province", nullable = false)
    private String shippingProvince;

    @Column(name = "shipping_ward", nullable = false)
    private String shippingWard;

    @Column(name = "shipping_home_address", nullable = false)
    private String shippingHomeAddress;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "shipped_date")
    private LocalDate shippedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<Payment> payments;

}
