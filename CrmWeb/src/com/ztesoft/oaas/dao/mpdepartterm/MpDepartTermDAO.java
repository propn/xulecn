package com.ztesoft.oaas.dao.mpdepartterm;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.MpDepartTermVO;

public interface MpDepartTermDAO extends DAO {

	MpDepartTermVO findByPrimaryKey(String pdepart_id,String pterm_code) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pdepart_id, String pterm_code,MpDepartTermVO vo) throws DAOSystemException;

	long delete( String pdepart_id, String pterm_code) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * ���ݲ���ID��ѯ���в��Ű��
     * @param party_id
     * @return ָ�����ŵ����а���б�MpDepartTermVO���ɵ�ArrayList��
     * @throws DAOSystemException
     */
    ArrayList getMpDepartTermsByParty(String party_id) throws DAOSystemException;
    
    long deleteByParty(String pparty_id) throws DAOSystemException;
}
