package com.umc.plogging.src.crews;

import com.umc.plogging.config.BaseException;
import com.umc.plogging.src.crews.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.plogging.config.BaseResponseStatus.*;

@Service
public class CrewService {
    private final CrewDao crewDao;
    private final CrewProvider crewProvider;

    @Autowired
    public CrewService(CrewDao crewDao, CrewProvider crewProvider) {
        this.crewDao = crewDao;
        this.crewProvider = crewProvider;
    }

    // 크루 생성(POST)
    public PostCrewRes createCrew(PostCrewReq postCrewReq) throws BaseException {
        try {
            int crewIdx = crewDao.createCrew(postCrewReq);
            return new PostCrewRes(crewIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 크루 가입(POST)
    public PostMemberRes joinCrew(int crewIdx, int userIdxByJwt) throws BaseException {
        try {
            int memberIdx = crewDao.joinCrew(crewIdx, userIdxByJwt);
            return new PostMemberRes(memberIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 크루 탈퇴 (DELETE)
    /*
    public DeleteCrewRes deleteCrew(int crewIdx) throws BaseException {
        try {
            DeleteCrewRes deleteCrewRes = crewDao.deleteCrew(crewIdx);
            return deleteCrewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
     */

}
