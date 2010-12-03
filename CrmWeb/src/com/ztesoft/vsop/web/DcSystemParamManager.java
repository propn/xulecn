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
	private Map productNbr2Id=new HashMap(); //��Ʒ���룭IDӳ���ϵ
	private Map productId2Nbr=new HashMap();//��ƷId������ӳ���ϵ
	private Map prodOfferNbr2Id=new HashMap();//����Ʒ����-IDӳ���ϵ
	private Map productId2prodOfferId=null;//��ƷID-����ֵ����ƷIDӳ���ϵ
	private Map productId2Object =new HashMap();//��ƷID-��Ʒ����
	private Map serviceAbility = new HashMap();//ҵ����������-ҵ����������
	private Map ProdOfferId2Nbr=new HashMap();//����ƷID-����Ʒ����
	private Map prodRelationMap=null;//��Ʒ��ϵ ��ƷID����ϵVO��A��+Z��)�б�
	private Map lanid2lanCodeMap = new HashMap();; //������ladId -ladCode ��Ӧ��ϵ
	private Map productOfferId2Object=new HashMap();//����ƷID-����Ʒ����
	private Map productOfferId2VproductIdsList=null;//����ƷID-����Ʒ��ֵ��Ʒ��Ա�б�
	//private Map productOfferIdsVproductIdsListWithAllState=null;//����ƷID-����Ʒ��ֵ��Ʒ��Ա�б�(��������Ʒ����״̬��)
	private Map crmCProdIdMap = new HashMap();//������Ʒ������ݣ�����+vsop����
	private Map crmInfoCode = new HashMap();//crm����-ismp������ƽ̨����ӳ���ϵ
	
	private PartnerDAOImpl partnerDAOImpl=new PartnerDAOImpl();
	private ProdDAOImpl prodDAOImpl=new ProdDAOImpl();
	private ProdOffDAOImpl prodOffDAOImpl=new ProdOffDAOImpl();
	private ProdRelationDAOImpl prodRelationDAOImpl=new ProdRelationDAOImpl();
	private ProdServAbilityRelDAOImpl prodServAbilityRelDAOImpl=new ProdServAbilityRelDAOImpl();
	private ProdSystemInfoDAOImpl prodSystemInfoDAOImpl=new ProdSystemInfoDAOImpl();
	private RrLanDAOImpl rrLanDAOImpl=new RrLanDAOImpl();
	private InfOrderRelationDao infOrderDao = null;//InfOrderRelationDao���õ�DcSystemParamManager,���Բ����ڴ˳�ʼ��
	
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
    //����ά��
    
    /**
     * ȫ��ˢ�»���
     * 
     * ����ˢ�³���:��Ʒ���������Ʒ�������Ʒ��ϵ����Ʒ����ҵ������ ��Ʒ����ϵͳ
     */
    public void getAllData(){
    	//��Ʒ����ƽ̨ key=productId value=List<system_code>
    	Map prodSystemMaps=prodSystemInfoDAOImpl.findByCond(" 1=1");
    	//��Ʒ����ҵ������ key=productId value=List<service_code>
    	Map prodServRelMaps=prodServAbilityRelDAOImpl.findByCond(" 1=1");
    	//��Ʒ�ṩ�̣�SPCP) key=PARTNER_ID value=PartnerVO
    	Map prodSpcpMaps=partnerDAOImpl.findByCond(" 1=1");
    	//��Ʒ��ϵ
    	prodRelationMap=prodRelationDAOImpl.findByCond(" 1=1");
    	
    	//��Ʒ�������
    	List prods=prodDAOImpl.findByCond(" 1=1");
    	for(int i=0;i<prods.size();i++){
    		ProdVO vo = (ProdVO)prods.get(i);
    		String productId=vo.getProdId();
    		//��Ʒ�������ʶӳ������
    		DcSystemParamManager.getInstance().getProductNbr2Id().put(vo.getProdNbr(),productId );
    		//��Ʒ��ʶ�����ӳ������
    		DcSystemParamManager.getInstance().getProductId2Nbr().put(productId,vo.getProdNbr());
    		//��Ʒ��Ӧ����ƽ̨�б�
    		List prodPlatforms=(List)prodSystemMaps.get(productId);
    		vo.setProdPlatforms(prodPlatforms);
    		//��Ʒ����ҵ�������б�
    		List prodServiceAbilitys=(List)prodServRelMaps.get(productId);
    		vo.setProdServiceAbilitys(prodServiceAbilitys);
    		//��Ʒ��Ӧ�ṩ�̣�SPCP)
    		PartnerVO  partnerVO=(PartnerVO)prodSpcpMaps.get(vo.getProdProviderId());
    		vo.setPartnerVO(partnerVO);
    		//��Ʒ��ʶ���Ʒ����ӳ������
    		this.productId2Object.put(productId, vo);
    		//������Ʒ������ݴ��뻺��
    		String prodFuncType = vo.getProdFuncType();
    		if(prodFuncType != null && "2".equals(prodFuncType)){
    			//2:������Ʒ
    			this.crmCProdIdMap.put(vo.getProdNbr(), productId);
    		}
    	}
    	//��Ʒ�봿��ֵ����Ʒ��ʶӳ������
    	this.productId2prodOfferId=prodOffDAOImpl.findAllProduct2Offer("");
    	
    	//����Ʒ��ʶ����ֵ��Ʒ��Ա�б�ӳ������
    	this.productOfferId2VproductIdsList=prodOffDAOImpl.findAllProductOffer2VproductList("");
    	
    	//����Ʒ��ʶ����ֵ��Ʒ��Ա�б�ӳ������(��������Ʒ״̬ )
    //	this.productOfferIdsVproductIdsListWithAllState=prodOffDAOImpl.findAllProductOfferSVproductList("");
    	//����Ʒ�������
    	List prodOffs=prodOffDAOImpl.findByCond(" state='0' and 1=1");
    	for(int i=0;i<prodOffs.size();i++){
    		ProdOffVO vo = (ProdOffVO)prodOffs.get(i);
    		productOfferId2Object.put(vo.getProdOffId(), vo);
    		DcSystemParamManager.getInstance().getProdOfferNbr2Id().put(vo.getOffNbr(), vo.getProdOffId());
    		this.ProdOfferId2Nbr.put(vo.getProdOffId(),vo.getOffNbr());//ProdOfferType2Id
    	}
    	//ҵ�������������
    	this.serviceAbility=prodServAbilityRelDAOImpl.findAllServAbility();
    	
    	List rrLanList = rrLanDAOImpl.findByCond(" 1=1 ");
    	for (int i = 0; i < rrLanList.size(); i++) {
			RrLanVO vo = (RrLanVO) rrLanList.get(i);
			this.lanid2lanCodeMap.put(vo.getLanId(),vo.getLanCode());
		}
    	String provCode = (String) this.configMap
				.get(com.ztesoft.vsop.VsopConstants.DC_PROVINCE_CODE);
		if ((CrmConstants.GX_PROV_CODE).equals(provCode.trim()) || (CrmConstants.JX_PROV_CODE).equals(provCode.trim())) {
			// ����ӿ���ȫ��ˢ��
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
     * ����ˢ�»���
     * ����ˢ�³���:��Ʒ�������Ʒ��ϵ����Ʒ����ҵ������ ��Ʒ����ϵͳ��������Ʒ�����SPCP��� ������ˢ�£������ˢ�£�
     * @param objectType 0��Ʒ or 1����Ʒ or 2��Ʒ�ṩ��(spcp)
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
     * ���ݲ�ƷIDˢ�µ�����Ʒ�������� 
     * @param objectId
     * @return
     * private here is better
     */
    public boolean getIncrementProduct(String objectId){
    	boolean ret =false;
    	//������Ʒ����ƽ̨ key=productId value=List<system_code>
    	Map prodSystemMaps=prodSystemInfoDAOImpl.findByCond(" product_id="+objectId);
    	//������Ʒ����ҵ������ key=productId value=List<service_code>
    	Map prodServRelMaps=prodServAbilityRelDAOImpl.findByCond(" product_id="+objectId);
    	//������Ʒ�������
    	List prods=prodDAOImpl.findByCond(" product_id="+objectId);
    	for(int i=0;i<prods.size();i++){
    		ProdVO vo = (ProdVO)prods.get(i);
    		String productId=vo.getProdId();
    		//��Ʒ�������ʶӳ������
    		DcSystemParamManager.getInstance().getProductNbr2Id().put(vo.getProdNbr(),productId );
    		//��Ʒ��ʶ�����ӳ������
    		DcSystemParamManager.getInstance().getProductId2Nbr().put(productId,vo.getProdNbr());
    		//��Ʒ��Ӧ����ƽ̨�б�
    		List prodPlatforms=(List)prodSystemMaps.get(productId);
    		vo.setProdPlatforms(prodPlatforms);
    		//��Ʒ����ҵ�������б�
    		List prodServiceAbilitys=(List)prodServRelMaps.get(productId);
    		vo.setProdServiceAbilitys(prodServiceAbilitys);
    		//������Ʒ�ṩ�̣�SPCP) key=PARTNER_ID value=PartnerVO ����ǲ�Ʒ���ڲ�Ʒ�ṩ��ͬ���������������(ʵ���ϲ�����)
        	Map prodSpcpMaps=partnerDAOImpl.findByCond(" partner_id="+vo.getProdProviderId());
    		//��Ʒ��Ӧ�ṩ�̣�SPCP)
    		PartnerVO  partnerVO=(PartnerVO)prodSpcpMaps.get(vo.getProdProviderId());
    		vo.setPartnerVO(partnerVO);
    		//������Ʒ��ʶ���Ʒ����ӳ������
    		this.productId2Object.put(productId, vo);
    		//������Ʒ������ݴ��뻺��
    		String prodFuncType = vo.getProdFuncType();
    		if(prodFuncType != null && "2".equals(prodFuncType)){
    			//2:������Ʒ
    			this.crmCProdIdMap.put(vo.getProdNbr(), productId);
    		}
    	}
    	
    	//������Ʒ��Ӧ��Ʒ��ϵ
    	Map tmpProdRelationMap=prodRelationDAOImpl.findByCond(" product_id="+objectId+" or pro_product_id="+objectId);
    	prodRelationMap.put(objectId, (List)tmpProdRelationMap.get(objectId));
    	
    	//������Ʒ�봿��ֵ����Ʒ��ʶӳ������
    	Map tmp=prodOffDAOImpl.findAllProduct2Offer(" and b.PRODUCT_ID="+objectId);
    	java.util.Set set=tmp.keySet();
    	java.util.Iterator it=set.iterator();
    	while(it.hasNext()){
    		String key=(String)it.next();
    		String value=(String)tmp.get(key);
    		this.productId2prodOfferId.put(key,value);
    	}
    	
    	//������ֵ����Ʒ��ʶ����ֵ��Ʒ��ʶ�б�ӳ������
    	Map tmp3=prodOffDAOImpl.findAllProductOffer2VproductList(" and c.prod_offer_id="+objectId);
    	java.util.Set set3=tmp3.keySet();
    	java.util.Iterator it3=set3.iterator();
    	while(it3.hasNext()){
    		String key=(String)it3.next();
    		List value=(List)tmp3.get(key);
    		this.productOfferId2VproductIdsList.put(key,value);
    	}
    	//������ֵ����Ʒ��ʶ����ֵ��Ʒ��ʶ�б�ӳ������
//    	Map tmp4=prodOffDAOImpl.findAllProductOfferSVproductList(" and c.prod_offer_id="+objectId);
//    	java.util.Set set4=tmp4.keySet();
//    	java.util.Iterator it4=set4.iterator();
//    	while(it4.hasNext()){
//    		String key=(String)it4.next();
//    		List value=(List)tmp4.get(key);
//    		this.productOfferIdsVproductIdsListWithAllState.put(key,value);
//    	}
    	
    	//ISMP��Ʒͬ��ʱͬʱ��������Ʒ����Ҫͬ��ˢ������Ʒ����
    	java.util.Iterator it2=set.iterator();
    	while(it2.hasNext()){//��Ʒ����������Ʒͬ������
    		String key=(String)it2.next();//��ƷID
    		String value=(String)tmp.get(key);//����ƷID
    		//��������Ʒ�������
        	List prodOffs=prodOffDAOImpl.findByCond(" state='0' and prod_offer_id="+value);
        	for(int i=0;i<prodOffs.size();i++){
        		ProdOffVO vo = (ProdOffVO)prodOffs.get(i);
        		productOfferId2Object.put(vo.getProdOffId(), vo);
        		DcSystemParamManager.getInstance().getProdOfferNbr2Id().put(vo.getOffNbr(), vo.getProdOffId());
        		this.ProdOfferId2Nbr.put(vo.getProdOffId(),vo.getOffNbr());
        	}
    	}
    	//����ӿ�������ˢ��
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
     * ��������ƷIDˢ�µ�������Ʒ��������
     * @param objectId
     * @return
     */
    public boolean getIncrementProdOffer(String objectId){
    	boolean ret =false;
    	//1��������Ʒ 2����Ʒ���ɸ���
    	//��������Ʒ�������
    	List prodOffs=prodOffDAOImpl.findByCond(" state='0' and prod_offer_id="+objectId);
    	for(int i=0;i<prodOffs.size();i++){
    		ProdOffVO vo = (ProdOffVO)prodOffs.get(i);
    		productOfferId2Object.put(vo.getProdOffId(), vo);
    		DcSystemParamManager.getInstance().getProdOfferNbr2Id().put(vo.getOffNbr(), vo.getProdOffId());
    		this.ProdOfferId2Nbr.put(vo.getProdOffId(),vo.getOffNbr());
    	}
    	//������Ʒ�봿��ֵ����Ʒ��ʶӳ������
    	Map tmp=prodOffDAOImpl.findAllProduct2Offer(" and c.prod_offer_id="+objectId);
    	java.util.Set set=tmp.keySet();
    	java.util.Iterator it=set.iterator();
    	while(it.hasNext()){
    		String key=(String)it.next();
    		String value=(String)tmp.get(key);
    		this.productId2prodOfferId.put(key,value);
    	}
    	
    	//������ֵ����Ʒ��ʶ����ֵ��Ʒ��ʶ�б�ӳ������
    	Map tmp3=prodOffDAOImpl.findAllProductOffer2VproductList(" and c.prod_offer_id="+objectId);
    	java.util.Set set3=tmp3.keySet();
    	java.util.Iterator it3=set3.iterator();
    	while(it3.hasNext()){
    		String key=(String)it3.next();
    		List value=(List)tmp3.get(key);
    		this.productOfferId2VproductIdsList.put(key,value);
    	}
    	
//    	//������ֵ����Ʒ��ʶ����ֵ��Ʒ��ʶ�б�ӳ������
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
     * ����SPCP��ʶˢ�µ���SPCP��������  
     * @param objectId
     * @return
     */
    public boolean getIncrementSpcp(String objectId){
    	//1���Ѵ��ڵĲ�Ʒ�ṩ�̱�� 
    	//2��������Ʒ�ṩ�� ���ʱ��Ӧ����ֵ��Ʒ���ܻ�û���ص����棬���ʱʵ���ϸ��²�������Ҫ������Ʒͬ��ʱ����
    	boolean ret =false;
    	//������Ʒ�ṩ�̣�SPCP) key=PARTNER_ID value=PartnerVO
    	Map prodSpcpMaps=partnerDAOImpl.findByCond(" partner_id="+objectId);
    	//������Ʒ��Ӧ�ṩ�̣�SPCP)
		PartnerVO  partnerVO=(PartnerVO)prodSpcpMaps.get(objectId);
    	
    	//���ղ�Ʒ�ṩ�̻�ȡ��Ʒ�������
    	List prods=prodDAOImpl.findByCond(" product_provider_id="+objectId);
    	for(int i=0;i<prods.size();i++){
    		ProdVO vo = (ProdVO)prods.get(i);
    		String productId=vo.getProdId();
    		//�������õ���ǰ��Ʒ�ṩ�̵Ĳ�ƷVO
    		ProdVO oldVo=(ProdVO)this.productId2Object.get(productId);
    		if(oldVo!=null){
    			oldVo.setPartnerVO(partnerVO);
    		}
    	}
    	ret=true;
    	return ret;
    }

    //����ʹ��
    
	/**
	 * ���������Ƿ���Ҫ��crm����ͬ����set
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
	 * �ж�
	 * @param productNbr
	 * @return
	 */
	public boolean isExistInCrmCode(String productNbr){
		Map crmCodeSet=this.getCrmInfoCode();
		return crmCodeSet.containsValue(productNbr);
	}
    
    /**
     * ϵͳ��������-ϵͳ����ֵ
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
     * ����ƷID��ȡ����Ʒ����VO
     * @param prodOfferId
     * @return
     */
    public ProdOffVO getProdOffVOById(String prodOfferId){
    	return (ProdOffVO)this.productOfferId2Object.get(prodOfferId);
    }
    /**
     * ����ƷID��ȡ����Ʒ����
     * @param prodOfferId
     * @return
     */
    public String getProdOfferNbrById(String prodOfferId){
    	return (String) this.ProdOfferId2Nbr.get(prodOfferId);
    }
    /**
     * ����ƷID��ȡ����Ʒ����
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
     * ����ƷID��ȡ����Ʒ����
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
     * ͨ�����۱�ʶ��ȡ��ֵ��Ʒ��Ա��ʶ�б�
     * @param offerId
     * @return
     */
    public List getVproductIdsByOfferId(String offerId){
    	return (List)this.productOfferId2VproductIdsList.get(offerId);
    }
    /**
     * ͨ�����۱�ʶ��ȡ��ֵ��Ʒ��Ա��ʶ�б�
     * @param offerId
     * @return
     */
    public List getVproductIdsByOfferIdWithAllState(String offerId){
    	return this.getVproductIdsByOfferId(offerId);
    	//return (List)this.productOfferIdsVproductIdsListWithAllState.get(offerId);
    }
    /**
     * ����Ʒ�����ȡ����ƷID
     * @param prodOfferNbr
     * @return
     */
    public String getProdOfferIdByNbr(String prodOfferNbr){
    	String offerId=(String) this.prodOfferNbr2Id.get(prodOfferNbr);
    	return offerId;
    }
    /**
     * ��ƷID��ȡ��ƷVO
     * @param productId
     * @return
     */
    public ProdVO getProductVOByid(String productId){
    	ProdVO vo=(ProdVO)this.productId2Object.get(productId);
    	return vo;
    }
    
    /**
     * ��ƷID��ȡ��Ʒ״̬
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
     * ��ƷID��ȡ��Ӧ�ṩ��״̬
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
     * ��ƷID��ȡ����ҵ�������б�
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
     * ��ƷID��ȡ����ƽ̨�б�
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
     * ��ƷID��ȡ��Ʒ����
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
     * ҵ�����������ȡҵ����������
     * @param code
     * @return
     */
    public String getServicenameByCode(String code){
    	String name = (String)this.serviceAbility.get(code);
    	return name;
    }
    /**
     * ��Ʒ�����ȡ��ƷID
     * @param productNbr
     * @return
     */
    public String getProductIdByNbr(String productNbr){
    	String productId=(String) this.productNbr2Id.get(productNbr);
    	return productId;
    }
    /**
     * ��ƷID��ȡ��Ʒ����
     * @param productId
     * @return
     */
    public String getProductNbrById(String productId){
    	String productNbr=(String) this.getProductId2Nbr().get(productId);
    	return productNbr;
    }
    /**
     * ��ƷID��ȡ��Ӧ����ֵ����ƷID
     * @param productId
     * @return
     */
    public String getofferIdByProductId(String productId){
    	String offerId=(String) this.productId2prodOfferId.get(productId);
    	return offerId;
    }
    
    /**
     * ��ƷID��ȡ��Ʒ��ϵ�б�
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
     * crm��ֵ��Ʒ�����ȡҵ��ƽ̨����
     * @param prodOfferId
     * @return
     */
    public String getCrmCodeIsmpNbrByCode(String code){
    	return (String) this.crmInfoCode.get(code);
    }
    /**
     * crmҵ������������ƷID��ȡvsop����
     * @param prodOfferId
     * @return
     */
    public String getCrmCProdByCode(String code){
    	return (String) this.crmCProdIdMap.get(code);
    }
}
