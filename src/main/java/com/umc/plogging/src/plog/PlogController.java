package com.umc.plogging.src.plog;

import com.umc.plogging.config.BaseException;
import com.umc.plogging.config.BaseResponse;
import com.umc.plogging.src.plog.PlogService;
import com.umc.plogging.src.plog.dto.*;
import com.umc.plogging.utils.JwtService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class PlogController {

    @Autowired
    private final PlogService plogService;
    @Autowired
    private final JwtService jwtService;

    public PlogController(PlogService plogService, JwtService jwtService){
        this.plogService = plogService;
        this.jwtService = jwtService;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "JWT Token", required = false,dataType = "string"
            ,paramType = "header")})
    @ApiOperation(value = "홈 화면", response = BaseResponse.class)
    @ApiResponses({
            @ApiResponse(code = 1000, message = "성공"),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.")
    })
    @GetMapping("/homes")
    public BaseResponse<HomeRes> getHome(){
        try {
            int userIdx = jwtService.getUserIdx();
            HomeRes homeRes = plogService.getHome(userIdx);
            return new BaseResponse<>(homeRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    @ApiResponses({
            @ApiResponse(code = 1000, message = "성공"),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2020, message = "없는 기록입니다.")
    })
    @ApiOperation(value = "달력 개별 플로깅보기", response = BaseResponse.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "JWT Token", required = false,dataType = "string"
            ,paramType = "header")})
    @GetMapping("/plogs/{plogIdx}")
    public BaseResponse<PlogRes> getPlog(@PathVariable("plogIdx") int plogIdx){
        try {
            int userIdx = jwtService.getUserIdx();
            PlogRes plogRes = plogService.getPlog(userIdx,plogIdx);
            return new BaseResponse<>(plogRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "JWT Token", required = false,dataType = "string"
            ,paramType = "header")})
    @ApiResponses({
            @ApiResponse(code = 1000, message = "성공"),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.")
    })
    @ApiOperation(value = "플로깅 기록 넣기", response = BaseResponse.class)
    @PostMapping("/plogs")
    public BaseResponse postPlog(@RequestBody PostPlogReq postPlogReq){
        try {
            int userIdx = jwtService.getUserIdx();
            plogService.postPlog(userIdx,postPlogReq);
            return new BaseResponse<>();
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "JWT Token", required = false,dataType = "string"
            ,paramType = "header")})
    @ApiResponses({
            @ApiResponse(code = 1000, message = "성공"),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.")
    })
    @ApiOperation(value = "아카이빙 화면", response = BaseResponse.class)
    @GetMapping("/plogs/archive")
    public BaseResponse<GetArchive> getArchive(){
        try {
            int userIdx = jwtService.getUserIdx();
            GetArchive getArchive = plogService.getArchive(userIdx);
            return new BaseResponse<>(getArchive);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
