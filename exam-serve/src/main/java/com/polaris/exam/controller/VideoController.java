package com.polaris.exam.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.video.VideoResponse;
import com.polaris.exam.dto.video.VideoEditRequest;
import com.polaris.exam.pojo.Video;
import com.polaris.exam.service.IUserService;
import com.polaris.exam.service.IVideoService;
import com.polaris.exam.utils.CreateUuid;
import com.polaris.exam.utils.NonStaticResourceHttpRequestHandler;
import com.polaris.exam.utils.RespBean;
import com.polaris.exam.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author polaris
 * @since 2022-02-15
 */
@Api(tags = "视频管理")
@RestController
@RequestMapping("/api/video")
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
    @ApiOperation(value = "视频列表")
    @PostMapping("/list")
    public RespBean videoList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit,@RequestParam(required = false) Integer subjectId){
        Page<Video> objectPage = new Page<>(page, limit);
        Page<Video> videoPage = videoService.videoList(objectPage, subjectId);
        List<Video> videoList = videoPage.getRecords();
        ArrayList<VideoResponse> videoResponse = new ArrayList<>();
        videoList.forEach(video -> {
            videoResponse.add(BeanUtil.copyProperties(video,VideoResponse.class));
        });
        HashMap<String, Object> data = new HashMap<>();
        data.put("data", videoResponse);
        data.put("total", videoPage.getTotal());
        return RespBean.success("成功", data);
    }
    @ApiOperation(value = "上传视频")
    @PostMapping("/upload")
    public RespBean upload( @RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return RespBean.error("上传视频不能为空");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        String format = simpleDateFormat.format(new Date());
        File dest = new File(rootPath+format);

        if(!dest.isDirectory()){
            dest.mkdirs();
        }
        try{
            String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "_"+file.getOriginalFilename();
            file.transferTo(new File(dest, fileName));
            String path = dest.getPath()+File.separatorChar+fileName;
            String url = rootUrl+"/"+fileName;
            HashMap<String, Object> map = new HashMap<>();
            map.put("path",path);
            map.put("url",url);
            return RespBean.success("上传成功",map);
        } catch (IOException e) {
            return RespBean.error("上传失败"+e.getMessage());
        }
    }
    @ApiOperation(value = "播放视频")
    @GetMapping("/preview/{path}")
    public void videoPreview(HttpServletRequest request, HttpServletResponse response, @PathVariable String path) throws Exception {
        String date = path.split("_")[0];
        String[] paths = date.split("-");
        String realPath = rootPath+File.separatorChar+paths[0]+File.separatorChar+paths[1]+File.separatorChar+paths[2]+File.separatorChar+path;
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

    @ApiOperation(value = "保存视频")
    @PostMapping("/save")
    public RespBean saveVideo(@RequestBody VideoEditRequest model){
        if(!validVideoUploadParam(model)){
            return RespBean.error("不能为空");
        }
        return RespBean.success("成功",videoService.uploadVideo(model));
    }

    @ApiOperation(value = "删除视频")
    @GetMapping("/delete/{id}")
    public RespBean deleteVideo(@PathVariable Integer id){
        Video video = videoService.getById(id);
        try{
            File file = new File(video.getPath());
            if(!file.exists()){
                videoService.removeById(video);
                return RespBean.success("删除视频");
            } else {
                if(file.delete()){
                    return RespBean.success("删除成功");
                } else {
                    return RespBean.error("删除失败");
                }
            }
        } catch (Exception e){
            return RespBean.error(e.getMessage());
        }
    }

    @ApiOperation(value = "选择视频")
    @GetMapping("/select/{id}")
    public RespBean selectVideo(@PathVariable Integer id){
        Video video = videoService.getById(id);
        VideoEditRequest videoUploadParam = BeanUtil.copyProperties(video, VideoEditRequest.class);
        return RespBean.success("成功",videoUploadParam);
    }

    @ApiOperation("获取指定年级的视频")
    @GetMapping("/level")
    public RespBean getVideoLevel(Principal principal){
        Integer userLevel = userService.getUserByUsername(principal.getName()).getUserLevel();
        return RespBean.success(userLevel+"年级课程视频", videoService.getLevelVideo(userLevel));
    }

    private boolean validVideoUploadParam(VideoEditRequest model){
        if(!StrUtil.isBlank(model.getName())){
            if(!StrUtil.isBlank(model.getPath())){
                return !StrUtil.isBlank(model.getUrl());
            }
        }
        return false;
    }
}
