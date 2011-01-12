package com.ztesoft.common.application;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;



public class AppContext {
public static void main(String []args){
	AppContext myapp=new AppContext();
	try {
		
		String code="new com.ztesoft.crm.business.common.query.SqlMapExe app;" +
	    "setField app null;" +
	    "setField app tempParam;" +
	    "exeret app getInstance null app tempParam;"+
		"loadInst app tempParam newApp;"+
		"setField newApp sql;" +
		"setField newApp tmpResult;" +
		"setString newApp sql=select 1 from dual;" +
		"exeret newApp queryForMapList sql newApp tmpResult;" +
		"getValue newApp tmpResult;" ;
		HashMap vo=new HashMap();
		myapp.process(code,vo);
		System.out.println(vo);
	} catch (Throwable e) {
		//e.printStackTrace();
		System.out.println(Error.getStackTraceAsString(e));
	} 
}
//����ִ����
private AppContext myapp=null;//��������ִ�е�����������
//��������
private HashMap _clsApp=new HashMap();
//������ʵ��(���AppClass��ʵ��:���ֶ�)
private HashMap _instApp=new HashMap();

public HashMap getAllClsApp(){
	return this._clsApp;
}
public HashMap getAllInstApp(){
	return this._instApp;
}

//��������(���AppClass��ʵ��:���ֶ�)
public void addClass(AppClass inAppClass) throws Exception{
	if(inAppClass==null)throw new Exception("class����Ϊnull");
	_clsApp.put(inAppClass.getClassName(),inAppClass);
}
//���������
//public void addIntercs(String interTime,String className,String methodName,Object _instObj) throws Exception{
//	AppClass _tmpApp= (AppClass)_clsApp.get(className);
//	Object []_objParam=null;
//	if(_tmpApp!=null && !_instApp.isEmpty()){
//		_tmpApp.addIntercs(interTime, className, methodName, _instObj);
//	}
//}
//����෽��
public void addClassMethod(String className,String methodName,Object _instObj) throws Exception{
	AppClass _tmpApp= (AppClass)_clsApp.get(className);
	if(_tmpApp!=null && !_instApp.isEmpty()){
		_tmpApp.addMethod(className, methodName,methodName, _instObj);
	}
}

//����෽��
public void addClassMethod(String className,String methodName) throws Exception{
	this.addClassMethod(className,methodName,"");
}
//����෽��
public void addClassMethod(String className,String methodName,String _instObj) throws Exception{
	this.addClassMethod(className,methodName,_instObj,null);
}
//����෽��
public void addClassMethod(String className,String methodName,String _instObj,String newMethodName) throws Exception{
	AppClass _tmpApp= (AppClass)_clsApp.get(className);
	Object []_objParam=null;
	if(_tmpApp!=null && !_instApp.isEmpty()){
		if(_instObj!=null && !_instObj.equals("")){
			String[]strbParam=_instObj.split(",");
			_objParam=new Object[strbParam.length];
			for(int i=0;i<strbParam.length;i++){
				_objParam[i]=_tmpApp.getValue(strbParam[i]);
			}
		}
		this.addClassMethod(className, methodName,_objParam,newMethodName);
	}
}
//����෽��
public void addClassMethod(String className,String methodName,Object[] _instObj) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	addClassMethod(className,methodName,_instObj,null);
}
//����෽��
public void addClassMethod(String className,String methodName,Object[] _instObj,String newMethodName) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	AppClass _tmpApp= (AppClass)_clsApp.get(className);
	if(_tmpApp!=null && !_instApp.isEmpty()){
		_tmpApp.addMethod(className, methodName, newMethodName,_instObj);
	}
}

//��������(���AppClass��ʵ��:���ֶ�)
public void addClass(String strAppClass) throws Exception{
	if(strAppClass==null || strAppClass.equals(""))throw new Exception("class����Ϊnull");
	AppClass inAppClass=new AppClass();
	inAppClass.loadFromClass(strAppClass, null);
	this.addClass(inAppClass);
}

//��������(���AppClass��ʵ��:���ֶ�)
public void addClass(String strAppClass,Object _obj) throws Exception{
	if(strAppClass==null || strAppClass.equals(""))throw new Exception("class����Ϊnull");
	AppClass inAppClass=new AppClass();
	inAppClass.loadFromClass(strAppClass, _obj);
	this.addClass(inAppClass);
}

