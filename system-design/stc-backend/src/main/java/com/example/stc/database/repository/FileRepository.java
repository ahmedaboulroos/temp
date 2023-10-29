package com.example.stc.database.repository;

import com.example.stc.database.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByContainingFolderId(Long folderId);

}
