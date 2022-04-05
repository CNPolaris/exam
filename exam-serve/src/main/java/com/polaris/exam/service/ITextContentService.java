package com.polaris.exam.service;

import com.polaris.exam.pojo.TextContent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * 文本表 服务类
 * </p>
 *
 * @author polaris
 * @since 2022-01-08
 */
public interface ITextContentService extends IService<TextContent> {
    /**
     * 根据文本id查询
     * @param contentId 文本
     * @return 文本
     */
    TextContent getTextContentById(Integer contentId);

    /**
     * 插入内容
     * @param model TextContent
     * @return int
     */
    int insertTextContent(TextContent model);

    /**
     * 根据id查找
     * @param id Integer
     * @return TextContent
     */
    TextContent selectById(Integer id);

    /**
     * json转换
     * @param list List<T>
     * @param mapper Function<? super T, ? extends R>
     * @param <T> <T, R>
     * @param <R> <T, R>
     * @return <T, R> TextContent
     */
    <T, R> TextContent jsonConvertInsert(List<T> list, Function<? super T, ? extends R> mapper);

    /**
     * json转换
     * @param list List<T>
     * @param mapper Function<? super T, ? extends R>
     * @param <T> <T, R>
     * @param <R> <T, R>
     * @return <T, R> TextContent
     */
    <T, R>TextContent jsonConvertUpdate(TextContent textContent, List<T> list, Function<? super T, ? extends R> mapper);
}
