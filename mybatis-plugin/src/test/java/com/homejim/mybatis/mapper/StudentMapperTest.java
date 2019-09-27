package com.homejim.mybatis.mapper;

import com.homejim.mybatis.entity.Student;
import com.homejim.mybatis.plugin.PageUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void testSelectList() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            PageUtil.setPagingParam(1, 2);
            List<Student> students = sqlSession.selectList("selectAll");
            for (int i = 0; i < students.size(); i++) {
                System.out.println(students.get(i));
            }

            List<Student> students2 = sqlSession.selectList("selectAll");
            for (int i = 0; i < students2.size(); i++) {
                System.out.println(students2.get(i));
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
    public void testSelectBtweenCreatedTimeMap() {

        Map<String, Object> params = new HashMap<>();
        Calendar bTime = Calendar.getInstance();
        // month 是从0~11， 所以9月是8
        bTime.set(2018, Calendar.AUGUST, 29);
        params.put("bTime", bTime.getTime());

        Calendar eTime = Calendar.getInstance();
        eTime.set(2018,Calendar.SEPTEMBER,2);
        params.put("eTime", eTime.getTime());
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            StudentMapper studentMapper = (StudentMapper) sqlSession.getMapper(StudentMapper.class);
            List<Student> students = studentMapper.selectBetweenCreatedTime(params);
            for (int i = 0; i < students.size(); i++) {
                System.out.println(students.get(i));
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
    public void testSelectBtweenCreatedTimeAnno() {

        Map<String, Object> params = new HashMap<>();
        Calendar bTime = Calendar.getInstance();
        // month 是从0~11， 所以9月是8
        bTime.set(2018, Calendar.AUGUST, 29);


        Calendar eTime = Calendar.getInstance();
        eTime.set(2018,Calendar.SEPTEMBER,2);

        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            StudentMapper studentMapper = (StudentMapper) sqlSession.getMapper(StudentMapper.class);
            List<Student> students = studentMapper.selectBetweenCreatedTimeAnno(bTime.getTime(), eTime.getTime());
            for (int i = 0; i < students.size(); i++) {
                System.out.println(students.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

    }
}
