package com.hisoft.pam.im.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hisoft.pam.im.common.exception.ImErrorCode;
import com.hisoft.pam.im.common.i18n.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Rest接口中返回的结果对象。
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult implements Serializable {
    /*
     * 是否操作成功.
     * */
    private Boolean isSuccess;
    /**
     * 消息代码,-1失败，0成功，其余为自定义，1-100为系统，101-999为自定义
     */
    private String code;
    /*
     * 消息.
     * */
    private String message;
    /*
     * 数据对象.
     * */
    private Object data;

    /**
     * 成功
     * @return
     */
    public static String success() {
        return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
    }

    /**
     * 成功
     * @return
     */
    public static String success(Object data) {
        return success(ImErrorCode.MSG_SUCCESS,ImErrorCode.MSG_SUCCESS,data);
    }

    /**
     * 有数据和消息代码的成功结果
     * @param code
     * @param data
     * @return
     */
    public static String success(String code,Object data) {
        return success(code, ImErrorCode.MSG_SUCCESS, data);
    }
    /**
     * 有数据和消息代码的成功结果
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static String success(String code,String message,Object data) {
        return JSON.toJSONStringWithDateFormat(new JsonResult(true,code, message, data),"yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteNullStringAsEmpty);
    }
    /**
     * 失败的结果
     * @return
     */
    public static String failed() {
        return failed(ImErrorCode.MSG_FAIL);
    }

    /**
     * 自定义消息的失败结果
     * @param msg
     * @return
     */
    public static String failed(String msg) {
        return failed(ImErrorCode.MSG_FAIL, msg);
    }

    /**
     * 自定义消息编号的失败结果
     * @param code
     * @param msg
     * @return
     */
    public static String failed(String code, String msg) {
        return JSON.toJSONString(new JsonResult(false,code, msg, null));
    }
}
