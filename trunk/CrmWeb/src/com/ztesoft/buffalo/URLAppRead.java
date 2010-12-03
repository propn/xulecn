package com.ztesoft.buffalo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.sql.*;
  public class URLAppRead 
  { 
   void display(String stockCode) 
   { 
	   String url = "http://hq.sinajs.cn/list="+stockCode;
	   String stockName = "";//股票名称
	   double  yesterdayAmount = 0;//昨日收盘价
	   double  todayBeginAmount = 0;//今日开盘价
	   double  todayEndAmount = 0;//今日收盘价
	   double  maxAmount = 0;//最高价
	   double  minAmount = 0;//最低价
	   double  amount = 0;//成交金额
	   String  rate = "";//增长率
	   try {   
		   
		   System.setProperty("http.proxyType", "4");
		   System.setProperty("http.proxyPort", "808");
		   System.setProperty("http.proxyHost", "10.40.199.193");
		   System.setProperty("http.proxySet", "true");		  
		   
	       URL u = new URL(url);   
	       byte[] b = new byte[256];   
	       InputStream in = null;   
	       ByteArrayOutputStream bo = new ByteArrayOutputStream();    
           try {   
               in = u.openStream();   
               int i;   
               while ((i = in.read(b)) != -1) {   
                   bo.write(b, 0, i);   
               }   
               String result = bo.toString();   
               String[] stocks = result.split(";");   
               for (int j=0;j<stocks.length-1;j++) {   
                   String[] datas = stocks[j].split(",");
                   for(int k=0;k<datas.length;k++){
                	   switch(k)
                	   {
                	   		case 0://股票名称
                	   			String temp = datas[k].substring(datas[k].lastIndexOf("_")+3,datas[k].lastIndexOf("_")+9);
                	   			temp = datas[k].substring(datas[k].lastIndexOf("_")+11);
                	   			stockName = temp; 			
                	   			break;                	   	 
                	   		case 1://股票开盘价
                	   			todayBeginAmount = Double.parseDouble(datas[k]);
                	   			break;
                	   		case 2://股票昨日收盘价
                	   			yesterdayAmount = Double.parseDouble(datas[k]);
                	   			break;     
                	   		case 3://股票今天收盘价
                	   			todayEndAmount = Double.parseDouble(datas[k]);
                	   			break;
                	   		case 4://股票今天最高价
                	   			maxAmount = Double.parseDouble(datas[k]);
                	   			break;   
                	   		case 5://股票今天最低价
                	   			minAmount = Double.parseDouble(datas[k]);
                	   			break;     
                	   		case 9://股票今天成交金额
                	   			amount = Double.parseDouble(datas[k])/10000;
                	   			break;    
                	   		default:
                	   			break;
                	   }	
                   }
                   //根据对照自己对应数据   
               }   
               bo.reset();   
               rate = Double.toString(((double)Math.round((todayEndAmount-yesterdayAmount)*100/yesterdayAmount*100)/100))+"%";
               //System.out.println(stockCode+" "+stockName+":"+todayEndAmount+" "+rate+ " max:"+ maxAmount +" min:"+minAmount);
               System.out.println(todayEndAmount+" "+yesterdayAmount+" "+ maxAmount +" "+minAmount+" "+amount);
           } catch (Exception e) {   
               System.out.println(e.getMessage());   
           } finally {   
               if (in != null) {   
                   in.close();   
               }   
           }      
	   } catch (Exception ex) {   
	       System.out.println(ex.getMessage());   
	   }  
   }
   
   public static void test(StringBuffer str){
	   str.append("test");
   }

   public static void testInt(int i){
	   i += 2;
   }   
   
   public static void main(String[] args) 
   {  
	   URLAppRead app=new URLAppRead(); 
	   for(int i=600000;i<602000;i++){
		   //app.display("sh"+String.valueOf(i));
	   }
	   for(int i=1;i<3000;i++){
		   int j = i+1000000;
		   String code = "sz"+String.valueOf(j).substring(1);
		   //app.display(code);
	   }	  
	   
	   /*
	   app.display("sh600104");
	   app.display("sh600501");
	   app.display("sz000728");
	   app.display("sh600176");
	   app.display("sh600066");
	   app.display("sh600196");
	   app.display("sz002077");
	   app.display("sh600895");
	   app.display("sh600726");
	   app.display("sh600497");
	   app.display("sh600019");
	   app.display("sh600029");
	   app.display("sz000983");
	   app.display("sz000886");
	   app.display("sh600837");
	   app.display("sh000001");
	   app.display("sz000060");
	   app.display("sh601088");
	   app.display("sh600674");
	   app.display("sh600686");
	   app.display("sh600036");
	   app.display("sh600362");
	   app.display("sh600500");
	   app.display("sh600188");
	   app.display("sz000061");
	   app.display("sh601006");
	   app.display("sh601939");
	   app.display("sh600050");
	   app.display("sh601857");
	   app.display("sh600581");
	   app.display("sh600801");
	   app.display("sh600129");
	   app.display("sz002131");
	   app.display("sh600383");
	   app.display("sh600359");
	   app.display("sz000898");
	   app.display("sh601919");
	   app.display("sh601318");
	   */
	   app.display("sh000001");
	   app.display("sz000680");
	   app.display("sz002364");
	   app.display("sh600362");
	   app.display("sz000717");
	   
   } 
  } 