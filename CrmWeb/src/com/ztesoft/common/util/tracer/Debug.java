/**
 * Classname    : Debug
 * Description  : ��־��Ϣ��ӡ
 * Author       : cwf
 * Date         : 2004-05-31
 *
 * Last Update  : 2004-05-31
 * Author       : cwf
 * Version      : 1.0
 */

package com.ztesoft.common.util.tracer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * This class is just a helper class to make it handy
 * to print out debug statements
 */
public final class Debug {

    private static Logger logger = Logger.getLogger("com.ztesoft");

    /**
     * @param :msg ��Ҫ��ӡ������̨����Ϣ
     */
    public static void print(String msg) {
        logger.log(Level.INFO, msg);
    }

    /**
     * @param :e ��Ҫ��ӡ������ ,msg ��Ҫ��ӡ������̨����Ϣ
     */
    public static void print(Exception e, String msg) {
        print( (Throwable) e, msg);
    }

    /**
     * @param :e ��Ҫ��ӡ������
     */
    public static void print(Exception e) {
        print(e, null);
    }

    /**
     * @param :t ��Ҫ��ӡ������ ,msg ��Ҫ��ӡ������̨����Ϣ
     */
    public static void print(Throwable t, String msg) {
        logger.log(Level.WARN, "Received throwable with Message: " + msg, t);
    }

    /**
     * @param :t ��Ҫ��ӡ������
     */
    public static void print(Throwable t) {
        print(t, null);
    }

    //-----------------------------------------------------------------

    /**
     * @param :msg ��Ҫ��ӡ������̨����Ϣ, obj �����ߵľ��
     *
     */
    public static void print(String msg, Object obj) {
        if(obj != null) {
            logger.log(Level.INFO, obj.toString() + ": " + msg);
        }
        else {
            logger.log(Level.INFO, "���thisָ�봫�������磺Debug.print(msg, this)");
        }
    }

    /**
     * @param :e ��Ҫ��ӡ������ ,msg ��Ҫ��ӡ������̨����Ϣ, obj �����ߵľ��
     *
     */
    public static void print(Exception e, String msg, Object obj) {
        print((Throwable) e, msg, obj);
    }

    /**
     * @param :e ��Ҫ��ӡ������, obj �����ߵľ��
     *
     */
    public static void print(Exception e, Object obj) {
        print(e, obj.toString(), obj);
    }

    /**
     * @param :t ��Ҫ��ӡ������ ,msg ��Ҫ��ӡ������̨����Ϣ, obj �����ߵľ��
     *
     */
    public static void print(Throwable t, String msg, Object obj) {
        logger.log(Level.WARN, obj.toString() + ": " + msg, t);
    }

    /**
     * @param :t ��Ҫ��ӡ������, obj �����ߵľ��
     *
     */
    public static void print(Throwable t, Object obj) {
        print(t, obj.toString(), obj);
    }

}
