//���ӡ�����ֵ���Ĳ�ѯ����
function addParamInt(fieldName,fieldValue,inputParams)
{
	var flag = 0;

	if ( inputParams == null )
	{
		inputParams = new Array();
	}
	for (var i=0;i<inputParams.length;i++ )
	{
		if (inputParams[i].ParamName == fieldName)
		{
			flag = 1;
			inputParams[i].ParamValues[inputParams[i].ParamValues.length] = fieldValue;
			break;
		}
	}
	if ( flag != 1)
	{
		var outObj=new Object();
		outObj.ParamName = fieldName;
		outObj.DataType = 1;
		outObj.ParamValues = new Array();

		outObj.ParamValues[outObj.ParamValues.length] = fieldValue;

		inputParams[inputParams.length]= outObj;
	}
}
//���ӡ��ַ���ֵ���Ĳ�ѯ����
function addParamStr(fieldName,fieldValue,inputParams)
{
	var flag = 0;

	if ( inputParams == null )
	{
		inputParams = new Array();
	}
	for (var i=0;i<inputParams.length;i++ )
	{
		if (inputParams[i].ParamName == fieldName)
		{
			flag = 1;
			inputParams[i].ParamValues[inputParams[i].ParamValues.length] = fieldValue;
			break;
		}
	}
	if ( flag != 1)
	{
		var outObj=new Object();
		outObj.ParamName = fieldName;
		outObj.DataType = 2;
		outObj.ParamValues = new Array();

		outObj.ParamValues[outObj.ParamValues.length] = fieldValue;

		inputParams[inputParams.length]= outObj;
	}
}

//���ӡ�������ֵ���Ĳ�ѯ����
function addParamDate(fieldName,fieldValue,inputParams)
{
	var flag = 0;

	if ( inputParams == null )
	{
		inputParams = new Array();
	}
	for (var i=0;i<inputParams.length;i++ )
	{
		if (inputParams[i].ParamName == fieldName)
		{
			flag = 1;
			inputParams[i].ParamValues[inputParams[i].ParamValues.length] = fieldValue;
			break;
		}
	}
	if ( flag != 1)
	{
		var outObj=new Object();
		outObj.ParamName = fieldName;
		outObj.DataType = 3;
		outObj.ParamValues = new Array();

		outObj.ParamValues[outObj.ParamValues.length] = fieldValue;

		inputParams[inputParams.length]= outObj;
	}
}


//ȡ(Param[],��inputParams)�ṹ�е���������
function  getParamValuesCount(inputParams)
{
    if (inputParams == null)
    {
		return 0;
    }
    if (inputParams.length == 0)
    {
		return 0;
    }
    var minCount = -1;

    for (var iIndex = 0; iIndex < inputParams.length; iIndex++)
    {
		var paramValues = inputParams[iIndex].ParamValues;

		if ( paramValues == null)
		{
			return 0;
		}
		if (paramValues.length == 0)
		{
			return 0;
		}
        if (paramValues.length < minCount || minCount == -1)
        {
            minCount = paramValues.length;
        }
    }

    return minCount;
}

//ȡ(Param[],��inputParams)�ṹ�е�����-ĳ��ĳ��
function getParamStr(inputParams,rowId,fieldName)
{
	var flag = 0;

	if ( inputParams == null )
	{
		return null;
	}
	//�ж������Ƿ����
	for (var i=0;i<inputParams.length;i++ )
	{
		if (inputParams[i].ParamName == fieldName)
		{
			flag = 1;
			if (inputParams[i].ParamValues == null)
			{
				return null;
			}
			else
			{
				if (rowId > inputParams[i].ParamValues.length)
				{
					return null;
				}
				else
				{
					return inputParams[i].ParamValues[rowId];
				}

			}
			break;
		}
	}

	if (flag != 1)
	{
		//throw  "FiedName is not found :" + fieldName ;
		return null;
	}
}
//���������ֶ�
function addOrderField(fieldName,dataSetFilter)
{
	if (dataSetFilter.OrderFields == null)
	{
		dataSetFilter.OrderFields =new Array();
	}
	dataSetFilter.OrderFields[dataSetFilter.OrderFields.length] = fieldName;
}
//������ʾ�ֶ�
function addShowField(fieldName,dataSetFilter)
{
	if (dataSetFilter.ShowFields == null)
	{
		dataSetFilter.ShowFields =new Array();
	}
	dataSetFilter.ShowFields[dataSetFilter.ShowFields.length] = fieldName;
}

