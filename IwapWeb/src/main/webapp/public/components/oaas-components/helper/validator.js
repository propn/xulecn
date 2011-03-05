
//函数名：string_ltrim(str)
//功能介绍：去掉用户名中的左边最前的空格
//参数说明：要检查的用户名
//返回值：去掉空格的用户名
function  string_ltrim(str)
{
	var newstr1,newstr2;
	newstr1=new String("");
	newstr2=new String("");
	for (i=0;i<str.length;i++)
	{
		newstr2=str.substr(i,1);

		if(newstr2==' ')
		{
			if(newstr1.length>0)
				{
					newstr1=newstr1+newstr2;
				}
		}
		else
			 newstr1=newstr1+newstr2;
	}
	return newstr1;
}
//函数名：string_rtrim(str)
//功能介绍：去掉用户名中的右边最后的空格
//参数说明：要检查的用户名
//返回值：去掉空格的用户名
function string_rtrim(str)
{
	var newstr1,newstr2;
	newstr1=new String(str);
	for (i=str.length-1;i>0;i--)
	{
		newstr2=str.charAt(i);
		if(newstr2!=' ')
		{
			newstr1=str.substr(0,i+1);
			break;
		}
	}
	return newstr1;
}

//函数名：space(str)
//功能介绍：去掉用户名中的空格
//参数说明：要检查的用户名
//返回值：去掉空格的用户名
function space(str)
{
	var newstr1,newstr2;
	newstr1=new String("");
	newstr2=new String("");
	for (i=0;i<str.length;i++)
	{
		newstr2=str.substr(i,1);
		if (newstr2!=' ')
		{

			newstr1=newstr1+newstr2;
		}
	}
	return newstr1;

}





//函数名：fucCheckTEL
//功能介绍：检查是否为电话号码
//参数说明：要检查的字符串
//返回值：1为是合法，0为不合法
function fucCheckTEL(TEL)
{
	var i,j,strTemp;
	strTemp="0123456789-()# ";
	for (i=0;i<TEL.length;i++)
	{
		j=strTemp.indexOf(TEL.charAt(i));
		if (j==-1)
		{
		//说明有字符不合法
			alert("电话号码填写不正确！")
			return false;
		}
	}
	//说明合法
	return true;
}

//请输入登录名：只能由英文字母(a-z)或(A-Z),数字(0-9),下划线(_)组成。登录名必需以字母开头，大小写没有区别
function checkname(name)
{
        var i, n
        if (!isletter(name.substr(0, 1)))
        {
                alert('请输入正确的登录名，必需以字母开头。')
                return false
        }
        for (i=0; i<name.length; i++)
        {
                n = name.charAt(i)
                if (!(isletter(n) || IsDigit(n) || (n=='_')))
                {
                        alert('请输入正确的登录名，只能由英文字母(a-z)或(A-Z), 数字(0-9)和下划线(_)组成。');
                        return false;
                }
        }
        if (n=='_')
        {
                alert('请输入正确的登录名，必须以字母或数字结尾')
                return false
        }
        return true;
}
function IsDigit(cCheck)
	{
	return (('0'<=cCheck) && (cCheck<='9'));
	}

//密码(至少6位)：
function checkpass(pass)
{
        var i
        if (pass.length<6){alert('请输入正确的密码，密码需至少6位');return false;}
        for (i=0; i<pass.length; i++)
        {
                if (pass.substr(i, 1) == ' ')
                {
                        alert('请输入正确的密码，密码不能含有空格');
                        return false;
                }
        }
        return true;
}
//再输入密码进行确认
function checkpass2(pass1,pass2)
{
  var sPasswd = pass1;
  var sPasswd1 = pass2;
  if (sPasswd != sPasswd1) {alert('两次输入的密码不相同');return;}
}


//两种校验日期：1）年月日分开填入 2）年月日作为一个string填入“2001－12－21”

