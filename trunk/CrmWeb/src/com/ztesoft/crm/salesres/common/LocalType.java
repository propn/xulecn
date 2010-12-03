package com.ztesoft.crm.salesres.common;

public final class LocalType
{
	public static final int JX = 2;//����
	public static final int GX = 20;//����
	public static final int CQ = 31;//����
	public static final int HN = 11;//����csss��־
	public static final int TJ = 3;//���crm��־
	
	private static int local = JX;
	
	private LocalType(){}
	
	public static void setLocal(int type)
	{
		local = type;
	}
	
	public static int getLocal()
	{
		return local;
	}
}