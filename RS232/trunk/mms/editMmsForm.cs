using System;
using System.IO;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Collections.Specialized;
using System.Collections;

namespace mms
{

    public partial class editMmsForm : Form
    {
        private string meetingName = "";
        public editMmsForm()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 初始化页面信息
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
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
                + "地点：\r\n" + "\r\n";

            textBox2.Text = "\r\n注意事项：\r\n1.凭此短信进行签到，请勿删除该信息";

            title.Text = "广州市府办公厅会议签到凭证";

        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        /// <summary>
        /// 发送彩信
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button1_Click(object sender, EventArgs e)
        {

            //遍历所有与会人员
            DbUtil dbUtil = new DbUtil();
            DataTable table = dbUtil.GetData();

            if (table != null && table.Rows.Count > 0)
            {
                labelInfo.Text = "\r\n开始创建彩信\r\n";
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

                string basePtah = Application.StartupPath + "\\temp\\";
                //删除历史文件
                if (Directory.Exists(basePtah))
                {
                    FileUtils.deleteDirectory(basePtah);
                }

                //创建
                Directory.CreateDirectory(Path.GetDirectoryName(basePtah));

                Message msg = new Message();

                labelInfo.Text = labelInfo.Text + "完成创建彩信\r\n\r\n";

                for (int i = 0; i < table.Rows.Count; i++)
                {
                    //生成彩信
                    String personId = table.Rows[i]["PERSONID"].ToString();
                    String Mobile = table.Rows[i]["TELEPHONE"].ToString();
                    String personName = table.Rows[i]["PERSONNAME"].ToString();

                    try
                    {

                        labelInfo.Text = labelInfo.Text + "开始向" + personName + "发送彩信\r\n";

                        Directory.CreateDirectory(Path.GetDirectoryName(basePtah + personId + "\\"));
                        //复制文件夹
                        FileUtils.CopyDirectory(Application.StartupPath + "\\msg\\", basePtah + personId + "\\", false);
                        //File.Copy(Application.StartupPath + "\\files\\qr\\130e9f8M1266895bc62M16f11e9848aa57fa12a423611c17c7fb.jpg", basePtah + personId + "\\1\\" + "att03.jpg");
                        File.Copy(Application.StartupPath + "\\files\\qr\\" + personId + ".jpg", basePtah + personId + "\\1\\" + "att03.jpg");
                        File.Copy(Application.StartupPath + "\\files\\qr\\" + personId + ".jpg", basePtah + personId + "\\2\\" + "att03.jpg");
                        File.Copy(Application.StartupPath + "\\files\\qr\\" + personId + ".jpg", basePtah + personId + "\\3\\" + "att03.jpg");
                        File.Copy(Application.StartupPath + "\\files\\qr\\" + personId + ".jpg", basePtah + personId + "\\4\\" + "att03.jpg");
                        File.Copy(Application.StartupPath + "\\files\\qr\\" + personId + ".jpg", basePtah + personId + "\\5\\" + "att03.jpg");

                        Zip zip = new Zip();

                        string[] FileInfo1 = new string[2];
                        FileInfo1[0] = basePtah + personId + "\\1" + "\\";
                        FileInfo1[1] = basePtah + personId + "\\1.zip";
                        zip.ZipFileMain(FileInfo1);

                        string[] FileInfo2 = new string[2];
                        FileInfo2[0] = basePtah + personId + "\\2" + "\\";
                        FileInfo2[1] = basePtah + personId + "\\2.zip";
                        zip.ZipFileMain(FileInfo2);


                        string[] FileInfo3 = new string[2];
                        FileInfo3[0] = basePtah + personId + "\\3" + "\\";
                        FileInfo3[1] = basePtah + personId + "\\3.zip";
                        zip.ZipFileMain(FileInfo3);


                        string[] FileInfo4 = new string[2];
                        FileInfo4[0] = basePtah + personId + "\\4" + "\\";
                        FileInfo4[1] = basePtah + personId + "\\4.zip";
                        zip.ZipFileMain(FileInfo4);


                        string[] FileInfo5 = new string[2];
                        FileInfo5[0] = basePtah + personId + "\\5" + "\\";
                        FileInfo5[1] = basePtah + personId + "\\5.zip";
                        zip.ZipFileMain(FileInfo5);



                        Hashtable request = new Hashtable();
                        //request["Mms_xt"] = basePtah + personId + "\\1.zip";

                        request.Add("Mms_xt", basePtah + personId + "\\1.zip");
                        request.Add("Mms", basePtah + personId + "\\2.zip");
                        request.Add("Mms_dt", basePtah + personId + "\\3.zip");
                        request.Add("Mms_cd", basePtah + personId + "\\4.zip");
                        request.Add("Mms_zd", basePtah + personId + "\\5.zip");

                        request.Add("Mobile", Mobile);

                        msg.sendMsg(request);
                        labelInfo.Text = labelInfo.Text + "完成向" + personName + "发送彩信\r\n\r\n";
                    }
                    catch (Exception ex)
                    {
                        labelInfo.Text = labelInfo.Text + "向" + personName + "发送彩信失败\r\n\r\n";

                    }


                }

            }
            labelInfo.Text = labelInfo.Text + "\r\n完成发送彩信\r\n";


        }


        /// <summary>
        /// 发送短信
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button3_Click(object sender, EventArgs e)
        {
            //遍历所有与会人员
            DbUtil dbUtil = new DbUtil();
            DataTable table = dbUtil.GetData();
            Message msg = new Message();

            labelInfo.Text = "\r\n开始发送短信\r\n\r\n";
            labelInfo.Text = labelInfo.Text +  "\r\n开始创建短信\r\n";

            if (table != null && table.Rows.Count > 0)
            {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < table.Rows.Count; i++)
                {
                    //发送彩信
                    String personId = table.Rows[i]["PERSONID"].ToString();
                    String Mobile = table.Rows[i]["TELEPHONE"].ToString();
                    String personName = table.Rows[i]["PERSONNAME"].ToString();
                    sb.Append(Mobile);
                    sb.Append(",");
                }
                labelInfo.Text = labelInfo.Text + "完成创建短信\r\n\r\n";

                try
                {
                    Hashtable request = new Hashtable();
                    request.Add("SmsContent", textBox1.Text);
                    request.Add("Mobile", sb.ToString());
                    msg.sendTextMsg(request);
                   
                }
                catch (Exception ex)
                {
                    labelInfo.Text = labelInfo.Text + "发送短信失败\r\n\r\n";
                }

            }
            labelInfo.Text = labelInfo.Text + "\r\n完成短信发送\r\n";
        }
    }
}
