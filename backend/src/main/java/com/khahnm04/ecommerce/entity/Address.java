package com.khahnm04.ecommerce.entity;

import com.khahnm04.ecommerce.common.enums.AddressTypeEnum;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressTypeEnum addressType = AddressTypeEnum.HOME;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
