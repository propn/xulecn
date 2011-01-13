function movstar(a,time,divName){
	movx=setInterval("mov("+a+","+divName+")",50)
	}
function movover(){
	clearInterval(movx)
	}
function mov(a,scrollMove){
	
	if(a>0)
	eval(scrollMove).doScroll("scrollbarDown");
    else
	eval(scrollMove).doScroll("scrollbarUp");
	}
function o_down(theobject){
object=theobject
	while(object.filters.alpha.opacity>60){
		object.filters.alpha.opacity+=-10}
		}
function o_up(theobject){
object=theobject
	while(object.filters.alpha.opacity<100){
		object.filters.alpha.opacity+=10}
		}
function wback(){
	if(scrollMove.history.length==0){window.history.back()}
	else{scrollMove.history.back()}
	}