using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;
//using System.Web.UI.WebControls;


namespace mms
{
    public partial class FilesForm : Form
    {


        public FilesForm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            FileUtils.openFile("c:\\a.doc");
        }

        private void listBox1_DoubleClick(object sender, EventArgs e)
        {
            // ListItem item = this.listBox1.SelectedItems[0];
            // if (this.listBox1.SelectItem != null)
            //  {
            //要做的事 
            //   } 

            //Convert.ToInt32(listBox1.SelectedItems[0].Value);


            //FileUtils.openFile(item.Value);
        }

        private void FilesForm_Load(object sender, EventArgs e)
        {
            // this.listBox1.Items.Clear();
            string path = Application.StartupPath + "\\files\\";

            string directoryCreator = Path.GetDirectoryName(path);

            if (!System.IO.Directory.Exists(directoryCreator))
            {
                System.IO.Directory.CreateDirectory(directoryCreator);
            }
            

            string[] MyFiles = System.IO.Directory.GetFiles(path);

            //new ListItem("姓名", "id");

            foreach (string filePath in MyFiles)
            {
                string fileName = filePath.Substring(filePath.LastIndexOf("\\") + 1, filePath.Length - filePath.LastIndexOf("\\") - 1);
                ListViewItem aItem = new ListViewItem(fileName, 0);

                //aItem.ImageIndex = 1;

                string createTime = File.GetCreationTime(filePath).ToString();
                aItem.SubItems.Add(createTime);


                string writeTime = File.GetLastWriteTime(filePath).ToString();
                aItem.SubItems.Add(writeTime);

                aItem.SubItems.Add(filePath);


                MyListView.Items.Add(aItem);
            }

            MyListView.View = View.Details;


        }

        private void MyListView_MouseDoubleClick(object sender, MouseEventArgs e)
        {
           // ListView lv = ((ListView)sender);
           // string str = lv.GetItemAt(e.X, e.Y + 3).Text.ToString();
           // MessageBox.Show(str, e.X + "" + e.Y, MessageBoxButtons.OK);

            if (this.MyListView.SelectedItems.Count != 0)
            {
                ListViewItem item = this.MyListView.SelectedItems[0];
                string filePath = item.SubItems[3].Text.Trim();
                try
                {

                    FileUtils.openFile(filePath);
                }catch(Exception ex){
                    MessageBox.Show(ex.Message);
                }
            }


        }


    }


}