//函数名：validateDay
//功能介绍：检查是否为日期
//参数说明：yearStr, monthStr, dayStr
//返回值：true,false
function validateDay(yearStr, monthStr, dayStr)
{
        var yearInt = parseInt(yearStr);
        var monthInt = parseInt(monthStr) - 1;
        var dayInt = parseInt(dayStr);
        if (monthInt > 11)
        {
                return false;
        }
        if (yearInt < 1900)
        {
                return false;
        }
        monthDays = new MakeArray(12)
        monthDays [0] = 31;
        monthDays [1] = 28;
        monthDays [2] = 31;
        monthDays [3] = 30;
        monthDays [4] = 31;
        monthDays [5] = 30;
        monthDays [6] = 31;
        monthDays [7] = 31;
        monthDays [8] = 30;
        monthDays [9] = 31;
        monthDays [10] = 30;
        monthDays [11] = 31;

        if (yearInt % 100 == 0)
        {
          if (yearInt % 400 == 0)
          {
            monthDays[1] = 29;
          }
        }
        else
        {
          if (yearInt % 4 == 0)
          {
            monthDays[1] = 29;
          }
        }

        if (dayInt > monthDays[monthInt])
        {
          return false;
        }
        return true;
}


//函数名：chkdate
//功能介绍：检查是否为日期
//参数说明：要检查的字符串datestr,格式如“2001－12－21”
//返回值：0：不是日期  1：是日期
function chkdate(datestr)
{
	var lthdatestr
	if (datestr != "")
		lthdatestr= datestr.length ;
	else
		lthdatestr=0;

	var tmpy="";
	var tmpm="";
	var tmpd="";
	//var datestr;
	var status;
	status=0;
	if ( lthdatestr== 0)
		return false

	for (i=0;i<lthdatestr;i++)
	{
		if ((datestr.charAt(i)<'0'||datestr.charAt(i)>'9')&&datestr.charAt(i)!='-')
		{
			return 0
		}
	}

	for (i=0;i<lthdatestr;i++)
	{	if (datestr.charAt(i)== '-')
		{
			status++;
		}
		if (status>2)
		{
			//alert("Invalid format of date!");
			return 0;
		}
		if ((status==0) && (datestr.charAt(i)!='-'))
		{
			tmpy=tmpy+datestr.charAt(i)
		}
		if ((status==1) && (datestr.charAt(i)!='-'))
		{
			tmpm=tmpm+datestr.charAt(i)
		}
		if ((status==2) && (datestr.charAt(i)!='-'))
		{
			tmpd=tmpd+datestr.charAt(i)
		}

	}
	year=new String (tmpy);
	month=new String (tmpm);
	day=new String (tmpd)
	//tempdate= new String (year+month+day);
	//alert(tempdate);
	if ((tmpy.length!=4) || (tmpm.length>2) || (tmpd.length>2))
	{
		//alert("Invalid format of date!");
		return 0;
	}
	if (!((1<=month) && (12>=month) && (31>=day) && (1<=day)) )
	{
		//alert ("Invalid month or day!");
		return 0;
	}
	if (!((year % 4)==0) && (month==2) && (day==29))
	{
		//alert ("This is not a leap year!");
		return 0;
	}
	if ((month<=7) && ((month % 2)==0) && (day>=31))
	{
		//alert ("This month is a small month!");
		return 0;

	}
	if ((month>=8) && ((month % 2)==1) && (day>=31))
	{
		//alert ("This month is a small month!");
		return 0;
	}
	if ((month==2) && (day==30))
	{
		//alert("The Febryary never has this day!");
		return 0;
	}

	return 1;
}

//函数名：checkTime
//功能介绍：检查是否为时间
//参数说明：yearStr, monthStr, dayStr,hourStr,minStr
//返回值：true,false
function checkTime(yearStr, monthStr, dayStr,hourStr,minStr)
{
        var yearInt = parseInt(yearStr);
        var monthInt = parseInt(monthStr) - 1;
        var dayInt = parseInt(dayStr);
    		var hourInt = parseInt(hourStr);
    		var minInt = parseInt(minStr);

        if (monthInt > 11)
        {
                return false;
        }
        if (yearInt < 1900)
        {
                return false;
        }
        monthDays = new Array(12)
        monthDays [0] = 31;
        monthDays [1] = 28;
        monthDays [2] = 31;
        monthDays [3] = 30;
        monthDays [4] = 31;
        monthDays [5] = 30;
        monthDays [6] = 31;
        monthDays [7] = 31;
        monthDays [8] = 30;
        monthDays [9] = 31;
        monthDays [10] = 30;
        monthDays [11] = 31;

        if (yearInt % 100 == 0)
        {
          if (yearInt % 400 == 0)
          {
            monthDays[1] = 29;
          }
        }
        else
        {
          if (yearInt % 4 == 0)
          {
            monthDays[1] = 29;
          }
        }

        if (dayInt > monthDays[monthInt])
        {
          return false;
        }
			
    		if (isNaN(hourInt) ||  hourInt > 23 || hourInt < 0)
    		{
    			return false;
    		}
    		if (isNaN(minInt)  || minInt > 59 || minInt < 0)
    		{
    			return false;
    		}
        return true;
}

