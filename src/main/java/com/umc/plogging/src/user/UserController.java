package com.umc.plogging.src.user;


import com.umc.plogging.config.BaseException;
import com.umc.plogging.config.BaseResponse;
import com.umc.plogging.src.user.dto.*;
import com.umc.plogging.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.umc.plogging.config.BaseResponseStatus.REQUEST_ERROR;

@RestController
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/user")
    public BaseResponse createUser(@RequestBody PostUserReq postUserReq){
        if(postUserReq.getNickName() == null || postUserReq.getPassword()==null){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try{
            userService.createUser(postUserReq);
            return new BaseResponse();
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
