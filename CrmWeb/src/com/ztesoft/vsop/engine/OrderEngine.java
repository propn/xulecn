package com.ztesoft.vsop.engine;

import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.IAction;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.service.business.AProductModifyService;
import com.ztesoft.vsop.engine.service.business.ChangeUIMCardService;
import com.ztesoft.vsop.engine.service.business.InstallService;
import com.ztesoft.vsop.engine.service.business.ModifyServiceFunctionService;
import com.ztesoft.vsop.engine.service.business.OrderAuthService;
import com.ztesoft.vsop.engine.service.business.OrderRelationOfferModifyService;
import com.ztesoft.vsop.engine.service.business.OrderRelationQryService;
import com.ztesoft.vsop.engine.service.business.OrderRelationSynService;
import com.ztesoft.vsop.engine.service.business.OrderRelationSynServiceFromX;
import com.ztesoft.vsop.engine.service.business.ProdInstDisassembleService;
import com.ztesoft.vsop.engine.service.business.ProdInstPayModeModifyService;
import com.ztesoft.vsop.engine.service.business.ProdNoModifyService;
import com.ztesoft.vsop.engine.service.business.TransferUserService;
import com.ztesoft.vsop.engine.service.business.UserStateChangeService;
import com.ztesoft.vsop.engine.service.business.VProductCancelService;
import com.ztesoft.vsop.engine.service.business.VProductModifyService;
import com.ztesoft.vsop.engine.service.business.VproductAllCancelOrderService;
import com.ztesoft.vsop.engine.service.business.VproductOrderService;
import com.ztesoft.vsop.engine.service.business.WorkSheetAcceptService;
/**
 * 服务引擎:事务管理、服务扭转
 * @author cooltan
 *
 */
public class OrderEngine implements IAction {
	protected static Logger logger = Logger.getLogger(OrderEngine.class);
	//0：订购
	public static final int SERVICE_APPLY0=0;
	public static final String SERVICE_APPLY0_STR="0";
	//1：退订
	public static final int SERVICE_CANCEL1=1;
	public static final String SERVICE_CANCEL1_STR="1";
	//2：全部退订
	public static final int SERVICE_CANCELALL2=2;
	public static final String SERVICE_CANCELALL2_STR="2";
	//10：新装
	public static final int SERVICE_INSTALL10=10;
	public static final String SERVICE_INSTALL10_STR="10";
	//11：用户状态变更
	public static final int SERVICE_CHGUSERSTATE11=11;
	public static final String SERVICE_CHGUSERSTATE11_STR="11";
	//12：改号
	public static final int SERVICE_CHGACCNBR12=12;
	public static final String SERVICE_CHGACCNBR12_STR="12";
	//13：改业务能力附属产品
	public static final int SERVICE_CHGAPRODUCTS13=13;
	public static final String SERVICE_CHGAPRODUCTS13_STR="13";
	//14：改增值产品
	public static final int SERVICE_CHGVPRODUCTS14=14;
	public static final String SERVICE_CHGVPRODUCTS14_STR="14";
	//15：拆机
	public static final int SERVICE_UNINSTALL15=15;
	public static final String SERVICE_UNINSTALL15_STR="15";
	//16：付费类型变更
	public static final int SERVICE_CHGPAYMODE16=16;
	public static final String SERVICE_CHGPAYMODE16_STR="16";
	//17：换UIM卡 xulei20100930
	public static final int SERVICE_CHANGEUIM17=17;
	public static final String SERVICE_CHANGEUIM17_STR="17";
	//20:改服务功能（包括13，14）
	public static final int SERVICE_MODIFYSERVICEFUNCTION20=20;
	public static final String SERVICE_MODIFYSERVICEFUNCTION20_STR="20";
	//21:用户移机（包括12,13）
	public static final int SERVICE_TRANSFERUSER21=21;
	public static final String SERVICE_TRANSFERUSER_STR="21";
	//30:ISMP订购关系同步
	public static final int SERVICE_ORSYNFROMISMP30=30;
	public static final String SERVICE_ORSYNFROMISMP30_STR="30";
	//31:订购鉴权
	public static final int SERVICE_ORDERAUTH31=31;
	public static final String SERVICE_ORDERAUTH31_STR="31";
	//32:订购关系查询
	public static final int SERVICE_QUERY32=32;
	public static final String SERVICE_QUERY32_STR="32";
	//33:服务开通工单处理
	public static final int SERVICE_WORKSHEETDEAL33=33;
	public static final String SERVICE_WORKSHEETDEAL33_STR="33";
	
