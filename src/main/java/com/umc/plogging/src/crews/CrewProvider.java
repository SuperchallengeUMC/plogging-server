package com.umc.plogging.src.crews;

import com.umc.plogging.src.crews.model.crew.*;
import com.umc.plogging.src.crews.model.member.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umc.plogging.config.BaseException;

import java.util.List;
import static com.umc.plogging.config.BaseResponseStatus.*;

@Service
public class CrewProvider {
    private final CrewDao crewDao;

    @Autowired
    public CrewProvider(CrewDao crewDao) {
        this.crewDao = crewDao;
    }

    // 전체 크루 조회
    public List<GetCrewsRes> getCrews() throws BaseException {
        try {
            List<GetCrewsRes> getCrewRes = crewDao.getCrews();
            return getCrewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetCrewRes getCrew(int crewIdx) throws BaseException {
        GetCrewRes getCrewRes = crewDao.getCrew(crewIdx);
        return getCrewRes;
        /*
        try {
            GetCrewRes getCrewRes = crewDao.getCrew(crewIdx);
            return getCrewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

         */
    }

    public List<GetMemberRes> getMembers(int crewIdx) throws BaseException {
        try {
            List<GetMemberRes> getMembersRes = crewDao.getMembers(crewIdx);
            return getMembersRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 region의 Crew들의 정보 조회
    public List<GetCrewsRes> getCrewsByRegion(String region) throws BaseException {
        try {
            List<GetCrewsRes> getCrewsRes = crewDao.getCrewsByRegion(region);
            return getCrewsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 가입한 Crew들의 정보 조회
    public List<GetCrewsRes> getCrewsByStatus(char status, int userIdxByJwt) throws BaseException {
        try {
            List<GetCrewsRes> getCrewsRes = crewDao.getCrewsByStatus(status, userIdxByJwt);
            return getCrewsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
