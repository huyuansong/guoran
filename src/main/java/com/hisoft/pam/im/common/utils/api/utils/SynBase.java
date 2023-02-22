package com.hisoft.pam.im.common.utils.api.utils;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 1.从resources/config.properties中读取测试api相关的数据 2.运行程序，测试查看测试结果
 * 
 * @author lizhmf
 * @date 2019年6月20日上午10:53:11
 */
public class SynBase {

//	private static String client_secret = "5e0f86dc58b24a0ca170";
//	private static String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCg1UlOUDw0DPCU5UiPgYgCvcSBKF/eTeBKvTcwfZIh7uNegzrt7sMoTvITZrXvMWFSSsw3JOWnsAI3BH3Kqyp8v3ClsVgXv2EYRI79hBnHYxZ0CEgqB/7gqlUUPV7GgXVyxWdoQuNdfTTuW7ihjaPawyStJ1G3I00woEjGyV9OEwIDAQAB";
//	private static String client_id = "nccloud5";
//	private static String username = "grfq";
//	private static String pwd = "grfq123";
//	private static String busi_center = "1";
//	// 获取token方式
//	private static String grant_type = "password";
//	// 服务器ip：port
//	private static String baseUrl = "http://imuyuan.com:8866";
//	private static String secret_level = "L0";
	private static String client_secret ;
	private static String pubKey ;
	private static String client_id ;
	private static String username;
	private static String pwd ;
	private static String busi_center ;
	// 获取token方式
	private static String grant_type ;
	// 服务器ip：port
	private static String baseUrl ;
	private static String tokenUrl;
	private static String secret_level;
	private static String requestBody = null;
	// openapi请求路径
	private static String apiUrl = null;

	public static String token = null;
	public static String repeat_check = null;
	public static String busi_id = null;
	
