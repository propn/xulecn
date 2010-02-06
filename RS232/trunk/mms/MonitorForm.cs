using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO.Ports;
using System.Data.OleDb;
using mms.Properties;

namespace mms
{
    public partial class monitorForm : Form
    {
        string InputData = String.Empty;

        delegate void SetInfoCallback(string text);

        public monitorForm()
        {
            InitializeComponent();
            // Settings.Default.rpcURL;
            // Nice methods to browse all available ports:
            string[] ports = SerialPort.GetPortNames();

            // Add all port names to the combo box:
            foreach (string port in ports)
            {
                cmbComSelect.Items.Add(port);
            }
        }

        private void cmbComSelect_SelectionChangeCommitted(object sender, EventArgs e)
        {
            if (port.IsOpen) port.Close();
            port.PortName = cmbComSelect.SelectedItem.ToString();
            stsStatus.Text = port.PortName + ": 9600,8N1";

            // try to open the selected port:
            try
            {
                port.Open();
            }
            catch
            {
                MessageBox.Show("串口 " + port.PortName + " 打开监控出错!", "会议签到", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                cmbComSelect.SelectedText = "";
                stsStatus.Text = "请选择扫描仪使用的串口!";
            }
        }
        /*
        private void btnSend_Click(object sender, EventArgs e)
        {
            if (port.IsOpen) port.WriteLine(txtOut.Text);
            else MessageBox.Show("串口已关闭", "会议签到", MessageBoxButtons.OK, MessageBoxIcon.Error);
            txtOut.Clear();
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            txtIn.Clear();
        }
        */
        private void port_DataReceived_1(object sender, SerialDataReceivedEventArgs e)
        {
            InputData = port.ReadExisting();
            if (InputData != String.Empty)
            {
                InputData = InputData.Trim();
                //             txtIn.Text = InputData;   // because of different threads this does not work properly !!
                //SetText(InputData);
                //MessageBox.Show(InputData, "message come", MessageBoxButtons.OK, MessageBoxIcon.Hand);
                if (null != InputData && InputData.Length > 0)
                {
                    DataTable table = (new DbUtil()).GetDataByPersonId(InputData);
                    if (table != null && table.Rows.Count > 0)
                    {
                        String name = table.Rows[0]["PERSONNAME"].ToString();
                        textBoxName.Text = name;

                        String DEPTNAME = table.Rows[0]["DEPTNAME"].ToString();
                        textBoxDept.Text = DEPTNAME;

                        String EXT2 = table.Rows[0]["EXT3"].ToString();
                        textBoxJob.Text = EXT2;


                        String SEATINGNO = table.Rows[0]["SEATINGNO"].ToString();
                        textBoxNo.Text = SEATINGNO;

                        String time = table.Rows[0]["TABLEID"].ToString();
                        textBoxTime.Text = time;

                        string personId = table.Rows[0]["PERSONID"].ToString();

                        new DbUtil().Update(personId);
                        time = new DbUtil().getCurrentTime();
                        textBoxTime.Text = time;
                        SetInfo("签到成功");
                    }
                    else
                    {
                        SetInfo("警告：该签到卡无效");

                    }


                }
            }
        }


        private void SetInfo(string text)
        {
            // InvokeRequired required compares the thread ID of the
            // calling thread to the thread ID of the creating thread.
            // If these threads are different, it returns true.
            if (this.labelInfo.InvokeRequired)
            {
                SetInfoCallback d = new SetInfoCallback(SetInfo);
                this.Invoke(d, new object[] { text });
            }
            else this.labelInfo.Text = text;
        }



    }
}
