
//��������string_ltrim(str)
//���ܽ��ܣ�ȥ���û����е������ǰ�Ŀո�
//����˵����Ҫ�����û���
//����ֵ��ȥ���ո���û���
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
//��������string_rtrim(str)
//���ܽ��ܣ�ȥ���û����е��ұ����Ŀո�
//����˵����Ҫ�����û���
//����ֵ��ȥ���ո���û���
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

//��������space(str)
//���ܽ��ܣ�ȥ���û����еĿո�
//����˵����Ҫ�����û���
//����ֵ��ȥ���ո���û���
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





//��������fucCheckTEL
//���ܽ��ܣ�����Ƿ�Ϊ�绰����
//����˵����Ҫ�����ַ���
//����ֵ��1Ϊ�ǺϷ���0Ϊ���Ϸ�
function fucCheckTEL(TEL)
{
	var i,j,strTemp;
	strTemp="0123456789-()# ";
	for (i=0;i<TEL.length;i++)
	{
		j=strTemp.indexOf(TEL.charAt(i));
		if (j==-1)
		{
		//˵�����ַ����Ϸ�
			alert("�绰������д����ȷ��")
			return false;
		}
	}
	//˵���Ϸ�
	return true;
}

//�������¼����ֻ����Ӣ����ĸ(a-z)��(A-Z),����(0-9),�»���(_)��ɡ���¼����������ĸ��ͷ����Сдû������
function checkname(name)
{
        var i, n
        if (!isletter(name.substr(0, 1)))
        {
                alert('��������ȷ�ĵ�¼������������ĸ��ͷ��')
                return false
        }
        for (i=0; i<name.length; i++)
        {
                n = name.charAt(i)
                if (!(isletter(n) || IsDigit(n) || (n=='_')))
                {
                        alert('��������ȷ�ĵ�¼����ֻ����Ӣ����ĸ(a-z)��(A-Z), ����(0-9)���»���(_)��ɡ�');
                        return false;
                }
        }
        if (n=='_')
        {
                alert('��������ȷ�ĵ�¼������������ĸ�����ֽ�β')
                return false
        }
        return true;
}
function IsDigit(cCheck)
	{
	return (('0'<=cCheck) && (cCheck<='9'));
	}

//����(����6λ)��
function checkpass(pass)
{
        var i
        if (pass.length<6){alert('��������ȷ�����룬����������6λ');return false;}
        for (i=0; i<pass.length; i++)
        {
                if (pass.substr(i, 1) == ' ')
                {
                        alert('��������ȷ�����룬���벻�ܺ��пո�');
                        return false;
                }
        }
        return true;
}
//�������������ȷ��
function checkpass2(pass1,pass2)
{
  var sPasswd = pass1;
  var sPasswd1 = pass2;
  if (sPasswd != sPasswd1) {alert('������������벻��ͬ');return;}
}


//����У�����ڣ�1�������շֿ����� 2����������Ϊһ��string���롰2001��12��21��

//��������validateDay
//���ܽ��ܣ�����Ƿ�Ϊ����
//����˵����yearStr, monthStr, dayStr
//����ֵ��true,false
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


//��������chkdate
//���ܽ��ܣ�����Ƿ�Ϊ����
//����˵����Ҫ�����ַ���datestr,��ʽ�硰2001��12��21��
//����ֵ��0����������  1��������
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

//��������checkTime
//���ܽ��ܣ�����Ƿ�Ϊʱ��
//����˵����yearStr, monthStr, dayStr,hourStr,minStr
//����ֵ��true,false
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

//��������compareDate
//���ܽ��ܣ��Ƚ�����
//����˵����Ҫ�Ƚϵ�����date1,date2
//����ֵ��0��date1��date2С�� 1��date1��date2��;2:date1=date2

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

//��������compareDatesLength
//���ܽ��ܣ��Ƚ�����
//����˵����Ҫ�Ƚϵ�����date1,date2
//����ֵ��0��date1��date2������һ���£� 1��date1��date2����һ����
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

