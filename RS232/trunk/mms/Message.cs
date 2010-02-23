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
        private string Customer_id = Properties.Settings.Default.Customer_id;//集团客户编号
        private string Corp_Account = Properties.Settings.Default.Corp_Account;//集团代码
        private string Agreement_id = Md5.GetMD5(Properties.Settings.Default.Agreement_id);//合同id
        private string Token = Md5.GetMD5(Properties.Settings.Default.Token);//密码


        public void sendMsg(string SmsContent)
        {
            msgProxy.Url = Properties.Settings.Default.rpcURL;
         
            string type = "1";//短/彩信标识        1—彩信  2—短信

            Hashtable map = new Hashtable();
            map.Add("Customer_id", Customer_id);
            map.Add("Corp_Account", Corp_Account);
            map.Add("Agreement_id", Agreement_id);
            map.Add("Token", Token);
            
            map.Add("type", type);

            //提交任务单
            string TaskID = addTask(map);
            map.Add("TaskID", TaskID);
            //提交彩信内容
            string state = AddContent(new Hashtable());


            //提交白名单
            //


        }

        /// <summary>
        /// 创建彩信任务单
        /// </summary>
        /// <returns>任务Id</returns>
        public string addTask(Hashtable map)
        {
            try
            {
               // XmlTextReader reader = new XmlTextReader(new StringReader(msgProxy.AddTask(new Hashtable())));
                string taskInfo = msgProxy.AddTask(map);
                XmlDocument document = new XmlDocument();
                document.LoadXml(taskInfo);
                string state = document.GetElementsByTagName("Code")[0].Value; 
                string taskId = "";
                if (state.Equals("0000"))
                {
                    taskId = document.GetElementsByTagName("TaskID")[0].Value;
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
        public string AddContent(Hashtable map)
        {

           

            string state = msgProxy.AddContent(map);
            //XmlTextReader reader = new XmlTextReader(new StringReader());
            XmlDocument document = new XmlDocument();
            document.LoadXml(state);
            state = document.GetElementsByTagName("Code")[0].Value;
            return state;
        }

        /// <summary>
        /// 添加白名单
        /// 
        /// </summary>
        /// <param name="map"></param>
        /// <returns></returns>
        public string AddWhiteList(Hashtable map)
        {
             map = new Hashtable();
            string state = msgProxy.AddContent(map);
            //XmlTextReader reader = new XmlTextReader(new StringReader());
            XmlDocument document = new XmlDocument();
            document.LoadXml(state);
            state = document.GetElementsByTagName("Code")[0].Value;
            return state;
        }





    }
}
