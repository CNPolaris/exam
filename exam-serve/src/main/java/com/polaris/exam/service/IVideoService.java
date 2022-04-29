package com.polaris.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polaris.exam.dto.video.VideoEditRequest;
import com.polaris.exam.dto.video.VideoPageRequest;
import com.polaris.exam.dto.video.VideoResponse;
import com.polaris.exam.pojo.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author polaris
 * @since 2022-02-15
 */
public interface IVideoService extends IService<Video> {
    /**
     * 视频列表
     * @param page Page<Video>
     * @param subjectId Integer
     * @return Page<Video>
     */
    Page<Video> videoList(Page<Video> page, Integer subjectId);

    /**
     * 获取视频列表
     * @param model VideoPageRequest
     * @return Page<Video>
     */
    Page<Video> getVideoList(VideoPageRequest model);
    /**
     * 视频上传
     * @param model VideoUploadParam
     * @return Video
     */
    Video uploadVideo(VideoEditRequest model);

    /**
     * 获取指定年级的视频
     * @param level Integer
     * @return List<Video>
     */
    List<VideoResponse> getLevelVideo(Integer level);
}
