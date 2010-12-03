/*
 * Created on 2005-9-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.powerise.ibss.util;
import java.util.HashMap;
import java.util.Iterator;

import com.powerise.ibss.framework.FrameException;
/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CacheData {
    private static HashMap m_Data = new HashMap();
    public static synchronized void setCacheData(String strCatagory, String strId, Object oData) throws FrameException {
        HashMap hmData = null;
        Object o = m_Data.get(strCatagory);
        if (o == null) {
            hmData = new HashMap();
        } else {
            hmData = (HashMap) o;
        }
        if (hmData.get(strId) == null)
        {
            hmData.put(strId, oData);
            m_Data.put(strCatagory,hmData);
        }
    }
    public static Object getCacheData(String strCatagory, String strId) throws FrameException {
        String strData = null;
        HashMap hmData = null;
        Object oData = null;
        Object o = m_Data.get(strCatagory);
        if (o != null) {
            hmData = (HashMap) o;
            oData = hmData.get(strId);          
        }
        return oData;
    }
    public static void clear() {
        Iterator it = m_Data.values().iterator();
        while (it.hasNext()) {
            ((HashMap) it.next()).clear();
        }
        m_Data.clear();
    }
}
