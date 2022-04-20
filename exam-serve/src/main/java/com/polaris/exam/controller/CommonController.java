package com.polaris.exam.controller;

import cn.hutool.core.bean.BeanUtil;
import com.polaris.exam.dto.user.LoginParam;
import com.polaris.exam.dto.user.UserInfoResponse;
import com.polaris.exam.enums.LevelEnum;
import com.polaris.exam.enums.SexTypeEnum;
import com.polaris.exam.enums.UserTypeEnum;
import com.polaris.exam.pojo.Class;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.*;
import com.polaris.exam.utils.RespBean;
import com.polaris.exam.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "登录管理模块",tags = "登录管理模块")
@RestController("CommonController")
@RequestMapping("/api")
public class CommonController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    private final LoginService loginService;
    private final IUserService userService;
    private final AdminCacheService adminCacheService;
    private final IClassService classService;
    private final OssAdminService ossAdminService;
    public CommonController(LoginService loginService, IUserService userService, IRoleService roleService, AdminCacheService adminCacheService, IClassService classService, OssAdminService ossAdminService) {
        this.loginService = loginService;
        this.userService = userService;
        this.adminCacheService = adminCacheService;
        this.classService = classService;
        this.ossAdminService = ossAdminService;
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public RespBean login(@RequestBody LoginParam adminLoginParam, HttpServletRequest request){
        if(adminLoginParam.equals(null)){
            return RespBean.error("不能为空");
        }
        return loginService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(),request);
    }
    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public RespBean logout(Principal principal) {
        adminCacheService.delUser(principal.getName());
        return RespBean.success("注销成功");
    }
    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info")
    public RespBean getUserInfo(Principal principal){
        if(principal == null){
            return RespBean.error("获取用户信息失败");
        }
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        UserInfoResponse userInfoResponse = BeanUtil.copyProperties(user, UserInfoResponse.class);
        userInfoResponse.setRoles(UserTypeEnum.fromCode(user.getRoleId()).getName());
        userInfoResponse.setSexStr(SexTypeEnum.fromCode(user.getSex()).getName());
        if(user.getRoleId()==UserTypeEnum.Student.getCode()){
            userInfoResponse.setUserLevelStr(LevelEnum.fromCode(user.getUserLevel()).getName());
            Class aClass = classService.getClassByUserId(user.getId());
            if(aClass!=null){
                userInfoResponse.setClassName(aClass.getClassName());
                userInfoResponse.setClassId(aClass.getId());
            }
        }
        return RespBean.success("获取用户信息成功", userInfoResponse);
    }
    @ApiOperation(value = "上传图片")
    @PostMapping("/image/upload")
    public RespBean uploadImage(@RequestBody MultipartFile file) throws IOException {
        try {
            if(file.isEmpty()){
                return RespBean.error("图片不能为空");
            }
            String imgName = StringUtils.getRandomImgName(file.getOriginalFilename());
            String image = ossAdminService.uploadImage(file.getInputStream(), imgName);
            if(image.isEmpty()){
                return RespBean.error("上传图片失败");
            }
            return RespBean.success("上传图片成功",image);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return RespBean.error("上传失败");
    }
//    @ApiOperation(value = "面板数据")
//    @GetMapping("/dash/info")
//    public RespBean dashInfo(Principal principal){
//        Map<String, Object> response = new HashMap<>();
//
//        User user = userService.getUserByUsername(principal.getName());
//
//        if(adminCacheService.getDashInfo(user.getUserName())!=null){
//            response = adminCacheService.getDashInfo(user.getUserName());
//            if(!response.isEmpty()){
//                return RespBean.success("成功",response);
//            }
//        }
//
//        int questionCount = questionService.selectQuestionCountByUser(user.getId());
//        int classCount = classService.selectClassCount();
//        int examPaperCount = examPaperService.selectCountByUser(user.getId());
//        int classUserCount = 0;
//        if(user.getRoleId().equals(UserTypeEnum.Teacher.getCode())){
//            Integer classId = classTeacherService.getClassIdByTeacher(user.getId());
//            classUserCount = classUserService.selectStudentCount(classId);
//        }
//        response.put("questionCount",questionCount);
//        response.put("classCount",classCount);
//        response.put("examPaperCount",examPaperCount);
//        response.put("classUserCount",classUserCount);
//        adminCacheService.setDashInfo(user.getUserName(), response);
//        return RespBean.success("成功",response);
//    }
//
//    @ApiOperation(value = "用户注册")
//    @PostMapping("/register")
//    public RespBean register(@RequestBody RegisterParam model){
//        if(model==null){
//            return RespBean.error("不能为空");
//        }
//        User exitUser = userService.getUserByUsername(model.getUserName());
//        if(exitUser!=null){
//            return RespBean.error("用户已经存在");
//        }
//
//        if(StrUtil.isBlank(model.getPassword())){
//            return RespBean.error("密码不能为空");
//        }
//        model.setPassword(passwordEncoder.encode(model.getPassword()));
//        User user = new User();
//        Date now = new Date();
//        BeanUtil.copyProperties(model,user);
//        user.setUserUuid(CreateUuid.createUuid());
//        user.setRoleId(UserTypeEnum.Student.getCode());
//        user.setCreateTime(now);
//        user.setLastActiveTime(new Date());
//        user.setModifyTime(now);
//        user.setStatus(StatusEnum.OK.getCode());
//        if(userService.save(user)){
//            adminCacheService.setUser(user);
//            return RespBean.success("注册成功",user);
//        }else{
//            return RespBean.error("注册失败");
//        }
//    }

    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/refreshToken")
    public RespBean refreshToken(Principal principal,HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String refreshToken = loginService.refreshToken(token);
        if (refreshToken == null){
            return RespBean.error("token已经过期！");
        }
        adminCacheService.setToken(principal.getName(),refreshToken);

        Map<String,Object> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("刷新token成功",tokenMap);
    }
}
