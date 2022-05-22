package com.polaris.exam.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.category.CategoryRequest;
import com.polaris.exam.pojo.PermissionCategory;
import com.polaris.exam.service.IPermissionCategoryService;
import com.polaris.exam.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-01-26
 */
@Api(tags = "管理员端权限目录管理模块")
@RestController("AdminCategoryController")
@RequestMapping("/api/admin/category")
public class PermissionCategoryController {
    private final IPermissionCategoryService permissionCategoryService;

    public PermissionCategoryController(IPermissionCategoryService permissionCategoryService) {
        this.permissionCategoryService = permissionCategoryService;
    }

    @ApiOperation(value = "获取资源类别")
    @GetMapping("/list")
    public RespBean getAllCategoryList(){
        return RespBean.success("成功",permissionCategoryService.getAllPermissionCategory());
    }

    @ApiOperation("分页获取资源类别")
    @PostMapping("/page")
    public RespBean getCategoryPage(@RequestBody CategoryRequest model){
        Map<String, Object> response = new HashMap<>(2);
        Page<PermissionCategory> page = permissionCategoryService.getPermissionCategoryPage(model);
        response.put("total", page.getTotal());
        response.put("list", page.getRecords());
        return RespBean.success(response);
    }

    @ApiOperation(value = "添加权限目录")
    @PostMapping("/create")
    public RespBean createCategory(@RequestBody PermissionCategory permissionCategory){
        return RespBean.success("更新权限目录成功",permissionCategoryService.createPermissionCategory(permissionCategory));
    }

    @ApiOperation(value = "更新权限目录")
    @PostMapping("/update/{id}")
    public RespBean updateCategory(@PathVariable Integer id, @RequestBody PermissionCategory permissionCategory){
        return RespBean.success("更新权限目录成功",permissionCategoryService.updatePermissionCategory(id,permissionCategory));
    }

    @ApiOperation(value = "权限目录删除")
    @GetMapping("/delete/{id}")
    public RespBean deleteCategory(@PathVariable Integer id){
        permissionCategoryService.removeById(id);
        return RespBean.success("删除成功");
    }

}
