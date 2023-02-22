package com.guoran.server.weicustomer.controller;

import com.guoran.server.common.Result;
import com.guoran.server.weicustomer.service.customerBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Api (tags = "客户管理-预收余额 ")
@RestController
public class customerBankController {
	@Autowired
	private customerBankService CustomerBankService;

	@ApiOperation (value = "客户银行信息", notes = "获取客户银行信息")
	@GetMapping ("/customer/bank")
	public Object CustomerBankGet() {
		return Result.success(CustomerBankService.findAll());
	}

}
