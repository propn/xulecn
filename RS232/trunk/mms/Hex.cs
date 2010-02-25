using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace mms
{
    class Hex
    {
        /// <summary>
        /// 彩信内容转为16进制
        /// </summary>
        /// <param name="bytes">彩信字节数组</param>
        /// <returns>彩信16进制字符串</returns>
        public static string encodeHex(byte[] bytes)
        {
            string str = "";
            for (int i = 0; i < bytes.Length; i++)
            {
                str += string.Format("{0:X000}", bytes[i]);
            }
            return str;
        }

        public static String readFile(String filename)
        {
            byte[] b = null;
            FileStream fis = null;
            int  size = 0;
            try
            {
                fis = new FileStream(filename, System.IO.FileMode.Open, System.IO.FileAccess.Read);
                size = (int)fis.Length;
                b = new byte[size];
                fis.Read(b, 0, size);

                fis.Close();
            }
            catch (Exception e)
            {
                throw new Exception("创建彩信失败");
            }
            return encodeHex(b);

        }


       /// <summary>
     /// 从汉字转换到16进制
      /// </summary>
      /// <param name="s"></param>
      /// <param name="charset">编码,如"utf-8","gb2312"</param>
      /// <param name="fenge">是否每字符用逗号分隔</param>
      /// <returns></returns>
     public static string ToHex(string s, string charset, bool fenge)
     {
         if ((s.Length % 2) != 0)
        {
             s += " ";//空格
             //throw new ArgumentException("s is not valid chinese string!");
         }
 
         System.Text.Encoding chs = System.Text.Encoding.GetEncoding(charset);
 
         byte[] bytes = chs.GetBytes(s);
 
         string str = "";
 
         for (int i = 0; i < bytes.Length; i++)
         {
             if ((int)bytes[i] < 10)
             {
                 str +="0"+ string.Format("{0:X}", bytes[i]);
             }
             else
             {
                 str += string.Format("{0:X}", bytes[i]);
             }
             
             if (fenge && (i != bytes.Length - 1))
             {
                 str += string.Format("{0}", ",");
             }
         }
 
         return str.ToLower();
     }
 
     /// <summary>
     /// 从16进制转换成汉字
     /// </summary>
     /// <param name="hex"></param>
     /// <param name="charset">编码,如"utf-8","gb2312"</param>
     /// <returns></returns>
     public static string UnHex(string hex, string charset)
     {
         if (hex == null)
             throw new ArgumentNullException("hex");
         hex = hex.Replace(",", "");
         hex = hex.Replace("\n", "");
         hex = hex.Replace("\\", "");
         hex = hex.Replace(" ", "");
         if (hex.Length % 2 != 0)
         {
             hex += "20";//空格
             //throw new ArgumentException("hex is not a valid number!", "hex");
         }
         // 需要将 hex 转换成 byte 数组。 
         byte[] bytes = new byte[hex.Length / 2];
 
         for (int i = 0; i < bytes.Length; i++)
         {
             try
             {
                 // 每两个字符是一个 byte。 
                 bytes[i] = byte.Parse(hex.Substring(i * 2, 2),
                 System.Globalization.NumberStyles.HexNumber);
             }
             catch
             {
                 // Rethrow an exception with custom message. 
                 throw new ArgumentException("hex is not a valid hex number!", "hex");
             }
         }
 
         System.Text.Encoding chs = System.Text.Encoding.GetEncoding(charset);
 
 
        // return chs.GetString(bytes);
         return ToHex(bytes.ToString(), "utf-8", false);

     }


    }
}
