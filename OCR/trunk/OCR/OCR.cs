using System;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using Microsoft.Win32;
using System.ComponentModel;
using WIALib;
using System.IO;
using System.Drawing.Imaging;
using System.Drawing;


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
        /// <summary>
        /// 扫描后图片
        /// </summary>
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
            fd.Filter = "扫描文件(*.tif)|*.tif|位图文件(*.bmp)|*.bmp|JPG文件(*.jpg)|*.jpg|GIF文件(*.gif)|*.gif|PNG文件(*.png)|*.png";

            if (fd.ShowDialog() == DialogResult.OK)
            {
                string fileName = fd.SafeFileName;
                string fileType = fileName.Substring(fileName.LastIndexOf(".")+1, 3);
                
                fileType = fileType.ToLower();

                if (fileType.Equals("tif"))
                {
                    filePath = fd.FileName;
                    
                }
                else
                {
                    ImageConverter(fd.FileName);
                }

                SetImage(filePath);
                
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
        /// 扫描图片、专为TIFF格式并显示
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ScanMenuItem_Click(object sender, EventArgs e)
        {
            WiaClass wiaManager = null;		// WIA manager COM object
            CollectionClass wiaDevs = null;		// WIA devices collection COM object
            ItemClass wiaRoot = null;		// WIA root device COM object
            CollectionClass wiaPics = null;		// WIA collection COM object
            ItemClass wiaItem = null;		// WIA image COM object
            									/// <summary> temporary image file. </summary>

            try
            {
                wiaManager = new WiaClass();		// create COM instance of WIA manager

                wiaDevs = wiaManager.Devices as CollectionClass;			// call Wia.Devices to get all devices
                if ((wiaDevs == null) || (wiaDevs.Count == 0))
                {
                    MessageBox.Show(this, "未找到扫描仪或照相机，请安装支持微软WIA接口的的设备!", "WIA", MessageBoxButtons.OK, MessageBoxIcon.Stop);
                    //Application.Exit();
                    return;
                }

                object selectUsingUI = System.Reflection.Missing.Value;			// = Nothing
                wiaRoot = (ItemClass)wiaManager.Create(ref selectUsingUI);	    // let user select device

                if (wiaRoot == null)
                {											// nothing to do
                    return;
                }
                // this call shows the common WIA dialog to let the user select a picture:
                wiaPics = wiaRoot.GetItemsFromUI(WiaFlag.SingleImage, WiaIntent.ImageTypeColor) as CollectionClass;

                if (wiaPics == null)
                    return;

                foreach (object wiaObj in wiaPics)			// enumerate all the pictures the user selected
                {
                   
                        //DisposeImage();						// remove previous picture
                        wiaItem = (ItemClass)Marshal.CreateWrapperOfType(wiaObj, typeof(ItemClass));
                        filePath = Path.GetTempFileName();				// create temporary file for image
                        filePath = filePath.Substring(0, filePath.LastIndexOf(".")) + ".bmp";
                        Cursor.Current = Cursors.WaitCursor;				// could take some time
                        this.Refresh();

                        MessageBox.Show(filePath);

                        wiaItem.Transfer(filePath, false);			// transfer picture to our temporary file
                        
                        Marshal.ReleaseComObject(wiaObj);					// release enumerated COM object

                        ImageConverter(filePath);
                }
            }
            catch (Exception ee)
            {
                MessageBox.Show(this, "获取图像失败\r\n" + ee.Message, "WIA", MessageBoxButtons.OK, MessageBoxIcon.Stop);
               
            }
            finally
            {
                if (wiaItem != null)
                {
                    Marshal.ReleaseComObject(wiaItem);		// release WIA image COM object
                }
                if (wiaPics != null)
                {
                    Marshal.ReleaseComObject(wiaPics);		// release WIA collection COM object
                }
                if (wiaRoot != null)
                {
                    Marshal.ReleaseComObject(wiaRoot);		// release WIA root device COM object
                }
                if (wiaDevs != null)
                {
                    Marshal.ReleaseComObject(wiaDevs);		// release WIA devices collection COM object
                }
                if (wiaManager != null)
                {
                    Marshal.ReleaseComObject(wiaManager);		// release WIA manager COM object
                }
                Cursor.Current = Cursors.Default;				// restore cursor
            }
        }

        /// <summary>
        /// 转换BMP格式文件为TIFF格式文件,并删除bmp文件
        /// </summary>
        /// <param name="bmpFile"></param>
        /// <returns>Tif文件路径</returns>
        public void ImageConverter(string strFileName)
        {

            try
            {
                Bitmap m_bitmap = new Bitmap(strFileName); ;
                
                string imageFileName = Path.GetTempFileName();// create temporary file for image
                imageFileName = imageFileName.Substring(0, imageFileName.LastIndexOf(".")) + ".tif";

                m_bitmap.Save(imageFileName, ImageFormat.Tiff);

                m_bitmap.Dispose();

               // File.Delete(strFileName);

                filePath= imageFileName;

               // MessageBox.Show(filePath);

                SetImage(imageFileName);
            }
            catch (Exception ee)
            {
                MessageBox.Show("转换图片格式出错\r\n" + ee.Message,"WIA", MessageBoxButtons.OK, MessageBoxIcon.Stop);
            }


        }



        [ComVisible(true)]
        public void Close()
        {
            
            if (OnClose != null)
            {
                OnClose("http://www.baidu.com"); //Calling event that will be catched in JS
            }
            else
            {
                MessageBox.Show("No Event Attached"); //If no events are attached send message.
            }
        }


        /// <summary>
        /// This interface shows events to javascript
        /// </summary>
        [Guid("68BD4E0D-D7BC-4cf6-BEB7-CAB950161E52")]
        [InterfaceType(ComInterfaceType.InterfaceIsIDispatch)]
        public interface ControlEvents
        {
            //Add a DispIdAttribute to any members in the source interface to specify the COM DispId.
            [DispId(0x60020000)]
            void OnClose(string redirectUrl);
        }

        public event ControlEventHandler OnClose;

        private void saveMenuItem_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(filePath))	// no tiff exists
            {		
                MessageBox.Show("文件不存在");
                return;
            }

            SaveFileDialog sd = new SaveFileDialog();
            sd.Title = "Save Image As...";
            sd.FileName = "scan.tif";
            sd.Filter = "TIFF file (*.tif)|*.tif";	// bmp bitmap file format
            if (sd.ShowDialog() != DialogResult.OK)
            {
                return;
            }
            _MODIDocument.SaveAs(sd.FileName, MODI.MiFILE_FORMAT.miFILE_FORMAT_TIFF, MODI.MiCOMP_LEVEL.miCOMP_LEVEL_MEDIUM);
            
           // pictureBox.Image.Save(sd.FileName);		// save to file
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

    public delegate void ControlEventHandler(string redirectUrl);

}
