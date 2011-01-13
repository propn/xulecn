package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RrLanVO extends ValueObject implements VO
{

    private String provId = "";
    private String lanId = "";
    private String lanCode = "";
    private String lanName = "";
    private String flag = "";
    private String areaCode = "";

    public RrLanVO()
    {
    }

    public RrLanVO(
            String pprovId, String planId, String planCode, String planName,
            String pflag)
    {
        provId = pprovId;
        lanId = planId;
        lanCode = planCode;
        lanName = planName;
        flag = pflag;
    }

    public String getProvId()
    {
        return provId;
    }

    public String getLanId()
    {
        return lanId;
    }

    public String getLanCode()
    {
        return lanCode;
    }

    public String getLanName()
    {
        return lanName;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setProvId(String pProvId)
    {
        provId = pProvId;
    }

    public void setLanId(String pLanId)
    {
        lanId = pLanId;
    }

    public void setLanCode(String pLanCode)
    {
        lanCode = pLanCode;
    }

    public void setLanName(String pLanName)
    {
        lanName = pLanName;
    }

    public void setFlag(String pFlag)
    {
        flag = pFlag;
    }

    public String getAreaCode()
    {
        return areaCode;
    }
    public void setAreaCode(String pAreaCode)
    {
        areaCode = pAreaCode;
    }
    
    public HashMap unloadToHashMap()
    {
        HashMap hashMap = new HashMap();
        hashMap.put("PROV_ID", this.provId);
        hashMap.put("LAN_ID", this.lanId);
        hashMap.put("LAN_CODE", this.lanCode);
        hashMap.put("LAN_NAME", this.lanName);
        hashMap.put("FLAG", this.flag);
        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap)
    {
        if(hashMap != null)
        {
            this.provId = (String)hashMap.get("PROV_ID");
            this.lanId = (String)hashMap.get("LAN_ID");
            this.lanCode = (String)hashMap.get("LAN_CODE");
            this.lanName = (String)hashMap.get("LAN_NAME");
            this.flag = (String)hashMap.get("FLAG");
        }
    }

    public List getKeyFields()
    {
        ArrayList arrayList = new ArrayList();
        arrayList.add("LAN_ID");
        return arrayList;
    }

    public String getTableName()
    {
        return "RR_LAN";
    }

}
