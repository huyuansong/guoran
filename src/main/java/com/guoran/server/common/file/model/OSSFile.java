package com.guoran.server.common.file.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OSSFile  {

	private Long ossId;

	private String fileName;

	private String fileUrl;

	private Long fileSize;
}
