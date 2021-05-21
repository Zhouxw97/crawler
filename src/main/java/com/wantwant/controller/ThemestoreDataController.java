package com.wantwant.controller;

import com.wantwant.annotation.SignatureValidation;
import com.wantwant.entity.dto.ThemestoreDataDto;
import com.wantwant.service.ThemestoreDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
*
* @author zhouxiaowen
* @since 2021-05-21
*/
@Api(tags = "主题门店业绩相关接口")
@RestController
@RequestMapping("/themestoreData")
public class ThemestoreDataController {

	@Autowired
	private ThemestoreDataService themestoreDataService;

	/**
	 * 新增主题门店业绩
	 *
	 * @param themestoreDataDto 需要保存的Dto
	 * @return int
	 * @author zhouxiaowen
	 * @since 2021-05-21
	 */
	@SignatureValidation
	@ApiOperation(value = "新增主题门店业绩")
	@ApiImplicitParam(name = "themestoreDataDto", value = "请求体", dataType = "ThemestoreDataDto", paramType = "body")
	@PostMapping
	public int insert(@Valid @RequestBody ThemestoreDataDto themestoreDataDto) {
		return themestoreDataService.insert(themestoreDataDto);
	}

}
