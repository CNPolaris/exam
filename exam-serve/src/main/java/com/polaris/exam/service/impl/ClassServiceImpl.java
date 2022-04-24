package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.classes.ClassRequest;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.mapper.ClassUserMapper;
import com.polaris.exam.mapper.ExamPaperMapper;
import com.polaris.exam.pojo.Class;
import com.polaris.exam.mapper.ClassMapper;
import com.polaris.exam.pojo.ClassUser;
import com.polaris.exam.service.IClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.utils.CreateUuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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
    private final ClassUserMapper classUserMapper;
    private final ExamPaperMapper examPaperMapper;
    @Autowired
    public ClassServiceImpl(ClassMapper classMapper, ClassUserMapper classUserMapper, ExamPaperMapper examPaperMapper) {
        this.classMapper = classMapper;
        this.classUserMapper = classUserMapper;
        this.examPaperMapper = examPaperMapper;
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

    @Override
    public List<Integer> getClassIdsByTeacherId(Integer tId) {
        return classMapper.getClassIdsByTeacherId(tId);
    }

    @Override
    public Page<Class> getClassByTeacherId(Page<Class> page, Integer tId) {
        List<Integer> classIds = classMapper.getClassIdsByTeacherId(tId);
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",classIds);
        return classMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<Integer> getClassIdsByExamId(Integer paperId) {
        return classMapper.getClassIdsByExamId(paperId);
    }

    @Override
    public Integer getStudentCountByClassIds(List<Integer> classIds) {
        QueryWrapper<ClassUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("class_id",classIds);
        return classUserMapper.selectCount(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> getClassUserPie(List<Integer> classIds) {
        List<Map<String, Object>> classList = new ArrayList<>(classIds.size());
        classIds.forEach(c->{
            HashMap<String, Object> map = new HashMap<>(2);
            map.put("name", getById(c).getClassName());
            map.put("value",classMapper.getStudentCountByClassId(c));
            classList.add(map);
        });
        return classList;
    }

    @Override
    public List<Map<String, Object>> getClassPaperPie(List<Integer> classIds) {
        List<Map<String, Object>> classList = new ArrayList<>(classIds.size());
        classIds.forEach(c->{
            HashMap<String, Object> map = new HashMap<>(2);
            map.put("name", getById(c).getClassName());
            map.put("value",examPaperMapper.getExamPaperCount(c));
            classList.add(map);
        });
        return classList;
    }

    @Override
    public List<Class> getClassListByPaperId(Integer paperId) {
        List<Integer> classIds = examPaperMapper.getClassIdsByPaperId(paperId);
        return classMapper.selectList(new QueryWrapper<Class>().in("id",classIds));
    }
}
