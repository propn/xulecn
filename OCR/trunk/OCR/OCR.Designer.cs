namespace OCR
{
    partial class OCR
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

        #region 组件设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(OCR));
            this.statusBar1 = new System.Windows.Forms.StatusBar();
            this.axMiDocView1 = new AxMODI.AxMiDocView();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.FileMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.OpenMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.ScanMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.OcrMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.OcrF2MenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.帮助ToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            ((System.ComponentModel.ISupportInitialize)(this.axMiDocView1)).BeginInit();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // statusBar1
            // 
            this.statusBar1.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.statusBar1.Location = new System.Drawing.Point(0, 391);
            this.statusBar1.Name = "statusBar1";
            this.statusBar1.Size = new System.Drawing.Size(491, 24);
            this.statusBar1.TabIndex = 6;
            // 
            // axMiDocView1
            // 
            this.axMiDocView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.axMiDocView1.Enabled = true;
            this.axMiDocView1.Location = new System.Drawing.Point(0, 24);
            this.axMiDocView1.Name = "axMiDocView1";
            this.axMiDocView1.OcxState = ((System.Windows.Forms.AxHost.State)(resources.GetObject("axMiDocView1.OcxState")));
            this.axMiDocView1.Size = new System.Drawing.Size(491, 367);
            this.axMiDocView1.TabIndex = 7;
            this.axMiDocView1.SelectionChanged += new System.EventHandler(this.axMiDocView1_SelectionChanged);
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.FileMenuItem,
            this.OcrMenuItem,
            this.帮助ToolStripMenuItem1});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(491, 24);
            this.menuStrip1.TabIndex = 8;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // FileMenuItem
            // 
            this.FileMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.OpenMenuItem,
            this.ScanMenuItem});
            this.FileMenuItem.Name = "FileMenuItem";
            this.FileMenuItem.Size = new System.Drawing.Size(41, 20);
            this.FileMenuItem.Text = "文件";
            // 
            // OpenMenuItem
            // 
            this.OpenMenuItem.Name = "OpenMenuItem";
            this.OpenMenuItem.Size = new System.Drawing.Size(152, 22);
            this.OpenMenuItem.Text = "打开";
            this.OpenMenuItem.Click += new System.EventHandler(this.OpenMenuItem_Click);
            // 
            // ScanMenuItem
            // 
            this.ScanMenuItem.Name = "ScanMenuItem";
            this.ScanMenuItem.Size = new System.Drawing.Size(152, 22);
            this.ScanMenuItem.Text = "扫描";
            this.ScanMenuItem.Click += new System.EventHandler(this.ScanMenuItem_Click);
            // 
            // OcrMenuItem
            // 
            this.OcrMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.OcrF2MenuItem});
            this.OcrMenuItem.Name = "OcrMenuItem";
            this.OcrMenuItem.Size = new System.Drawing.Size(35, 20);
            this.OcrMenuItem.Text = "OCR";
            // 
            // OcrF2MenuItem
            // 
            this.OcrF2MenuItem.Name = "OcrF2MenuItem";
            this.OcrF2MenuItem.ShortcutKeys = System.Windows.Forms.Keys.F2;
            this.OcrF2MenuItem.Size = new System.Drawing.Size(152, 22);
            this.OcrF2MenuItem.Text = "OCR";
            this.OcrF2MenuItem.Click += new System.EventHandler(this.oCRToolStripMenuItem_Click);
            // 
            // 帮助ToolStripMenuItem1
            // 
            this.帮助ToolStripMenuItem1.Name = "帮助ToolStripMenuItem1";
            this.帮助ToolStripMenuItem1.Size = new System.Drawing.Size(41, 20);
            this.帮助ToolStripMenuItem1.Text = "帮助";
            // 
            // OCR
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.axMiDocView1);
            this.Controls.Add(this.statusBar1);
            this.Controls.Add(this.menuStrip1);
            this.Name = "OCR";
            this.Size = new System.Drawing.Size(491, 415);
            this.Load += new System.EventHandler(this.OCR_Load);
            ((System.ComponentModel.ISupportInitialize)(this.axMiDocView1)).EndInit();
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.StatusBar statusBar1;
        private AxMODI.AxMiDocView axMiDocView1;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem FileMenuItem;
        private System.Windows.Forms.ToolStripMenuItem OcrMenuItem;
        private System.Windows.Forms.ToolStripMenuItem OpenMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 帮助ToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem ScanMenuItem;
        private System.Windows.Forms.ToolStripMenuItem OcrF2MenuItem;

    }
}
