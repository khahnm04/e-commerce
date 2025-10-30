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
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "ward", nullable = false)
    private String ward;

    @Column(name = "home_address")
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
