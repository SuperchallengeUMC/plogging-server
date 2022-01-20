package com.umc.plogging.src.crews;

import com.umc.plogging.src.crews.model.crew.*;
import com.umc.plogging.src.crews.model.member.*;
import com.umc.plogging.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;;
import com.umc.plogging.config.BaseException;
import com.umc.plogging.config.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 해당 지역 크루 조회 API
     * [GET] /crews?region=""
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetCrewsRes>> getCrews(@RequestParam(required = false) String region) {
        try {
            if (region == null) {
                List<GetCrewsRes> getCrewsRes = crewProvider.getCrews();
                return new BaseResponse<>(getCrewsRes);
            }

            List<GetCrewsRes> getCrewsRes = crewProvider.getCrewsByRegion(region);
            return new BaseResponse<>(getCrewsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가입한 크루 조회 API
     * [GET] /crews/mycrews?status=""
     */
    @ResponseBody
    @GetMapping("/mycrews")
    public BaseResponse<List<GetCrewsRes>> getMyCrews(@RequestParam(required = false) String status) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if (status == null) {
                // status queryString을 추가해주세요라는 에러 메세지
            }

            List<GetCrewsRes> getCrewsRes = crewProvider.getCrewsByStatus(status, userIdxByJwt);
            return new BaseResponse<>(getCrewsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 크루 하나 조회 API
     * [GET] /crews/:userIdx
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{crewIdx}")
    public BaseResponse<GetCrewRes> getCrew(@PathVariable("crewIdx") int crewIdx) {
        try {
            GetCrewRes getCrewRes = crewProvider.getCrew(crewIdx);
            return new BaseResponse<>(getCrewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 크루 소속 크루원 조회 API
     * [GET] /crews/:userIdx
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{crewIdx}/member")
    public BaseResponse<List<GetMemberRes>> getMembers(@PathVariable("crewIdx") int crewIdx) {

        try {
            List<GetMemberRes> getMembersRes = crewProvider.getMembers(crewIdx);
            return new BaseResponse<>(getMembersRes);
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
     * [DELETE] /crews/:crewIdx/:userIdx
     */
    @ResponseBody
    @DeleteMapping("/{crewIdx}/{userIdx}")
    public BaseResponse<DeleteMemberRes> DeleteMember(@PathVariable("crewIdx") int crewIdx, @PathVariable("userIdx") int userIdx) {
        try {
            DeleteMemberRes deleteMemberRes = crewService.deleteMember(crewIdx, userIdx);
            return new BaseResponse<>(deleteMemberRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
