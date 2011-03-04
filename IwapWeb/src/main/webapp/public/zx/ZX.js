window.undefined = window.undefined;
SystemContext = {
	appName :  'CrmWeb' ,
	appContextPath : '/CrmWeb' ,
	appWebRoot :  'CrmWeb\\CrmWeb\\' ,
	appBuffaloServlet : '/CrmWeb/nfapp' 
}
ZX = { //ZX是一个json对象
	version : '2.0'
};
ZX.apply = function(o, c, defaults) { // 静态的继承,不是实例的继承
	// no "this" reference for friendly out of scope calls
	if (defaults) {
		ZX.apply(o, defaults);
	}
	if (o && c && typeof c == 'object') {
		for (var p in c) { // 字符串的false转换为boolean
			if (c[p] == "false") {
				c[p] = false;
			}
			if (c[p] == "true") {
				c[p] = true;
			}
			o[p] = c[p];
		}
	}
	return o;
};
(function() {
	var idSeed = 0, toString = Object.prototype.toString, DOC = document;
	ZX.apply(ZX, {

				applyIf : function(o, c) {
					if (o) {
						for (var p in c) {
							if (ZX.isEmpty(o[p])) {
								o[p] = c[p];
							}
						}
					}
					return o;
				},
				id : function(el, prefix) {
					return (el = ZX.getDom(el) || {}).id = el.id
							|| (prefix || "ZX-gen") + (++idSeed);
				},
				extend : function() { // 对象的继承
					var io = function(o) {
						for (var m in o) {
							this[m] = o[m];
						}
					};
					var oc = Object.prototype.constructor;
					return function(sb, sp, overrides) {
						if (ZX.isObject(sp)) { // 判断sp是否为json对象,此处为function
							overrides = sp;
							sp = sb;
							sb = overrides.constructor != oc
									? overrides.constructor
									: function() {
										sp.apply(this, arguments);
									};
						}
						var F = function() {
						}, sbp, spp = sp.prototype; // sp原型对象

						F.prototype = spp;// F 的原型指向spp, 继续spp的所有的函数
						sbp = sb.prototype = new F();// sb的原型指向 f的实例
						sbp.constructor = sb;
						sb.superclass = spp; // 父类为sp
						if (spp.constructor == oc) {
							spp.constructor = sp; // 父类的构造函数为sp sb子类指向父类
						}
						sb.override = function(o) {
							ZX.override(sb, o);
						};
						sbp.superclass = sbp.supr = (function() {// sbp的父类为sp
							return spp;
						});
						sbp.override = io; // 实现override方法
						ZX.override(sb, overrides); // 把overrides中的属性拷贝到目标sb中
						sb.extend = function(o) {// sb实现extend方法
							ZX.extend(sb, o);
						};
						return sb;
					};
				}(),

				override : function(origclass, overrides) {
					if (overrides) {
						var p = origclass.prototype;
						ZX.apply(p, overrides);
						if (ZX.isIE && overrides.toString != origclass.toString) {
							p.toString = overrides.toString;
						}
					}
				},
				namespace : function() {
					var o, d;
					ZX.each(arguments, function(v) {
								d = v.split(".");
								o = window[d[0]] = window[d[0]] || {};
								ZX.each(d.slice(1), function(v2) {
											o = o[v2] = o[v2] || {};
										});
							});
					return o;
				},
				urlAppend : function(url, s) {
					if (!ZX.isEmpty(s)) {
						return url + (url.indexOf('?') === -1 ? '?' : '&') + s;
					}
					return url;
				},
				each : function(array, fn, scope) {

					if (ZX.isEmpty(array, true)) {
						return;
					}
					if (ZX.isPrimitive(array)) {
						array = [array];
					}

					for (var i = 0, len = array.length; i < len; i++) {
						if (fn.call(scope || array[i], array[i], i, array) === false) {
							return i;
						};
					}
				},
				getDom : function(el) {
					if (!el || !DOC) {
						return null;
					}
					return el.dom ? el.dom : (ZX.isString(el) ? DOC
							.getElementById(el) : el);
				},
				getBody : function() {
					return ZX.get(DOC.body || DOC.documentElement);
				},
				isEmpty : function(v, allowBlank) {
					return v === null || v === undefined
							|| ((ZX.isArray(v) && !v.length))
							|| (!allowBlank ? v === '' : false);
				},
				isArray : function(v) {
					return toString.apply(v) === '[object Array]';
				},
				isObject : function(v) {
					return v && typeof v == "object";
				},
				isPrimitive : function(v) {
					return ZX.isString(v) || ZX.isNumber(v) || ZX.isBoolean(v);
				},
				isFunction : function(v) {
					return toString.apply(v) === '[object Function]';
				},
				isNumber : function(v) {
					return typeof v === 'number' && isFinite(v);
				},
				isString : function(v) {
					return typeof v === 'string';
				},
				isBoolean : function(v) {
					return typeof v === 'boolean';
				},
				isDefined : function(v) {
					return typeof v !== 'undefined';
				}
			});
	ZX.ns = ZX.namespace;
})();
ZX.apply(Function.prototype, {

			createInterceptor : function(fcn, scope) {
				var method = this;
				return !ZX.isFunction(fcn) ? this : function() {
					var me = this, args = arguments;
					fcn.target = me;
					fcn.method = method;
					return (fcn.apply(scope || me || window, args) !== false)
							? method.apply(me || window, args)
							: null;
				};
			},

			createCallback : function(/* args... */) {
				var args = arguments, method = this;
				return function() {
					return method.apply(window, args);
				};
			},

			createDelegate : function(obj, args, appendArgs) {
				var method = this;
				return function() {
					var callArgs = args || arguments;
					if (appendArgs === true) {
						callArgs = Array.prototype.slice.call(arguments, 0);
						callArgs = callArgs.concat(args);
					} else if (ZX.isNumber(appendArgs)) {
						callArgs = Array.prototype.slice.call(arguments, 0); // copy
						// arguments
						// first
						var applyArgs = [appendArgs, 0].concat(args); // create
						// method
						// call params
						Array.prototype.splice.apply(callArgs, applyArgs); // splice
						// them in
					}
					return method.apply(obj || window, callArgs);
				};
			},
			defer : function(millis, obj, args, appendArgs) {
				var fn = this.createDelegate(obj, args, appendArgs);
				if (millis > 0) {
					return setTimeout(fn, millis);
				}
				fn();
				return 0;
			}
		});
		//将format作为String对象的静态方法
