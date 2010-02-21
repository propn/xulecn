using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;

namespace mms
{
    static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]


        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            //Application.Run(new main());
            Application.Run(new editMmsForm());

            //Application.Run(new FrmTest());
            //Application.Run(new MsgForm());
            // Application.Run(new FilesForm());
            //Application.Run(new UpdateForm());
            //Application.Run(new BrowseForm());
        }


    }
}
