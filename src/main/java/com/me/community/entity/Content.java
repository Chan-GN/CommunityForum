package com.me.community.entity;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
public class Content {
    private String article;
    private String code;
}
