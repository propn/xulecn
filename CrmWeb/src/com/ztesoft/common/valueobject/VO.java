package com.ztesoft.common.valueobject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;



/**
 * @Classname : VO
 * @Description : �������ݶ���ӿ�,����ÿ����ֵ���� ��Ӧ�Ľӿں���ʹ�ô�����������������
 * @Copyright ?? 2006 ZTEsoft.
 * @Author : llh
 * @Create Date : 2006-3-22
 * 
 * @Last Modified :
 * @Modified by :
 * @Version : 1.0
 */
public interface VO extends Serializable {
	// �����ݵ�����HashMap�У� HashMap�Ĺؼ���Ϊ��Ӧ�ı��ֶ�����
	// ʹ�ô�������������
	public HashMap unloadToHashMap();

	// ��HashMap�ж�ȡ����, ʹ�ô�������������
	public void loadFromHashMap(HashMap map);

	// ��ȡ�ؼ����б� ʹ�ô�������������
	public List getKeyFields();

	// ��ȡ��Ӧ�ı����� ʹ�ô�������������
	String getTableName();
}
