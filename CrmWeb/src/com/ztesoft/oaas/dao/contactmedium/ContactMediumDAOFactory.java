package com.ztesoft.oaas.dao.contactmedium;

public class ContactMediumDAOFactory
{
    public static ContactMediumDAO getContactMediumDAO() {
        return new ContactMediumDAOImpl();
    }
}