//函数名：compareDate
//功能介绍：比较日期
//参数说明：要比较的日期date1,date2
//返回值：0：date1比date2小； 1：date1比date2大;2:date1=date2

function compareDate(date1,date2)
{

    /**
     * modify by mjj,20020820
     */
	//chkdate(date1)
	//chkdate(date2)
    date1 = convertdate(date1);
	date2 = convertdate(date2);

	if(date1<date2)
	{
		//alert("date1<date2")
		return 0
	}
	if(date1>date2)
	{
		//alert("date1>date2")
		return 1
	}
	else
	{
		//alert("date1=date2")
		return 2
	}
}

//函数名：compareDatesLength
//功能介绍：比较日期
//参数说明：要比较的日期date1,date2
//返回值：0：date1与date2相差不超过一个月； 1：date1与date2相差超过一个月
function compareDatesLength(date1,date2)
{
	rq1 = convertdate(date1);
	rq2 = convertdate(date2);
	if (Number(rq1.substring(0,4)) + 1 < Number(rq2.substring(0,4)) ){
    		return 1;
	}
      	if (Number(rq1.substring(0,4)) + 1 == Number(rq2.substring(0,4)) ){
      	           if ( (Number(rq2.substring(5,7)) +12 - Number(rq1.substring(5,7))) <1 )
		   {
      			 return 0 ;
    		   }
                   if ( (Number(rq2.substring(5,7)) +12 - Number(rq1.substring(5,7))) == 1 )
		   {
      			 if( Number(rq2.substring(8,10)) <= Number(rq1.substring(8,10)) )
			 {
      			      return 0 ;
			  }
    		   }
      	}
	if (Number(rq1.substring(0,4)) == Number(rq2.substring(0,4)) ){
      		if (Number(rq2.substring(5,7)) == Number(rq1.substring(5,7))){
		       return 0 ;
    		}
		if (Number(rq2.substring(5,7)) == Number(rq1.substring(5,7)) + 1 ){
		     if( Number(rq2.substring(8,10)) <= Number(rq1.substring(8,10))){
		              return 0 ;
		     }
    		}
	}
    	return 1;
}

//函数名：chkemail
//功能介绍：检查是否为Email Address
//参数说明：要检查的字符串
//返回值：0：不是  1：是
function chkemail(a)
{
	var i=a.length;
	if(i==0) return 1;

	var temp = a.indexOf('@');
	var tempd = a.indexOf('.');

	if ((chkspc(a)==0))
	{	alert ("请填写正确的e-mail地址!");
		return 0;
	}



	if (temp > 0) {
		if ((i-temp) > 3){
			if(tempd>0){
				if (((tempd-temp)>1) && ((i-tempd)>1)){
					return 1;
				}
				else{
					alert("检查Email地址是否填写正确");
					return 0;
				}
			}
			else{
				alert("检查Email地址是否填写正确");
				return 0;
			}
		}
		else{
			alert("检查Email地址是否填写正确");
			return 0;
		}
	}
	else
	{
		alert("检查Email地址是否填写正确");
		return 0;
	}
}

//检查身份证号码：checkID
function checkID(a)
{
	var i,j,strTemp;
	strTemp="0123456789";
	if ( a.length!= 15 || a.length!=18 )
	{
		alert("你填写的身份证号码不是15位或18位的！")
		return 0
	}
	for (i=0;i<a.length;i++)
	{
		j=strTemp.indexOf(a.charAt(i));
		if (j==-1)
		{
		//说明有字符不是数字
			alert("你填写的身份证号码不正确！有字符")
			return 0;
		}
	}
	//说明是数字
	return 1;
}

//检查邮政编码：checkPostalcode()
function checkPostalcode(a)
{
	var i,j,strTemp;
	strTemp="0123456789";
	if ( a.length!= 6 )
	{
		alert("你填写的邮政编码不是6位的！")
		return 0
	}
	for (i=0;i<a.length;i++)
	{
		j=strTemp.indexOf(a.charAt(i));
		if (j==-1)
		{
		//说明有字符不是数字
			alert("你填写的邮政编码不正确！有字符")
			return 0;
		}
	}
	//说明是数字
	return 1;
}


