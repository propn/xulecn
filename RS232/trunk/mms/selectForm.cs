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
    public partial class selectForm : Form
    {

        public selectForm()
        {
            InitializeComponent();
        }

        private void selectForm_Load(object sender, EventArgs e)
        {
            // TODO: 这行代码将数据加载到表“dbDataSet.MEETINGPERSON”中。您可以根据需要移动或移除它。
            this.mEETINGPERSONTableAdapter.Fill(this.dbDataSet.MEETINGPERSON);

        }

        private void button4_Click(object sender, EventArgs e)
        {
            this.Dispose();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            string sql = "UPDATE  MEETINGPERSON  SET  NOTIFID = True WHERE (PERSONID IN (";
            string personids = "";
            foreach(DataGridViewRow row in this.dataGridView1.Rows)
            {
               if (row.Cells[0].FormattedValue.ToString() == "True"){
                   personids = personids + "'" + row.Cells[1].Value.ToString()+"',";
               }
            }
            DbUtil dbu = new DbUtil();
            OleDbConnection conn = dbu.getConn();
            if (conn.State != ConnectionState.Open)
            {
                conn.Open();
            }

            //初始化连接
            OleDbCommand cmd2 = new OleDbCommand("UPDATE  MEETINGPERSON  SET  NOTIFID = False");
            cmd2.Connection = conn;
            cmd2.ExecuteNonQuery();

            OleDbCommand cmd = new OleDbCommand(sql+personids+"))");
            cmd.Connection = conn;
            cmd.ExecuteNonQuery();
            
            conn.Close();
            this.Dispose();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            DbUtil dbu = new DbUtil();
            OleDbConnection conn = dbu.getConn();
            if (conn.State != ConnectionState.Open)
            {
                conn.Open();
            }

            //初始化连接
            OleDbCommand cmd2 = new OleDbCommand("UPDATE  MEETINGPERSON  SET  NOTIFID = True");
            cmd2.Connection = conn;
            cmd2.ExecuteNonQuery();

            conn.Close();
            this.Dispose();

        }

        private void button2_Click(object sender, EventArgs e)
        {
            DbUtil dbu = new DbUtil();
            OleDbConnection conn = dbu.getConn();
            if (conn.State != ConnectionState.Open)
            {
                conn.Open();
            }

            //初始化连接
            OleDbCommand cmd2 = new OleDbCommand("UPDATE  MEETINGPERSON  SET  NOTIFID = True");
            cmd2.Connection = conn;
            cmd2.ExecuteNonQuery();

          

            string sql = "UPDATE  MEETINGPERSON  SET  NOTIFID = False WHERE (PERSONID IN (";
            string personids = "";
            foreach (DataGridViewRow row in this.dataGridView1.Rows)
            {
                if (row.Cells[0].FormattedValue.ToString() == "True")
                {
                    personids = personids + "'" + row.Cells[1].Value.ToString() + "',";
                }
            }

            OleDbCommand cmd = new OleDbCommand(sql + personids + "))");
            cmd.Connection = conn;
            cmd.ExecuteNonQuery();

           

            conn.Close();
            this.Dispose();

        }
    }
}
