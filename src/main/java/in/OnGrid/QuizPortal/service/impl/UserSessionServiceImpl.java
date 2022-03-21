package in.OnGrid.QuizPortal.service.impl;

import in.OnGrid.QuizPortal.dao.UserSessionDao;
import in.OnGrid.QuizPortal.model.Entity.User;
import in.OnGrid.QuizPortal.model.Entity.UserSession;
import in.OnGrid.QuizPortal.service.UserSessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class UserSessionServiceImpl implements UserSessionService {
    @Autowired
    UserSessionDao userSessionDao;

    @Override
    public UserSession createUserToken(User user) {
        if(user == null)
            throw new IllegalArgumentException("No user found.");

        UserSession userSession = new UserSession();

        String token = UUID.randomUUID().toString();
        Date dateTime = new Date();

        userSession.setToken(token);
        userSession.setSignInTime(dateTime);
        userSession.setUser(user);

        userSessionDao.save(userSession);
        return userSession;
    }

    @Override
    public void UserLogout(String token) {
        if(!checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");

        UserSession loggedInUser = userSessionDao.findByToken(token);
        if (loggedInUser == null)
            throw new IllegalArgumentException("No user present with this token.");

        if (loggedInUser.getSignOutTime() != null)
            throw new IllegalArgumentException("User is already logout.");

        Date dateTime = new Date();
        loggedInUser.setSignOutTime(dateTime);
        userSessionDao.save(loggedInUser);
    }

    @Override
    public Boolean checkAuthorization(String token) {
        if(StringUtils.isBlank(token))
            throw new AccessDeniedException("User Unauthorized.");
        UserSession userSession = userSessionDao.findByToken(token);
        return userSession != null && userSession.getSignOutTime() == null;
    }
}