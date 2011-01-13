package com.ztesoft.common.util;


/**
 * �ɽ�VOת��XML�е�item�ڵ�Ľӿ�.
 * ������Ҫ������ת��Ϊxml��ʽ��item�ڵ㴫��ǰ������TreeList�����ʵ�����Ҫʵ�ָýӿ�
 * 
 * @author shi.xiangsheng
 *
 */
public interface XMLItem
{
    /**
     * ��VO���ֶ���fld1��fld2��...; ֵval1, val2, ...
     * ת����ĸ�ʽΪ&ltitem fld1='val1', fld2='val2', ...&gt
     * @param sbXml �������ת����xmlƬ�ϵ�StringBuffer
     * @return ���ת����xmlƬ�Ϻ��StringBuffer�� ͬsbXml
     */
    StringBuffer toXmlItemUnclosed(StringBuffer sbXml);
    /**
     * ��ȡ����֯���ṹVO����·��ֵ
     * @param vo ����֯���ṹVO
     * @return ��·��ֵ, ""��ʾ���ڵ�, null��ʾ�����ṹ
     */
    String pathInTree();
}
