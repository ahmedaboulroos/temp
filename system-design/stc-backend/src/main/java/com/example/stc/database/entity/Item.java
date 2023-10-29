package com.example.stc.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {

    @Id
    @GeneratedValue
    protected Long id;

    @Enumerated(EnumType.STRING)
    protected ItemType type;

    @Column(nullable = false)
    protected String name;

    @ManyToOne
    protected PermissionGroup permissionGroup;

    @ManyToOne
    private Folder containingFolder;

}
