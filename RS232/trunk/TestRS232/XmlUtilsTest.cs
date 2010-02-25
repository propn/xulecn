using mms;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections;

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
    }
}
