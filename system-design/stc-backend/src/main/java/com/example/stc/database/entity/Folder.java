package com.example.stc.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "FOLDERS")
@PrimaryKeyJoinColumn(name = "FOLDER_ID")
public class Folder extends Item {

    @OneToMany(mappedBy = "containingFolder")
    private List<Item> items = new ArrayList<>();

    public Folder() {
        type = ItemType.FOLDER;
    }

}
