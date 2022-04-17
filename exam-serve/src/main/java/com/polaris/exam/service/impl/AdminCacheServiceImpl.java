package com.polaris.exam.service.impl;

import com.polaris.exam.dto.paper.ExamPaperEditRequest;
import com.polaris.exam.dto.paper.ExamPaperSubmit;
import com.polaris.exam.dto.question.QuestionResponse;
import com.polaris.exam.pojo.Permission;
import com.polaris.exam.pojo.User;
import com.polaris.exam.service.AdminCacheService;
import com.polaris.exam.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Service
public class AdminCacheServiceImpl implements AdminCacheService {
    @Autowired
    private final RedisService redisService;
    @Value("${redis.key.user}")
    private String REDIS_KEY_USER;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.permissionList}")
    private String REDIS_KEY_PERMISSION_LIST;
    @Value("${redis.key.allPermission}")
    private String REDIS_KEY_ALLPERMISSION;
    @Value("${redis.key.token}")
    private String REDIS_KEY_TOKEN;
    @Value("${redis.key.dashInfo}")
    private String REDIS_KEY_DASHINFO;
    @Value("${redis.key.analyze}")
    private String REDIS_KEY_ANALYZE;
    @Value("${redis.key.system}")
    private String REDIS_KEY_SYSTEMINFO;
    @Value("${redis.key.userList}")
    private String REDIS_KEY_USER_LIST;
    public AdminCacheServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 设置缓存后台用户信息
     *
     * @param user
     */
    @Override
    public void setUser(User user) {
        String key= REDIS_KEY_USER +":"+user.getUserName();
        redisService.set(key,user,REDIS_EXPIRE);
    }

    /**
     * 获取缓存后台用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User getUser(String username) {
        String key= REDIS_KEY_USER +":"+username;
        return (User)redisService.get(key);
    }

    /**
     * 删除后台用户缓存
     *
     * @param username
     */
    @Override
    public void delUser(String username) {
        if(username!=null){
            String key= REDIS_KEY_USER +":"+username;
            redisService.del(key);
        }
    }

    /**
     * 设置缓存后台资源列表
     *
     * @param roleId
     * @param permissionList
     */
    @Override
    public void setPermissionListByRole(Integer roleId, List<Permission> permissionList) {
        String key = REDIS_KEY_PERMISSION_LIST+":"+roleId;
        redisService.set(key,permissionList,REDIS_EXPIRE);
    }

    /**
     * 获取缓存后台资源列表
     *
     * @param roleId
     */
    @Override
    public List<Permission> getPermissionListByRole(Integer roleId) {
        String key = REDIS_KEY_PERMISSION_LIST+":"+roleId;
        return (List<Permission>)redisService.get(key);
    }

    /**
     * 删除缓存后台资源列表
     *
     * @param roleId
     */
    @Override
    public void delPermissionListByRole(Integer roleId) {
        String key = REDIS_KEY_PERMISSION_LIST+":"+roleId;
        redisService.del(key);
    }

    /**
     * 设置缓存后台全部资源列表
     *
     * @param permissionList
     */
    @Override
    public void setPermissionAll(List<Permission> permissionList) {
        String key = REDIS_KEY_ALLPERMISSION+":"+"allPermission";
        redisService.set(key,permissionList,REDIS_EXPIRE);
    }

    /**
     * 获取缓存后台全部资源列表
     */
    @Override
    public List<Permission> getPermissionAll() {
        String key = REDIS_KEY_ALLPERMISSION+":"+"allPermission";
        return (List<Permission>)redisService.get(key);
    }

    /**
     * 删除缓存后台全部资源列表
     */
    @Override
    public void delPermissionAll() {
        String key = REDIS_KEY_ALLPERMISSION+":"+"allPermission";
        redisService.del(key);
    }

    /**
     * 设置缓存后台token
     *
     * @param username
     * @param token
     */
    @Override
    public void setToken(String username, String token) {
        String key = REDIS_KEY_TOKEN+":"+username;
        redisService.set(key,token,REDIS_EXPIRE);
    }

    /**
     * 获取缓存后台token
     *
     * @param username
     * @return
     */
    @Override
    public String getToken(String username) {
        String key = REDIS_KEY_TOKEN+":"+username;
        return (String)redisService.get(key);
    }

    /**
     * 判断token是否存在
     *
     * @param username String
     * @return Boolean
     */
    @Override
    public Boolean hasToken(String username) {
        String key = REDIS_KEY_TOKEN+":"+username;
        return redisService.hasKey(key);
    }

    /**
     * 删除缓存后台token
     *
     * @param username
     */
    @Override
    public void delToken(String username) {
        String key = REDIS_KEY_TOKEN+":"+username;
        redisService.del(key);
    }

    /**
     * 删除面板信息
     *
     * @param username String
     */
    @Override
    public void delDashInfo(String username) {
        String key = REDIS_KEY_DASHINFO+":"+username;
        redisService.del(key);
    }

    /**
     * 设置面板信息
     *
     * @param username String
     * @param response Map<String,Object>
     */
    @Override
    public void setDashInfo(String username, Map<String,Object> response) {
        String key = REDIS_KEY_DASHINFO+":"+username;
        redisService.set(key,response,REDIS_EXPIRE);
    }

    @Override
    public void setAnalyzeInfo(Integer paperId, Integer classId, Map<String, Object> response) {
        String key = REDIS_KEY_ANALYZE+":"+paperId+":"+classId;
        redisService.set(key,response,600);
    }

    @Override
    public Map<String, Object> getAnalyzeInfo(Integer paperId, Integer classId) {
        String key = REDIS_KEY_ANALYZE+":"+paperId+":"+classId;
        return (Map<String, Object>)redisService.get(key);
    }

    /**
     * 获取面板信息
     *
     * @param username String
     * @return Map<String, Object>
     */
    @Override
    public Map<String,Object> getDashInfo(String username) {
        String key = REDIS_KEY_DASHINFO+":"+username;
        return (Map<String,Object>)redisService.get(key);
    }

    @Override
    public void setSystemInfo(Map<String, Object> systemInfo) {
        String key = REDIS_KEY_SYSTEMINFO+":"+"server";
        redisService.set(key, systemInfo, 600);
    }

    @Override
    public Map<String, Object> getSystemInfo() {
        String key = REDIS_KEY_SYSTEMINFO+":"+"server";
        return (Map<String,Object>) redisService.get(key);
    }

    @Override
    public Boolean hasQuestionList(Integer page) {
        String key = "exam:question:"+page;
        return redisService.hasKey(key);
    }

    /**
     * 设置题库缓存
     *
     * @param questionList List<QuestionResponse>
     * @param page         Integer
     */
    @Override
    public void setQuestionList(List<QuestionResponse> questionList, Integer page) {
        String key = "exam:question:"+page;
        redisService.set(key,questionList,REDIS_EXPIRE);
    }

    /**
     * 获取题库
     *
     * @param page Integer
     * @return List<QuestionResponse>
     */
    @Override
    public List<QuestionResponse> getQuestionList(Integer page) {
        String key = "exam:question:"+page;
        return (List<QuestionResponse>) redisService.get(key);
    }

    @Override
    public void setQuestionTotal(Long total) {
        String key = "exam:question:total";
        redisService.set(key,total,REDIS_EXPIRE);
    }

    @Override
    public Integer getQuestionTotal() {
        String key = "exam:question:total";
        return (Integer) redisService.get(key);
    }

    @Override
    public void setUserList(List<User> userList, String type, Integer  page) {
        String key = REDIS_KEY_USER_LIST+":"+type+":"+page;
        redisService.set(key, userList, REDIS_EXPIRE);
    }

    @Override
    public List<User> getUserList(String type, Integer page) {
        String key = REDIS_KEY_USER_LIST+":"+type+":"+page;
        return (List<User>) redisService.get(key);
    }

    @Override
    public Boolean hasUserList(String type, Integer page) {
        return redisService.hasKey(REDIS_KEY_USER_LIST+":"+type+":"+page);
    }

    @Override
    public void setUserTotal(String type, Long total) {
        String key = REDIS_KEY_USER_LIST+":"+type+":"+"total";
        redisService.set(key,total,REDIS_EXPIRE);
    }

    @Override
    public Integer getUserTotal(String type) {
        String key = REDIS_KEY_USER_LIST+":"+type+":"+"total";
        return (Integer) redisService.get(key);
    }

    @Override
    public Boolean hasUserTotal(String type) {
        return redisService.hasKey(REDIS_KEY_USER_LIST+":"+type+":"+"total");
    }

    @Override
    public void setDoingPaper(String username, Integer paperId, Integer expire, ExamPaperEditRequest paper) {
        redisService.set("exam:doing:"+paperId+":"+username,paper,expire*60);
    }

    @Override
    public ExamPaperEditRequest getDoingPaper(String username, Integer paperId) {
        ExamPaperEditRequest paper = (ExamPaperEditRequest) redisService.get("exam:doing:" + username + ":" + paperId);
        paper.setSuggestTime((int) (redisService.getExpire("exam:doing:"+paperId+":"+username)/60));
        return paper;
    }

    @Override
    public Boolean hasDoingPaper(String username, Integer paperId) {
        return redisService.hasKey("exam:doing:"+paperId+":"+username);
    }

    @Override
    public void setAnswer(String username, Integer paperId, ExamPaperSubmit submit) {
        redisService.set("exam:answer:"+paperId+":"+username,submit);
    }

    @Override
    public ExamPaperSubmit getAnswer(String username, Integer paperId) {
        return (ExamPaperSubmit) redisService.get("exam:answer:"+paperId+":"+username);
    }

    @Override
    public Boolean hasAnswer(String username, Integer paperId) {
        return redisService.hasKey("exam:answer:"+paperId+":"+username);
    }
}
