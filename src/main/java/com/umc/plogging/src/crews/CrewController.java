package com.umc.plogging.src.crews;

import com.umc.plogging.src.crews.model.crew.*;
import com.umc.plogging.src.crews.model.member.*;
import com.umc.plogging.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;;
import com.umc.plogging.config.BaseException;
import com.umc.plogging.config.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.umc.plogging.config.BaseResponseStatus.DATABASE_ERROR;
import static com.umc.plogging.config.BaseResponseStatus.NEED_MORE_INFO;

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
    @ApiOperation(value = "크루 개설", notes = "크루를 만들고, 만든 사람이 크루장이 됩니다.")
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCrewRes> createCrew(@RequestBody PostCrewReq postCrewReq) {
        if(postCrewReq.getName() == null || postCrewReq.getDescription() == null || Integer.valueOf(postCrewReq.getHowmany()) == null || postCrewReq.getTargetDay() == null || postCrewReq.getContact() == null || postCrewReq.getRegion() == null) {
            return new BaseResponse<>(NEED_MORE_INFO);
        }
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            PostCrewRes postCrewRes = crewService.createCrew(postCrewReq, userIdxByJwt);
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
    @ApiOperation(value = "크루 조회", notes = "모든 크루 조회, query string을 이용하여 지역별 크루 조회")
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
    @ApiOperation(value = "가입한 크루 조회", notes = "query string으로 status(T,F)를 구분")
    @ResponseBody
    @GetMapping("/mycrews")
    public BaseResponse<List<GetCrewsRes>> getMyCrews(@RequestParam(required = false) char status) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if (status == 0) { // 이 부분 에러 나서 수정 필요 ! ! !
                throw new BaseException(DATABASE_ERROR);
            }

            List<GetCrewsRes> getCrewsRes = crewProvider.getCrewsByStatus(status, userIdxByJwt);
            return new BaseResponse<>(getCrewsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 크루 하나 조회 API
     * [GET] /crews/:crewIdx
     */
    // Path-variable
    @ApiOperation(value = "하나의 크루 조회", notes = "crewIdx를 이용하여 크루 조회, 크루 상세 페이지에서 사용")
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
     * [GET] /crews/:crewIdx/member
     */
    // Path-variable
    @ApiOperation(value = "크루 소속 크루원 조회", notes = "crewIdx를 통해 그 크루에 속한 크루원 리스트를 조회")
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
    @ApiOperation(value = "크루 가입", notes = "crewIdx를 통해 해당 크루 가입")
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
    @ApiOperation(value = "크루 탈퇴", notes = "crewIdx를 이용하여 해당 크루를 탈퇴")
    @ResponseBody
    @DeleteMapping("/{crewIdx}")
    public BaseResponse<String> DeleteMember(@PathVariable("crewIdx") int crewIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            crewService.deleteMember(crewIdx, userIdxByJwt);
            String result = "크루를 탈퇴하였습니다.";

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
