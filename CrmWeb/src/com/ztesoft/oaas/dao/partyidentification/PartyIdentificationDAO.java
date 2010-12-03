package com.ztesoft.oaas.dao.partyidentification;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartyIdentificationVO;

public interface PartyIdentificationDAO extends DAO {

	PartyIdentificationVO findByPrimaryKey(String pident_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pident_id,PartyIdentificationVO vo) throws DAOSystemException;

	long delete( String pident_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * ���ݲ����˱�ʶ��ȡ������ʶ����Ϣ
     * @param pparty_id
     * @return �����˱�ʶ��Ӧ�Ĳ�����ʶ����Ϣ�б�PartyIdentificationVO���ɵ�ArrayList��
     * @throws DAOSystemException
     */
    ArrayList getPartyIdentificationsByParty(String pparty_id) throws DAOSystemException;
    
    long deleteByParty(String pparty_id) throws DAOSystemException;
}
