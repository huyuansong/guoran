package com.guoran.server.common.utils.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

//发送短信
@Service
public class NoteUtil {

    //    @Value(value = "${jeecg.noteMessageContent}")
//    private  String messageContent;
    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";
    @Value(value = "${hr.token.url}")
    private String url;
    @Value(value = "${hr.username}")
    private String username;
    @Value(value = "${hr.password}")
    private String password;

    public String noteLogin() {
        // 输入流
        InputStream is = null;
        BufferedReader br = null;
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        String token = "";
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("application", Locale.CHINA);
            //传入test.url配置名称，获取配置信息并赋值给url
            url = resourceBundle.getString("hr.token.url");
            username = resourceBundle.getString("hr.username");
            password = resourceBundle.getString("hr.password");
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            JSONObject user = new JSONObject();
            user.put("username", username);
            user.put("password", password);
            /*
             * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
             */
            URIBuilder uriBuilder = new URIBuilder(url);
            /** 第一种添加参数的形式 */
//        uriBuilder.addParameter("username", "wbguoranfengqing001");
//        uriBuilder.addParameter("password", "wbguoranfengqing001");
//            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//                paramList.add(new BasicNameValuePair("uesrname","wbguoranfengqing001"));
//                paramList.add(new BasicNameValuePair("password","guoranfengqing"));
            /** 第二种添加参数的形式 */
//            SimpleDateFormat formatter = new SimpleDateFormat("HH:00:00");
//            String dateString = formatter.format(new Date());
//            List<NameValuePair> list = new ArrayList<>();
//            BasicNameValuePair param1 = new BasicNameValuePair("userName", "wbguoranfengqing001");
//            BasicNameValuePair param2 = new BasicNameValuePair("passWord","guoranfengqing" );
////              BasicNameValuePair param3 = new BasicNameValuePair("passWord",password);
//
//            list.add(param1);
//            list.add(param2);
//            uriBuilder.setParameters(list);

            // 创建httpGet远程连接实例
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            // 设置请求头信息，鉴权
            httpPost.setHeader("contentType", "application/json");
            StringEntity stringEntity = new StringEntity(user.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
//            httpPost.setEntity(new StringEntity(user.toString(),"utf-8"));
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpPost.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpPost);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串

            result = EntityUtils.toString(entity);
            Map<String, Object> result1 = JSONObject.parseObject(result, Map.class);

            Object data = result1.get("data");
            Map<String, List> data1 = JSONObject.parseObject(data.toString(), Map.class);

            List rows = data1.get("rows");
            Object o = rows.get(0);
            Map map = JSONObject.parseObject(o.toString(), Map.class);
            Object token1 = map.get("token");


            token = token1.toString();
            System.out.println(token);
            // 设置token缓存有效时间
//            redisUtil.set("notetoken", token);
//            redisUtil.expire("notetoken", 86400);//有效期一天
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return token;
    }
//    //发送验证码
//    public  String noteSend(String phone) {
//        /*Object token=redisUtil.get("notetoken");
//        if(token==null){
//            noteLogin();
//            noteSend(phone);
//        }*/
//        noteLogin();
//        Object token=redisUtil.get("notetoken");
//        String token1=token.toString();
//        // 输入流
//        InputStream is = null;
//        BufferedReader br = null;
//        // 创建httpClient实例
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CloseableHttpResponse response = null;
//        String result = "";
//        try {
//            // 通过址默认配置创建一个httpClient实例
//            httpClient = HttpClients.createDefault();
//
//
//            /*SimpleDateFormat formatter = new SimpleDateFormat("HH:00:00");
//            String dateString = formatter.format(new Date());
//
//            System.out.println(systemname+"+"+password+"+"+dateString);
//            String password1=PasswordUtil.encrypt(systemname,password,dateString);
//            String password2=PasswordUtil.decrypt(systemname,password1,dateString);
//
//            System.out.println("加密后"+password1);
//            System.out.println("解密后"+password2);
//            System.out.println("TOKEN"+token1);*/
//
//            // 创建请求参数
//            List<Map<String,Object>> listjson=new ArrayList<>();
//            Map<String,Object> map=new HashMap<>();
//            String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
//            map.put("messageContent",messageContent+code);
//            map.put("deleted",0);
//            map.put("sendMessageType",2);
//            map.put("systemName",systemname);
//            map.put("messageSign","鞍山考试院");
//            map.put("messageTo",phone);
//            listjson.add(map);
//            String lowerCaseCaptcha = code.toLowerCase();
//            String key=MD5Util.MD5Encode(phone, "utf-8");
//            redisUtil.set(key, lowerCaseCaptcha, 100);
//            String messageListJson=JSONObject.toJSONString(listjson);
//            /*
//             * 添加请求参数
//             */
//            URIBuilder uriBuilder = new URIBuilder(url+"send/message");
//            uriBuilder.addParameter("sendMessageType", "2");
//             uriBuilder.addParameter("systemName", systemname);
//            uriBuilder.addParameter("token", token1);
//            uriBuilder.addParameter("passWord", password);
//            uriBuilder.addParameter("messageListJson", messageListJson);
//            //post方式添加请求参数
//            /*List<NameValuePair> list = new ArrayList<>();
//            BasicNameValuePair param1 = new BasicNameValuePair("sendMessageType", "2");
//            BasicNameValuePair param2 = new BasicNameValuePair("systemName", systemname);
//            BasicNameValuePair param3 = new BasicNameValuePair("token", token1);
//            BasicNameValuePair param5 = new BasicNameValuePair("passWord", password);
//            BasicNameValuePair param4    = new BasicNameValuePair("messageListJson", messageListJson);
//            list.add(param1);
//            list.add(param2);
//            list.add(param3);
//            list.add(param4);
//            list.add(param5);
//            // 使用URL实体转换工具
//            UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");*/
//            // 创建httpPost远程连接实例
//            HttpPost httpPost = new HttpPost(uriBuilder.build());
////            httpPost.setEntity(entityParam);
//            // 设置请求头信息，鉴权
//            httpPost.setHeader("Content-Type", "application/json");
//            httpPost.addHeader("Accept","*/*");
//            // 设置配置请求参数
//            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
//                    .setConnectionRequestTimeout(35000)// 请求超时时间
//                    .setSocketTimeout(60000)// 数据读取超时时间
//                    .build();
//            // 为httpGet实例设置配置
//            httpPost.setConfig(requestConfig);
//            // 执行get请求得到返回对象
//            response = httpClient.execute(httpPost);
//            // 通过返回对象获取返回数据
//            HttpEntity entity = response.getEntity();
//            // 通过EntityUtils中的toString方法将结果转换为字符串
//            result = EntityUtils.toString(entity);
//            saveSysLog(phone,messageContent+code,result);
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } finally {
//            // 关闭资源
//            if (null != response) {
//                try {
//                    response.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (null != httpClient) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return result;
//    }
//
//    private void saveSysLog(String phone, String message,String result) {
//        SysNoteLog sysLog = new SysNoteLog();
//        //注解上的描述,操作日志内容
//        sysLog.setLogContent(message);
//        sysLog.setLogType(2);
//        //请求的参数
//        sysLog.setRequestParam(phone);
//        //获取request
//        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
//        //设置IP地址
//        sysLog.setIp(IPUtils.getIpAddr(request));
//        //获取登录用户信息
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        if (sysUser != null) {
//            sysLog.setUserid(sysUser.getUsername());
//            sysLog.setUsername(sysUser.getRealname());
//            sysLog.setCreateBy(sysUser.getUsername());
//        }
//        sysLog.setResponse(result);
//        sysLog.setCreateTime(new Date());
//        //保存系统日志
//        sysNoteLogMapper.insert(sysLog);
//    }
}