ZX.applyIf(String, { //用法 :alert(String.format("name is {0} and age is {1}", "John", 12));
			format : function(format) {
			var args = Array.prototype.slice.call(arguments, 1); //args为除去第一个参数的新的数组 
				return format.replace(/\{(\d+)\}/g, function(m, i) {  // format("name is {0} and age is {1}", "John", 12); format =第一二参数
							return args[i];
						});
			}
		});
		//将indexOf 作为new Array()对象的实例方法
ZX.applyIf(Array.prototype, {
			indexOf : function(o) { //数组对象中是否存在另外一个对象 与String.indexOf不同
				for (var i = 0, len = this.length; i < len; i++) {
					if (this[i] == o) {
						return i;
					}
				}
				return -1;
			},
			remove : function(o) {
				var index = this.indexOf(o);
				if (index != -1) {
					this.splice(index, 1); // 删除某一个元素
				}
				return this;
			}

		});

ZX.genByHtml = function() {
	var heads = document.getElementsByTagName("head");
	var head;
	if (heads.length == 0) {
		head = document.createElement('head');
		document.body.appendChild(head);
	} else {
		head = heads[0];
	}
	var beans = document.getElementsByTagName('dl');
	var script = document.createElement("script");
	script.id = 'beansScript';
	for (var i = 0; i < beans.length; i++) {
		//var bean = new ZX.Bean(beans[i]);
		script.text = 'var ' + beans[i].id + ' ;';
	}
	head.appendChild(script);
	for (var i = 0; i < beans.length; i++) {
		var beanId = beans[i].id;
		window[beanId] = new ZX.Bean(beans[i]);
		var bean = window[beanId];
		var childNodes = beans[i].childNodes;
		for (var j = 0; j < childNodes.length; j++) {
			var field = new ZX.Bean.Field(childNodes[j]);
			var actionChildNodes = childNodes[j].childNodes;
			for (var k = 0; k < actionChildNodes.length; k++) {
				var action = new ZX.Action(actionChildNodes[k]);
				field.addAction(action);
			}
			field.registerEvents();
			bean.add(field);
		}
	}
	
	ZX.attachSpecEvent();	
};

