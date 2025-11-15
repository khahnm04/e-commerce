package com.khahnm04.ecommerce.modules.banner.domain.entity;

import com.khahnm04.ecommerce.shared.common.enums.StatusEnum;
import com.khahnm04.ecommerce.shared.entity.BaseEntity;
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
@Table(name = "banners")
public class Banner extends BaseEntity<Long> {

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "image", nullable = false, columnDefinition = "TEXT")
    private String image;

    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status = StatusEnum.ACTIVE;

    @OneToMany(mappedBy = "banner", fetch = FetchType.LAZY)
    private List<BannerDetail> bannerDetails;

}
