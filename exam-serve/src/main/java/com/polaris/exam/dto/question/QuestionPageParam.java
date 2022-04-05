package com.polaris.exam.dto.question;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuestionPageParam {
    private Integer id;
    private Integer level;
    private Integer subjectId;
    private Integer questionType;
}
