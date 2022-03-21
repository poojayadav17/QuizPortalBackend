package in.OnGrid.QuizPortal.service.impl;

import in.OnGrid.QuizPortal.dao.QuestionDao;
import in.OnGrid.QuizPortal.dao.QuizDao;
import in.OnGrid.QuizPortal.model.Entity.Question;
import in.OnGrid.QuizPortal.model.Entity.Quiz;
import in.OnGrid.QuizPortal.model.dto.CreateUpdateQuizRequest;
import in.OnGrid.QuizPortal.model.dto.SubmitQuizRequest;
import in.OnGrid.QuizPortal.model.dto.SubmitQuizResponse;
import in.OnGrid.QuizPortal.model.dto.QuizSubmitAnswer;
import in.OnGrid.QuizPortal.service.QuizService;
import in.OnGrid.QuizPortal.service.UserSessionService;
import org.springframework.security.AccessDeniedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    UserSessionService userSessionService;

    @Override
    public Quiz createQuiz(String token, CreateUpdateQuizRequest request) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");
        if (request == null)
            throw new IllegalArgumentException("Request cannot be null.");

        String description = request.getDescription();
        String image = request.getImage();

        if (StringUtils.isBlank(description))
            throw new IllegalArgumentException("Description cannot be null.");
        if (StringUtils.isBlank(image))
            throw new IllegalArgumentException("Image cannot be null.");

        Quiz quiz = new Quiz();
        quiz.setDescription(description);
        quiz.setImage(image);

        quizDao.save(quiz);
        return quiz;
    }

    @Override
    public Quiz updateQuiz(String token, CreateUpdateQuizRequest request, Integer quizId) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");
        if (request == null)
            throw new IllegalArgumentException("Request cannot be null");

        String description = request.getDescription();
        String image = request.getImage();

        if (StringUtils.isBlank(description))
            throw new IllegalArgumentException("Description cannot be null.");
        if (StringUtils.isBlank(image))
            throw new IllegalArgumentException("Image cannot be null.");

        if (quizId == null)
            throw new IllegalArgumentException("Quiz id cannot be null.");

        Quiz quiz = quizDao.getById(quizId);
        if (quiz == null)
            throw new IllegalArgumentException("No quiz found by this id.");

        quiz.setDescription(description);
        quiz.setImage(image);

        quizDao.save(quiz);
        return quiz;
    }

    @Override
    public List<Quiz> getQuizzes(String token) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");

        return quizDao.findAll();
    }

    @Override
    public Quiz getQuiz(Integer quizId, String token) {
        if (quizId == null)
            throw new IllegalArgumentException("Quiz id cannot be null.");
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");
        return quizDao.getById(quizId);
    }

    @Override
    public SubmitQuizResponse getResult(String token, Integer quizId, SubmitQuizRequest request) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");
        if (quizId == null)
            throw new IllegalArgumentException("Quiz id cannot be null.");
        if (request == null)
            throw new IllegalArgumentException("Request cannot be null.");

        List<QuizSubmitAnswer> submitAnswer = request.getSubmitAnswers();
        if (submitAnswer == null)
            throw new IllegalArgumentException("Answers cannot be null.");

        Quiz quiz = quizDao.getById(quizId);
        if (quiz == null)
            throw new IllegalArgumentException("No quiz found by this id.");

        List<Question> questionList = quiz.getQuestionList();
        if (questionList == null)
            throw new IllegalArgumentException("Question list is null.");

        Map<Integer, Boolean> map = new HashMap<>();

        for (Question question : questionList) {
            if (question.getId() == null || question.getAnswer() == null)
                throw new IllegalArgumentException("Question id or answer cannot be null.");
            map.put(question.getId(), question.getAnswer());
        }

        int score = 0, wrong = 0;
        for (QuizSubmitAnswer listOfAnswers : submitAnswer) {
            Integer quesId = listOfAnswers.getId();
            Boolean chosenAnswer = listOfAnswers.getAnswer();

            if (quesId == null || chosenAnswer == null)
                throw new IllegalArgumentException("Question id or answer cannot be null.");

            if (map.get(quesId).equals(chosenAnswer))
                score++;
            else
                wrong++;
        }

        SubmitQuizResponse result = new SubmitQuizResponse();
        result.setScore(score);
        result.setWrong(wrong);
        return result;
    }
}
