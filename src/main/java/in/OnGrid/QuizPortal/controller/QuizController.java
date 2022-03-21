package in.OnGrid.QuizPortal.controller;

import in.OnGrid.QuizPortal.model.Entity.Quiz;
import in.OnGrid.QuizPortal.model.dto.*;
import in.OnGrid.QuizPortal.service.QuizService;
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
public class QuizController {
    @Autowired
    QuizService quizService;
    @Autowired
    UserSessionService userSessionService;

    @PostMapping(path = "/")
    public BaseResponse<Quiz> createQuiz(@RequestHeader("Authorization") String token,
                                         @RequestBody CreateUpdateQuizRequest request) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User unauthorized.");

        Quiz quiz = quizService.createQuiz(token, request);
        return new BaseResponse<Quiz>(HttpStatus.OK.value(), "Success", quiz);
    }

    @PostMapping(path = "/{quizId}/")
    public BaseResponse<Quiz> updateQuiz(@RequestHeader("Authorization") String token,
                                         @RequestBody CreateUpdateQuizRequest request,
                                         @PathVariable("quizId") Integer quizId) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User unauthorized.");

        Quiz quiz = quizService.updateQuiz(token, request, quizId);
        return new BaseResponse<Quiz>(HttpStatus.OK.value(), "Success", quiz);
    }

    @GetMapping(path = "/")
    public BaseResponse<List<Quiz>> getQuizzes(@RequestHeader("Authorization") String token) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User unauthorized.");

        List<Quiz> quizzes = quizService.getQuizzes(token);
        return new BaseResponse<List<Quiz>>(HttpStatus.OK.value(), "Success", quizzes);
    }

    @GetMapping(path = "/{quizId}")
    public BaseResponse<Quiz> getQuiz(@RequestHeader("Authorization") String token,
                                      @PathVariable("quizId") Integer quizId) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User unauthorized.");

        Quiz quiz = quizService.getQuiz(quizId, token);
        return new BaseResponse<Quiz>(HttpStatus.OK.value(), "Success", quiz);
    }

    @PostMapping(path = "/{quizId}/submit")
    public BaseResponse<SubmitQuizResponse> getResult(@RequestHeader("Authorization") String token,
                                                      @RequestBody SubmitQuizRequest request,
                                                      @PathVariable("quizId") Integer quizId) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User unauthorized.");

        SubmitQuizResponse result = quizService.getResult(token, quizId, request);
        return new BaseResponse<SubmitQuizResponse>(HttpStatus.OK.value(), "Success", result);
    }
}
