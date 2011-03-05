/*******************************************
树节点信息,IsSpread代表此节点是否曾被展开过，
ChildrenList为子节点列表，若从未被展开过则
ChildrenList为空
********************************************/

function TreeNodeModel() {
  this.Id;
  this.Name;
  this.IsSpread;
  this.ChildrenList;
}


/*******************************************
获得给定节点的子节点列表
nodeIndex：给定节点的索引，若为空则Get根节点
obj：存储所有节点的对象，以给定节点的索引值为
     索引，须定义为 var obj = {}
********************************************/

function GetChildrenNodes(nodeIndex,obj)
{
	var treenode = new TreeNodeModel();
	
	var m = 0;
	
	if(nodeIndex == null)  //Get根节点，存入obj
	{
		var arr=new Array();
		var arrAreaDtos = GetAreaInfoList();
		for (i = 0; i < arrAreaDtos.length; i++) 
		{
			arr[i]=new Object();
			arr[i].text = arrAreaDtos[i].name;
			arr[i].id = arrAreaDtos[i].areaId;
			
			treenode.Id = arr[i].id;
			treenode.Name = arr[i].text;
			treenode.IsSpread = false;
			treenode.ChildrenList = null;
			obj[m] = treenode;  
			m++;
		}
		return arr;
	}

	else
	{	
		if(obj['nodeIndex'].IsSpread)  //曾被展开过，直接从obj中读出子节点列表
			return obj['nodeIndex'].ChildrenList;
		else   //从后台取数据，存入obj
			{
				var arr=new Array();
				var arrAreaDtos = GetSubAreaInfoList("obj['nodeIndex'].Id");
				for (i = 0; i < arrAreaDtos.length; i++) 
				{
					arr[i]=new Object();
					arr[i].text = arrAreaDtos[i].name;
					arr[i].id = arrAreaDtos[i].areaId;
			
					treenode.Id = arr[i].id;
					treenode.Name = arr[i].text;
					treenode.IsSpread = false;
					treenode.ChildrenList = null;
					
					var childIndex = nodeIndex + "." + m;
					obj['childIndex'] = treenode;
					m++;
				}
				obj['nodeIndex'].IsSpread = true;
				obj['nodeIndex'].ChildrenList = arr;
				
				return arr;
			}
			
	}
}