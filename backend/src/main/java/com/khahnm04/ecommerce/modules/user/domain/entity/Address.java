package com.khahnm04.ecommerce.modules.user.domain.entity;

import com.khahnm04.ecommerce.shared.common.enums.AddressTypeEnum;
import com.khahnm04.ecommerce.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address extends BaseEntity<Long> {

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "ward", nullable = false)
    private String ward;

    @Column(name = "home_address", nullable = false)
    private String homeAddress;

    @Column(name = "reminiscent_name")
    private String reminiscentName;

    @ColumnDefault("'HOME'")
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressTypeEnum addressType = AddressTypeEnum.HOME;

    @ColumnDefault("0")
    @Column(name = "is_default")
    private Boolean isDefault = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
