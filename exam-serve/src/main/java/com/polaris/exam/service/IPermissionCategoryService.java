package com.polaris.exam.service;

import com.polaris.exam.pojo.PermissionCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-26
 */
public interface IPermissionCategoryService extends IService<PermissionCategory> {
    /**
     * 获取全部权限分类目录
     * @return List<PermissionCategory>
     */
    List<PermissionCategory> getAllPermissionCategory();

    /**
     * 更新权限目录
     * @param id Integer
     * @param permissionCategory PermissionCategory
     * @return PermissionCategory
     */
    PermissionCategory updatePermissionCategory(Integer id, PermissionCategory permissionCategory);

    /**
     * 添加权限目录
     * @param model PermissionCategory
     * @return PermissionCategory
     */
    PermissionCategory createPermissionCategory(PermissionCategory model);
}
