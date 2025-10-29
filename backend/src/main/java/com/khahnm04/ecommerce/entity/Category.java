package com.khahnm04.ecommerce.entity;

import com.khahnm04.ecommerce.common.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image", length = 500)
    private String image;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status = StatusEnum.ACTIVE;

}