	public static void main(String[] args) {
		try {
			// 初始化数据
			init();
			// 请求token
			token = getToken();
			System.out.println("getTokenData:" + token);
			if (token != null) {
				// 测试openapi
				testApi(token,"/nccloud/api/ic/materialout/saveAndSign","[{\n" +
						"\t\"ic_material_b\": [{\n" +
						"\t\t\"nshouldassistnum\": \"0\",\n" +
						"\t\t\"vchangerate\": \"1/1\",\n" +
						"\t\t\"corpvid\": \"JT0021\",\n" +
						"\t\t\"cbodytranstypecode\": \"4D-01\",\n" +
						"\t\t\"cbodywarehouseid\": \"JT0021001001\",\n" +
						"\t\t\"cmaterialvid\": \"14036203\",\n" +
//						"\t\t\"castunitid\": \"EA\",\n" +
						"\t\t\"cmaterialoid\": \"14036203\",\n" +
						"\t\t\"corpoid\": \"JT0021\",\n" +
//						"\t\t\"cunitid\": \"EA\",\n" +
						"\t\t\"ncostprice\": \"1\"\n" +
						"\t}],\n" +
						"\t\"ic_material_h\": {\n" +
						"\t\t\"cfanaceorgvid\": \"JT0021\",\n" +
						"\t\t\"ctrantypeid\": \"4D-01\",\n" +
						"\t\t\"cwarehouseid\": \"JT0021001001\",\n" +
						"\t\t\"cdrawcalbodyvid\": \"JT0021\",\n" +
						"\t\t\"vtrantypecode\": \"4D-01\",\n" +
						"\t\t\"corpvid\": \"JT0021\",\n" +
						"\t\t\"corpoid\": \"JT0021\",\n" +
						"\t\t\"cdrawcalbodyoid\": \"JT0021\",\n" +
						"\t\t\"pk_org\": \"JT0021\",\n" +
						"\t\t\"vdef1\": \"GR\",\n" +
						"\t\t\"cdrawwarehouseid\": \"JT0021001001\",\n" +
						"\t\t\"ccostdomainoid\": \"JT0021\",\n" +
						"\t\t\"vnote\": \"备注\",\n" +
						"\t\t\"cdptid\": \"JT002103\",\n" +
						"\t\t\"cdptvid\": \"JT002103\",\n" +
						"\t\t\"cfanaceorgoid\": \"JT0021\"\n" +
						"\t}\n" +
						"}]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//调用远程接口
	public String SynNCC(String apiUrl,String requestBody){
		String o = null;
		try {
					// 初始化数据
			init();
			System.out.println(requestBody);
			// 请求token
			token = getToken();
//			System.out.println("getTokenData:" + token);
			if (token != null) {
				// 测试openapi
				 o = testApi(token, apiUrl, requestBody);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 通过refresh_token重新获取token
	 * 
	 * @param refresh_token
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	private static String getTokenByRefreshToken(String refresh_token) throws UnsupportedEncodingException, Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		// 密码模式认证
		paramMap.put("grant_type", "refresh_token");
		// 第三方应用id
		paramMap.put("client_id", client_id);
		// 第三方应用secret 公钥加密
		paramMap.put("client_secret", URLEncoder.encode(Encryption.pubEncrypt(pubKey, client_secret), "utf-8"));
		// 签名
		String sign = SHA256Util.getSHA256(client_id + client_secret + refresh_token + pubKey);
		paramMap.put("signature", sign);

		String url = baseUrl + "/nccloud/opm/accesstoken";
		String mediaType = "application/x-www-form-urlencoded";
		String token = doPost(url, paramMap, mediaType, null, "");
		return token;
	}

	private static String getToken() throws Exception {
		String token = null;
		if ("password".equals(grant_type)) {
			// 密码模式
			token = getTokenByPWD();
		} else if ("client_credentials".equals(grant_type)) {
			// 客户端模式
			token = getTokenByClient();
		} else if ("authorization_code".equals(grant_type)) {
			// TODO 页面跳转

			// 授权码模式
		}else if ("no".equals(grant_type)){
			token = getTokenByNothing();
			// 微服务模式
		}
		return token;
	}
	/**
	 * 微服务模式获取token
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getTokenByNothing() throws Exception {
		String url = tokenUrl;
		//String mediaType = "application/x-www-form-urlencoded";
		String token = doPost(url, null, null, null, "");
		return token;
	}
	/**
	 * 客户端模式获取token
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getTokenByClient() throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		// 密码模式认证
		paramMap.put("grant_type", "client_credentials");
		// 第三方应用id
		paramMap.put("client_id", client_id);
		// 第三方应用secret 公钥加密
		paramMap.put("client_secret", URLEncoder.encode(Encryption.pubEncrypt(pubKey, client_secret), "utf-8"));
		// 账套编码
		paramMap.put("biz_center", busi_center);
		// // TODO 传递数据源和ncc登录用户
		// paramMap.put("dsname", "TM_0614");
		// paramMap.put("usercode", "1");

		// 签名
		String sign = SHA256Util.getSHA256(client_id + client_secret + pubKey);
		paramMap.put("signature", sign);

		String url = baseUrl + "/nccloud/opm/accesstoken";
		String mediaType = "application/x-www-form-urlencoded";
		String token = doPost(url, paramMap, mediaType, null, "");
		return token;
	}



	/**
	 * 密码模式获取token
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static String getTokenByPWD() throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		// 密码模式认证
		paramMap.put("grant_type", "password");
		// 第三方应用id
		paramMap.put("client_id", client_id);
		// 第三方应用secret 公钥加密
		paramMap.put("client_secret", URLEncoder.encode(Encryption.pubEncrypt(pubKey, client_secret), "utf-8"));
		// ncc用户名
		paramMap.put("username", username);
		// 密码 公钥加密
		paramMap.put("password", URLEncoder.encode(Encryption.pubEncrypt(pubKey, pwd), "utf-8"));
		// 账套编码
		paramMap.put("biz_center", busi_center);
		// 签名
		String sign = SHA256Util.getSHA256(client_id + client_secret + username + pwd + pubKey);
		paramMap.put("signature", sign);

		String url = baseUrl + "/nccloud/opm/accesstoken";
		String mediaType = "application/x-www-form-urlencoded";
		String token = doPost(url, paramMap, mediaType, null, "");
		return token;
	}

	/**
	 * 请求openapi
	 * 
	 * @param token
//	 * @param security_key
	 *                         请求body参数加密压缩用的key
	 * @throws Exception
	 */
	private static String testApi(String token,String apiUrl,String requestBody) throws Exception {
		// token转对象，获取api访问所用token和secret
		ResultMessageUtil returnData = new Gson().fromJson(token, ResultMessageUtil.class);
		Map<String, String> data = (Map<String, String>) returnData.getData();
		String access_token = data.get("access_token");
		String security_key = data.get("security_key");
		String refresh_token = data.get("refresh_token");
		System.out.println("【ACCESS_TOKEN】:" + access_token);

		// 请求路径
		String url = baseUrl + apiUrl;
		// header 参数
		Map<String,String> headermap = new HashMap<>();
		headermap.put("access_token", access_token);
		headermap.put("client_id", client_id);
		if(!"no".equals(grant_type)){
			StringBuffer sb = new StringBuffer();
			sb.append(client_id);

//		Map rBody = new HashMap();
//		String pks[]= new String[1];
//		pks[0]="1001A21000000000YOI4";
//		rBody.put("pk_voucher", pks);
//		System.out.println(JSON.toJSONString(rBody));
//		requestBody = JSON.toJSONString(rBody);
			if (StringUtils.isNotBlank(requestBody)) {
				// sb.append(requestBody.replaceAll("\\s*|\t|\r|\n", "").trim());
				sb.append(requestBody);
			}
			sb.append(pubKey);
			String sign = SHA256Util.getSHA256(sb.toString());
			headermap.put("signature", sign);

			if (StringUtils.isNotBlank(busi_id)) {
				headermap.put("busi_id", busi_id);
			}
			if (StringUtils.isNotBlank(repeat_check)) {
				headermap.put("repeat_check", repeat_check);
			}
		}

		headermap.put("ucg_flag", "y");

		String mediaType = "application/json;charset=utf-8";

		// 表体数据json
		// 根据安全级别选择加密或压缩请求表体参数
		String json = dealRequestBody(requestBody, security_key, secret_level);

		// 返回值
		String result = doPost(url, null, mediaType, headermap, json);
		//String result2 = dealResponseBody(result, security_key, secret_level);
		System.out.println("【RESULT】:" + result);
		// System.out.println("result解密:" + result2);
		return result;
	}

	private static String dealResponseBody(String source, String security_key, String level) throws Exception {
		String result = null;

		if (StringUtils.isEmpty(level) || SecretConst.LEVEL0.equals(level)) {
			result = source;
		} else if (SecretConst.LEVEL1.equals(level)) {
			result = Decryption.symDecrypt(security_key, source);
		} else if (SecretConst.LEVEL2.equals(level)) {
			result = CompressUtil.gzipDecompress(source);
		} else if (SecretConst.LEVEL3.equals(level)) {
			result = CompressUtil.gzipDecompress(Decryption.symDecrypt(security_key, source));
		} else if (SecretConst.LEVEL4.equals(level)) {
			result = Decryption.symDecrypt(security_key, CompressUtil.gzipDecompress(source));
		} else {
			throw new Exception("无效的安全等级");
		}

		return result;
	}

	/**
	 * 初始化参数
	 */
	private static void init() {
		// TODO Auto-generated method stub
		Properties properties = new Properties();

		String filepath = "application.properties";
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classloader.getResourceAsStream(filepath);
		try {
			InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
			properties.load(reader);

			client_secret = new String(properties.getProperty("client_secret").getBytes("utf-8"), "utf-8");
			client_id = properties.getProperty("client_id");
			pubKey = properties.getProperty("pubKey");
			username = properties.getProperty("username");
			pwd = properties.getProperty("pwd");
			busi_center = properties.getProperty("busi_center");
			baseUrl = properties.getProperty("baseUrl");
			tokenUrl = properties.getProperty("tokenUrl");
			requestBody = new String(properties.getProperty("requestBody").getBytes("utf-8"), "utf-8");
			apiUrl = properties.getProperty("apiUrl");
			grant_type = properties.getProperty("grant_type");
			secret_level = properties.getProperty("secret_level");
			repeat_check = properties.getProperty("repeat_check");
			busi_id = properties.getProperty("busi_id");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 根据安全级别设置，表体是否加密或压缩
	private static String dealRequestBody(String source, String security_key, String level) throws Exception {
		String result = null;
		if (StringUtils.isEmpty(level) || SecretConst.LEVEL0.equals(level)) {
			result = source;
		} else if (SecretConst.LEVEL1.equals(level)) {
			result = Encryption.symEncrypt(security_key, source);
		} else if (SecretConst.LEVEL2.equals(level)) {
			result = CompressUtil.gzipCompress(source);
		} else if (SecretConst.LEVEL3.equals(level)) {
			result = Encryption.symEncrypt(security_key, CompressUtil.gzipCompress(source));
		} else if (SecretConst.LEVEL4.equals(level)) {
			result = CompressUtil.gzipCompress(Encryption.symEncrypt(security_key, source));
		} else {
			throw new Exception("无效的安全等级");
		}

		return result;
	}

	/**
	 * 发送post请求
	 * 
	 * @param baseUrl
	 * @param paramMap
	 * @param mediaType
	 * @param headers
	 * @param json
	 * @return
	 */
	private static String doPost(String baseUrl, Map<String, String> paramMap, String mediaType, Map<String, String> headers, String json) {

		HttpURLConnection urlConnection = null;
		InputStream in = null;
		OutputStream out = null;
		BufferedReader bufferedReader = null;
		String result = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(baseUrl);
			if (paramMap != null) {
				sb.append("?");
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					sb.append(key + "=" + value).append("&");
				}
				baseUrl = sb.toString().substring(0, sb.toString().length() - 1);
			}

			URL urlObj = new URL(baseUrl);
			urlConnection = (HttpURLConnection) urlObj.openConnection();
			urlConnection.setConnectTimeout(50000);
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(false);
			urlConnection.addRequestProperty("content-type", mediaType);
			if (headers != null) {
				for (String key : headers.keySet()) {
					urlConnection.addRequestProperty(key, headers.get(key));
				}
			}
			out = urlConnection.getOutputStream();
			out.write(json.getBytes("utf-8"));
			out.flush();
			int resCode = urlConnection.getResponseCode();
			if (resCode == HttpURLConnection.HTTP_OK || resCode == HttpURLConnection.HTTP_CREATED || resCode == HttpURLConnection.HTTP_ACCEPTED) {
				in = urlConnection.getInputStream();
			} else {
				in = urlConnection.getErrorStream();
			}
			bufferedReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			String ecod = urlConnection.getContentEncoding();
			if (ecod == null) {
				ecod = Charset.forName("utf-8").name();
			}
			result = new String(temp.toString().getBytes("utf-8"), ecod);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			urlConnection.disconnect();
		}
		return result;
	}

	class SecretConst {
		/**
		 * LEVEL0 不压缩、不加密
		 */
		public static final String LEVEL0 = "L0";
		/**
		 * LEVEL1 只加密、不压缩
		 */
		public static final String LEVEL1 = "L1";
		/**
		 * LEVEL2 只压缩、不加密
		 */
		public static final String LEVEL2 = "L2";
		/**
		 * LEVEL3 先压缩、后加密
		 */
		public static final String LEVEL3 = "L3";
		/**
		 * LEVEL4 先加密、后压缩
		 */
		public static final String LEVEL4 = "L4";
	}
}
