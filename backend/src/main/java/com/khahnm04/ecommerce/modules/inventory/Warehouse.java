package com.khahnm04.ecommerce.modules.inventory;

import com.khahnm04.ecommerce.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouses")
public class Warehouse extends BaseEntity<Long> {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
    private List<Inventory> inventories;

}
