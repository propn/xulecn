using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;   

namespace mms
{
    class Md5
    {
        public static string GetMD5(string sDataIn)
        {
            //MD5CryptoServiceProvider md5 = new MD5CryptoServiceProvider();
            //byte[] byteValue, byteHash;
            //byteValue = System.Text.Encoding.UTF8.GetBytes(sDataIn);
            //byteHash = md5.ComputeHash(byteValue);
            //md5.Clear();
            //string sTemp = "";
            //for (int i = 0; i < byteHash.Length; i++)
            //{
            //    sTemp += byteHash[i].ToString("X").PadLeft(2, '0');
            //}
            //return  sTemp.ToLower();  

            return System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(sDataIn, "MD5");
            
        }
    }
}
