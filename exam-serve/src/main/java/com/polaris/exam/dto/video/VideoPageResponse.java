package com.polaris.exam.dto.video;

import com.polaris.exam.pojo.Video;
import lombok.Data;

import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class VideoPageResponse {
    private long total;
    private List<Video> list;
}
