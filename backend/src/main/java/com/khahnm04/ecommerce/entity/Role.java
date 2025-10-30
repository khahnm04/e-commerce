package com.khahnm04.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role extends BaseEntity<Long> {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToMany
    Set<Permission> permissions;

}
