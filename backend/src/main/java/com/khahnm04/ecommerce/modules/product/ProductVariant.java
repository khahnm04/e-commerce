package com.khahnm04.ecommerce.modules.product;

import com.khahnm04.ecommerce.shared.entity.BaseEntity;
import com.khahnm04.ecommerce.modules.inventory.Inventory;
import com.khahnm04.ecommerce.modules.order.OrderDetail;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_variants")
public class ProductVariant extends BaseEntity<Long> {

    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "old_price", nullable = false)
    private Long oldPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<ProductVariantValue> productVariantValues;

}
