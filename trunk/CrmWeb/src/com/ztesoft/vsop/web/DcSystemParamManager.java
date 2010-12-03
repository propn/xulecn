package com.ztesoft.vsop.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.oaas.dao.rrlan.RrLanDAOImpl;
import com.ztesoft.oaas.vo.RrLanVO;
import com.ztesoft.vsop.order.dao.InfOrderRelationDao;
import com.ztesoft.vsop.web.dao.PartnerDAOImpl;
import com.ztesoft.vsop.web.dao.ProdDAOImpl;
import com.ztesoft.vsop.web.dao.ProdOffDAOImpl;
import com.ztesoft.vsop.web.dao.ProdRelationDAOImpl;
import com.ztesoft.vsop.web.dao.ProdServAbilityRelDAOImpl;
import com.ztesoft.vsop.web.dao.ProdSystemInfoDAOImpl;
import com.ztesoft.vsop.web.vo.PartnerVO;
import com.ztesoft.vsop.web.vo.ProdOffVO;
import com.ztesoft.vsop.web.vo.ProdVO;
/**
 * 
 * @author cooltan
 *
 */
public class DcSystemParamManager {
	private static Logger logger = Logger.getLogger(DcSystemParamManager.class);
	
	public static final String CACHE_OBJECTTYPE_PRODUCT0="0";
	public static final String CACHE_OBJECTTYPE_PRODOFFER1="1";
	public static final String CACHE_OBJECTTYPE_SPCP2="2";
	
	private Map configMap=null;
	private Map productNbr2Id=new HashMap(); //产品编码－ID映射关系
	private Map productId2Nbr=new HashMap();//产品Id－编码映射关系
	private Map prodOfferNbr2Id=new HashMap();//销售品编码-ID映射关系
	private Map productId2prodOfferId=null;//产品ID-纯增值销售品ID映射关系
	private Map productId2Object =new HashMap();//产品ID-产品对象
	private Map serviceAbility = new HashMap();//业务能力编码-业务能力名称
	private Map ProdOfferId2Nbr=new HashMap();//销售品ID-销售品编码
	private Map prodRelationMap=null;//产品关系 产品ID－关系VO（A端+Z端)列表
	private Map lanid2lanCodeMap = new HashMap();; //本地网ladId -ladCode 对应关系
	private Map productOfferId2Object=new HashMap();//销售品ID-销售品对象
	private Map productOfferId2VproductIdsList=null;//销售品ID-销售品增值产品成员列表
	//private Map productOfferIdsVproductIdsListWithAllState=null;//销售品ID-销售品增值产品成员列表(包括销售品各种状态下)
	private Map crmCProdIdMap = new HashMap();//附属产品规格数据：编码+vsop主键
	private Map crmInfoCode = new HashMap();//crm编码-ismp或其他平台编码映射关系
	
	private PartnerDAOImpl partnerDAOImpl=new PartnerDAOImpl();
	private ProdDAOImpl prodDAOImpl=new ProdDAOImpl();
	private ProdOffDAOImpl prodOffDAOImpl=new ProdOffDAOImpl();
	private ProdRelationDAOImpl prodRelationDAOImpl=new ProdRelationDAOImpl();
	private ProdServAbilityRelDAOImpl prodServAbilityRelDAOImpl=new ProdServAbilityRelDAOImpl();
	private ProdSystemInfoDAOImpl prodSystemInfoDAOImpl=new ProdSystemInfoDAOImpl();
	private RrLanDAOImpl rrLanDAOImpl=new RrLanDAOImpl();
	private InfOrderRelationDao infOrderDao = null;//InfOrderRelationDao引用到DcSystemParamManager,所以不能在此初始化
	
    public static DcSystemParamManager config=new DcSystemParamManager();
    private DcSystemParamManager(){
    }
    public static DcSystemParamManager getInstance(){ 	 
    	return config;
    }
    public void setConfigMap(HashMap configMap){
    	this.configMap=configMap;
    }
    public  Map getConfigMap(){
    	return configMap;
    }
    //缓存维护
    
