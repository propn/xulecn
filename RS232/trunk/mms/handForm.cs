﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace mms
{
    public partial class UpdateForm : Form
    {
        string personId = "";

        public UpdateForm()
        {
            InitializeComponent();
        }


        private void button1_Click(object sender, EventArgs e)
        {


            string phone = textBoxPhone.Text.Trim();

            if (phone.Length != 11 || !phone.StartsWith("13"))
            {
                MessageBox.Show("请输入正确的手机号码", "注意：", MessageBoxButtons.OK);
                textBoxPhone.Text = "";
                return;
            }

            DataTable table = (new DbUtil()).GetDataByPhone(phone);
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

                personId = table.Rows[0]["PERSONID"].ToString();


            }
            else
            {
                MessageBox.Show("手机号码号码不存在，请核对后重新输入", "注意：", MessageBoxButtons.OK);
                textBoxPhone.Text = "";
                return;
            }


        }

        private void UpdateForm_Load(object sender, EventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (personId != "")
            {
                new DbUtil().Update(personId);
                string time = new DbUtil().getCurrentTime();
                textBoxTime.Text = time;
                MessageBox.Show("成功签到时间:" + time, textBoxName.Text + " 签到成功", MessageBoxButtons.OK);
                this.Close();
            }
            else
            {
                MessageBox.Show("请先查找要进行手工签到的人，然后再进行签到操作", "注意", MessageBoxButtons.OK);
            }
        }
    }
}
