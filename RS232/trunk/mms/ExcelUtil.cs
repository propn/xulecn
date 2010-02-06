using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.OleDb;

using Excel = Microsoft.Office.Interop.Excel;

using mms.Properties;

namespace mms
{
    class ExcelUtil
    {


        public static void impExcel(string appPath)
        {
            string strDeleteAccess = "";
            string strGetDataFromExcel = "";
            string strInsertIntoAccess = "";
            OleDbConnection oleDbConnAccess;
            OleDbConnection oleDbConnExcel;
            OleDbCommand oleDbCmdAccess;
            OleDbCommand oleDbCmdExcel;
            OleDbDataReader oleDbDataReaderExcel;

            oleDbConnAccess = new OleDbConnection(Settings.Default.dbConnectionString);
            oleDbConnAccess.Open();
            strDeleteAccess = "delete from MEETINGPERSON ";
            oleDbCmdAccess = new OleDbCommand(strDeleteAccess, oleDbConnAccess);
            oleDbCmdAccess.ExecuteNonQuery();
            oleDbCmdAccess.Dispose();
            oleDbConnAccess.Close();

            oleDbConnExcel = new OleDbConnection("Provider=Microsoft.Jet.OLEDB.4.0;Jet OLEDB:Database Password=;Extended properties=Excel 5.0;Data Source="
                + appPath + "\\files\\data\\persons.xls");
            oleDbConnExcel.Open();

            strGetDataFromExcel = "SELECT * FROM [Sheet1$]";

            oleDbCmdExcel = new OleDbCommand(strGetDataFromExcel, oleDbConnExcel);
            oleDbDataReaderExcel = oleDbCmdExcel.ExecuteReader();

            if (oleDbDataReaderExcel.HasRows == true)
            {
                oleDbConnAccess.Open();
                for (; ; )
                {
                    if (oleDbDataReaderExcel.Read() == true)
                    {

                        strInsertIntoAccess = "insert into MEETINGPERSON(PERSONID, PERSONNAME, NOTIFID, TELEPHONE, DEPTNAME, ADDRESSID,MEETINGID, MEETINGNAME, [TABLEID], SEATINGNO, QRID, EXT1, EXT2, EXT3, EXT4,EXT5) values('"
                            + oleDbDataReaderExcel.GetValue(0).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(1).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(2).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(3).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(4).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(5).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(6).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(7).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(8).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(9).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(10).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(11).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(12).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(13).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(14).ToString()
                            + "','" + oleDbDataReaderExcel.GetValue(15).ToString()

                            + "') ";

                        oleDbCmdAccess = new OleDbCommand(strInsertIntoAccess, oleDbConnAccess);
                        oleDbCmdAccess.ExecuteNonQuery();
                        oleDbCmdAccess.Dispose();
                    }
                    else
                        break;
                }
                oleDbConnAccess.Close();
            }
            oleDbDataReaderExcel.Close();
            oleDbCmdExcel.Dispose();
            oleDbConnExcel.Close();
        }

        /// <summary>
        /// 更新数据到excel文件中
        /// </summary>
        /// <param name="appPath"></param>
        public static void insertExcel(string appPath)
        {
            //createExcel();
            string strInsertIntoExcel = "";
            string strGetDataFromAccess = "";

            OleDbConnection oleDbConnAccess;
            OleDbCommand oleDbCmdAccess;

            OleDbConnection oleDbConnExcel;
            OleDbCommand oleDbCmdExcel;
            oleDbConnExcel = new OleDbConnection("Provider=Microsoft.Jet.OLEDB.4.0;Jet OLEDB:Database Password=;Extended properties=Excel 5.0;Data Source="
               + appPath + "\\files\\data\\persons.xls");
            OleDbDataReader oleDbDataReaderAccess;
            oleDbConnAccess = new OleDbConnection(Settings.Default.dbConnectionString);
            oleDbConnAccess.Open();

            strGetDataFromAccess = "SELECT * FROM MEETINGPERSON";

            oleDbCmdAccess = new OleDbCommand(strGetDataFromAccess, oleDbConnAccess);
            oleDbDataReaderAccess = oleDbCmdAccess.ExecuteReader();

            if (oleDbDataReaderAccess.HasRows == true)
            {
                oleDbConnExcel.Open();

                for (; ; )
                {
                    if (oleDbDataReaderAccess.Read() == true)
                    {
                        string personId = oleDbDataReaderAccess.GetValue(0).ToString();
                        string tableId = oleDbDataReaderAccess.GetValue(9).ToString();


                        strInsertIntoExcel = "insert into  [Sheet1$](PERSONID, PERSONNAME, NOTIFID, TELEPHONE, DEPTNAME, ADDRESSID,MEETINGID, MEETINGNAME, [TABLEID], SEATINGNO, QRID, EXT1, EXT2, EXT3, EXT4,EXT5) values('"
                       + oleDbDataReaderAccess.GetValue(0).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(1).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(2).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(3).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(4).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(5).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(6).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(7).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(8).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(9).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(10).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(11).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(12).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(13).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(14).ToString()
                       + "','" + oleDbDataReaderAccess.GetValue(15).ToString()

                       + "') ";


                        oleDbCmdExcel = new OleDbCommand(strInsertIntoExcel, oleDbConnExcel);
                        oleDbCmdExcel.ExecuteNonQuery();
                        oleDbCmdExcel.Dispose();

                    }
                    else
                    {
                        break;
                    }
                }
                oleDbConnExcel.Close();
            }
            oleDbDataReaderAccess.Close();
            oleDbCmdAccess.Dispose();
            oleDbConnAccess.Close();

        }