	//34:CRM优惠实例同步
	public static final int SERVICE_OFFERCRMTOVSOPSYN34=34;
	public static final String SERVICE_OFFERCRMTOVSOPSYN34_STR="34";
	
	//35:X平台订购关系同步
	public static final int SERVICE_ORSYNFROMX35=35;
	public static final String SERVICE_ORSYNFROMX35_STR="35";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 服务层 
	 * 根据服务编码，完成执行每个服务操作。
	 * @param in
	 * @return
	 */
	public Map innerEngine(Map in) throws Exception{//key includes :serviceCode busiObject   interfaceCode interfaceType
		AbstractBusinessService aAbstractBusinessService=null;
		int serviceCode=Integer.parseInt((String)in.get("serviceCode"));
		switch(serviceCode){
		case SERVICE_APPLY0 :aAbstractBusinessService=new VproductOrderService();break;
		case SERVICE_CANCEL1 :aAbstractBusinessService=new VProductCancelService();break;
		case SERVICE_CANCELALL2 :aAbstractBusinessService=new VproductAllCancelOrderService();break;
		case SERVICE_INSTALL10 :aAbstractBusinessService=new InstallService();break;
		case SERVICE_CHGUSERSTATE11 :aAbstractBusinessService=new UserStateChangeService();break;
		case SERVICE_CHGACCNBR12 :aAbstractBusinessService=new ProdNoModifyService();break;
		case SERVICE_CHGAPRODUCTS13 :aAbstractBusinessService=new AProductModifyService();break;
		case SERVICE_CHGVPRODUCTS14 :aAbstractBusinessService=new VProductModifyService();break;
		case SERVICE_UNINSTALL15 :aAbstractBusinessService=new ProdInstDisassembleService();break;
		case SERVICE_CHGPAYMODE16 :aAbstractBusinessService=new ProdInstPayModeModifyService();break;
		case SERVICE_CHANGEUIM17 :aAbstractBusinessService=new ChangeUIMCardService();break;
		case SERVICE_ORSYNFROMISMP30:aAbstractBusinessService=new OrderRelationSynService();break;
		case SERVICE_ORDERAUTH31:aAbstractBusinessService=new OrderAuthService();break;
		case SERVICE_QUERY32:aAbstractBusinessService=new OrderRelationQryService();break;
		case SERVICE_WORKSHEETDEAL33:aAbstractBusinessService=new WorkSheetAcceptService();break;
		case SERVICE_MODIFYSERVICEFUNCTION20:aAbstractBusinessService=new ModifyServiceFunctionService();break;
		case SERVICE_TRANSFERUSER21:aAbstractBusinessService=new TransferUserService();break;
		case SERVICE_OFFERCRMTOVSOPSYN34:aAbstractBusinessService=new OrderRelationOfferModifyService();break;
		case SERVICE_ORSYNFROMX35:aAbstractBusinessService=new OrderRelationSynServiceFromX();break;
		default:break;
		}
		
		Map out =aAbstractBusinessService.service(in);
		return out;
	}
	/**
	 * 事务层
	 * 进行事务管理，最终的业务操作由innerEngine完成。
	 * @param in
	 * @return
	 */
	public Map engine(Map in){//key includes :serviceCode busiObject   interfaceCode interfaceType
		Map ret=null;
		Object resultCode=in.get("resultCode");
		if(null !=resultCode)
			return in;
		try {
			//服务名字、方法名字、入参
			ret = DataTranslate._Map(ServiceManager.callJavaBeanService("ORDERENGINE","innerEngine" ,in));
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
			ret=in;
		}
		return ret;
	}
	public int perform(DynamicDict dto) throws Exception {
		String methodName = (String) dto.getValueByName(Const.ACTION_METHOD);
		Map in =(Map)dto.getValueByName(Const.ACTION_PARAMETER) ;
		if("innerEngine".equals(methodName)){
			Map out=this.innerEngine(in);
			dto.setValueByName(Const.ACTION_RESULT, out);
		}
		return 0;
	}

}
