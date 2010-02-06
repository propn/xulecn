using System;
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
        public editMmsForm()
        {
            InitializeComponent();
        }

        private void editMmsForm_Load(object sender, EventArgs e)
        {
            textBox1.Text = "\r\n尊敬的领导请准时参加XXX会议:\r\n"
                + "时间：20100204 10：30-20100204 11：30 \r\n"
                + "地点：XXXXXX\r\n"
                + "注意事项：凭此短信进行签到，请勿删除该信息";

            textBox2.Text = "提示;4个LOGO图片用来适配不同大小的手机屏幕。"
            +"\r\n需要修改图片，请替换【程序安装目录】\\image\\下的图片";
            
            
            pictureBox1.Image=Image.FromFile("image\\0.jpg");
            pictureBox2.Image = Image.FromFile("image\\1.jpg");
            pictureBox3.Image = Image.FromFile("image\\2.jpg");
            pictureBox4.Image = Image.FromFile("image\\3.jpg");
            pictureBox5.Image = Image.FromFile("image\\4.jpg");

            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            
            //遍历所有与会人员
            DbUtil dbUtil = new DbUtil();
            //OleDbConnection conn = dbUtil.getConn();

            DataTable dataTable = dbUtil.GetData();

            if (table != null && table.Rows.Count > 0)
            {
                String name = table.Rows[0]["PERSONNAME"].ToString();
                textBoxName.Text = name;

                String DEPTNAME = table.Rows[0]["DEPTNAME"].ToString();
                textBoxDept.Text = DEPTNAME;

                String EXT2 = table.Rows[0]["EXT3"].ToString();
                textBoxJob.Text = EXT2;


                String SEATINGNO = table.Rows[0]["SEATINGNO"].ToString();
                textBoxNo.Text = SEATINGNO;

                String time = table.Rows[0]["TABLEID"].ToString();
                textBoxTime.Text = time;

                personId = table.Rows[0]["PERSONID"].ToString();


            }

          
            {
                //提交任务单
                //编辑短信
                //提交白名单
                //

            }
        }
    }
}
