package com.khahnm04.ecommerce.entity.inventory;

import com.khahnm04.ecommerce.common.enums.StockMovementTypeEnum;
import com.khahnm04.ecommerce.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_movements")
public class StockMovement extends BaseEntity<Long> {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private StockMovementTypeEnum type;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

}
