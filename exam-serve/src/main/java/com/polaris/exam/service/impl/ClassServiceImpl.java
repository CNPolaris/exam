package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.classes.ClassRequest;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.pojo.Class;
import com.polaris.exam.mapper.ClassMapper;
import com.polaris.exam.service.IClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.utils.CreateUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-17
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {

    private final ClassMapper classMapper;
    @Autowired
    public ClassServiceImpl(ClassMapper classMapper) {
        this.classMapper = classMapper;
    }

    /**
     * 获取班级总数
     *
     * @return int
     */
    @Override
    public int selectClassCount() {
        return classMapper.selectCount(new QueryWrapper<Class>());
    }

    /**
     * 获取班级列表
     *
     * @param page  Page<Class>
     * @param name String
     * @return Page<Class>
     */
    @Override
    public Page<Class> classPage(Page<Class> page, String name) {
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        if(!name.isEmpty()){
            queryWrapper.eq("class_name",name);
        }
        return classMapper.selectPage(page,queryWrapper);
    }

    /**
     * 通过id查找
     *
     * @param classId Integer
     * @return Class
     */
    @Override
    public Class selectById(Integer classId) {
        return classMapper.selectById(classId);
    }

    /**
     * 通过id删除
     *
     * @param classId Integer
     */
    @Override
    public void deleteById(Integer classId) {
        Class aClass = classMapper.selectById(classId);
        aClass.setStatus(StatusEnum.NO.getCode());
        updateById(aClass);
    }

    /**
     * 增加学生人数
     *
     * @param classId Integer
     */
    @Override
    public void increaseStudentCount(Integer classId) {
        Class aClass = classMapper.selectById(classId);
        int value = aClass.getClassCount().intValue();
        aClass.setClassCount(value++);
        updateById(aClass);
    }

    /**
     * 减少学生人数
     *
     * @param classId Integer
     */
    @Override
    public void decreaseStudentCount(Integer classId) {
        Class aClass = classMapper.selectById(classId);
        int value = aClass.getClassCount().intValue();
        aClass.setClassCount(value--);
        updateById(aClass);
    }

    @Override
    public Class editClass(ClassRequest model) {
        if(model.getId()!=null){
            Class aClass = selectById(model.getId());
            aClass.setClassName(model.getClassName());
            save(aClass);
            return aClass;
        }
        Class aClass = new Class();
        aClass.setClassName(model.getClassName());
        aClass.setClassCount(0);
        aClass.setStatus(StatusEnum.OK.getCode());
        aClass.setClassCode(CreateUuid.createUuid());
        aClass.setCreateTime(new Date());
        save(aClass);
        return aClass;
    }

    @Override
    public Class getClassByUserId(Integer userId) {
        return classMapper.getClassByUserId(userId);
    }

    @Override
    public List<Class> getClassByTeacherId(Integer tId) {
        return classMapper.getClassByTeacherId(tId);
    }
}
