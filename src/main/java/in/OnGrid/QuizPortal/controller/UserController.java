package in.OnGrid.QuizPortal.controller;

import in.OnGrid.QuizPortal.model.Entity.User;
import in.OnGrid.QuizPortal.model.Entity.UserSession;
import in.OnGrid.QuizPortal.model.dto.BaseResponse;
import in.OnGrid.QuizPortal.model.dto.CreateUserRequest;
import in.OnGrid.QuizPortal.model.dto.LoginRequest;
import in.OnGrid.QuizPortal.model.dto.UpdateUserRequest;
import in.OnGrid.QuizPortal.service.UserService;
import in.OnGrid.QuizPortal.service.UserSessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/")
    public BaseResponse<UserSession> createUser(@RequestBody CreateUserRequest request) {
        return new BaseResponse<>(HttpStatus.OK.value(), userService.createUser(request));
    }

    @PostMapping(path = "/{userId}/")
    public BaseResponse<User> updateUser(@RequestHeader("Authorization") String token,
                                         @RequestBody UpdateUserRequest request,
                                         @PathVariable("userId") Long userId) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User Unauthorized");

        User user = userService.updateUser(token, request, userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "Success", user);
    }

    @GetMapping(path = "/{userId}/profile")
    public BaseResponse<User> getUser(@RequestHeader("Authorization") String token,
                                      @PathVariable("userId") Long userId) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User Unauthorized");

        User user = userService.getUser(token, userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "Success", user);
    }

    @PostMapping(path = "/login")
    public BaseResponse<UserSession> userLogin(@RequestBody LoginRequest request) {
        UserSession token = userService.userLogin(request);
        return new BaseResponse<>(HttpStatus.OK.value(), "Success", token);
    }

    @PostMapping(path = "/logout")
    public BaseResponse<?> userLogout(@RequestHeader("Authorization") String token) throws AccessDeniedException {
        if (StringUtils.isBlank(token))
            throw new AccessDeniedException("User Unauthorized");

        userService.userLogout(token);
        return new BaseResponse<>(HttpStatus.OK.value(), "Successfully Logged out.");
    }
}