public void setInst(String appName,Object _inst){
	//��ȡһ��ʵ��
	AppClass _tmpApp= (AppClass)_instApp.get(appName);
	_tmpApp.setInst(_inst);
}
//��������(���AppClass��ʵ��:���ֶ�)
public void addClass(String strAppClass,Object[]obj) throws Exception{
	if(strAppClass==null || strAppClass.equals(""))throw new Exception("class����Ϊnull");
	AppClass inAppClass=new AppClass();
	inAppClass.loadFromClass(strAppClass, obj);
	this.addClass(inAppClass);
}
//��ȡ��ʵ��
public void newInstance(String className,String instanceName) throws Exception{
	AppClass _tmpApp= (AppClass)_instApp.get(instanceName);
	if(_tmpApp==null){
		this.setNewInstance(className, instanceName);
		_tmpApp= (AppClass)_instApp.get(instanceName);
	}
}
//��ȡ��ʵ��
public AppClass getInstance(String instanceName) throws Exception{
	return (AppClass)_instApp.get(instanceName);
}

//�ж��Ƿ��������ʵ��
public boolean isInstance(String instanceName) throws Exception{
	return _instApp.containsKey(instanceName);
}

//������ʵ��������
private void setNewInstance(String className,String instanceName) throws Exception{
	if(_instApp.containsKey(instanceName))throw new Exception("ʵ�����Ѿ�����");
	//�����Ƿ����������
	AppClass _class=this.ClassForName(className);
	//����һ��ʵ��������������
	AppClass _inst=(AppClass)_class.clone();
	_instApp.put(instanceName, _inst);
}
//����һ��AppClassʵ��
private AppClass ClassForName(String className) throws Exception{
	if(className==null || className.equals(""))throw new Exception("ClassNotFoundException");
	if(_clsApp==null || _clsApp.isEmpty())throw new Exception("ClassNotFoundException");
	AppClass _class=(AppClass)_clsApp.get(className);
	if(_class==null)throw new Exception("ClassNotFoundException");
	return _class;
}

//����ҵ������ķ���
public HashMap process(String acceptService,HashMap vo) throws Exception{
	if(acceptService==null || acceptService.equals(""))return null;
	String[]strAcceptService=acceptService.split(";");
	ArrayList _acceptService=new ArrayList();
	Complier mytrim=new Complier();
	for(int i=0;i<strAcceptService.length;i++){
		_acceptService.add(mytrim.trimSpace(strAcceptService[i]));
	}
	return this.process(null,_acceptService,vo);
}

//����ҵ������ķ���
public HashMap process(AppContext app,String acceptService,HashMap vo) throws Exception{
	if(acceptService==null || acceptService.equals(""))return null;
	String[]strAcceptService=acceptService.split(";");
	ArrayList _acceptService=new ArrayList();
	for(int i=0;i<strAcceptService.length;i++){
		_acceptService.add(strAcceptService[i]);
	}
	return this.process(app,_acceptService,vo);
}

//���ⲿ���õ��뵱ǰ��
public void loadOriFields(String appName,HashMap vo) throws Exception{
	if(myapp==null)myapp=new AppContext();
	AppClass _bo=myapp.getInstance(appName);
	Iterator it=vo.entrySet().iterator();
	while(it.hasNext()){
		Map.Entry map=(Map.Entry)it.next();
		String key=(String)map.getKey();
		Object val=map.getValue();
		if(!_bo.isContainField(key))
		_bo.setField("public String "+key+"=null");
		_bo.setValue(key, val);
	}	
}
//����ҵ������ķ���
public HashMap process(String acceptService) throws Exception{
	if(acceptService==null || acceptService.equals(""))return null;
	String[]strAcceptService=acceptService.split(";");
	ArrayList _acceptService=new ArrayList();
	for(int i=0;i<strAcceptService.length;i++){
		_acceptService.add(strAcceptService[i]);
	}
	return this.process(null,_acceptService,null);
}


