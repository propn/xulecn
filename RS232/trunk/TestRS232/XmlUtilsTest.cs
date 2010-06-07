using mms;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections;
using System.Xml;
using System;

namespace TestRS232
{
    
    
    /// <summary>
    ///这是 XmlUtilsTest 的测试类，旨在
    ///包含所有 XmlUtilsTest 单元测试
    ///</summary>
    [TestClass()]
    public class XmlUtilsTest
    {


        private TestContext testContextInstance;

        /// <summary>
        ///获取或设置测试上下文，上下文提供
        ///有关当前测试运行及其功能的信息。
        ///</summary>
        public TestContext TestContext
        {
            get
            {
                return testContextInstance;
            }
            set
            {
                testContextInstance = value;
            }
        }

        #region 附加测试属性
        // 
        //编写测试时，还可使用以下属性:
        //
        //使用 ClassInitialize 在运行类中的第一个测试前先运行代码
        //[ClassInitialize()]
        //public static void MyClassInitialize(TestContext testContext)
        //{
        //}
        //
        //使用 ClassCleanup 在运行完类中的所有测试后再运行代码
        //[ClassCleanup()]
        //public static void MyClassCleanup()
        //{
        //}
        //
        //使用 TestInitialize 在运行每个测试前先运行代码
        //[TestInitialize()]
        //public void MyTestInitialize()
        //{
        //}
        //
        //使用 TestCleanup 在运行完每个测试后运行代码
        //[TestCleanup()]
        //public void MyTestCleanup()
        //{
        //}
        //
        #endregion


        /// <summary>
        ///dealGetTaskList 的测试
        ///</summary>
        [TestMethod()]
        public void dealGetTaskListTest()
        {
            string sxml = string.Empty; // TODO: 初始化为适当的值
            Hashtable expected = null; // TODO: 初始化为适当的值
            Hashtable actual;
            actual = XmlUtils.dealGetTaskList(sxml);
            Assert.AreEqual(expected, actual);
            Assert.Inconclusive("验证此测试方法的正确性。");
        }

        /// <summary>
        ///XmlUtils 构造函数 的测试
        ///</summary>
        [TestMethod()]
        public void XmlUtilsConstructorTest()
        {
            XmlUtils target = new XmlUtils();
            Assert.Inconclusive("TODO: 实现用来验证目标的代码");
        }

        /// <summary>
        ///dealAddcontant 的测试
        ///</summary>
        [TestMethod()]
        public void dealAddcontantTest()
        {
            string sxml = string.Empty; // TODO: 初始化为适当的值
            Hashtable expected = null; // TODO: 初始化为适当的值
            Hashtable actual;
            actual = XmlUtils.dealAddcontant(sxml);
            Assert.AreEqual(expected, actual);
            Assert.Inconclusive("验证此测试方法的正确性。");
        }

        /// <summary>
        ///dealAddtask 的测试
        ///</summary>
        [TestMethod()]
        public void dealAddtaskTest()
        {
            string sxml = "<?xml version='1.0' encoding='UTF-8'?><Response><Code>0000</Code><ResponseMessage>添加任务单成功</ResponseMessage><TaskList><Task><TaskID>3642</TaskID></Task></TaskList></Response>"; // TODO: 初始化为适当的值
            Hashtable expected = null; // TODO: 初始化为适当的值
            Hashtable actual;
            actual = XmlUtils.dealAddtask(sxml);
            Assert.AreEqual(expected, actual);
            Assert.Inconclusive("验证此测试方法的正确性。");
        }

        /// <summary>
        ///dealAddwhitelist 的测试
        ///</summary>
        [TestMethod()]
        public void dealAddwhitelistTest()
        {
            string sxml = string.Empty; // TODO: 初始化为适当的值
            Hashtable expected = null; // TODO: 初始化为适当的值
            Hashtable actual;
            actual = XmlUtils.dealAddwhitelist(sxml);
            Assert.AreEqual(expected, actual);
            Assert.Inconclusive("验证此测试方法的正确性。");
        }


        /// <summary>
        ///dealStatus 的测试
        ///</summary>
        [TestMethod()]
        public void dealStatus()
        {
            string sxml = "<?xml version='1.0' encoding='utf-8'?><StatusList><Member><MmsId>5</MmsId><Mobile>13500000000</Mobile><Result>Retrieved</Result></Member><Member><MmsId>5</MmsId><Mobile>13600000000</Mobile><Result>Message is too large</Result></Member></StatusList>";
            XmlElement theBook = null, theElem = null, root = null;
            XmlDocument xmldoc = new XmlDocument();
            try
            {
                xmldoc.Load(sxml);
                root = xmldoc.DocumentElement;

                //---  再将所有价格低于10的书删除  ----
                XmlNodeList someBooks = root.SelectNodes("/StatusList/Member[Result=Retrieved]");

                Console.Out.WriteLine("---  符合条件的书有　" + someBooks.Count + "本。  ---");

                for (int i = 0; i < someBooks.Count; i++)
                {
                    //someBooks.Item(i).ParentNode.RemoveChild(someBooks.Item(i));
                    Console.Out.WriteLine(someBooks.Item(i).OuterXml);
                }
                Console.Out.WriteLine("---  删除后的ＸＭＬ ----");
                Console.Out.WriteLine(xmldoc.OuterXml);



            }
            catch(Exception ex){
            }
        }

    }
}
