package com.polaris.exam.dto.question;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuestionEditItem {
    @NotBlank
    private String prefix;
    @NotBlank
    private String content;

    private String score;

    private String itemUuid;
}
