package com.ztesoft.crm.salesres.dao;

import com.ztesoft.crm.salesres.dao.impl.MpOperatorLanDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.NochangelogDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcImsiDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcImsiSegDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcLevelLogDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNoDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNoImsiRelateDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNoPreDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNoRecRuleDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNoRemainDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNoRemainInfoDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNoSegDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNobagNoDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNobagRuleDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNobagRuleDetaDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNosegAppDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNosegApplogDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcNosegWanDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcSimDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcSimEvdoDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcSimImsiDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RcSimRelaDAOImpl;
import com.ztesoft.crm.salesres.dao.impl.RnNumberPreDAOImpl;


public class SrNSDAOFactory {
    private static SrNSDAOFactory instance = new SrNSDAOFactory();

    public SrNSDAOFactory() {
    }

    public static SrNSDAOFactory getInstance() {
        return instance;
    }

    public RcImsiDAO getRcImsiDAO() {
        return new RcImsiDAOImpl();
    }

    public RcSimDAO getRcSimDAO() {
        return new RcSimDAOImpl();
    }

    public RcNoSegDAO getRcNoSegDAO() {
        return new RcNoSegDAOImpl();
    }

    public RcNoDAO getRcNoDAO() {
        return new RcNoDAOImpl();
    }

    public RcNoImsiRelateDAO getRcNoImsiRelateDAO() {
        return new RcNoImsiRelateDAOImpl();
    }

    public RcImsiSegDAO getRcImsiSegDAO() {
        return new RcImsiSegDAOImpl();
    }

    public RcSimImsiDAO getRcSimImsiDAO() {
        return new RcSimImsiDAOImpl();
    }

    public RcNoPreDAO getRcNoPreDAO() {
        return new RcNoPreDAOImpl();
    }

    public RnNumberPreDAO getRnNumberPreDAO() {
        return new RnNumberPreDAOImpl();
    }

    public RcNobagRuleDAO getRcNobagRuleDAO() {
        return new RcNobagRuleDAOImpl();
    }

    public RcNobagRuleDetaDAO getRcNobagRuleDetaDAO() {
        return new RcNobagRuleDetaDAOImpl();
    }

    public RcNobagNoDAO getRcNobagNoDAO() {
        return new RcNobagNoDAOImpl();
    }

    public RcLevelLogDAO getRcLevelLogDAO() {
        return new RcLevelLogDAOImpl();
    }

    public RcNoRemainInfoDAO getRcNoRemainInfoDAO() {
        return new RcNoRemainInfoDAOImpl();
    }

    public RcNoRemainDAO getRcNoRemainDAO() {
        return new RcNoRemainDAOImpl();
    }

    public RcSimEvdoDAO getRcSimEvdoDAO() {
        return new RcSimEvdoDAOImpl();
    }

    public NochangelogDAO getNochangelogDAO() {
        return new NochangelogDAOImpl();
    }

    public RcSimRelaDAO getRcSimRelaDAO() {
        return new RcSimRelaDAOImpl();
    }

    public RcNosegWanDAO getRcNosegWanDAO() {
        return new RcNosegWanDAOImpl();
    }

    public RcNosegAppDAO getRcNosegAppDAO() {
        return new RcNosegAppDAOImpl();
    }

    public RcNosegApplogDAO getRcNosegApplogDAO() {
        return new RcNosegApplogDAOImpl();
    }

    public MpOperatorLanDAO getMpOperatorLanDAO() {
        return new MpOperatorLanDAOImpl();
    }

    public RcNoRecRuleDAO getRcNoRecRuleDAO() {
        return new RcNoRecRuleDAOImpl();
    }
}
