package com.khahnm04.ecommerce.entity.news;

import com.khahnm04.ecommerce.common.enums.NewsStatusEnum;
import com.khahnm04.ecommerce.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news")
public class News extends BaseEntity<Long> {

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("'PENDING'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NewsStatusEnum status;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    private List<NewsDetail> newsDetails;

}
