package com.guoran.server.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
	private String id;
	private String customerId;
	private String userName;
	private String taxpayerIdentification;
	private String stageFirst;
	private String compensate;
	private String collection;
}
