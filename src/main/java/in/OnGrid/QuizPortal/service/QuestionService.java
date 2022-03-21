package in.OnGrid.QuizPortal.service;

import in.OnGrid.QuizPortal.model.Entity.Question;
import in.OnGrid.QuizPortal.model.dto.CreateUpdateQuestionRequest;

import java.util.List;

public interface QuestionService {
    Question createQuestion(String token, CreateUpdateQuestionRequest request, Integer quizId);

    Question updateQuestion(String token, Integer questionId, CreateUpdateQuestionRequest request);

    List<Question> getQuestions(String token, Integer quizId);

    Question getQuestion(String token, Integer questionId);
}
