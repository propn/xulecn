/*************************************************************************
  File Name:billingCycleCommDAO.js
	Author:xu.yiliang
	create Version:01.00.001
	Create Version Date:2005-10-27
	modify Version:01.01.010
	modify Version Date:2005-10-27
	Description:
*************************************************************************/

/******************************************************
 *�������ܣ���ȡ���������б�
 *���������billingCycleTypeId	������������
 						beginDate	��ʼʱ��
 						endDate	��ֹʱ��
 *�� �� ֵ��list-���������б�
 ******************************************************/
function findBillCycle(beginDate,endDate)
{
		var list = null;
    try
    {
      list = callRemoteFunction("com.ztesoft.bsn.validatemgr.func.FuncCommon","findBCList",beginDate,endDate);
  	}
    catch(e)
    {
      if(e.Type == '1')
	    {
	      ErrorHandle(null,1,1,"",e.Time+"<br>"+e.Code+":"+e.Desc);
	    }
	    if(e.Type == '2')
	    {
	      ErrorHandle(null,2,1,e.Time+"\n"+e.Code+":"+e.Desc,"");
	    }
      return null;
    }
    return list;
}

/******************************************************
 *�������ܣ��������ȡ���������б�
 *���������billingCycleTypeId	������������
 						beginDate	��ʼʱ��
 						endDate	��ֹʱ��
 *�� �� ֵ��list-���������б�
 ******************************************************/
function findBCListByRegionId(regionId,beginDate,endDate)
{
		var list = null;
    try
    {
      list = callRemoteFunction("com.ztesoft.bsn.validatemgr.func.FuncCommon","findBCListByRegionId",regionId,beginDate,endDate);
  	}
    catch(e)
    {
      if(e.Type == '1')
	    {
	      ErrorHandle(null,1,1,"",e.Time+"<br>"+e.Code+":"+e.Desc);
	    }
	    if(e.Type == '2')
	    {
	      ErrorHandle(null,2,1,e.Time+"\n"+e.Code+":"+e.Desc,"");
	    }
      return null;
    }
    return list;
}

/******************************************************
 *�������ܣ���Ȩ�޻�ȡ���������б�
 *���������regionIdArr	Ȩ��
 						beginDate	��ʼʱ��
 						endDate	��ֹʱ��
 *�� �� ֵ��list-���������б�
 ******************************************************/
function findBCListByRole(regionIdArr,beginDate,endDate)
{
		var list = null;
    try
    {
      list = callRemoteFunction("com.ztesoft.bsn.validatemgr.func.FuncCommon","findBCListByRole",regionIdArr,beginDate,endDate);
  	}
    catch(e)
    {
      if(e.Type == '1')
	    {
	      ErrorHandle(null,1,1,"",e.Time+"<br>"+e.Code+":"+e.Desc);
	    }
	    if(e.Type == '2')
	    {
	      ErrorHandle(null,2,1,e.Time+"\n"+e.Code+":"+e.Desc,"");
	    }
      return null;
    }
    return list;
}