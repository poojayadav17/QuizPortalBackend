package in.OnGrid.QuizPortal.service;

import in.OnGrid.QuizPortal.model.Entity.Quiz;
import in.OnGrid.QuizPortal.model.dto.CreateUpdateQuizRequest;
import in.OnGrid.QuizPortal.model.dto.SubmitQuizRequest;
import in.OnGrid.QuizPortal.model.dto.SubmitQuizResponse;
import in.OnGrid.QuizPortal.model.dto.QuizSubmitAnswer;

import java.util.List;

public interface QuizService {
    Quiz createQuiz(String token, CreateUpdateQuizRequest request);

    Quiz updateQuiz(String token, CreateUpdateQuizRequest request, Integer quizId);

    List<Quiz> getQuizzes(String token);

    Quiz getQuiz(Integer quizId, String token);

    SubmitQuizResponse getResult(String token, Integer quizId, SubmitQuizRequest request);
}
