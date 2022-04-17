package com.polaris.exam.service;

import com.polaris.exam.dto.paper.ExamPaperEditRequest;
import com.polaris.exam.dto.question.QuestionResponse;
import com.polaris.exam.pojo.Permission;
import com.polaris.exam.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author polaris
 */
public interface AdminCacheService {
    /**
     * 设置缓存后台用户信息
     * @param user user
     */
    void setUser(User user);

    /**
     * 获取缓存后台用户信息
     * @param username username
     * @return User
     */
    User getUser(String username);

    /**
     * 删除后台用户缓存
     * @param username String
     */
    void delUser(String username);
    /**
     * 设置缓存后台资源列表
     * @param roleId roleId
     * @param permissionList permissionList
     */
    void setPermissionListByRole(Integer roleId, List<Permission> permissionList);
    /**
     * 获取缓存后台资源列表
     * @param roleId roleId
     * @return List<Permission>
     */
    List<Permission> getPermissionListByRole(Integer roleId);

    /**
     * 删除缓存后台资源列表
     * @param roleId roleId
     */
    void delPermissionListByRole(Integer roleId);

    /**
     * 设置缓存后台全部资源列表
     * @param permissionList permissionList
     */
    void setPermissionAll(List<Permission> permissionList);

    /**
     * 获取全部资源列表缓存
     * @return List<Permission>
     */
    List<Permission> getPermissionAll();
    /**
     * 删除缓存后台全部资源列表
     */
    void delPermissionAll();
    /**
     * 设置缓存后台token
     * @param username username
     * @param token token
     */
    void setToken(String username,String token);

    /**
     * 获取缓存后台token
     * @param username username
     * @return String
     */
    String getToken(String username);

    /**
     * 判断token是否存在
     * @param username String
     * @return Boolean
     */
    Boolean hasToken(String username);
    /**
     * 删除缓存后台token
     * @param username username
     */
    void delToken(String username);

    /**
     * 删除面板信息
     * @param username String
     */
    void delDashInfo(String username);

    /**
     * 设置面板信息
     * @param username String
     * @param response Map<String,Object>
     */
    void setDashInfo(String username, Map<String,Object> response);

    /**
     * 获取面板信息
     * @param username String
     * @return Map<String,Object>
     */
    Map<String,Object> getDashInfo(String username);

    /**
     * 设置分析结果
     * @param paperId Integer
     * @param classId Integer
     * @param response Map<String,Object>
     */
    void setAnalyzeInfo(Integer paperId,Integer classId, Map<String,Object> response);

    /**
     * 获取分析结果
     * @param paperId Integer
     * @param classId Integer
     * @return Map<String,Object>
     */
    Map<String,Object> getAnalyzeInfo(Integer paperId,Integer classId);

    /**
     * 设置系统分析内容
     * @param systemInfo Map<String,Object>
     */
    void setSystemInfo(Map<String,Object> systemInfo);

    /**
     * 获取系统分析内容
     * @return Map<String,Object>
     */
    Map<String,Object> getSystemInfo();

    Boolean hasQuestionList(Integer page);

    /**
     * 设置题库缓存
     * @param questionList List<QuestionResponse>
     * @param page Integer
     */
    void setQuestionList(List<QuestionResponse> questionList, Integer page);

    /**
     * 获取题库
     * @param page Integer
     * @return List<QuestionResponse>
     */
    List<QuestionResponse> getQuestionList(Integer page);

    /**
     * 设置题目总数
     * @param total Long
     */
    void setQuestionTotal(Long total);

    /**
     * 获取题目总数
     * @return Integer
     */
    Integer getQuestionTotal();

    /**
     * 设置不同类型用户缓存
     * @param userList List<User>
     * @param type String
     * @param page Integer
     */
    void setUserList(List<User> userList, String type, Integer page);

    /**
     * 获取不同类型用户缓存
     * @param type String
     * @param page Integer
     * @return List<User>
     */
    List<User> getUserList(String type, Integer page);

    /**
     * 判断缓存是否存在
     * @param type String
     * @param page Integer
     * @return Boolean
     */
    Boolean hasUserList(String type, Integer page);

    /**
     * 设置不同用户总数
     * @param type String
     * @param total Long
     */
    void setUserTotal(String type, Long total);

    /**
     * 获取不同用户总数
     * @param type String
     * @return Integer
     */
    Integer getUserTotal(String type);

    /**
     * 判断是否存在用户总数
     * @param type String
     * @return Boolean
     */
    Boolean hasUserTotal(String type);

    /**
     * 缓存学生考试试卷
     * @param username String
     * @param paperId Integer
     * @param expire Integer
     */
    void setDoingPaper(String username, Integer paperId, Integer expire, ExamPaperEditRequest paper);

    /**
     * 获取学生考试试卷
     * @param username String
     * @param paperId Integer
     * @return ExamPaperEditRequest
     */
    ExamPaperEditRequest getDoingPaper(String username,Integer paperId);
    /**
     * 判断学生考试试卷
     * @param username String
     * @param paperId Integer
     * @return Boolean
     */
    Boolean hasDoingPaper(String username,Integer paperId);
}
