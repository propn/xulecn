using System;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using Microsoft.Win32;


namespace OCR
{

    /// <summary>
    /// TIF扫描文件OCR识别
    /// </summary>
    [ProgId("OCR")]
    [Guid("2C4A839B-01CB-4f85-AF94-48036F674776")]
    [ComVisible(true)]
    public partial class OCR : UserControl ,IDisposable
    {
        private string filePath = ""; 
        private MODIOCRParameters _MODIParameters = new MODIOCRParameters();
        private MODI.Document _MODIDocument = null;

        public OCR()
        {
            InitializeComponent();
        }

        ~OCR()
        {
        }

        /// <summary>
        /// 初始化显示内容
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OCR_Load(object sender, EventArgs e)
        {
            init();//初始化显示
        }

        private void SetImage(string filename)
        {
            // set the image..
            try
            {
                _MODIDocument = new MODI.Document();
                _MODIDocument.Create(filename);
                axMiDocView1.Document = _MODIDocument;

                axMiDocView1.Refresh();
            }
            catch (System.Runtime.InteropServices.COMException ee)
            {
                MessageBox.Show(ee.Message);
            }
        }

        /*改用菜单监听F2
        private void axMiDocView1_PreviewKeyDown(object sender, PreviewKeyDownEventArgs e)
        {
            if (e.KeyCode.Equals(Keys.F2))
            {
                Analyse();
            }
        }*/


        private void axMiDocView1_SelectionChanged(object sender, EventArgs e)
        {

            // copy to clipboard..
            if (axMiDocView1.TextSelection != null)
            {
                axMiDocView1.TextSelection.CopyToClipboard();
            }
        }


        //进行OCR识别
        public void Analyse()
        {
            if (_MODIDocument == null) return;
            try
            {
                
                _MODIDocument.OnOCRProgress += new MODI._IDocumentEvents_OnOCRProgressEventHandler(this.ShowProgress);
               
                _MODIDocument.OCR(_MODIParameters.Language, _MODIParameters.WithAutoRotation, _MODIParameters.WithStraightenImage);
                _MODIDocument.Save();
                MessageBox.Show("save");
                statusBar1.Text = "识别完成...";
            }
            catch (Exception ee)
            {
                // simple exception "handling"
                MessageBox.Show(ee.Message);
            }
        }



        public void ShowProgress(int progress, ref bool cancel)
        {
            statusBar1.Text = progress.ToString() + "% processed.";
        }




        ///	<summary>
        ///	Register the class as a	control	and	set	it's CodeBase entry
        ///	</summary>
        ///	<param name="key">The registry key of the control</param>
        [ComRegisterFunction()]
        public static void RegisterClass(string key)
        {
            // Strip off HKEY_CLASSES_ROOT\ from the passed key as I don't need it
            StringBuilder sb = new StringBuilder(key);

            sb.Replace(@"HKEY_CLASSES_ROOT\", "");
            // Open the CLSID\{guid} key for write access
            RegistryKey k = Registry.ClassesRoot.OpenSubKey(sb.ToString(), true);

            // And create	the	'Control' key -	this allows	it to show up in
            // the ActiveX control container
            RegistryKey ctrl = k.CreateSubKey("Control");
            ctrl.Close();

            // Next create the CodeBase entry	- needed if	not	string named and GACced.
            RegistryKey inprocServer32 = k.OpenSubKey("InprocServer32", true);
            inprocServer32.SetValue("CodeBase", Assembly.GetExecutingAssembly().CodeBase);
            inprocServer32.Close();
            // Finally close the main	key
            k.Close();
            MessageBox.Show("TIF文件OCR控件安装成功");
        }

        ///	<summary>
        ///	Called to unregister the control
        ///	</summary>
        ///	<param name="key">Tke registry key</param>
        [ComUnregisterFunction()]
        public static void UnregisterClass(string key)
        {
            StringBuilder sb = new StringBuilder(key);
            sb.Replace(@"HKEY_CLASSES_ROOT\", "");

            // Open	HKCR\CLSID\{guid} for write	access
            RegistryKey k = Registry.ClassesRoot.OpenSubKey(sb.ToString(), true);

            // Delete the 'Control'	key, but don't throw an	exception if it	does not exist
            k.DeleteSubKey("Control", false);

            // Next	open up	InprocServer32
            //RegistryKey	inprocServer32 = 
            k.OpenSubKey("InprocServer32", true);

            // And delete the CodeBase key,	again not throwing if missing
            k.DeleteSubKey("CodeBase", false);

            // Finally close the main key
            k.Close();
            MessageBox.Show("TIF文件OCR控件删除完成");
        }

        
        /// <summary>
        /// F2
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void oCRToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Analyse();

        }

        /// <summary>
        /// 打开TIF文件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OpenMenuItem_Click(object sender, EventArgs e)
        {
            System.Windows.Forms.OpenFileDialog fd = new OpenFileDialog();
            fd.Filter = "扫描文件(*.tif)|*.tif";

            if (fd.ShowDialog() == DialogResult.OK)
            {
                //MessageBox.Show(fd.FileName);
                SetImage(fd.FileName);
            }
        }


        /// <summary>
        /// 
        /// tif文件路径
        /// </summary>
        [ComVisible(true)]
        public string FilePath
        {
            get
            {
                return filePath;
            }

            set
            {
                filePath = value;
            }
        }

        /// <summary>
        /// 初始化
        /// </summary>
        [ComVisible(true)]
        public void init()
        {
            if(!String.IsNullOrEmpty(FilePath)){
                SetImage(FilePath);
            }
        }

        /// <summary>
        /// 关闭
        /// </summary>
        [ComVisible(true)]
        public void close()
        {
            MessageBox.Show("close");
            _MODIDocument = null;
            Dispose();
        }




      
        /// <summary>
        /// 扫描图片并显示
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ScanMenuItem_Click(object sender, EventArgs e)
        {

           
        }

        

    }

    /// <summary>
    /// Representation for the MODI OCR parameters
    /// OCR 参数
    /// 
    /// </summary>
    public class MODIOCRParameters
    {
        private MODI.MiLANGUAGES _language = MODI.MiLANGUAGES.miLANG_CHINESE_SIMPLIFIED;//语音默认为中文

        public MODI.MiLANGUAGES Language
        {
            get { return _language; }
            set { _language = value; }
        }
        private bool _withAutoRotation = true;
        public bool WithAutoRotation
        {
            get { return _withAutoRotation; }
            set { _withAutoRotation = value; }
        }
        private bool _WithStraightenImage = true;
        public bool WithStraightenImage
        {
            get { return _WithStraightenImage; }
            set { _WithStraightenImage = value; }
        }
    }

}
