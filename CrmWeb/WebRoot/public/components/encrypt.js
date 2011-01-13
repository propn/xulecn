function Encrypt(){
	this.encrypt = function( source ){
		return doEncrypt( source, "T" ) ;
	}
	this.decrypt = function( source ){
		return doEncrypt( source, "F" ) ;
	}
}

function doEncrypt( source, flag ){
	var ajaxCall = new NDAjaxCall( false ) ;
	var encryptRespond = null ;
	
	var callBack = function( reply ){
		encryptRespond = reply.getResult() ;
	}
	
	var encryptRequest = new EncryptRequest() ;
	encryptRequest.flag = flag ;
	if( flag == "T" ){
		encryptRequest.proclaimedBuff = source ;
	}else if ( flag == "F" ){
		encryptRequest.secretBuff = source ;
	}
	ajaxCall.remoteCall("LoginService","encrypt", [encryptRequest],callBack);
	
	if( encryptRespond.success == "0" ){
		if( flag == "T" ){
			alert("����ʧ��:" + encryptRespond.reason );
		}else if( flag == "F" ){
			alert("����ʧ��:" + encryptRespond.reason ) ;
		}
		return "" ;
	}
	return encryptRespond.strResultBuff;
}

function EncryptRequest(){
	this.flag = ""; //���ܻ����"T"����,"F"����,"S"��������Ϊǰ�������ļ���
	this.proclaimedBuff = ""; //���ĵ��ַ���
	this.secretBuff = ""; //�����ַ���
}

function EncryptRespond(){
	this.strMsgNo = "";
	this.success = ""; //������Ϣ�Ƿ�ɹ�: 0 ʧ�� 1 �ɹ�
	this.reason = ""; //ʧ��ԭ��
	this.flag = ""; //ԭʼ���ܻ����"T"����,"F"����,"S"��������Ϊǰ�������ļ���
	this.proclaimedBuff = ""; //ԭʼ���ĵ��ַ���
	this.secretBuff = ""; //ԭʼ�����ַ���
	this.strResultBuff = ""; //����ַ���
}