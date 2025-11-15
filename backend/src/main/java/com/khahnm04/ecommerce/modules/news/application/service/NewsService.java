package com.khahnm04.ecommerce.modules.news.application.service;

import com.khahnm04.ecommerce.modules.news.request.NewsRequest;
import com.khahnm04.ecommerce.modules.news.response.NewsResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface NewsService {

    NewsResponse createNews(NewsRequest request);
    PageResponse<NewsResponse> getAllNews(int page, int size, String sort);
    PageResponse<NewsResponse> getAllDeletedNews(int page, int size, String sort);
    NewsResponse getNewsDetailById(Long id);
    NewsResponse getNewsDetailBySlug(String slug);
    NewsResponse updateNews(Long id, NewsRequest request, MultipartFile file);
    void updateNewsStatus(Long id, String status);
    void softDeleteNews(Long id);
    void deleteNews(Long id);
    void restoreNews(Long id);

}
