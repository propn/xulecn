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
     * 根据参与人标识获取参与人识别信息
     * @param pparty_id
     * @return 参与人标识对应的参与人识别信息列表（PartyIdentificationVO构成的ArrayList）
     * @throws DAOSystemException
     */
    ArrayList getPartyIdentificationsByParty(String pparty_id) throws DAOSystemException;
    
    long deleteByParty(String pparty_id) throws DAOSystemException;
}