//����ҵ������ķ��� vo�Ǵ����һ������
/*����ִ��ʾ��:
 * 
 * //�½���ʵ�� new
	_lstClass.add("new com.ztesoft.common.Application.AppTest myapp");
	//�������ֶ� setField
	_lstClass.add("setField myapp b");
	//�������ֶ�ֵ setValue
	_lstClass.add("setValue myapp b=\"mzptest\"");
	//ִ���෽�� exenon,������b,�޷���ֵ
	_lstClass.add("exenon myapp print b");
	//����෽�� addmethod ������:AppTest ��������:print ��AppTest��ʵ���Ĺ��캯��������c
	//_lstClass.add("addmothod myapp com.ztesoft.common.Application.AppTest print c");
	//�Է���print���õ���ǰ������,���������õ�����a�ķ���b,���캯��������c
	_lstClass.add("addbfinterc myapp print a b c;");
	//�Է���print���õ��ú�������,���������õ�����a�ķ���b,���캯��������c
	_lstClass.add("addafinterc myapp print a b c;");
	//ִ���෽�� exeret,������a,����ֵ�������ֶ�b��
	_lstClass.add("exeret myapp print a b");
	//����෽�� gUtils.commonDAO_ods���ֶα���(��ҪԤ���о����ʵ��) preUpdate�෽�� mypreUpdate�෽������
	_lstClass.add("setMethod myapp gUtils.commonDAO_ods preUpdate mypreUpdate;");
	//myapp��һ����ʵ������˼�����һ��myappʵ�������񣬲��ҰѴ����vo��������ʵ��vo.get("myapp")�����ô�����ǰ������ֻҪִ�������ķ�����������������ʵ��ȥִ��
	_lstClass.add("setInst myapp");
	//��ȡ��ʵ��myapp��a�ֶε�ֵ
	_lstClass.add("getValue myapp a");
 * 
 * */
