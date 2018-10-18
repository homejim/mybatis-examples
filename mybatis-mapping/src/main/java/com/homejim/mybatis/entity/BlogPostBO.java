package com.homejim.mybatis.entity;

import java.util.List;

public class BlogPostBO extends Blog {

    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
