package com.ztesoft.crm.salesres.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollectionUtil {

    /**
     * 从list中选出reqNum个数据
     * @param list
     * @param totalNum
     * @param reqNum
     * @return
     */
    public List selRandomList(List list,int reqNum){
    	if(list==null||list.size()<=0||reqNum<=0||list.size()<=reqNum)
    		return list;
    	int totalNum = list.size();
    	List rtn = new ArrayList();
    	int[] tempArr = this.geneRandomNum(totalNum, reqNum);
    	for(int i=0;i<tempArr.length;i++){
    		rtn.add(list.get(tempArr[i]));
    	}
    	return rtn;
    }
    
    /**
     * 从制定的totalNum个数字中随机取出reqNum个数字出来。数字从1到totalNum
     * @param totalNum
     * @param reqNum
     * @return
     */
    public int[] geneRandomNum(int totalNum,int reqNum){
       if(totalNum<=0||reqNum<=0||reqNum>totalNum)
    	   return null;
       int totalArr[] = new int[totalNum];
       int reqArr[] = new int[reqNum];
       for(int i=0;i<totalNum;i++){
    	   totalArr[i]=i;
       }
       Random ran = new Random();
       int round = totalNum;
       int tempArr[] = totalArr;
       for(int j=0;j<reqNum;j++){
    	   int temp = ran.nextInt(round);
           reqArr[j] = tempArr[temp];
           int tempArr2[] = new int[round-1];
           for(int k=0,p=0;k<round;k++){
        	   if(k!=temp){
        	     tempArr2[p] = tempArr[k];  
        		 p++;
        	   }
           }
           tempArr = tempArr2;
           round--;
       }
       return reqArr;
    }
	
    /**
     * 得到固定长度的随机数，这个长度要小于16
     * @param length
     * @return
     */
    public static String getFixRandNum(int length){
    	if(length<1)
    		return "0";
    	double ran = Math.random();
    	String result = String.valueOf(ran).substring(2, 2+length);
    	return result;
    }
    
}
