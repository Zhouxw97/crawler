package com.wantwant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wantwant.entity.dto.JdItemQueryDto;
import com.wantwant.entity.vo.JdItemVo;
import com.wantwant.service.JdItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 京东商品品类信息 controller
 *
 * @author zhouxiaowen
 * @date 2021-04-26 12:08
 * @version 1.0
 */

@Api(tags = "京东商品品类信息相关接口")
@RestController
@RequestMapping("/jdItem")
public class JdItemController {

    @Autowired
    private JdItemService jdItemService;


    /**
     * 分页查询数据
     *
     * @param jdItemQueryDto
     * @param currentPage
     * @param pageSize
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.wantwant.entity.vo.JdItemVo>
     * @author: zhouxiaowen
     * @date: 2021-04-26 14:18
     */
    @ApiOperation(value = "分页查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "页数", dataType = "int", paramType = "query", example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", example = "10") })
    @GetMapping
    public IPage<JdItemVo> getJdItemByPage(JdItemQueryDto jdItemQueryDto,
                                           @RequestParam(required = false, defaultValue = "1") int currentPage,
                                           @RequestParam(required = false, defaultValue = "10") int pageSize){
        Page<JdItemVo> page = new Page<>(currentPage, pageSize);
        return jdItemService.getJdItemByPage(page,jdItemQueryDto);
    }


}