//函数名：chksafe
//功能介绍：检查是否含有"'",'\\',"/"
//参数说明：要检查的字符串
//返回值：0：是  1：不是
function chksafe(a)
{
	return 1;
/*	fibdn = new Array ("'" ,"\\", "、", ",", ";", "/");
	i=fibdn.length;
	j=a.length;
	for (ii=0;ii<i;ii++)
	{	for (jj=0;jj<j;jj++)
		{	temp1=a.charAt(jj);
			temp2=fibdn[ii];
			if (tem';p1==temp2)
			{	return 0; }
		}
	}
	return 1;
*/
}

//函数名：chkspc
//功能介绍：检查是否含有空格
//参数说明：要检查的字符串
//返回值：0：是  1：不是
function chkspc(a)
{
	var i=a.length;
	var j = 0;
	var k = 0;
	while (k<i)
	{
		if (a.charAt(k) != " ")
			j = j+1;
		k = k+1;
	}
	if (j==0)
	{
		return 0;
	}

	if (i!=j)
	{ return 2; }
	else
	{
		return 1;
	}
}


//opt1 小数     opt2   负数
//当opt2为1时检查num是否是负数
//当opt1为1时检查num是否是小数
//返回1是正确的，0是错误的
function chknbr(num,opt1,opt2)
{
	var i=num.length;
	var staus;
//staus用于记录.的个数
	status=0;
	if ((opt2!=1) && (num.charAt(0)=='-'))
	{
		//alert("You have enter a invalid number.");
		return 0;

	}
//当最后一位为.时出错
	if (num.charAt(i-1)=='.')
	{
		//alert("You have enter a invalid number.");
		return 0;
	}

	for (j=0;j<i;j++)
	{
		if (num.charAt(j)=='.')
		{
			status++;
		}
		if (status>1)
		{
		//alert("You have enter a invalid number.");
		return 0;
		}
		if (num.charAt(j)<'0' || num.charAt(j)>'9' )
		{
			//if (((opt1==0) || (num.charAt(j)!='.')) && (j!=0))
			if (((opt1==0) || (num.charAt(j)!='.')))
			{
				//alert("You have enter a invalid number.");
				return 0;
			}
		}
	}
	return 1;
}


//函数名：fucPWDchk
//功能介绍：检查是否含有非数字或字母
//参数说明：要检查的字符串
//返回值：0：含有 1：全部为数字或字母
function fucPWDchk(str)
{
  var strSource ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  var ch;
  var i;
  var temp;

  for (i=0;i<=(str.length-1);i++)
  {

    ch = str.charAt(i);
    temp = strSource.indexOf(ch);
    if (temp==-1)
    {
     return 0;
    }
  }
  if (strSource.indexOf(ch)==-1)
  {
    return 0;
  }
  else
  {
    return 1;
  }
}

function jtrim(str)
{     while (str.charAt(0)==" ")
          {str=str.substr(1);}
     while (str.charAt(str.length-1)==" ")
         {str=str.substr(0,str.length-1);}
     return(str);
}


//函数名：fucCheckNUM
//功能介绍：检查是否为数字
//参数说明：要检查的数字
//返回值：1为是数字，0为不是数字
function fucCheckNUM(NUM)
{
	var i,j,strTemp;
	strTemp="0123456789";
	if ( NUM.length== 0)
		return 0
	for (i=0;i<NUM.length;i++)
	{
		j=strTemp.indexOf(NUM.charAt(i));
		if (j==-1)
		{
		//说明有字符不是数字
			return 0;
		}
	}
	//说明是数字
	return 1;
}

//函数名：checkLength
//功能介绍：检查字符串的长度,方便判断是不是超过指定长度
//参数说明：要检查的字符串strTemp,指定长度strlength
//返回值：不超过true，超过false
function checkLength(strTemp,strLength)
{
	if(strTemp.length>strLength )
	{
		alert("长度超过指定长度了！")
		return false
	}
	else
		return true
}

//函数名：fucCheckLength
//功能介绍：获得字符串的长度
//参数说明：要检查的字符串strTemp
//返回值：长度值
function fucCheckLength(strTemp)
{
	var i,sum;
	sum=0;
	for(i=0;i<strTemp.length;i++)
	{
		if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255))
			sum=sum+1;
		else
			sum=sum+2;
	}
	return sum;
}

function isletter(c)
{
        if (((c>='a') && (c<='z'))||((c>='A') && (c<='Z')))
                return true
        else
                return false
}


