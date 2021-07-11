package io.transaction.notifymsg.account.utils;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;


public class BaseConnectionUtils {
	//文件流类型
	public static final String TYPE_STREAM = "type_stream";
	//字符串类型
	public static final String TYPE_STRING = "type_string";
	
	/**
	 * 从PostMethod中获取字符串数据
	 * @param postMethod
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected static String getStringFromPostMethod(PostMethod postMethod, String type) throws Exception{
		String result = "";
		switch (type) {
		case TYPE_STREAM:
			InputStream in = postMethod.getResponseBodyAsStream();
			if(in != null){
				InputStreamReader reader = new InputStreamReader(in, CharsetCode.getCharset(CharsetCode.CHARSET_UTF));
				result = FileCopyUtils.copyToString(reader);
			}
			break;
		case TYPE_STRING:
			result = postMethod.getResponseBodyAsString();
			break;
		default:
			result = postMethod.getResponseBodyAsString();
			break;
		}
		return result;
	}
	
	/**
	 * 从getMethod中获取字符串
	 * @param getMethod
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected static String getStringFromGetMethod(GetMethod getMethod, String type) throws Exception{
		String result = "";
		switch (type) {
		case TYPE_STREAM:
			InputStream in = getMethod.getResponseBodyAsStream();
			if(in != null){
				InputStreamReader reader = new InputStreamReader(in, CharsetCode.getCharset(CharsetCode.CHARSET_UTF));
				result = FileCopyUtils.copyToString(reader);
			}
			break;
		case TYPE_STRING:
			result = getMethod.getResponseBodyAsString();
			break;
		default:
			result = getMethod.getResponseBodyAsString();
			break;
		}
		return result;
	}
}
