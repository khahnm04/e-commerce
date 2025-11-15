package com.khahnm04.ecommerce.modules.news.application.mapper;

import com.khahnm04.ecommerce.modules.news.request.NewsRequest;
import com.khahnm04.ecommerce.modules.news.response.NewsResponse;
import com.khahnm04.ecommerce.modules.news.domain.entity.News;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    News fromNewsRequestToNews(NewsRequest request);

    @Mapping(target = "image", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNews(@MappingTarget News news, NewsRequest request);

    NewsResponse toNewsResponse(News news);

}
