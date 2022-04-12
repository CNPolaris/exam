package com.polaris.exam.mapper;

import com.polaris.exam.pojo.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author polaris
 * @since 2022-02-15
 */
public interface VideoMapper extends BaseMapper<Video> {
    @Select("select * from video where level=#{level} limit 5")
    List<Video> getLevelVideo(Integer level);
}
