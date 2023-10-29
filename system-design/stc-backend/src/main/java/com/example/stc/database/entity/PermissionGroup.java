package com.example.stc.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "PERMISSION_GROUPS")
public class PermissionGroup {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "GROUP_NAME")
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Permission> permissions = new ArrayList<>();

    @OneToMany(mappedBy = "permissionGroup")
    private List<Item> items = new ArrayList<>();

}
