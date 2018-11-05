package com.homejim.mybatis.mapper;

import com.homejim.mybatis.entity.Student;
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
import java.util.*;

/**
 * Created by IntelliJ IDEA
 * User: Homejim
 * Date 2018-9-4
 * Time 20:40
 */
public class StudentMapperTest {
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
    public void selectByStudent() {
        SqlSession sqlSession = null;
        sqlSession = sqlSessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        Student search = new Student();
        search.setName("明");

        System.out.println("只有名字时的查询");
        List<Student> studentsByName = studentMapper.selectByStudentSelective(search);
        for (int i = 0; i < studentsByName.size(); i++) {
            System.out.println(ToStringBuilder.reflectionToString(studentsByName.get(i), ToStringStyle.MULTI_LINE_STYLE));
        }

        search.setName(null);
        search.setSex((byte) 1);
        System.out.println("只有性别时的查询");
        List<Student> studentsBySex = studentMapper.selectByStudentSelective(search);
        for (int i = 0; i < studentsBySex.size(); i++) {
            System.out.println(ToStringBuilder.reflectionToString(studentsBySex.get(i), ToStringStyle.MULTI_LINE_STYLE));
        }

        System.out.println("姓名和性别同时存在的查询");
        search.setName("明");
        List<Student> studentsByNameAndSex = studentMapper.selectByStudentSelective(search);
        for (int i = 0; i < studentsByNameAndSex.size(); i++) {
            System.out.println(ToStringBuilder.reflectionToString(studentsByNameAndSex.get(i), ToStringStyle.MULTI_LINE_STYLE));
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void updateByStudentSelective() {
        SqlSession sqlSession = null;
        sqlSession = sqlSessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        Student student = new Student();
        student.setStudentId(1);
        student.setName("明明");
        student.setPhone("13838438888");
        System.out.println(studentMapper.updateByPrimaryKeySelective(student));

        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void insertByStudentSelective() {
        SqlSession sqlSession = null;
        sqlSession = sqlSessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        Student student = new Student();
        student.setName("小飞机");
        student.setPhone("13838438899");
        student.setEmail("xiaofeiji@qq.com");
        student.setLocked((byte) 0);

        System.out.println(studentMapper.insertSelective(student));

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void selectByIdOrName() {
        SqlSession sqlSession = null;
        sqlSession = sqlSessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        Student student = new Student();
        student.setName("小飞机");
        student.setStudentId(1);

        Student studentById = studentMapper.selectByIdOrName(student);
        System.out.println("有 ID 则根据 Id 获取");
        System.out.println(ToStringBuilder.reflectionToString(studentById, ToStringStyle.MULTI_LINE_STYLE));

        student.setStudentId(null);
        Student studentByName = studentMapper.selectByIdOrName(student);
        System.out.println("没有 ID 则根据 name 获取");
        System.out.println(ToStringBuilder.reflectionToString(studentByName, ToStringStyle.MULTI_LINE_STYLE));

        student.setName(null);
        Student studentNull = studentMapper.selectByIdOrName(student);
        System.out.println("没有 ID 和 name, 返回 null");
        Assert.assertNull(studentNull);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void selectByStudentWhereTag() {
        SqlSession sqlSession = null;
        sqlSession = sqlSessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        Student search = new Student();
        search.setName("明");

        System.out.println("只有名字时的查询");
        List<Student> studentsByName = studentMapper.selectByStudentSelectiveWhereTag(search);
        for (int i = 0; i < studentsByName.size(); i++) {
            System.out.println(ToStringBuilder.reflectionToString(studentsByName.get(i), ToStringStyle.MULTI_LINE_STYLE));
        }

        search.setSex((byte) 1);
        System.out.println("姓名和性别同时存在的查询");
        List<Student> studentsBySex = studentMapper.selectByStudentSelectiveWhereTag(search);
        for (int i = 0; i < studentsBySex.size(); i++) {
            System.out.println(ToStringBuilder.reflectionToString(studentsBySex.get(i), ToStringStyle.MULTI_LINE_STYLE));
        }

        System.out.println("姓名和性别都不存在时查询");
        search.setName(null);
        search.setSex(null);
        List<Student> studentsByNameAndSex = studentMapper.selectByStudentSelectiveWhereTag(search);
        for (int i = 0; i < studentsByNameAndSex.size(); i++) {
            System.out.println(ToStringBuilder.reflectionToString(studentsByNameAndSex.get(i), ToStringStyle.MULTI_LINE_STYLE));
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void selectByStudentIdList() {
        SqlSession sqlSession = null;
        sqlSession = sqlSessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        List<Integer> ids = new LinkedList<>();
        ids.add(1);
        ids.add(3);

        List<Student> students = studentMapper.selectByStudentIdList(ids);
        for (int i = 0; i < students.size(); i++) {
            System.out.println(ToStringBuilder.reflectionToString(students.get(i), ToStringStyle.MULTI_LINE_STYLE));
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void insertList() {
        SqlSession sqlSession = null;
        sqlSession = sqlSessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        List<Student> students = new LinkedList<>();
        Student stu1 = new Student();
        stu1.setName("批量01");
        stu1.setPhone("13888888881");
        stu1.setLocked((byte) 0);
        stu1.setEmail("13888888881@138.com");
        stu1.setSex((byte) 1);
        students.add(stu1);

        Student stu2 = new Student();
        stu2.setName("批量02");
        stu2.setPhone("13888888882");
        stu2.setLocked((byte) 0);
        stu2.setEmail("13888888882@138.com");
        stu2.setSex((byte) 0);
        students.add(stu2);

        System.out.println(studentMapper.insertList(students));
        sqlSession.commit();
        sqlSession.close();
    }
}