        public static void expExcel(string appPath)
        {
            string folder = appPath + "\\files\\data\\";

            string directoryCreator = Path.GetDirectoryName(folder);

            if (!System.IO.Directory.Exists(directoryCreator))
            {
                System.IO.Directory.CreateDirectory(directoryCreator);
            }


            string filePath = appPath + "\\files\\data\\persons.xls";


           // File.Move(filePath, appPath + "\\files\\data\\persons.xls.old");
            if (File.Exists(filePath))
            {
                File.Delete(filePath);
            }
            
            createExcel(filePath);
            insertExcel(appPath);
        }

        public static void createExcel(string filePath)
        {
            Excel.Application xlApp;
            Excel.Workbook xlWorkBook;
            Excel.Worksheet xlWorkSheet;
            object misValue = System.Reflection.Missing.Value;

            xlApp = new Excel.ApplicationClass();
            xlWorkBook = xlApp.Workbooks.Add(misValue);

            xlWorkSheet = (Excel.Worksheet)xlWorkBook.Worksheets.get_Item(1);

            //xlWorkSheet.Cells[1, 1] = "http://csharp.net-informations.com";

            xlWorkSheet.Cells[1, 1] = "PERSONID";
            xlWorkSheet.Cells[1, 2] = "PERSONNAME";
            xlWorkSheet.Cells[1, 3] = "NOTIFID";
            xlWorkSheet.Cells[1, 4] = "TELEPHONE";
            xlWorkSheet.Cells[1, 5] = "DEPTNAME";
            xlWorkSheet.Cells[1, 6] = "ADDRESSID";
            xlWorkSheet.Cells[1, 7] = "MEETINGID";
            xlWorkSheet.Cells[1, 8] = "MEETINGNAME";
            xlWorkSheet.Cells[1, 9] = "TABLEID";
            xlWorkSheet.Cells[1, 10] = "SEATINGNO";
            xlWorkSheet.Cells[1, 11] = "QRID";
            xlWorkSheet.Cells[1, 12] = "EXT1";
            xlWorkSheet.Cells[1, 13] = "EXT2";
            xlWorkSheet.Cells[1, 14] = "EXT3";
            xlWorkSheet.Cells[1, 15] = "EXT4";
            xlWorkSheet.Cells[1, 16] = "EXT5";

            xlWorkBook.SaveAs(filePath, Excel.XlFileFormat.xlWorkbookNormal, misValue, misValue, misValue, misValue, Excel.XlSaveAsAccessMode.xlExclusive, misValue, misValue, misValue, misValue, misValue);
            xlWorkBook.Close(true, misValue, misValue);
            xlApp.Quit();

            releaseObject(xlWorkSheet);
            releaseObject(xlWorkBook);
            releaseObject(xlApp);
        }

        private static void releaseObject(object obj)
        {
            try
            {
                System.Runtime.InteropServices.Marshal.ReleaseComObject(obj);
                obj = null;
            }
            catch (Exception ex)
            {
                obj = null;
                throw ex;
            }
            finally
            {
                GC.Collect();
            }
        }


    }






}
