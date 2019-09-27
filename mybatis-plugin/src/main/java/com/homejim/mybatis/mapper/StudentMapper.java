package com.homejim.mybatis.mapper;

import com.homejim.mybatis.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StudentMapper {

    /**
     *
     * @return
     */
    List<Student> selectAll();

    Student selectByPrimaryKey(int id);

    int updateByPrimaryKey(Student student);

    /**
     * 获取一段时间内的用户
     * @param params
     * @return
     */
    List<Student> selectBetweenCreatedTime(Map<String, Object> params);


    /**
     *
     * @param bTime 开始时间
     * @param eTime 结束时间
     * @return
     */
    List<Student> selectBetweenCreatedTimeAnno(@Param("bTime")Date bTime, @Param("eTime")Date eTime);
}