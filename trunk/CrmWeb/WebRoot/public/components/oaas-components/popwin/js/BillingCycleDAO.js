/*************************************************************************
  File Name:BillingCycleDAO.js
	Author:zhang.jing
	create Version:01.00.001
	Create Version Date:2005-03-17
	modify Version:
	modify Version Date:
	Description:
*************************************************************************/

/******************************************************
 *�������ܣ���ȡ���������б�
 *���������billingCycleTypeId	������������
 						beginDate	��ʼʱ��
 						endDate	��ֹʱ��
 *�� �� ֵ��list-���������б�
 ******************************************************/
function queryBillCycle(beginDate,endDate)
{
		var list = null;
    try
    {
      list = callRemoteFunction("com.ztesoft.bsn.cashmgr.bll.QueryCommonMgr","findBillingCycleWithoutType",beginDate,endDate);
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
 *�������ܣ���ȡ�����˺�����������б�
 *���������billingCycleTypeId	������������
 						beginDate	��ʼʱ��
 						endDate	��ֹʱ��
 *�� �� ֵ��list-���������б�
 ******************************************************/
function queryAfterStrikeBillCycle(regionId,beginDate,endDate)
{
		var list = null;
    try
    {
      list = callRemoteFunction("com.ztesoft.bsn.cashmgr.bll.QueryCommonMgr",
      	"findAfterStrikeBCList",regionId,beginDate,endDate);
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
 *�������ܣ���ȡ����δ�����˵����������б�
 *���������billingCycleTypeId	������������
 						beginDate	��ʼʱ��
 						endDate	��ֹʱ��
 *�� �� ֵ��list-���������б�
 ******************************************************/
function findAvailNotStrikeByRegionId(regionId,beginDate,endDate)
{
		var list = null;
    try
    {
      list = callRemoteFunction("com.ztesoft.bsn.cashmgr.bll.QueryCommonMgr",
      	"findAvailNotStrikeByRegionIdArr",regionId,beginDate,endDate);
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
 *�������ܣ�����regionId��ȡ���������б�
 *���������regionId	��������ʶ
 						beginDate	��ʼʱ��
 						endDate	��ֹʱ��
 *�� �� ֵ��list-���������б�
 ******************************************************/
function findBCList(regionId,beginDate,endDate)
{
	var list = null;
  try
  {
    list = callRemoteFunction("com.ztesoft.bsn.plusminusmgr.bll.AdjustMgr","findBCList",regionId,beginDate,endDate);
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
 *�������ܣ���ѯ����ǰ������
 *���������beginDate	��ʼʱ��
 						endDate	��ֹʱ��
 *�� �� ֵ��list-���������б�
 ******************************************************/
function findBeforeBCList(beginDate,endDate)
{
  var list = null;
  try
  {
    list = callRemoteFunction("com.ztesoft.bsn.plusminusmgr.bll.PlusMgr","findBCList",beginDate,endDate);
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
