////////////////////////////////////////////////////////////////////////////////////
/////���I�ˆ��(zfengs 2009-07-02)
///////////////////////////////////////////////////////////////////////////////////
var bagMenu = new packageContextMenu("bagConfigMenu",140);

function MenuHelper(){}

MenuHelper.prototype = {
	//��ʼ�����ӟ��u
	initAddMenu : function(){
		bagMenu.addItem('��������',"menuHelper.addToPopDefine()");
		bagMenu.create();
	},
	/*initDelIncrProdMenu : function(){
		bagMenu.addItem('ɾ��',"menuHelper.delIncrProdRecord()");
		bagMenu.create();
	},
	
	delIncrProdRecord : function(){
		//new IncrProdDealer().deleteRecordOnRightClick();
	},*/
	//�������������Ҽ��˵�
	popAddMenu : function(){
		bagMenu.clearItems();
		this.initAddMenu();
		bagMenu.popMenu();		
	},	
	
	//��ʼ���h�����u
	initRemoveMenu : function(){
		bagMenu.addItem('��������ɾ��',"menuHelper.delFromPopDefine()");
		bagMenu.create();
	},
	
	//����ɾ��������ť�˵�
	popRemoveMenu : function(){
		bagMenu.clearItems();
		this.initRemoveMenu();
		bagMenu.popMenu();
	},
	
	//����ɾ��������Ʒ
	popRemoveIncrProdMenu : function(){
		bagMenu.clearItems();
		this.initDelIncrProdMenu();
		bagMenu.popMenu();
	},
	//����ɾ��������Ʒ�Ҽ��˵�
	popRemoveOtherProdMenu : function(){
		bagMenu.clearItems();
		bagMenu.addItem('ɾ���ø�����Ʒ',"deleteOnRightClick()");
		bagMenu.create();
		bagMenu.popMenu();		
	},
	//��������
	addToPopDefine : function(){
		//��ȡ��ǰ�ҳ
		var tabIndex = -1;
		if($("servTabs"))
		   tabIndex = $("servTabs").getSelectedPageIndex();
		
		if(tabIndex == 0){
			//�б�ҳ
			var selItem=sellTreeView.selectedItem;
			
			//alert( selItem.offerId)
			if (typeof(selItem) == "undefined" || selItem == null
					|| selItem.offerId == "" )
				return;
				
			var offerId = selItem.offerId;	
			
			NDAjaxCall.getSyncInstance().remoteCall("UasSellService","addPopDef",[offerId ],this.callBack);
				
		}else{
			//����ҳ
			var dataset = OfferSearchInfoDataset.getCurrent();
			
			if(!dataset){return}
			
			var offerId = dataset.getValue("offerId");
			
			NDAjaxCall.getSyncInstance().remoteCall("UasSellService","addPopDef",[offerId ],this.callBack);
		
		}
		
		
		
	},
	
	//ɾ������
	delFromPopDefine : function(){
		var record = OfferHotSaleDataset.getCurrent();
		if(record){
			
			var offerId = record.getValue("productOfferId");
		
			NDAjaxCall.getSyncInstance().remoteCall("UasSellService","delPopDef",[offerId ],this.refresh);	
		}
	},
	
	//�ص�����
	callBack : function( reply ){
        alert(reply.getResult());
        menuHelper.refresh.apply(menuHelper,arguments);
    },
    
    //ˢ�������ײ�
    refresh : function(){
  	  loadHotSaleData();
    },
	
	//�󶨲˵�
	initBindMenuFor : function(){
		//����������
		try{
			$('table_OfferHotSaleDataset').oncontextmenu = function(){
				menuHelper.popRemoveMenu.apply(menuHelper,arguments);
			};
		}catch(e){}	
		try{
			//�󶨲�ѯ���ݼ�
			$('table_OfferSearchInfoDataset').oncontextmenu = function(){
				menuHelper.popAddMenu.apply(menuHelper,arguments);
			};
		}catch(e){}
		try{
			//�󶨲�ѯ���ݼ�
			$('table_buyedOtherProd').oncontextmenu = function(){
				menuHelper.popRemoveOtherProdMenu.apply(menuHelper,arguments);
			};
		}catch(e){}
		//�󶨲�ѯ���ݼ�
		/*$('table_incrProdDataset').oncontextmenu = function(){
			menuHelper.popRemoveIncrProdMenu.apply(menuHelper,arguments);
		};*/
	}
}
//�����Ҽ��˵�����
var menuHelper = new MenuHelper();
menuHelper.initBindMenuFor();
//////////////////////////////////////////////////
////����������
/////////////////////////////////////////////////
var popDef = {
	//����
	operId : "1",
	
	//Ĭ��Ϊ1
	popType : "1",
	
	//�ײ�ID
	popDefn : "",
	
	//����
	orderbyId : "1",
	
	//��ע	
	note : ""
}