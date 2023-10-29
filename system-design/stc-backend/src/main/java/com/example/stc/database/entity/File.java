package com.example.stc.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "FILES")
@PrimaryKeyJoinColumn(name = "FILE_ID")
public class File extends Item {

    @Lob
    @Column(name = "BINARY_DATA")
    private byte[] binary;

    public File() {
        type = ItemType.FILE;
    }

    private String contentType;

}
