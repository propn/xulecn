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
			alert("加密失败:" + encryptRespond.reason );
		}else if( flag == "F" ){
			alert("解密失败:" + encryptRespond.reason ) ;
		}
		return "" ;
	}
	return encryptRespond.strResultBuff;
}

function EncryptRequest(){
	this.flag = ""; //加密或解密"T"加密,"F"解密,"S"将密文作为前导和明文加密
	this.proclaimedBuff = ""; //明文的字符串
	this.secretBuff = ""; //密文字符串
}

function EncryptRespond(){
	this.strMsgNo = "";
	this.success = ""; //返回信息是否成功: 0 失败 1 成功
	this.reason = ""; //失败原因
	this.flag = ""; //原始加密或解密"T"加密,"F"解密,"S"将密文作为前导和明文加密
	this.proclaimedBuff = ""; //原始明文的字符串
	this.secretBuff = ""; //原始密文字符串
	this.strResultBuff = ""; //结果字符串
}