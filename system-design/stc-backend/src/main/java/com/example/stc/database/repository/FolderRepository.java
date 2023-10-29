package com.example.stc.database.repository;

import com.example.stc.database.entity.Folder;
import com.example.stc.database.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findAllByType(ItemType type);

    List<Folder> findAllByTypeAndContainingFolderId(ItemType type, Long parentId);

}
