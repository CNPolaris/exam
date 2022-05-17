package com.polaris.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.bo.AdminUserDetails;
import com.polaris.exam.dto.user.UpdateUserInfo;
import com.polaris.exam.dto.user.UploadUserParam;
import com.polaris.exam.enums.StatusEnum;
import com.polaris.exam.enums.UserTypeEnum;
import com.polaris.exam.mapper.PermissionMapper;
import com.polaris.exam.mapper.RolePermissionMapper;
import com.polaris.exam.pojo.ClassUser;
import com.polaris.exam.pojo.Permission;
import com.polaris.exam.pojo.RolePermission;
import com.polaris.exam.pojo.User;
import com.polaris.exam.mapper.UserMapper;
import com.polaris.exam.service.AdminCacheService;
import com.polaris.exam.service.IClassUserService;
import com.polaris.exam.service.IRoleService;
import com.polaris.exam.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.polaris.exam.utils.CreateUuid;
import com.polaris.exam.utils.RespBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final UserMapper userMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final PermissionMapper permissionMapper;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;
    private final AdminCacheService cacheService;
    private final IClassUserService classUserService;
    public UserServiceImpl(UserMapper userMapper, RolePermissionMapper rolePermissionMapper, PermissionMapper permissionMapper, PasswordEncoder passwordEncoder, IRoleService roleService, AdminCacheService cacheService, IClassUserService classUserService) {
        this.userMapper = userMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.permissionMapper = permissionMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.cacheService = cacheService;
        this.classUserService = classUserService;
    }

    /**
     * 获取全部用户信息
     * @return RespBean
     */
    @Override
    public RespBean getAllUserList(Page<User>page) {
        Map<String,Object> returnMap = new HashMap<>();
        IPage<User> data = userMapper.selectPage(page,null);
        returnMap.put("total",data.getTotal());
        returnMap.put("data", data.getRecords());
        return RespBean.success("查询成功",returnMap);
    }

    /**
     * 获取学生人数
     *
     * @return int
     */
    @Override
    public int selectStudentCount() {
        return userMapper.selectCount(new QueryWrapper<User>().eq("role_id",UserTypeEnum.Student.getCode()));
    }

    /**
     * 根据参数获取用户列表
     *
     * @param page  分页
     * @param param 参数
     * @return RespBean
     */
    @Override
    public RespBean getAllUserList(Page<User> page, User param) {
        HashMap<String, Object> returnMap = new HashMap<>();
        Map<String, Object> beanToMap = BeanUtil.beanToMap(param, true, false);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(beanToMap,false);
        IPage<User> data = userMapper.selectPage(page,queryWrapper);
        returnMap.put("total",data.getTotal());
        returnMap.put("data",data.getRecords());
        return RespBean.success("查询成功",returnMap);
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return User
     */
    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_name",username));
    }

    @Override
    public String getUsernameById(Integer id) {
        return userMapper.selectById(id).getUserName();
    }

    /**
     * 根据角色id查询权限列表
     * @param roleId 角色ID
     * @return List
     */
    @Override
    public List<Permission> getPermissionList(Integer roleId) {
        List<Permission> permissionList = new ArrayList<>();
        if(cacheService.getPermissionListByRole(roleId)!=null){
            permissionList=cacheService.getPermissionListByRole(roleId);
        }else{
            List<Integer> permissionIdList = new ArrayList<>();
            QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
            List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(queryWrapper.eq("role_id", roleId));
            for( RolePermission e: rolePermissionList){
                permissionIdList.add(e.getPermissionId());
            }
            for(Integer id: permissionIdList){
                permissionList.add(permissionMapper.selectOne(new QueryWrapper<Permission>().eq("id",id)));
            }
            if(!permissionList.isEmpty()){
                cacheService.setPermissionListByRole(roleId,permissionList);
            }
        }
        return permissionList;
    }

    /**
     * 根据用户名查询用户详情
     * @param username 用户名
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", username));
        if(user!=null){
            List<Permission> permissionList = getPermissionList(user.getRoleId());
            return new AdminUserDetails(user,permissionList);
        }
        throw new UsernameNotFoundException("用户不存在");
    }

    @Override
    public int insert(User user) {
        roleService.increaseCount(user.getRoleId());
        return userMapper.insert(user);
    }

    @Override
    public Integer deleteById(Integer id) {
        roleService.decreaseCount(userMapper.selectById(id).getRoleId());
        User user = userMapper.selectById(id);
        cacheService.delUser(user.getUserName());
        return userMapper.deleteById(id);
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User addTeacher(Integer id) {
        User user = userMapper.selectById(id);
        user.setRoleId(UserTypeEnum.Teacher.getCode());
        cacheService.setUser(user);
        return user;
    }

    @Override
    public User deleteTeacher(Integer id) {
        User user = userMapper.selectById(id);
        user.setRoleId(UserTypeEnum.Student.getCode());
        cacheService.setUser(user);
        return user;
    }

    @Override
    public User disabledUserById(Integer id) {
        User user = userMapper.selectById(id);
        user.setStatus(StatusEnum.NO.getCode());
        cacheService.setUser(user);
        return user;
    }

    @Override
    public User ableUserById(Integer id) {
        User user = userMapper.selectById(id);
        user.setStatus(StatusEnum.OK.getCode());
        cacheService.setUser(user);
        return user;
    }

    @Override
    public List<User> selectByIds(List<Integer> ids) {
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public Page<User> selectByIds(Page<User> page, List<Integer> ids) {
        return userMapper.selectPage(page,new QueryWrapper<User>().in("id",ids));
    }

    @Override
    public User updateUserInfo(String username, UpdateUserInfo info) {
        User user = getUserByUsername(username);
//        user.setUserLevel(info.getUserLevel());
        user.setRealName(info.getRealName());
        user.setAge(info.getAge());
        user.setPhone(info.getPhone());
        user.setBirthDay(info.getBirthDay());
        user.setModifyTime(new Date());
        updateById(user);
        cacheService.setUser(user);
        return user;
    }

    @Override
    public User updateUserPassword(Integer id, UpdateUserInfo model) {
        User user = getById(id);
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        updateById(user);
        return user;
    }

    /**
     * 更新用户状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public User updateUserStatus(Integer id, Integer status) {
        User user = selectById(id);
        user.setStatus(status);
        updateById(user);
        cacheService.setUser(user);
        return user;
    }

    @Override
    public Page<User> getStudentList(Page<User> page, String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (username==null){
            queryWrapper.eq("role_id",UserTypeEnum.Student.getCode());
        } else {
            queryWrapper.eq("role_id",UserTypeEnum.Student.getCode()).eq("user_name",username);
        }
        return userMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Page<User> getTeacherList(Page<User> page, String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (username==null){
            queryWrapper.eq("role_id",UserTypeEnum.Teacher.getCode());
        } else {
            queryWrapper.eq("role_id",UserTypeEnum.Teacher.getCode()).eq("user_name",username);
        }
        return userMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Page<User> getAdminList(Page<User> page, String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (username==null){
            queryWrapper.eq("role_id",UserTypeEnum.Admin.getCode());
        } else {
            queryWrapper.eq("role_id",UserTypeEnum.Admin.getCode()).eq("user_name",username);
        }
        return userMapper.selectPage(page, queryWrapper);
    }

    /**
     * 通过用户名模糊搜索用户
     *
     * @param username String
     * @return List<Map < String, Object>>
     */
    @Override
    public List<Map<String, Object>> selectUserByUsername(String username) {
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().like("user_name", username));
        List<Map<String, Object>> list = new ArrayList<>();
        userList.forEach(user -> {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("name",user.getUserName());
            temp.put("value",user.getId());
            list.add(temp);
        });
        return list;
    }

    /**
     * 批量上传用户
     */
    @Override
    public void uploadUser(List<UploadUserParam> form) {
        for(UploadUserParam item: form){
            User user = new User();
            if(userMapper.selectCount(new QueryWrapper<User>().eq("user_name",item.getUserName()))>0){
                continue;
            }
            user.setUserName(item.getUserName());
            user.setUserUuid(CreateUuid.createUuid());
            user.setPassword(passwordEncoder.encode(item.getPassword()));
            user.setAge(item.getAge());
            user.setSex(item.getSex());
            user.setRoleId(item.getRoleId());
            user.setRealName(item.getRealName());
            user.setPhone(item.getPhone());
            user.setStatus(StatusEnum.OK.getCode());
//            user.setUserLevel(item.getUserLevel());
            user.setBirthDay(item.getBirthDay());
            user.setCreateTime(new Date());
            user.setModifyTime(new Date());
            user.setAvatar("https://gitee.com/cnpolaris-tian/giteePagesImages/raw/master/image_path/1644288003796af36811f-5dac-4054-a6fc-37d82565bd4d.jpg");
            save(user);

            ClassUser classUser = new ClassUser();
            classUser.setUserId(user.getId());
            classUser.setClassId(item.getClassId());
            classUserService.save(classUser);

        }
    }


}
