package com.guoran.server.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
