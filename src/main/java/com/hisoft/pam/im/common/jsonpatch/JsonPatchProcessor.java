package com.hisoft.pam.im.common.jsonpatch;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
 * JsonPatch处理接口
 */
public interface JsonPatchProcessor {
    /**
     * 移除
     * @param path
     */
	void remove(List<String> path);

    /**
     * 修改
     * @param path
     * @param value
     */
    void replace(List<String> path, JsonNode value);

    /**
     * 新增
     * @param path
     * @param value
     */
    void add(List<String> path, JsonNode value);

    /**
     * 移动
     * @param fromPath
     * @param toPath
     */
    void move(List<String> fromPath, List<String> toPath);

    /**
     * 复制
     * @param fromPath
     * @param toPath
     */
    void copy(List<String> fromPath, List<String> toPath);

    /**
     * 测点
     * @param path
     * @param value
     */
    void test(List<String> path, JsonNode value);
}