    /**
     * 全量刷新缓存
     * 
     * 增量刷新场景:产品变更、销售品变更、产品关系、产品关联业务能力 产品接入系统
     */
    public void getAllData(){
    	//产品接入平台 key=productId value=List<system_code>
    	Map prodSystemMaps=prodSystemInfoDAOImpl.findByCond(" 1=1");
    	//产品关联业务能力 key=productId value=List<service_code>
    	Map prodServRelMaps=prodServAbilityRelDAOImpl.findByCond(" 1=1");
    	//产品提供商（SPCP) key=PARTNER_ID value=PartnerVO
    	Map prodSpcpMaps=partnerDAOImpl.findByCond(" 1=1");
    	//产品关系
    	prodRelationMap=prodRelationDAOImpl.findByCond(" 1=1");
    	
    	//产品规格数据
    	List prods=prodDAOImpl.findByCond(" 1=1");
    	for(int i=0;i<prods.size();i++){
    		ProdVO vo = (ProdVO)prods.get(i);
    		String productId=vo.getProdId();
    		//产品编码与标识映射数据
    		DcSystemParamManager.getInstance().getProductNbr2Id().put(vo.getProdNbr(),productId );
    		//产品标识与编码映射数据
    		DcSystemParamManager.getInstance().getProductId2Nbr().put(productId,vo.getProdNbr());
    		//产品对应接入平台列表
    		List prodPlatforms=(List)prodSystemMaps.get(productId);
    		vo.setProdPlatforms(prodPlatforms);
    		//产品关联业务能力列表
    		List prodServiceAbilitys=(List)prodServRelMaps.get(productId);
    		vo.setProdServiceAbilitys(prodServiceAbilitys);
    		//产品对应提供商（SPCP)
    		PartnerVO  partnerVO=(PartnerVO)prodSpcpMaps.get(vo.getProdProviderId());
    		vo.setPartnerVO(partnerVO);
    		//产品标识与产品对象映射数据
    		this.productId2Object.put(productId, vo);
    		//附属产品规格数据存入缓存
    		String prodFuncType = vo.getProdFuncType();
    		if(prodFuncType != null && "2".equals(prodFuncType)){
    			//2:附属产品
    			this.crmCProdIdMap.put(vo.getProdNbr(), productId);
    		}
    	}
    	//产品与纯增值销售品标识映射数据
    	this.productId2prodOfferId=prodOffDAOImpl.findAllProduct2Offer("");
    	
    	//销售品标识与增值产品成员列表映射数据
    	this.productOfferId2VproductIdsList=prodOffDAOImpl.findAllProductOffer2VproductList("");
    	
    	//销售品标识与增值产品成员列表映射数据(所有销售品状态 )
    //	this.productOfferIdsVproductIdsListWithAllState=prodOffDAOImpl.findAllProductOfferSVproductList("");
    	//销售品规格数据
    	List prodOffs=prodOffDAOImpl.findByCond(" state='0' and 1=1");
    	for(int i=0;i<prodOffs.size();i++){
    		ProdOffVO vo = (ProdOffVO)prodOffs.get(i);
    		productOfferId2Object.put(vo.getProdOffId(), vo);
    		DcSystemParamManager.getInstance().getProdOfferNbr2Id().put(vo.getOffNbr(), vo.getProdOffId());
    		this.ProdOfferId2Nbr.put(vo.getProdOffId(),vo.getOffNbr());//ProdOfferType2Id
    	}
    	//业务能力规格数据
    	this.serviceAbility=prodServAbilityRelDAOImpl.findAllServAbility();
    	
    	List rrLanList = rrLanDAOImpl.findByCond(" 1=1 ");
    	for (int i = 0; i < rrLanList.size(); i++) {
			RrLanVO vo = (RrLanVO) rrLanList.get(i);
			this.lanid2lanCodeMap.put(vo.getLanId(),vo.getLanCode());
		}
    	String provCode = (String) this.configMap
				.get(com.ztesoft.vsop.VsopConstants.DC_PROVINCE_CODE);
		if ((CrmConstants.GX_PROV_CODE).equals(provCode.trim()) || (CrmConstants.JX_PROV_CODE).equals(provCode.trim())) {
			// 反向接口码全量刷新
			if (null == this.infOrderDao) {
				this.infOrderDao = new InfOrderRelationDao();
				this.crmInfoCode = this.infOrderDao.cacheIsmpCode();
			}
		}
    	/*
    	List prodRelas=prodRelationDAOImpl.findByCond("");
    	*/
    }
    /**
     * 增量刷新缓存
     * 增量刷新场景:产品变更（产品关系、产品关联业务能力 产品接入系统）、销售品变更、SPCP变更 （单个刷新，不组合刷新）
     * @param objectType 0产品 or 1销售品 or 2产品提供商(spcp)
     * @param objectId   product.product_id or  prod_offer.prod_offer_id  or partner.partner_id
     */
    public boolean getIncrementData(String objectType,String objectId){
    	boolean ret=false;
    	if(CACHE_OBJECTTYPE_PRODUCT0.equals(objectType)){
    		ret=this.getIncrementProduct(objectId);
    		
    	}else if(CACHE_OBJECTTYPE_PRODOFFER1.equals(objectType)){
    		ret=this.getIncrementProdOffer(objectId);
    		
    	}else if(CACHE_OBJECTTYPE_SPCP2.equals(objectType)){
    		ret=this.getIncrementSpcp(objectId);
    	}else{
    		//error occur
    	}
    	return ret;
    }
    
