using System;
using System.Collections.Generic;
using System.Text;
using System.Globalization;
/**  
 * 说明：在东亚各国，除了通用的公元纪年之外，还有各自以前使用的阴历纪年法，在.net2.0种增加了针对东亚各国的日历类EastAsianLunisolarCalendar，  
 * 它是一个抽象类，有各种针对不同国家的的子类，其中ChineseLunisolarCalendar就是针对中国的日历类，它提公元纪年与中国传统农历纪年之间的相互转换  
 * 利用它可以计算天干地支等有关农历的信息，本程序就是来简单展示这个类的用法。它能计算的农历范围从公历1901－2-19至2101-1-28。  
 * 作者：周公  
 * 日期：2007-11-21  
 * 最后维护日期：2010-01-05  
 * 首发地址：http://blog.csdn.net/zhoufoxcn/archive/2007/11/21/1896258.aspx  
 */
namespace mms
{
    public class Calendar
    {
        private static ChineseLunisolarCalendar chineseDate = new ChineseLunisolarCalendar();
        static void Main(string[] args)
        {
            //ChineseLunisolarCalendar chineseDate = new ChineseLunisolarCalendar();   
            ShowYearInfo();
            ShowCurrentYearInfo();
            Console.ReadLine();
        }
        /// <summary>   
        /// 展示阴历年份信息   
        /// </summary>   
        public static void ShowYearInfo()
        {
            for (int i = chineseDate.MinSupportedDateTime.Year; i < chineseDate.MaxSupportedDateTime.Year; i++)
            {
                Console.WriteLine("年份：{0}，月份总数：{1}，总天数：{2}，干支序号：{3}", i, chineseDate.GetMonthsInYear(i), chineseDate.GetDaysInYear(i)
                                  , chineseDate.GetSexagenaryYear(new DateTime(i, 3, 1)));
            }
        }
        /// <summary>   
        /// 展示当前年份信息   
        /// </summary>   
        public static void ShowCurrentYearInfo()
        {
            int lYear = chineseDate.GetYear(DateTime.Now);
            int lMonth = chineseDate.GetMonth(DateTime.Now);
            int lDay = chineseDate.GetDayOfMonth(DateTime.Now);

            /** GetLeapMonth(int year)方法返回一个1到13之间的数字，  
             * 比如：1、该年阴历2月有闰月，则返回3  
             * 如果：2、该年阴历8月有闰月，则返回9  
             * GetMonth(DateTime dateTime)返回是当前月份，忽略是否闰月  
             * 比如：1、该年阴历2月有闰月，2月返回2，闰2月返回3  
             * 如果：2、该年阴历8月有闰月，8月返回8，闰8月返回9  
             */
            int leapMonth = chineseDate.GetLeapMonth(lYear);//获取第几个月是闰月,等于0表示本年无闰月   

            //如果今年有闰月   
            if (leapMonth > 0)
            {
                //闰月数等于当前月份   
                if (lMonth == leapMonth)
                {
                    Console.WriteLine("今年的阴历日期：{0}年闰{1}月{2}日。", lYear, lMonth - 1, lDay);
                }
                else if (lMonth > leapMonth)//   
                {
                    Console.WriteLine("今年的阴历日期：{0}年{1}月{2}日。", lYear, lMonth - 1, lDay);
                }
                else
                {
                    Console.WriteLine("今年的阴历日期：{0}年{1}月{2}日。", lYear, lMonth, lDay);
                }

            }
            else
            {
                Console.WriteLine("今年的阴历日期：{0}年{1}月{2}日。", lYear, lMonth, lDay);
            }
            Console.WriteLine("今天的公历日期：" + DateTime.Now.ToString("yyyy-MM-dd"));
            Console.WriteLine("今年阴历天数：{0}，今年{1}闰年", chineseDate.GetDaysInYear(DateTime.Now.Year), (chineseDate.IsLeapYear(DateTime.Now.Year) == true) ? "是" : "不是");

            Console.WriteLine("今年农历每月的天数：");//注意：如果有13个数字表示当年有闰月   
            for (int i = 1; i <= chineseDate.GetMonthsInYear(DateTime.Now.Year); i++)
            {
                Console.Write("{0,-5}", chineseDate.GetDaysInMonth(DateTime.Now.Year, i));
            }
        }
    }
}