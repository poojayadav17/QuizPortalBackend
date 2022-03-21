package in.OnGrid.QuizPortal.service;

import in.OnGrid.QuizPortal.model.Entity.User;
import in.OnGrid.QuizPortal.model.Entity.UserSession;
import in.OnGrid.QuizPortal.model.dto.CreateUserRequest;
import in.OnGrid.QuizPortal.model.dto.LoginRequest;
import in.OnGrid.QuizPortal.model.dto.UpdateUserRequest;

public interface UserService {
    UserSession createUser(CreateUserRequest request);

    User updateUser(String token, UpdateUserRequest request, Long userId);

    User getUser(String token, Long userId);

    UserSession userLogin(LoginRequest request);

    void userLogout(String token);
}
