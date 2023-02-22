package com.hisoft.pam.im.common.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;

public class DingUtil {
    private static String APPKEY = "ding2qwyliwvzc3dufcc";//(这里是应用的AppKey)
    private static String APPSECRET = "E0RyaK5N1m5okiSx4GsujWS6M0eNUla8JlujI4pAZxdi7kOBTec_RsgD6alEhw-D";//(这里是应用的APPSECRET )
    private static AccessTokenlin atl;
    private static String getAccessToken() throws Exception {
        DefaultDingTalkClient client = new 	DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(APPKEY);
        request.setAppsecret(APPSECRET);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        String accessToken = response.getAccessToken();
        //将获取的AccessToken和它的过期时间封装到一个AccessToken类中
        atl = new AccessTokenlin(accessToken, "7200");
        return accessToken;
    }
    public static String GetAccessToken() throws Exception {
        //这里我们需要判断一下Accesstoken是否过期，然后才将其返回
        if(atl==null||atl.isExpired()) {
            getAccessToken();
        }
        return atl.getAccessToken();
    }

    /**
     * 发送通知信息
     * @throws ApiException
     * @throws Exception
     */
    public static void sendMsg(String jobNum,String text) throws  ApiException, Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //传入的是员工的工号
        request.setUseridList(jobNum);
        //申请的应用AgentId
        request.setAgentId(1688776543L);
        //true为全体员工
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(text);
        request.setMsg(msg);

    	 /*//发送图片
    	msg.setMsgtype("image");
    	msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
    	msg.getImage().setMediaId("@lADOdvRYes0CbM0CbA");
    	request.setMsg(msg);
    	/*
		//发送文件
    	msg.setMsgtype("file");
    	msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
    	msg.getFile().setMediaId("@lADOdvRYes0CbM0CbA");
    	request.setMsg(msg);
		//发送链接
    	msg.setMsgtype("link");
    	msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
    	msg.getLink().setTitle("test");
    	msg.getLink().setText("test");
    	msg.getLink().setMessageUrl("test");
    	msg.getLink().setPicUrl("test");
    	request.setMsg(msg);

    	msg.setMsgtype("markdown");
    	msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
    	msg.getMarkdown().setText("##### text");
    	msg.getMarkdown().setTitle("### Title");
    	request.setMsg(msg);

    	msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
    	msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
    	msg.getOa().getHead().setText("head");
    	msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
    	msg.getOa().getBody().setContent("xxx");
    	msg.setMsgtype("oa");
    	request.setMsg(msg);
    	//发送的文本信息+url
    	msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
    	msg.getActionCard().setTitle("xxx123411111");
    	msg.getActionCard().setMarkdown("### 测试123111");
    	msg.getActionCard().setSingleTitle("接入钉钉发送通知接口");
    	msg.getActionCard().setSingleUrl("https://www.baidu.com");
    	msg.setMsgtype("action_card");
    	request.setMsg(msg);
		*/
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,GetAccessToken());
        System.out.println(response);
    }

    public static void main(String[] args) throws Exception {
//        SendMsg();
    }
}
