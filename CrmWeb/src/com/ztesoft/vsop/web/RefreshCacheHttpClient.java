package com.ztesoft.vsop.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
/**
 * 
 * @author cooltan
 *
 */
public class RefreshCacheHttpClient {
	private static Logger logger = Logger.getLogger(RefreshCacheHttpClient.class);
	private static RefreshCacheHttpClient instance =new RefreshCacheHttpClient();
	
	private RefreshCacheHttpClient(){
		
	}
	public static RefreshCacheHttpClient getInstance(){
		return instance;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map map=new HashMap();
		map.put("objectType", "2");
		map.put("objectId", "1");
		RefreshCacheHttpClient.getInstance().
		refreshAllServerCaches("0","1");
		//System.out.println("ret="+ret);

	}
	
	/**
	 * ����ˢ�¸����������Ļ���
	 * @param objectType
	 * @param objectId
	 */
	public void refreshAllServerCaches(String objectType,String objectId){
		//ע�����õ�url����http://10.40.199.142:8002/VsopWeb,http://10.40.199.142:8003/VsopWeb (also separate by semicolon is compatible)
		try {
			String urls =DcSystemParamManager.getInstance().getParameter("VSOP_CLUSTER_URLS");
			logger.info("urls="+urls);
			if (urls == null || "".equals(urls)) {// ���÷Ǽ�Ⱥ,ϵͳ�������Բ�����
				InitConfigServlet aInitConfigServlet = new InitConfigServlet();
				aInitConfigServlet.refreshCacheIncrement(objectType, objectId);
			} else {// ��Ⱥ��������ͨ��http����ˢ�¸�������
				String[] urla = null;
				if (urls.indexOf(",") != -1) {
					urla = urls.split(",");
				} else {
					urla = urls.split(";");
				}
				//logger.info("urla="+urla);
				if (urla != null) {
					for (int i = 0; i < urla.length; i++) {
						String url = urla[i];
						Map map = new HashMap();
						map.put("objectType", objectType);
						map.put("objectId", objectId);
						logger.info("url="+url+",objectType="
							+ objectType + ",objectId=" + objectId);
						String tmp=this.send(url + "/servlet/InitConfigServlet", "GET",map);
						logger.info("SuccessRefreshOneServerCaches,objectType="
								+ objectType + ",objectId=" + objectId+",ret="+tmp);
					}
				}
				logger.info("����ˢ�¸�������������ɹ� SuccessRefreshAllServerCaches,objectType="
						+ objectType + ",objectId=" + objectId);
			}
		} catch (Exception ex) {//�쳣�ڲ���������Ӱ������ҵ�����
			ex.printStackTrace();
			logger.error("����ˢ�¸�������������ʧ�� ErrorRefreshAllServerCaches,objectType="
					+ objectType + ",objectId=" + objectId, ex);
		}
		
	}
	/**
	 * ����Http����
	 * @param urlString
	 * @param method
	 * @param parameters
	 * @return
	 */
	public String send(String urlString, String method, Map parameters) {   
        HttpURLConnection urlConnection = null;   
        BufferedReader bufferedReader=null;
        InputStream in=null;
        StringBuffer temp = new StringBuffer("");
        try {
        	//get����ƴװ
			if (method.equalsIgnoreCase("GET") && parameters != null) {
				StringBuffer param = new StringBuffer();
				int i = 0;
				Set set = parameters.keySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					if (i == 0)
						param.append("?");
					else
						param.append("&");
					param.append(key).append("=").append(parameters.get(key));
					i++;
				}
				urlString += param;
			}
			//��������
			URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod(method);
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(false);
			//urlConnection.connect();

			//post��������
			if (method.equalsIgnoreCase("POST") && parameters != null) {
				StringBuffer param = new StringBuffer();
				Set set = parameters.keySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					param.append("&");
					param.append(key).append("=").append(parameters.get(key));
				}
				urlConnection.getOutputStream().write(
						param.toString().getBytes());
				urlConnection.getOutputStream().flush();
				urlConnection.getOutputStream().close();
			}

			//��ȡ���ؽ�� ���ؽ��δ�õ�
			in = urlConnection.getInputStream();
			bufferedReader = new BufferedReader(
					new InputStreamReader(in));
			String line = bufferedReader.readLine();
			while (line != null) {
				temp.append(line);
				line = bufferedReader.readLine();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("", ex);
		} finally {
			if(bufferedReader!=null){
        		try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
        	}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
            if (urlConnection != null)   
                urlConnection.disconnect();  
		}
		return temp.toString();
    } 
}
