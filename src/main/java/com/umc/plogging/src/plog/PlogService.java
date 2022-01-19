package com.umc.plogging.src.plog;

import com.umc.plogging.config.BaseException;
import com.umc.plogging.src.plog.PlogDao;
import com.umc.plogging.src.plog.dto.*;
import com.umc.plogging.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.umc.plogging.config.BaseResponseStatus.*;

@Service
public class PlogService {

    private final PlogDao plogDao;
    private final JwtService jwtService;

    @Autowired
    public PlogService(PlogDao plogDao, JwtService jwtService) {
        this.plogDao = plogDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public HomeRes getHome(int userIdx) throws BaseException {
        HomeRes homeRes = new HomeRes();
        homeRes.setName(plogDao.getName(userIdx));
        homeRes.setUserImage(plogDao.getUserImage(userIdx));
        List<Timestamp> startTime = plogDao.getStart(userIdx);
        List<Timestamp> endTime= plogDao.getEnd(userIdx);
        long sumTime = 0;
        double distance = 0;
        for (int i =0; i<startTime.size(); i++){
            Date start_date = startTime.get(i);
            Date end_date= endTime.get(i);
            sumTime += (end_date.getTime()-start_date.getTime())/1000;

            distance += Double.parseDouble(plogDao.getDistance(userIdx).get(i));
        }
        long hour = (sumTime/3600);
        long minute = (sumTime-(3600*hour))/60;
        String strTime = Long.toString(hour)+":"+ Long.toString(minute);

        homeRes.setTimeSum(strTime);
        homeRes.setCalendar(plogDao.getCalender(userIdx));

        homeRes.setDistanceSum(Double.toString(distance));
        homeRes.setPlogSum(Integer.toString(startTime.size()));

        plogDao.getStart(userIdx);
        return homeRes;
    }

    @Transactional
    public PlogRes getPlog(int userIdx, int plogIdx) throws BaseException{
        if (plogDao.checkPlog(plogIdx)==0){
            throw new BaseException(NON_EXIST_PLOG);
        }
        PlogRes plogRes = new PlogRes();
        plogRes.setArchiveImg(plogDao.getPlogImage(plogIdx));


        LocalDateTime startTime = plogDao.getStartByPlogIdx(plogIdx).toLocalDateTime();
        LocalDateTime endTime = plogDao.getEndByPlogIdx(plogIdx).toLocalDateTime();
        int timeDiff = ((endTime.getHour()-startTime.getHour())*60+(endTime.getMinute()-startTime.getMinute()));
        plogRes.setDate(startTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + " 플로깅 기록이에요!");

        int kcal = (timeDiff/15)*2*60;
        plogRes.setRecord(startTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd "))+startTime.getDayOfWeek()+
                startTime.format(DateTimeFormatter.ofPattern(" a hh:mm~"))+
                endTime.format(DateTimeFormatter.ofPattern("a hh:mm\n"))+
                plogDao.getDistanceByPlogIdx(plogIdx)+"km, "+timeDiff+"분, "+kcal +"Kcal");


        return plogRes;
    }

}
