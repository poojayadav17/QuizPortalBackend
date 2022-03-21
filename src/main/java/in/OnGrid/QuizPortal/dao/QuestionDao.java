package in.OnGrid.QuizPortal.dao;

import in.OnGrid.QuizPortal.model.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findAllByQuizId(int quizId);
}
