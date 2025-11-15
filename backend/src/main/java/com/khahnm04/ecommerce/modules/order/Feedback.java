package com.khahnm04.ecommerce.modules.order;

import com.khahnm04.ecommerce.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
public class Feedback extends BaseEntity<Long> {

    @Column(name = "rating", nullable = false)
    private Byte rating;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "images")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> images;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_detail_id", nullable = false)
    private OrderDetail orderDetail;

}
