package com.guoran.server.wei.customer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wei
 */
@Api (tags = "用户管理")
@RestController
public class UserController {
	@ApiOperation (value = "用户管理", notes = "用户管理")
	@GetMapping ("/user")
	public String user() {
		return "hello";
	}
}
