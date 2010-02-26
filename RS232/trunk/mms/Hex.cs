using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Text.RegularExpressions;
using System.Globalization;

namespace mms
{
    class Hex
    {
        internal enum HasCode
        {
            ASCII,
            UNICODE,
            UTF_8,
            UTF_7,
        }

        /// <summary>
        /// 彩信内容转为16进制
        /// </summary>
        /// <param name="bytes">彩信字节数组</param>
        /// <returns>彩信16进制字符串</returns>
        public static string encodeHex(byte[] bytes)
        {
            StringBuilder builder = new StringBuilder();
            //byte[] bytes = Encoding.Default.GetBytes(inputText);
            for (int i = 0; i < bytes.Length; i++)
            {
                builder = builder.Append(bytes[i].ToString("X2"));
            }

            return builder.ToString();

        }

        public static String readFile(String filename)
        {
            byte[] b = null;
            FileStream fis = null;
            int size = 0;
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


        public string get_Hextext(HasCode code, string text)
        {
            string str = null;
            if (text == null)
            {
                return str;
            }
            string pattern = "[^a-fA-F0-9]";
            Regex regex = new Regex(pattern);
            if (!regex.Match(text).Success)
            {
                byte[] bytes = new byte[0x1000];
                int length = text.Length;

                int num2 = 0;

                if ((length % 2) == 1)
                {
                    length--;
                    text = text.Substring(0, length);
                }
                length /= 2;
                for (int i = 0; i < length; i++)
                {
                    num2 = (int)long.Parse(text.Substring(i * 2, 2), NumberStyles.AllowHexSpecifier);
                    bytes[i] = (byte)num2;
                }
                switch (code)
                {
                    case HasCode.ASCII:
                        return Encoding.Default.GetString(bytes, 0, length);

                    case HasCode.UNICODE:
                        return Encoding.Unicode.GetString(bytes, 0, length);

                    case HasCode.UTF_8:
                        return Encoding.UTF8.GetString(bytes, 0, length);

                    case HasCode.UTF_7:

                        return Encoding.UTF7.GetString(bytes, 0, length);

                }
                return str;
            }
            return null;
        }


        public string Convert(string inputText, HasCode code)
        {
            if (inputText != null)
            {
                switch (code)
                {
                    case HasCode.ASCII:
                        {
                            StringBuilder builder = new StringBuilder();
                            byte[] bytes = Encoding.Default.GetBytes(inputText);
                            for (int i = 0; i < bytes.Length; i++)
                            {
                                builder = builder.Append(bytes[i].ToString("X2"));
                            }
                            return builder.ToString();
                        }
                    case HasCode.UNICODE:
                        {
                            StringBuilder builder2 = new StringBuilder();
                            byte[] buffer2 = Encoding.Unicode.GetBytes(inputText);
                            for (int j = 0; j < buffer2.Length; j++)
                            {
                                builder2 = builder2.Append(buffer2[j].ToString("X2"));
                            }
                            return builder2.ToString();
                        }
                    case HasCode.UTF_8:
                        {
                            StringBuilder builder3 = new StringBuilder();
                            byte[] buffer3 = Encoding.UTF8.GetBytes(inputText);
                            for (int k = 0; k < buffer3.Length; k++)
                            {
                                builder3 = builder3.Append(buffer3[k].ToString("X2"));
                            }
                            return builder3.ToString();
                        }
                    case HasCode.UTF_7:
                        {
                            StringBuilder builder4 = new StringBuilder();
                            byte[] buffer4 = Encoding.UTF7.GetBytes(inputText);
                            for (int m = 0; m < buffer4.Length; m++)
                            {
                                builder4 = builder4.Append(buffer4[m].ToString("X2"));
                            }
                            return builder4.ToString();
                        }
                }
            }
            return null;
        }





    }
}
