package com.homejim.mybatis.plugin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Page {
    private int offset;
    private int limit;

}