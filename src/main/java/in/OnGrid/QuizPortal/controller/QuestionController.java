package in.OnGrid.QuizPortal.controller;

import in.OnGrid.QuizPortal.model.Entity.Question;
import in.OnGrid.QuizPortal.model.dto.BaseResponse;
import in.OnGrid.QuizPortal.model.dto.CreateUpdateQuestionRequest;
import in.OnGrid.QuizPortal.service.QuestionService;
import in.OnGrid.QuizPortal.service.UserSessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/quiz")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @PostMapping(path = "/{quizId}/question")
    public BaseResponse<Question> createQuestion(@RequestHeader("Authorization") String token,
                                                 @RequestBody CreateUpdateQuestionRequest request,
                                                 @PathVariable("quizId") Integer quizId) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User unauthorized.");

        Question question = questionService.createQuestion(token, request, quizId);
        return new BaseResponse<Question>(HttpStatus.OK.value(), "Success", question);
    }

    @PostMapping(path = "/question/{questionId}")
    public BaseResponse<Question> updateQuestion(@RequestHeader("Authorization") String token,
                                                 @RequestBody CreateUpdateQuestionRequest request,
                                                 @PathVariable("questionId") Integer questionId) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User unauthorized.");

        Question question = questionService.updateQuestion(token, questionId, request);
        return new BaseResponse<Question>(HttpStatus.OK.value(), "Success", question);
    }

    @GetMapping(path = "/{quizId}/question")
    public BaseResponse<List<Question>> getQuestions(@RequestHeader("Authorization") String token,
                                                     @PathVariable("quizId") Integer quizId) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User unauthorized.");

        List<Question> questionList = questionService.getQuestions(token, quizId);
        return new BaseResponse<List<Question>>(HttpStatus.OK.value(), "Success", questionList);
    }

    @GetMapping(path = "/question/{questionId}")
    public BaseResponse<Question> getQuestion(@RequestHeader("Authorization") String token,
                                              @PathVariable("questionId") Integer questionId) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User unauthorized.");
        Question question = questionService.getQuestion(token, questionId);
        return new BaseResponse<Question>(HttpStatus.OK.value(), "Success", question);
    }
}
