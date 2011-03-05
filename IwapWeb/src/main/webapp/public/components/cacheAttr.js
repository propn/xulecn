/*
@author AyaKoizumi 
@date 091019
@desc 用于缓存页面的attr_code的静态数据值
*/
//vo数据类
CacheAttrVo=function(){
}
CacheAttrVo.prototype={
cacheAttrId:new Array(),
cacheAttrVal:new Array() 
};

//操作上面vo的动作类
OperCache=function(){
	this.cacheAttrVo=new CacheAttrVo();
}

OperCache.prototype.setValue=function(pKey,pVal){
	var cacheAttrVo=new CacheAttrVo();//因为调用的是对象的静态属性，所以可以在这里new一个实例来访问
	for(var i=0;i<cacheAttrVo.cacheAttrId.length;i++){
		if(cacheAttrVo.cacheAttrId[i]=== pKey){//覆盖原来的值
			cacheAttrVo.cacheAttrVal[i]=pVal;
			return;
		}
	}
	cacheAttrVo.cacheAttrId[cacheAttrVo.cacheAttrId.length]=pKey;
	cacheAttrVo.cacheAttrVal[cacheAttrVo.cacheAttrVal.length]=pVal;
},
OperCache.prototype.getValue=function(pKey){
    var cacheAttrVo=new CacheAttrVo();//因为调用的是对象的静态属性，所以可以在这里new一个实例来访问
	for(var i=0;i<cacheAttrVo.cacheAttrId.length;i++){
		if(cacheAttrVo.cacheAttrId[i]=== pKey)
		return cacheAttrVo.cacheAttrVal[i];
	}
	return null;
}