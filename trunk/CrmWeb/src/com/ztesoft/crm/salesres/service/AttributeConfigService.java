package com.ztesoft.crm.salesres.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.buffalo.request.RequestContext;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.vo.AttrVO;
import com.ztesoft.crm.salesres.vo.AttrValueVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


/**
 * @Classname     : ProdBaseInfoConfigService
 * @Description   : 产品基本信息配置业务管理类
 * @Copyright 2006: ZTEsoft.
 * @Author        : lirx
 * @Create Date   : 2006-3-25
 * @Last Modified :
 * @Modified by   :
 * @Version       : 1.0
 */
public class AttributeConfigService extends DictService {
    private static Logger logger = Logger.getLogger(AttributeConfigService.class);

    /**
     * 保存产品属性信息
     * @param prodOffVO
     * @return String
     */
    /*public String saveAttribute(AttrVO attrVO) throws Exception {
        String isSuccess = "Fail";

        if (attrVO != null) {

                    AttributeConfigHome.class);
            List codeLst = qryAttribute(attrVO.getAttrCode());
            List nameLst = qryAttributeByName(attrVO.getAttrName());

            //对属性编码和属性名称进行唯一性检验
            if ((codeLst.size() == 0) && (nameLst.size() == 0)) {
                attrVO.setState("00A");
                isSuccess = bean.saveAttribute(attrVO);
            } else {
                isSuccess = "notUnique";
            }
        }

        return isSuccess;
    }

    *//**
     * 更新产品属性信息
     * @param attrVO
     * @return string
     *//*
    public String updateAttribute(AttrVO attrVO) throws Exception {
        String isSuccess = "Fail";

        if (attrVO != null) {

                    AttributeConfigHome.class);
            AttrVO attr = (AttrVO) ((List) bean.qryAttribute(attrVO)).get(0);

            //对属性编码和属性名称进行唯一性检验
            List codeLst = bean.qryAttribute("attr_code='" +
                    attrVO.getAttrCode() + "'" + " and attr_code!='" +
                    attr.getAttrCode() + "'");

            List nameLst = bean.qryAttribute("attr_name='" +
                    attrVO.getAttrName() + "'" + " and attr_Name!='" +
                    attr.getAttrName() + "'");

            if ((codeLst.size() == 0) && (nameLst.size() == 0)) {
                isSuccess = bean.updateAttribute(attrVO);
            } else {
                isSuccess = "notUnique";
            }
        }

        return isSuccess;
    }

    *//**
     * 根据属性编码查询属性信息
     * @param attrCode
     * @return list
     *//*
    public List qryAttribute(String attrCode) throws Exception {
        StringBuffer cond = new StringBuffer();
        List list = null;

        if ((attrCode != null) && !"".equals(attrCode)) {
            cond.append("attr_code='" + attrCode + "'");
        } else {
            cond.append(" 1=1");
        }


                AttributeConfigHome.class);
        list = bean.qryAttribute(cond.toString());

        return list;
    }

    *//**
     * 根据属性名字查询属性信息
     * @param attrName
     * @return list
     *//*
    public List qryAttributeByName(String attrName) throws Exception {
        StringBuffer cond = new StringBuffer();
        List list = null;

        if ((attrName != null) && !"".equals(attrName)) {
            cond.append("attr_name='" + attrName + "'");
        } else {
            cond.append(" 1=1");
        }


                AttributeConfigHome.class);
        list = bean.qryAttribute(cond.toString());

        return list;
    }

    *//**
     * 根据属性ID查询属性取值
     * @param attrId
     * @return list
     *//*
    public List qryAttrValue(String attrId) throws Exception {
        StringBuffer cond = new StringBuffer();
        List list = null;

        if ((attrId != null) && !"".equals(attrId)) {
            cond.append(" attr_id='" + attrId + "'");
        } else {
            cond.append(" 1=1");
        }


                AttributeConfigHome.class);
        list = bean.qryAttrValue(cond.toString());

        return list;
    }

    *//**
     * 根据属性ID查询属性取值
     * @param attrId
     * @return list
     *//*
    public List qryAttrValueByAttrCode(String attrCode)
        throws Exception {
        StringBuffer cond = new StringBuffer();
        List list = null;
        cond.append(
            " attr_id in(select attr_id from attribute where attr_code='" +
            attrCode + "')");


                AttributeConfigHome.class);
        list = bean.qryAttrValue(cond.toString());

        return list;
    }

    *//**
     * 分页查询属性信息
     * @param attrId
     * @param pageIndex
     * @param pageSize
     * @return  PageModel
     */
    public PageModel qryAttributeByPagination(String attrCode, String attrName,
        int pageIndex, int pageSize) throws Exception {
        

        HashMap map = new HashMap();
        map.put("attrCode", attrCode);
        map.put("attrName", attrName);
        
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));

        //return bean.qrydcDataInfo(map, pageIndex, pageSize);
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("AttrConfigBO","qryAttributeByPagination" ,map);
        return result;

        
    }

    /**
     * 分页查询属性取值
     * @param attrValDesc
     * @param pageIndex
     * @param pageSize
     * @return PageModel
     * @throws Exception
     *//*
    public PageModel qryAttrValByPagination(String attrValDesc, int pageIndex,
        int pageSize) throws Exception {
        StringBuffer sb = new StringBuffer();
        String filterStr = DAOUtils.filterQureyLikeCond(attrValDesc);
        sb.append(
            " attr_id in (select attr_id from attribute where attr_code='PREF_TYPE')");
        sb.append(" and attr_value_desc like '%" + filterStr + "%'");


                AttributeConfigHome.class);
        PageModel pm = bean.qryAttrValByPagination(sb.toString(), pageIndex,
                pageSize);

        return pm;
    }

    *//**
     * 保存属性取值信息
     * @param attrValueVO
     * @return string
     *//*
    public String saveAttrValue(AttrValueVO attrValueVO)
        throws Exception {
        String isSuccess = "Fail";

        if (attrValueVO != null) {

                    AttributeConfigHome.class);
            isSuccess = bean.saveAttrValue(attrValueVO);
            logger.debug("attrValueVO:" + attrValueVO.getAttrId());
        }

        return isSuccess;
    }

    *//**
     * 更新属性取值信息
     * @param attrValueVO
     * @return string
     *//*
    public String updateAttrValue(AttrValueVO attrValueVO)
        throws Exception {
        String isSuccess = "Fail";

        if (attrValueVO != null) {

                    AttributeConfigHome.class);
            isSuccess = bean.updateAttrValue(attrValueVO);
        }

        return isSuccess;
    }

    *//**
     * 删除属性信息
     * @param attrValueVO
     * @return string
     *//*
    public String removeAttrValue(AttrValueVO attrValueVO)
        throws Exception {
        String isSuccess = "Fail";

        if (attrValueVO != null) {

                    AttributeConfigHome.class);
            isSuccess = bean.removeAttrValue(attrValueVO);
            logger.debug("attrValueVO:" + attrValueVO.getAttrId());
        }

        return isSuccess;
    }

    *//**
     * 删除属性信息
     * @param attrVO
     * @return string
     * @throws java.rmi.RemoteException
     *//*
    public String removeAttr(AttrVO attrVO) throws Exception {
        String isSuccess = "Fail";

        if (attrVO != null) {
            ;


                    AttributeConfigHome.class);

            //判断该属性是否被商品明细属性约束表或者产品属性表引用
            long lCount = bean.qryProdAttr(attrVO.getAttrId());

            if (lCount == 0) {
                isSuccess = bean.removeAttr(attrVO);
                isSuccess = bean.removeAttrValue(" attr_id=" +
                        attrVO.getAttrId());
            } else {
                isSuccess = "inUse";
            }
        }

        return isSuccess;
    }

    *//**
       * 分页查询
       * @param attrValDesc
       * @param pageIndex
       * @param pageSize
       * @return PageModel
       * @throws Exception
       */
    public PageModel qryFeeItem(String searchType, String searchKeyword,
        int pageIndex, int pageSize) throws Exception {

//                AttributeConfigHome.class);
//        PageModel pm = bean.qryFeeItem(searchType, searchKeyword, pageIndex,
//                pageSize, this.getGlobalVar(GlobalVariableHelper.PROV_ID));
//
//        return pm;
        HashMap map = new HashMap();
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));
        map.put("searchType", searchType);
        map.put("searchKeyword", searchKeyword);
        map.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));
        
        
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SalesResourceBo","qryFeeItem" ,map);
        return result;  
    }

    public String getGlobalVar(String strGlobalName) throws Exception {
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();
        //GlobalVariableHelper helper = new GlobalVariableHelper(getRequest());
        GlobalVariableHelper helper = new GlobalVariableHelper(req);
        String strGlobal = helper.getVariable(strGlobalName);

        if (strGlobal != null) {
            return strGlobal;
        } else {
            return "0";
        }
    }
    }
