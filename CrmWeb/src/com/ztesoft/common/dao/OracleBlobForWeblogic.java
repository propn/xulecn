/**
 * 
 */
package com.ztesoft.common.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Administrator
 * 
 */
public class OracleBlobForWeblogic {
	
	/**
     * �洢Blob�ֶΣ�������Oracle���ݿ�����
     * �������������Խ׶Σ���ô��Ҫ��Connection����Ϊ���Զ��ύ
     * @param rs Ԥ�����¼��
     * @param index ���ݿ���Blob�ֶε�����
     * @param buffer ������
     * @throws SQLException
     */
    public static void setBlob(ResultSet rs, int index, byte[] buffer)
        throws SQLException {
        OutputStream outstream = null;

        try {
            if (rs instanceof oracle.jdbc.driver.OracleResultSet) {
                outstream =
                    ( (oracle.jdbc.driver.OracleResultSet) rs).getBLOB(index).
                    getBinaryOutputStream();
            }
            else {
                Blob blob = rs.getBlob(index);
                if (blob instanceof oracle.sql.BLOB) {
                    outstream = ( (oracle.sql.BLOB) blob).getBinaryOutputStream();
                }
//                else if (blob instanceof weblogic.jdbc.common.OracleBlob) {
//                    outstream = ( (weblogic.jdbc.common.OracleBlob) blob).
//                        getBinaryOutputStream();
//                }
                else {
                    throw new SQLException(
                        "Unable to handle concrete type of Blob, " +
                        blob.getClass().getName());
                }
            }
            if (outstream != null) {
                outstream.write(buffer);
                outstream.flush();
            }
            else {
                throw new SQLException("IOStream Is Null");
            }
        }
        catch (IOException ex) {
            throw new DAOSystemException(ex.getMessage());
        }
        finally {
            if (outstream != null) {
                try {
                    outstream.close();
                }
                catch (IOException ex) {
                	}
                }
            }
        }
        
	public static void setOracleBlob(ResultSet rs, int index, byte[] buffer)
			throws SQLException {
		OutputStream outstream = null;

		try {
			if (rs instanceof oracle.jdbc.driver.OracleResultSet) {
				outstream = ((oracle.jdbc.driver.OracleResultSet) rs).getBLOB(
						index).getBinaryOutputStream();
			} else {
				Blob blob = rs.getBlob(index);
				if (blob instanceof oracle.sql.BLOB) {
					outstream = ((oracle.sql.BLOB) blob)
							.getBinaryOutputStream();
				}else {
					throw new SQLException(
							"Unable to handle concrete type of Blob, "
									+ blob.getClass().getName());
				}
			}
			if (outstream != null) {
				outstream.write(buffer);
				outstream.flush();
			} else {
				throw new SQLException("IOStream Is Null");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (outstream != null) {
				try {
					outstream.close();
				} catch (IOException ex) {

				}
			}
		}
	}

	/**
	 * �����ݿ��еõ�Blob���ֽ�������
	 * 
	 * @param rs
	 *            �����
	 * @param index
	 *            ���ݿ���Blob�ֶε�����
	 * @return Blob�ֶε�����
	 * @throws SQLException
	 */
	public static byte[] getOracleBlob(ResultSet rs, int index)
			throws SQLException {
		Blob blob = rs.getBlob(index);
		return blob.getBytes(1, (int) blob.length());
	}
	/**
     * �����ݿ��еõ�Blob���ֽ�������
     * @param rs �����
     * @param index ���ݿ���Blob�ֶε�����
     * @return Blob�ֶε�����
     * @throws SQLException
     */
    public static byte[] getBlob(ResultSet rs, String fieldName)
        throws SQLException {
        Blob blob = rs.getBlob(fieldName);
        return blob.getBytes(1, (int) blob.length());
    }
}
