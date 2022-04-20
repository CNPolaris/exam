package com.polaris.exam.controller.student;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.video.VideoEditRequest;
import com.polaris.exam.dto.video.VideoPageRequest;
import com.polaris.exam.dto.video.VideoPageResponse;
import com.polaris.exam.pojo.Video;
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

/**
 * @author CNPolaris
 * @version 1.0
 */
@Api(value = "视频管理", tags = "学生端")
@RestController("StudentVideoController")
@RequestMapping("/api/student/video")
public class VideoController {
    @Value("${video.path}")
    private String rootPath;
    @Value("${video.url}")
    private String rootUrl;
    private final IUserService userService;
    private final IVideoService videoService;
    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;
    public VideoController(IUserService userService, IVideoService videoService, NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler) {
        this.userService = userService;
        this.videoService = videoService;
        this.nonStaticResourceHttpRequestHandler = nonStaticResourceHttpRequestHandler;
    }

    @ApiOperation("获取视频列表")
    @PostMapping("/list")
    public RespBean getStudentVideoList(Principal principal, @RequestBody VideoPageRequest model){
        Page<Video> videoPage = videoService.getVideoList(model);
        VideoPageResponse response = new VideoPageResponse();
        response.setTotal(videoPage.getTotal());
        response.setList(videoPage.getRecords());
        return RespBean.success("成功",response);
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
