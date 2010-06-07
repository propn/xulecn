using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;

namespace mms
{
    class EMmsService
    {
       
        public string errMsg="";

		string myServiceUrl =Properties.Settings.Default.mms_EMms_SMInterface;
        string userName = Properties.Settings.Default.userName;
        string password = Properties.Settings.Default.password;
        string verInfo = Properties.Settings.Default.verInfo;
        string port = Properties.Settings.Default.port;


        EMms.SMInterface myService = new EMms.SMInterface();
        CookieContainer cookie = new CookieContainer();


        public EMmsService()
        {
            myService.CookieContainer = cookie;			//保持登录Session
            myService.Url = myServiceUrl;
        }

        public string getErrMsg(){
            return errMsg;
        }

        /// <summary>
        /// 上传白名单
        /// </summary>
        /// <param name="phoneNumbers"></param>
        /// <param name="personNames"></param>
        /// <returns></returns>
        public int UpLoadContact(string[] personNames,string[] phoneNumbers )
        {
            int ret= myService.UpLoadContact(personNames, phoneNumbers, out errMsg);
            return ret;
        }

        /// <summary>
        /// 登录系统
        /// </summary>
        /// <returns></returns>
        public int login()
        {
            int ret = myService.Login(userName, password, verInfo, out errMsg);

            if (ret <= 0) //ret>0 登录成功,
            {
                //显示错误信息 strErrMsg;
                return -1;
            }
           
            return 0;
        }


        /// <summary>
        /// 发送彩信
        /// </summary>
        /// <param name="eMms"></param>
        /// <returns></returns>
        public int sendMms(Emsg eMms)
        {
            int rst=myService.SendMMS2(eMms.strMobile, port, eMms.title, eMms.strFileList, eMms.data,out errMsg);
            return rst;
        }

        /// <summary>
        /// 验证白名单
        /// </summary>
        /// <param name="strMobiles"></param>
        /// <param name="?"></param>
        /// <returns></returns>
        public int ValidPhone(string[] strMobiles){
            return myService.ValidPhone(strMobiles,out errMsg);
        }

        /// <summary>
        /// 获取彩信发送状态
        /// </summary>
        /// <returns></returns>
        public string GetMmsStatus()
        {
            string xmlStr = "";
            myService.GetMmsStatus(out xmlStr, out errMsg);
            return xmlStr;
 
        }


        /// <summary>
        /// 接收短信
        /// </summary>
        /// <param name="NextId"></param>
        /// <returns></returns>
        public int GetSmsList(int nextId,string strxml)
        {
            return myService.GetSmsList(nextId,out strxml,out errMsg);
        }

    }
}
