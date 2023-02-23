package com.guoran.server.wei.customer.controller;

import com.guoran.server.common.Result;
import com.guoran.server.wei.customer.service.CustomerBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wei
 */
@Api (tags = "客户管理-客户银行信息 ")
@RestController
public class CustomerBankController {
	@Autowired
	private CustomerBankService customerBankService;

	@ApiOperation (value = "客户银行信息", notes = "获取客户银行信息")
	@GetMapping ("/customer/bank")
	public Result customerBankGet() {
		return Result.success(customerBankService.findAll());
	}

	@ApiOperation (value = "test", notes = "test")
	@GetMapping ("/customer/bank2")
	public Result customerBankGet2() {
		return Result.success("获取成功");
	}

}
