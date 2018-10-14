package com.mybatis.homejim.mapper;

import com.homejim.mybatis.entity.BlogBO;
import com.homejim.mybatis.entity.BlogCustom;
import com.homejim.mybatis.mapper.BlogMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class BlogMapperTest {
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

    /**
     *  resultType 方式一测试
     */
    @Test
    public void testSelectBoById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        BlogBO blogBO = blogMapper.selectBoById(1);
        System.out.println(ToStringBuilder.reflectionToString(blogBO, ToStringStyle.MULTI_LINE_STYLE));
    }
    /**
     *  resultType 方式二测试
     */
    @Test
    public void testSelectCustomById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        BlogCustom blogCustom = blogMapper.selectCutomById(1);
        System.out.println(ToStringBuilder.reflectionToString(blogCustom, ToStringStyle.MULTI_LINE_STYLE));
    }

    /**
     *  resultMap 方式测试
     */
    @Test
    public void testSelectCustomByIdMap() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        BlogCustom blogCustom = blogMapper.selectCutomByIdMap(1);
        System.out.println(ToStringBuilder.reflectionToString(blogCustom, ToStringStyle.MULTI_LINE_STYLE));
    }

    /**
     *  resultMap + association 方式测试
     */
    @Test
    public void testSelectCustomByIdAMap() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        BlogCustom blogCustom = blogMapper.selectCutomByIdAMap(1);
        System.out.println(ToStringBuilder.reflectionToString(blogCustom, ToStringStyle.MULTI_LINE_STYLE));
    }

    /**
     *  resultMap + association 嵌套查询方式测试
     */
    @Test
    public void testSelectBlogAndAuthorByIdSelect() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        BlogCustom blogCustom = blogMapper.selectBlogAndAuthorByIdSelect(1);

        Assert.assertNotNull(blogCustom);
        System.out.println("开始使用author对象");
        Assert.assertNotNull(blogCustom.getAuthor());
        System.out.println(ToStringBuilder.reflectionToString(blogCustom, ToStringStyle.MULTI_LINE_STYLE));

    }

    /**
     *  resultMap + association 嵌套查询方式测试(延迟加载）
     */
    @Test
    public void testSelectBlogAndAuthorByIdSelectLazy() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        BlogCustom blogCustom = blogMapper.selectBlogAndAuthorByIdSelect(1);

        Assert.assertNotNull(blogCustom);
        System.out.println("开始使用author对象");
        Assert.assertNotNull(blogCustom.getAuthor());

    }
    /**
     *  resultMap + association 嵌套查询方式测试(延迟加载不延迟lazyLoadTriggerMethods）
     */
    @Test
    public void testSelectBlogAndAuthorByIdSelectTrigger() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        BlogCustom blogCustom = blogMapper.selectBlogAndAuthorByIdSelect(1);
        blogCustom.equals(null);
        Assert.assertNotNull(blogCustom);
        sqlSession.close();
        System.out.println("开始使用author对象");
        Assert.assertNotNull(blogCustom.getAuthor());

    }
}
