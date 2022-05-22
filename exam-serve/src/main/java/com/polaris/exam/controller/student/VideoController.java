package com.polaris.exam.controller.student;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.video.VideoEditRequest;
import com.polaris.exam.dto.video.VideoPageRequest;
import com.polaris.exam.dto.video.VideoResponse;
import com.polaris.exam.pojo.Video;
import com.polaris.exam.service.ISubjectService;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.service.IVideoService;
import com.polaris.exam.utils.NonStaticResourceHttpRequestHandler;
import com.polaris.exam.utils.RespBean;
import com.polaris.exam.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "视频管理", tags = "学生端视频管理模块")
@RestController("StudentVideoController")
@RequestMapping("/api/student/video")
public class VideoController {
    @Value("${video.path}")
    private String rootPath;
    @Value("${video.url}")
    private String rootUrl;
    private final IUserService userService;
    private final IVideoService videoService;
    private final ISubjectService subjectService;
    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;
    public VideoController(IUserService userService, IVideoService videoService, ISubjectService subjectService, NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler) {
        this.userService = userService;
        this.videoService = videoService;
        this.subjectService = subjectService;
        this.nonStaticResourceHttpRequestHandler = nonStaticResourceHttpRequestHandler;
    }

    @ApiOperation("获取视频列表")
    @PostMapping("/list")
    public RespBean getStudentVideoList(Principal principal, @RequestBody VideoPageRequest model){
        Page<Video> videoPage = videoService.getVideoList(model);
        ArrayList<VideoResponse> videoResponse = new ArrayList<>(model.getLimit());
        videoPage.getRecords().forEach(video -> {
            VideoResponse properties = BeanUtil.copyProperties(video, VideoResponse.class);
            properties.setSubject(subjectService.getById(video.getSubjectId()).getName());
            videoResponse.add(properties);
        });
        HashMap<String, Object> data = new HashMap<>();
        data.put("list", videoResponse);
        data.put("total", videoPage.getTotal());
        return RespBean.success("成功", data);
    }
    @ApiOperation(value = "选择视频")
    @GetMapping("/select/{id}")
    public RespBean selectVideo(@PathVariable Integer id){
        Video video = videoService.getById(id);
        VideoEditRequest videoUploadParam = BeanUtil.copyProperties(video, VideoEditRequest.class);
        return RespBean.success("成功",videoUploadParam);
    }
    @ApiOperation(value = "播放视频")
    @GetMapping("/preview/{path}")
    public void videoPreview(HttpServletRequest request, HttpServletResponse response, @PathVariable String path) throws Exception {
        String date = path.split("_")[0];
        String[] paths = date.split("-");
        String realPath = rootPath+ File.separatorChar+paths[0]+File.separatorChar+paths[1]+File.separatorChar+paths[2]+File.separatorChar+path;
        Path filePath = Paths.get(realPath);
        if(Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if(!StringUtils.isEmpty(mimeType)){
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }
    @ApiOperation("获取指定年级的视频")
    @GetMapping("/level")
    public RespBean getVideoLevel(Principal principal){
        Integer userLevel = userService.getUserByUsername(principal.getName()).getUserLevel();
        return RespBean.success(userLevel+"年级课程视频", videoService.getLevelVideo(userLevel));
    }
}
