package com.scalpnote.domain.diary.domain;

import com.scalpnote.domain.common.BaseEntity;
import com.scalpnote.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "Diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "status = 'ACTIVE'")
public class Diary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "diary_image_url")
    private String diaryImageUrl;


    @Builder
    public Diary(User writer, String title, String content, String diaryImageUrl) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.diaryImageUrl = diaryImageUrl;
    }
}