    /**
     * 根据产品ID刷新单个产品缓存数据 
     * @param objectId
     * @return
     * private here is better
     */
    public boolean getIncrementProduct(String objectId){
    	boolean ret =false;
    	//单个产品接入平台 key=productId value=List<system_code>
    	Map prodSystemMaps=prodSystemInfoDAOImpl.findByCond(" product_id="+objectId);
    	//单个产品关联业务能力 key=productId value=List<service_code>
    	Map prodServRelMaps=prodServAbilityRelDAOImpl.findByCond(" product_id="+objectId);
    	//单个产品规格数据
    	List prods=prodDAOImpl.findByCond(" product_id="+objectId);
    	for(int i=0;i<prods.size();i++){
    		ProdVO vo = (ProdVO)prods.get(i);
    		String productId=vo.getProdId();
    		//产品编码与标识映射数据
    		DcSystemParamManager.getInstance().getProductNbr2Id().put(vo.getProdNbr(),productId );
    		//产品标识与编码映射数据
    		DcSystemParamManager.getInstance().getProductId2Nbr().put(productId,vo.getProdNbr());
    		//产品对应接入平台列表
    		List prodPlatforms=(List)prodSystemMaps.get(productId);
    		vo.setProdPlatforms(prodPlatforms);
    		//产品关联业务能力列表
    		List prodServiceAbilitys=(List)prodServRelMaps.get(productId);
    		vo.setProdServiceAbilitys(prodServiceAbilitys);
    		//单个产品提供商（SPCP) key=PARTNER_ID value=PartnerVO 如果是产品先于产品提供商同步过来则会有问题(实际上不出现)
        	Map prodSpcpMaps=partnerDAOImpl.findByCond(" partner_id="+vo.getProdProviderId());
    		//产品对应提供商（SPCP)
    		PartnerVO  partnerVO=(PartnerVO)prodSpcpMaps.get(vo.getProdProviderId());
    		vo.setPartnerVO(partnerVO);
    		//单个产品标识与产品对象映射数据
    		this.productId2Object.put(productId, vo);
    		//附属产品规格数据存入缓存
    		String prodFuncType = vo.getProdFuncType();
    		if(prodFuncType != null && "2".equals(prodFuncType)){
    			//2:附属产品
    			this.crmCProdIdMap.put(vo.getProdNbr(), productId);
    		}
    	}
    	
    	//单个产品对应产品关系
    	Map tmpProdRelationMap=prodRelationDAOImpl.findByCond(" product_id="+objectId+" or pro_product_id="+objectId);
    	prodRelationMap.put(objectId, (List)tmpProdRelationMap.get(objectId));
    	
    	//单个产品与纯增值销售品标识映射数据
    	Map tmp=prodOffDAOImpl.findAllProduct2Offer(" and b.PRODUCT_ID="+objectId);
    	java.util.Set set=tmp.keySet();
    	java.util.Iterator it=set.iterator();
    	while(it.hasNext()){
    		String key=(String)it.next();
    		String value=(String)tmp.get(key);
    		this.productId2prodOfferId.put(key,value);
    	}
    	
    	//单个增值销售品标识与增值产品标识列表映射数据
    	Map tmp3=prodOffDAOImpl.findAllProductOffer2VproductList(" and c.prod_offer_id="+objectId);
    	java.util.Set set3=tmp3.keySet();
    	java.util.Iterator it3=set3.iterator();
    	while(it3.hasNext()){
    		String key=(String)it3.next();
    		List value=(List)tmp3.get(key);
    		this.productOfferId2VproductIdsList.put(key,value);
    	}
    	//单个增值销售品标识与增值产品标识列表映射数据
//    	Map tmp4=prodOffDAOImpl.findAllProductOfferSVproductList(" and c.prod_offer_id="+objectId);
//    	java.util.Set set4=tmp4.keySet();
//    	java.util.Iterator it4=set4.iterator();
//    	while(it4.hasNext()){
//    		String key=(String)it4.next();
//    		List value=(List)tmp4.get(key);
//    		this.productOfferIdsVproductIdsListWithAllState.put(key,value);
//    	}
    	
    	//ISMP产品同步时同时新增销售品，需要同步刷新销售品缓存
    	java.util.Iterator it2=set.iterator();
    	while(it2.hasNext()){//产品、基础销售品同步生成
    		String key=(String)it2.next();//产品ID
    		String value=(String)tmp.get(key);//销售品ID
    		//单个销售品规格数据
        	List prodOffs=prodOffDAOImpl.findByCond(" state='0' and prod_offer_id="+value);
        	for(int i=0;i<prodOffs.size();i++){
        		ProdOffVO vo = (ProdOffVO)prodOffs.get(i);
        		productOfferId2Object.put(vo.getProdOffId(), vo);
        		DcSystemParamManager.getInstance().getProdOfferNbr2Id().put(vo.getOffNbr(), vo.getProdOffId());
        		this.ProdOfferId2Nbr.put(vo.getProdOffId(),vo.getOffNbr());
        	}
    	}
    	//反向接口码增量刷新
    	String provCode = (String) this.configMap
				.get(com.ztesoft.vsop.VsopConstants.DC_PROVINCE_CODE);
		if ((CrmConstants.GX_PROV_CODE).equals(provCode.trim())) {
			if (null == this.infOrderDao) {
				this.infOrderDao = new InfOrderRelationDao();
				this.crmInfoCode = this.infOrderDao.cacheIsmpCode();
			}
		}
    	ret=true;
    	return ret;
    }
    /**
     * 根据销售品ID刷新单个销售品缓存数据
     * @param objectId
     * @return
     */
    public boolean getIncrementProdOffer(String objectId){
    	boolean ret =false;
    	//1新增销售品 2销售品构成更改
    	//单个销售品规格数据
    	List prodOffs=prodOffDAOImpl.findByCond(" state='0' and prod_offer_id="+objectId);
    	for(int i=0;i<prodOffs.size();i++){
    		ProdOffVO vo = (ProdOffVO)prodOffs.get(i);
    		productOfferId2Object.put(vo.getProdOffId(), vo);
    		DcSystemParamManager.getInstance().getProdOfferNbr2Id().put(vo.getOffNbr(), vo.getProdOffId());
    		this.ProdOfferId2Nbr.put(vo.getProdOffId(),vo.getOffNbr());
    	}
    	//单个产品与纯增值销售品标识映射数据
    	Map tmp=prodOffDAOImpl.findAllProduct2Offer(" and c.prod_offer_id="+objectId);
    	java.util.Set set=tmp.keySet();
    	java.util.Iterator it=set.iterator();
    	while(it.hasNext()){
    		String key=(String)it.next();
    		String value=(String)tmp.get(key);
    		this.productId2prodOfferId.put(key,value);
    	}
    	
    	//单个增值销售品标识与增值产品标识列表映射数据
    	Map tmp3=prodOffDAOImpl.findAllProductOffer2VproductList(" and c.prod_offer_id="+objectId);
    	java.util.Set set3=tmp3.keySet();
    	java.util.Iterator it3=set3.iterator();
    	while(it3.hasNext()){
    		String key=(String)it3.next();
    		List value=(List)tmp3.get(key);
    		this.productOfferId2VproductIdsList.put(key,value);
    	}
    	
//    	//单个增值销售品标识与增值产品标识列表映射数据
//    	Map tmp4=prodOffDAOImpl.findAllProductOfferSVproductList(" and c.prod_offer_id="+objectId);
//    	java.util.Set set4=tmp4.keySet();
//    	java.util.Iterator it4=set4.iterator();
//    	while(it4.hasNext()){
//    		String key=(String)it4.next();
//    		List value=(List)tmp4.get(key);
//    		this.productOfferIdsVproductIdsListWithAllState.put(key,value);
//    	}
    	
    	ret=true;
    	return ret;
    }
    /**
     * 根据SPCP标识刷新单个SPCP缓存数据  
     * @param objectId
     * @return
     */
    public boolean getIncrementSpcp(String objectId){
    	//1、已存在的产品提供商变更 
    	//2、新增产品提供商 则此时对应的增值产品可能还没加载到缓存，则此时实际上更新不到而需要留待产品同步时更新
    	boolean ret =false;
    	//单个产品提供商（SPCP) key=PARTNER_ID value=PartnerVO
    	Map prodSpcpMaps=partnerDAOImpl.findByCond(" partner_id="+objectId);
    	//单个产品对应提供商（SPCP)
		PartnerVO  partnerVO=(PartnerVO)prodSpcpMaps.get(objectId);
    	
    	//按照产品提供商获取产品规格数据
    	List prods=prodDAOImpl.findByCond(" product_provider_id="+objectId);
    	for(int i=0;i<prods.size();i++){
    		ProdVO vo = (ProdVO)prods.get(i);
    		String productId=vo.getProdId();
    		//更新引用到当前产品提供商的产品VO
    		ProdVO oldVo=(ProdVO)this.productId2Object.get(productId);
    		if(oldVo!=null){
    			oldVo.setPartnerVO(partnerVO);
    		}
    	}
    	ret=true;
    	return ret;
    }

