package in.OnGrid.QuizPortal.service.impl;

import in.OnGrid.QuizPortal.dao.UserDao;
import in.OnGrid.QuizPortal.dao.UserSessionDao;
import in.OnGrid.QuizPortal.model.Entity.User;
import in.OnGrid.QuizPortal.model.Entity.UserSession;
import in.OnGrid.QuizPortal.model.dto.CreateUserRequest;
import in.OnGrid.QuizPortal.model.dto.LoginRequest;
import in.OnGrid.QuizPortal.model.dto.UpdateUserRequest;
import in.OnGrid.QuizPortal.service.UserService;
import in.OnGrid.QuizPortal.service.UserSessionService;
import in.OnGrid.QuizPortal.util.Gender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    UserSessionService userSessionService;
    @Autowired
    UserSessionDao userSessionDao;

    @Override
    public UserSession createUser(CreateUserRequest request) {
        if (request == null)
            throw new IllegalArgumentException("Request cannot be blank.");
        String email = request.getEmail();
        String name = request.getName();
        String mobile = request.getMobile();
        String password = request.getPassword();

        if (StringUtils.isBlank(email))
            throw new IllegalArgumentException("Email cannot be blank.");
        if (StringUtils.isBlank(name))
            throw new IllegalArgumentException("Name cannot be blank.");
        if (StringUtils.isBlank(mobile))
            throw new IllegalArgumentException("Mobile cannot be blank.");
        if (StringUtils.isBlank(password))
            throw new IllegalArgumentException("Password cannot be blank.");

        if (!email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+.([a-z]{2,8})([.a-z]{2,8})?$"))
            throw new IllegalArgumentException("Invalid Email eg : (yourName)@(domain).(extension)(.in).");
        if (!mobile.matches("\\d{10}"))
            throw new IllegalArgumentException("Mobile no. should be of 10 digits.");
        if (password.length() < 8)
            throw new IllegalArgumentException("Password should be of minimum length 8.");
        if (userDao.findByEmail(email) != null)
            throw new IllegalArgumentException("Email already registered.");

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setMobile(mobile);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        userDao.save(user);
        return userSessionService.createUserToken(user);
    }

    @Override
    public User updateUser(String token, UpdateUserRequest request, Long userId) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");
        if (request == null)
            throw new IllegalArgumentException("Request cannot be blank.");

        String name = request.getName();
        String mobile = request.getMobile();
        Date dob = request.getDob();
        Gender gender = request.getGender();

        if (StringUtils.isBlank(name))
            throw new IllegalArgumentException("Name cannot be blank.");
        if (StringUtils.isBlank(mobile))
            throw new IllegalArgumentException("Mobile cannot be blank.");
        if (dob == null)
            throw new IllegalArgumentException("D.O.B cannot be blank.");

        Date dt = new Date();

        if (!mobile.matches("\\d{10}"))
            throw new IllegalArgumentException("Mobile no. should be of 10 digits.");
        if (dob.compareTo(dt) > 0)
            throw new IllegalArgumentException("Please enter a valid D.O.B.");
        if (userId == null)
            throw new IllegalArgumentException("User Id cannot be null.");

        User user = userSessionDao.findByToken(token).getUser();
        if (user == null)
            throw new IllegalArgumentException("No user found by this token.");
        if (user.getId() != userId)
            throw new IllegalArgumentException("User Id is invalid.");

        user.setName(name);
        user.setMobile(mobile);
        user.setDob(dob);
        user.setGender(gender);

        userDao.save(user);
        return user;
    }

    @Override
    public User getUser(String token, Long userId) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User unauthorized.");
        if (userId == null)
            throw new IllegalArgumentException("User id cannot be null.");

        User user = userSessionDao.findByToken(token).getUser();
        if (user == null)
            throw new IllegalArgumentException("No user found by this token.");
        if (user.getId() != userId)
            throw new IllegalArgumentException("User Id is invalid.");
        return user;
    }

    @Override
    public UserSession userLogin(LoginRequest request) {
        if (request == null)
            throw new IllegalArgumentException("Request cannot be blank.");
        String email = request.getEmail();
        String password = request.getPassword();

        if (StringUtils.isBlank(email))
            throw new IllegalArgumentException("Email cannot be blank.");
        if (StringUtils.isBlank(password))
            throw new IllegalArgumentException("Password cannot be blank.");

        if (!email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+.([a-z]{2,8})([.a-z]{2,8})?$"))
            throw new IllegalArgumentException("Invalid Email.");

        User user = userDao.findByEmail(email);
        if (user == null)
            throw new IllegalArgumentException("Invalid Email.");

        String savedPassword = user.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

        if (!bCryptPasswordEncoder.matches(password, savedPassword))
            throw new IllegalArgumentException("Your password is incorrect.");
        return userSessionService.createUserToken(user);
    }

    @Override
    public void userLogout(String token) {
        if (!userSessionService.checkAuthorization(token))
            throw new AccessDeniedException("User Unauthorized");
        userSessionService.UserLogout(token);
    }
}
