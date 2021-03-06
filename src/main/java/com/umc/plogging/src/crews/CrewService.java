package com.umc.plogging.src.crews;

import com.umc.plogging.config.BaseException;
import com.umc.plogging.src.crews.model.crew.*;
import com.umc.plogging.src.crews.model.member.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.plogging.config.BaseResponseStatus.*;

@Service
public class CrewService {
    private final CrewDao crewDao;

    @Autowired
    public CrewService(CrewDao crewDao) {
        this.crewDao = crewDao;
    }

    // 크루 생성(POST)
    public PostCrewRes createCrew(PostCrewReq postCrewReq, int userIdxByJwt) throws BaseException {
        int crewIdx = crewDao.createCrew(postCrewReq, userIdxByJwt);
        return new PostCrewRes(crewIdx);
        /*
        try {
            int crewIdx = crewDao.createCrew(postCrewReq, userIdxByJwt);
            return new PostCrewRes(crewIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

         */
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
    public void deleteMember(int crewIdx, int userIdx) throws BaseException {
        try {
            int result = crewDao.deleteMember(crewIdx, userIdx);
            // int memberInCrew = crewDao.memberInCrew(crewIdx, userIdx);

            if (result == 0) {
                throw new BaseException(FAILED_TO_DELETE);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