    //缓存使用
    
	/**
	 * 返回用于是否需要送crm反向同步的set
	 */
	private Map getCrmInfoCode() {
		if (null == this.crmInfoCode) {
			InfOrderRelationDao infDao = new InfOrderRelationDao();
			this.crmInfoCode = infDao.cacheIsmpCode();
			return this.crmInfoCode;
		} else {
			return this.crmInfoCode;
		}
	}
	
	/**
	 * 判断
	 * @param productNbr
	 * @return
	 */
	public boolean isExistInCrmCode(String productNbr){
		Map crmCodeSet=this.getCrmInfoCode();
		return crmCodeSet.containsValue(productNbr);
	}
    
    /**
     * 系统参数编码-系统参数值
     * @param key
     * @return
     */
    public static String getParameter(String key){
    	if(null == getInstance().getConfigMap())
    		return null;
    	String paraValue= (String)getInstance().getConfigMap().get(key);
    	if(paraValue!=null) paraValue=paraValue.trim();
    	return paraValue;
    }
    /**
     * 销售品ID获取销售品对象VO
     * @param prodOfferId
     * @return
     */
    public ProdOffVO getProdOffVOById(String prodOfferId){
    	return (ProdOffVO)this.productOfferId2Object.get(prodOfferId);
    }
    /**
     * 销售品ID获取销售品编码
     * @param prodOfferId
     * @return
     */
    public String getProdOfferNbrById(String prodOfferId){
    	return (String) this.ProdOfferId2Nbr.get(prodOfferId);
    }
    /**
     * 销售品ID获取销售品名称
     * @param prodOfferId
     * @return
     */
    public String getProdOfferNameById(String prodOfferId){
    	String name="";
    	ProdOffVO vo=(ProdOffVO)this.productOfferId2Object.get(prodOfferId);
    	if(vo!=null){
    		name+=vo.getProdOffName();
    	}
    	return name;
    }
    /**
     * 销售品ID获取销售品类型
     * @param prodOfferId
     * @return
     */
    public String getProdOfferTypeById(String prodOfferId){
    	String name="";
    	ProdOffVO vo=(ProdOffVO)this.productOfferId2Object.get(prodOfferId);
    	if(vo!=null){
    		name+=vo.getProdOffSubType();
    	}
    	return name;
    }
    /**
     * 通过销售标识获取增值产品成员标识列表
     * @param offerId
     * @return
     */
    public List getVproductIdsByOfferId(String offerId){
    	return (List)this.productOfferId2VproductIdsList.get(offerId);
    }
    /**
     * 通过销售标识获取增值产品成员标识列表
     * @param offerId
     * @return
     */
    public List getVproductIdsByOfferIdWithAllState(String offerId){
    	return this.getVproductIdsByOfferId(offerId);
    	//return (List)this.productOfferIdsVproductIdsListWithAllState.get(offerId);
    }
    /**
     * 销售品编码获取销售品ID
     * @param prodOfferNbr
     * @return
     */
    public String getProdOfferIdByNbr(String prodOfferNbr){
    	String offerId=(String) this.prodOfferNbr2Id.get(prodOfferNbr);
    	return offerId;
    }
    /**
     * 产品ID获取产品VO
     * @param productId
     * @return
     */
    public ProdVO getProductVOByid(String productId){
    	ProdVO vo=(ProdVO)this.productId2Object.get(productId);
    	return vo;
    }
    
