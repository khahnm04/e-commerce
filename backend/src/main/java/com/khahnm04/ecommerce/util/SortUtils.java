package com.khahnm04.ecommerce.util;

import org.springframework.data.domain.Sort;

public class SortUtils {

    public static Sort parseSort(String sort) {
        if (sort == null || sort.trim().isEmpty()) {
            return Sort.unsorted();
        }

        String[] parts = sort.trim().split(",");
        String property = parts[0].trim();

        Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return Sort.by(direction, property);
    }

}
