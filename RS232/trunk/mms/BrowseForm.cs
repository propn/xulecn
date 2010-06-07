using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data.OleDb;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace mms
{
    public partial class BrowseForm : System.Windows.Forms.Form
    {
        private System.Windows.Forms.DataGrid dataGrid1;
        private System.Windows.Forms.ComboBox comboBox1;
        private OleDbConnection conn = (new DbUtil()).getConn();
        private DataSet ds1;//已到
        private DataSet ds2;//未到
        string sql = "SELECT PERSONNAME as 姓名,  TELEPHONE as 手机号码, DEPTNAME as 单位名称, EXT3 as 职位,[TABLEID] as 签到时间,SEATINGNO as 座号  FROM MEETINGPERSON ";
        private Button button2;
        private IContainer components;


        public BrowseForm()
        {
            InitializeComponent();
        }


        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                {
                    components.Dispose();
                }
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码
        /// <summary>
        /// 设计器支持所需的方法 - 不要使用代码编辑器修改
        /// 此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.dataGrid1 = new System.Windows.Forms.DataGrid();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.button2 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGrid1)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGrid1
            // 
            this.dataGrid1.BackgroundColor = System.Drawing.SystemColors.Control;
            this.dataGrid1.CaptionBackColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.dataGrid1.CaptionForeColor = System.Drawing.SystemColors.ActiveCaption;
            this.dataGrid1.DataMember = "";
            this.dataGrid1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dataGrid1.Font = new System.Drawing.Font("宋体", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dataGrid1.ForeColor = System.Drawing.SystemColors.ControlText;
            this.dataGrid1.HeaderForeColor = System.Drawing.SystemColors.ControlText;
            this.dataGrid1.Location = new System.Drawing.Point(0, 0);
            this.dataGrid1.Name = "dataGrid1";
            this.dataGrid1.PreferredColumnWidth = 150;
            this.dataGrid1.PreferredRowHeight = 30;
            this.dataGrid1.ReadOnly = true;
            this.dataGrid1.RowHeaderWidth = 50;
            this.dataGrid1.Size = new System.Drawing.Size(859, 507);
            this.dataGrid1.TabIndex = 4;
            // 
            // comboBox1
            // 
            this.comboBox1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox1.Font = new System.Drawing.Font("宋体", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.comboBox1.ForeColor = System.Drawing.SystemColors.ControlText;
            this.comboBox1.Items.AddRange(new object[] {
            "未到人员",
            "已到人员"});
            this.comboBox1.Location = new System.Drawing.Point(636, 2);
            this.comboBox1.MaxDropDownItems = 3;
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(131, 20);
            this.comboBox1.TabIndex = 7;
            this.comboBox1.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged);
            // 
            // button2
            // 
            this.button2.AutoSize = true;
            this.button2.Font = new System.Drawing.Font("宋体", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.button2.ForeColor = System.Drawing.SystemColors.ControlText;
            this.button2.Location = new System.Drawing.Point(784, 0);
            this.button2.Name = "button2";
            this.button2.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.button2.Size = new System.Drawing.Size(75, 22);
            this.button2.TabIndex = 2;
            this.button2.Text = "关闭";
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // BrowseForm
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(6, 14);
            this.ClientSize = new System.Drawing.Size(859, 507);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.dataGrid1);
            this.Name = "BrowseForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "浏览签到信息";
            this.Load += new System.EventHandler(this.BrowseForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGrid1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }
        #endregion

                

        private void query()
        {

            //MessageBox.Show("ok");
            string whereCnt1 = " where  ([TABLEID] IS NOT NULL) AND ([TABLEID] <> '') order by DEPTNAME";
            string whereCnt2 = " where [TABLEID] is null or [TABLEID]='' order by DEPTNAME";
            string distsql1 = sql + whereCnt1;
            string distsql2 = sql + whereCnt2;

            //防止重复打开连接
            if (conn.State != ConnectionState.Open)
            {
                conn.Open();
            }


            OleDbDataAdapter adp = new OleDbDataAdapter(distsql1, conn);
           
            ds1 = new DataSet();
            ds1.Clear();
            adp.Fill(ds1);

            adp = new OleDbDataAdapter(distsql2, conn);
            ds2 = new DataSet();
            ds2.Clear();
            adp.Fill(ds2);


            if (null != comboBox1.Text.ToString())
            {
                if (comboBox1.Text.ToString().Equals("已到人员"))
                {
                    dataGrid1.DataSource = ds1.Tables[0].DefaultView;
                }
                else if (comboBox1.Text.ToString().Equals("未到人员"))
                {
                    dataGrid1.DataSource = ds2.Tables[0].DefaultView;
                }
            }

            dataGrid1.CaptionText = "已到人数：" + ds1.Tables[0].Rows.Count + "     未到人数：" + ds2.Tables[0].Rows.Count;
          
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Dispose(true);
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            query();
        }

        private void BrowseForm_Load(object sender, EventArgs e)
        {
            comboBox1.SelectedIndex = 0;
            this.WindowState = FormWindowState.Maximized;


        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            query();
        }

       
    }
}
