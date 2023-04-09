package com.team.puddy.domain.image.domain;

import com.team.puddy.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Image extends BaseTimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath; //파일 S3 경로

    private String originalName;

    private String storedName;

    public void updateImage(String imagePath, String originalName) {
        this.imagePath = imagePath;
        this.originalName = originalName;
    }


}
