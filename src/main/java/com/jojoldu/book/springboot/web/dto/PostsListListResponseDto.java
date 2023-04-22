package com.jojoldu.book.springboot.web.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class PostsListListResponseDto {

    private List<PostsListResponseDto> postsListResponseDtos;

    public PostsListListResponseDto(List<PostsListResponseDto> postsListResponseDtos) {
        this.postsListResponseDtos = postsListResponseDtos;
    }
}
