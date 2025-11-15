package com.khahnm04.ecommerce.modules.product;

import com.khahnm04.ecommerce.shared.common.enums.ProductStatusEnum;
import com.khahnm04.ecommerce.modules.banner.domain.entity.BannerDetail;
import com.khahnm04.ecommerce.modules.brand.domain.entity.Brand;
import com.khahnm04.ecommerce.modules.category.domain.entity.CategoryProduct;
import com.khahnm04.ecommerce.modules.news.domain.entity.NewsDetail;
import com.khahnm04.ecommerce.shared.entity.BaseEntity;
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
@Table(name = "products")
public class Product extends BaseEntity<Long> {

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "old_price", nullable = false)
    private Long oldPrice;

    @Column(name = "image", nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatusEnum status = ProductStatusEnum.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<NewsDetail> newsDetails;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<BannerDetail> bannerDetails;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVariant> productVariants;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductFaq> productFaqs;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductQuestion> productQuestions;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductAttributeValue> productAttributeValues;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<CategoryProduct> categoryProducts;

}
