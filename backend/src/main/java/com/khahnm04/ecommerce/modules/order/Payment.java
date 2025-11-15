package com.khahnm04.ecommerce.modules.order;

import com.khahnm04.ecommerce.shared.common.enums.PaymentMethodEnum;
import com.khahnm04.ecommerce.shared.common.enums.PaymentStatusEnum;
import com.khahnm04.ecommerce.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment extends BaseEntity<Long> {

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethodEnum paymentMethod;

    @ColumnDefault("'UNPAID'")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatusEnum paymentStatus = PaymentStatusEnum.UNPAID;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "transaction_code", length = 100)
    private String transactionCode;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}
