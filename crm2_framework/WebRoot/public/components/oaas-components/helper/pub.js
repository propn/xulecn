
/*
	通过链表形式构造树，便于扩展
	class treeNode
	{
		id
		text
		children  array of treeNode
	}
	输入参数为treenodes=array of treenode
*/
function createNode(treeName,treeNodes,parentNode)
{
	for (var i=0;i<treeNodes.length;i++)
	{
		var node=treeName.createTreeNode();
		node.setAttribute("ID",treeNodes[i].id);
		node.setAttribute("TEXT",treeNodes[i].text);
		
		if(parentNode==null)
			treeName.add(node);
		else
			parentNode.add(node);
		
		if (treeNodes[i].children!=null)
		{
			{
				createNode(treeName,treeNodes[i].children,node);	
			}
		}
	}
	treeName.expandLevel=1;
}

function TreeRemove(treeName)
{
	var arrNode=treeName.getChildren();
	for (var i=0;i<arrNode.length;i++)
	{
		arrNode[i].remove();
	}
}

 ///将结构体中的值赋给相关的树节点
function GetTreeNodeDatas()
{
	//设定费用列表
	var treeNodes=new Array();	
	len=treeNodes.length;
	treeNodes[treeNodes.length]=new Object();
	treeNodes[len].text="桌面";
	treeNodes[len].id="0";
	
	treeNodes[len].children=new Array();	
	lenChildren=treeNodes[len].children.length;	
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="程序功能变动";
	treeNodes[len].children[lenChildren].id="0.0";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="停机";
	treeNodes[len].children[lenChildren].id="0.1";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="复机";
	treeNodes[len].children[lenChildren].id="0.2";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="拆机";
	treeNodes[len].children[lenChildren].id="0.3";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="过户";
	treeNodes[len].children[lenChildren].id="0.4";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="修改帐务关系";
	treeNodes[len].children[lenChildren].id="0.5";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="修改用户密码";
	treeNodes[len].children[lenChildren].id="0.6";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="资费计划变动";
	treeNodes[len].children[lenChildren].id="0.7";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="故障申请";
	treeNodes[len].children[lenChildren].id="0.8";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="客户咨询";
	treeNodes[len].children[lenChildren].id="0.9";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="客户投诉";
	treeNodes[len].children[lenChildren].id="0.10";
	
	lenChildren=treeNodes[len].children.length;
	treeNodes[len].children[lenChildren]=new Object();
	treeNodes[len].children[lenChildren].text="客户建议";
	treeNodes[len].children[lenChildren].id="0.10";
	
	return treeNodes;
}

function createNodes(treeName)
{
	//var lst = new Array();
	var node;
	try
	{
    	var retobj = callRemoteFunction("DBOperService","QueryServiceByCust","qryAreaList", null,null);
    }
    catch(e)
    {
        ErrorHandle("后台错误",1, 1, "DBOperService\nQueryServiceByCust\nqryAreaList", e);
		return;
    }
	
	//lst.RecordCount = retobj.RecordCount;
	var sRows = retobj.DataSet;
	for(var i=0;i<sRows.length;i++)
	{
		var treeNode = treeName.createTreeNode();
		treeNode.setAttribute("ID",sRows[i][0]);
		treeNode.setAttribute("TEXT",sRows[i][2]);		
				
		if(sRows[i][1] == "")
		{
			treeName.add(treeNode);
			//lst[i] = treeNode;
			node = treeNode;
		}
		else
		{
			/*for(j=0;j<lst.length;j++)
			{
				var node = lst[j];
				if(node.getAttribute("ID") == sRows[i][1])
				{
					node.add(treeNode);
				}
			}
			lst[i] = treeNode;*/
			if(node.getAttribute("ID") == sRows[i][1])
			{
				node.add(treeNode);
			}
			else
			{
				getChildNodes(node,treeNode,sRows[i][1]);
			}
		}
	}
}

function getChildNodes(node,treeNode,sRow)
{
	var children = node.getChildren();
	if(children == null) return;
	
	for(var j=0;j<children.length;j++)
	{
		var node2 = children[j];
		if(node2.getAttribute("ID") == sRow)
		{
			node2.add(treeNode);
		}
		else
		{
			getChildNodes(node2);
		}
	}
}