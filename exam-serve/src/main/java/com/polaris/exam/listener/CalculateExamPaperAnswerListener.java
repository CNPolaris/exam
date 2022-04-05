package com.polaris.exam.listener;

import com.polaris.exam.dto.paper.ExamPaperAnswerInfo;
import com.polaris.exam.enums.ExamPaperTypeEnum;
import com.polaris.exam.enums.QuestionTypeEnum;
import com.polaris.exam.event.CalculateExamPaperAnswerCompleteEvent;
import com.polaris.exam.pojo.ExamPaper;
import com.polaris.exam.pojo.ExamPaperAnswer;
import com.polaris.exam.pojo.ExamPaperQuestionCustomerAnswer;
import com.polaris.exam.pojo.TextContent;
import com.polaris.exam.service.IExamPaperAnswerService;
import com.polaris.exam.service.IExamPaperQuestionCustomerAnswerService;
import com.polaris.exam.service.ITaskExamCustomerAnswerService;
import com.polaris.exam.service.ITextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Component
public class CalculateExamPaperAnswerListener implements ApplicationListener<CalculateExamPaperAnswerCompleteEvent> {
    private final IExamPaperAnswerService examPaperAnswerService;
    private final IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final ITextContentService textContentService;
    private final ITaskExamCustomerAnswerService examCustomerAnswerService;
    @Autowired
    public CalculateExamPaperAnswerListener(IExamPaperAnswerService examPaperAnswerService, IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, ITextContentService textContentService, ITaskExamCustomerAnswerService examCustomerAnswerService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.textContentService = textContentService;
        this.examCustomerAnswerService = examCustomerAnswerService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CalculateExamPaperAnswerCompleteEvent event) {
        Date now = new Date();
        ExamPaperAnswerInfo examPaperAnswerInfo = (ExamPaperAnswerInfo) event.getSource();
        ExamPaper examPaper = examPaperAnswerInfo.getExamPaper();
        ExamPaperAnswer examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperAnswerInfo.getExamPaperQuestionCustomerAnswers();

        examPaperAnswerService.saveOrUpdate(examPaperAnswer);
        examPaperQuestionCustomerAnswers.stream().filter(a-> QuestionTypeEnum.needSaveTextContent(a.getQuestionType())).forEach(d->{
            TextContent textContent = new TextContent();
            textContent.setContent(d.getAnswer());
            textContent.setCreateTime(now);
            textContentService.save(textContent);

            d.setTextContentId(textContent.getId());
            d.setAnswer(null);
        });
        examPaperQuestionCustomerAnswers.forEach(d->{
            d.setExamPaperAnswerId(examPaperAnswer.getId());
        });
        examPaperQuestionCustomerAnswerService.insertList(examPaperQuestionCustomerAnswers);

        switch(ExamPaperTypeEnum.fromCode(examPaper.getPaperType())){
            case Task:{
                examCustomerAnswerService.insertOrUpdate(examPaper,examPaperAnswer);
                break;
            }
            default:
                break;
        }
    }
}
