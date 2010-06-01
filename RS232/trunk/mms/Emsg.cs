using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace mms
{
    /// <summary>
    /// 易讯彩信对象
    /// </summary>
    class Emsg
    {
        string[] _strFileList;
        byte[][] _data;
        string[] _strMobile;
        string _title;

        public Emsg()
        {
        }

        public Emsg(String[] files,string[] mobiles,string title)
        {
            _strMobile = mobiles;//手机号码
            _title = title;//彩信标题

            _strFileList = new string[files.Length];
            _data = new byte[files.Length][];

            for (int i = 0; i < files.Length; i++)
            {
                
                //初始化 data
                FileInfo fileInfo = new FileInfo(files[i]);

                _strFileList[i] = fileInfo.Name;//初始化 strFileList

                _data[i] = new byte[fileInfo.Length];
                FileStream fs = File.OpenRead(files[i]);
                fs.Read(data[i], 0, (int)fileInfo.Length);
                fs.Close();
                fs = null;

            }

        }

        public string[] strFileList
        {
            get { return _strFileList; }
            set { _strFileList = value; }
        }

        public byte[][] data
        {
            get { return _data; }
            set { _data = value; }
        }

        public string[] strMobile
        {
            get { return _strMobile; }
            set { _strMobile = value; }
        }

        public string title
        {
            get { return _title; }
            set { _title = value; }
        }

          
    }
}
