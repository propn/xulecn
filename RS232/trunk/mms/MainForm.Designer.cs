namespace mms
{
    partial class main
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(main));
            this.menu = new System.Windows.Forms.MenuStrip();
            this.file = new System.Windows.Forms.ToolStripMenuItem();
            this.imp = new System.Windows.Forms.ToolStripMenuItem();
            this.exp = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator2 = new System.Windows.Forms.ToolStripSeparator();
            this.mfiles = new System.Windows.Forms.ToolStripMenuItem();
            this.exitMenu = new System.Windows.Forms.ToolStripMenuItem();
            this.check = new System.Windows.Forms.ToolStripMenuItem();
            this.monitor = new System.Windows.Forms.ToolStripMenuItem();
            this.handCheck = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.pCount = new System.Windows.Forms.ToolStripMenuItem();
            this.message = new System.Windows.Forms.ToolStripMenuItem();
            this.editMmsMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.sendMsg = new System.Windows.Forms.ToolStripMenuItem();
            this.receiveMsg = new System.Windows.Forms.ToolStripMenuItem();
            this.help = new System.Windows.Forms.ToolStripMenuItem();
            this.about = new System.Windows.Forms.ToolStripMenuItem();
            this.monthCalendar1 = new System.Windows.Forms.MonthCalendar();
            this.label1 = new System.Windows.Forms.Label();
            this.menu.SuspendLayout();
            this.SuspendLayout();
            // 
            // menu
            // 
            this.menu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.file,
            this.check,
            this.message,
            this.help});
            this.menu.Location = new System.Drawing.Point(0, 0);
            this.menu.Name = "menu";
            this.menu.Size = new System.Drawing.Size(571, 24);
            this.menu.TabIndex = 0;
            this.menu.Text = "menuStrip1";
            // 
            // file
            // 
            this.file.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.imp,
            this.exp,
            this.toolStripSeparator2,
            this.mfiles,
            this.exitMenu});
            this.file.Name = "file";
            this.file.Size = new System.Drawing.Size(41, 20);
            this.file.Text = "文件";
            // 
            // imp
            // 
            this.imp.Name = "imp";
            this.imp.Size = new System.Drawing.Size(152, 22);
            this.imp.Text = "导入会议信息";
            this.imp.Click += new System.EventHandler(this.imp_Click);
            // 
            // exp
            // 
            this.exp.Name = "exp";
            this.exp.Size = new System.Drawing.Size(152, 22);
            this.exp.Text = "导出与会信息";
            this.exp.Click += new System.EventHandler(this.exp_Click);
            // 
            // toolStripSeparator2
            // 
            this.toolStripSeparator2.Name = "toolStripSeparator2";
            this.toolStripSeparator2.Size = new System.Drawing.Size(149, 6);
            // 
            // mfiles
            // 
            this.mfiles.Name = "mfiles";
            this.mfiles.Size = new System.Drawing.Size(152, 22);
            this.mfiles.Text = "离线办公";
            this.mfiles.Click += new System.EventHandler(this.mfiles_Click);
            // 
            // exitMenu
            // 
            this.exitMenu.Name = "exitMenu";
            this.exitMenu.Size = new System.Drawing.Size(152, 22);
            this.exitMenu.Text = "退出系统";
            this.exitMenu.Click += new System.EventHandler(this.exitMenu_Click_1);
            // 
            // check
            // 
            this.check.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.monitor,
            this.handCheck,
            this.toolStripSeparator1,
            this.pCount});
            this.check.Name = "check";
            this.check.Size = new System.Drawing.Size(41, 20);
            this.check.Text = "签到";
            // 
            // monitor
            // 
            this.monitor.Name = "monitor";
            this.monitor.Size = new System.Drawing.Size(118, 22);
            this.monitor.Text = "自动签到";
            this.monitor.Click += new System.EventHandler(this.monitor_Click);
            // 
            // handCheck
            // 
            this.handCheck.Name = "handCheck";
            this.handCheck.Size = new System.Drawing.Size(118, 22);
            this.handCheck.Text = "手工签到";
            this.handCheck.Click += new System.EventHandler(this.handCheck_Click);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(115, 6);
            // 
            // pCount
            // 
            this.pCount.Name = "pCount";
            this.pCount.Size = new System.Drawing.Size(118, 22);
            this.pCount.Text = "统计";
            this.pCount.Click += new System.EventHandler(this.pCount_Click);
            // 
            // message
            // 
            this.message.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.editMmsMenuItem,
            this.sendMsg,
            this.receiveMsg});
            this.message.Name = "message";
            this.message.Size = new System.Drawing.Size(41, 20);
            this.message.Text = "彩信";
            // 
            // editMmsMenuItem
            // 
            this.editMmsMenuItem.Name = "editMmsMenuItem";
            this.editMmsMenuItem.Size = new System.Drawing.Size(118, 22);
            this.editMmsMenuItem.Text = "发送彩信";
            this.editMmsMenuItem.Click += new System.EventHandler(this.editMmsMenuItem_Click);
            // 
            // sendMsg
            // 
            this.sendMsg.Name = "sendMsg";
            this.sendMsg.Size = new System.Drawing.Size(118, 22);
            this.sendMsg.Text = "发送短信";
            this.sendMsg.Click += new System.EventHandler(this.sendMsg_Click);
            // 
            // receiveMsg
            // 
            this.receiveMsg.Name = "receiveMsg";
            this.receiveMsg.Size = new System.Drawing.Size(118, 22);
            this.receiveMsg.Text = "查询结果";
            this.receiveMsg.Click += new System.EventHandler(this.receiveMsg_Click);
            // 
            // help
            // 
            this.help.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.about});
            this.help.Name = "help";
            this.help.Size = new System.Drawing.Size(41, 20);
            this.help.Text = "帮助";
            // 
            // about
            // 
            this.about.Name = "about";
            this.about.Size = new System.Drawing.Size(94, 22);
            this.about.Text = "关于";
            this.about.Click += new System.EventHandler(this.about_Click);
            // 
            // monthCalendar1
            // 
            this.monthCalendar1.Location = new System.Drawing.Point(252, 127);
            this.monthCalendar1.Name = "monthCalendar1";
            this.monthCalendar1.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.BackColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.label1.ForeColor = System.Drawing.SystemColors.ActiveCaption;
            this.label1.Location = new System.Drawing.Point(124, 78);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(0, 12);
            this.label1.TabIndex = 2;
            // 
            // main
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackgroundImage = global::mms.Properties.Resources.bg;
            this.ClientSize = new System.Drawing.Size(571, 369);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.monthCalendar1);
            this.Controls.Add(this.menu);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MainMenuStrip = this.menu;
            this.MaximizeBox = false;
            this.Name = "main";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "广州市政府办公厅会议系统";
            this.Load += new System.EventHandler(this.main_Load);
            this.menu.ResumeLayout(false);
            this.menu.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menu;
        private System.Windows.Forms.ToolStripMenuItem file;
        private System.Windows.Forms.ToolStripMenuItem imp;
        private System.Windows.Forms.ToolStripMenuItem exp;
        private System.Windows.Forms.ToolStripMenuItem check;
        private System.Windows.Forms.ToolStripMenuItem monitor;
        private System.Windows.Forms.ToolStripMenuItem pCount;
        private System.Windows.Forms.ToolStripMenuItem message;
        private System.Windows.Forms.ToolStripMenuItem sendMsg;
        private System.Windows.Forms.ToolStripMenuItem receiveMsg;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripMenuItem handCheck;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator2;
        private System.Windows.Forms.ToolStripMenuItem mfiles;
        private System.Windows.Forms.ToolStripMenuItem help;
        private System.Windows.Forms.ToolStripMenuItem about;
        private System.Windows.Forms.ToolStripMenuItem exitMenu;
        private System.Windows.Forms.MonthCalendar monthCalendar1;
        private System.Windows.Forms.ToolStripMenuItem editMmsMenuItem;
        private System.Windows.Forms.Label label1;
    }
}

