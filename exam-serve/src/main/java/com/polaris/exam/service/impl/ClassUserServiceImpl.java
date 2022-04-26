package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.analysis.StatisticsRequest;
import com.polaris.exam.dto.classes.ClassUserParam;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.pojo.ClassUser;
import com.polaris.exam.mapper.ClassUserMapper;
import com.polaris.exam.service.IClassService;
import com.polaris.exam.service.IClassUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 班级-用户关联表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
@Service
public class ClassUserServiceImpl extends ServiceImpl<ClassUserMapper, ClassUser> implements IClassUserService {

    private final ClassUserMapper classUserMapper;
    private final IClassService classService;

    public ClassUserServiceImpl(ClassUserMapper classUserMapper, IClassService classService) {
        this.classUserMapper = classUserMapper;
        this.classService = classService;
    }

    /**
     * 通过班级id获取学生人数
     *
     * @param classId Integer
     * @return int
     */
    @Override
    public int selectStudentCount(Integer classId) {
        return classUserMapper.selectCount(new QueryWrapper<ClassUser>().eq("class_id",classId));
    }

    /**
     * 学生加入班级
     *
     * @param param ClassUserParam
     * @return ClassUser
     */
    @Override
    public ClassUser insertClassUser(ClassUserParam param) {
        ClassUser classUser = new ClassUser();
        classUser.setClassId(param.getClassId());
        classUser.setUserId(param.getUserId());
        save(classUser);
        classService.increaseStudentCount(classUser.getClassId());
        return classUser;
    }

    @Override
    public void deleteClassUser(Integer id) {
        ClassUser classUser = classUserMapper.selectById(id);
        classUser.setStatus(StatusEnum.NO.getCode());
        classUserMapper.updateById(classUser);
        classService.decreaseStudentCount(classUser.getClassId());
    }

    /**
     * 根据班级id获取学生id列表
     *
     * @param classId Integer
     * @return List<Integer>
     */
    @Override
    public List<Integer> selectStudentIdByClassId(Integer classId) {
        List<Integer> studentIds = new ArrayList<>();
        List<ClassUser> classUserList = classUserMapper.selectList(new QueryWrapper<ClassUser>().eq("class_id", classId).eq("status",StatusEnum.OK.getCode()));
        classUserList.forEach(c->{
            studentIds.add(c.getUserId());
        });
        return studentIds;
    }

    @Override
    public void deleteClassUserByClass(Integer classId) {
        QueryWrapper<ClassUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", classId);
        classUserMapper.delete(queryWrapper);
    }

    @Override
    public Page<ClassUser> getStudentIdsByClass(StatisticsRequest model) {
        Page<ClassUser> page = new Page<>(model.getPage(), model.getLimit());
        QueryWrapper<ClassUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", model.getClassId());
        return classUserMapper.selectPage(page, queryWrapper);
    }
}
