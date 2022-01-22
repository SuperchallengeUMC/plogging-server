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
import java.time.LocalDate;
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
        double distance = 0;
        for (int i =0; i<startTime.size(); i++){
            distance += Double.parseDouble(plogDao.getDistance(userIdx).get(i));
        }
        List<String> array= plogDao.getEnd(userIdx);
        int sum_hour = 0;
        int sum_minute = 0;
        for (int i = 0 ; i < array.size(); i++){
            String[] time = array.get(i).split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            sum_hour += hour;
            sum_minute += minute;
        }
        sum_hour += sum_minute / 60;
        sum_minute = sum_minute % 60;

        String sum_time = Integer.toString(sum_hour) + ":" + Integer.toString(sum_minute);

        homeRes.setTimeSum(sum_time);

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


        LocalDateTime startTime = plogDao.getStartByPlogIdx(plogIdx).toLocalDateTime();

//        int timeDiff = ((endTime.getHour()-startTime.getHour())*60+(endTime.getMinute()-startTime.getMinute()));
        plogRes.setTitle(startTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + " 플로깅 기록이에요!");

        plogRes.setTime(plogDao.getEndByPlogIdx(plogIdx));
        plogRes.setDistance(plogDao.getDistanceByPlogIdx(plogIdx));
        plogRes.setCalorie(plogDao.getCalorieByPlogIdx(plogIdx));
        plogRes.setPictures(plogDao.getPlogImage(plogIdx));

        plogRes.setDate(startTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));


        int startHour = startTime.getHour();
        int startMinute = startTime.getMinute();

        String endTime = plogDao.getEndByPlogIdx(plogIdx);
        String[] time = endTime.split(":");
        int temp = startMinute + Integer.parseInt(time[0]);

        int endHour = startHour + (temp /60);
        int endMinute = temp % 60;

        plogRes.setRecord(startHour+":"+startMinute+" ~ "+endHour+":"+endMinute);



//        int kcal = (timeDiff/15)*2*60;
//        plogRes.setRecord(startTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd "))+startTime.getDayOfWeek()+
//                startTime.format(DateTimeFormatter.ofPattern(" a hh:mm~"))+
//                endTime.format(DateTimeFormatter.ofPattern("a hh:mm\n"))+
//                plogDao.getDistanceByPlogIdx(plogIdx)+"km, "+timeDiff+"분, "+kcal +"Kcal");


        return plogRes;
    }

    @Transactional
    public void postPlog(int userIdx,PostPlogReq postPlogReq) throws BaseException{
        String minute = postPlogReq.getTime();
        LocalDateTime currentDateTime = LocalDateTime.now();



        System.out.println(minute);
        plogDao.postPlog(userIdx,postPlogReq);
    }

    public GetArchive getArchive(int userIdx) {
        GetArchive getArchive = new GetArchive();

        getArchive.setNickName(plogDao.getName(userIdx));
        getArchive.setArchiveImg(plogDao.getArchiveImg(userIdx));

        return getArchive;
    }
}
