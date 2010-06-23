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
        /// 获取所有数据
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

        /// <summary>
        /// 获取已经选中数据
        /// </summary>
        /// <returns></returns>
        public DataTable GetCheckedData()
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
                string commandText = "SELECT PERSONID, PERSONNAME, NOTIFID, TELEPHONE, DEPTNAME, ADDRESSID,MEETINGID, MEETINGNAME, [TABLEID], SEATINGNO, QRID, EXT1, EXT2, EXT3, EXT4, EXT5 FROM MEETINGPERSON where NOTIFID=true";
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


        public DataTable GetDataByPhone(string phone,string name)
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
                    + "FROM MEETINGPERSON  WHERE (TELEPHONE = '" + phone + "' and PERSONNAME ='" + name + "') ";

                if (name.Length==0)
                {
                     commandText = "SELECT PERSONID, PERSONNAME, NOTIFID, TELEPHONE, DEPTNAME, ADDRESSID,MEETINGID, MEETINGNAME, [TABLEID], SEATINGNO, QRID, EXT1, EXT2, EXT3, EXT4, EXT5 "
                    + "FROM MEETINGPERSON  WHERE (TELEPHONE = '" + phone + "') ";
                }

                if (phone.Length == 0)
                {
                     commandText = "SELECT PERSONID, PERSONNAME, NOTIFID, TELEPHONE, DEPTNAME, ADDRESSID,MEETINGID, MEETINGNAME, [TABLEID], SEATINGNO, QRID, EXT1, EXT2, EXT3, EXT4, EXT5 "
                    + "FROM MEETINGPERSON  WHERE ( PERSONNAME ='" + name + "') ";
                }



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


        /// <summary>
        /// 获取所有手机号码
        /// </summary>
        /// <returns></returns>
        public void getAllPhones(out string[] phones, out string[] personNames)
        {
            
            DataTable table = GetData();
            string[] _phones = null;
            string[] _personNames = null; ;

            if (table != null && table.Rows.Count > 0)
            {
                 _phones = new string[table.Rows.Count];
                 _personNames = new string[table.Rows.Count];

               // Message msg = new Message();
                //创建白名单
                //生成白名单 对移动号码生成白名单
                for (int i = 0; i < table.Rows.Count; i++)
                {
                    String Mobile = table.Rows[i]["TELEPHONE"].ToString();
                    String personName = table.Rows[i]["PERSONNAME"].ToString();

                    //if (Message.isCmbNO(Mobile))
                   // {
                        _phones[i] = Mobile;
                        _personNames[i] = personName;
                   // }
                }
            }

            phones = _phones;
            personNames = _personNames;
        }


        /// <summary>
        /// 获取选择人员的手机号码
        /// </summary>
        /// <returns></returns>
        public void getCheckedPhones(out string[] phones, out string[] personNames)
        {
            DataTable table = GetCheckedData();
            string[] _phones = null;
            string[] _personNames = null; ;

            if (table != null && table.Rows.Count > 0)
            {
                _phones = new string[table.Rows.Count];
                _personNames = new string[table.Rows.Count];

               // Message msg = new Message();
                //创建白名单
                //生成白名单 对移动号码生成白名单
                for (int i = 0; i < table.Rows.Count; i++)
                {
                    String Mobile = table.Rows[i]["TELEPHONE"].ToString();
                    String personName = table.Rows[i]["PERSONNAME"].ToString();

                    if (Message.isCmbNO(Mobile))
                    {
                        _phones[i] = Mobile;
                        _personNames[i] = personName;
                    }
                }
            }

            phones = _phones;
            personNames = _personNames;
        }


        /// <summary>
        /// 更新发送状态
        /// </summary>
        /// <param name="personid"></param>
        /// <param name="personname"></param>
        /// <param name="telephone"></param>
        /// <param name="deptname"></param>
        /// <returns></returns>
        public bool UpdateSendInfo(string personid,string sendId)
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
                string commandText = string.Format("UPDATE MEETINGPERSON SET EXT1='{0}' WHERE PersonID='{1}'"
                    , sendId, personid);
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
        /// 更新发送状态
        /// </summary>
        /// <param name="personid"></param>
        /// <param name="personname"></param>
        /// <param name="telephone"></param>
        /// <param name="deptname"></param>
        /// <returns></returns>
        public bool UpdateSendInfo2(string mobile, string result)
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
                string commandText = string.Format("UPDATE MEETINGPERSON SET EXT1='{0}' WHERE TELEPHONE='{1}'", result, mobile);
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






    }
}
