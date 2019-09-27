package com.homejim.mybatis.plugin;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hejiajin hejiajin@3vjia.com
 * @since 2019-09-27 13:07
 */
public class PageUtil {
    private static final ThreadLocal<Page> LOCAL_PAGE = new ThreadLocal<Page>();

    public static void setPagingParam(int offset, int limit) {
        Page page = new Page(offset, limit);
        LOCAL_PAGE.set(page);
    }

    public static void removePagingParam() {
        LOCAL_PAGE.remove();
    }

    public static Page getPaingParam() {
        return LOCAL_PAGE.get();
    }

    @AllArgsConstructor
    @Getter
    static class Page {
        private int offset;
        private int limit;

    }
}