//
function checkcode(acode,alength,aname)
{
	var i,j,strTemp;
	strTemp="0123456789";

	if (alength != -1){
		//alert(acode.length);
		if (acode.length==0){
			alert(aname+"不能为空!");
			return 0 ;
		}
		if ( acode.length!= alength ){
			alert("你填写的"+aname+"不是"+alength+"位,请重输!");
			return 0 ;
		}
	}
	else{
		if (acode.length <=0){
			alert(aname+"不能为空!");
			return 0;
		}
	}
	for (i=0;i<acode.length;i++)
	{
		j=strTemp.indexOf(acode.charAt(i));
		if (j==-1)
		{
		//说明有字符不是数字
			alert("你填写的"+aname+"不正确,有字符!");
			return 0;
		}
	}
	//说明是数字
	return 1;
}

//检查长度及是否为int
function checkint(acode,alength,aname)
{
	var i,j,strTemp;
	strTemp="0123456789";
        
	if (acode.length <=0){
		alert(aname+"不能为空!");
		return 0;
	}
	if(acode.length>alength){
		alert(aname+"的长度不能大于"+alength+"位!");
		return 0;
		
	}
	for (i=0;i<acode.length;i++)
	{
		j=strTemp.indexOf(acode.charAt(i));
		if (j==-1)
		{
		//说明有字符不是数字
			alert("你填写的"+aname+"不正确,必须为整数!");
			return 0;
		}
	}

	if (alength == 0){
		if (acode <0){
			alert("你填写的"+aname+"应该大于等于零");
			return 0 ;
		}
	}
	else if (alength == 1){
		if (acode < 1){
			alert("你填写的"+aname+"应该大于等于1");
			return 0 ;
		}
	}
	

	return 1;
}

//检查是否为整数
function checkday(acode,startvalue,endvalue,aname)
{
	var i,j,strTemp;
	strTemp="0123456789";
	if (acode.length <=0){
		alert(aname+"不能为空!");
		return 0;
	}

	for (i=0;i<acode.length;i++)
	{
		j=strTemp.indexOf(acode.charAt(i));
		if (j==-1)
		{
		//说明有字符不是数字
			alert("你填写的"+aname+"不正确,必须为整数!");
			return 0;
		}
	}

	if (Number(acode) > endvalue || Number(acode) < startvalue ){
           alert(aname+"只能在"+startvalue+"-"+endvalue+"之间!");
           return 0;
	}
	//说明是数字
	return 1;
}



//检查是否为float
function checkfloat(acode,aname)
{
        if (acode.value.length <= 0) {
             alert("请输入"+aname+"!");
             return 0;
        }

        if (isNaN(acode.value) == true) {
             alert(aname+"只能为数字!");
             return 0;
        }
        if (acode.value < 0) {
             alert(aname+"必须大于等于零!");
             return 0;
        }
	return 1;
}


/**
*检查是否是小于上限的money格式,小数位最多两位
*sNum:要检查money
* max：整数部分最大位数,3=<max<=9
*/
function isUnderMoney(sNum,max)
{
	var re;
	if(max==3)
	{
		re=/^((\.)(\d){1,2}|(\d){1,3}(\.)(\d){1,2}|(\d){1,3})$/;
	}
	else if(max==4)
	{
		re=/^((\.)(\d){1,2}|(\d){1,4}(\.)(\d){1,2}|(\d){1,4})$/;
	}
	else if(max==5)
	{
		re=/^((\.)(\d){1,2}|(\d){1,5}(\.)(\d){1,2}|(\d){1,5})$/;
	}
	else if(max==6)
	{
		re=/^((\.)(\d){1,2}|(\d){1,6}(\.)(\d){1,2}|(\d){1,6})$/;
	}
	else if(max==7)
	{
		re=/^((\.)(\d){1,2}|(\d){1,7}(\.)(\d){1,2}|(\d){1,7})$/;
	}
	else if(max==8)
	{
		re=/^((\.)(\d){1,2}|(\d){1,8}(\.)(\d){1,2}|(\d){1,8})$/;
	}
	else if(max==9)
	{
		re=/^((\.)(\d){1,2}|(\d){1,9}(\.)(\d){1,2}|(\d){1,9})$/;
	}

    return re.test(sNum);
}

//检查折扣率
function checkPercent(acode,aname,startvalue,endvalue)
{
	if (acode.value.length <= 0 ){
           alert("请输入"+ aname+"!");
           return 0;
        }
        if (Number(acode.value) > endvalue || Number(acode.value < startvalue) ){
           alert(aname+"只能在"+startvalue+"-"+endvalue+"之间!");
           return 0;
        }
        if (isNaN(acode.value) == true) {
           alert(aname+"只能为数字!");
           return 0;
        }
        return 1;
}

