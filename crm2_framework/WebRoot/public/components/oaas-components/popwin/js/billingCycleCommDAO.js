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
 *函数功能：获取帐务周期列表
 *输入参数：billingCycleTypeId	帐务周期类型
 						beginDate	开始时间
 						endDate	终止时间
 *返 回 值：list-帐务周期列表
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
 *函数功能：按区域获取帐务周期列表
 *输入参数：billingCycleTypeId	帐务周期类型
 						beginDate	开始时间
 						endDate	终止时间
 *返 回 值：list-帐务周期列表
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
 *函数功能：按权限获取帐务周期列表
 *输入参数：regionIdArr	权限
 						beginDate	开始时间
 						endDate	终止时间
 *返 回 值：list-帐务周期列表
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