/*
@author AyaKoizumi 
@date 091019
@desc ���ڻ���ҳ���attr_code�ľ�̬����ֵ
*/
//vo������
CacheAttrVo=function(){
}
CacheAttrVo.prototype={
cacheAttrId:new Array(),
cacheAttrVal:new Array() 
};

//��������vo�Ķ�����
OperCache=function(){
	this.cacheAttrVo=new CacheAttrVo();
}

OperCache.prototype.setValue=function(pKey,pVal){
	var cacheAttrVo=new CacheAttrVo();//��Ϊ���õ��Ƕ���ľ�̬���ԣ����Կ���������newһ��ʵ��������
	for(var i=0;i<cacheAttrVo.cacheAttrId.length;i++){
		if(cacheAttrVo.cacheAttrId[i]=== pKey){//����ԭ����ֵ
			cacheAttrVo.cacheAttrVal[i]=pVal;
			return;
		}
	}
	cacheAttrVo.cacheAttrId[cacheAttrVo.cacheAttrId.length]=pKey;
	cacheAttrVo.cacheAttrVal[cacheAttrVo.cacheAttrVal.length]=pVal;
},
OperCache.prototype.getValue=function(pKey){
    var cacheAttrVo=new CacheAttrVo();//��Ϊ���õ��Ƕ���ľ�̬���ԣ����Կ���������newһ��ʵ��������
	for(var i=0;i<cacheAttrVo.cacheAttrId.length;i++){
		if(cacheAttrVo.cacheAttrId[i]=== pKey)
		return cacheAttrVo.cacheAttrVal[i];
	}
	return null;
}