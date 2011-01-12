var packageServPath= "com.ztesoft.bsn.customermgr.bll.ServMgr";
var CATALOG_FLAG = "CATALOG";//产品目录标志
var PRODUCT_FLAG = "PRODUCT";
var SELECTED_FALSE = "你选择的是产品目录，请重新选择此节点下的子节点！";

function initPage()
{
		var productList = findAllProductCatal();
		var loadData = new Array();
  if(productList != null)
  {
    for(var i=0 ;i<productList.length;i++)
    {
      loadData[i] = new Object();
      loadData[i].productName = productList[i].catalogItemName;
      loadData[i].productId = productList[i].catalogItemId;
      loadData[i].children = new Array();
      loadData[i].flag = CATALOG_FLAG;
    }
    document.all.productTreeView.loadByData(loadData);
  }
}

function loadProduct()
{
		var selItem = document.all.productTreeView.selectedItem;
		if(selItem != null &&selItem.flag == CATALOG_FLAG&&selItem.items.length == 0)
		{
			var tempVal = findProductProductCatalIdList(selItem.productId);
	    if(tempVal != null)
	    {
	      var loadData = new Array();
	      for(var j=0;j<tempVal.length;j++)
	      {
					loadData[j] = new Object();
		      loadData[j].productName = tempVal[j].productName;
		      loadData[j].productId = tempVal[j].productId;
		      loadData[j].flag = PRODUCT_FLAG;
	      }
	      selItem.insertByData(loadData);
	      //先收拢，再展开
	      selItem.expand(false);   
	      selItem.expand(true);  
			}
		}
}


function findAllProductCatal()
{
	var returnVal = null;
  try{
     returnVal = callRemoteFunction(packageServPath,"findAllProductCatalog");
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
    return false;
  }

  return returnVal;
}

function findProductProductCatalIdList(productId)
{
	var returnVal = null;
  try{
     returnVal = callRemoteFunction(packageServPath,"findProductByCatalogItemId",productId);
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
    return false;
  }

  return returnVal;
}

function setProductId()
{
		var srcNode = document.all.productTreeView.selectedItem;
		var items = document.all.productTreeView.checkedItems;
		if(items!=''){
				var node = items[0];
				var obj = new Object();
				obj.Id = node.productId;
				obj.DisplayName = node.productName;
				returnValue = obj;
				for(var i = 0; i <items.length; i++)
				{
				    if(items[i].flag == CATALOG_FLAG)
				    {
				    	items[i].setChecked(false);
				    	ErrorHandle(null,2,1,SELECTED_FALSE,null);
   						return;
				    }
						if(srcNode.productId != items[i].productId)
						{
								items[i].setChecked(false);
						}
				}
		}
}


function fncOk()
{
		window.opener=null;
		window.close(); 
}
function fncCancel()
{
	window.opener=null;
	window.close();
}