/*******************************************
���ڵ���Ϣ,IsSpread����˽ڵ��Ƿ�����չ������
ChildrenListΪ�ӽڵ��б�����δ��չ������
ChildrenListΪ��
********************************************/

function TreeNodeModel() {
  this.Id;
  this.Name;
  this.IsSpread;
  this.ChildrenList;
}


/*******************************************
��ø����ڵ���ӽڵ��б�
nodeIndex�������ڵ����������Ϊ����Get���ڵ�
obj���洢���нڵ�Ķ����Ը����ڵ������ֵΪ
     �������붨��Ϊ var obj = {}
********************************************/

function GetChildrenNodes(nodeIndex,obj)
{
	var treenode = new TreeNodeModel();
	
	var m = 0;
	
	if(nodeIndex == null)  //Get���ڵ㣬����obj
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
		if(obj['nodeIndex'].IsSpread)  //����չ������ֱ�Ӵ�obj�ж����ӽڵ��б�
			return obj['nodeIndex'].ChildrenList;
		else   //�Ӻ�̨ȡ���ݣ�����obj
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