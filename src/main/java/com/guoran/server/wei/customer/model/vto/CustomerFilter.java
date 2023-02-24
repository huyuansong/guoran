package com.guoran.server.wei.customer.model.vto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFilter {
	@NotEmpty (message = "客户名称不能为空")
	@Length (min = 1, max = 20, message = "客户名称长度必须在1-20之间")
	private String name;


}
