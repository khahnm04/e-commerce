package com.khahnm04.fashionecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role extends BaseEntity {

    @Id
    private String name;

    private String description;

    @ManyToMany
    Set<Permission> permissions;

}
