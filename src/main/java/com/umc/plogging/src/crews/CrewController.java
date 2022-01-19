package com.umc.plogging.src.crews;

import com.umc.plogging.src.crews.model.*;
import com.umc.plogging.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;;
import com.umc.plogging.config.BaseException;
import com.umc.plogging.config.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.umc.plogging.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/crews")

public class CrewController {
    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    @Autowired
    private final CrewProvider crewProvider;
    @Autowired
    private final CrewService crewService;
    private final JwtService jwtService;


    public CrewController(CrewProvider crewProvider, CrewService crewService, JwtService jwtService) {
        this.crewProvider = crewProvider;
        this.crewService = crewService;
        this.jwtService = jwtService;
    }
    // ******************************************************************************

    /**
     * 크루 개설 API
     * [POST] /crews
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCrewRes> createCrew(@RequestBody PostCrewReq postCrewReq) {
        try {
            PostCrewRes postCrewRes = crewService.createCrew(postCrewReq);
            return new BaseResponse<>(postCrewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 모든 크루 조회 API
     * [GET] /crews
     * 또는
     * 해당 지역, 크루원, 상태를 가지는 크루 API
     * [GET] /crews?region="" /crews?crewidx=""&status=""
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetCrewRes>> getCrews(@RequestParam(required = false) String region) {
        try {
            if (region == null) {
                List<GetCrewRes> getCrewsRes = crewProvider.getCrews();
                return new BaseResponse<>(getCrewsRes);
            }

            List<GetCrewRes> getCrewsRes = crewProvider.getCrewsByRegion(region);
            return new BaseResponse<>(getCrewsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 크루 가입 API
     * [POST] /crews/:crewIdx
     */
    // Body
    @ResponseBody
    @PostMapping("/{crewIdx}")
    public BaseResponse<PostMemberRes> joinCrew(@PathVariable("crewIdx") int crewIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            PostMemberRes postMemberRes = crewService.joinCrew(crewIdx, userIdxByJwt);
            return new BaseResponse<>(postMemberRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 크루 탈퇴 API
     * [DELETE] /crews/:crewIdx
     */
    /*
    @ResponseBody
    @DeleteMapping("/{crewIdx}")
    public BaseResponse<DeleteCrewRes> DeleteCrew(@PathVariable("crewIdx") int crewIdx) {
        try {
            DeleteCrewRes deleteCrewRes = crewService.deleteCrew(crewIdx);
            return new BaseResponse<>(deleteCrewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
     */
}
