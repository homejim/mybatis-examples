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

    /**
     * 根据输入的学生信息进行条件检索
     * 1. 当只输入用户名时， 使用用户名进行模糊检索；
     * 2. 当只输入邮箱时， 使用性别进行完全匹配
     * 3. 当用户名和性别都存在时， 用这两个条件进行查询匹配的用
     */
    List<Student> selectByStudentSelective(Student student);

    /**
     * 更新非空属性
     */
    int updateByPrimaryKeySelective(Student record);

    /**
     * 非空字段才进行插入
     */
    int insertSelective(Student record);

    /**
     * - 当 studen_id 有值时， 使用 studen_id 进行查询；
     * - 当 studen_id 没有值时， 使用 name 进行查询；
     * - 否则返回空
     */
    Student selectByIdOrName(Student record);

    /**
     * 根据输入的学生信息进行条件检索
     * 1. 当只输入用户名时， 使用用户名进行模糊检索；
     * 2. 当只输入邮箱时， 使用性别进行完全匹配
     * 3. 当用户名和性别都存在时， 用这两个条件进行查询匹配的用
     */
    List<Student> selectByStudentSelectiveWhereTag(Student student);


    /**
     * 获取 id 集合中的用户信息
     * @param ids
     * @return
     */
    List<Student> selectByStudentIdList(@Param("ids") List<Integer> ids);

    /**
     * 批量插入学生
     */
    int insertList(List<Student> students);
}