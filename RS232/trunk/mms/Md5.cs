using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;   

namespace mms
{
    class Md5
    {
        /// <summary>
        /// 
        /// </summary>
        /// <param name="sDataIn"></param>
        /// <returns></returns>
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

        /// <summary>
        /// 
        /// </summary>
        /// <param name="toEncrypt"></param>
        /// <returns></returns>
        public static string Encrypt(string toEncrypt)
        {
            byte[] result = null;

            MD5 md5 = MD5.Create();

            result = md5.ComputeHash(Encoding.Unicode.GetBytes(toEncrypt));

            // Releases all resources used by the System.Security.Cryptography.HashAlgorithm. 

            md5.Clear();

            return Convert.ToBase64String(result); 


        }

    }
}
