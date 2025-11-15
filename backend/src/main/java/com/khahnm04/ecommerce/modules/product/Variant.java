package com.khahnm04.ecommerce.modules.product;

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
@Table(name = "variants")
public class Variant extends BaseEntity<Long> {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY)
    private List<VariantValue> variantValues;

}
