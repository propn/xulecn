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
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox comboBox1;
        private OleDbConnection conn = (new DbUtil()).getConn();
        private DataSet ds;
        string sql = "SELECT PERSONNAME as 姓名,  TELEPHONE as 手机号码, DEPTNAME as 单位名称, EXT3 as 职位,SEATINGNO as 座位号码 ,[TABLEID] as 签到时间 FROM MEETINGPERSON ";

        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.Container components = null;


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
            this.button1 = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            ((System.ComponentModel.ISupportInitialize)(this.dataGrid1)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGrid1
            // 
            this.dataGrid1.AllowSorting = false;
            this.dataGrid1.BackgroundColor = System.Drawing.SystemColors.Control;
            this.dataGrid1.CaptionBackColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.dataGrid1.CaptionForeColor = System.Drawing.SystemColors.ActiveCaption;
            this.dataGrid1.DataMember = "";
            this.dataGrid1.Font = new System.Drawing.Font("宋体", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dataGrid1.ForeColor = System.Drawing.SystemColors.ControlText;
            this.dataGrid1.HeaderForeColor = System.Drawing.SystemColors.ControlText;
            this.dataGrid1.Location = new System.Drawing.Point(1, 35);
            this.dataGrid1.Name = "dataGrid1";
            this.dataGrid1.PreferredColumnWidth = 100;
            this.dataGrid1.PreferredRowHeight = 30;
            this.dataGrid1.ReadOnly = true;
            this.dataGrid1.Size = new System.Drawing.Size(567, 323);
            this.dataGrid1.TabIndex = 4;
            // 
            // button1
            // 
            this.button1.Font = new System.Drawing.Font("宋体", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.button1.ForeColor = System.Drawing.SystemColors.ControlText;
            this.button1.Location = new System.Drawing.Point(391, 4);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 9;
            this.button1.Text = "开始查询";
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("宋体", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label1.ForeColor = System.Drawing.SystemColors.ControlText;
            this.label1.Location = new System.Drawing.Point(-2, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(101, 12);
            this.label1.TabIndex = 8;
            this.label1.Text = "请选择签到状态：";
            // 
            // comboBox1
            // 
            this.comboBox1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox1.Font = new System.Drawing.Font("宋体", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.comboBox1.ForeColor = System.Drawing.SystemColors.ControlText;
            this.comboBox1.Items.AddRange(new object[] {
            "所有人员",
            "已到人员",
            "未到人员"});
            this.comboBox1.Location = new System.Drawing.Point(105, 6);
            this.comboBox1.MaxDropDownItems = 3;
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(172, 20);
            this.comboBox1.TabIndex = 7;
            // 
            // BrowseForm
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(6, 14);
            this.ClientSize = new System.Drawing.Size(569, 367);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.dataGrid1);
            this.Name = "BrowseForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "浏览签到信息";
            ((System.ComponentModel.ISupportInitialize)(this.dataGrid1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }
        #endregion

        private void BrowseForm_Load(object sender, System.EventArgs e)
        {


        }


        private void button1_Click(object sender, System.EventArgs e)
        {

            string whereCnt = " where [TABLEID] is not null ";
            string distsql = "";

            if (null != comboBox1.Text.ToString())
            {
                if (comboBox1.Text.ToString().Equals("已到人员"))
                {
                    distsql = sql + whereCnt;
                }
                else if (comboBox1.Text.ToString().Equals("未到人员"))
                {
                    distsql = sql + " where [TABLEID] is null";
                }
                else
                {
                    distsql = sql;
                }
            }
            else
            {
                distsql = sql;
            }


            //防止重复打开连接
            if (conn.State != ConnectionState.Open)
            {
                conn.Open();
            }


            OleDbDataAdapter adp = new OleDbDataAdapter(distsql, conn);

            ds = new DataSet();
            ds.Clear();

            adp.Fill(ds);


            if (ds.Tables[0].Rows.Count != 0)
            {
                dataGrid1.DataSource = ds.Tables[0].DefaultView;
                dataGrid1.CaptionText = "共有" + ds.Tables[0].Rows.Count + "条查询结果";
            }
            else
            {
                dataGrid1.CaptionText = "没有您所查找的员工信息";
                dataGrid1.DataSource = null;
            }
        }

    }
}
