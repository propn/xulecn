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
    public partial class setForm : Form
    {
        string dbConnectionString = Properties.Settings.Default.dbConnectionString;
        string dataSource = "";
        string constr1 = "Provider=Microsoft.Jet.OLEDB.4.0;";
        string constr3 = " Persist Security Info=True";
             
        public setForm()
        {
            InitializeComponent();
        }

        private void setForm_Load(object sender, EventArgs e)
        {
            string[] info = dbConnectionString.Split(';');
            foreach(string str in info){
                if (str.StartsWith("Data Source"))
                {
                    dataSource = str.Split('=')[1];
                }
            }
            textBox1.Text = dataSource;
        }

        private void button1_Click(object sender, EventArgs e)
        {
             System.Windows.Forms.OpenFileDialog fd = new OpenFileDialog();
             fd.Filter = "access文件(*.mdb)|*.mdb";

            if (fd.ShowDialog() == DialogResult.OK)
            {
                textBox1.Text = fd.FileName;
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            dataSource = textBox1.Text;
          dbConnectionString = constr1 + dataSource + constr3;

//            Properties.Settings.Default.Properties.Remove("dbConnectionString");
  //          System.Configuration.SettingsProperty property = new System.Configuration.SettingsProperty("dbConnectionString");
    //        property.Attributes.Add("dbConnectionString", dbConnectionString);

//            Properties.Settings.Default.Properties.Add(property); 
 //           Properties.Settings.Default.Save();
 //           Properties.Settings.Default.Reload();

            this.Dispose();
        }
    }
}
