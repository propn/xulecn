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
        static string encodeHex(byte[] bytes)
        {
            string str = "";
            for (int i = 0; i < bytes.Length; i++)
            {
                str += string.Format("{0:X}", bytes[i]);
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

    }
}
