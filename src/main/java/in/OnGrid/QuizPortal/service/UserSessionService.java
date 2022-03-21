package in.OnGrid.QuizPortal.service;

import in.OnGrid.QuizPortal.model.Entity.User;
import in.OnGrid.QuizPortal.model.Entity.UserSession;


public interface UserSessionService {

    UserSession createUserToken(User user);

    void UserLogout(String token);

    Boolean checkAuthorization(String token);
}
