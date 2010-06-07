namespace mms
{
    partial class selectForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.mEETINGPERSONBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.dbDataSetBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.dbDataSet = new mms.dbDataSet();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.mEETINGPERSONTableAdapter = new mms.dbDataSetTableAdapters.MEETINGPERSONTableAdapter();
            this.NOTIFID = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            this.pERSONIDDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.pERSONNAMEDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dEPTNAMEDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.tELEPHONEDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.EXT1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.mEETINGPERSONBindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dbDataSetBindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dbDataSet)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridView1
            // 
            this.dataGridView1.AllowUserToAddRows = false;
            this.dataGridView1.AllowUserToDeleteRows = false;
            this.dataGridView1.AllowUserToOrderColumns = true;
            this.dataGridView1.AutoGenerateColumns = false;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.NOTIFID,
            this.pERSONIDDataGridViewTextBoxColumn,
            this.pERSONNAMEDataGridViewTextBoxColumn,
            this.dEPTNAMEDataGridViewTextBoxColumn,
            this.tELEPHONEDataGridViewTextBoxColumn,
            this.EXT1});
            this.dataGridView1.DataSource = this.mEETINGPERSONBindingSource;
            this.dataGridView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dataGridView1.Location = new System.Drawing.Point(0, 0);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowHeadersWidth = 60;
            this.dataGridView1.RowTemplate.Height = 23;
            this.dataGridView1.Size = new System.Drawing.Size(637, 437);
            this.dataGridView1.TabIndex = 0;
            // 
            // mEETINGPERSONBindingSource
            // 
            this.mEETINGPERSONBindingSource.DataMember = "MEETINGPERSON";
            this.mEETINGPERSONBindingSource.DataSource = this.dbDataSetBindingSource;
            // 
            // dbDataSetBindingSource
            // 
            this.dbDataSetBindingSource.DataSource = this.dbDataSet;
            this.dbDataSetBindingSource.Position = 0;
            // 
            // dbDataSet
            // 
            this.dbDataSet.DataSetName = "dbDataSet";
            this.dbDataSet.SchemaSerializationMode = System.Data.SchemaSerializationMode.IncludeSchema;
            // 
            // button1
            // 
            this.button1.AutoEllipsis = true;
            this.button1.Location = new System.Drawing.Point(525, 29);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 1;
            this.button1.Text = "全选";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(525, 58);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 2;
            this.button2.Text = "反选";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(525, 87);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(75, 23);
            this.button3.TabIndex = 3;
            this.button3.Text = "确定";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(525, 116);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(75, 23);
            this.button4.TabIndex = 4;
            this.button4.Text = "关闭";
            this.button4.UseVisualStyleBackColor = true;
            this.button4.Click += new System.EventHandler(this.button4_Click);
            // 
            // mEETINGPERSONTableAdapter
            // 
            this.mEETINGPERSONTableAdapter.ClearBeforeFill = true;
            // 
            // NOTIFID
            // 
            this.NOTIFID.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.AllCells;
            this.NOTIFID.DataPropertyName = "NOTIFID";
            this.NOTIFID.HeaderText = "选择";
            this.NOTIFID.Name = "NOTIFID";
            this.NOTIFID.SortMode = System.Windows.Forms.DataGridViewColumnSortMode.Automatic;
            this.NOTIFID.Width = 54;
            // 
            // pERSONIDDataGridViewTextBoxColumn
            // 
            this.pERSONIDDataGridViewTextBoxColumn.DataPropertyName = "PERSONID";
            this.pERSONIDDataGridViewTextBoxColumn.HeaderText = "PERSONID";
            this.pERSONIDDataGridViewTextBoxColumn.Name = "pERSONIDDataGridViewTextBoxColumn";
            this.pERSONIDDataGridViewTextBoxColumn.Visible = false;
            // 
            // pERSONNAMEDataGridViewTextBoxColumn
            // 
            this.pERSONNAMEDataGridViewTextBoxColumn.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.AllCells;
            this.pERSONNAMEDataGridViewTextBoxColumn.DataPropertyName = "PERSONNAME";
            this.pERSONNAMEDataGridViewTextBoxColumn.HeaderText = "姓名";
            this.pERSONNAMEDataGridViewTextBoxColumn.Name = "pERSONNAMEDataGridViewTextBoxColumn";
            this.pERSONNAMEDataGridViewTextBoxColumn.Width = 54;
            // 
            // dEPTNAMEDataGridViewTextBoxColumn
            // 
            this.dEPTNAMEDataGridViewTextBoxColumn.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.AllCells;
            this.dEPTNAMEDataGridViewTextBoxColumn.DataPropertyName = "DEPTNAME";
            this.dEPTNAMEDataGridViewTextBoxColumn.HeaderText = "单位";
            this.dEPTNAMEDataGridViewTextBoxColumn.Name = "dEPTNAMEDataGridViewTextBoxColumn";
            this.dEPTNAMEDataGridViewTextBoxColumn.Width = 54;
            // 
            // tELEPHONEDataGridViewTextBoxColumn
            // 
            this.tELEPHONEDataGridViewTextBoxColumn.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.AllCells;
            this.tELEPHONEDataGridViewTextBoxColumn.DataPropertyName = "TELEPHONE";
            this.tELEPHONEDataGridViewTextBoxColumn.HeaderText = "手机";
            this.tELEPHONEDataGridViewTextBoxColumn.Name = "tELEPHONEDataGridViewTextBoxColumn";
            this.tELEPHONEDataGridViewTextBoxColumn.Width = 54;
            // 
            // EXT1
            // 
            this.EXT1.DataPropertyName = "EXT1";
            this.EXT1.HeaderText = "彩信发送状态";
            this.EXT1.Name = "EXT1";
            this.EXT1.ReadOnly = true;
            // 
            // selectForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(637, 437);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.dataGridView1);
            this.Name = "selectForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "选择发送人员";
            this.Load += new System.EventHandler(this.selectForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.mEETINGPERSONBindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dbDataSetBindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dbDataSet)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.BindingSource dbDataSetBindingSource;
        private dbDataSet dbDataSet;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.BindingSource mEETINGPERSONBindingSource;
        private mms.dbDataSetTableAdapters.MEETINGPERSONTableAdapter mEETINGPERSONTableAdapter;
        private System.Windows.Forms.DataGridViewCheckBoxColumn NOTIFID;
        private System.Windows.Forms.DataGridViewTextBoxColumn pERSONIDDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn pERSONNAMEDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn dEPTNAMEDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn tELEPHONEDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn EXT1;
    }
}