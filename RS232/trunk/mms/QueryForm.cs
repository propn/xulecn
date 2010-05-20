using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data.OleDb;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Data.SqlClient;


namespace mms
{
    public partial class QueryForm : Form
    {

        private OleDbConnection conn = (new DbUtil()).getConn();
        
        private DataSet ds;//已到
        private OleDbDataAdapter adp;

        string sql = "select c.DEPTNAME as 单位,c.应到人数,d.已到人数,c.未到人数 from (select b.DEPTNAME, b.应到人数 ,a.未到人数 from (select DEPTNAME , count(*) as 应到人数 from MEETINGPERSON  group by DEPTNAME )b  left join (select DEPTNAME , count(*) as 未到人数 from MEETINGPERSON  where ( [TABLEID]='' or [TABLEID] is null )  group by DEPTNAME )a on b.DEPTNAME=a.DEPTNAME )c left join (select DEPTNAME,count(*) as 已到人数 from  MEETINGPERSON   where ( [TABLEID]<>'' and [TABLEID] is not null ) group by DEPTNAME )d on c.DEPTNAME =d.DEPTNAME ";

        string sql2 = "select 应到人数,未到人数,已到人数 from (SELECT  COUNT(*) AS 应到人数  FROM MEETINGPERSON) a ,(select count(*) as 未到人数 from MEETINGPERSON  where ( [TABLEID]='' or [TABLEID] is null ) ) b,(select count(*) as 已到人数 from  MEETINGPERSON   where ( [TABLEID]<>'' and [TABLEID] is not null ) ) c";
     
        public QueryForm()
        {
            InitializeComponent();
        }

        private void QueryForm_Load(object sender, EventArgs e)
        {
            this.WindowState = FormWindowState.Maximized;
            query();
            dataGridView1.AutoResizeColumns();

            //获取会议信息，如果系统未包含会议信息则提示导入信息
            DbUtil dbUtil = new DbUtil();
            //OleDbConnection conn = dbUtil.getConn();
            DataTable table = dbUtil.GetData();

            if (table != null && table.Rows.Count > 0)
            {
                //会议名称
                label1.Text = table.Rows[0]["MEETINGNAME"].ToString();
                //会议时间
                label2.Text = table.Rows[0]["EXT4"].ToString();
            }
           
        }

        private void query()
        {
        
            //防止重复打开连接
            if (conn.State != ConnectionState.Open)
            {
                conn.Open();
            }
            adp = new OleDbDataAdapter(sql, conn);
            ds = new DataSet();
            ds.Clear();
            adp.Fill(ds);
            dataGridView1.DataSource = ds.Tables[0].DefaultView;

            adp = new OleDbDataAdapter(sql2, conn);
            DataTable table = new DataTable();
            adp.Fill(table);

            if (table != null && table.Rows.Count > 0)
            {
                //会议名称
                label6.Text = table.Rows[0]["应到人数"].ToString();
                label3.Text = table.Rows[0]["未到人数"].ToString();
                label4.Text = table.Rows[0]["已到人数"].ToString();
            }

            textBox1.Text = "";
            DataTable table2=ds.Tables[0];
            if (table2 != null && table2.Rows.Count > 0)
            {
                foreach (DataGridViewRow row in this.dataGridView1.Rows)
                {
                    if (row.Cells[2].Value== null || row.Cells[2].FormattedValue.ToString() == ""  )
                    {
                        if (row.Cells[0].Value !=null)
                        {
                            textBox1.Text = textBox1.Text + "\r\n" + row.Cells[0].Value.ToString();
                        }
                        
                    }
                }
            }

        }

        private void panel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            query();
        }
    }
}
