package in.OnGrid.QuizPortal.dao;

import in.OnGrid.QuizPortal.model.Entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionDao extends JpaRepository<UserSession, Long> {
    UserSession findByToken(String token);
}
