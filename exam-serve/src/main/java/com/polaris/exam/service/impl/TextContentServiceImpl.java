package com.polaris.exam.service.impl;

import cn.hutool.json.JSONUtil;
import com.polaris.exam.pojo.TextContent;
import com.polaris.exam.mapper.TextContentMapper;
import com.polaris.exam.service.ITextContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 文本表 服务实现类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
@Service
public class TextContentServiceImpl extends ServiceImpl<TextContentMapper, TextContent> implements ITextContentService {
    private final TextContentMapper textContentMapper;
    @Autowired
    public TextContentServiceImpl(TextContentMapper textContentMapper) {
        this.textContentMapper = textContentMapper;
    }

    /**
     * 根据文本id查询
     * @param contentId 文本
     * @return 文本
     */
    @Override
    public TextContent getTextContentById(Integer contentId) {
        return textContentMapper.selectById(contentId);
    }

    @Override
    public int insertTextContent(TextContent model) {
        return textContentMapper.insert(model);
    }

    @Override
    public TextContent selectById(Integer id) {
        return textContentMapper.selectById(id);
    }

    @Override
    public <T, R> TextContent jsonConvertInsert(List<T> list,Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = JSONUtil.toJsonStr(list);
        } else {
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = JSONUtil.toJsonStr(mapList);
        }
        TextContent textContent = new TextContent();
        textContent.setContent(frameTextContent);
        textContent.setCreateTime(new Date());
        //insertByFilter(textContent);  cache useless
        return textContent;
    }

    @Override
    public <T, R> TextContent jsonConvertUpdate(TextContent textContent, List<T> list, Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = JSONUtil.toJsonStr(list);
        } else {
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = JSONUtil.toJsonStr(mapList);
        }
        textContent.setContent(frameTextContent);
        updateById(textContent);
        //this.updateByIdFilter(textContent);  cache useless
        return textContent;
    }
}
