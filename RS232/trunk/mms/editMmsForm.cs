using System;
using System.IO;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace mms
{
   
    public partial class editMmsForm : Form
    {
        private string meetingName = "";
        public editMmsForm()
        {
            InitializeComponent();
        }

        private void editMmsForm_Load(object sender, EventArgs e)
        {
            //获取会议信息
            //获取会议信息，如果系统未包含会议信息则提示导入信息
            DbUtil dbUtil = new DbUtil();
            //OleDbConnection conn = dbUtil.getConn();
            DataTable table = dbUtil.GetData();
            

            if (table != null && table.Rows.Count > 0)
            {
                meetingName = table.Rows[0]["MEETINGNAME"].ToString();
            }

            //初始化系统界面
            textBox1.Text = "\r\n尊敬的领导:\r\n请准时参加[" + meetingName + "]会议\r\n"
                + "时间：\r\n" + "yyyyMMdd hh:mm-yyyyMMdd hh:mm" + "\r\n"
                + "地点：\r\n"+"\r\n";

            textBox2.Text = "\r\n注意事项：\r\n1.凭此短信进行签到，请勿删除该信息";

            title.Text = "广州市府办公厅会议通知";

        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            
            //遍历所有与会人员
            DbUtil dbUtil = new DbUtil();
            DataTable table = dbUtil.GetData();

            if (table != null && table.Rows.Count > 0)
            {
                labelInfo.Text = "开始创建彩信...";
                //生成att01.txt文件
                FileStream objFileStream = new FileStream("att01.txt", FileMode.Create, FileAccess.Write);
                StreamWriter objStreamWriter = new StreamWriter(objFileStream);
                objStreamWriter.Write(textBox1.Text); //将字符串写入到文件中
                objStreamWriter.Close();
                //生成att02.txt
                objFileStream = new FileStream("att02.txt", FileMode.Create, FileAccess.Write);
                objStreamWriter = new StreamWriter(objFileStream);
                objStreamWriter.Write(textBox2.Text); //将字符串写入到文件中
                objStreamWriter.Close();
                //生成title.txt
                objFileStream = new FileStream("title.txt", FileMode.Create, FileAccess.Write);
                objStreamWriter = new StreamWriter(objFileStream);
                objStreamWriter.Write(title.Text); //将字符串写入到文件中
                objStreamWriter.Close();


                //创建
                string directoryCreator = Path.GetDirectoryName("temp");
                if (!System.IO.Directory.Exists(directoryCreator))
                {
                    System.IO.Directory.CreateDirectory(directoryCreator);
                }

                labelInfo.Text = "完成创建彩信";
                labelInfo.Text = "开始发送彩信...";

                for (int i = 0; i < table.Rows.Count;i++ )
                {
                    //生成彩信
                    String personId = table.Rows[i]["PERSONID"].ToString();
                   
                 
                }
                labelInfo.Text = "完成发送彩信";

            }

          
           
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Md5.GetMD5("asdf");
        }
    }
}
