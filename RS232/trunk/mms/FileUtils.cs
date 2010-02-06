using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace mms
{
    static class  FileUtils
    {
        public static void openFile(string filePath)
        {
            try
            {
                System.Diagnostics.Process.Start(filePath);
            }catch(Exception e){

                throw new Exception("系统无法打开此格式文件，请安装该文件的处理工具"+e.Message);
            }
        }
    }
}