function ParamObjectToParamArray(argObj)
{
	var arrParamInput = new Array();
	for(var key in argObj)
	{
		var paramObj =  new Object();
		paramObj.ParamName=key;
		paramObj.ParamValue=argObj[key];
		arrParamInput[arrParamInput.length] = paramObj;
	}
	return arrParamInput;
}


function ParamArrayToParamObject(paramArray)
{
	var retObj = new Object();
	for(var i=0;i<paramArray.length;i++)
	{
		var key = paramArray[i].ParamName;
		if(key == null) throw "The Parameter name can't be null";
		retObj[key] = paramArray[i].ParamValue;
	}
	return retObj;
}


/*
��̨�Ľṹ����
class QueryResult
{
	long RecordCount;
	string[] ColumnList;
	string[][] DataSet;
}
*/
function QueryResultToObject(result)
{
	var arrTable = new Array();
	arrTable.RecordCount = result.RecordCount;
	for(var i=0;i<result.RecordCount;i++)
	{
		var obj = new Object();
		for(var j=0;j<result.ColumnList.length;j++)
		{
			obj[result.ColumnList[j]] = result.DataSet[i][j];
		}
		arrTable[i] = obj;
	}
	return arrTable;
}

/************************************************************
 �������ƣ�addCallService()
 �������ܣ����ӵ��õķ���
 ���������
			serviceName        :  ��������
			params             :  ������Ҫ�Ĳ����б�
			arrSubCallService  :  �ӷ��������

 �����������
 �� �� ֵ������
 ����˵����
 ************************************************************/
function addCallService(serviceName, params, arrSubCallService)
{
	var objCallService = new Object();

	objCallService.ServiceName = serviceName ;
	objCallService.Params = params ;

	if (arrSubCallService == null)
	{
		objCallService.Callservices = null;
	}
	else
	{
		objCallService.Callservices = new Array();
		for(var i=0;i<arrSubCallService.length;i++)
		{
			objCallService.Callservices[objCallService.Callservices.length] = arrSubCallService[i];
		}
	}

	return objCallService;
}


/*
//�ɵĺ�����
function callRemoteQueryFunction(queryName,argObj,filter)
{
	var retObj = callRemoteFunction("DBOperService","QueryService",queryName,ParamObjectToParamArray(argObj),filter);
	if(retObj == null) return null;
	return QueryResultToObject(retObj);

}
*/
//�Ʒ��û���ѯ�ӿ�__added by yuqianli 2004-10-20
function callRemoteQueryRBFunction(queryName,argObj,filter)
{
	var retObj = callRemoteFunction("RbCommonQueryService","QueryService",queryName,argObj,filter);
	if(retObj == null) return null;
	return QueryResultToObject(retObj);
}

//�Ʒ��û������ӿ�__added by yuqianli 2004-10-21
function callRemoteOperRBFunction(operName,argObj)
{
	var retObj = callRemoteFunction("RbCommonUpdateService","UpdateService",operName,argObj);
	if(retObj == null) return null;
	return QueryResultToObject(retObj);
}
//ͨ�ò�ѯ�ӿ�
function callRemoteQueryFunction(queryName,argObj,filter)
{
	var retObj = callRemoteFunction("CommonQueryService","QueryService",queryName,argObj,filter);
	if(retObj == null) return null;
	return QueryResultToObject(retObj);
}

//ͨ������ɾ���Ľӿ�
function callRemoteDBOperFunction(operName,argObj)
{
	var retArr = callRemoteFunction("CommonUpdateService","UpdateService",operName,argObj);
	return retArr;
	/*
	var retArr = callRemoteFunction("DBOperService","CUDService",operName,ParamObjectToParamArray(argObj));
	if(retArr == null) return null;
	return ParamArrayToParamObject(retArr);
	*/
}

//ͨ������ɾ���Ľӿ�-----���ӷ���(�������)
function callRemoteMultiDBOperFunction(callService)
{
	var retArr = callRemoteFunction("CommonUpdateService","ComplexUpdateCallService",callService);
	return retArr;

	/*
	var retArr = callRemoteFunction("DBOperService","CUDService",operName,ParamObjectToParamArray(argObj));
	if(retArr == null) return null;
	return ParamArrayToParamObject(retArr);
	*/
}
