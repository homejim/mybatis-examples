package com.homejim.mybatis.mapper;

import com.homejim.mybatis.entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Homejim
 * Date 2018-9-4
 * Time 20:40
 */
public class MybatisCacheTest {
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
    public void oneSqlSession() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            // 执行第一次查询
            List<Student> students = studentMapper.selectAll();
            for (int i = 0; i < students.size(); i++) {
                System.out.println(students.get(i));
            }
            studentMapper.updateByPrimaryKey(students.get(0));
            System.out.println("=============开始同一个 Sqlsession 的第二次查询============");
            // 同一个 sqlSession 进行第二次查询

            List<Student> stus = studentMapper.selectAll();
            Assert.assertEquals(students, stus);
            for (int i = 0; i < stus.size(); i++) {
                System.out.println("stus:" + stus.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void differSqlSession() {
        SqlSession sqlSession = null;
        SqlSession sqlSession2 = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            // 执行第一次查询
            List<Student> students = studentMapper.selectAll();
            for (int i = 0; i < students.size(); i++) {
                System.out.println(students.get(i));
            }
            System.out.println("=============开始不同 Sqlsession 的第二次查询============");
            // 从新创建一个 sqlSession2 进行第二次查询
            sqlSession2 = sqlSessionFactory.openSession();
            StudentMapper studentMapper2 = sqlSession2.getMapper(StudentMapper.class);
            List<Student> stus = studentMapper2.selectAll();
            Assert.assertNotEquals(students, stus);
            for (int i = 0; i < stus.size(); i++) {
                System.out.println("stus:" + stus.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
            if (sqlSession2 != null) {
                sqlSession2.close();
            }
        }
    }

    @Test
    public void sameSqlSessionNoCache() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            // 执行第一次查询
            Student student = studentMapper.selectByPrimaryKey(1);
            List<Student> students = studentMapper.selectAll();
            System.out.println("=============开始同一个 Sqlsession 的第二次查询============");
            // 同一个 sqlSession 进行第二次查询
            Student stu = studentMapper.selectByPrimaryKey(1);
            List<Student> stus = studentMapper.selectAll();
            Assert.assertNotEquals(student, stu);
            Assert.assertNotEquals(students, stus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void secendLevelCacheTest() {

        // 获取 SqlSession　对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //  获取 Mapper 对象
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        // 使用 Mapper 接口的对应方法，查询 id=2 的对象
        Student student = studentMapper.selectByPrimaryKey(2);
        // 更新对象的名称
        student.setName("奶茶");
        // 再次使用相同的 SqlSession 查询id=2 的对象
        Student student1 = studentMapper.selectByPrimaryKey(2);
   /*     Assert.assertEquals("奶茶", student1.getName());
        // 同一个 SqlSession 使用缓存， 则得到的对象都一样的
        Assert.assertEquals(student, student1);*/

        sqlSession.close();

        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        StudentMapper studentMapper1 = sqlSession1.getMapper(StudentMapper.class);
        Student student2 = studentMapper1.selectByPrimaryKey(2);
        Student student3 = studentMapper1.selectByPrimaryKey(2);
        Assert.assertEquals("奶茶", student2.getName());
        Assert.assertNotEquals(student3, student2);

        sqlSession1.close();
    }

}
