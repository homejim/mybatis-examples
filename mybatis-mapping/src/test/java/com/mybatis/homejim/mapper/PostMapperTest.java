package com.mybatis.homejim.mapper;

import com.homejim.mybatis.entity.Post;
import com.homejim.mybatis.mapper.PostMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class PostMapperTest {
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSelectListByBlogId(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
        List<Post> posts = postMapper.selectPostByBlogId(1);
        for (int i = 0; i < posts.size(); i++) {
            System.out.println(posts.get(i).getContent());
        }
    }
}
