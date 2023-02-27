package com.hansung.hansungcommunity.controller;

import com.me.community.controller.ArticleApiController;
import com.me.community.dto.ArticleDto;
import com.me.community.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleApiControllerTest {
    @InjectMocks
    private ArticleApiController articleApiController;

    @Mock
    private ArticleService articleService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleApiController).build();
    }

    @Test
    void postSuccess() throws Exception {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(1L);
        articleDto.setTitle("Test");

        when(articleService.post(any(Long.class), any(ArticleDto.class))).thenReturn(articleDto);
    }
}