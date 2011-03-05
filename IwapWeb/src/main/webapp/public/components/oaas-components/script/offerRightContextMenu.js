////////////////////////////////////////////////////////////////////////////////////
/////右鍵菜單類(zfengs 2009-07-02)
///////////////////////////////////////////////////////////////////////////////////
var bagMenu = new packageContextMenu("bagConfigMenu",140);

function MenuHelper(){}

MenuHelper.prototype = {
	//初始化增加熱賣
	initAddMenu : function(){
		bagMenu.addItem('加入热卖',"menuHelper.addToPopDefine()");
		bagMenu.create();
	},
	/*initDelIncrProdMenu : function(){
		bagMenu.addItem('删除',"menuHelper.delIncrProdRecord()");
		bagMenu.create();
	},
	
	delIncrProdRecord : function(){
		//new IncrProdDealer().deleteRecordOnRightClick();
	},*/
	//弹出增加热卖右键菜单
	popAddMenu : function(){
		bagMenu.clearItems();
		this.initAddMenu();
		bagMenu.popMenu();		
	},	
	
	//初始化刪除熱賣
	initRemoveMenu : function(){
		bagMenu.addItem('从热卖中删除',"menuHelper.delFromPopDefine()");
		bagMenu.create();
	},
	
	//弹出删除热卖按钮菜单
	popRemoveMenu : function(){
		bagMenu.clearItems();
		this.initRemoveMenu();
		bagMenu.popMenu();
	},
	
	//弹出删除附属产品
	popRemoveIncrProdMenu : function(){
		bagMenu.clearItems();
		this.initDelIncrProdMenu();
		bagMenu.popMenu();
	},
	//弹出删除附属产品右键菜单
	popRemoveOtherProdMenu : function(){
		bagMenu.clearItems();
		bagMenu.addItem('删除该附属产品',"deleteOnRightClick()");
		bagMenu.create();
		bagMenu.popMenu();		
	},
	//增加热卖
	addToPopDefine : function(){
		//获取当前活动页
		var tabIndex = -1;
		if($("servTabs"))
		   tabIndex = $("servTabs").getSelectedPageIndex();
		
		if(tabIndex == 0){
			//列表页
			var selItem=sellTreeView.selectedItem;
			
			//alert( selItem.offerId)
			if (typeof(selItem) == "undefined" || selItem == null
					|| selItem.offerId == "" )
				return;
				
			var offerId = selItem.offerId;	
			
			NDAjaxCall.getSyncInstance().remoteCall("UasSellService","addPopDef",[offerId ],this.callBack);
				
		}else{
			//查找页
			var dataset = OfferSearchInfoDataset.getCurrent();
			
			if(!dataset){return}
			
			var offerId = dataset.getValue("offerId");
			
			NDAjaxCall.getSyncInstance().remoteCall("UasSellService","addPopDef",[offerId ],this.callBack);
		
		}
		
		
		
	},
	
	//删除热卖
	delFromPopDefine : function(){
		var record = OfferHotSaleDataset.getCurrent();
		if(record){
			
			var offerId = record.getValue("productOfferId");
		
			NDAjaxCall.getSyncInstance().remoteCall("UasSellService","delPopDef",[offerId ],this.refresh);	
		}
	},
	
	//回调函数
	callBack : function( reply ){
        alert(reply.getResult());
        menuHelper.refresh.apply(menuHelper,arguments);
    },
    
    //刷新热卖套餐
    refresh : function(){
  	  loadHotSaleData();
    },
	
	//绑定菜单
	initBindMenuFor : function(){
		//绑定热卖数据
		try{
			$('table_OfferHotSaleDataset').oncontextmenu = function(){
				menuHelper.popRemoveMenu.apply(menuHelper,arguments);
			};
		}catch(e){}	
		try{
			//绑定查询数据集
			$('table_OfferSearchInfoDataset').oncontextmenu = function(){
				menuHelper.popAddMenu.apply(menuHelper,arguments);
			};
		}catch(e){}
		try{
			//绑定查询数据集
			$('table_buyedOtherProd').oncontextmenu = function(){
				menuHelper.popRemoveOtherProdMenu.apply(menuHelper,arguments);
			};
		}catch(e){}
		//绑定查询数据集
		/*$('table_incrProdDataset').oncontextmenu = function(){
			menuHelper.popRemoveIncrProdMenu.apply(menuHelper,arguments);
		};*/
	}
}
//创建右键菜单对象
var menuHelper = new MenuHelper();
menuHelper.initBindMenuFor();
//////////////////////////////////////////////////
////热卖对象定义
/////////////////////////////////////////////////
var popDef = {
	//工号
	operId : "1",
	
	//默认为1
	popType : "1",
	
	//套餐ID
	popDefn : "",
	
	//序列
	orderbyId : "1",
	
	//备注	
	note : ""
}