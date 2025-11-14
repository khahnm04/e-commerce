package com.khahnm04.ecommerce.service.news;

import com.khahnm04.ecommerce.dto.request.news.NewsRequest;
import com.khahnm04.ecommerce.dto.response.news.NewsResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface NewsService {

    NewsResponse createNews(NewsRequest request, MultipartFile file);
    PageResponse<NewsResponse> getAllNews(int page, int size, String sort);
    PageResponse<NewsResponse> getAllDeletedNews(int page, int size, String sort);
    NewsResponse getNewsDetailById(Long id);
    NewsResponse getNewsDetailBySlug(String slug);
    NewsResponse updateNews(Long id, NewsRequest request, MultipartFile file);
    void updateNewsStatus(Long id, String status);
    void softDeleteNews(Long id);
    void deleteNews(Long id);

}
