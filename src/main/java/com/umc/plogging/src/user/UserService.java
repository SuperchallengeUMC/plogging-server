package com.umc.plogging.src.user;


import com.umc.plogging.config.BaseException;
import com.umc.plogging.config.secret.Secret;
import com.umc.plogging.src.user.dto.*;
import com.umc.plogging.utils.AES128;
import com.umc.plogging.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.umc.plogging.config.BaseResponseStatus.*;

@Service
public class UserService {
    private final UserDao userDao;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public void createUser(PostUserReq postUserReq) throws BaseException {
        if (userDao.checkNickName(postUserReq.getNickName()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_NAME);
        }
        String pwd;
        try {
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);
            userDao.createUser(postUserReq);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
    }

    @Transactional
    public PostLoginRes loginUser(PostLoginReq postLoginReq) throws BaseException{
        if(userDao.checkNickName(postLoginReq.getNickName()) == 0){
            throw new BaseException(FAILED_TO_LOGIN);
        }
        String realpw;
        LoginInfo loginInfo;
        loginInfo = userDao.checkNickNameAccount(postLoginReq.getNickName());
        try{
            realpw = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(loginInfo.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        PostLoginRes postLoginRes = new PostLoginRes();
        if (postLoginReq.getPassword().equals(realpw)){

            String jwt = jwtService.createJwt(loginInfo.getUserIdx());
            postLoginRes.setJwt(jwt);
            postLoginRes.setUserIdx(loginInfo.getUserIdx());
            return postLoginRes;
        }
        else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }
}
