package com.khahnm04.ecommerce.entity.brand;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.entity.BaseEntity;
import com.khahnm04.ecommerce.entity.product.Product;
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
@Table(name = "brands")
public class Brand extends BaseEntity<Long> {

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo", columnDefinition = "TEXT")
    private String logo;

    @Column(name = "country")
    private String country;

    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status = StatusEnum.ACTIVE;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> products;

}
