package com.khahnm04.ecommerce.util;

import org.springframework.data.domain.Sort;

import java.util.List;

public class SortUtils {

    public static Sort parseSort(List<String> sorts) {
        if (sorts == null || sorts.isEmpty()) {
            return Sort.unsorted();
        }

        Sort combinedSort = Sort.unsorted();
        for (String sortStr : sorts) {
            if (sortStr.contains(",")) {
                String[] parts = sortStr.split(",");
                String property = parts[0].trim();
                Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC : Sort.Direction.ASC;
                combinedSort = combinedSort.and(Sort.by(direction, property));
            } else {
                combinedSort = combinedSort.and(Sort.by(Sort.Direction.ASC, sortStr.trim()));
            }
        }
        return combinedSort;
    }

}
