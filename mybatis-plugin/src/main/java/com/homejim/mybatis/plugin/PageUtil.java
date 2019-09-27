package com.homejim.mybatis.plugin;

/**
 * @author homejim
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

}
