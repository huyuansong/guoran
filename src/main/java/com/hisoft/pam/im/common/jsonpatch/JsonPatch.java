package com.hisoft.pam.im.common.jsonpatch;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;


/**
 * 此类接受JsonPatchProcessor的实现用为处理依据
 * @author machao
 *
 */
public class JsonPatch {
	private static final DecodePathFunction DECODE_PATH_FUNCTION = new DecodePathFunction();
	private final static class DecodePathFunction implements Function<String, String> {
        @Override
        public String apply(String path) {
            return path.replaceAll("~1", "/").replaceAll("~0", "~"); 
        }
    }
	private JsonNode patch;
	private JsonPatchProcessor processor;
	public JsonPatch(JsonNode patch,JsonPatchProcessor processor){
		this.patch=patch;
		this.processor=processor;
	}
	/**
	 * 开始处理JsonPatch
	 * @throws IllegalAccessException 
	 */
	public void apply() throws IllegalAccessException{
		if (!patch.isArray()){
            throw new ClassCastException("JSON Patch必须为数组");
		}
		Iterator<JsonNode> operations = patch.iterator();//patch中的所有操作
		while (operations.hasNext()) {
			JsonNode jsonNode = operations.next();
            if (!jsonNode.isObject()) throw new ClassCastException("JSON Patch必须是object");
            //操作名
            String operation=getPatchAttr(jsonNode, OperationAttrEnum.OP.value())
            				.toString().replaceAll("\"", "");
            //jsonPointer 路径
            List<String> path = getPath(getPatchAttr(jsonNode, OperationAttrEnum.PATH.value()));
            switch (operation) {
	            case "remove": {
	                processor.remove(path);
	                break;
	            }
	            case "add": {
                    JsonNode value = getPatchAttr(jsonNode, OperationAttrEnum.VALUE.value());
                    processor.add(path, value);
                    break;
                }
                case "replace": {
                    JsonNode value = getPatchAttr(jsonNode, OperationAttrEnum.VALUE.value());
                    processor.replace(path, value);
                    break;
                }

                case "move": {
                    List<String> fromPath = getPath(getPatchAttr(jsonNode, OperationAttrEnum.FROM.value()));
                    processor.move(fromPath, path);
                    break;
                }

                case "copy": {
                    List<String> fromPath = getPath(getPatchAttr(jsonNode, OperationAttrEnum.FROM.value()));
                    processor.copy(fromPath, path);
                    break;
                }

                case "test": {
                    JsonNode value=getPatchAttr(jsonNode, OperationAttrEnum.VALUE.value());
                    processor.test(path, value);
                    break;
                }
            }
		}
	}
	/**
	 * 从jsonNode获取给定的字段名的字段。
	 * @param jsonNode
	 * @param attrName
	 * @return
	 * @throws IllegalAccessException
	 */
	private JsonNode getPatchAttr(JsonNode jsonNode, String attrName) throws IllegalAccessException {
		JsonNode child = jsonNode.get(attrName);
        if (child == null)
            throw new IllegalAccessException("JSON Patch 不包含 '" + attrName + "' 字段");
        return child;
	}

    /**
     *  获取路径信息
     * @param path
     * @return
     */
	private  List<String> getPath(JsonNode path) {
        List<String> paths = Splitter.on('/').splitToList(path.toString().replaceAll("\"", ""));
        return Lists.newArrayList(Iterables.transform(paths, DECODE_PATH_FUNCTION));
    }
}
