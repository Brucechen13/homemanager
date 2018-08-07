package com.cc.springdemo.controller;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(description = "这是一个控制器的描述 ")
public class SwapperUserInfoController {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SwapperUserInfoController.class);
    private String no;
    private String kind;
    private String name;

    @ApiOperation(value = "测试接口", notes = "测试接口描述")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID",
                    required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "user_name", value = "宠物",
                    required = true, dataType = "PetController")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求完成"),
            @ApiResponse(code = 400, message = "请求参数错误")
    })
    @RequestMapping(path = "/index/{id}", method = RequestMethod.PUT)
    public SwapperUserInfoController index1(@PathVariable("id") String id,
                                            @RequestBody SwapperUserInfoController pet) {
        return pet;
    }
}