//��������chkemail
//���ܽ��ܣ�����Ƿ�ΪEmail Address
//����˵����Ҫ�����ַ���
//����ֵ��0������  1����
function chkemail(a)
{
	var i=a.length;
	if(i==0) return 1;

	var temp = a.indexOf('@');
	var tempd = a.indexOf('.');

	if ((chkspc(a)==0))
	{	alert ("����д��ȷ��e-mail��ַ!");
		return 0;
	}



	if (temp > 0) {
		if ((i-temp) > 3){
			if(tempd>0){
				if (((tempd-temp)>1) && ((i-tempd)>1)){
					return 1;
				}
				else{
					alert("���Email��ַ�Ƿ���д��ȷ");
					return 0;
				}
			}
			else{
				alert("���Email��ַ�Ƿ���д��ȷ");
				return 0;
			}
		}
		else{
			alert("���Email��ַ�Ƿ���д��ȷ");
			return 0;
		}
	}
	else
	{
		alert("���Email��ַ�Ƿ���д��ȷ");
		return 0;
	}
}

//������֤���룺checkID
function checkID(a)
{
	var i,j,strTemp;
	strTemp="0123456789";
	if ( a.length!= 15 || a.length!=18 )
	{
		alert("����д�����֤���벻��15λ��18λ�ģ�")
		return 0
	}
	for (i=0;i<a.length;i++)
	{
		j=strTemp.indexOf(a.charAt(i));
		if (j==-1)
		{
		//˵�����ַ���������
			alert("����д�����֤���벻��ȷ�����ַ�")
			return 0;
		}
	}
	//˵��������
	return 1;
}

//����������룺checkPostalcode()
function checkPostalcode(a)
{
	var i,j,strTemp;
	strTemp="0123456789";
	if ( a.length!= 6 )
	{
		alert("����д���������벻��6λ�ģ�")
		return 0
	}
	for (i=0;i<a.length;i++)
	{
		j=strTemp.indexOf(a.charAt(i));
		if (j==-1)
		{
		//˵�����ַ���������
			alert("����д���������벻��ȷ�����ַ�")
			return 0;
		}
	}
	//˵��������
	return 1;
}


