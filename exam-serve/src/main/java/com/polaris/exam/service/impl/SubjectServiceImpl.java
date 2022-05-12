package com.polaris.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.pojo.Subject;
import com.polaris.exam.mapper.SubjectMapper;
import com.polaris.exam.service.ISubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学科表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {
    private final SubjectMapper subjectMapper;

    @Autowired
    public SubjectServiceImpl(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    /**
     * 获取学科列表
     *
     * @param page 分页
     * @return RespBean
     */
    @Override
    public RespBean getAllSubjectList(Page<Subject> page) {
        HashMap<String, Object> returnMap = new HashMap<>();
        IPage<Subject> data = subjectMapper.selectPage(page, null);
        returnMap.put("total",data.getTotal());
        returnMap.put("data",data.getRecords());
        return RespBean.success("查询成功",returnMap);
    }

    /**
     * 根据参数获取学科列表
     *
     * @param page  分页
     * @param level 参数
     * @return RespBean
     */
    @Override
    public RespBean getAllSubjectList(Page<Subject> page, Integer level) {
        HashMap<String, Object> returnMap = new HashMap<>();
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        if(level!=null){
            queryWrapper.eq("level",level);
        }
        IPage<Subject> data = subjectMapper.selectPage(page,queryWrapper);
        returnMap.put("total",data.getTotal());
        returnMap.put("data",data.getRecords());
        return RespBean.success("查询成功",returnMap);
    }

    /**
     * 添加学科
     *
     * @param param 参数
     * @return RespBean
     */
    @Override
    public RespBean create(Subject param) {
        try{
            Subject subject = new Subject();
            BeanUtil.copyProperties(param,subject);
            QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",param.getName()).eq("level",param.getLevel());
            if(subjectMapper.selectCount(queryWrapper)>0){
                return RespBean.error("该学科已经存在");
            }
            subject.setStatus(StatusEnum.OK.getCode());
            subjectMapper.insert(subject);
            return RespBean.success("添加学科成功",subject);
        }catch (Exception e){
            return RespBean.error("添加学科失败",e);
        }
    }

    /**
     * 删除学科
     *
     * @param subjectId 学科id
     * @return RespBean
     */
    @Override
    public RespBean delete(Integer subjectId) {
        try{
            subjectMapper.deleteById(subjectId);
            return RespBean.success("删除学科信息成功");
        }catch (Exception e){
            return RespBean.error("删除学科失败");
        }
    }

    /**
     * 更新学科信息
     *
     * @param param 参数
     * @return RespBean
     */
    @Override
    public RespBean update(Integer id, Subject param) {
        try{
            Subject subject = subjectMapper.selectById(id);
            if(subject==null){
                return RespBean.error("学科不存在");
            }
            subject.setName(param.getName());
            subject.setLevel(param.getLevel());
            subject.setItemOrder(param.getItemOrder());
            subjectMapper.updateById(subject);
            return RespBean.success("更新成功",subject);
        }catch (Exception e){
            return RespBean.error("更新失败");
        }
    }

    /**
     * 查询学科
     *
     * @param subjectId 学科id
     * @return RespBean
     */
    @Override
    public RespBean search(Integer subjectId) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        Subject subject = subjectMapper.selectOne(queryWrapper.eq("id", subjectId));
        return RespBean.success("查询成功",subject);
    }

    /**
     * 根据学科id获取年级
     *
     * @param subjectId 学科id
     * @return 年级
     */
    @Override
    public Integer levelBySubjectId(Integer subjectId) {
        return subjectMapper.selectById(subjectId).getLevel();
    }

    @Override
    public Subject updateSubjectStatus(Integer id, Integer status) {
        Subject subject = getById(id);
        subject.setStatus(status);
        updateById(subject);
        return subject;
    }

    @Override
    public List<Map<String, Object>> subjectNameList() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Subject> subjectList = subjectMapper.selectList(new QueryWrapper<>());
        subjectList.forEach(subject -> {
            Map<String, Object> map = new HashMap<>();
            map.put("label",subject.getName());
            map.put("value",subject.getId());
            list.add(map);
        });
        return list;
    }

    /**
     * 获取学科列表
     *
     * @return List<Subject>
     */
    @Override
    public List<Subject> allSubjectList() {
        return subjectMapper.selectList(new QueryWrapper<>());
    }
}
