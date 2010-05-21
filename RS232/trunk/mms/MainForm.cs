using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

using mms.Properties;

namespace mms
{
    /// <summary>
    /// 
    /// </summary>
    public partial class main : Form
    {
        private string zipFileName = "";
        public  string meetingName = "";

        public main()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void monitor_Click(object sender, EventArgs e)
        {
            // MessageBox.Show("新增监控Form", "menuClick", MessageBoxButtons.OK);

            new monitorForm().Show();
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void imp_Click(object sender, EventArgs e)
        {

            System.Windows.Forms.OpenFileDialog fd = new OpenFileDialog();
            fd.Filter = "压缩文件(*.zip)|*.zip|所有文件(*.*)|*.*";

            if (fd.ShowDialog() == DialogResult.OK)
            {
                Zip unZip = new Zip();
                //删除历史文件
                if (Directory.Exists(Application.StartupPath + "\\files"))
                {
                    unZip.deleteDirectory(Application.StartupPath + "\\files");
                }



                //解压缩会议文件文件
                string[] FileProperties = new string[2];
                zipFileName = fd.SafeFileName.Substring(0, fd.SafeFileName.LastIndexOf("."));
                FileProperties[0] = fd.FileName;//待解压的文件
                //FileProperties[1] = Path.GetFullPath(".")+"\\";//解压后放置的目标目录
                // string projectPath = "";
                // projectPath = GetAppRootPath();
                //FileProperties[1] = Path.GetFullPath(".");//解压后放置的目标目录
                FileProperties[1] = Application.StartupPath + "\\files\\";


                unZip.UnZip(FileProperties);

                //导入excel
                ExcelUtil excelUtil = new ExcelUtil();
                ExcelUtil.impExcel(Application.StartupPath.Trim());

                MessageBox.Show("文件导入成功", "导入会议信息", MessageBoxButtons.OK);
                //panelMain.Hide();
                //this.ParentForm.Refresh();
                main_Load(sender, e);
               
                //monthCalendar1.Dispose();
            }
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void exp_Click(object sender, EventArgs e)
        {
            System.Windows.Forms.SaveFileDialog fd = new SaveFileDialog();

            if (fd.ShowDialog() == DialogResult.OK)
            {
                string[] FileProperties = new string[2];
                //修改
                string projectPath = Application.StartupPath + "\\files\\";
                //导出excel
                ExcelUtil.expExcel(Application.StartupPath);

                //压缩
                FileProperties[0] = projectPath;//待压缩文件目录
                FileProperties[1] = fd.FileName + ".zip"; //压缩后的目标文件
                Zip zip = new Zip();
                zip.ZipFileMain(FileProperties);

                MessageBox.Show("文件导入成功", "导出会议信息", MessageBoxButtons.OK);
               
            }

        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void mfiles_Click(object sender, EventArgs e)
        {
            new FilesForm().Show();

           // MessageBox.Show("打开会议文件列表", "menuClick", MessageBoxButtons.OK);
            
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void pCount_Click(object sender, EventArgs e)
        {
            //MessageBox.Show("打开会议签到情况列表", "menuClick", MessageBoxButtons.OK);
            //打开人员列表form
            //new QueryForm().Show();
            new BrowseForm().Show();

        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void handCheck_Click(object sender, EventArgs e)
        {
            //MessageBox.Show("打开手工签到页面", "menuClick", MessageBoxButtons.OK);
            new UpdateForm().Show();

        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void sendMsg_Click(object sender, EventArgs e)
        {
            //MessageBox.Show("打开发送彩信页面", "menuClick", MessageBoxButtons.OK);
            new Mm7Form().Show();
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void receiveMsg_Click(object sender, EventArgs e)
        {

            new MsgForm().Show();
            //MessageBox.Show("打开彩信接收结果列表", "menuClick", MessageBoxButtons.OK);
        }

        private void about_Click(object sender, EventArgs e)
        {
            new AboutForm().Show();
        }

        private string GetAppRootPath()
        {
            string projectPath = Application.StartupPath;
            //string projectPath = Directory.GetCurrentDirectory();//当前程序运行目录
            int appbinindex = projectPath.IndexOf("bin");
            if (appbinindex >= 0)
            {
                projectPath = projectPath.Substring(0, appbinindex);
            }
            return projectPath;
        }

      
        private void exitMenu_Click_1(object sender, EventArgs e)
        {
            Dispose(true);
        }

        private void editMmsMenuItem_Click(object sender, EventArgs e)
        {
            new editMmsForm().Show();

        }

        private void main_Load(object sender, EventArgs e)
        {
            //获取会议信息，如果系统未包含会议信息则提示导入信息
            DbUtil dbUtil = new DbUtil();
            //OleDbConnection conn = dbUtil.getConn();
            DataTable table = dbUtil.GetData();

            if (table != null && table.Rows.Count > 0)
            {
                //会议名称
                meetingName = table.Rows[0]["MEETINGNAME"].ToString();
                //会议地点
                string meetingRoomName = table.Rows[0]["QRID"].ToString();
                //会议时间
                string date = table.Rows[0]["EXT4"].ToString();
                toolStripStatusLabel1.Text = "会议名称:" + meetingName + "  时间:" + date + "  地点:" + meetingRoomName;
            }
        }

        private void 与会情况表ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new QueryForm().Show();
        }

        private void setMenuItem_Click(object sender, EventArgs e)
        {
            new setForm().Show();
        }
    }
}
