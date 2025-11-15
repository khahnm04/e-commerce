package com.khahnm04.ecommerce.modules.inventory;

import com.khahnm04.ecommerce.shared.entity.BaseEntity;
import com.khahnm04.ecommerce.modules.product.ProductVariant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventories")
public class Inventory extends BaseEntity<Long> {

    @ColumnDefault("0")
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(mappedBy = "inventory", fetch = FetchType.LAZY)
    private List<StockMovement> stockMovements;

}
