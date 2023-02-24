package com.guoran.server.wei.customer.controller;

import com.guoran.server.common.Result;
import com.guoran.server.wei.customer.model.vto.CustomerFilter;
import com.guoran.server.wei.customer.service.CustomerBanlanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Wei
 */
@Api (tags = "客户管理-客户余额信息 ")
@RequestMapping ("/customer")
@RestController
public class CustomerBalanceController {
	@Autowired
	private CustomerBanlanceService customerBalanceService;

	/**
	 * 分页展示余额信息
	 *
	 * @return Result
	 */
	@ApiOperation (value = "分页展示余额信息", notes = "分页展示余额信息")
	@GetMapping ("/balance/{size}/{page}")
	public Result customerBalanceGet(@PathVariable String size, @PathVariable String page) {
		return Result.success(customerBalanceService.findAll(size, page));
	}

	/**
	 * 全局异常测试
	 *
	 * @return Result
	 */
	@ApiOperation (value = "全局异常测试", notes = "全局异常测试")
	@GetMapping ("/balance/errot")
	public Result customerBalanceError() {
		return Result.success("全局异常测试");
	}


	/**
	 * 过滤器测试
	 *
	 * @return Result
	 */
	@ApiOperation (value = "过滤器测试", notes = "过滤器测试")
	@GetMapping ("/balance/filter")
	public Result customerBalanceFilter() {
		return Result.success("过滤器测试");
	}

	/**
	 * 根据用户名来搜素客户
	 *
	 * @return Result
	 */
	@ApiOperation (value = "根据用户名来搜素客户", notes = "根据用户名来搜素客户")
	@PostMapping ("/balance/byName")
	public Result customerBalanceFilter(@RequestBody @Valid CustomerFilter customerFilter) {
		return Result.success(customerBalanceService.findByName(customerFilter.getName()));
	}


}
