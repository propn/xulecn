using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CookComputing.XmlRpc;
using System.Collections;


namespace mms
{

    /// <summary>
    /// rpc调用
    /// </summary>
    public interface IMath : IXmlRpcProxy
    {
        [XmlRpcMethod("math.Add")]
        int Add(int a, int b);

        [XmlRpcMethod("math.Subtract")]
        int Subtract(int a, int b);

        [XmlRpcMethod("math.Multiply")]
        int Multiply(int a, int b);

        [XmlRpcMethod("math.Divide")]
        int Divide(int a, int b);
    }

    public interface IMsg : IXmlRpcProxy
    {
        /// <summary>
        /// 
        /// </summary>
        /// <param name="map">
        /// string Customer_id,集团客户编号，企业彩讯提供
        /// string Corp_Account,集团代码企业彩讯提供
        /// int START, 开始记录条数,方便分页
        /// int END 结束记录条数,方便分页
        /// </param>
        /// <returns>
        /*     <?xml version=”1.0” encoding=”UTF-8”?>
                <Response>
		                <Code>0000</Code>
		                <ResponseMessage>成功</ResponseMessage>
	                  <TaskList total=”任务单总记录数”>
		                <Task>
			                <TaskID>任务单ID</TaskID>
			                <Title>任务单标题</Title>
			                <Submit_Time>任务单提交时间</Submit_Time >
			                <Send_Time>实际下发时间</Send_Time>
			                <Status>状态</Status>
			                <Sum_Send>成功下发数</Sum_send>
			                <Succ_Send>成功接受数</Succ_send>
		                </Task>

	                  </TaskList>
                </Response>
         * */
        /// </returns>
        [XmlRpcMethod("GetTaskList")]
        string GetTaskList(Hashtable map);


        /// <summary>
        /// 
        /// </summary>
        /// <param name="map">
        /// Customer_id,集团客户编号，企业彩讯提供
        /// Corp_Account,集团代码企业彩讯提供
        /// Agreement_id,任务单ID
        /// Type,短/彩信标识        1—彩信  2—短信
        /// SendTime,定时下发,格式yyyy-MM-dd hh24:mm:ss
        /// SmsContent,短信内容(文本) 短信任务单时必须
        /// 
        /// </param>
        /// <returns>
        /*
                <?xml version="1.0" encoding="UTF-8"?>
                <Response>
                <Code>0000</Code>
                <ResponseMessage>成功</ResponseMessage>
                <TaskList>
                    <Task>
                            <TaskID>任务单ID</TaskID>
                    </Task>
                </TaskList>
                </Response >
        **/
        /// </returns>
        [XmlRpcMethod("AddTask")]
        string AddTask(Hashtable map);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="map">
        /// Customer_id,集团客户编号，企业彩讯提供
        /// Corp_Account,集团代码企业彩讯提供
        /// TaskID,任务单ID
        /// MmsType,彩信类型  1-通用彩信 2-适配彩信  (只有为彩信任务单才有用)
        /// SmsContent,短信内容(文本) 短信任务单时必须
        /// Mms,彩信内容(二进制流) t彩信任务单时必须,如果是适配彩信，此处存放中图。
        /// Mms_xt,彩信内容(二进制流) 彩信任务单时并且MmsType为2时必须，此处存放小图
        /// Mms_dt,彩信内容(二进制流) 彩信任务单时并且MmsType为2时必须，此处存放大图
        /// Mms_cd,彩信内容(二进制流) 彩信任务单时并且MmsType为2时必须，此处存放超大图
        /// Mms_zd 彩信内容(二进制流) 彩信任务单时并且MmsType为2时必须，此处存放最大图
        /// 
        /// </param>
        /// <returns>
        /*     <?xml version="1.0" encoding="UTF-8"?>
                <Response>
	            <Code>0000</Code>
	            <ResponseMessage>成功</ResponseMessage>
                </Response >
        **/
        /// </returns>
        [XmlRpcMethod("AddContent")]
        string AddContent(Hashtable map);


        /// <summary>
        /// 
        /// 
        /// </summary>
        /// <param name="map">
        /// Customer_id集团客户编号，企业彩讯提供
        /// Corp_Account集团代码企业彩讯提供
        /// TaskID任务单ID
        /// Mobile白名单号码使用,号间隔
        /// </param>
        /// <returns>
        /*    <?xml version="1.0" encoding="UTF-8"?>
              <Response>
                 <Code>0000</Code>
                 <ResponseMessage>成功</ResponseMessage>
              </Response >
        **/
        /// </returns>
        [XmlRpcMethod("AddWhiteList")]
        string AddWhiteList(Hashtable map);

    }


}
