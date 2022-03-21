package in.OnGrid.QuizPortal.service.impl;

import in.OnGrid.QuizPortal.dao.QuestionDao;
import in.OnGrid.QuizPortal.dao.QuizDao;
import in.OnGrid.QuizPortal.model.Entity.Question;
import in.OnGrid.QuizPortal.model.Entity.Quiz;
import in.OnGrid.QuizPortal.model.dto.CreateUpdateQuestionRequest;
import in.OnGrid.QuizPortal.service.QuestionService;
import in.OnGrid.QuizPortal.service.UserSessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    QuizDao quizDao;
    @Autowired
    UserSessionService userSessionService;

    @Override
    public Question createQuestion(String token, CreateUpdateQuestionRequest request, Integer quizId) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");
        if (request == null)
            throw new IllegalArgumentException("Request cannot be null.");
        if (quizId == null)
            throw new IllegalArgumentException("Quiz id cannot be null.");

        String description = request.getDescription();
        Boolean ans = request.getAnswer();

        if (StringUtils.isBlank(description))
            throw new IllegalArgumentException("Description cannot be null.");
        if (ans == null)
            throw new IllegalArgumentException("Answer cannot be null.");

        Quiz quiz = quizDao.getById(quizId);
        if (quiz == null)
            throw new IllegalArgumentException("No quiz found by this id.");

        Question question = new Question();
        question.setDescription(description);
        question.setAnswer(ans);
        question.setQuiz(quiz);
//        quiz.getQuestionList().add(question);

        quizDao.save(quiz);
        questionDao.save(question);
        return question;
    }

    @Override
    public Question updateQuestion(String token, Integer questionId, CreateUpdateQuestionRequest request) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");
        if (questionId == null)
            throw new IllegalArgumentException("Question id cannot be null.");
        if (request == null)
            throw new IllegalArgumentException("Request cannot be null.");

        Question question = questionDao.getById(questionId);
        if (question == null)
            throw new IllegalArgumentException("No question found by this id.");

        String description = request.getDescription();
        Boolean ans = request.getAnswer();

        if (StringUtils.isBlank(description))
            throw new IllegalArgumentException("Description cannot be null.");
        if (ans == null)
            throw new IllegalArgumentException("Answer cannot be null.");

        question.setDescription(description);
        question.setAnswer(ans);

        questionDao.save(question);
        return question;
    }

    @Override
    public List<Question> getQuestions(String token, Integer quizId) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");
        if (quizId == null)
            throw new IllegalArgumentException("Quiz id cannot be null.");

        return questionDao.findAllByQuizId(quizId);
    }

    @Override
    public Question getQuestion(String token, Integer questionId) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");
        if (questionId == null)
            throw new IllegalArgumentException("Question id cannot be null.");
        return questionDao.getById(questionId);
    }
}
