using mms;
using Microsoft.VisualStudio.TestTools.UnitTesting;
namespace TestRS232
{
    
    
    /// <summary>
    ///这是 FileUtilsTest 的测试类，旨在
    ///包含所有 FileUtilsTest 单元测试
    ///</summary>
    [TestClass()]
    public class FileUtilsTest
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
        ///openFile 的测试
        ///</summary>
        [TestMethod()]
        public void openFileTest()
        {
            string filePath = string.Empty; // TODO: 初始化为适当的值
            FileUtils.openFile(filePath);
            Assert.Inconclusive("无法验证不返回值的方法。");
        }

        /// <summary>
        ///DirectoryName 的测试
        ///</summary>
        [TestMethod()]
        public void DirectoryNameTest()
        {
            string DirectoryPath = string.Empty; // TODO: 初始化为适当的值
            int expected = 0; // TODO: 初始化为适当的值
            int actual;
            actual = FileUtils.DirectoryName(DirectoryPath);
            Assert.AreEqual(expected, actual);
            Assert.Inconclusive("验证此测试方法的正确性。");
        }

        /// <summary>
        ///deleteDirectory 的测试
        ///</summary>
        [TestMethod()]
        public void deleteDirectoryTest()
        {
            string str = string.Empty; // TODO: 初始化为适当的值
            FileUtils.deleteDirectory(str);
            Assert.Inconclusive("无法验证不返回值的方法。");
        }

        /// <summary>
        ///CopyDirectory 的测试
        ///</summary>
        [TestMethod()]
        public void CopyDirectoryTest()
        {
            string DirectoryPath = string.Empty; // TODO: 初始化为适当的值
            string DirAddress = string.Empty; // TODO: 初始化为适当的值
            bool DirFirst = false; // TODO: 初始化为适当的值
            FileUtils.CopyDirectory(DirectoryPath, DirAddress, DirFirst);
            Assert.Inconclusive("无法验证不返回值的方法。");
        }
    }
}
