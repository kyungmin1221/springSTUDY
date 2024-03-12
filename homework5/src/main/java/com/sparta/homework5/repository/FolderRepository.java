package com.sparta.homework5.repository;

import com.sparta.homework5.domain.FolderEntity;
import com.sparta.homework5.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity , Long> {
    List<FolderEntity> findAllByUserAndFolderNameIn(UserEntity user, List<String> folderNames);

}