    /**
     * 产品ID获取产品状态
     * @param productId
     * @return
     */
    public String getProductStateById(String productId){
    	String state="";
    	ProdVO vo=(ProdVO)this.productId2Object.get(productId);
    	if(vo!=null){
    		state+=vo.getProdStateCd();
    	}
    	return state;
    }
    
    /**
     * 产品ID获取对应提供商状态
     * @param productId
     * @return
     */
    public String getSpcpStateById(String productId){
    	String state="";
    	ProdVO vo=(ProdVO)this.productId2Object.get(productId);
    	if(vo!=null){
    		if(vo.getPartnerVO()!=null){
    			state+=vo.getPartnerVO().getState();
    		}
    	}
    	return state;
    }
    /**
     * 产品ID获取关联业务能力列表
     * @param productId
     * @return
     */
    public List getProductServiceAbilitysByid(String productId){
    	List prodServiceAbilitys=null;
    	ProdVO vo=(ProdVO)this.productId2Object.get(productId);
    	if(vo!=null){
    		prodServiceAbilitys=vo.getProdServiceAbilitys();
    	}
    	return prodServiceAbilitys;
    }
    /**
     * 产品ID获取接入平台列表
     * @param productId
     * @return
     */
    public List getProductPlatformsById(String productId){
    	List prodPlatforms=null;
    	ProdVO vo=(ProdVO)this.productId2Object.get(productId);
    	if(vo!=null){
    		prodPlatforms=vo.getProdPlatforms();
    	}
    	return prodPlatforms;
    }
    /**
     * 产品ID获取产品名称
     * @param productId
     * @return
     */
    public String getProductnameById(String productId){
    	String name="";
    	ProdVO vo=(ProdVO)this.productId2Object.get(productId);
    	if(vo!=null){
    		name+=vo.getProdName();
    	}
    	return name;
    }
    /**
     * 业务能力编码获取业务能力名称
     * @param code
     * @return
     */
    public String getServicenameByCode(String code){
    	String name = (String)this.serviceAbility.get(code);
    	return name;
    }
    /**
     * 产品编码获取产品ID
     * @param productNbr
     * @return
     */
    public String getProductIdByNbr(String productNbr){
    	String productId=(String) this.productNbr2Id.get(productNbr);
    	return productId;
    }
    /**
     * 产品ID获取产品编码
     * @param productId
     * @return
     */
    public String getProductNbrById(String productId){
    	String productNbr=(String) this.getProductId2Nbr().get(productId);
    	return productNbr;
    }
    /**
     * 产品ID获取对应纯增值销售品ID
     * @param productId
     * @return
     */
    public String getofferIdByProductId(String productId){
    	String offerId=(String) this.productId2prodOfferId.get(productId);
    	return offerId;
    }
    