//比较开始时间和结束时间(格式:11小时11分钟11秒 :111111)
function comparedate(rq1,rq2){

    if ( Number(rq1.substring(0,2))>23 ) {
         alert("开始时间非法设置,请重新填写!");
         return 0 ;
     }
    else if (Number(rq1.substring(2,4)) >59){
         alert("开始时间非法设置,请重新填写!");
         return 0 ;
    }
    else if (Number(rq1.substring(4,6)) >59){
        alert("开始时间非法设置,请重新填写!");
        return 0 ;
    }

   if ( Number(rq2.substring(0,2))>24 ) {
        alert("结束时间非法设置,请重新填写!");
        return 0 ;
      }
   else if (Number(rq2.substring(0,2))==24) {
   		if (Number(rq2.substring(2,4))!=0 || Number(rq2.substring(4,6))!=0 ) {
	        alert("结束时间非法设置,请重新填写!");
    	    return 0 ;
    	}
   }
   else if (Number(rq2.substring(2,4)) >59){
        alert("结束时间非法设置,请重新填写!");
        return 0 ;
      }
   else if (Number(rq2.substring(4,6)) >59){
        alert("结束时间非法设置,请重新填写!");
        return 0 ;
    }

    if (Number(rq1.substring(0,2)) > Number(rq2.substring(0,2)) ){
    	alert("开始时间不能大于结束时间");
    	return 0;
    	}
    else if (Number(rq1.substring(0,2)) == Number(rq2.substring(0,2)) ){
    	if (Number(rq1.substring(2,4)) > Number(rq2.substring(2,4)) ){
    	    alert("开始时间不能大于结束时间");
    	    return 0;
    	}
    	else if (Number(rq1.substring(2,4)) == Number(rq2.substring(2,4)) ){
    	    if (Number(rq1.substring(4,6)) >= Number(rq2.substring(4,6)) ){
    	       alert("开始时间不能大于结束时间");
    	       return 0;
    	    }
    	}
    }
}

//把时间写成标准的格式 :2000-1-1 -> 2000-01-01
function convertdate(datestr)
{
//对于已经通过验证的日期
	var lthdatestr
	lthdatestr= datestr.length ;

	var lastdate="";
	var tmpy="";
	var tmpm="";
	var tmpd="";
	var status =0 ;
	for (i=0;i<lthdatestr;i++)
	{	if (datestr.charAt(i)== '-')
		{
			status++;
		}
		if ((status==0) && (datestr.charAt(i)!='-'))
		{
			tmpy=tmpy+datestr.charAt(i)
		}
		if ((status==1) && (datestr.charAt(i)!='-'))
		{
			tmpm=tmpm+datestr.charAt(i)
		}
		if ((status==2) && (datestr.charAt(i)!='-'))
		{
			tmpd=tmpd+datestr.charAt(i)
		}
	}
	year=new String (tmpy);
	month=new String (tmpm);
	day=new String (tmpd)
	if (month.length ==1){
	   month = "0"+month;
	}
	if (day.length ==1){
		day = "0"+day;
	}
	lastdate = year +"-" +month + "-" + day ;
	return lastdate ;
}
//比较开始时间和结束时间 格式:(2000-9-1 || 2000-09-1)
function compareespdate(date1,date2)
{
	rq1 = convertdate(date1);
	rq2 = convertdate(date2);
	if (Number(rq1.substring(0,4)) > Number(rq2.substring(0,4)) ){
		alert("开始时间不能大于结束时间");
		return 0;
	}
	else if (Number(rq1.substring(0,4)) == Number(rq2.substring(0,4)) ){
		if (Number(rq1.substring(5,7)) > Number(rq2.substring(5,7)) ){
			alert("开始时间不能大于结束时间");
			return 0;
		}
		else if (Number(rq1.substring(5,7)) == Number(rq2.substring(5,7)) ){
			if (Number(rq1.substring(8,10)) >= Number(rq2.substring(8,10)) ){
				alert("开始时间不能大于结束时间");
				return 0;
			}
		}
	}
}