//��������chksafe
//���ܽ��ܣ�����Ƿ���"'",'\\',"/"
//����˵����Ҫ�����ַ���
//����ֵ��0����  1������
function chksafe(a)
{
	return 1;
/*	fibdn = new Array ("'" ,"\\", "��", ",", ";", "/");
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

//��������chkspc
//���ܽ��ܣ�����Ƿ��пո�
//����˵����Ҫ�����ַ���
//����ֵ��0����  1������
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


//opt1 С��     opt2   ����
//��opt2Ϊ1ʱ���num�Ƿ��Ǹ���
//��opt1Ϊ1ʱ���num�Ƿ���С��
//����1����ȷ�ģ�0�Ǵ����
function chknbr(num,opt1,opt2)
{
	var i=num.length;
	var staus;
//staus���ڼ�¼.�ĸ���
	status=0;
	if ((opt2!=1) && (num.charAt(0)=='-'))
	{
		//alert("You have enter a invalid number.");
		return 0;

	}
//�����һλΪ.ʱ����
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


//��������fucPWDchk
//���ܽ��ܣ�����Ƿ��з����ֻ���ĸ
//����˵����Ҫ�����ַ���
//����ֵ��0������ 1��ȫ��Ϊ���ֻ���ĸ
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


//��������fucCheckNUM
//���ܽ��ܣ�����Ƿ�Ϊ����
//����˵����Ҫ��������
//����ֵ��1Ϊ�����֣�0Ϊ��������
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
		//˵�����ַ���������
			return 0;
		}
	}
	//˵��������
	return 1;
}

//��������checkLength
//���ܽ��ܣ�����ַ����ĳ���,�����ж��ǲ��ǳ���ָ������
//����˵����Ҫ�����ַ���strTemp,ָ������strlength
//����ֵ��������true������false
function checkLength(strTemp,strLength)
{
	if(strTemp.length>strLength )
	{
		alert("���ȳ���ָ�������ˣ�")
		return false
	}
	else
		return true
}

//��������fucCheckLength
//���ܽ��ܣ�����ַ����ĳ���
//����˵����Ҫ�����ַ���strTemp
//����ֵ������ֵ
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
			alert(aname+"����Ϊ��!");
			return 0 ;
		}
		if ( acode.length!= alength ){
			alert("����д��"+aname+"����"+alength+"λ,������!");
			return 0 ;
		}
	}
	else{
		if (acode.length <=0){
			alert(aname+"����Ϊ��!");
			return 0;
		}
	}
	for (i=0;i<acode.length;i++)
	{
		j=strTemp.indexOf(acode.charAt(i));
		if (j==-1)
		{
		//˵�����ַ���������
			alert("����д��"+aname+"����ȷ,���ַ�!");
			return 0;
		}
	}
	//˵��������
	return 1;
}

//��鳤�ȼ��Ƿ�Ϊint
function checkint(acode,alength,aname)
{
	var i,j,strTemp;
	strTemp="0123456789";
        
	if (acode.length <=0){
		alert(aname+"����Ϊ��!");
		return 0;
	}
	if(acode.length>alength){
		alert(aname+"�ĳ��Ȳ��ܴ���"+alength+"λ!");
		return 0;
		
	}
	for (i=0;i<acode.length;i++)
	{
		j=strTemp.indexOf(acode.charAt(i));
		if (j==-1)
		{
		//˵�����ַ���������
			alert("����д��"+aname+"����ȷ,����Ϊ����!");
			return 0;
		}
	}

	if (alength == 0){
		if (acode <0){
			alert("����д��"+aname+"Ӧ�ô��ڵ�����");
			return 0 ;
		}
	}
	else if (alength == 1){
		if (acode < 1){
			alert("����д��"+aname+"Ӧ�ô��ڵ���1");
			return 0 ;
		}
	}
	

	return 1;
}

//����Ƿ�Ϊ����
function checkday(acode,startvalue,endvalue,aname)
{
	var i,j,strTemp;
	strTemp="0123456789";
	if (acode.length <=0){
		alert(aname+"����Ϊ��!");
		return 0;
	}

	for (i=0;i<acode.length;i++)
	{
		j=strTemp.indexOf(acode.charAt(i));
		if (j==-1)
		{
		//˵�����ַ���������
			alert("����д��"+aname+"����ȷ,����Ϊ����!");
			return 0;
		}
	}

	if (Number(acode) > endvalue || Number(acode) < startvalue ){
           alert(aname+"ֻ����"+startvalue+"-"+endvalue+"֮��!");
           return 0;
	}
	//˵��������
	return 1;
}



//����Ƿ�Ϊfloat
function checkfloat(acode,aname)
{
        if (acode.value.length <= 0) {
             alert("������"+aname+"!");
             return 0;
        }

        if (isNaN(acode.value) == true) {
             alert(aname+"ֻ��Ϊ����!");
             return 0;
        }
        if (acode.value < 0) {
             alert(aname+"������ڵ�����!");
             return 0;
        }
	return 1;
}


/**
*����Ƿ���С�����޵�money��ʽ,С��λ�����λ
*sNum:Ҫ���money
* max�������������λ��,3=<max<=9
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

//����ۿ���
function checkPercent(acode,aname,startvalue,endvalue)
{
	if (acode.value.length <= 0 ){
           alert("������"+ aname+"!");
           return 0;
        }
        if (Number(acode.value) > endvalue || Number(acode.value < startvalue) ){
           alert(aname+"ֻ����"+startvalue+"-"+endvalue+"֮��!");
           return 0;
        }
        if (isNaN(acode.value) == true) {
           alert(aname+"ֻ��Ϊ����!");
           return 0;
        }
        return 1;
}

//�ȽϿ�ʼʱ��ͽ���ʱ��(��ʽ:11Сʱ11����11�� :111111)
function comparedate(rq1,rq2){

    if ( Number(rq1.substring(0,2))>23 ) {
         alert("��ʼʱ��Ƿ�����,��������д!");
         return 0 ;
     }
    else if (Number(rq1.substring(2,4)) >59){
         alert("��ʼʱ��Ƿ�����,��������д!");
         return 0 ;
    }
    else if (Number(rq1.substring(4,6)) >59){
        alert("��ʼʱ��Ƿ�����,��������д!");
        return 0 ;
    }

   if ( Number(rq2.substring(0,2))>24 ) {
        alert("����ʱ��Ƿ�����,��������д!");
        return 0 ;
      }
   else if (Number(rq2.substring(0,2))==24) {
   		if (Number(rq2.substring(2,4))!=0 || Number(rq2.substring(4,6))!=0 ) {
	        alert("����ʱ��Ƿ�����,��������д!");
    	    return 0 ;
    	}
   }
   else if (Number(rq2.substring(2,4)) >59){
        alert("����ʱ��Ƿ�����,��������д!");
        return 0 ;
      }
   else if (Number(rq2.substring(4,6)) >59){
        alert("����ʱ��Ƿ�����,��������д!");
        return 0 ;
    }

    if (Number(rq1.substring(0,2)) > Number(rq2.substring(0,2)) ){
    	alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
    	return 0;
    	}
    else if (Number(rq1.substring(0,2)) == Number(rq2.substring(0,2)) ){
    	if (Number(rq1.substring(2,4)) > Number(rq2.substring(2,4)) ){
    	    alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
    	    return 0;
    	}
    	else if (Number(rq1.substring(2,4)) == Number(rq2.substring(2,4)) ){
    	    if (Number(rq1.substring(4,6)) >= Number(rq2.substring(4,6)) ){
    	       alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
    	       return 0;
    	    }
    	}
    }
}

//��ʱ��д�ɱ�׼�ĸ�ʽ :2000-1-1 -> 2000-01-01
function convertdate(datestr)
{
//�����Ѿ�ͨ����֤������
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
//�ȽϿ�ʼʱ��ͽ���ʱ�� ��ʽ:(2000-9-1 || 2000-09-1)
function compareespdate(date1,date2)
{
	rq1 = convertdate(date1);
	rq2 = convertdate(date2);
	if (Number(rq1.substring(0,4)) > Number(rq2.substring(0,4)) ){
		alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
		return 0;
	}
	else if (Number(rq1.substring(0,4)) == Number(rq2.substring(0,4)) ){
		if (Number(rq1.substring(5,7)) > Number(rq2.substring(5,7)) ){
			alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
			return 0;
		}
		else if (Number(rq1.substring(5,7)) == Number(rq2.substring(5,7)) ){
			if (Number(rq1.substring(8,10)) >= Number(rq2.substring(8,10)) ){
				alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
				return 0;
			}
		}
	}
}

//�ȽϿ�ʼʱ��ͽ���ʱ�� ��ʽ:(2000-9-1 || 2000-09-1)
function compareespdate2(date1,date2)
{
	rq1 = convertdate(date1);
	rq2 = convertdate(date2);
	if (Number(rq1.substring(0,4)) > Number(rq2.substring(0,4)) ){
		alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
		return 0;
	}
	else if (Number(rq1.substring(0,4)) == Number(rq2.substring(0,4)) ){
		if (Number(rq1.substring(5,7)) > Number(rq2.substring(5,7)) ){
			alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
			return 0;
		}
		else if (Number(rq1.substring(5,7)) == Number(rq2.substring(5,7)) ){
			if (Number(rq1.substring(8,10)) > Number(rq2.substring(8,10)) ){
				alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
				return 0;
			}
		}
	}
}

//�Ƿ�ѡ����check
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
       alert("��ѡ��"+aname+"!");
       return 0 ;
    }
    return 1;
}


//����check��ѡ��
function checkAll(aform,avalue)
{
        for (var j=0;j<aform.elements.length;j++){
	 	if (aform.elements[j].name==avalue){
	 	aform.elements[j].checked=true;
	 	}
	}
}

//����check����ѡ��
function uncheckAll(aform,avalue)
{
    	for (var j=0;j<aform.elements.length;j++){
	 	if (aform.elements[j].name==avalue){
	 	aform.elements[j].checked=false;
	 	}
	}
}

//������check ��ѡ
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

//������������,С�����ֲ��ܳ�����λ
//����1��ʾ�Ϸ�
//����0��ʾС��������
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

//����ά����
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

//һά����
function arrayName(size){
	this.length=size;
	for(var x=0; x<=size;x++){
		this[x]="";
	}
}

//�Ƚ�����
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

//��ʱ�鿴�澯��Ϣ
function showFeedBackAlarm(){
	var Digital=new Date();
	var hours=Digital.getHours() ;
	var minutes=Digital.getMinutes();
	var seconds=Digital.getSeconds() ;
	for(var tempid =0;tempid<startArray.length;tempid++){
		if(hours == startArray[tempid] && minutes == 0 && seconds>=0 && seconds<5){
			if(confirm("�Ƿ�鿴�澯��Ϣ?")){
				//location = "./AlarmForm.jsp";
				var popup = null;
				popup = window.open('','�澯��Ϣ','alwaysRaised,dependent,height=600,width=750, top=100,left=150, menubar=no,scrollbars=no,resizable=no ,location=no');
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
//��������openAccountKeyDown()
//���ܽ��ܣ�ҳ���а��»س�����������ύ��ť�����ʾ�ύ�������ط���ʾ������һ�������
//			�ύ��ˢ��֮�䣬���ڿ��Դ��ύ�����ҵ�ˢ�£����������������
//����ֵ��true or false
//Ӫҵҳ���а����Ĵ������
function openAccountKeyDown(){
	if (window.event.keyCode==13){
		//ֻҪ�����ύ��ť���س���Ϊtab
		if (window.event.srcElement.name!="Submit"){
			window.event.keyCode=9;
	}
	}
	//���ύ��ť�ϰ������ң��൱��tab��ת�Ƶ�ˢ�°�ť
	if (window.event.keyCode==39){
		if (window.event.srcElement.name=="Submit"){
			window.event.keyCode=9;
		}
	}

	return false;
}


//��������fucCheckMovTEL
//���ܽ��ܣ�����ƶ��绰�����λ��
//����˵����Ҫ�����ַ���
function fucCheckMovTEL(MovTEL)
{

	if ((MovTEL.length==11) || (MovTEL.length==12)){
		return true;
	}
	else{
		//alert(MovTEL.length);
		alert('��������ȷ���ƶ��绰���룬�ƶ��绰������11λ��12λ');
		return false;
	}
}
/**
 * add by mjj,20020820
 * ��ʽ�����ڶ���Ϊ�ַ���
 * date:���ڶ���
 * strFormat:��ʽ���ַ�����֧������yyyy-MM-dd��yyyyMMdd��yy-MM-dd��ʽ��
 * ���ò�������ĸ�ʽ����ʽ�����򷵻�Ĭ�ϵ�yyyy-MM-dd��ʽ�������ַ���
 * ����ֵ�����ظ�ʽ������ַ���
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
    //����Ĭ�ϸ�ʽ��ֱ�ӷ��ظ�ʽ������ַ���
    if('yyyy-MM-dd'==strFormat){
        return (year + sepChar + month + sepChar + day);
    }

    //����yyyy-MM-dd�ĸ�ʽ
    if(10==strFormat.length){
        if('yyyy'==strFormat.substring(0,4)&&'MM'==strFormat.substring(5,7)&&'dd'==strFormat.substring(8,10)){
            if(strFormat.charAt(4)==strFormat.charAt(7)){
                sepChar = strFormat.charAt(4);
            }
        }
    }
    //����yyyyMMdd��yy-MM-dd�ĸ�ʽ
    else if(8==strFormat.length){
        if('yyyyMMdd'!=strFormat){
            //����yy-MM-dd�ĸ�ʽ
            if('yy'==strFormat.substring(0,2)&&'MM'==strFormat.substring(3,5)&&'dd'==strFormat.substring(6,8)){
                if(strFormat.charAt(2)==strFormat.charAt(5)){
                    sepChar = strFormat.charAt(2);
                }
                year = Math.floor((year/10))%10 + '' + year%10;
            }
        }
        else{ //��ΪyyyyMMdd�ĸ�ʽ������ָ���Ϊ��
            sepChar = '';
        }
    }
    return (year + sepChar + month + sepChar + day);
}


//��������fucCheckNUM
//���ܽ��ܣ�����Ƿ�Ϊ����
//����˵����Ҫ��������
//����ֵ��1Ϊ�����֣�0Ϊ��������
function fucCheckPHONE(NUM)
{
	var i,j,strTemp;
	strTemp="0123456789-,��";
	if ( NUM.length== 0)
		return 0
	for (i=0;i<NUM.length;i++)
	{
		j=strTemp.indexOf(NUM.charAt(i));
		if (j==-1)
		{
		//˵�����ַ���������
			return 0;
		}
	}
	//˵��������
	return 1;
}


