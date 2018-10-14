package com.homejim.mybatis.entity;

public class BlogCustom extends Blog{
   private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}