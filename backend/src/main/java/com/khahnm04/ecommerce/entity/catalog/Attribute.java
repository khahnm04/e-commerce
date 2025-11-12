package com.khahnm04.ecommerce.entity.catalog;

import com.khahnm04.ecommerce.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attributes")
public class Attribute extends BaseEntity<Long> {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "attribute", fetch = FetchType.LAZY)
    private List<ProductAttributeValue> productAttributeValues;

}
