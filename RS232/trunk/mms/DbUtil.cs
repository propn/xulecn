using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data.OleDb;
using System.Data;
using mms.Properties;


namespace mms
{
    /// <summary>
    /// 数据库操作类
    /// </summary>
    class DbUtil
    {
        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public OleDbConnection getConn()
        {
            OleDbConnection conn = new OleDbConnection(Settings.Default.dbConnectionString);
            return conn;
        }

        /// <summary>
        /// 修改数据
        /// </summary>
        /// <returns></returns>
        public DataTable GetData()
        {

            DataTable dt = new DataTable();
            OleDbConnection conn = getConn();

            try
            {
                //防止重复打开连接
                if (conn.State != ConnectionState.Open)
                {
                    conn.Open();
                }
                string commandText = "SELECT PERSONID, PERSONNAME, NOTIFID, TELEPHONE, DEPTNAME, ADDRESSID,MEETINGID, MEETINGNAME, [TABLEID], SEATINGNO, QRID, EXT1, EXT2, EXT3, EXT4, EXT5 FROM MEETINGPERSON ";
                OleDbCommand cmd = new OleDbCommand(commandText);
                //初始化连接
                cmd.Connection = conn;

                OleDbDataAdapter ada = new OleDbDataAdapter(cmd);
                ada.Fill(dt);
                return dt;
            }
            catch (Exception e)
            {
                throw new Exception("查询与会人员信息出错，请检查数据库配置" + e.Data);
            }
            finally
            {
                conn.Close();
            }
        }

        public DataTable GetDataByPhone(string phone)
        {
            DataTable dt = new DataTable();
            OleDbConnection conn = getConn();

            try
            {
                //防止重复打开连接
                if (conn.State != ConnectionState.Open)
                {
                    conn.Open();
                }

                string commandText = "SELECT PERSONID, PERSONNAME, NOTIFID, TELEPHONE, DEPTNAME, ADDRESSID,MEETINGID, MEETINGNAME, [TABLEID], SEATINGNO, QRID, EXT1, EXT2, EXT3, EXT4, EXT5 "
                    + "FROM MEETINGPERSON  WHERE (TELEPHONE = '" + phone + "') ";

                OleDbCommand cmd = new OleDbCommand(commandText);
                //初始化连接
                cmd.Connection = conn;

                OleDbDataAdapter ada = new OleDbDataAdapter(cmd);
                ada.Fill(dt);
                return dt;
            }
            catch (Exception e)
            {
                throw new Exception("查询与会人员信息出错，请检查数据库配置" + e.Data);
            }
            finally
            {
                conn.Close();
            }

        }

        public DataTable GetDataByPersonId(string personId)
        {
            DataTable dt = new DataTable();
            OleDbConnection conn = getConn();

            try
            {
                //防止重复打开连接
                if (conn.State != ConnectionState.Open)
                {
                    conn.Open();
                }
                string commandText = "SELECT PERSONID, PERSONNAME, NOTIFID, TELEPHONE, DEPTNAME, ADDRESSID,MEETINGID, MEETINGNAME, [TABLEID], SEATINGNO, QRID, EXT1, EXT2, EXT3, EXT4, EXT5 "
                    + "FROM MEETINGPERSON  WHERE (PERSONID = '" + personId + "') ";

                OleDbCommand cmd = new OleDbCommand(commandText);
                //初始化连接
                cmd.Connection = conn;

                OleDbDataAdapter ada = new OleDbDataAdapter(cmd);
                ada.Fill(dt);
                return dt;
            }
            catch (Exception e)
            {
                throw new Exception("查询与会人员信息出错，请检查数据库配置" + e.Data);
            }
            finally
            {
                conn.Close();
            }

        }

        /// <summary>
        /// 修改（可以增加参数，或者把表封装成一个类）
        /// </summary>
        /// <param name="personid"></param>
        /// <param name="personname"></param>
        /// <param name="telephone"></param>
        /// <param name="deptname"></param>
        /// <returns></returns>
        public bool Update(string personid)
        {
            OleDbConnection conn = getConn();

            try
            {
                // DateTime.Now.ToLongTimeString;
                //防止重复打开连接
                if (conn.State != ConnectionState.Open)
                {
                    conn.Open();
                }
                string commandText = string.Format("UPDATE MEETINGPERSON SET [TABLEID]='{0}' WHERE PersonID='{1}'"
                    , getCurrentTime(), personid);
                OleDbCommand cmd = new OleDbCommand(commandText);
                //初始化连接
                cmd.Connection = conn;

                int i = cmd.ExecuteNonQuery();

                return (i > 0);
            }
            catch (Exception e)
            {
                throw new Exception("查询与会人员信息出错，请检查数据库配置" + e.Data);
            }
            finally
            {
                conn.Close();
            }
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="personid"></param>
        /// <returns></returns>
        public bool Delete(string personid)
        {
            OleDbConnection conn = getConn();

            try
            {
                //防止重复打开连接
                if (conn.State != ConnectionState.Open)
                {
                    conn.Open();
                }
                string commandText = string.Format("DELETE FROM MEETINGPERSON WHERE PersonID='{0}'"
                    , personid);
                OleDbCommand cmd = new OleDbCommand(commandText);
                //初始化连接
                cmd.Connection = conn;

                int i = cmd.ExecuteNonQuery();

                return (i > 0);
            }
            catch (Exception e)
            {
                throw new Exception("查询与会人员信息出错，请检查数据库配置" + e.Data);
            }
            finally
            {
                conn.Close();
            }
        }

        public string getCurrentTime()
        {

            System.DateTime currentTime = new DateTime();
            currentTime = System.DateTime.Now;

            int year = currentTime.Year;
            int month = currentTime.Month;
            int day = currentTime.Day;
            int hour = currentTime.Hour;
            int minute = currentTime.Minute;
            int second = currentTime.Second;

            return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

        }
    }
}
