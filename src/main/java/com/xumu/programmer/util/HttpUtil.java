package com.xumu.programmer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	
	public String getApiResult(String urlStr, String params) {
        StringBuffer sbf = new StringBuffer();
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(20000);
            conn.setDoOutput(true);
            String paramStr = params;
            byte[] bytes = paramStr.getBytes("UTF-8");
            conn.getOutputStream().write(bytes);
            conn.connect();
            InputStream inStream = conn.getInputStream();
            String strRead = null;
            reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                	e.printStackTrace();
                }
            }
            if (null != conn) {
                conn.disconnect();
            }
        }
        return sbf.toString();
    }
}
