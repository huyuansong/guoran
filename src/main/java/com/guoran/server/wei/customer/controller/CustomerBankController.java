package com.guoran.server.wei.customer.controller;

import com.guoran.server.common.Result;
import com.guoran.server.wei.customer.service.CustomerBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wei
 */
@Api (tags = "客户管理-客户银行信息 ")
@RequestMapping ("/customer")
@RestController
public class CustomerBankController {
	@Autowired
	private CustomerBankService customerBankService;

	/**
	 * @param size
	 * @param page
	 * @return Result
	 */
	@ApiOperation (value = "客户银行信息", notes = "获取客户银行信息")
	@GetMapping ("/bank/${size}/${page}")
	public Result customerBankGet(@PathVariable String size, @PathVariable String page) {
		return Result.success(customerBankService.findAll(size, page));
	}


}
