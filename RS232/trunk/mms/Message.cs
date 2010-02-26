using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.IO;
using System.Xml; 
using CookComputing.XmlRpc;
namespace mms
{
    class Message
    {
        private IMsg msgProxy = XmlRpcProxyGen.Create<IMsg>();
        private int Customer_id = Properties.Settings.Default.Customer_id;//集团客户编号
        private string Corp_Account = Properties.Settings.Default.Corp_Account;//集团代码
        private int Agreement_id =  Properties.Settings.Default.Agreement_id;//合同id
        private string Token = Md5.GetMD5(Properties.Settings.Default.Token);//密码


        public void sendMsg(Hashtable request)
        {
            msgProxy.Url = Properties.Settings.Default.rpcURL;
            int Type = 1;//短/彩信标识        1—彩信  2—短信
            //IDictionary props = new Hashtable();
            //props["port"] = 8888;

            XmlRpcStruct map = new XmlRpcStruct();
            map.Add("Customer_id", Customer_id);
            map.Add("Corp_Account", Corp_Account);
            map.Add("Agreement_id", Agreement_id);
            map.Add("Token", Token);
            map.Add("Type", Type);

           // map.Add();

            //提交任务单
            int TaskID = addTask(map);
            map.Add("TaskID", TaskID);
            //提交彩信内容
            map.Add("MmsType", 2);//1-通用彩信 2-适配彩信
            //string Mms_xt = (string)request["Mms_xt"];
            map.Add("Mms_xt", Hex.readFile((string)request["Mms_xt"]));
            map.Add("Mms", Hex.readFile((string)request["Mms"]));
            map.Add("Mms_dt", Hex.readFile((string)request["Mms_dt"]));
            map.Add("Mms_cd", Hex.readFile((string)request["Mms_cd"]));
            map.Add("Mms_zd", Hex.readFile((string)request["Mms_zd"]));

            string state = AddContent(map);

            map.Clear();
            map.Add("Customer_id", Customer_id);
            map.Add("Corp_Account", Corp_Account);
            map.Add("TaskID", TaskID);
            map.Add("Token", Token);
            map.Add("Mobile", (string)request["Mobile"]);
            AddWhiteList(map);
            //提交白名单
            //

        }

        /// <summary>
        /// 创建彩信任务单
        /// </summary>
        /// <returns>任务Id</returns>
        public int addTask(XmlRpcStruct map)
        {
            try
            {
               // XmlTextReader reader = new XmlTextReader(new StringReader(msgProxy.AddTask(new Hashtable())));
                 
                string taskInfo = msgProxy.AddTask(map);

                XmlDocument xmldoc = new XmlDocument();
                xmldoc.LoadXml(taskInfo);
                XmlElement root = null;
                root = xmldoc.DocumentElement;
                XmlElement Code = (XmlElement)root.SelectSingleNode("/Response/Code");
                int taskId = 0;
                if (Code.InnerText.Equals("0000"))
                {
                    XmlElement TaskID = (XmlElement)root.SelectSingleNode("/Response/TaskList/Task/TaskID");
                    taskId =int.Parse(TaskID.InnerText.Trim());
                }
                return taskId;

            }catch(Exception e){
                throw new Exception("创建任务单失败！");
            }
            
        }

        /// <summary>
        /// 添加彩信内容
        /// </summary>
        /// <param name="map"></param>
        /// <returns>添加结果</returns>
        public string AddContent(XmlRpcStruct map)
        {
            
            string state = msgProxy.AddContent(map);
            
            XmlDocument xmldoc = new XmlDocument();
            xmldoc.LoadXml(state);
            XmlElement root = null;
            root = xmldoc.DocumentElement;
            XmlElement Code = (XmlElement)root.SelectSingleNode("/Response/Code");
            if (Code.InnerText.Equals("0000"))
            {
                return "ok";
            }
            return state;

        }

        /// <summary>
        /// 添加白名单
        /// 
        /// </summary>
        /// <param name="map"></param>
        /// <returns></returns>
        public string AddWhiteList(XmlRpcStruct map)
        {

            string state = msgProxy.AddWhiteList(map);
            //XmlTextReader reader = new XmlTextReader(new StringReader());
            XmlDocument xmldoc = new XmlDocument();
            xmldoc.LoadXml(state);
            XmlElement root = null;
            root = xmldoc.DocumentElement;
            XmlElement Code = (XmlElement)root.SelectSingleNode("/Response/Code");
            if (Code.InnerText.Equals("0000"))
            {
                return "ok";
            }
            return state;
        }





    }
}
