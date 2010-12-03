package com.ztesoft.crm.salesres.common;

public final class LocalType
{
	public static final int JX = 2;//江西
	public static final int GX = 20;//广西
	public static final int CQ = 31;//重庆
	public static final int HN = 11;//河南csss标志
	public static final int TJ = 3;//天津crm标志
	
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