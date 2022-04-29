package com.polaris.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.video.VideoEditRequest;
import com.polaris.exam.dto.video.VideoPageRequest;
import com.polaris.exam.dto.video.VideoResponse;
import com.polaris.exam.pojo.Video;
import com.polaris.exam.mapper.VideoMapper;
import com.polaris.exam.service.ISubjectService;
import com.polaris.exam.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-02-15
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    private final VideoMapper videoMapper;
    private final ISubjectService subjectService;
    public VideoServiceImpl(VideoMapper videoMapper, ISubjectService subjectService) {
        this.videoMapper = videoMapper;
        this.subjectService = subjectService;
    }

    /**
     * 视频列表
     *
     * @param page      Page<Video>
     * @param subjectId Integer
     * @return Page<Video>
     */
    @Override
    public Page<Video> videoList(Page<Video> page, Integer subjectId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        if(subjectId != null){
            queryWrapper.eq("subject_id", subjectId);
        }
        return videoMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Page<Video> getVideoList(VideoPageRequest model) {
        Page<Video> page = new Page<>(model.getPage(), model.getLimit());
        if(model.getSubjectId()!=null){
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("subject_id", model.getSubjectId());
            return videoMapper.selectPage(page, queryWrapper);
        }
        return videoMapper.selectPage(page,new QueryWrapper<>());
    }

    /**
     * 视频上传
     *
     * @param model VideoUploadParam
     */
    @Override
    public Video uploadVideo(VideoEditRequest model) {
        Video video = new Video();
        // 如果存在id 更新
        if(model.getId()!=null && getById(model.getId())!=null){
            video = getById(model.getId());
            video.setName(model.getName());
            video.setLevel(model.getLevel());
            video.setSubjectId(model.getSubjectId());
            video.setPath(model.getPath());
            video.setCover(model.getCover());
            video.setUrl(model.getUrl());
            video.setTags(model.getTags().toString());
            save(video);
        } else {//不存在id 新建
            video.setName(model.getName());
            video.setLevel(model.getLevel());
            video.setSubjectId(model.getSubjectId());
            video.setPath(model.getPath());
            video.setCover(model.getCover());
            video.setUrl(model.getUrl());
            video.setTags(model.getTags().toString());
            video.setCreateTime(new Date());
            save(video);
        }
        return video;
    }

    @Override
    public List<VideoResponse> getLevelVideo(Integer level) {
        List<Video> levelVideo = videoMapper.getLevelVideo(level);
        List<VideoResponse> objects = new ArrayList<>();
        levelVideo.forEach(video -> {
            VideoResponse videoResponse = BeanUtil.copyProperties(video, VideoResponse.class);
            videoResponse.setSubject(subjectService.getById(video.getSubjectId()).getName());
            objects.add(videoResponse);
        });
        return objects;
    }
}
