package com.ztesoft.oaas.dao.prodFamily;

public class ProdFamilyDAOFactory {
    public static ProdFamilyDAO getProdFamilyDAO() {
        return new ProdFamilyDAOImpl();
    }
}