ZX.attachSpecEvent=function(oPane){
	//绑定特殊事件
	oPane = oPane || document;
	var inputs = oPane.all.tags('input');
	//var inputs = document.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		var input = inputs[i];
		
		if(input.type == "button" && input.className == "newbutton"){
			input.attachEvent("onmouseover",function(){
												var ele = event.srcElement;
												ele.style.backgroundImage="url("+SystemContext.appContextPath+"/business/core/style/images/shade1.png)";
											}) 
			input.attachEvent("onmouseout",function(){
											var ele = event.srcElement;
											ele.style.backgroundImage="url("+SystemContext.appContextPath+"/business/core/style/images/shade.png)";
										}) 
		}
		
		if((input.type == "text"||input.type == "password") && (input.className == "floattxtctrl" || input.className == "datetxtctrl")){
		
			input.attachEvent("onkeypress",function(){return false;}) 
		}
	}
};

ZX.genByObject = function(beans) {
	var heads = document.getElementsByTagName("head");
	var head;
	if (heads.length == 0) {
		head = document.createElement('head');
		document.body.appendChild(head);
	} else {
		head = heads[0];
	}
	var script = document.createElement("script");
	script.id = 'objectsScript';
	for (var i = 0; i < beans.length; i++) {
		//var bean = new ZX.Bean(beans[i]);
		script.text = 'var ' + beans[i].id + ' ;';
	}
	head.appendChild(script);
	for (var i = 0; i < beans.length; i++) {
		var beanId = beans[i].id;
		window[beanId] = new ZX.Bean(beans[i]);
		var bean = window[beanId];
		var childNodes = beans[i].fields;
		for (var j = 0; j < childNodes.length; j++) {
			var field = new ZX.Bean.Field(childNodes[j]);
			/*
			 * var actionChildNodes = childNodes[j].childNodes; for (var k = 0;
			 * k < actionChildNodes.length; k++) { var action = new
			 * ZX.Action(actionChildNodes[k]); field.addAction(action); }
			 * field.registerEvents();
			 */
			bean.add(field);
		}
	}
};
var js_path_prefix = SystemContext.appContextPath+'/'
ZX._import = function(type, path, className) {

	if (type == "js")
		document.write('<script language="JavaScript" src="' + js_path_prefix
				+ path + '" charset="GBK"></script>');
	else if (type == "css")
		document.write('<link type="text/css" rel="stylesheet" href="'
				+ js_path_prefix + path + '" charset="GBK"/>');
	else if (type == "behavior") {
		document.write('<style>' + className + '{behavior:url('
				+ js_path_prefix + path + '); }</style>');
	} else if (type == "namespace") {
		document.write('<?import namespace="' + className
				+ '" implementation="' + js_path_prefix + path + '" />');
	} else if (type == "text") {
		document.write('<script language="JavaScript">' + path + '</script>');
	}

};
ZX.importLib = function(lib) {
	switch (lib) {
		case "kernel" : {

			ZX._import("js", "public/components/environment.js");
			ZX._import("js", "public/zx/Array.js");
			ZX._import("js", "public/zx/Event.js");
			ZX._import("js", "public/zx/Basis.js");
			ZX._import("js", "public/zx/Element.js");
			ZX._import("js", "public/zx/Bean.js");
			ZX._import("js", "public/zx/Field.js");
			ZX._import("js", "public/zx/Action.js");
			ZX._import("js", "public/zx/VTypes.js");
			ZX._import("js", "public/zx/Proto.js");
			ZX._import("js", "public/zx/Ajax.js");
			ZX._import("js", "public/components/messageBox/messageBox.js");
			ZX._import("js", "public/components/messageBox/dialog.js");
			ZX._import("js", "public/components/html.js");
			ZX._import("js", "public/components/select.js");
			ZX._import("js", "public/components/jquery-1.4.1.min.js");
			// ZX._import("text", "jQ = jQuery.noConflict();");
			// jQ = jQuery.noConflict();

			break;
		}
		case "window" : {
			ZX._import("css",
					"public/widgets/window/codebase/dhtmlxwindows.css");
			ZX
					._import("css",
							"public/widgets/window/codebase/skins/dhtmlxwindows_dhx_skyblue.css");
			ZX._import("js", "public/widgets/window/codebase/dhtmlxcommon.js");
			ZX._import("js", "public/widgets/window/codebase/dhtmlxwindows.js");
			ZX._import("js",
					"public/widgets/window/codebase/dhtmlxcontainer.js");
			break;
		}
		case "tree" : {
			ZX._import("js", "public/components/environment.js");
			ZX._import("css", "public/widgets/rightmenu/Themes/Default/contextmenu.css");
			ZX._import("css", "public/widgets/tree/codebase/dhtmlxtree.css");
			ZX._import("js", "public/widgets/tree/codebase/dhtmlxcommon.js");
			ZX._import("js", "public/widgets/tree/codebase/dhtmlxtree.js");
			ZX._import("js", "public/zx/Tree.js");
			ZX._import("js", "public/widgets/rightmenu/Javascripts/Plugins/jquery.contextmenu.js");
			ZX._import("js", "public/widgets/rightmenu/Javascripts/contextmenu.js");
			break;
		}
		case "date" : {
			ZX._import("js", "public/widgets/date/date.js");
			break;
		}
	}
};
(function() {
	ZX.importLib('kernel');
	var metas = document.all.tags("META");
	for (var i = 0; i < metas.length; i++) {
		if (metas[i].httpEquiv == "library") {
			var libraries = metas[i].content.split(",");
			for (var i = 0; i < libraries.length; i++) {
				if (libraries[i] != "kernel") {
					ZX.importLib(libraries[i]);
				}
			}
			break;
		}
	}

	// 系统功能菜单的特殊处理
	document.attachEvent("onclick", function() {
				
				if(!event.srcElement)
				   return;
				if(event.srcElement.className =="clickNullClass")
					return;
				var currNode  = event.srcElement;
				
				while (currNode && !(currNode.className && currNode.className.indexOf('hideNullClass')>-1)){
						
					currNode = currNode.parentElement;
				}
				if(currNode && currNode.className.indexOf("hideNullClass")>-1)
					return;
				if (top.mainFrame && top.mainFrame.document) {
					var divs = top.mainFrame.document
							.getElementsByTagName('div');
					for (var i = 0; i < divs.length; i++) {
						var className = divs[i].className;
						if (className == 'b-m-mpanel' || className.indexOf('hideNullClass')>-1) {
							if (divs[i].style.display != 'none')
								divs[i].style.display = 'none';
						}
					}
				}
		
			});

})();


function openDialog(url, arg, sizeConfig, dialogWidth, dialogHeight) {
	var sizeConfigResult = "";
	if (!sizeConfig || sizeConfig == "") {
		sizeConfigResult = "dialogWidth:" + dialogWidth + "px;dialogHeight:"
				+ dialogHeight + "px;";
	} else {
		var __config = eval("__" + sizeConfig);
		sizeConfigResult = "dialogWidth:" + __config[0] + "px;dialogHeight:"
				+ __config[1] + "px;";
	}
	return window.showModalDialog(url, arg, "status:no;help:no;scroll:no;"
					+ sizeConfigResult);
}
