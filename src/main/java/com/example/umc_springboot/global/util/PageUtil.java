package com.example.umc_springboot.global.util;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageUtil {

    public Sort parseSort(String sort){
        if (sort == null || sort.isBlank()) {
            // 기본 정렬
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        String[] tokens = sort.split(",");
        String property = tokens[0];

        Sort.Direction direction =
                (tokens.length > 1 && tokens[1].equalsIgnoreCase("asc"))
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        return Sort.by(direction, property);
    }

}
