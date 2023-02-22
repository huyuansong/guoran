package com.guoran.server.weicustomer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class customer_bank {
	private String id;
	private String customer_id;
	private String account_name;
	private String bank_account;
	private String open_account_bank;
	private String inter_bank_number;
	private String default_account;
	private String create_by;
	private String create_time;
	private String update_by;
	private String update_time;
	private String pk_cust;
	private String pk_id;
}
