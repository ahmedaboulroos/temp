package com.example.stc.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "PERMISSIONS")
public class Permission {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USER_EMAIL")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_level")
    private PermissionLevel level;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private PermissionGroup group;

}
