package com.guoran.server.wei.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBank {
	private String id;
	private String customerId;
	private String accountName;
	private String bankAccount;
	private String openAccountBank;
	private String interBankNumber;
	private String defaultAccount;
	private String createBy;
	private String createTime;
	private String updateBy;
	private String updateTime;
	private String pkCust;
	private String pkId;
}