//比较开始时间和结束时间 格式:(2000-9-1 || 2000-09-1)
function compareespdate2(date1,date2)
{
	rq1 = convertdate(date1);
	rq2 = convertdate(date2);
	if (Number(rq1.substring(0,4)) > Number(rq2.substring(0,4)) ){
		alert("开始时间不能大于结束时间");
		return 0;
	}
	else if (Number(rq1.substring(0,4)) == Number(rq2.substring(0,4)) ){
		if (Number(rq1.substring(5,7)) > Number(rq2.substring(5,7)) ){
			alert("开始时间不能大于结束时间");
			return 0;
		}
		else if (Number(rq1.substring(5,7)) == Number(rq2.substring(5,7)) ){
			if (Number(rq1.substring(8,10)) > Number(rq2.substring(8,10)) ){
				alert("开始时间不能大于结束时间");
				return 0;
			}
		}
	}
}

//是否选择了check
function checkenable(aform,aname,avalue){
    var num=0;
    for (var j=0;j<aform.elements.length;j++){
	 if (aform.elements[j].name== avalue ){
            if(aform.elements[j].checked == true){
                 num=num+1;
            }
        }
    }
    if (num == 0){
       alert("请选择"+aname+"!");
       return 0 ;
    }
    return 1;
}


//所有check都选中
function checkAll(aform,avalue)
{
        for (var j=0;j<aform.elements.length;j++){
	 	if (aform.elements[j].name==avalue){
	 	aform.elements[j].checked=true;
	 	}
	}
}

//所有check都不选中
function uncheckAll(aform,avalue)
{
    	for (var j=0;j<aform.elements.length;j++){
	 	if (aform.elements[j].name==avalue){
	 	aform.elements[j].checked=false;
	 	}
	}
}

//把所有check 反选
function switchAll(aform,avalue)
{
   	for (var j=0;j<aform.elements.length;j++){
	 	if (aform.elements[j].name==avalue){
	 	aform.elements[j].checked = !aform.elements[j].checked;
	 	}
	}
}


function verifyIP (IPvalue) {
errorString = "";
theName = "IPaddress";

var ipPattern = /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/;
var ipArray = IPvalue.match(ipPattern);

if (IPvalue == "0.0.0.0")
errorString = errorString + theName + ': '+IPvalue+' is a special IP address and cannot be used here.';
else if (IPvalue == "255.255.255.255")
errorString = errorString + theName + ': '+IPvalue+' is a special IP address and cannot be used here.';
if (ipArray == null)
errorString = errorString + theName + ': '+IPvalue+' is not a valid IP address.';
else {
for (i = 0; i < 4; i++) {
thisSegment = ipArray[i];
if (thisSegment > 255) {
errorString = errorString + theName + ': '+IPvalue+' is not a valid IP address.';
i = 4;
}
if ((i == 0) && (thisSegment > 255)) {
errorString = errorString + theName + ': '+IPvalue+' is a special IP address and cannot be used here.';
i = 4;
      }
   }
}
extensionLength = 3;
if (errorString == "")
return 0;
else
return 1;
}

//检查输入的数字,小数部分不能超过两位
//返回1表示合法
//返回0表示小数过长了
function checkdecimal(num)
{
	if(num.value.indexOf('.')>0){
		num=num.value.substr(num.value.indexOf('.')+1,num.value.length-1);

		if ((num.length)<3){
			return 1;
		}
		else{
			return 0;
		}
	}
			return 1;
}

//建二维数组
function creatMArray(row,col){
	var indx=0;
	this.length=(row*10)+col

	for(var x=1;x<=row;x++){

		for(var y=1;y<=col;y++){

			indx=(x*10)+y;

			this[indx]="";
		}
	}

}

//一维数组
function arrayName(size){
	this.length=size;
	for(var x=0; x<=size;x++){
		this[x]="";
	}
}

//比较日期
function comdate(date1,date2)
{
	rq1 = convertdate(date1);
	rq2 = convertdate(date2);
	if (Number(rq1.substring(0,4)) > Number(rq2.substring(0,4)) ){
		return 0;
	}
	else if (Number(rq1.substring(0,4)) == Number(rq2.substring(0,4)) ){
		if (Number(rq1.substring(5,7)) > Number(rq2.substring(5,7)) ){
			return 0;
		}
		else if (Number(rq1.substring(5,7)) == Number(rq2.substring(5,7)) ){
			if (Number(rq1.substring(8,10)) >= Number(rq2.substring(8,10)) ){
				return 0;
			}
		}
	}
	return 1 ;
}

//check first click the button
function chkfirst(first){
	if(first != 0)
		return false;
	else
		return true;
}

