package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.user.UpdateUserInfo;
import com.polaris.exam.dto.user.UploadUserParam;
import com.polaris.exam.pojo.Permission;
import com.polaris.exam.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.polaris.exam.utils.RespBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
public interface IUserService extends IService<User> {
    /**
     * 获取全部用户信息
     * @param page 分页
     * @return RespBean
     */
    RespBean getAllUserList(Page<User> page);

    /**
     * 获取学生人数
     * @return int
     */
    int selectStudentCount();
    /**
     * 根据参数获取用户列表
     * @param page 分页
     * @param param 参数
     * @return RespBean
     */
    RespBean getAllUserList(Page<User> page, User param);
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return User
     */
    User getUserByUsername(String username);

    /**
     * 通过id获取用户名
     * @param id Integer
     * @return String
     */
    String getUsernameById(Integer id);
    /**
     * 根据用户名查询用户详情
     * @param username 用户名
     * @return RespBean
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 根据角色id查询权限列表
     * @param roleId 角色Id
     * @return List
     */
    List<Permission> getPermissionList(Integer roleId);

    /**
     * 插入user
     * @param user User
     * @return int
     */
    int insert(User user);

    /**
     * 通过id删除用户
     * @param id Integer
     * @return Integer
     */
    Integer deleteById(Integer id);

    /**
     * 通过id选择用户
     * @param id Integer
     * @return User
     */
    User selectById(Integer id);

    /**
     * 根据id修改用户类型为教师
     * @param id Integer
     * @return User
     */
    User addTeacher(Integer id);

    /**
     * 移除教师
     * @param id Integer
     * @return User
     */
    User deleteTeacher(Integer id);

    /**
     * 账号禁用
     * @param id Integer
     * @return User
     */
    User disabledUserById(Integer id);

    /**
     * 账号解禁
     * @param id Integer
     * @return User
     */
    User ableUserById(Integer id);

    /**
     * 通过ids查询用户list
     * @param ids List<Integer>
     * @return List<User>
     */
    List<User> selectByIds(List<Integer>ids);

    /**
     * 更新用户信息
     * @param username String
     * @param info UpdateUserInfo
     * @return User
     */
    User updateUserInfo(String username, UpdateUserInfo info);

    /**
     * 更新用户状态
     * @param id Integer
     * @param status Integer
     * @return User
     */
    User updateUserStatus(Integer id, Integer status);

    /**
     * 获取学生列表
     * @param page Page<User>
     * @param username String
     * @return Page<User>
     */
    Page<User> getStudentList(Page<User> page, String username);

    /**
     * 获取教师列表
     * @param page Page<User>
     * @param username String
     * @return Page<User>
     */
    Page<User> getTeacherList(Page<User> page, String username);

    /**
     * 获取管理员列表
     * @param page Page<User>
     * @param username String
     * @return Page<User>
     */
    Page<User> getAdminList(Page<User> page, String username);

    /**
     * 通过用户名模糊搜索用户
     * @param username String
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> selectUserByUsername(String username);

    /**
     * 批量上传用户
     * @param form
     */
    void uploadUser(List<UploadUserParam> form);
}
