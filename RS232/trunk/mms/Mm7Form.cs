using System;
using System.Windows.Forms;
using System.Net;
using CookComputing.XmlRpc;
using mms.Properties;

namespace mms
{
    /*
     * 发送手机彩信
     * 
     * 
     */
    public partial class Mm7Form : Form
    {
        private System.ComponentModel.IContainer components;
        private LinkLabel linkLabel1;
        private TextBox textBox1;
        private Label label2;

        private IMsg msg;
        public Mm7Form()
        {
            InitializeComponent();
            msg = XmlRpcProxyGen.Create<IMsg>();
            msg.Url = Settings.Default.rpcURL;
        }

        /// <summary>
        /// Clean up any resources being used.
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

        private void InitializeComponent()
        {
            this.linkLabel1 = new System.Windows.Forms.LinkLabel();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // linkLabel1
            // 
            this.linkLabel1.AutoSize = true;
            this.linkLabel1.Location = new System.Drawing.Point(235, 190);
            this.linkLabel1.Name = "linkLabel1";
            this.linkLabel1.Size = new System.Drawing.Size(53, 12);
            this.linkLabel1.TabIndex = 0;
            this.linkLabel1.TabStop = true;
            this.linkLabel1.Text = "发送彩信";
            this.linkLabel1.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.linkLabel1_LinkClicked);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(14, 24);
            this.textBox1.Multiline = true;
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(274, 144);
            this.textBox1.TabIndex = 3;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 9);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(113, 12);
            this.label2.TabIndex = 4;
            this.label2.Text = "体验平台使用说明：";
            // 
            // Mm7Form
            // 
            this.ClientSize = new System.Drawing.Size(306, 230);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.linkLabel1);
            this.Name = "Mm7Form";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "发送彩信";
            this.Load += new System.EventHandler(this.Mm7Form_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        private void Mm7Form_Load(object sender, System.EventArgs e)
        {
            textBox1.Text = "1．  暂不支持WAPPUSH功能，即彩信任务单的重要性不能选择“重要”；\r\n\r\n" +
                "2．  暂不支持合同长号码扩展，即短信、彩信任务单不能填写合同长号码扩展；\r\n\r\n" +
                "3．广州市政府彩信日发送量最多100条, 体验权限2010年1月14日生效，至2010年2月28日结束；\r\n\r\n" +
                "用户名：guangzhou_gov  密码：test_2010";
        }

        private void linkLabel1_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {

            linkLabel1.LinkVisited = true;
            System.Diagnostics.Process.Start("http://218.206.83.17:9080/eepwww");
        }



    }
}
