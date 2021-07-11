package io.transaction.notifymsg.account.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 网络链接;POST，GET请求
 * @author liuyazhuang
 *
 */
public class HttpConnectionUtils extends BaseConnectionUtils{
	
	/**
	 * SOAP12调用webservice
	 * @param methodName
	 * @param wsdlLocation
	 * @param soapRequestData
	 * @return
	 * @throws Exception
	 */
	public static String soap12(String methodName, String wsdlLocation, String soapRequestData, String type) throws Exception {
    	String soapResponseData = "";
        PostMethod postMethod = new PostMethod(wsdlLocation);
        byte[] bytes = soapRequestData.getBytes("UTF-8");
        InputStream inputStream = new ByteArrayInputStream(bytes, 0, bytes.length);
        RequestEntity requestEntity = new InputStreamRequestEntity(inputStream, bytes.length, "application/soap+xml; charset=utf-8");
        postMethod.setRequestEntity(requestEntity);
        HttpClient httpClient = new HttpClient();
        int statusCode = httpClient.executeMethod(postMethod);
        if(statusCode == HttpStatus.SC_OK){
        	 soapResponseData = getStringFromPostMethod(postMethod, type);
        }
        return soapResponseData;
    }
	
	/**
	 * POST方式调用webservice接口
	 * @param protocol: http/https
	 * @throws Exception
	 */
	public static String postWebService(String host, int port, String protocol, String url, NameValuePair[] nameValuePairs,/* String postKey, */String type) throws Exception{
		String result = "";
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		HttpClient httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost(host, port,  protocol);
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		postMethod.setRequestBody(nameValuePairs);
		try {
			int status = httpClient.executeMethod(postMethod);
			if(status == HttpStatus.SC_OK){
				result = getStringFromPostMethod(postMethod, type);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(postMethod != null){
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	
	/**
	 * POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String postData(String url, String body, NameValuePair[] nameValuePairs, Map<String, String> headers, String type) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				postMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		postMethod.setRequestBody(body);
		try {
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = getStringFromPostMethod(postMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	/**
	 * POST请求数据
	 * @param url
	 * @param nameValuePairs
	 * @return
	 */
	public static String postStringData(String url, String body, NameValuePair[] nameValuePairs, Map<String, String> headers, String type) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				postMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		postMethod.setRequestBody(new NameValuePair[] { new NameValuePair(
				"parameter", body) });
		try {
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = getStringFromPostMethod(postMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	
	/**
	 * GET方式请求数据获取资源
	 * @param url
	 * @param nameValuePairs
	 * @return
	 */
	public static String getData(String url, NameValuePair[] nameValuePairs, Map<String, String> headers, String type) throws Exception{
		String result = "";
		GetMethod getMethod = new GetMethod(url);
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				getMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		getMethod.setQueryString(nameValuePairs);
		try {
			int status = new HttpClient().executeMethod(getMethod);
			if (status == HttpStatus.SC_OK) {
				result = getStringFromGetMethod(getMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return result;
	}
	
	
	
	
	/**
	 * 上传文件
	 * 
	 * @param url
	 *            ：服务器接口
	 * @param imagePath
	 *            ：本地图片绝对路径
	 * @return
	 */
	public static String sendPostContainerFile(String url, String oldImageRealPath, String imagePath) throws Exception {
		String result = null;
		File file = new File(imagePath);
		if(!file.exists()) return null;
		PostMethod postMethod = new PostMethod(url);
		try {
			Part[] parts = { new FilePart("file", file),
					new StringPart("oldImageRealPath", oldImageRealPath) };
			postMethod.setRequestEntity(new MultipartRequestEntity(parts,
					postMethod.getParams()));
			int status = new HttpClient()
					.executeMethod(postMethod);
			if (status == HttpStatus.SC_OK)
				result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			throw e;
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	/**
	 * 上传文件
	 * 
	 * @param url
	 *            ：服务器接口
	 * @param imagePath
	 *            ：本地图片绝对路径
	 * @return
	 */
	public static String sendPostContainerFile(String url, String oldImageRealPath, String imagePath, String type) throws Exception {
		String result = null;
		File file = new File(imagePath);
		if(!file.exists()) return null;
		PostMethod postMethod = new PostMethod(url);
		try {
			Part[] parts = { new FilePart("file", file),
					new StringPart("oldImageRealPath", oldImageRealPath) };
			postMethod.setRequestEntity(new MultipartRequestEntity(parts,
					postMethod.getParams()));
			int status = new HttpClient()
			.executeMethod(postMethod);
			if (status == HttpStatus.SC_OK)
				result = getStringFromPostMethod(postMethod, type);
		} catch (Exception e) {
			throw e;
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	
	/**
	 * POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postData(String url, String params, NameValuePair[] nameValuePairs, Map<String, String> headers) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				postMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		postMethod.setRequestBody(new NameValuePair[] { new NameValuePair(
				"parameter", params) });
		try {
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	/**
	 * POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static InputStream postDataWithStream(String url, String params, NameValuePair[] nameValuePairs, Map<String, String> headers) throws Exception{
		InputStream in = null;
		PostMethod postMethod = new PostMethod(url);
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				postMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		postMethod.setRequestBody(new NameValuePair[] { new NameValuePair(
				"parameter", params) });
		try {
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				in = postMethod.getResponseBodyAsStream();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return in;
	}
	/**
	 * POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postData(String url, JSONObject jsonObject, NameValuePair[] nameValuePairs, Map<String, String> headers) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				postMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		postMethod.setRequestBody(new NameValuePair[] { new NameValuePair(
				"parameter", jsonObject.toString()) });
		try {
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	/**
	 * POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postData(String url, JSONObject jsonObject, NameValuePair[] nameValuePairs) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		Header header = new Header();
		header.setName("Content-Type");
		header.setValue("application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setRequestHeader(header);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		postMethod.setRequestBody(new NameValuePair[] { new NameValuePair(
				"parameter", jsonObject.toString()) });
		try {
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	/**
	 * POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postData(String url, JSONObject jsonObject, NameValuePair[] nameValuePairs, String type) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		Header header = new Header();
		header.setName("Content-Type");
		header.setValue("application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setRequestHeader(header);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		postMethod.setRequestBody(new NameValuePair[] { new NameValuePair(
				"parameter", jsonObject.toString()) });
		try {
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = getStringFromPostMethod(postMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	
	/**
	 * POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postData(String url, JSONObject jsonObject, NameValuePair[] nameValuePairs, Map<String, String> headers, String type) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				postMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		postMethod.setRequestEntity(new StringRequestEntity(jsonObject.toString(), null, "utf-8"));
		postMethod.setRequestBody(new NameValuePair[] { new NameValuePair(
				"parameter", jsonObject.toString()) });
		try {
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = getStringFromPostMethod(postMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	
	/**
	 * POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postDataWithParameter(String url, String jsonString, NameValuePair[] nameValuePairs, Map<String, String> headers, String type) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				postMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		postMethod.setRequestEntity(new StringRequestEntity(jsonString, null, "utf-8"));
		postMethod.setRequestBody(new NameValuePair[] { new NameValuePair(
				"parameter", jsonString) });
		try {
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = getStringFromPostMethod(postMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	
	/**
	 * 微信POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postWechatData(String url, JSONObject jsonObject, NameValuePair[] nameValuePairs) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		if (jsonObject == null) {
			jsonObject = new JSONObject();
		}
		postMethod.setQueryString(nameValuePairs);
		try {
			postMethod.setRequestEntity(new StringRequestEntity(jsonObject.toString(), null, "utf-8"));
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	/**
	 * 微信POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postWechatData(String url, String json, NameValuePair[] nameValuePairs) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		try {
			postMethod.setRequestEntity(new StringRequestEntity(json, null, "utf-8"));
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	/**
	 * 微信POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postWechatData(String url, JSONObject jsonObject, NameValuePair[] nameValuePairs, String type) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		try {
			postMethod.setRequestEntity(new StringRequestEntity(jsonObject.toString(), null, "utf-8"));
			int status = new HttpClient().executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				result = getStringFromPostMethod(postMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	/**
	 * 微信POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postWechatData(String url, String json, NameValuePair[] nameValuePairs, String type) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		postMethod.setQueryString(nameValuePairs);
		try {
			postMethod.setRequestEntity(new StringRequestEntity(json, "Content-Type: application/json", "utf-8"));
			new HttpClient().executeMethod(postMethod);
			result = getStringFromPostMethod(postMethod, type);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	
	/**
	 * 微信POST请求数据
	 * @param url
	
	 * @param nameValuePairs
	 * @return
	 */
	public static String postWechatData(String url, String json, NameValuePair[] nameValuePairs, Map<String, String> headers, String type) throws Exception{
		String result = "";
		PostMethod postMethod = new PostMethod(url);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				postMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		postMethod.setQueryString(nameValuePairs);
		try {
			postMethod.setRequestEntity(new StringRequestEntity(json, "application/json", "utf-8"));
			int code = new HttpClient().executeMethod(postMethod);
			if(code > 0) {
				result = getStringFromPostMethod(postMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return result;
	}
	
	/**
	 * GET方式请求数据获取资源
	 * @param url
	 * @param nameValuePairs
	 * @return
	 */
	public static String getWechatData(String url, NameValuePair[] nameValuePairs) throws Exception{
		String result = "";
		GetMethod getMethod = new GetMethod(url);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		getMethod.setQueryString(nameValuePairs);
		try {
			int status = new HttpClient().executeMethod(getMethod);
			System.out.println(status);
			if (status == HttpStatus.SC_OK) {
				result = getMethod.getResponseBodyAsString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return result;
	}
	/**
	 * GET方式请求数据获取资源
	 * @param url
	 * @param nameValuePairs
	 * @return
	 */
	public static String getWechatData(String url, Map<String, String> headers, NameValuePair[] nameValuePairs) throws Exception{
		String result = "";
		GetMethod getMethod = new GetMethod(url);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				getMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		getMethod.setQueryString(nameValuePairs);
		try {
			int status = new HttpClient().executeMethod(getMethod);
			System.out.println(status);
			if (status == HttpStatus.SC_OK) {
				result = getMethod.getResponseBodyAsString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return result;
	}
	/**
	 * GET方式请求数据获取资源
	 * @param url
	 * @param nameValuePairs
	 * @return
	 */
	public static String getWechatData(String url, NameValuePair[] nameValuePairs, String type) throws Exception{
		String result = "";
		GetMethod getMethod = new GetMethod(url);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		getMethod.setQueryString(nameValuePairs);
		try {
			int status = new HttpClient().executeMethod(getMethod);
			if (status == HttpStatus.SC_OK) {
				result = getStringFromGetMethod(getMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return result;
	}
	/**
	 * GET方式请求数据获取资源
	 * @param url
	 * @param nameValuePairs
	 * @return
	 */
	public static String getPayData(String url, NameValuePair[] nameValuePairs, Map<String, String> headers, String type) throws Exception{
		String result = "";
		GetMethod getMethod = new GetMethod(url);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		if(!CollectionUtils.isEmpty(headers)){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				getMethod.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		getMethod.setQueryString(nameValuePairs);
		try {
			int status = new HttpClient().executeMethod(getMethod);
			if (status == HttpStatus.SC_OK) {
				result = getStringFromGetMethod(getMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return result;
	}
	/**
	 * GET方式请求数据获取资源
	 * @param url
	 * @param nameValuePairs
	 * @return
	 */
	public static String getData(String url, NameValuePair[] nameValuePairs) throws Exception{
		String result = "";
		GetMethod getMethod = new GetMethod(url);
		Header header = new Header();
		header.setName("Content-Type");
		header.setValue("application/x-www-form-urlencoded; charset=UTF-8");
		getMethod.setRequestHeader(header);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		getMethod.setQueryString(nameValuePairs);
		try {
			int status = new HttpClient().executeMethod(getMethod);
			if (status == HttpStatus.SC_OK) {
				result = getMethod.getResponseBodyAsString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return result;
	}
	/**
	 * GET方式请求数据获取资源
	 * @param url
	 * @param nameValuePairs
	 * @return
	 */
	public static String getData(String url, NameValuePair[] nameValuePairs, String type) throws Exception{
		String result = "";
		GetMethod getMethod = new GetMethod(url);
		Header header = new Header();
		header.setName("Content-Type");
		header.setValue("application/x-www-form-urlencoded; charset=UTF-8");
		getMethod.setRequestHeader(header);
		if(ObjectUtils.isEmpty(nameValuePairs)){
			nameValuePairs = new NameValuePair[]{};
		}
		getMethod.setQueryString(nameValuePairs);
		try {
			int status = new HttpClient().executeMethod(getMethod);
			if (status == HttpStatus.SC_OK) {
				result = getStringFromGetMethod(getMethod, type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return result;
	}
}
