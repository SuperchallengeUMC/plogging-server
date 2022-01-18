package com.umc.plogging.src.crews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umc.plogging.config.BaseException;
import com.umc.plogging.src.crews.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import static com.umc.plogging.config.BaseResponseStatus.*;

@Service
public class CrewProvider {
    private final CrewDao crewDao;

    @Autowired
    public CrewProvider(CrewDao crewDao) {
        this.crewDao = crewDao;
    }

    // Crew들의 정보를 조회
    public List<GetCrewRes> getCrews() throws BaseException {
        try {
            List<GetCrewRes> getCrewRes = crewDao.getCrews();
            return getCrewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 region의 Crew들의 정보 조회
    public List<GetCrewRes> getCrewsByRegion(String region) throws BaseException {
        try {
            List<GetCrewRes> getCrewsRes = crewDao.getCrewsByRegion(region);
            return getCrewsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
