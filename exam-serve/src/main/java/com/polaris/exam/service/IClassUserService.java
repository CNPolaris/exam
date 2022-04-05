package com.polaris.exam.service;

import com.polaris.exam.dto.classes.ClassUserParam;
import com.polaris.exam.pojo.ClassUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 班级-用户关联表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
public interface IClassUserService extends IService<ClassUser> {
    /**
     * 通过班级id获取学生人数
     * @param classId Integer
     * @return int
     */
    int selectStudentCount(Integer classId);

    /**
     * 学生加入班级
     * @param param ClassUserParam
     * @return ClassUser
     */
    ClassUser insertClassUser(ClassUserParam param);

    /**
     * 根据班级id获取学生id列表
     * @param classId Integer
     * @return List<Integer>
     */
    List<Integer> selectStudentIdByClassId(Integer classId);

    /**
     * 删除学生班级关联
     * @param id Integer
     */
    void deleteClassUser(Integer id);

    /***
     * 当删除班级时 清除关联
     * @param classId Integer
     */
    void deleteClassUserByClass(Integer classId);
}
