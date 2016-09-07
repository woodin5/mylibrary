package com.wmz.mylibrary.utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * Title: UploadUtil
 * Description: 实现文件上传的工具类
 * Company: Deyiff
 * @author Kevin
 * @date 2014年5月20日
 */
public class UploadUtil {

	public static final String SUCCESS = "1";
	public static final String FAILURE = "0";
	public static final String IMG_TYPE = "img[]";
	public static final String AUDIO_TYPE = "audio[]";
	public static final String FILE_CONTENT_TYPE = "application/octet-stream";
	
	private static final String SEND_TYPE = "POST"; 
	private static final int CONNECT_TIME_OUT = 5000;			//连接超时时间
	private static final int READ_TIME_OUT = 120000;			//超时时间
	private static final String CHARSET = "utf-8";		//编码格式

	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String BOUNDARY = "--------------deyi";
	
	private static final int UPLOAD_SUCCESS = 200;
	
	/**
	 *
	 * @param header
	 * @param requestURL 上传路径
	 * @param formParams 请求参数 key为参数名，value为参数值
	 * @param formFiles 要上传的文件
	 * @return
	 */
	public static String post(HashMap<String, String> header, String requestURL, Map<String, String> formParams, FormFile... formFiles){
		
		URL url = null;
		HttpURLConnection conn = null;
		
		try {
			url = new URL(requestURL);			
			conn = (HttpURLConnection)url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setReadTimeout(READ_TIME_OUT);
			conn.setConnectTimeout(CONNECT_TIME_OUT);
			conn.setRequestMethod(SEND_TYPE);
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
			
			if(header != null) {
				Set<Entry<String, String>> entrys = header.entrySet();
				for(Entry<String, String> entry : entrys) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			
			DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());

			if(formParams != null && formParams.size() > 0) {
				StringBuffer sBuffer = new StringBuffer();
				//上传表单参数部分
				for(Entry<String, String> entry : formParams.entrySet()){
					sBuffer.append(PREFIX).append(BOUNDARY).append(LINE_END);
					sBuffer.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
					sBuffer.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END + LINE_END);
					sBuffer.append(entry.getValue());
					sBuffer.append(LINE_END);
				}
				outStream.write(sBuffer.toString().getBytes());
			}

			if(formFiles != null && formFiles.length > 0) {
				//上传文件部分
				for(FormFile formFile : formFiles){
					System.out.print("filePath="+formFile.filePath);
					if(TextUtils.isEmpty(formFile.filePath)){
						break;
					}
					File file = new File(formFile.filePath);
					if(!file.exists()) {
						continue;
					}
					
					StringBuffer fileBuffer = new StringBuffer();
					fileBuffer.append(PREFIX).append(BOUNDARY).append(LINE_END);
					fileBuffer.append("Content-Disposition: form-data; name=\"" + formFile.formName + "\"; filename=\"" + new File(formFile.filePath).getName() + "\"" + LINE_END);
					fileBuffer.append("Content-Type: " + formFile.contentType + "; charset=" + CHARSET + LINE_END + LINE_END);					
					outStream.write(fileBuffer.toString().getBytes());
					
					InputStream fileInputStream = new FileInputStream(file);					
					byte[] buffer = new byte[32*1024];
					int len = 0;
					while((len=fileInputStream.read(buffer)) != -1){
						outStream.write(buffer, 0, len);
					}
					fileInputStream.close();
					outStream.write(LINE_END.getBytes());
				}
			}
			
			String endFlag = PREFIX + BOUNDARY + PREFIX + LINE_END;
			outStream.write(endFlag.getBytes());
			
			outStream.flush();
			outStream.close();
			
			int responseCode = conn.getResponseCode();
			
			if(responseCode == UPLOAD_SUCCESS) {
				InputStream is = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(isr);
				StringBuffer response = new StringBuffer();
				int resLen = 0;
				while((resLen = isr.read()) != -1){
					response.append((char)resLen);
				}
				return response.toString();
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn!=null) {
				conn.disconnect();
			}
		}

		return null;
	}
}
