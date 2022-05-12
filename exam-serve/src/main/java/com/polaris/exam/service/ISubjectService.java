package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.pojo.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.polaris.exam.utils.RespBean;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学科表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface ISubjectService extends IService<Subject> {
    /**
     * 获取学科列表
     * @param page 分页
     * @return RespBean
     */
    RespBean getAllSubjectList(Page<Subject> page);

    /**
     * 根据参数获取学科列表
     * @param page 分页
     * @param level 参数
     * @return RespBean
     */
    RespBean getAllSubjectList(Page<Subject> page,Integer level);

    /**
     * 添加学科
     * @param param 参数
     * @return RespBean
     */
    RespBean create(Subject param);

    /**
     * 删除学科
     * @param subjectId 学科id
     * @return RespBean
     */
    RespBean delete(Integer subjectId);

    /**
     * 更新学科信息
     * @param param 参数
     * @return RespBean
     */
    RespBean update(Integer id, Subject param);

    /**
     * 查询学科
     * @param subjectId 学科id
     * @return RespBean
     */
    RespBean search(Integer subjectId);

    /**
     * 根据学科id获取年级
     * @param subjectId 学科id
     * @return 年级
     */
    Integer levelBySubjectId(Integer subjectId);

    /**
     * 更新学科状态
     * @param id Integer
     * @param status Integer
     * @return Subject
     */
    Subject updateSubjectStatus(Integer id, Integer status);

    /**
     * 获取学科名称map
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> subjectNameList();

    /**
     * 获取学科列表
     * @return List<Subject>
     */
    List<Subject> allSubjectList();

    /**
     * 获取教师所属学科
     * @param teacherId Integer
     * @return List<Subject>
     */
    List<Subject> getTeacherAllSubject(Integer teacherId);
}
