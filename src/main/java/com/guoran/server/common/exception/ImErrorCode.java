package com.guoran.server.common.exception;

/**
 * * <pre>
 *  * 错误码8位，格式 ：固定前缀（2位） + 错误码类型 （2位） + 模块码（2位） + 代码（2位），如 IMSE0101
 *  * 固定前缀：IM-南非留学生信息管理系统
 *  * 错误码类型: SE-系统错误；FE-字段校验错误；TE-通讯异常；BE-业务错误; OE-其它异常	｛待补充｝
 *  * 模块码：01-用户基本信息；02-权限；03-日志管理
 *  * <pre>
 */
public interface ImErrorCode {
    /**
     * 字符串为空或null
     */
    final String MSG_STR_ISEMPTY="00000020";
    /**
     * 成功
     */
    final String MSG_SUCCESS = "00000000";
    /**
     * 失败
     */
    final String MSG_FAIL = "99999999";
    /**
     * 登陆
     */
    final String LOGIN_SUCCESS="login.success";
    /**
     * 文件上传失败
     */
    final String FILE_UPLOAD_FAIL ="OE03000001";
    /**
     * 文件上传超过10M
     */
    final String FILE_UPLOAD_SIZE = "OE03000002";
    /**
     * 文件格式不正确
     */
    final String FILE_UPLOAD_TYPE = "OE03000003";
    /**
     * 文件不能为null
     */
    final String FILE_UPLOAD_EMPTY = "OE03000004";
    //答题人数达到上限
    final String LIMIT_NUM="OE03000005";
    //请使用微信客户端扫码
    final String ONLY_WEIXIN="OE03000006";
    //您已经答过此问卷
    final String ANSWERED_PAPER="OE03000007";
    //没有题目可答
    final String NO_QUESTIONS="OE03000008";
    //没有找到相应的问卷
    final String UNFOUND_PAPER="OE03000009";
    //问卷调查已经结束
    final String ALREADY_OVER="OE03000010";
    //问卷调查还未开始
    final String NOT_START="OE03000011";

}
