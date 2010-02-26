/// <summary>
/// 压缩文件
/// </summary>

using System;
using System.IO;

using ICSharpCode.SharpZipLib.Checksums;
using ICSharpCode.SharpZipLib.Zip;
using ICSharpCode.SharpZipLib.GZip;
using System.Windows.Forms;



namespace mms
{
    class Zip
    {

        ZipOutputStream s = null;
        Crc32 crc = null;
        string fileprefixpath = "";
        /// <summary>
        /// 压缩文件
        /// </summary>
        /// <param name="FileToZip"></param>
        /// <param name="ZipedFile"></param>
        /// <param name="CompressionLevel"></param>
        /// <param name="BlockSize"></param>
        public void ZipFile(string FileToZip, string ZipedFile, int CompressionLevel, int BlockSize)
        {

            //如果文件没有找到，则报错
            if (!System.IO.File.Exists(FileToZip))
            {
                throw new System.IO.FileNotFoundException("The specified file " + FileToZip + " could not be found. Zipping aborderd");
            }

            System.IO.FileStream StreamToZip = new System.IO.FileStream(FileToZip, System.IO.FileMode.Open, System.IO.FileAccess.Read);
            System.IO.FileStream ZipFile = System.IO.File.Create(ZipedFile);
            ZipOutputStream ZipStream = new ZipOutputStream(ZipFile);
            ZipEntry ZipEntry = new ZipEntry("ZippedFile");
            ZipStream.PutNextEntry(ZipEntry);
            ZipStream.SetLevel(CompressionLevel);
            byte[] buffer = new byte[BlockSize];
            System.Int32 size = StreamToZip.Read(buffer, 0, buffer.Length);
            ZipStream.Write(buffer, 0, size);
            try
            {
                while (size < StreamToZip.Length)
                {
                    int sizeRead = StreamToZip.Read(buffer, 0, buffer.Length);
                    ZipStream.Write(buffer, 0, sizeRead);
                    size += sizeRead;

                }
            }
            catch (System.Exception ex)
            {
                throw ex;
            }
            ZipStream.Finish();
            ZipStream.Close();
            StreamToZip.Close();
        }

        /// <summary>
        /// 压缩文件夹
        /// </summary>
        /// <param name="args"></param>
        public void ZipFileMain(string[] args)
        {

            fileprefixpath = args[0];
            crc = new Crc32();

            string directoryCreator = Path.GetDirectoryName(args[1]);
            if (!System.IO.Directory.Exists(directoryCreator))
            {
                System.IO.Directory.CreateDirectory(directoryCreator);
            }
            
            s = new ZipOutputStream(File.Create(args[1]));

            s.SetLevel(4); // 0 - store only to 9 - means best compression

            //string[] filenames = Directory.GetFiles(args[0]);
            
            SearchDirectory(args[0]);

            s.Finish();
            s.Close();
        }

        /// <summary>
        /// 解压缩zip格式
        /// </summary>
        /// <param name="args"></param>
        public void UnZip(string[] args)
        {
            ZipInputStream s = new ZipInputStream(File.OpenRead(args[0]));

            ZipEntry theEntry;
            while ((theEntry = s.GetNextEntry()) != null)
            {

                string directoryName = Path.GetDirectoryName(args[1]);
                //修改，根目录时Path.GetDirectoryName返回null
                if (string.IsNullOrEmpty(directoryName))
                {
                    directoryName = args[1];
                }
                string fileName = Path.GetFileName(theEntry.Name);

                //生成解压目录
                Directory.CreateDirectory(directoryName);

                if (fileName != String.Empty)
                {

                    //解压文件到指定的目录
                    string directoryCreator = Path.GetDirectoryName(args[1] + theEntry.Name);
                    if (!System.IO.Directory.Exists(directoryCreator))
                    {
                        System.IO.Directory.CreateDirectory(directoryCreator);
                    }
                    FileStream streamWriter = File.Create(args[1] + theEntry.Name);

                    int size = 2048;
                    byte[] data = new byte[2048];
                    while (true)
                    {
                        size = s.Read(data, 0, data.Length);
                        if (size > 0)
                        {
                            streamWriter.Write(data, 0, size);
                        }
                        else
                        {
                            break;
                        }
                    }

                    streamWriter.Close();
                }
            }
            s.Close();
        }

        /// <summary>
        /// 解压缩gz压缩格式
        /// </summary>
        /// <param name="args"></param>
        public void gunZip(string[] args)
        {
        }

        //遍历目录
        public void SearchDirectory(string str)
        {
            try
            {
                // 当前目录下的所有文件夹   
                string currentPath = str;
                string[] allFolder = System.IO.Directory.GetDirectories(currentPath);
                string[] allFile = System.IO.Directory.GetFiles(currentPath);

                foreach (string _folder in allFolder)
                {

                    SearchDirectory(_folder);

                }
                foreach (string file in allFile)
                {
                    //打开压缩文件
                    FileStream fs = File.OpenRead(file);

                    byte[] buffer = new byte[fs.Length];
                    fs.Read(buffer, 0, buffer.Length);

                    string filenametmp = file.Replace(fileprefixpath, "");

                    ZipEntry entry = new ZipEntry(filenametmp);

                    entry.DateTime = DateTime.Now;

                    // set Size and the crc, because the information
                    // about the size and crc should be stored in the header
                    // if it is not set it is automatically written in the footer.
                    // (in this case size == crc == -1 in the header)
                    // Some ZIP programs have problems with zip files that don't store
                    // the size and crc in the header.
                    entry.Size = fs.Length;
                    fs.Close();

                    crc.Reset();
                    crc.Update(buffer);

                    //entry.Crc = crc.Value;

                    s.PutNextEntry(entry);

                    s.Write(buffer, 0, buffer.Length);
                }
            }
            catch (Exception e)
            {
                MessageBox.Show("递归压缩文件时发生错误" + e.Message, "menuClick", MessageBoxButtons.OK);
            }
        }


        //遍历删除目录
        public void deleteDirectory(string str)
        {
            // 当前目录下的所有文件夹   
            string currentPath = str;
            string[] allFolder = System.IO.Directory.GetDirectories(currentPath);
            string[] allFile = System.IO.Directory.GetFiles(currentPath);

            foreach (string file in allFile)
            {
                File.Delete(file);
            }

            foreach (string _folder in allFolder)
            {

                deleteDirectory(_folder);

            }
        }


    }
}