private HashMap process(AppContext parentApp, ArrayList acceptService,HashMap vo) throws Exception{
	HashMap hsRet=vo;
	String errCode="";
	try{
	if(parentApp!=null)//����д�app����,���ô�������appִ��ҵ�����
	myapp=parentApp;
	//����ƫ����,����֧��goto���
	boolean isFalse=true;
	if(acceptService!=null && !acceptService.isEmpty()){
		if(myapp==null)myapp=new AppContext();
		for(int i=0;i<acceptService.size();i++){
			//����ִ��ҵ�����
			String _current=(String)acceptService.get(i);
//			System.out.println("\n"+_current);
			errCode=_current;
			String []_code=_current.split(" ");
			String _head=_code[0];
			if(_head.equals("else")){
				isFalse=true;
				continue;
			}
			if(!isFalse)continue;
			
			if(_head.equals("load")){//�����load����,��ô��Ѵ����vo��ֵ��ֵ��һ������ʵ��
				//���������⴦��,��Ϊ������һ������,��Ҫ��������÷��ھ����һ��������ֶ���,����������д����ȡvo��key��val
				if(vo!=null && !vo.isEmpty()){
					Iterator it=vo.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry map=(Map.Entry)it.next();
						String []appNameField=((String)map.getKey()).split("/");
						String appName=appNameField[0];
						if(!myapp.isInstance(appName)){
							continue;//ֻҪû�����ʵ����
						}
						AppClass _bo=myapp.getInstance(appName);//��ȡһ��ʵ������class
						String fieldName=appNameField[1];
						//�������ֶ�
						if(!_bo.isField(fieldName)){
							_bo.setField("public String "+fieldName+"=null");
						}
						//�������ֶ�ֵ
						_bo.setValue(fieldName, map.getValue());
					}
				}
				continue;
			}
			if(_head.equals("loadInst")){//�����loadInst myapp a newApp
				//��ȡ����ʵ��
				AppClass _bo=myapp.getInstance(_code[1]);
				Object _res=_bo.getValue(_code[2]);
				if(_res!=null){
					Class _clazz=_res.getClass();
					String _className=_clazz.getName();
					if(!_clsApp.containsKey(_className))
						myapp.addClass(_className,_res);//�������+�ⲿʵ������
					//ʵ��������
					myapp.newInstance(_className, _code[3]);
				}
			}
			if(_head.equals("new")){//�����new com.ztesoft.common.Application.AppTest myapp��������ʽ
				if(_code.length==3){
					//�ж��Ƿ����������,�����,�Ͳ�Ҫ�����
					if(!_clsApp.containsKey(_code[1]))
					myapp.addClass(_code[1]);//�������
					myapp.newInstance(_code[1], _code[2]);//ʵ��������
				}
				if(_code.length==5){//new com.ztesoft.common.Application.AppTest myapp2 myapp b
					AppClass _bo=myapp.getInstance(_code[3]);
					String []paramVal=_code[4].split(",");
					Object []paramObj=new Object[paramVal.length];
					for(int j=0;j<paramVal.length;j++){
						paramObj[j]=_bo.getValue(paramVal[j]);
					}
					myapp.addClass(_code[1], paramObj);
					myapp.newInstance(_code[1], _code[2]);//ʵ��������
				}
				
				continue;
			}
			if(_head.equals("setField")){//�����setField myapp b��������ʽ��ȡ��setField myapp b=a
				AppClass _bo=myapp.getInstance(_code[1]);
				String _val=_code[2];
				String []val=_val.split("=");
				if(val.length==2){
					_bo.setField("public String "+val[0]+"=null");
					//��ȡval[1]����ʵ���� val[1]����������ʽapp.field��ȡthis����asdfsdf
					String _value=val[1];
					//�ж��Ƿ���.
					if(_value.indexOf("/")>=0){//�����app.field
						//_value=_value.replaceAll(".", "/");//�滻��app/field
						String [] _tmpApp=_value.split("/");
						AppClass _co=myapp.getInstance(_tmpApp[0]);//app
						if(_co.isContainField(_tmpApp[1])){//field
							_bo.setValue(val[0], _co.getValue(_tmpApp[1]));
						}
					}else{
						if(_value.equals("this")){//�����this
							_bo.setValue(val[0], myapp);
						}else{//�����asdfdsf
							_bo.setValue(val[0], _value);
						}
					}
				}else{
					_bo.setField("public String "+_code[2]+"=null");
				}
				continue;
			}
			if(_head.equals("setValue")){//�����setValue myapp b=app.field"��������ʽ
				AppClass _bo=myapp.getInstance(_code[1]);
				String secondSegment=_code[2];
				String[]fieldVal=secondSegment.split("=");
				String key=fieldVal[0].trim();
				String val=fieldVal[1].trim();
				//���val��һ�����ñ���,��ô��ʽӦ����app.field
				String _tmpval=val;
				String [] _tmpApp=_tmpval.split("/");
				if(_tmpApp.length==2){//�����setField myapp b=app.field"��������ʽ
					AppClass _co=myapp.getInstance(_tmpApp[0]);//app
					if(_co.isContainField(_tmpApp[1])){//field
						_bo.setValue(key, _co.getValue(_tmpApp[1]));
					}
				}
				if(_tmpApp.length==1){//�����setValue myapp b=this"��setValue myapp b=sdfdsfsd��������ʽ
					if(_tmpval!=null && _tmpval.equals("this")){//�����this,��ô�Ͱѵ�ǰ�����ø�������ֶ�
						_bo.setValue(key.trim(), myapp);
//						hsRet.put(key.trim(),myapp);
					}else{
						//��Ҫ�ж�_tmpval�Ƿ���һ��ʵ��������,�����,��ô��Ҫ�����ʵ��������ȡ����
						AppClass _co=myapp.getInstance(_tmpval);//app
						if(_co==null)
							_bo.setValue(key, _tmpval);//ֱ�Ӹ�ֵ�ַ���
						else{//�ѵ�ǰʵ��������ʵ����ʵ�����ø�ֵ��_boʵ����key�ֶ�
							_bo.setValue(key, _co.getInst());
						}
					}
				}

				continue;
			}
			if(_head.equals("setString")){//�����setString myapp b=asdf sd"��������ʽ
				AppClass _bo=myapp.getInstance(_code[1]);
				String secondSegment=_code[2];
				int equalidx=secondSegment.indexOf("=");
				String key=secondSegment.trim().substring(0, equalidx);
				String val=secondSegment.trim().substring(equalidx+1);
				StringBuffer strbVal=new StringBuffer(val);
				if(_code.length>3){
					for(int j=3;j<_code.length;j++){
						strbVal.append(" ");
						strbVal.append(_code[j]);
					}
				}
//				System.out.println(strbVal.toString());
				_bo.setValue(key, strbVal.toString());
				continue;
			}
			if(_head.equals("exenon")){//�����exenon myapp print a��������ʽ:ִ��myappʵ����print����,���������ǵ�ǰmyappʵ����a�ֶ�
				AppClass _bo=myapp.getInstance(_code[1]);
				_bo.executeMethod(_code[2], _code[3]);
				continue;
			}
			if(_head.equals("exeret")){//�����exeret myapp print a b c��������ʽ:ִ��myappʵ����print����,���������ǵ�ǰmyappʵ����a�ֶ�,����ִ�к󷵻ص�ֵ��ֵ��bʵ����c�ֶ�
				AppClass _bo=myapp.getInstance(_code[1]);//ִ�з�����ʵ��
				AppClass _co=myapp.getInstance(_code[4]);//setvalue��ʵ��
				_co.setValue(_code[5], _bo.executeMethod(_code[2], _code[3]));
//				hsRet.put(_code[5], _co.getValue(_code[5]));
				continue;
			}
			if(_head.equals("addbfinterc")){//�����addbfinterc myapp print a b c;��������ʽ
				//�Է���print���õ���ǰ������,���������õ�����a�ķ���b,���캯��������c
				String c=null;
				if(_code.length==6)
					c=_code[5];
				AppClass _bo=myapp.getInstance(_code[1]);
				String interTime="bf_"+String.valueOf(System.currentTimeMillis());//����ʱ����bf_+ԭ�������ص�����+�����صķ�����
				_bo.addIntercs(interTime,_bo.getClassName(),_code[2],_code[3], _code[4], c);
				continue;
			}
			if(_head.equals("addafinterc")){//�����addbfinterc myapp print a b c;��������ʽ
				//�Է���print���õ��ú�������,���������õ�����a�ķ���b,���캯��������c
				String c=null;
				if(_code.length==6)
					c=_code[5];
				AppClass _bo=myapp.getInstance(_code[1]);
				String interTime="af_"+String.valueOf(System.currentTimeMillis());//����ʱ����bf_+ԭ�������ص�����+�����صķ�����
				_bo.addIntercs(interTime,_bo.getClassName(),_code[2],_code[3], _code[4], c);
				continue;
			}
//			if(_head.equals("setMethod")){//�����setMethod myapp a b c;��������ʽ
//				//��aʵ����b������c�ı�����ӵ�myapp�������ȥ
//				AppClass _bo=myapp.getInstance(_code[1]);
//				Object _obj=_bo.getValue(_code[2]);//��ȡʵ������
//				String _className=_obj.getClass().getName();//��ȡ����
//				_bo.setMethod(_className, _code[3], _code[4],_obj);
//				continue;
//			}
			if(_head.equals("eval")){//�����eval myapp appContext
				//��appContext���ַ���ִ��,����ִ�еĽ������ʵ����ӳ�䵽��ǰִ�е�myapp����
				AppClass _bo=myapp.getInstance(_code[1]);
				String _strCode=(String)_bo.getValue(_code[2]);//��ȡʵ������,һ�ο�ִ�еĴ���
				myapp.process(myapp,_strCode,vo);//ʹ�õ�ǰ��appִ����δ���
				continue;
			}
//			if(_head.equals("setInst")){//�����setInst myapp��������ʽ
//				//myapp��Ҫ�ڴ����vo�ﶨ��,��һ�����ʵ��
//				if(vo==null || vo.isEmpty()) throw new Exception("�޷���ȡ��ʵ��" +_code[1]);
//				//�ж��Ƿ����������,�����,�Ͳ�Ҫ�����
//				Object _classInst=vo.get(_code[1]);
//				String ClassName=_classInst.getClass().getName();
//				String instName=_code[1];
//				if(!_clsApp.containsKey(ClassName))
//				myapp.addClass(ClassName,_classInst);//�������(������ʵ��������)
//				myapp.newInstance(ClassName, instName);//ʵ��������
//				continue;
//			}
			if(_head.equals("getValue")){//�����getValue myapp a��������ʽ
				AppClass _bo=myapp.getInstance(_code[1]);
				hsRet.put(_code[2], _bo.getValue(_code[2]));
				continue;
			}
			if(_head.equals("setList")){//�����setList myapp a c,d,e��������ʽ
				AppClass _bo=myapp.getInstance(_code[1]);
				ArrayList _lst=new ArrayList();
				String _key=_code[2];
				String _val=_code[3];
				String[]__val=_val.split(",");
				if(__val!=null && __val.length>0){
					for(int j=0;j<__val.length;j++){
						String _obj=(String)_bo.getValue(__val[j]);
						_lst.add(_obj);
					}
				}
				_bo.setValue(_key, _lst);
				continue;
			}
			if(_head.equals("if")){//�����if myapp a myapp b goto c��������ʽ
				AppClass _lBo=myapp.getInstance(_code[1]);
				Object _objl=_lBo.getValue(_code[2]);
				AppClass _rBo=myapp.getInstance(_code[3]);
				Object _objr=_rBo.getValue(_code[4]);
				if(_objl!=null && _objr!=null){//������,����ƫ����
					if(_objl.equals(_objr)){
						isFalse=true;
					}else{
						isFalse=false;
					}
				}
				continue;
			}
			if(_head.equals("Object")){//�����Object myapp a myapp b��������ʽ
				AppClass _bo=myapp.getInstance(_code[1]);
				Object val=_bo.getValue(_code[2]);
				AppClass _co=myapp.getInstance(_code[3]);
				Object[]_obj={val};
				_co.setValue(_code[4], _obj);
				continue;
			}
		}
	}
	}
	catch(Throwable ex){
		String errMsg="\n"+errCode+"\n"+Error.getStackTraceAsString(ex);
		throw new Exception(errMsg,ex);
	}
	return hsRet;
}


}