//定时查看告警信息
function showFeedBackAlarm(){
	var Digital=new Date();
	var hours=Digital.getHours() ;
	var minutes=Digital.getMinutes();
	var seconds=Digital.getSeconds() ;
	for(var tempid =0;tempid<startArray.length;tempid++){
		if(hours == startArray[tempid] && minutes == 0 && seconds>=0 && seconds<5){
			if(confirm("是否查看告警信息?")){
				//location = "./AlarmForm.jsp";
				var popup = null;
				popup = window.open('','告警信息','alwaysRaised,dependent,height=600,width=750, top=100,left=150, menubar=no,scrollbars=no,resizable=no ,location=no');
				if (popup != null) {
					if (popup.opener == null) {
						popup.opener = self;
					}
					popup.location.href = 'PopupAlarmForm.jsp';
					popup.focus();
					return true ;
				}
			}
			else{
				break ;
				return true ;
			}
		}
	}
	setTimeout("showFeedBackAlarm()",4000);
}
//函数名：openAccountKeyDown()
//功能介绍：页面中按下回车键，如果在提交按钮上则表示提交，其他地方表示跳到下一个输入点
//			提交和刷新之间，现在可以从提交按向右到刷新，但还不能向左回来
//返回值：true or false
//营业页面中按键的处理操作
function openAccountKeyDown(){
	if (window.event.keyCode==13){
		//只要不是提交按钮，回车改为tab
		if (window.event.srcElement.name!="Submit"){
			window.event.keyCode=9;
	}
	}
	//在提交按钮上按下向右，相当于tab，转移到刷新按钮
	if (window.event.keyCode==39){
		if (window.event.srcElement.name=="Submit"){
			window.event.keyCode=9;
		}
	}

	return false;
}


//函数名：fucCheckMovTEL
//功能介绍：检查移动电话号码的位数
//参数说明：要检查的字符串
function fucCheckMovTEL(MovTEL)
{

	if ((MovTEL.length==11) || (MovTEL.length==12)){
		return true;
	}
	else{
		//alert(MovTEL.length);
		alert('请输入正确的移动电话号码，移动电话号码需11位或12位');
		return false;
	}
}
/**
 * add by mjj,20020820
 * 格式化日期对象为字符串
 * date:日期对象；
 * strFormat:格式化字符串，支持形如yyyy-MM-dd和yyyyMMdd或yy-MM-dd形式，
 * 若该参数传入的格式化形式不对则返回默认的yyyy-MM-dd格式的日期字符串
 * 返回值：返回格式化后的字符串
 */
function formatDate(date,strFormat)
{
    //Default format is "yyyy-MM-dd";
    //default seperate char is '-'
    var sepChar = '-';

    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if(month<10){
        month = '0' + month;
    }
    if(day<10){
        day = '0' + day;
    }
    //若是默认格式则直接返回格式化后的字符串
    if('yyyy-MM-dd'==strFormat){
        return (year + sepChar + month + sepChar + day);
    }

    //形如yyyy-MM-dd的格式
    if(10==strFormat.length){
        if('yyyy'==strFormat.substring(0,4)&&'MM'==strFormat.substring(5,7)&&'dd'==strFormat.substring(8,10)){
            if(strFormat.charAt(4)==strFormat.charAt(7)){
                sepChar = strFormat.charAt(4);
            }
        }
    }
    //形如yyyyMMdd和yy-MM-dd的格式
    else if(8==strFormat.length){
        if('yyyyMMdd'!=strFormat){
            //形如yy-MM-dd的格式
            if('yy'==strFormat.substring(0,2)&&'MM'==strFormat.substring(3,5)&&'dd'==strFormat.substring(6,8)){
                if(strFormat.charAt(2)==strFormat.charAt(5)){
                    sepChar = strFormat.charAt(2);
                }
                year = Math.floor((year/10))%10 + '' + year%10;
            }
        }
        else{ //若为yyyyMMdd的格式，则设分隔符为空
            sepChar = '';
        }
    }
    return (year + sepChar + month + sepChar + day);
}


//函数名：fucCheckNUM
//功能介绍：检查是否为数字
//参数说明：要检查的数字
//返回值：1为是数字，0为不是数字
function fucCheckPHONE(NUM)
{
	var i,j,strTemp;
	strTemp="0123456789-,、";
	if ( NUM.length== 0)
		return 0
	for (i=0;i<NUM.length;i++)
	{
		j=strTemp.indexOf(NUM.charAt(i));
		if (j==-1)
		{
		//说明有字符不是数字
			return 0;
		}
	}
	//说明是数字
	return 1;
}


