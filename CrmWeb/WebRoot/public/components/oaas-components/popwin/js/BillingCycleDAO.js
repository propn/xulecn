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
 *函数功能：获取帐务周期列表
 *输入参数：billingCycleTypeId	帐务周期类型
 						beginDate	开始时间
 						endDate	终止时间
 *返 回 值：list-帐务周期列表
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
 *函数功能：获取批冲账后的帐务周期列表
 *输入参数：billingCycleTypeId	帐务周期类型
 						beginDate	开始时间
 						endDate	终止时间
 *返 回 值：list-帐务周期列表
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
 *函数功能：获取出账未批冲账的帐务周期列表
 *输入参数：billingCycleTypeId	帐务周期类型
 						beginDate	开始时间
 						endDate	终止时间
 *返 回 值：list-帐务周期列表
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
 *函数功能：根据regionId获取帐务周期列表
 *输入参数：regionId	本地网标识
 						beginDate	开始时间
 						endDate	终止时间
 *返 回 值：list-帐务周期列表
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
 *函数功能：查询出账前的账期
 *输入参数：beginDate	开始时间
 						endDate	终止时间
 *返 回 值：list-帐务周期列表
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
