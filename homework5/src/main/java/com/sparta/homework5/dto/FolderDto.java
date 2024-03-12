package com.sparta.homework5.dto;

import com.sparta.homework5.domain.FolderEntity;
import lombok.*;

import java.util.List;

public class FolderDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FolderRequestDto {
        List<String> folderNames;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FolderResponseDto {

        private Long id;

        private String folderName;

        public FolderResponseDto(FolderEntity folder) {
            this.id = folder.getId();
            this.folderName = folder.getFolderName();
        }

    }
}
