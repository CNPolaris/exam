package com.polaris.exam.dto.video;

import lombok.Data;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
public class VideoPageRequest {
    private Integer page;
    private Integer limit;
    private Integer subjectId;
}
