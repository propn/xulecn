using System;
using System.Collections;
using System.Text;
using System.Xml;

namespace mms
{
    /// <summary>
    /// <?xml version="1.0" encoding="UTF-8"?>
    ///<books>
    /// <book>
    ///  <name>哈里波特</name>
    ///  <price>10</price>
    ///  <memo>这是一本很好看的书。</memo>
    /// </book>
    /// <book id="B02">
    ///  <name>三国演义</name>
    ///  <price>10</price>
    ///  <memo>四大名著之一。</memo>
    /// </book>
    /// <book id="B03">
    ///  <name>水浒</name>
    ///  <price>6</price>
    ///  <memo>四大名著之一。</memo>
    /// </book>
    /// <book id="B04">
    ///  <name>红楼</name>
    ///  <price>5</price>
    ///  <memo>四大名著之一。</memo>
    /// </book>
    ///</books>  
    /// </summary>
    class XmlUtils
    {
        static void Main2(string[] args)
        {
            XmlElement theBook = null, theElem = null, root = null;
            XmlDocument xmldoc = new XmlDocument();
            try
            {
                xmldoc.Load("Books.xml");
                root = xmldoc.DocumentElement;
                //---  新建一本书开始 ----
                theBook = xmldoc.CreateElement("book");
                theElem = xmldoc.CreateElement("name");
                theElem.InnerText = "新书";
                theBook.AppendChild(theElem);

                theElem = xmldoc.CreateElement("price");
                theElem.InnerText = "20";
                theBook.AppendChild(theElem);

                theElem = xmldoc.CreateElement("memo");
                theElem.InnerText = "新书更好看。";
                theBook.AppendChild(theElem);
                root.AppendChild(theBook);
                Console.Out.WriteLine("---  新建一本书开始 ----");
                Console.Out.WriteLine(root.OuterXml);
                //---  新建一本书完成 ----

                //---  下面对《哈里波特》做一些修改。 ----
                //---  查询找《哈里波特》----
                theBook = (XmlElement)root.SelectSingleNode("/books/book[name='哈里波特']");
                Console.Out.WriteLine("---  查找《哈里波特》 ----");
                Console.Out.WriteLine(theBook.OuterXml);
                //---  此时修改这本书的价格 -----
                theBook.GetElementsByTagName("price").Item(0).InnerText = "15";//getElementsByTagName返回的是NodeList，所以要跟上item(0)。另外，GetElementsByTagName("price")相当于SelectNodes(".//price")。
                Console.Out.WriteLine("---  此时修改这本书的价格 ----");
                Console.Out.WriteLine(theBook.OuterXml);
                //---  另外还想加一个属性id，值为B01 ----
                theBook.SetAttribute("id", "B01");
                Console.Out.WriteLine("---  另外还想加一个属性id，值为B01 ----");
                Console.Out.WriteLine(theBook.OuterXml);
                //---  对《哈里波特》修改完成。 ----

                //---  再将所有价格低于10的书删除  ----
                theBook = (XmlElement)root.SelectSingleNode("/books/book[@id='B02']");
                Console.Out.WriteLine("---  要用id属性删除《三国演义》这本书 ----");
                Console.Out.WriteLine(theBook.OuterXml);
                theBook.ParentNode.RemoveChild(theBook);
                Console.Out.WriteLine("---  删除后的ＸＭＬ ----");
                Console.Out.WriteLine(xmldoc.OuterXml);

                //---  再将所有价格低于10的书删除  ----
                XmlNodeList someBooks = root.SelectNodes("/books/book[price<10]");
                Console.Out.WriteLine("---  再将所有价格低于10的书删除  ---");
                Console.Out.WriteLine("---  符合条件的书有　" + someBooks.Count + "本。  ---");

                for (int i = 0; i < someBooks.Count; i++)
                {
                    someBooks.Item(i).ParentNode.RemoveChild(someBooks.Item(i));
                }
                Console.Out.WriteLine("---  删除后的ＸＭＬ ----");
                Console.Out.WriteLine(xmldoc.OuterXml);

                xmldoc.Save("books.xml");//保存到books.xml

                Console.In.Read();
            }
            catch (Exception e)
            {
                Console.Out.WriteLine(e.Message);
            }
        }

        public static Hashtable dealAddtask(string sxml)
        {
            XmlDocument xmldoc = new XmlDocument();
            xmldoc.LoadXml(sxml);

            XmlElement root = null;

            root = xmldoc.DocumentElement;

            XmlElement Code = (XmlElement)root.SelectSingleNode("/Response/Code");

            string taskId = "";

            if (Code.Equals("0000"))
            {
                XmlElement TaskID = (XmlElement)root.SelectSingleNode("/Response/TaskList/Task/TaskID");
                taskId = TaskID.InnerText;
            }
            return null;
            
        }

        public static Hashtable dealAddcontant(string sxml)
        {
            return null;
        }


        public static Hashtable dealAddwhitelist(string sxml)
        {
            return null;
        }

        public static Hashtable dealGetTaskList(string sxml)
        {
            return null;
        }
    }
}