    /**
     * 产品ID获取产品关系列表
     * @param productId
     * @return
     */
    public List getProdRelationById(String productId){
    	List prodRelations=null;
    	if(prodRelationMap!=null){
    		prodRelations=(List)prodRelationMap.get(productId);
    	}
    	return prodRelations;
    }
    
	public Map getProductNbr2Id() {
		return productNbr2Id;
	}
	public Map getProdOfferNbr2Id() {
		return prodOfferNbr2Id;
	}
	
	public Map getProductId2prodOfferId() {
		return productId2prodOfferId;
	}
	public Map getProductId2Object() {
		return productId2Object;
	}
	public Map getServiceAbility() {
		return serviceAbility;
	}
	public void setServiceAbility(Map serviceAbility) {
		this.serviceAbility = serviceAbility;
	}
	public Map getProdOfferId2Nbr() {
		return ProdOfferId2Nbr;
	}
	public void setProdOfferId2Nbr(Map prodOfferId2Nbr) {
		ProdOfferId2Nbr = prodOfferId2Nbr;
	}
	public Map getLanid2lanCodeMap(){
		return lanid2lanCodeMap;
	}
	public Map getProductId2Nbr() {
		return productId2Nbr;
	}
	public void setProductId2Nbr(Map productId2Nbr) {
		this.productId2Nbr = productId2Nbr;
	}
	public Map getCrmCProdIdMap() {
		return crmCProdIdMap;
	}
	public void setCrmCProdIdMap(Map crmCProdIdMap) {
		this.crmCProdIdMap = crmCProdIdMap;
	}
	/**
     * crm增值产品编码获取业务平台编码
     * @param prodOfferId
     * @return
     */
    public String getCrmCodeIsmpNbrByCode(String code){
    	return (String) this.crmInfoCode.get(code);
    }
    /**
     * crm业务能力附属产品ID获取vsop编码
     * @param prodOfferId
     * @return
     */
    public String getCrmCProdByCode(String code){
    	return (String) this.crmCProdIdMap.get(code);
    }
}
