package com.guoran.server.example.balance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class balance {
	private String id;
	private String customer_id;
	private String user_name;
	private String taxpayer_identification;
	private String stage_first;
	private String compensate;
	private String collection;
}
