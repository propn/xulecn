using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.IO;
using System.Xml; 

namespace mms
{
    class Message
    {
        private IMsg msgProxy;
        
        public void sendMsg()
        {
            msgProxy.Url = Properties.Settings.Default.rpcURL;
            Hashtable map=new Hashtable();
            string taskInfo=msgProxy.AddTask(map);
            XmlTextReader reader = new XmlTextReader(new StringReader(taskInfo));
            
                //提交任务单
                //编辑短信
                //提交白名单
                //


        }

        /// <summary>
        /// 创建彩信任务单
        /// </summary>
        /// <returns>任务Id</returns>
        public string addTask()
        {
            try
            {
               // XmlTextReader reader = new XmlTextReader(new StringReader(msgProxy.AddTask(new Hashtable())));
                
                string taskInfo = msgProxy.AddTask(new Hashtable());
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
            Hashtable map = new Hashtable();
            string state = msgProxy.AddContent(map);
            //XmlTextReader reader = new XmlTextReader(new StringReader());
            XmlDocument document = new XmlDocument();
            document.LoadXml(state);
            string state = document.GetElementsByTagName("Code")[0].Value;
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
            Hashtable map = new Hashtable();
            string state = msgProxy.AddContent(map);
            //XmlTextReader reader = new XmlTextReader(new StringReader());
            XmlDocument document = new XmlDocument();
            document.LoadXml(state);
            string state = document.GetElementsByTagName("Code")[0].Value;
            return state;
        }

    }
}
