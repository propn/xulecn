using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;

namespace mms
{
	class Sha1
	{
        public static string Encrypt(string toEncrypt)
        {
            // 创建新的加密服务提供程序对象
            SHA1 sha1 = SHA1.Create();
            byte[] result = null;
            // 将原始字符串转换成字节数组，然后计算散列，并返回一个字节数组 
            result = sha1.ComputeHash(Encoding.Unicode.GetBytes(toEncrypt));
            // Releases all resources used by the System.Security.Cryptography.HashAlgorithm.
            sha1.Clear();
            // 返回散列值的 Base64 编码字符串 
            return Convert.ToBase64String(result);
        }
	}
}
