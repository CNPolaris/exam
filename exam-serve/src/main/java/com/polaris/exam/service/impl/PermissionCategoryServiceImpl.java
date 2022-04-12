package com.polaris.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.polaris.exam.pojo.PermissionCategory;
import com.polaris.exam.mapper.PermissionCategoryMapper;
import com.polaris.exam.service.IPermissionCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-26
 */
@Service
public class PermissionCategoryServiceImpl extends ServiceImpl<PermissionCategoryMapper, PermissionCategory> implements IPermissionCategoryService {
    private final PermissionCategoryMapper permissionCategoryMapper;

    public PermissionCategoryServiceImpl(PermissionCategoryMapper permissionCategoryMapper) {
        this.permissionCategoryMapper = permissionCategoryMapper;
    }

    @Override
    public List<PermissionCategory> getAllPermissionCategory() {
        return permissionCategoryMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public PermissionCategory updatePermissionCategory(Integer id, PermissionCategory permissionCategory) {
        PermissionCategory category = getById(id);
        category.setName(permissionCategory.getName());
        category.setSort(permissionCategory.getSort());
        save(category);
        return category;
    }

    @Override
    public PermissionCategory createPermissionCategory(PermissionCategory model) {
        PermissionCategory category = new PermissionCategory();
        category.setSort(model.getId());
        category.setName(model.getName());
        category.setCreateTime(new Date());
        save(category);
        return category;
    }
}