

(function($){
	if(!$) return;
	
	
	/**
	 * @class woo
	 * WooUI组件的工具集合类
	 * @singleton
	 */
	$.woo = $.woo || {version:"@VERSION"};
	
	$.extend($.woo,{
		
		/**
		 * 自动解析html，详见{@link parse}
		 * @type Boolean
		 */
		autoParse : true,
		
		/**
		 * wooUI的组件，每个组件初始化时将自身的名称压入这个数组
		 */
		components : [],
		
		/**
		 * 在指定上下文中解析html结构，如果发现某个DOM节点有css样式类 'wooui-'+组件名 ，且没有属性autoParse='false'，
		 * 则用组件名对应的组件去渲染
		 * 
		 */
		parse : function(context){
			if ($.woo.autoParse){
				for(var i=0; i<$.woo.components.length; i++){
					(function(){
						var comp = $.woo.components[i];
						var dom = $('.wooui-' + comp, context);
						if (dom.length && !(dom.attr('autoParse') != 'false')){
							if (dom[comp]){
								dom[comp]();
							}
						}
					})();
				}
			}
		},
		
		/**
		 *  当传入的参数是一个javascript数组，则返回true，否则返回false
		 * 
		 * @param {Object}
		 * @return {Boolean}
		 */
		isArray : function(v) {
			return v && typeof v.length == 'number'
					&& typeof v.splice == 'function';
		},
		
		/**
		 *  当传入的参数是一个javascript日期对象，则返回true，否则返回false
		 * 
		 * @param {Date}
		 * @return {Boolean}
		 */
		isDate : function(v){
			return v && typeof v.getFullYear == 'function';
		},
		
		/**
         *  当传入的参数是一个javascript对象，则返回true，否则返回false
         * @param {Object} object 
         * @return {Boolean}
         */
        isObject : function(v){
            return v && typeof v == 'object';
        },

        /**
         *  当传入的参数是一个javascript基本数据类型（包括String/Number/Boolean），则返回true，否则返回false
         * @param {Mixed} value
         * @return {Boolean}
         */
        isPrimitive : function(v){
            return $.woo.isString(v) || $.woo.isNumber(v) || $.woo.isBoolean(v);
        },

		/**
		 *  当传入的参数是一个javascript函数，则返回true，否则返回false
		 * @param {Object}
		 *            fn
		 * @return {Boolean}
		 */
		isFunction : function(fn) {
			return !!fn && typeof fn != 'string' && !fn.nodeName
					&& fn.constructor != Array
					&& /^[\s[]?function/.test(fn + '');
		},
		
		/**
         *  当传入的参数是一个javascript数字，则返回true，否则返回false
         * @param {Object} v
         * @return {Boolean}
         */
        isNumber: function(v){
            return typeof v === 'number' && isFinite(v);
        },

        /**
         *  当传入的参数是一个javascript字符串，则返回true，否则返回false
         * @param {Object} v
         * @return {Boolean}
         */
        isString: function(v){
            return typeof v === 'string';
        },

        /**
         *  当传入的参数是一个javascript 布尔值，则返回true，否则返回false
         * @param {Object} v The object to test
         * @return {Boolean}
         */
        isBoolean: function(v){
            return typeof v === 'boolean';
        },

        /**
         *  当传入的参数不是一个javascript undefined，则返回true，否则返回false
         * @param {Object} v The object to test
         * @return {Boolean}
         */
        isDefined: function(v){
            return typeof v !== 'undefined';
        },
        
        /**
         *  当传入的参数不是一个javascript undefined,null或者空的字符串，则返回true，否则返回false
         * @param {Mixed} value 
         * @param {Boolean} allowBlank (可选的) 是否把空字符串当做空
         * @return {Boolean}
         */
        isEmpty : function(v, allowBlank){
            return v === null || v === undefined || (!allowBlank ? v === '' : false);
        },

		/**
		 *  返回传入参数的类型 类型包括:
		 * <ul>
		 * <li><b>string</b>: 参数是一个字符串</li>
		 * <li><b>number</b>: 参数是一个字符串</li>
		 * <li><b>boolean</b>: 参数是一个布尔值</li>
		 * <li><b>function</b>: 参数是一个函数引用</li>
		 * <li><b>object</b>: 参数是一个对象</li>
		 * <li><b>array</b>: 参数是一个数组</li>
		 * <li><b>regexp</b>: 参数是一个正则表达式对象</li>
		 * <li><b>undefined</b>: 参数是undefined</li>
		 * <li><b>null</b>: 参数是一个null值</li>
		 * <li><b>element</b>: 参数是一个DOM元素</li>
		 * <li><b>nodelist</b>: 参数是一个DOM元素列表</li>
		 * <li><b>textnode</b>: 参数一个非空文本节点</li>
		 * <li><b>whitespace</b>: 参数是一个空白文本节点</li>
		 * </ul>
		 * @param {Mixed}
		 *            object
		 * @return {String}
		 */
		type : function(o) {
			if (o === undefined) {
				return 'undefined';
			}
			if (o === null) {
				return 'null';
			}
			if (o.htmlElement) {
				return 'element';
			}
			var t = typeof o;
			if (t == 'object' && o.nodeName) {
				switch (o.nodeType) {
					case 1 :
						return 'element';
					case 3 :
						return (/\S/).test(o.nodeValue)
								? 'textnode'
								: 'whitespace';
				}
			}
			if (t == 'object' || t == 'function') {
				switch (o.constructor) {
					case Array :
						return 'array';
					case RegExp :
						return 'regexp';
				}
				if (typeof o.length == 'number' && typeof o.item == 'function') {
					return 'nodelist';
				}
			}
			return t;
		},
		/**
         *  将一个对象转变为一个url的参数字符串。
         * @param {Object} o
         * @param {String} pre 前缀
         * @return {String}
         */
        urlEncode: function(o, pre){
            var undef, buf = [], key, e = encodeURIComponent;

            for(key in o){
                undef = !$.woo.isDefined(o[key]);
                $.woo.each(undef ? key : o[key], function(val, i){
                    buf.push('&', e(key), '=', (val != key || !undef) ? e(val) : '');
                });
            }
            if(!pre){
                buf.shift();
                pre = '';
            }
            return pre + buf.join('');
        },

        /**
         *  将一个url的参数字符串转变为一个对象。
         * @param {String} string
         * @param {Boolean} overwrite (可选的) 同名参数用一个数组代替。默认为false。
         * @return {Object} 
         */
        urlDecode : function(string, overwrite){
            if(!string || !string.length){
                return {};
            }
            var obj = {};
            var pairs = string.split('&');
            var pair, name, value;
            for(var i = 0, len = pairs.length; i < len; i++){
            	var pos = pairs[i].indexOf('=');
            	if(pos <= 0){
            		continue;
            	}
            	name = decodeURIComponent(pairs[i].slice(0,pos));
            	value = decodeURIComponent(pairs[i].slice(pos+1));
                /*pair = pairs[i].split('=');
                name = decodeURIComponent(pair[0]);
                value = decodeURIComponent(pair[1]);*/
                if(overwrite !== true){
                    if(typeof obj[name] == 'undefined'){
                        obj[name] = value;
                    }else if(typeof obj[name] == 'string'){
                        obj[name] = [obj[name]];
                        obj[name].push(value);
                    }else{
                        obj[name].push(value);
                    }
                }else{
                    obj[name] = value;
                }
            }
            return obj;
        },
        
        /**
         *  在一个url后面追加一个查询字符串
         * @param {String} url 
         * @param {String} s
         * @return (String) 
         */
        urlAppend : function(url, s){
            if(!$.woo.isEmpty(s)){
                return url + (url.indexOf('?') === -1 ? '?' : '&') + s;
            }
            return url;
        },
        
        /**
         *  遍历数组并对每一个数组项执行一个函数
         * @param {Array} array 要遍历的数组
         * @param {Function} fn 遍历回调函数，回调函数通过下面参数来调用：
         * <ul>
         * <li>item {Mixed} : 数组中的第index个item</li>
         * <li>index{Number} : 数组中item的索引</li>
         * <li>array {Array} : 被遍历的数组</li>
         * </ul>
         * @param {Object} scope 回调函数执行的作用域
         * 
         */
		each : function(array, fn, scope){
            if(typeof array.length == 'undefined' || typeof array == 'string'){
                array = [array];
            }
            for(var i = 0, len = array.length; i < len; i++){
                if(fn.call(scope || array[i], array[i], i, array) === false){ return i; };
            }
        },
        
        /**
         * 将一个可迭代的值（有数字索引和length属性）转化为一个真正的数组。
         * <p>注意，不要用于字符串，因为IE不支持"abc"[0]这样的写法。如果要将字符串转化为数组，可以使用"abc".match(/./g) ===> [a,b,c]</p>
         * @param {Iterable} 要转化为数组的可迭代对象
         * @return (Array) 数组
         */
        toArray : function(){
        	//TODO !$.support.htmlSerialize用于判断是ie浏览器，这个判断不合理，以后要改
            return !$.support.htmlSerialize ?
                function(a, i, j, res){
                    res = [];
                    $.woo.each(a, function(v) {
                        res.push(v);
                    });
                    return res.slice(i || 0, j || res.length);
                } :
                function(a, i, j){
                    return Array.prototype.slice.call(a, i || 0, j || a.length);
                }
        }(),
        /**
         *  遍历一个数组或迭代一个对象的属性，并执行一个函数。
         * <b>Note</b>: 如果确定要遍历一个数组，最好使用each方法。
         * @param {Object/Array} object
         * @param {Function} fn 
         * 如果函数返回false，则迭代将会停止。
         */
        iterate : function(obj, fn, scope){
            if(isIterable(obj)){
                $.woo.each(obj, fn, scope);
                return;
            }else if($.woo.isObject(obj)){
                for(var prop in obj){
                    if(obj.hasOwnProperty(prop)){
                        if(fn.call(scope || obj, prop, obj[prop]) === false){
                            return;
                        };
                    }
                }
            }
        },
        /**
         *  验证一个值是否数字，如果不是，则返回一个默认值。
         * @param {Mixed} value 应该是一个数字，但是任何类型都可以传入
         * @param {Number} defaultValue 原始值如果不是数字，则返回这个默认值
         * @return {Number} 返回 Value 或 defaultValue
         */
        num : function(v, defaultValue){
            v = Number(v === null || typeof v == 'boolean'? NaN : v);
            return isNaN(v)? defaultValue : v;
        },

        
        value : function(v, defaultValue, allowBlank){
            return $.woo.isEmpty(v, allowBlank) ? defaultValue : v;
        },

        /**
         *  将一个正则表达式中的已定义符号转义
         * @param {String} str
         * @return {String}
         */
        escapeRe : function(s) {
            return s.replace(/([.*+?^${}()|[\]\/\\])/g, '\\$1');
        },
        /**
         *  创建一个数组备份，并且将其中的空值清除掉，并将新的数组返回。该方法不会改变原始数组。
         * @param {Array/NodeList} arr 需要清除的数组
         * @return {Array} 新的，去掉空值的数组
         */
        clean : function(arr){
            var ret = [];
            $.woo.each(arr, function(v){
                if(!!v){
                    ret.push(v);
                }
            });
            return ret;
        },

        /**
         *  创建一个数组的备份，并且将重复的值过滤掉。该方法不会改变原始数组。
         * @param {Array} arr 需要过滤的值
         * @return {Array} 过滤后的不包含重复值的数组
         */
        unique : function(arr){
            var ret = [],
                collect = {};

            $.woo.each(arr, function(v) {
                if(!collect[v]){
                    ret.push(v);
                }
                collect[v] = true;
            });
            return ret;
        },

        /**
         *  将一个多维数组转变为一个一维数组，并返回。该方法不会改变原始数组。
         * @param {Array} arr 多维数组
         * @return {Array} 新的
         */
        flatten : function(arr){
            var worker = [];
            function rFlatten(a) {
                $.woo.each(a, function(v) {
                    if($.woo.isArray(v)){
                        rFlatten(v);
                    }else{
                        worker.push(v);
                    }
                });
                return worker;
            }
            return rFlatten(arr);
        },

        /**
         *  返回数组中的最小值
         * @param {Array|NodeList} arr The Array from which to select the minimum value.
         * @param {Function} comp (optional) a function to perform the comparision which determines minimization.
         *                   If omitted the '<' operator will be used. Note: gt = 1; eq = 0; lt = -1
         * @return {Object} The minimum value in the Array.
         */
        min : function(arr, comp){
            var ret = arr[0];
            comp = comp || function(a,b){ return a < b ? -1 : 1; };
            $.woo.each(arr, function(v) {
                ret = comp(ret, v) == -1 ? ret : v;
            });
            return ret;
        },

        /**
         *  返回数组中的最大值
         * @param {Array|NodeList} arr 
         * @param {Function} comp (可选的) 自定义的比较器。默认为 “>”比较。
         *                  
         * @return {Object} 数组中的最大值
         */
        max : function(arr, comp){
            var ret = arr[0];
            comp = comp || function(a,b){ return a > b ? 1 : -1; };
            $.woo.each(arr, function(v) {
                ret = comp(ret, v) == 1 ? ret : v;
            });
            return ret;
        },

        /**
         *  计算数组中的平均值
         * @param {Array} arr
         * @return {Number}
         */
        mean : function(arr){
           return $.woo.sum(arr) / arr.length;
        },

        /**
         *  计算数组中所有项的总和
         * @param {Array} arr
         * @return {Number}
         */
        sum : function(arr){
           var ret = 0;
           $.woo.each(arr, function(v) {
               ret += v;
           });
           return ret;
        },

        /**
         * 将一个集合按照true/false的值类型进行区分，得到两个集合
         * <pre><code>
// 例子 1:
TOONE.partition([true, false, true, true, false]); // [[true, true, true], [false, false]]

// 例子 2:
TOONE.partition(
    TOONE.query('p'),
    function(val){
        return val.className == 'class1'
    }
);
// 如果p元素的css样式类为 'class1'则为true，
// 如果p元素没有名字为'class1'的css样式类，则为false
         * </code></pre>
         * @param {Array|NodeList} arr 需要区分的集合。
         * @param {Function} truth （可选的） 一个用来确定 true or false 的函数。如果省略该函数，则集合中的值本身必须可以进行true/false判断。
         * @return {Array} [true<Array>,false<Array>]
         */
        partition : function(arr, truth){
            var ret = [[],[]];
            $.woo.each(arr, function(v, i, a) {
                ret[ (truth && truth(v, i, a)) || (!truth && v) ? 0 : 1].push(v);
            });
            return ret;
        },

        /**
         * 在数组的每一项上调用同一个方法。
         * <pre><code>
// 例子：
TOONE.invoke(TOONE.query('p'), 'getAttribute', 'id');
// [el1.getAttribute('id'), el2.getAttribute('id'), ..., elN.getAttribute('id')]
         * </code></pre>
         * @param {Array|NodeList} arr 需要执行方法的数组
         * @param {String} methodName  执行的方法名
         * @param {Anything} ... 方法被调用时传递的参数
         * @return {Array} 最终的方法调用后得到的结果。
         */
        invoke : function(arr, methodName){
            var ret = [],
                args = Array.prototype.slice.call(arguments, 2);
            $.woo.each(arr, function(v,i) {
                if (v && typeof v[methodName] == 'function') {
                    ret.push(v[methodName].apply(v, args));
                } else {
                    ret.push(undefined);
                }
            });
            return ret;
        },

        /**
         * 获取每一个数组项的某个属性值，组成新的数组，并返回。
         * <pre><code>
// 例子：
TOONE.pluck(TOONE.query('p'), 'className'); // [el1.className, el2.className, ..., elN.className]
         * </code></pre>
         * @param {Array|NodeList} arr 需要获取值的对象组成的数组
         * @param {String} prop 属性名
         * @return {Array} 
         */
        pluck : function(arr, prop){
            var ret = [];
            $.woo.each(arr, function(v) {
                ret.push( v[prop] );
            });
            return ret;
        },
        
        /**
         *  创建并返回一个 'version 4' RFC-4122 UUID 字符串
         * @param {Array} arr
         * @return {Number}
         */
        createUUID: function(){
			var s = [], itoh = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'];
			for (var i = 0; i < 36; i++) s[i] = Math.floor(Math.random()*0x10);
			s[14] = 4;
			s[19] = (s[19] & 0x3) | 0x8;
			for (var i = 0; i < 36; i++) s[i] = itoh[s[i]];
			s[8] = s[13] = s[18] = s[23] = '-';
			return s.join('');
        },
        
        /**
         *  将数字按照给定的格式进行格式化。格式化模板匹配/^(#*,)?(#*(#|0))\.(#*)$/，例如以下格式：
         * 
         * <li>##.##</li> //如果该值为零，则不显示，返回一个空格的unicode编码 “&#160;”
		 * <li>##.#####</li>
		 * <li>##</li>只显示四舍五入的整数
         * <li>#0.##</li> //这个模板的意思是：如果该值为零，则强制显示，而不是转为空
         * <li>###,###.##</li>
         * <li>###,###</li>分段的整数
         * <li>###,##0.##</li>
         * 
         * <pre><code>
			// 例子：
			$.woo.numberFormat(2134.5685,'##.##'); // 2134.57
			$.woo.numberFormat(2134.5685,'##.###'); // 2134.569
			$.woo.numberFormat(0.00000,'##.##'); // &#160;
			$.woo.numberFormat('000.00000','##.##'); // &#160;
			$.woo.numberFormat(0.00000,'#0.##'); // 0.00
			$.woo.numberFormat(2342134.5685,'###,###.###'); // 2,342,134.57
         * </code></pre>
         * 
         * @param {Number/String} value 需要格式化的值
         * @param {String} pattern 格式化模板
         * @return {Number} 格式化后的值
         */
        numberFormat : function(value, pattern) {
			var re = /^(#*,)?(#*(#|0))\.(#*)$/;
			var re2 = /^\s+|\s+$/g;
			
			var t = pattern.replace(re2,'');
			
			var result = re.exec(t);
			
			var len = result[4].length;
			
			var tail = '.' + String(Math.pow(10,len)).slice(1);
			
			var ps,whole,sub,suf;
			
			if(result[1] == ''){
				if(Number(value) == 0){
					result[3]== '0' ? value = '0' + tail : value = '&#160;';
				}else{
					value = Math.round(value*Math.pow(10,len))/Math.pow(10,len);
					value = String(value);
					ps = value.split('.');
					suf = String(Math.pow(10,len - (ps[1] ? ps[1].length : len))).slice(1);
					whole = ps[0];
					sub = ps[1] ? '.'+ ps[1] + suf : tail;
					value = whole + sub;
				}
			}else{
				if(Number(value) == 0){
					result[3]== '0' ? value = '0' + tail : value = '&#160;';
				}else{
					
					value = Math.round(value*Math.pow(10,len))/Math.pow(10,len);
					if((result[1].length - 1) ==  result[2].length){
						value = String(value);
						ps = value.split('.');
						suf = String(Math.pow(10,len - (ps[1] ? ps[1].length : len))).slice(1);
						whole = ps[0];
						sub = ps[1] ? '.'+ ps[1] +suf : tail;
						var r = new RegExp('(\\d+)(\\d{'+result[2].length+'})');
						while (r.test(whole)) {
							whole = whole.replace(r, '$1' + ',' + '$2');
						}
						value = whole + sub;
					}
				}
			}
			return value;
		},
		
		/**
		 * @description 克隆一个引用数据类型{Object/Array}的值，如果需要深度克隆，则第二个参数传入true
		 * @param {Object/Array} value 需要克隆的原始值
		 * @param {Boolean} deep 是否深度克隆，默认为false
		 * @return {Object/Array} 
		 */
		clone: function(value, deep){
			deep = deep != 'undefined' ? deep : false;
			
			if(this.isArray(value)){
				var aa = Array.prototype.slice.call(value,0);
				if(deep){
					for(var i = 0, l = aa.length;i < l; i++){
						var copy = aa[i];
						if(copy && ($.woo.isArray(copy) || $.woo.isObject(copy))){
							aa[i] = $.woo.clone(copy,deep);
						}
					}
				}
				return aa;
			}
			
			var ret = {};
			for ( var name in value ) {
				var src = ret[ name ], copy = value[ name ];

				// 预防无限循环
				if ( ret === copy )
					continue;

				// 如果是深度拷贝，则递归复制
				if ( deep && copy && ($.woo.isArray(copy) || $.woo.isObject(copy)) )
					ret[ name ] = $.woo.clone(copy, deep);

				// 排除掉 undefined 值
				else if ( copy !== undefined )
					ret[ name ] = copy;
			}
		
			return ret;
		},
		
		/**
		 * 获取指定目标的某个样式的值，如果值不为数字，例如auto之类的，则返回0
		 */
		getStyleValue : function(target,css){
			var val = parseInt($(target).css(css));
				if (isNaN(val)) {
					return 0;
				} else {
					return val;
				}
		},
		
		// $.ui.plugin is deprecated.  Use the proxy pattern instead.
	plugin: {
		add: function(module, option, set) {
			var proto = $.woo[module].prototype;
			for(var i in set) {
				proto.plugins[i] = proto.plugins[i] || [];
				proto.plugins[i].push([option, set[i]]);
			}
		},
		call: function(instance, name, args) {
			var set = instance.plugins[name];
			if(!set || !instance.element[0].parentNode) { return; }

			for (var i = 0; i < set.length; i++) {
				if (instance.options[set[i][0]]) {
					set[i][1].apply(instance.element, args);
				}
			}
		}
	},
	
	//判断节点a是否包含节点b （关于compareDocumentPosition和contains 用法见 http://www.cnblogs.com/siceblue/archive/2010/02/02/1661833.html）
	//与16按位于的原因是，如果包含 值为20 即返回true; 如果不包含值为 0 即返回false
	contains: function(a, b) {
		return document.compareDocumentPosition
			? a.compareDocumentPosition(b) & 16
			: a !== b && a.contains(b);
	},
	
	//判断元素的sroll 在top或者left方向是是否有滚动
	hasScroll: function(el, a) {

		//If overflow is hidden, the element might have extra content, but the user wants to hide it
		if ($(el).css('overflow') == 'hidden') { return false; }

		var scroll = (a && a == 'left') ? 'scrollLeft' : 'scrollTop',
			has = false;

		if (el[scroll] > 0) { return true; }

		// TODO: determine which cases actually cause this to happen
		// if the element doesn't have the scroll set, see if it's possible to
		// set the scroll
		el[scroll] = 1;
		has = (el[scroll] > 0);
		el[scroll] = 0;
		return has;
	},
	
    //确定x坐标是否在元素内部
    //x：要确认的坐标；reference：参考坐标；size：元素宽度
	isOverAxis: function(x, reference, size) {
		//Determines when x coordinate is over "b" element axis
		return (x > reference) && (x < (reference + size));
	},
	//确定x、y坐标是否同事在元素内部
  	//x、y：坐标；top、left：元素坐标；height、width：元素宽高
	isOver: function(y, x, top, left, height, width) {
		//Determines when x, y coordinates is over "b" element
		return $.ui.isOverAxis(y, top, height) && $.ui.isOverAxis(x, left, width);
	},

	keyCode: {
		BACKSPACE: 8,
		CAPS_LOCK: 20,
		COMMA: 188,
		CONTROL: 17,
		DELETE: 46,
		DOWN: 40,
		END: 35,
		ENTER: 13,
		ESCAPE: 27,
		HOME: 36,
		INSERT: 45,
		LEFT: 37,
		NUMPAD_ADD: 107,
		NUMPAD_DECIMAL: 110,
		NUMPAD_DIVIDE: 111,
		NUMPAD_ENTER: 108,
		NUMPAD_MULTIPLY: 106,
		NUMPAD_SUBTRACT: 109,
		PAGE_DOWN: 34,
		PAGE_UP: 33,
		PERIOD: 190,
		RIGHT: 39,
		SHIFT: 16,
		SPACE: 32,
		TAB: 9,
		UP: 38
	},
	/**
	 * 动态加入样式
	 * 
	 * @param {}
	 *            href
	 * @return {}
	 */
	addCssStyle : function(href) {
		return $('<link href="' + href
				+ '" rel="stylesheet" type="text/css" />').appendTo('head');
	}
	});

	//jQuery plugins
	$.fn.extend({
		_focus: $.fn.focus,
		//设置元素焦点（delay：延迟时间）
		focus: function(delay, fn) {
			return typeof delay === 'number'
				? this.each(function() {
					var elem = this;
					setTimeout(function() {
						$(elem).focus();
						(fn && fn.call(elem));
					}, delay);
				})
				: this._focus.apply(this, arguments);
		},
		//设置元素支持被选择
		enableSelection: function() {
			return this
				.attr('unselectable', 'off')
				.css('MozUserSelect', '')
				.unbind('selectstart.ui');
		},
		 //设置元素不支持被选择
		disableSelection: function() {
			return this
				.attr('unselectable', 'on')
				.css('MozUserSelect', 'none')
				.bind('selectstart.ui', function() { return false; });
		},
		
		//获取设置滚动属性的 父元素
		scrollParent: function() {
			var scrollParent;
			if(($.browser.msie && (/(static|relative)/).test(this.css('position'))) || (/absolute/).test(this.css('position'))) {
				scrollParent = this.parents().filter(function() {
					return (/(relative|absolute|fixed)/).test($.curCSS(this,'position',1)) && (/(auto|scroll)/).test($.curCSS(this,'overflow',1)+$.curCSS(this,'overflow-y',1)+$.curCSS(this,'overflow-x',1));
				}).eq(0);
			} else {
				scrollParent = this.parents().filter(function() {
					return (/(auto|scroll)/).test($.curCSS(this,'overflow',1)+$.curCSS(this,'overflow-y',1)+$.curCSS(this,'overflow-x',1));
				}).eq(0);
			}
	
			return (/fixed/).test(this.css('position')) || !scrollParent.length ? $(document) : scrollParent;
		},
	 	//设置或获取元素的垂直坐标
		zIndex: function(zIndex) {
			if (zIndex !== undefined) {
				return this.css('zIndex', zIndex);
			}
			
			if (this.length) {
				var elem = $(this[0]), position, value;
				while (elem.length && elem[0] !== document) {
					// Ignore z-index if position is set to a value where z-index is ignored by the browser
					// This makes behavior of this function consistent across browsers
					// WebKit always returns auto if the element is positioned
					position = elem.css('position');
					if (position == 'absolute' || position == 'relative' || position == 'fixed')
					{
						// IE returns 0 when zIndex is not specified
						// other browsers return a string
						// we ignore the case of nested elements with an explicit value of 0
						// <div style="z-index: -10;"><div style="z-index: 0;"></div></div>
						value = parseInt(elem.css('zIndex'));
						if (!isNaN(value) && value != 0) {
							return value;
						}
					}
					elem = elem.parent();
				}
			}
	
			return 0;
		}
	});
	
/**
 * 扩展选择器引擎的功能
 * $.extend($.expr[':'],{
	    inline: function(a) {
	        return $(a).css('display') === 'inline';
	    }
	});
	$(':inline'); // Selects ALL inline elements
	$('a:inline'); // Selects ALL inline anchors
 */
$.extend($.expr[':'], {
	/**
	 * match:一个匹配数组 
	 * match[0]:data(key)
	 * match[1]:data
	 * match[2]:未知
	 * match[3]:key
	 */
	data: function(elem, i, match) {
		return !!$.data(elem, match[3]);
	},

	focusable: function(element) {
		var nodeName = element.nodeName.toLowerCase(),
			tabIndex = $.attr(element, 'tabindex');
		return (/input|select|textarea|button|object/.test(nodeName)
			? !element.disabled
			: 'a' == nodeName || 'area' == nodeName
				? element.href || !isNaN(tabIndex)
				: !isNaN(tabIndex))
			// the element and all of its ancestors must be visible
			// the browser may report that the area is hidden
			&& !$(element)['area' == nodeName ? 'parents' : 'closest'](':hidden').length;
	},

	tabbable: function(element) {
		var tabIndex = $.attr(element, 'tabindex');
		return (isNaN(tabIndex) || tabIndex >= 0) && $(element).is(':focusable');
	}
});

$.extend(Array.prototype, {
        indexOf: function(o) {
            for (var i = 0,
            len = this.length; i < len; i++) {
                if (this[i] == o) {
                    return i;
                }
            }
            return - 1;
        },
        remove: function(o) {
            var index = this.indexOf(o);
            if (index != -1) {
                this.splice(index, 1);
            }
            return this;
        }
    });
    
$(function(){
	if ($.woo.autoParse){
		$.woo.parse();
	}
});

})(jQuery);/**
 * jQuery-wooUI cookie
 * 
 * @VERSION cookie组件，$.cookie('the_cookie'); //读取Cookie值
 * $.cookie('the_cookie', 'the_value'); //设置cookie的值
 * $.cookie('the_cookie', 'the_value', {expires: 7, path: '/', domain: 'www.toone.com.cn', secure: true});//新建一个cookie 包括有效期 路径 域名等
 * $.cookie('the_cookie', 'the_value'); //新建cookie
 * $.cookie('the_cookie', null); //删除一个cookie
 *
 */

/**
 * Create a cookie with the given name and value and other optional parameters.
 *
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Set the value of a cookie.
 * @example $.cookie('the_cookie', 'the_value', { expires: 7, path: '/', domain: 'jquery.com', secure: true });
 * @desc Create a cookie with all available options.
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Create a session cookie.
 * @example $.cookie('the_cookie', null);
 * @desc Delete a cookie by passing null as value. Keep in mind that you have to use the same path and domain
 *       used when the cookie was set.
 *
 * @param String name The name of the cookie.
 * @param String value The value of the cookie.
 * @param Object options An object literal containing key/value pairs to provide optional cookie attributes.
 * @option Number|Date expires Either an integer specifying the expiration date from now on in days or a Date object.
 *                             If a negative value is specified (e.g. a date in the past), the cookie will be deleted.
 *                             If set to null or omitted, the cookie will be a session cookie and will not be retained
 *                             when the the browser exits.
 * @option String path The value of the path atribute of the cookie (default: path of page that created the cookie).
 * @option String domain The value of the domain attribute of the cookie (default: domain of page that created the cookie).
 * @option Boolean secure If true, the secure attribute of the cookie will be set and the cookie transmission will
 *                        require a secure protocol (like HTTPS).
 * @type undefined
 */

/**
 * Get the value of a cookie with the given name.
 *
 * @example $.cookie('the_cookie');
 * @desc Get the value of a cookie.
 *
 * @param String name The name of the cookie.
 * @return The value of the cookie.
 * @type String
 *
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */
jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        // CAUTION: Needed to parenthesize options.path and options.domain
        // in the following expressions, otherwise they evaluate to undefined
        // in the packed version for some reason...
        var path = options.path ? '; path=' + (options.path) : '';
        var domain = options.domain ? '; domain=' + (options.domain) : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};/**
 * jQuery-wooUI component
 * 
 * 组件基类，主要实现以下四个方面：
 * 1.继承：所有组件均继承其默认实现
 * 2.桥接：在jquery对象中缓存组件对象，通过jquery对象调用组件属性及方法
 * 3.面向切面：实现方法的AOP功能，详见AOP功能使用介绍
 * 4.鼠标事件：实现基类组件对鼠标事件的支持，详见鼠标事件功能使用介绍
 * 
 * 
 * $.component此函数完成了对jQuery本身的扩展，根据第一个参数来确定模块的命名空间和函数名；第二个参数确定模块的基类（默认是$.Component）；第三个参数实现模块本身的方法。比如标签切换插件tabs.js中开始：
 * $.component(“woo.tabs”, {…});//这里只有两个参数，那么基类就默认是$.Component
 * 第一个参数：”woo.tabs”用来表示在jQuery上选择（或增加）一个命名空间，即如果jQuery.woo不存在，则定义jQuery.woo =
 * {},然后在jQuery.woo上增加一个函数，名称为tabs.最后调用$.component.bridge将tabs方法挂在jQuery对象上。这样，所有的jquery对象将拥有tabs方法。
 * 
 * 注意：jquery woo有严格的命名规范，每个控件对外只暴露一个借口。控件所有方法或属性通过向此借口传递不同参数来调用和获取。
 * 
 * WooUI的组件是基于$.Component基类实现的。所以一般我们做控件是都要重写$.Component类中的一些方法。一般来说，一个ui控件需要实现下列的方法或属性：
 * 属性： 
 * @1.options 用来缓存组件的配置参数
 * @2._create 控件初始化调用,多次调用$(xx).tabs()这样不带参数的方法只会执行一次
 * @3._init 一般不用实现，默认为空函数，每次“$(xx).tabs()”这样调用时会调用此方法 
 * @4._setOption “$(xx).tabs(‘option’,xxx)”这种调用方式会调用此方法 公开方法： 
 * @5.destroy 销毁模块 
 * @6.option 设置或获取参数
 * @7.enable 启用模块功能 
 * @8.disable 禁用功能
 * 
 * 当我们基于$.Component进行UI组件开发时，一般上都会重写这些接口，同时增加UI组件相关的私有或公有方法。
 * 
 * 记住，jquery woo的实例是和元素关联起来的，作为数据保存起来了。暴露给用户使用的只是jquery对象上增加的方法。一般我们不需要获取ui的实例。
 */
(function($) {
	// 所有模块的基类
	$.Component = function(options, element) {
		if (arguments.length) {// 如果有参数，调用初始化函数
			this._createComp(options, element);
		}
	}

	$.Component.prototype = {
		cmpName : 'component',
		eventProfix : '',
		options : {
			disabled : false,

			// 鼠标的options
			cancel : ':input,option',
			distance : 1,
			delay : 0
		},
		_createComp : function(options, element) {
			// 将组件实例存储到了$.data中，供$.component.bridge使用
			this.element = this.el = $(element).data(this.cmpName, this);
			this.options = $.extend(true, {}, this.options, $.metadata
							&& $.metadata.get(element)[this.cmpName], options);

			var self = this;
			this.el.bind("remove." + this.cmpName, function() {
						self.destroy();
					});

			this._create();// 创建
			this._init();// 初始化
		},
		_create : $.noop,
		_init : $.noop,

		destroy : function() {// 销毁模块：去除绑定事件、去除数据、去除样式、属性
			this.el.unbind("." + this.cmpName).removeData(this.cmpName);
			this.comp().unbind("." + this.cmpName).removeAttr("aria-disabled")
					.removeClass(this.widgetBaseClass + "-disabled "
							+ this.namespace + "-state-disabled");
		},

		comp : function() {
			return this.el;
		},
		
		option : function(key, value) {
			var options = key, self = this;

			if (arguments.length === 0) {
				// don't return a reference to the internal hash
				return $.extend({}, self.options);
			}

			if (typeof key === "string") {
				if (value === undefined) {
					return this.options[key];
				}
				options = {};
				options[key] = value;
			}

			$.each(options, function(key, value) {
						self._setOption(key, value);
					});

			return self;
		},
		_setOption : function(key, value) {
			this.options[key] = value;

			if (key === "disabled") {
				this.comp()[value ? "addClass" : "removeClass"](this.widgetBaseClass
						+ "-disabled"
						+ " "
						+ this.namespace
						+ "-state-disabled").attr("aria-disabled", value);
			}

			return this;
		},

		enable : function() {
			return this._setOption("disabled", false);
		},
		disable : function() {
			return this._setOption("disabled", true);
		},

		fireEvent : function(type, event, data) {
			var callback = this.options[type];

			event = $.Event(event);
			event.type = (type === this.widgetEventPrefix
					? type
					: this.widgetEventPrefix + type).toLowerCase();
			data = data || {};

			// copy original event properties over to the new event
			// this would happen if we could call $.event.fix instead of $.Event
			// but we don't have a way to force an event to be fixed multiple
			// times
			if (event.originalEvent) {
				for (var i = $.event.props.length, prop; i;) {
					prop = $.event.props[--i];
					event[prop] = event.originalEvent[prop];
				}
			}

			this.el.trigger(event, data);

			return !($.isFunction(callback)
					&& callback.call(this.el[0], event, data) === false || event
					.isDefaultPrevented());
		},

		// 面向切面编程AOP的实现
		yield : null,
		returnValues : {},
		before : function(method, f) {
			var original = this[method];
			this[method] = function() {
				f.apply(this, arguments);
				return original.apply(this, arguments);
			};
		},
		after : function(method, f) {
			var original = this[method];
			this[method] = function() {
				this.returnValues[method] = original.apply(this, arguments);
				return f.apply(this, arguments);
			}
		},
		around : function(method, f) {
			var original = this[method];
			this[method] = function() {
				var tmp = this.yield;
				this.yield = original;
				var ret = f.apply(this, arguments);
				this.yield = tmp;
				return ret;
			}
		},

		// 鼠标事件初始化
		_mouseInit : function() {
			var self = this;

			this.el.bind('mousedown.' + this.cmpName, function(event) {
						return self._mouseDown(event);
					}).bind('click.' + this.cmpName, function(event) {
						if (self._preventClickEvent) {
							// 阻止鼠标事件冒泡
							self._preventClickEvent = false;
							event.stopImmediatePropagation();
							return false;
						}
					});

			this.started = false;
		},

		// TODO: make sure destroying one instance of mouse doesn't mess with
		// other instances of mouse
		_mouseDestroy : function() {
			this.el.unbind('.' + this.cmpName);
		},

		_mouseDown : function(event) {
			// don't let more than one widget handle mouseStart
			// TODO: figure out why we have to use originalEvent
			event.originalEvent = event.originalEvent || {};
			if (event.originalEvent.mouseHandled) {
				return;
			}

			// we may have missed mouseup (out of window)
			(this._mouseStarted && this._mouseUp(event));

			this._mouseDownEvent = event;

			var self = this, btnIsLeft = (event.which == 1), elIsCancel = (typeof this.options.cancel == "string"
					? $(event.target).parents().add(event.target)
							.filter(this.options.cancel).length
					: false);
			// 如果点击的是左键，执行_mouseCapture 返回为false,则函数返回
			if (!btnIsLeft || elIsCancel || !this._mouseCapture(event)) {
				return true;
			}
			// 是否延迟,0 : this.mouseDelayMet = true
			this.mouseDelayMet = !this.options.delay;
			if (!this.mouseDelayMet) {
				this._mouseDelayTimer = setTimeout(function() {
							self.mouseDelayMet = true;
						}, this.options.delay);
			}

			if (this._mouseDistanceMet(event) && this._mouseDelayMet(event)) {
				this._mouseStarted = (this._mouseStart(event) !== false);
				if (!this._mouseStarted) {
					event.preventDefault();
					return true;
				}
			}

			// these delegates are required to keep context
			this._mouseMoveDelegate = function(event) {
				return self._mouseMove(event);
			};
			this._mouseUpDelegate = function(event) {
				return self._mouseUp(event);
			};
			$(document).bind('mousemove.' + this.cmpName,
					this._mouseMoveDelegate).bind('mouseup.' + this.cmpName,
					this._mouseUpDelegate);

			// preventDefault() is used to prevent the selection of text here -
			// however, in Safari, this causes select boxes not to be selectable
			// anymore, so this fix is needed
			($.browser.safari || event.preventDefault());

			event.originalEvent.mouseHandled = true;
			return true;
		},

		_mouseMove : function(event) {
			// IE mouseup check - mouseup happened when mouse was out of window
			if ($.browser.msie && !event.button) {
				return this._mouseUp(event);
			}

			if (this._mouseStarted) {
				this._mouseDrag(event);
				return event.preventDefault();
			}

			if (this._mouseDistanceMet(event) && this._mouseDelayMet(event)) {
				this._mouseStarted = (this._mouseStart(this._mouseDownEvent,
						event) !== false);
				(this._mouseStarted ? this._mouseDrag(event) : this
						._mouseUp(event));
			}

			return !this._mouseStarted;
		},

		_mouseUp : function(event) {
			$(document).unbind('mousemove.' + this.cmpName,
					this._mouseMoveDelegate).unbind('mouseup.' + this.cmpName,
					this._mouseUpDelegate);

			if (this._mouseStarted) {
				this._mouseStarted = false;
				this._preventClickEvent = (event.target == this._mouseDownEvent.target);
				this._mouseStop(event);
			}

			return false;
		},

		_mouseDistanceMet : function(event) {
			return (Math.max(
					Math.abs(this._mouseDownEvent.pageX - event.pageX), Math
							.abs(this._mouseDownEvent.pageY - event.pageY)) >= this.options.distance);
		},

		_mouseDelayMet : function(event) {
			return this.mouseDelayMet;
		},

		_mouseStart : $.noop,
		_mouseDrag : $.noop,
		_mouseStop : $.noop,
		_mouseCapture : function(event) {
			return true;
		},
		//2010/11/30 add by zhaob关于拖放组件
		_trigger: function( type, event, data ) {
			var callback = this.options[ type ];

			event = $.Event( event );
			event.type = ( type === this.widgetEventPrefix ?
				type :
				this.widgetEventPrefix + type ).toLowerCase();
			data = data || {};

			// copy original event properties over to the new event
			// this would happen if we could call $.event.fix instead of $.Event
			// but we don't have a way to force an event to be fixed multiple times
			if ( event.originalEvent ) {
				for ( var i = $.event.props.length, prop; i; ) {
					prop = $.event.props[ --i ];
					event[ prop ] = event.originalEvent[ prop ];
				}
			}

			this.element.trigger( event, data );

			return !( $.isFunction(callback) &&
				callback.call( this.element[0], event, data ) === false ||
				event.isDefaultPrevented() );
		},
		
		//以下为observable功能的实现
		addEvents : function(){},
		on : function(){}
	
		
		

	}
	
	
	/**
	 * 
	 * @param {name}
	 *            如'woo.mycomponent'
	 * @param {base}
	 *            重写获取增加的属性成员，如{options : {v : 10}}
	 * @param {prototype}
	 * @return {Boolean} $.component("woo.mycomponent",{})
	 *         $.component("woo.mycomponent", $.Component,{})
	 */

	$.component = function(name, base, prototype) {
		var ns = name.split('.')[0], fullName;// woo
		name = name.split('.')[1];// mycomponent
		fullName = ns + '-' + name;// woo-mycomponent
		// 如果没有定义prototype prototype为新属性，base为$.Component
		if (!prototype) {
			prototype = base;
			base = $.Component;
		}

		/**
		 * create selector for plugin 扩展选择器引擎的功能 $(":woo-mycomponent")
		 * $(":woo-menu") 可以选择所有该类的组件对象集合
		 * 该选择器能否用于判断某个DOM元素是否已经定义为某个wooui的组件?
		 * 例如：
		 */
		$.expr[":"][fullName] = function(elem) {
			return !!$.data(elem, name);
		};

		/**
		 * 创建命名空间 $['woo']
		 */
		$[ns] = $[ns] || {};
		// 定义$['woo']['mycomponent']构造函数
		$[ns][name] = function(options, element) {
			if (arguments.length) {
				this._createComp(options, element);
			}
		};
		var basePrototype = new base();// 创建一个基类对象$.Component
		// we need to make the options hash a property directly on the new
		// instance
		// otherwise we'll modify the options hash on the prototype that we're
		// inheriting from
		// $.each( basePrototype, function( key, val ) {
		// if ( $.isPlainObject(val) ) {
		// basePrototype[ key ] = $.extend( {}, val );
		// }
		// });
		// extend(dest,src1,src2,src3...srcN) ,合并多个对象.
		basePrototype.options = $.extend({}, basePrototype.options);
		/**
		 * 1.extend(boolean,dest,src1,src2...), 深度镶套对象
		 * 2.定义$['woo']['mycomponent']原型，把子类原型指向合并后的父类对象，这样通过子类对象也可以直接调用基类prototype的方法
		 */
		$[ns][name].prototype = $.extend(true, basePrototype, {
					ns : ns,
					cmpName : name,
					eventProfix : $[ns][name].prototype.eventProfix || name,
					widgetBaseClass : fullName
				}, prototype);
		/**
		 *add by chenjs 2010-11-04 16:03
		 *将组件名称压入到$.woo.components数组中。
		 */
		$.woo.components = $.woo.components || [];
		$.woo.components.push(name);
		
		$.component.bridge(name, $[ns][name]);
	};

	// 将组件功能附加到jQuery对象中,其实是构造器附加过去，于是jquery对象可以通过构造器调用组件实例方法及属性，其实原理就是找到jquery对象中的data的组件实例，并调用实例的属性跟方法，实例的data存储在_createComp中实现
	$.component.bridge = function(name, object) {
		$.fn[name] = function(options) {
			var isMethodCall = typeof options === "string", args = Array.prototype.slice
					.call(arguments, 1), returnValue = this;
			// 如果第一个参数是string类型，就认为是调用模块方法
			// 剩下的参数作为方法的参数，后面会用到

			// allow multiple hashes to be passed on init
			// 可以简单认为是$.extend(true,options,args[0],...),args可以是一个参数或是数组
			options = !isMethodCall && args.length ? $.extend.apply(null, [
							true, options].concat(args)) : options;
			// prevent calls to internal methods
			// 开头带下划线的方法都是私有方法，不让调用
			if (isMethodCall && options.substring(0, 1) === "_") {
				return returnValue;
			}

			if (isMethodCall) {// 如果是调用函数
				this.each(function() {
							var instance = $.data(this, name), // 得到实例，实例作为一个数据和元素关联上
							methodValue = instance
									&& $.isFunction(instance[options])
									? instance[options].apply(instance, args)
									: instance;// 如果实例和方法均存在，调用方法，把args作为参数传进去
							// 如果methodValue不是jquery对象也不是undefined
							if (methodValue !== instance
									&& methodValue !== undefined) {
								returnValue = methodValue;
								return false;// 跳出each，一般获取options的值会走这个分支
							}
						});
			} else {// 不是函数调用的话
				this.each(function() {
							var instance = $.data(this, name);
							if (instance) {// 实例存在
								if (options) {// 有参数
									instance.option(options);// 调用option函数，一般是设置状态之类的操作
								}
								instance._init();// 再次调用此函数，根据options调整
							} else {
								// 没有实例的话，给元素绑定一个实例。注意这里的this是dom，object是模块类
								$.data(this, name, new object(options, this));
							}
						});
			}

			return returnValue;// 返回，有可能是jquery对象，有可能是其他值
		};
	};
	
	
	/**
	 * 创建一个$.component的实例作为顶级类
	 */
	$.component('woo.component');

	// JavaScript继承实现方法，见
	// http://groups.google.com/group/comp.lang.javascript/msg/e04726a66face2a2
	// http://webreflection.blogspot.com/2008/10/big-douglas-begetobject-revisited.html
	// 这里就是虚拟一个类，把该类原型指向传入的类的原型，并创建该类的实例
	var object = (function(F) {// F为一个空的构造函数，因为在使用原型方法之前需要定义一个函数
		return (function(o) {
			F.prototype = o;// o为superproto
			return new F();
		});
	})(function() {
			});

	// 创建一个组件的子类
	var OVERRIDE = /xyz/.test(function() {
				xyz;
			}) ? /\b_super\b/ : /.*/;

	// 添加一个方法
	$.woo.component.subclass = function subclass(name,base,prototype) {
		$.component(name); // 不要将base和prototype参数传递进去
		name = name.split('.');
		var component = $[name[0]][name[1]], 
			superclass = this, 
			superproto = superclass.prototype,
		
			// 把子类的原型指向父类的实例
			proto = arguments[0] = component.prototype = object(superproto); // 从父类继承
		$.extend.apply(null, arguments); // and add other add-in methods to
		// the prototype
		component.subclass = subclass;

		// Subtle point: we want to call superclass init and destroy if they
		// exist
		// (otherwise the user of this function would have to keep track of all
		// that)
		for (key in proto) {
			if (proto.hasOwnProperty(key))
				switch (key) {

					case '_create' :
						var create = proto._create;
						proto._create = function() {
							superproto._create.apply(this);
							create.apply(this);
						};
						break;
					case '_init' :
						var init = proto._init;
						proto._init = function() {
							superproto._init.apply(this);
							init.apply(this);
						};
						break;
					case 'destroy' :
						var destroy = proto.destroy;
						proto.destroy = function() {
							destroy.apply(this);
							superproto.destroy.apply(this);
						};
						break;
					case 'options' :
					
						var options = proto.options;
						proto.options = $.extend({}, superproto.options,
								options);
						break;
					default :
						if ($.isFunction(proto[key])
								&& $.isFunction(superproto[key])
								&& OVERRIDE.test(proto[key])) {
							proto[key] = (function(name, fn) {
								return function() {
									var tmp = this._super;
									this._super = superproto[name];
									try {
										var ret = fn.apply(this, arguments);
									} finally {
										this._super = tmp;
									}
									return ret;
								};
							})(key, proto[key]);
						}
						break;
				}
		}
	};
})(jQuery);/**
 * resizable - wooUI
 * 基于jQuery1.4.2+
 */
(function($){
	$.fn.resizable = function(options){
		function resize(e){
			var resizeData = e.data;
			var options = $.data(resizeData.target, 'resizable').options;
			if (resizeData.dir.indexOf('e') != -1) {
				var width = resizeData.startWidth + e.pageX - resizeData.startX;
				width = Math.min(
							Math.max(width, options.minWidth),
							options.maxWidth
						);
				resizeData.width = width;
			}
			if (resizeData.dir.indexOf('s') != -1) {
				var height = resizeData.startHeight + e.pageY - resizeData.startY;
				height = Math.min(
						Math.max(height, options.minHeight),
						options.maxHeight
				);
				resizeData.height = height;
			}
			if (resizeData.dir.indexOf('w') != -1) {
				resizeData.width = resizeData.startWidth - e.pageX + resizeData.startX;
				if (resizeData.width >= options.minWidth && resizeData.width <= options.maxWidth) {
					resizeData.left = resizeData.startLeft + e.pageX - resizeData.startX;
				}
			}
			if (resizeData.dir.indexOf('n') != -1) {
				resizeData.height = resizeData.startHeight - e.pageY + resizeData.startY;
				if (resizeData.height >= options.minHeight && resizeData.height <= options.maxHeight) {
					resizeData.top = resizeData.startTop + e.pageY - resizeData.startY;
				}
			}
		}
		
		function applySize(e){
			var resizeData = e.data;
			var target = resizeData.target;
			if ($.boxModel == true){
				$(target).css({
					width: resizeData.width - resizeData.deltaWidth,
					height: resizeData.height - resizeData.deltaHeight,
					left: resizeData.left,
					top: resizeData.top
				});
			} else {
				$(target).css({
					width: resizeData.width,
					height: resizeData.height,
					left: resizeData.left,
					top: resizeData.top
				});
			}
		}
		
		function doDown(e){
			$.data(e.data.target, 'resizable').options.onStartResize.call(e.data.target, e);
			return false;
		}
		
		function doMove(e){
			resize(e);
			if ($.data(e.data.target, 'resizable').options.onResize.call(e.data.target, e) != false){
				applySize(e)
			}
			return false;
		}
		
		function doUp(e){
			resize(e, true);
			applySize(e);
			$(document).unbind('.resizable');
			$.data(e.data.target, 'resizable').options.onStopResize.call(e.data.target, e);
			return false;
		}
		
		return this.each(function(){
			var opts = null;
			var state = $.data(this, 'resizable');
			if (state) {
				$(this).unbind('.resizable');
				opts = $.extend(state.options, options || {});
			} else {
				opts = $.extend({}, $.fn.resizable.defaults, options || {});
			}
			
			if (opts.disabled == true) {
				return;
			}
			
			$.data(this, 'resizable', {
				options: opts
			});
			
			var target = this;
			
			// bind mouse event using namespace resizable
			$(this).bind('mousemove.resizable', onMouseMove)
				   .bind('mousedown.resizable', onMouseDown);
			
			function onMouseMove(e) {
				var dir = getDirection(e);
				if (dir == '') {
					$(target).css('cursor', 'default');
				} else {
					$(target).css('cursor', dir + '-resize');
				}
			}
			
			function onMouseDown(e) {
				var dir = getDirection(e);
				if (dir == '') return;
				
				var data = {
					target: this,
					dir: dir,
					startLeft: getCssValue('left'),
					startTop: getCssValue('top'),
					left: getCssValue('left'),
					top: getCssValue('top'),
					startX: e.pageX,
					startY: e.pageY,
					startWidth: $(target).outerWidth(),
					startHeight: $(target).outerHeight(),
					width: $(target).outerWidth(),
					height: $(target).outerHeight(),
					deltaWidth: $(target).outerWidth() - $(target).width(),
					deltaHeight: $(target).outerHeight() - $(target).height()
				};
				$(document).bind('mousedown.resizable', data, doDown);
				$(document).bind('mousemove.resizable', data, doMove);
				$(document).bind('mouseup.resizable', data, doUp);
			}
			
			// get the resize direction
			function getDirection(e) {
				var dir = '';
				var offset = $(target).offset();
				var width = $(target).outerWidth();
				var height = $(target).outerHeight();
				var edge = opts.edge;
				if (e.pageY > offset.top && e.pageY < offset.top + edge) {
					dir += 'n';
				} else if (e.pageY < offset.top + height && e.pageY > offset.top + height - edge) {
					dir += 's';
				}
				if (e.pageX > offset.left && e.pageX < offset.left + edge) {
					dir += 'w';
				} else if (e.pageX < offset.left + width && e.pageX > offset.left + width - edge) {
					dir += 'e';
				}
				
				var handles = opts.handles.split(',');
				for(var i=0; i<handles.length; i++) {
					var handle = handles[i].replace(/(^\s*)|(\s*$)/g, '');
					if (handle == 'all' || handle == dir) {
						return dir;
					}
				}
				return '';
			}
			
			function getCssValue(css) {
				var val = parseInt($(target).css(css));
				if (isNaN(val)) {
					return 0;
				} else {
					return val;
				}
			}
			
		});
	};
	
	$.fn.resizable.defaults = {
			disabled:false,
			handles:'n, e, s, w, ne, se, sw, nw, all',
			minWidth: 10,
			minHeight: 10,
			maxWidth: 10000,//$(document).width(),
			maxHeight: 10000,//$(document).height(),
			edge:5,
			onStartResize: function(e){},
			onResize: function(e){},
			onStopResize: function(e){}
	};
	
})(jQuery);(function($){
	$.woo.component.subclass('woo.draggable',{
		componentEventPrefix: "drag",
		options:{
			addClasses: true,
			appendTo: "parent",
			axis: false,
			connectToSortable: false,
			containment: false,
			cursor: "auto",
			cursorAt: false,
			grid: false,
			handle: false,
			helper: "original",
			iframeFix: false,
			opacity: false,
			refreshPositions: false,
			revert: false,
			revertDuration: 500,
			scope: "default",
			scroll: true,
			scrollSensitivity: 20,
			scrollSpeed: 20,
			snap: false,
			snapMode: "both",
			snapTolerance: 20,
			stack: false,
			zIndex: false
		},

		_create:function(){
			if (this.options.helper == 'original' && !(/^(?:r|a|f)/).test(this.element.css("position")))
			this.element[0].style.position = 'relative';

			(this.options.addClasses && this.element.addClass("ui-draggable"));
			(this.options.disabled && this.element.addClass("ui-draggable-disabled"));

			this._mouseInit();
		},

		destroy:function(){
			if(!this.element.data('draggable')) return;
			this.element
				.removeData("draggable")
				.unbind(".draggable")
				.removeClass("ui-draggable"
					+ " ui-draggable-dragging"
					+ " ui-draggable-disabled");
			this._mouseDestroy();

			return this;
		},

		_mouseCapture:function(event){
			var o = this.options;

			// among others, prevent a drag on a resizable-handle
			if (this.helper || o.disabled || $(event.target).is('.ui-resizable-handle'))
				return false;

			//Quit if we're not on a valid handle
			this.handle = this._getHandle(event);
			if (!this.handle)
				return false;

			return true;
		},

		_mouseStart:function(event){
			var o = this.options;

			//Create and append the visible helper
			this.helper = this._createHelper(event);

			//Cache the helper size
			this._cacheHelperProportions();

			//If ddmanager is used for droppables, set the global draggable
			if($.woo.ddmanager)
				$.woo.ddmanager.current = this;

			/*
			 * - Position generation -
			 * This block generates everything position related - it's the core of draggables.
			 */

			//Cache the margins of the original element
			this._cacheMargins();

			//Store the helper's css position
			this.cssPosition = this.helper.css("position");
			this.scrollParent = this.helper.scrollParent();

			//The element's absolute position on the page minus margins
			this.offset = this.positionAbs = this.element.offset();
			this.offset = {
				top: this.offset.top - this.margins.top,
				left: this.offset.left - this.margins.left
			};

			$.extend(this.offset, {
				click: { //Where the click happened, relative to the element
					left: event.pageX - this.offset.left,
					top: event.pageY - this.offset.top
				},
				parent: this._getParentOffset(),
				relative: this._getRelativeOffset() //This is a relative to absolute position minus the actual position calculation - only used for relative positioned helper
			});

			//Generate the original position
			this.originalPosition = this.position = this._generatePosition(event);
			this.originalPageX = event.pageX;
			this.originalPageY = event.pageY;

			//Adjust the mouse offset relative to the helper if 'cursorAt' is supplied
			(o.cursorAt && this._adjustOffsetFromHelper(o.cursorAt));

			//Set a containment if given in the options
			if(o.containment)
				this._setContainment();

			//Trigger event + callbacks
			if(this._trigger("start", event) === false) {
				this._clear();
				return false;
			}

			//Recache the helper size
			this._cacheHelperProportions();

			//Prepare the droppable offsets
			if ($.woo.ddmanager && !o.dropBehaviour)
				$.woo.ddmanager.prepareOffsets(this, event);

			this.helper.addClass("ui-draggable-dragging");
			this._mouseDrag(event, true); //Execute the drag once - this causes the helper not to be visible before getting its correct position
			return true;
		},

		_mouseDrag:function(event, noPropagation){
			//Compute the helpers position
			this.position = this._generatePosition(event);
			this.positionAbs = this._convertPositionTo("absolute");

			//Call plugins and callbacks and use the resulting position if something is returned
			if (!noPropagation) {
				var ui = this._uiHash();
				if(this._trigger('drag', event, ui) === false) {
					this._mouseUp({});
					return false;
				}
				this.position = ui.position;
			}

			if(!this.options.axis || this.options.axis != "y") this.helper[0].style.left = this.position.left+'px';
			if(!this.options.axis || this.options.axis != "x") this.helper[0].style.top = this.position.top+'px';
			if($.woo.ddmanager) $.woo.ddmanager.drag(this, event);

			return false;
		},

		_mouseStop: function(event){
			//If we are using droppables, inform the manager about the drop
			var dropped = false;
			if ($.woo.ddmanager && !this.options.dropBehaviour)
				dropped = $.woo.ddmanager.drop(this, event);

			//if a drop comes from outside (a sortable)
			if(this.dropped) {
				dropped = this.dropped;
				this.dropped = false;
			}
			
			//if the original element is removed, don't bother to continue
			if(!this.element[0] || !this.element[0].parentNode)
				return false;

			if((this.options.revert == "invalid" && !dropped) || (this.options.revert == "valid" && dropped) || this.options.revert === true || ($.isFunction(this.options.revert) && this.options.revert.call(this.element, dropped))) {
				var self = this;
				$(this.helper).animate(this.originalPosition, parseInt(this.options.revertDuration, 10), function() {
					if(self._trigger("stop", event) !== false) {
						self._clear();
					}
				});
			} else {
				if(this._trigger("stop", event) !== false) {
					this._clear();
				}
			}

			return false;
		},

		cancel: function() {
		
			if(this.helper.is(".ui-draggable-dragging")) {
				this._mouseUp({});
			} else {
				this._clear();
			}
			
			return this;
		},

		_getHandle: function(event) {

			var handle = !this.options.handle || !$(this.options.handle, this.element).length ? true : false;
			$(this.options.handle, this.element)
				.find("*")
				.andSelf()
				.each(function() {
					if(this == event.target) handle = true;
				});

			return handle;

		},

		_createHelper: function(event) {

			var o = this.options;
			var helper = $.isFunction(o.helper) ? $(o.helper.apply(this.element[0], [event])) : (o.helper == 'clone' ? this.element.clone() : this.element);

			if(!helper.parents('body').length)
				helper.appendTo((o.appendTo == 'parent' ? this.element[0].parentNode : o.appendTo));

			if(helper[0] != this.element[0] && !(/(fixed|absolute)/).test(helper.css("position")))
				helper.css("position", "absolute");

			return helper;

		},

		_adjustOffsetFromHelper: function(obj) {
			if (typeof obj == 'string') {
				obj = obj.split(' ');
			}
			if ($.isArray(obj)) {
				obj = {left: +obj[0], top: +obj[1] || 0};
			}
			if ('left' in obj) {
				this.offset.click.left = obj.left + this.margins.left;
			}
			if ('right' in obj) {
				this.offset.click.left = this.helperProportions.width - obj.right + this.margins.left;
			}
			if ('top' in obj) {
				this.offset.click.top = obj.top + this.margins.top;
			}
			if ('bottom' in obj) {
				this.offset.click.top = this.helperProportions.height - obj.bottom + this.margins.top;
			}
		},

		_getParentOffset: function() {

			//Get the offsetParent and cache its position
			this.offsetParent = this.helper.offsetParent();
			var po = this.offsetParent.offset();

			// This is a special case where we need to modify a offset calculated on start, since the following happened:
			// 1. The position of the helper is absolute, so it's position is calculated based on the next positioned parent
			// 2. The actual offset parent is a child of the scroll parent, and the scroll parent isn't the document, which means that
			//    the scroll is included in the initial calculation of the offset of the parent, and never recalculated upon drag
			if(this.cssPosition == 'absolute' && this.scrollParent[0] != document && $.woo.contains(this.scrollParent[0], this.offsetParent[0])) {
				po.left += this.scrollParent.scrollLeft();
				po.top += this.scrollParent.scrollTop();
			}

			if((this.offsetParent[0] == document.body) //This needs to be actually done for all browsers, since pageX/pageY includes this information
			|| (this.offsetParent[0].tagName && this.offsetParent[0].tagName.toLowerCase() == 'html' && $.browser.msie)) //Ugly IE fix
				po = { top: 0, left: 0 };

			return {
				top: po.top + (parseInt(this.offsetParent.css("borderTopWidth"),10) || 0),
				left: po.left + (parseInt(this.offsetParent.css("borderLeftWidth"),10) || 0)
			};

		},

		_getRelativeOffset: function() {

			if(this.cssPosition == "relative") {
				var p = this.element.position();
				return {
					top: p.top - (parseInt(this.helper.css("top"),10) || 0) + this.scrollParent.scrollTop(),
					left: p.left - (parseInt(this.helper.css("left"),10) || 0) + this.scrollParent.scrollLeft()
				};
			} else {
				return { top: 0, left: 0 };
			}

		},

		_cacheMargins: function() {
			this.margins = {
				left: (parseInt(this.element.css("marginLeft"),10) || 0),
				top: (parseInt(this.element.css("marginTop"),10) || 0)
			};
		},

		_cacheHelperProportions: function() {
			this.helperProportions = {
				width: this.helper.outerWidth(),
				height: this.helper.outerHeight()
			};
		},

		_setContainment: function() {

			var o = this.options;
			if(o.containment == 'parent') o.containment = this.helper[0].parentNode;
			if(o.containment == 'document' || o.containment == 'window') this.containment = [
				0 - this.offset.relative.left - this.offset.parent.left,
				0 - this.offset.relative.top - this.offset.parent.top,
				$(o.containment == 'document' ? document : window).width() - this.helperProportions.width - this.margins.left,
				($(o.containment == 'document' ? document : window).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top
			];

			if(!(/^(document|window|parent)$/).test(o.containment) && o.containment.constructor != Array) {
				var ce = $(o.containment)[0]; if(!ce) return;
				var co = $(o.containment).offset();
				var over = ($(ce).css("overflow") != 'hidden');

				this.containment = [
					co.left + (parseInt($(ce).css("borderLeftWidth"),10) || 0) + (parseInt($(ce).css("paddingLeft"),10) || 0) - this.margins.left,
					co.top + (parseInt($(ce).css("borderTopWidth"),10) || 0) + (parseInt($(ce).css("paddingTop"),10) || 0) - this.margins.top,
					co.left+(over ? Math.max(ce.scrollWidth,ce.offsetWidth) : ce.offsetWidth) - (parseInt($(ce).css("borderLeftWidth"),10) || 0) - (parseInt($(ce).css("paddingRight"),10) || 0) - this.helperProportions.width - this.margins.left,
					co.top+(over ? Math.max(ce.scrollHeight,ce.offsetHeight) : ce.offsetHeight) - (parseInt($(ce).css("borderTopWidth"),10) || 0) - (parseInt($(ce).css("paddingBottom"),10) || 0) - this.helperProportions.height - this.margins.top
				];
			} else if(o.containment.constructor == Array) {
				this.containment = o.containment;
			}

		},

		_convertPositionTo: function(d, pos) {

			if(!pos) pos = this.position;
			var mod = d == "absolute" ? 1 : -1;
			var o = this.options, scroll = this.cssPosition == 'absolute' && !(this.scrollParent[0] != document && $.woo.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent : this.scrollParent, scrollIsRootNode = (/(html|body)/i).test(scroll[0].tagName);

			return {
				top: (
					pos.top																	// The absolute mouse position
					+ this.offset.relative.top * mod										// Only for relative positioned nodes: Relative offset from element to offset parent
					+ this.offset.parent.top * mod											// The offsetParent's offset without borders (offset + border)
					- ($.browser.safari && $.browser.version < 526 && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollTop() : ( scrollIsRootNode ? 0 : scroll.scrollTop() ) ) * mod)
				),
				left: (
					pos.left																// The absolute mouse position
					+ this.offset.relative.left * mod										// Only for relative positioned nodes: Relative offset from element to offset parent
					+ this.offset.parent.left * mod											// The offsetParent's offset without borders (offset + border)
					- ($.browser.safari && $.browser.version < 526 && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft() ) * mod)
				)
			};

		},

		_generatePosition: function(event) {

			var o = this.options, scroll = this.cssPosition == 'absolute' && !(this.scrollParent[0] != document && $.woo.contains(this.scrollParent[0], this.offsetParent[0])) ? this.offsetParent : this.scrollParent, scrollIsRootNode = (/(html|body)/i).test(scroll[0].tagName);
			var pageX = event.pageX;
			var pageY = event.pageY;

			/*
			 * - Position constraining -
			 * Constrain the position to a mix of grid, containment.
			 */

			if(this.originalPosition) { //If we are not dragging yet, we won't check for options

				if(this.containment) {
					if(event.pageX - this.offset.click.left < this.containment[0]) pageX = this.containment[0] + this.offset.click.left;
					if(event.pageY - this.offset.click.top < this.containment[1]) pageY = this.containment[1] + this.offset.click.top;
					if(event.pageX - this.offset.click.left > this.containment[2]) pageX = this.containment[2] + this.offset.click.left;
					if(event.pageY - this.offset.click.top > this.containment[3]) pageY = this.containment[3] + this.offset.click.top;
				}

				if(o.grid) {
					var top = this.originalPageY + Math.round((pageY - this.originalPageY) / o.grid[1]) * o.grid[1];
					pageY = this.containment ? (!(top - this.offset.click.top < this.containment[1] || top - this.offset.click.top > this.containment[3]) ? top : (!(top - this.offset.click.top < this.containment[1]) ? top - o.grid[1] : top + o.grid[1])) : top;

					var left = this.originalPageX + Math.round((pageX - this.originalPageX) / o.grid[0]) * o.grid[0];
					pageX = this.containment ? (!(left - this.offset.click.left < this.containment[0] || left - this.offset.click.left > this.containment[2]) ? left : (!(left - this.offset.click.left < this.containment[0]) ? left - o.grid[0] : left + o.grid[0])) : left;
				}

			}

			return {
				top: (
					pageY																// The absolute mouse position
					- this.offset.click.top													// Click offset (relative to the element)
					- this.offset.relative.top												// Only for relative positioned nodes: Relative offset from element to offset parent
					- this.offset.parent.top												// The offsetParent's offset without borders (offset + border)
					+ ($.browser.safari && $.browser.version < 526 && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollTop() : ( scrollIsRootNode ? 0 : scroll.scrollTop() ) ))
				),
				left: (
					pageX																// The absolute mouse position
					- this.offset.click.left												// Click offset (relative to the element)
					- this.offset.relative.left												// Only for relative positioned nodes: Relative offset from element to offset parent
					- this.offset.parent.left												// The offsetParent's offset without borders (offset + border)
					+ ($.browser.safari && $.browser.version < 526 && this.cssPosition == 'fixed' ? 0 : ( this.cssPosition == 'fixed' ? -this.scrollParent.scrollLeft() : scrollIsRootNode ? 0 : scroll.scrollLeft() ))
				)
			};

		},

		_clear: function() {
			this.helper.removeClass("ui-draggable-dragging");
			if(this.helper[0] != this.element[0] && !this.cancelHelperRemoval) this.helper.remove();
			//if($.woo.ddmanager) $.woo.ddmanager.current = null;
			this.helper = null;
			this.cancelHelperRemoval = false;
		},

		// From now on bulk stuff - mainly helpers

		_trigger: function(type, event, ui) {
			ui = ui || this._uiHash();
			$.woo.plugin.call(this, type, [event, ui]);
			if(type == "drag") this.positionAbs = this._convertPositionTo("absolute"); //The absolute position has to be recalculated after plugins
			return $.Component.prototype._trigger.call(this, type, event, ui);
		},

		plugins: {},

		_uiHash: function(event) {
			return {
				helper: this.helper,
				position: this.position,
				originalPosition: this.originalPosition,
				offset: this.positionAbs
			};
		}
	});

	$.woo.plugin.add("draggable", "cursor", {
		start: function(event, ui) {
			var t = $('body'), o = $(this).data('draggable').options;
			if (t.css("cursor")) o._cursor = t.css("cursor");
			t.css("cursor", o.cursor);
		},
		stop: function(event, ui) {
			var o = $(this).data('draggable').options;
			if (o._cursor) $('body').css("cursor", o._cursor);
		}
	});

	$.woo.plugin.add("draggable", "iframeFix", {
		start: function(event, ui) {
			var o = $(this).data('draggable').options;
			$(o.iframeFix === true ? "iframe" : o.iframeFix).each(function() {
				$('<div class="ui-draggable-iframeFix" style="background: #fff;"></div>')
				.css({
					width: this.offsetWidth+"px", height: this.offsetHeight+"px",
					position: "absolute", opacity: "0.001", zIndex: 1000
				})
				.css($(this).offset())
				.appendTo("body");
			});
		},
		stop: function(event, ui) {
			$("div.ui-draggable-iframeFix").each(function() { this.parentNode.removeChild(this); }); //Remove frame helpers
		}
	});

	$.woo.plugin.add("draggable", "opacity", {
		start: function(event, ui) {
			var t = $(ui.helper), o = $(this).data('draggable').options;
			if(t.css("opacity")) o._opacity = t.css("opacity");
			t.css('opacity', o.opacity);
		},
		stop: function(event, ui) {
			var o = $(this).data('draggable').options;
			if(o._opacity) $(ui.helper).css('opacity', o._opacity);
		}
	});

	$.woo.plugin.add("draggable", "scroll", {
		start: function(event, ui) {
			var i = $(this).data("draggable");
			if(i.scrollParent[0] != document && i.scrollParent[0].tagName != 'HTML') i.overflowOffset = i.scrollParent.offset();
		},
		drag: function(event, ui) {

			var i = $(this).data("draggable"), o = i.options, scrolled = false;

			if(i.scrollParent[0] != document && i.scrollParent[0].tagName != 'HTML') {

				if(!o.axis || o.axis != 'x') {
					if((i.overflowOffset.top + i.scrollParent[0].offsetHeight) - event.pageY < o.scrollSensitivity)
						i.scrollParent[0].scrollTop = scrolled = i.scrollParent[0].scrollTop + o.scrollSpeed;
					else if(event.pageY - i.overflowOffset.top < o.scrollSensitivity)
						i.scrollParent[0].scrollTop = scrolled = i.scrollParent[0].scrollTop - o.scrollSpeed;
				}

				if(!o.axis || o.axis != 'y') {
					if((i.overflowOffset.left + i.scrollParent[0].offsetWidth) - event.pageX < o.scrollSensitivity)
						i.scrollParent[0].scrollLeft = scrolled = i.scrollParent[0].scrollLeft + o.scrollSpeed;
					else if(event.pageX - i.overflowOffset.left < o.scrollSensitivity)
						i.scrollParent[0].scrollLeft = scrolled = i.scrollParent[0].scrollLeft - o.scrollSpeed;
				}

			} else {

				if(!o.axis || o.axis != 'x') {
					if(event.pageY - $(document).scrollTop() < o.scrollSensitivity)
						scrolled = $(document).scrollTop($(document).scrollTop() - o.scrollSpeed);
					else if($(window).height() - (event.pageY - $(document).scrollTop()) < o.scrollSensitivity)
						scrolled = $(document).scrollTop($(document).scrollTop() + o.scrollSpeed);
				}

				if(!o.axis || o.axis != 'y') {
					if(event.pageX - $(document).scrollLeft() < o.scrollSensitivity)
						scrolled = $(document).scrollLeft($(document).scrollLeft() - o.scrollSpeed);
					else if($(window).width() - (event.pageX - $(document).scrollLeft()) < o.scrollSensitivity)
						scrolled = $(document).scrollLeft($(document).scrollLeft() + o.scrollSpeed);
				}

			}

			if(scrolled !== false && $.woo.ddmanager && !o.dropBehaviour)
				$.woo.ddmanager.prepareOffsets(i, event);

		}
	});

	$.woo.plugin.add("draggable", "snap", {
		start: function(event, ui) {

			var i = $(this).data("draggable"), o = i.options;
			i.snapElements = [];

			$(o.snap.constructor != String ? ( o.snap.items || ':data(draggable)' ) : o.snap).each(function() {
				var $t = $(this); var $o = $t.offset();
				if(this != i.element[0]) i.snapElements.push({
					item: this,
					width: $t.outerWidth(), height: $t.outerHeight(),
					top: $o.top, left: $o.left
				});
			});

		},
		drag: function(event, ui) {

			var inst = $(this).data("draggable"), o = inst.options;
			var d = o.snapTolerance;

			var x1 = ui.offset.left, x2 = x1 + inst.helperProportions.width,
				y1 = ui.offset.top, y2 = y1 + inst.helperProportions.height;

			for (var i = inst.snapElements.length - 1; i >= 0; i--){

				var l = inst.snapElements[i].left, r = l + inst.snapElements[i].width,
					t = inst.snapElements[i].top, b = t + inst.snapElements[i].height;

				//Yes, I know, this is insane ;)
				if(!((l-d < x1 && x1 < r+d && t-d < y1 && y1 < b+d) || (l-d < x1 && x1 < r+d && t-d < y2 && y2 < b+d) || (l-d < x2 && x2 < r+d && t-d < y1 && y1 < b+d) || (l-d < x2 && x2 < r+d && t-d < y2 && y2 < b+d))) {
					if(inst.snapElements[i].snapping) (inst.options.snap.release && inst.options.snap.release.call(inst.element, event, $.extend(inst._uiHash(), { snapItem: inst.snapElements[i].item })));
					inst.snapElements[i].snapping = false;
					continue;
				}

				if(o.snapMode != 'inner') {
					var ts = Math.abs(t - y2) <= d;
					var bs = Math.abs(b - y1) <= d;
					var ls = Math.abs(l - x2) <= d;
					var rs = Math.abs(r - x1) <= d;
					if(ts) ui.position.top = inst._convertPositionTo("relative", { top: t - inst.helperProportions.height, left: 0 }).top - inst.margins.top;
					if(bs) ui.position.top = inst._convertPositionTo("relative", { top: b, left: 0 }).top - inst.margins.top;
					if(ls) ui.position.left = inst._convertPositionTo("relative", { top: 0, left: l - inst.helperProportions.width }).left - inst.margins.left;
					if(rs) ui.position.left = inst._convertPositionTo("relative", { top: 0, left: r }).left - inst.margins.left;
				}

				var first = (ts || bs || ls || rs);

				if(o.snapMode != 'outer') {
					var ts = Math.abs(t - y1) <= d;
					var bs = Math.abs(b - y2) <= d;
					var ls = Math.abs(l - x1) <= d;
					var rs = Math.abs(r - x2) <= d;
					if(ts) ui.position.top = inst._convertPositionTo("relative", { top: t, left: 0 }).top - inst.margins.top;
					if(bs) ui.position.top = inst._convertPositionTo("relative", { top: b - inst.helperProportions.height, left: 0 }).top - inst.margins.top;
					if(ls) ui.position.left = inst._convertPositionTo("relative", { top: 0, left: l }).left - inst.margins.left;
					if(rs) ui.position.left = inst._convertPositionTo("relative", { top: 0, left: r - inst.helperProportions.width }).left - inst.margins.left;
				}

				if(!inst.snapElements[i].snapping && (ts || bs || ls || rs || first))
					(inst.options.snap.snap && inst.options.snap.snap.call(inst.element, event, $.extend(inst._uiHash(), { snapItem: inst.snapElements[i].item })));
				inst.snapElements[i].snapping = (ts || bs || ls || rs || first);

			};

		}
	});

	$.woo.plugin.add("draggable", "stack", {
		start: function(event, ui) {

			var o = $(this).data("draggable").options;

			var group = $.makeArray($(o.stack)).sort(function(a,b) {
				return (parseInt($(a).css("zIndex"),10) || 0) - (parseInt($(b).css("zIndex"),10) || 0);
			});
			if (!group.length) { return; }
			
			var min = parseInt(group[0].style.zIndex) || 0;
			$(group).each(function(i) {
				this.style.zIndex = min + i;
			});

			this[0].style.zIndex = min + group.length;

		}
	});

	$.woo.plugin.add("draggable", "zIndex", {
		start: function(event, ui) {
			var t = $(ui.helper), o = $(this).data("draggable").options;
			if(t.css("zIndex")) o._zIndex = t.css("zIndex");
			t.css('zIndex', o.zIndex);
		},
		stop: function(event, ui) {
			var o = $(this).data("draggable").options;
			if(o._zIndex) $(ui.helper).css('zIndex', o._zIndex);
		}
	});
})(jQuery);/**
 * droppable - tui
 * 基于jQuery1.4.2+
 */
(function($){
	$.woo.component.subclass('woo.droppable',{
		componentEventPrefix: "drop",
		options:{
			accept: '*',
			activeClass: false,
			addClasses: true,
			greedy: false,
			hoverClass: false,
			scope: 'default',
			tolerance: 'intersect'
		},
		_create: function() {

			var o = this.options, accept = o.accept;
			this.isover = 0; this.isout = 1;

			this.accept = $.isFunction(accept) ? accept : function(d) {
				return d.is(accept);
			};

			//Store the droppable's proportions
			this.proportions = { width: this.element[0].offsetWidth, height: this.element[0].offsetHeight };

			// Add the reference and positions to the manager
			$.woo.ddmanager.droppables[o.scope] = $.woo.ddmanager.droppables[o.scope] || [];
			$.woo.ddmanager.droppables[o.scope].push(this);

			(o.addClasses && this.element.addClass("ui-droppable"));

		},
		destroy: function() {
			var drop = $.woo.ddmanager.droppables[this.options.scope];
			for ( var i = 0; i < drop.length; i++ )
				if ( drop[i] == this )
					drop.splice(i, 1);

			this.element
				.removeClass("ui-droppable ui-droppable-disabled")
				.removeData("droppable")
				.unbind(".droppable");

			return this;
		},

		_setOption: function(key, value) {

			if(key == 'accept') {
				this.accept = $.isFunction(value) ? value : function(d) {
					return d.is(value);
				};
			}
			$.Component.prototype._setOption.apply(this, arguments);
		},

		_activate: function(event) {
			var draggable = $.woo.ddmanager.current;
			if(this.options.activeClass) this.element.addClass(this.options.activeClass);
			(draggable && this._trigger('activate', event, this.ui(draggable)));
		},

		_deactivate: function(event) {
			var draggable = $.woo.ddmanager.current;
			if(this.options.activeClass) this.element.removeClass(this.options.activeClass);
			(draggable && this._trigger('deactivate', event, this.ui(draggable)));
		},

		_over: function(event) {

			var draggable = $.woo.ddmanager.current;
			if (!draggable || (draggable.currentItem || draggable.element)[0] == this.element[0]) return; // Bail if draggable and droppable are same element

			if (this.accept.call(this.element[0],(draggable.currentItem || draggable.element))) {
				if(this.options.hoverClass) this.element.addClass(this.options.hoverClass);
				this._trigger('over', event, this.ui(draggable));
			}

		},

		_out: function(event) {

			var draggable = $.woo.ddmanager.current;
			if (!draggable || (draggable.currentItem || draggable.element)[0] == this.element[0]) return; // Bail if draggable and droppable are same element

			if (this.accept.call(this.element[0],(draggable.currentItem || draggable.element))) {
				if(this.options.hoverClass) this.element.removeClass(this.options.hoverClass);
				this._trigger('out', event, this.ui(draggable));
			}

		},

		_drop: function(event,custom) {

			var draggable = custom || $.woo.ddmanager.current;
			if (!draggable || (draggable.currentItem || draggable.element)[0] == this.element[0]) return false; // Bail if draggable and droppable are same element

			var childrenIntersection = false;
			this.element.find(":data(droppable)").not(".ui-draggable-dragging").each(function() {
				var inst = $.data(this, 'droppable');
				if(
					inst.options.greedy
					&& !inst.options.disabled
					&& inst.options.scope == draggable.options.scope
					&& inst.accept.call(inst.element[0], (draggable.currentItem || draggable.element))
					&& $.woo.intersect(draggable, $.extend(inst, { offset: inst.element.offset() }), inst.options.tolerance)
				) { childrenIntersection = true; return false; }
			});
			if(childrenIntersection) return false;

			if(this.accept.call(this.element[0],(draggable.currentItem || draggable.element))) {
				if(this.options.activeClass) this.element.removeClass(this.options.activeClass);
				if(this.options.hoverClass) this.element.removeClass(this.options.hoverClass);
				this._trigger('drop', event, this.ui(draggable));
				return this.element;
			}

			return false;

		},

		ui: function(c) {
			return {
				draggable: (c.currentItem || c.element),
				helper: c.helper,
				position: c.position,
				offset: c.positionAbs
			};
		}
	});

	$.woo.intersect = function(draggable, droppable, toleranceMode) {

		if (!droppable.offset) return false;

		var x1 = (draggable.positionAbs || draggable.position.absolute).left, x2 = x1 + draggable.helperProportions.width,
			y1 = (draggable.positionAbs || draggable.position.absolute).top, y2 = y1 + draggable.helperProportions.height;
		var l = droppable.offset.left, r = l + droppable.proportions.width,
			t = droppable.offset.top, b = t + droppable.proportions.height;

		switch (toleranceMode) {
			case 'fit':
				return (l < x1 && x2 < r
					&& t < y1 && y2 < b);
				break;
			case 'intersect':
				return (l < x1 + (draggable.helperProportions.width / 2) // Right Half
					&& x2 - (draggable.helperProportions.width / 2) < r // Left Half
					&& t < y1 + (draggable.helperProportions.height / 2) // Bottom Half
					&& y2 - (draggable.helperProportions.height / 2) < b ); // Top Half
				break;
			case 'pointer':
				var draggableLeft = ((draggable.positionAbs || draggable.position.absolute).left + (draggable.clickOffset || draggable.offset.click).left),
					draggableTop = ((draggable.positionAbs || draggable.position.absolute).top + (draggable.clickOffset || draggable.offset.click).top),
					isOver = $.woo.isOver(draggableTop, draggableLeft, t, l, droppable.proportions.height, droppable.proportions.width);
				return isOver;
				break;
			case 'touch':
				return (
						(y1 >= t && y1 <= b) ||	// Top edge touching
						(y2 >= t && y2 <= b) ||	// Bottom edge touching
						(y1 < t && y2 > b)		// Surrounded vertically
					) && (
						(x1 >= l && x1 <= r) ||	// Left edge touching
						(x2 >= l && x2 <= r) ||	// Right edge touching
						(x1 < l && x2 > r)		// Surrounded horizontally
					);
				break;
			default:
				return false;
				break;
			}

	};

	/*
		This manager tracks offsets of draggables and droppables
	*/
	$.woo.ddmanager = {
		current: null,
		droppables: { 'default': [] },
		prepareOffsets: function(t, event) {

			var m = $.woo.ddmanager.droppables[t.options.scope] || [];
			var type = event ? event.type : null; // workaround for #2317
			var list = (t.currentItem || t.element).find(":data(droppable)").andSelf();

			droppablesLoop: for (var i = 0; i < m.length; i++) {

				if(m[i].options.disabled || (t && !m[i].accept.call(m[i].element[0],(t.currentItem || t.element)))) continue;	//No disabled and non-accepted
				for (var j=0; j < list.length; j++) { if(list[j] == m[i].element[0]) { m[i].proportions.height = 0; continue droppablesLoop; } }; //Filter out elements in the current dragged item
				m[i].visible = m[i].element.css("display") != "none"; if(!m[i].visible) continue; 									//If the element is not visible, continue

				m[i].offset = m[i].element.offset();
				m[i].proportions = { width: m[i].element[0].offsetWidth, height: m[i].element[0].offsetHeight };

				if(type == "mousedown") m[i]._activate.call(m[i], event); //Activate the droppable if used directly from draggables

			}

		},
		drop: function(draggable, event) {

			var dropped = false;
			$.each($.woo.ddmanager.droppables[draggable.options.scope] || [], function() {

				if(!this.options) return;
				if (!this.options.disabled && this.visible && $.woo.intersect(draggable, this, this.options.tolerance))
					dropped = dropped || this._drop.call(this, event);

				if (!this.options.disabled && this.visible && this.accept.call(this.element[0],(draggable.currentItem || draggable.element))) {
					this.isout = 1; this.isover = 0;
					this._deactivate.call(this, event);
				}

			});
			return dropped;

		},
		drag: function(draggable, event) {

			//If you have a highly dynamic page, you might try this option. It renders positions every time you move the mouse.
			if(draggable.options.refreshPositions) $.woo.ddmanager.prepareOffsets(draggable, event);

			//Run through all droppables and check their positions based on specific tolerance options
			$.each($.woo.ddmanager.droppables[draggable.options.scope] || [], function() {

				if(this.options.disabled || this.greedyChild || !this.visible) return;
				var intersects = $.woo.intersect(draggable, this, this.options.tolerance);

				var c = !intersects && this.isover == 1 ? 'isout' : (intersects && this.isover == 0 ? 'isover' : null);
				if(!c) return;

				var parentInstance;
				if (this.options.greedy) {
					var parent = this.element.parents(':data(droppable):eq(0)');
					if (parent.length) {
						parentInstance = $.data(parent[0], 'droppable');
						parentInstance.greedyChild = (c == 'isover' ? 1 : 0);
					}
				}

				// we just moved into a greedy child
				if (parentInstance && c == 'isover') {
					parentInstance['isover'] = 0;
					parentInstance['isout'] = 1;
					parentInstance._out.call(parentInstance, event);
				}

				this[c] = 1; this[c == 'isout' ? 'isover' : 'isout'] = 0;
				this[c == "isover" ? "_over" : "_out"].call(this, event);

				// we just moved out of a greedy child
				if (parentInstance && c == 'isout') {
					parentInstance['isout'] = 0;
					parentInstance['isover'] = 1;
					parentInstance._over.call(parentInstance, event);
				}
			});

		}
	};
})(jQuery);/**
 * switchable - WooUI 基于jQuery1.4.2+
 * 
 */

;(function($) {
	$.switchable = $.switchable || {};

	$.switchable = {
		cfg: {
			// 触点
			triggers: "a",
			// 当前触点的className
			currentCls: "current",
			// 默认激活项
			initIndex: 0,
			// 触发类型
			triggerType: "mouse", // or click
			// 触发延迟
			delay: .1, // 100ms

			// 切换效果
			effect: "default",
			// 每次切换的 Panel 数量
			steps: 1,
			// 可见区域的 Panel 数量
			visible: 1,
			// 动画步时
			speed: .7, // 700ms
			easing: "swing",
			
			// 循环
			circular: false,
			// 纵向切换
			vertical: false,
			// 点击视图区切换
			panelSwitch: false,
			
			beforeSwitch: null,
			onSwitch: null,
			api: false
		},
		//自定义效果
		addEffect: function(name, fn) {
			effects[name] = fn;
		}
	};
	//内置效果
	var effects = {
		"default": function(i, done) {
			this.getPanels().hide();
			this.getVisiblePanel(i).show();
			done.call();
		}, 
		"ajax": function(i, done)  {			
			this.getPanels().first().load(this.getTriggers().eq(i).attr("href"), done);	
		}
	};   	

	function switchTo(triggers, panels, cfg) { 
		
		var self = this,
			$self = $(this),
			current,
			index = triggers.length -1;

		// 绑定所有回调函数
		$.each(cfg, function(name, fn) {
			if ( $.isFunction(fn) ) {
				$self.bind(name, fn);
			}
		});
		
		// 公共方法
		$.extend(this, {
			click: function(i, e) {

				var trigger = triggers.eq(i);
				
				if ( typeof i == 'string' && i.replace("#", "") ) {
					trigger = triggers.filter("[href*=" + i.replace("#", "") + "]");
					i = Math.max(trigger.index(), 0);
				}
				/*
				if ( !trigger.length ) { 
					if ( current >= 0 ) {
						return self;
					}
					i = cfg.initIndex;
					trigger = triggers.eq(i);
				}
				*/
				e = e || $.Event();
				e.type = "beforeSwitch";
				$self.trigger(e, [i]);
				if ( e.isDefaultPrevented() ) {
					return;
				}
				
				// Call the effect
				effects[cfg.effect].call(self, i, function() {
					// onSwitch callback
					e.type = "onSwitch";
					$self.trigger(e, [i]);					
				});			
				
				// onStart
				e.type = "onStart";
				$self.trigger(e, [i]);				
				if ( e.isDefaultPrevented() ) {
					return;
				}
				
				current = i;
				triggers.removeClass(cfg.currentCls);	
				trigger.addClass(cfg.currentCls);
				
				return self;
			},
			
			getCfg: function() {
				return cfg;	
			},

			getTriggers: function() {
				return triggers;	
			},
			
			getPanels: function() {
				return panels;	
			},
			
			getVisiblePanel: function(i) {
				return self.getPanels().slice(i * cfg.steps, (i + 1) * cfg.steps);	
			},
			
			getIndex: function() {
				return current;	
			}, 
			
			move: function(i) {
				if ( panels.parent().is(":animated") || panels.length <= cfg.visible ) {
					return self;
				}
				
				if ( typeof i == 'number' ) {
					if ( i < 0 ) {
						return cfg.circular ? self.click(index) : self;
					}
					else if ( i > index ) {
						return cfg.circular ? self.click(0) : self;
					}
					else {
						return self.click(i);
					}
				} else {
					return self.click();
				}
			}, 
			
			next: function() {
				return self.move(current + 1);
			},
			
			prev: function() {
				return self.move(current - 1);	
			},
			
			bind: function(name, fn) {
				$self.bind(name, fn);
				return self;	
			},	
			
			unbind: function(name) {
				$self.unbind(name);
				return self;
			},
			
			beforeSwitch: function(fn) {
				return this.bind("beforeSwitch", fn);
			},
			
			onSwitch: function(fn) {
				return this.bind("onSwitch", fn);
			},
			
			resetPosition: function(isBackward) {}
		
		});
		
		//为每个触点绑定事件
		var switchTimer;
		triggers.each(function(i) {
			if ( cfg.triggerType === "mouse" ) {//响应鼠标悬浮
				$(this).bind({
					"mouseenter": function(e) {
						//不重复触发
						if ( i !== current ) {
							//延时处理，鼠标快速滑过不触发
							switchTimer = setTimeout(function() {
								self.click(i, e);
							}, cfg.delay * 1000);
						}
					},
					"mouseleave": function() {
						//鼠标快速滑出，取消悬浮事件
						clearTimeout(switchTimer);
					}
				});
			} else {//响应点击
				$(this).bind("click", function(e) {
					//不重复触发
					if ( i !== current ) {
						self.click(i, e);
					}
					return false;
				});
			}
		});

		if ( location.hash ) {
			self.click(location.hash);
		} else {
			if ( cfg.initIndex === 0 || cfg.initIndex > 0 ) {
				self.click(cfg.initIndex);
			}
		}		
		
		// 视图区通过锚链切换
		panels.find("a[href^=#]").click(function(e) {
			self.click($(this).attr("href"), e);		
		}); 

		// 点击视图区切换
		if ( cfg.panelSwitch ) {
			panels.css("cursor", "pointer").click(function() {
				self.next();
				return false;
			}); 
		}
	}
	
	$.fn.switchable = function(container, cfg) {

		var el = this.eq(typeof cfg == 'number' ? cfg : 0).data("switchable");
		if ( el ) {
			return el;
		}

		if ( $.isFunction(cfg) ) {
			cfg = { beforeSwitch: cfg };
		}
		
		var globals = $.extend({}, $.switchable.cfg), len = this.length;
		cfg = $.extend(globals, cfg);

		this.each(function(i) {
			var root = $(this);
			
			// 获取 panels
			var panels = container.jquery ? container : root.children(container);
			if ( !panels.length ) {
				panels = len == 1 ? $(container) : root.parent().find(container);
			}

			// 获取 triggers
			var els = root.find(cfg.triggers);
			// 自动创建 triggers
			if ( !els.length ) {
				// 向上取整
				var counts = Math.ceil(panels.length / cfg.steps);
				for (var i = 1; i <= counts; i++) {
					$("<a>", {
						href: "javascript:void(0);",
						target: "_self",
						text: i
					}).appendTo( root );
				}
				els = root.children("a");
			}

			el = new switchTo(els, panels, cfg);
			root.data("switchable", el);

		});		
		
		return cfg.api ? el : this;		
	};		
		
})(jQuery);

/**
 * 淡隐淡现
**/
$.switchable.addEffect("fade", function(i, done) {
	var self = this,
		cfg = self.getCfg(),
		items = self.getPanels(),
		thisItem = self.getVisiblePanel(i);

	items.hide();
	thisItem.fadeIn(cfg.speed * 1000, done);
});

/**
 * 滚动
 *
 * Panel's HTML Makeup:
 * <container>
 *    <wrapper>
 *       <panel />
 *       <panel />
 *       <panel />
 *    </wrapper>
 * </container>
**/
$.switchable.addEffect("scroll", function(i, done) {
	var self = this,
		cfg = self.getCfg(),
		thisItem = self.getVisiblePanel(i),
		wrap = self.getPanels().parent(),
		current = self.getIndex(),
		len = self.getTriggers().length - 1,

		// 从第一个反向滚动到最后一个 or 从最后一个正向滚动到第一个
		isCritical = (current === 0 && i === len) || (current === len && i === 0),
		isBackward = (current === 0 && i === len) ? true : false,
		prop = cfg.vertical ? { top : -thisItem.position().top } : { left : -thisItem.position().left };
	
	// 开始动画
	if ( wrap.is(":animated") ) {
		wrap.stop(true);
	}
	wrap.animate(prop, cfg.speed * 1000, cfg.easing, function() {
		done.call();
		// 复原位置（为了兼容plugin-carousel.js）
		if ( isCritical ) {
			self.resetPosition(isBackward);
		}
	});
});


;(function($) {		

	var t = $.switchable; 
	t.plugin = t.plugin || {};
	
	t.plugin.autoplay = {
		cfg: {
			// 自动播放
			autoplay: true,
			// 自动播放间隔
			interval: 3, // 3000ms
			// 鼠标悬停暂停
			autopause: true,
			api: false
		}
	};	
	
	$.fn.autoplay = function(cfg) { 

		if ( typeof cfg == 'number' ) {
			cfg = { interval: cfg };	
		}
		
		var opts = $.extend({}, t.plugin.autoplay.cfg), ret;
		$.extend(opts, cfg);   	
		
		this.each(function() {		
				
			var api = $(this).switchable();			
			if ( api ) {
				ret = api;
			}
			
			var timer, hoverTimer, stopped = true;
	
			api.play = function() {
	
				// do not start additional timer if already exists
				if ( timer ) {
					return;
				}
				
				stopped = false;
				
				// construct new timer
				timer = setInterval(function() {
					api.next();
				}, opts.interval*1000);
				
				api.next();
			};	

			api.pause = function() {
				timer = clearInterval(timer);	
			};
			
			// when stopped - mouseover won't restart 
			api.stop = function() {
				api.pause();
				stopped = true;	
			};
		
			/* when mouse enters, autoplay stops */
			if ( opts.autopause ) {
				api.getPanels().hover(function() {			
					api.pause();
					clearTimeout(hoverTimer);
				}, function() {
					if ( !stopped ) {						
						hoverTimer = setTimeout(api.play, opts.interval*1000);						
					}
				});
			}			
			
			if ( opts.autoplay ) {
				setTimeout(api.play, opts.interval*1000);				
			}

		});
		
		return opts.api ? ret : this;
		
	}; 
	
})(jQuery);

(function($) {		

	$.fn.carousel = function() {

		this.each(function() {
				
			var api = $(this).switchable(),
				cfg = api.getCfg(),
				panels = api.getPanels(),
				wrap = panels.parent(),

				index = api.getTriggers().length -1,
				firstItem = panels.slice(0, cfg.steps),
				lastItem = panels.slice(index * cfg.steps),

				lastPosition = cfg.vertical ? lastItem.position().top : lastItem.position().left,
				direction = cfg.vertical ? "top" : "left",

				allow = panels.length > cfg.visible,
				size = 0;

			panels.css("position", "relative").each(function() {
				size += cfg.vertical ? $(this).outerHeight(true) : $(this).outerWidth(true);
			});

			if ( allow && lastItem.length < cfg.visible ) {
				wrap.append( panels.slice(0, cfg.visible).clone().addClass("clone") );
			}

			$.extend(api, {

				move: function(i) {
					if ( wrap.is(":animated") || !allow ) {
						return this;
					}

					// 从第一个反向滚动到最后一个
					if ( i < 0 ) {
						// 调整位置
						this.adjustPosition(true);
						// 触发最后一组 panels
						return this.click(index);
					}
					// 从最后一个正向滚动到第一个
					else if ( i > index ) {
						// 调整位置
						this.adjustPosition(false);
						// 触发第一组 panels
						return this.click(0);
					}
					else {
						return this.click(i);
					}
				}, 

				adjustPosition: function(isBackward) {
					var theItem = isBackward ? lastItem : firstItem;

					// 调整 panels 到下一个视图中
					$.each(theItem, function() {
						$(this).css(direction, isBackward ? -size : size + "px");
					});
				},

				resetPosition: function(isBackward) {
					var theItem = isBackward ? lastItem : firstItem;
					
					// 滚动完成后，复位到正常状态
					$.each(theItem, function() {
						$(this).css(direction, "0px");
					});
					// 瞬移到正常位置
					wrap.css(direction, isBackward ? -lastPosition : 0 + "px");
				}
			
			});

		});
		
		return this;
		
	}; 
	
})(jQuery);
(function($) {
		
	$.fn.wheel = function( fn ){
		return this[ fn ? "bind" : "trigger" ]( "wheel", fn );
	};

	// special event cfgig
	$.event.special.wheel = {
		setup: function(){
			$.event.add( this, wheelEvents, wheelHandler, {} );
		},
		teardown: function(){
			$.event.remove( this, wheelEvents, wheelHandler );
		}
	};

	// events to bind ( browser sniffed... )
	var wheelEvents = !$.browser.mozilla ? "mousewheel" : // IE, opera, safari
		"DOMMouseScroll" + ( $.browser.version < "1.9" ? " mousemove" : "" ); // firefox

	// shared event handler
	function wheelHandler( event ) {
		
		switch ( event.type ){
			
			// FF2
			case "mousemove": 
				return $.extend( event.data, { // 存储正确的属性
					clientX: event.clientX, clientY: event.clientY,
					pageX: event.pageX, pageY: event.pageY
				});
				
			// firefox	
			case "DOMMouseScroll": 
				$.extend( event, event.data );
				event.delta = -event.detail / 3;
				break;
				
			// IE, opera, safari	
			case "mousewheel":				
				event.delta = event.wheelDelta / 120;
				break;
		}
		
		event.type = "wheel"; // 事件劫持
		return $.event.handle.call( this, event, event.delta );
	}

	$.fn.mousewheel = function() {

		this.each(function() {		

			var api = $(this).switchable();
			
			api.getPanels().parent().wheel(function(e, delta)  {
				if ( delta < 0 ) {
					api.next();
				} else {
					api.prev();
				}
				return false;
			});
		});

		return this;
	};
	
})(jQuery);/**
 * panel - wooUI 基于jQuery1.4.2+
 * 
 * 问题： this.panel的处理是否合适？ contentMode属性为component时没处理 最大化时ie下高宽不对
 * 
 */
(function($) {
	$.woo.component.subclass('woo.panel', {
		options : {
			title : null,
			iconCls : null,
			width : 'auto',
			height : 'auto',
			left : null,
			top : null,
			cls : null,
			headerCls : null,
			bgcolor : '#fff',
			bodyCls : null,
			style : null,
			/**
			 * 面板内容的地址
			 */
			href : null,
			/**
			 * 内容的放置模式 iframe：以iframe方式来加载href指向地址的内容,该情况下，忽略content属性 content:
			 * 作为innerHTML的方式加载进来,该情况下，如果content不为null，则加载content，否则加载href
			 * component: 作为wooUI的组件渲染
			 */
			contentMode : '',
			/**
			 * 加载内容时，显示的信息
			 */
			loadingMessage : '正在加载...',
			/**
			 * 指定面板内容
			 */
			content : null,
			/**
			 * 是否缓存内容
			 */
			cache : true,
			/**
			 * 是否自适应上一级dom的高宽
			 */
			fit : false,
			/**
			 * 指定面板的适应高宽的对象，确定最大化时的高宽、位置
			 */
			container : null,
			border : true,
			doSize : true, // true to set size and do layout
			noheader : false,
			collapsible : false,
			minimizable : false,
			maximizable : false,
			closable : false,
			collapsed : false,
			minimized : false,
			maximized : false,
			closed : false,

			/**
			 * 自定义工具图标 { iconCls:'',//图标的css类名 // * handler:function(){}//点击触发事件 }
			 */
			tools : null,

			onLoad : $.noop,
			onBeforeOpen : $.noop,
			onOpen : $.noop,
			onBeforeClose : $.noop,
			onClose : $.noop,
			onBeforeDestroy : $.noop,
			onDestroy : $.noop,
			/**
			 * @param {Numbwer}
			 *            width
			 * @param {Numbwer}
			 *            height
			 */
			onResize : $.noop,
			/**
			 * @param {Numbwer}
			 *            left
			 * @param {Numbwer}
			 *            top
			 */
			onMove : $.noop,
			onMaximize : $.noop,
			onRestore : $.noop,
			onMinimize : $.noop,
			onBeforeCollapse : $.noop,
			onBeforeExpand : $.noop,
			onCollapse : $.noop,
			onExpand : $.noop
		},

		_create : function() {
			this._wrapPanel();
			this._addHeader();
			this._setBorder();
			if (this.options.doSize == true) {
				this.el.css('display', 'block');
				this.resize();
			}
			if (this.options.closed == true) {
				this.el.hide();
			} else {
				this.open();
			}
			// wangkun 2011/1/12
			// 为panel绑定窗体改变的事件，当浏览器大小改变时，panel随之改变
			this._bindWindowResize();
		},
		/**
		 * @desc: 当浏览器大小改变时，panel随之改变（前提是options中的fit为true）
		 */
		_bindWindowResize : function() {
			var self = this, opts = this.options, target = this.el;
			if (opts.fit === true) {
				$(window).resize(function() {
							self.resize();
						});
			}
		},
		_removeNode : function(node) {
			node.each(function() {
						$(this).remove();
						if ($.browser.msie) {
							this.outerHTML = '';
						}
					});
		},
		_addHeader : function() {
			var opts = this.options, self = this;

			this._removeNode(this.el.find('>div.panel-header'));
			if (opts.title && !opts.noheader) {
				/*
				 * var header = ['<div class="panel-header"><div
				 * class="panel-title', opts.iconCls?' panel-with-icon':'',
				 * '">', opts.title?opts.title:'', '</div>', opts.iconCls?'<div
				 * class="panel-icon '+opts.iconCls+'"></div>':'', '<div
				 * class="panel-tool">', '</div>', '</div>' ];
				 */
				var header = $('<div class="panel-header"><div class="panel-title">'
						+ opts.title + '</div></div>').prependTo(this.el);
				if (opts.iconCls) {
					header.find('>div.panel-title').addClass('panel-with-icon');
					$('<div class="panel-icon"></div>').addClass(opts.iconCls)
							.appendTo(header);
				}

				var tool = $('<div class="panel-tool"></div>').appendTo(header);

				opts.tools = opts.tools ? opts.tools : [];
				//
				if (opts.collapsible) {
					opts.tools.push({
								iconCls : 'panel-tool-collapse',
								handler : function(el, e) {
									if ($(e.target)
											.hasClass('panel-tool-expand')) {
										el.panel('expand', true);
									} else {
										el.panel('collapse', true);
									}
									return false;
								}
							});
				}

				if (opts.minimizable) {
					opts.tools.push({
								iconCls : 'panel-tool-min',
								handler : function(el, e) {
									el.panel('minimize');
									return false;
								}
							});
				}

				if (opts.maximizable) {
					opts.tools.push({
								iconCls : 'panel-tool-max',
								handler : function(el, e) {
									if ($(e.target)
											.hasClass('panel-tool-restore')) {
										el.panel('restore');
									} else {
										el.panel('maximize');
									}
									return false;
								}
							});
				}

				if (opts.closable) {
					opts.tools.push({
								iconCls : 'panel-tool-close',
								handler : function(el, e) {
									el.panel('close');
									return false;
								}
							});
				}
				this.addTools(opts.tools);
				delete opts.tools;
				this.el.find('>div.panel-body')
						.removeClass('panel-body-noheader');
			} else {
				this.el.find('>div.panel-body').addClass('panel-body-noheader');
			}
		},

		/**
		 * [ { iconCls:'icon-cut', handler:function(el,e){ alert('cut'); } },{
		 * iconCls:'icon-remove', handler:function(el,e){ alert('remove'); } } ]
		 */
		addTools : function(tools) {
			var self = this;
			var tool = this.el.find('>div.panel-header>div.panel-tool');
			if (tools) {
				for (var i = tools.length - 1; i >= 0; i--) {
					var t = $('<div></div>').addClass(tools[i].iconCls)
							.appendTo(tool);
					t.data('handler', tools[i].handler).bind('click',
							function(e) {
								var handler = $(this).data('handler');
								if ($.isFunction(handler)) {
									handler.call(self, self.el, e);
								}
							}).hover(function() {
								$(this).addClass('panel-tool-over');
							}, function() {
								$(this).removeClass('panel-tool-over');
							});
				}
			}
		},

		/**
		 * ['icon-cut','panel-tool-min']s
		 */
		removeTools : function(tools) {
			var tool = this.el.find('>div.panel-header>div.panel-tool');
			if (tools) {
				for (var i = tools.length - 1; i >= 0; i--) {
					tool.find('>div.' + tools[i]).removeData('handler')
							.remove();
				}
			}
		},

		_setBorder : function() {
			var el = this.el;
			if (this.options.border == true) {
				el.find('>div.panel-header')
						.removeClass('panel-header-noborder');
				el.find('>div.panel-body').removeClass('panel-body-noborder');
			} else {
				el.find('>div.panel-header').addClass('panel-header-noborder');
				el.find('>div.panel-body').addClass('panel-body-noborder');
			}
		},

		_wrapPanel : function() {
			var self = this;
			this.el.addClass('panel')
					.wrapInner('<div class="panel-body"></div>');
			this.el.find(">div.panel-body").css("backgroundColor",
					this.options.bgcolor);
			this.el.bind('_resize', function() {
						if (self.options.fit == true) {
							self.resize();
						}
						return false;
					});
			this.loaded = false;
		},

		/**
		 * 重新设置面板尺寸
		 * 
		 * @param {Object}
		 *            param 可选参数 { width:300, height:200, top:10, left:10 }
		 */
		resize : function(param) {
			var opts = this.options, el = this.el, pheader = el
					.find('>div.panel-header'), pbody = el
					.find('>div.panel-body');
			if (param) {
				if (param.width)
					opts.width = param.width;
				if (param.height)
					opts.height = param.height;
				if (param.left != null)
					opts.left = param.left;
				if (param.top != null)
					opts.top = param.top;
			}
			if (opts.fit == true) {
				// var p = opts.container?$(opts.container):el.parent();
				var p = null;
				if (opts.container) {
					p = $(opts.container);
					opts.width = p.width();
					opts.height = p.height();
				} else {
					if (el.parent()[0] == $('body')[0]) {
						p = $(window);
						opts.width = p.outerWidth();
						opts.height = p.outerHeight();
					} else {
						p = el.parent();
						opts.width = p.width();
						opts.height = p.height();
					}
				}
				// opts.width = p.width();
				// opts.height = p.height();
			}
			el.css({
						// position:'absolute',
						left : opts.left,
						top : opts.top
					});
			if (opts.style)
				el.css(opts.style);
			el.addClass(opts.cls);
			pheader.addClass(opts.headerCls);
			pbody.addClass(opts.bodyCls);

			if (!isNaN(opts.width)) {
				if ($.boxModel == true) {
					el.width(opts.width - (el.outerWidth() - el.width()));
					pheader.width(el.width()
							- (pheader.outerWidth() - pheader.width()));
					pbody.width(el.width()
							- (pbody.outerWidth() - pbody.width()));
				} else {
					el.width(opts.width);
					pheader.width(el.width());
					pbody.width(el.width());
				}
			} else {
				el.width('auto');
				pbody.width('auto');
			}
			if (!isNaN(opts.height)) {
				// var height = opts.height -
				// (panel.outerHeight()-panel.height()) - pheader.outerHeight();
				// if ($.boxModel == true){
				// height -= pbody.outerHeight() - pbody.height();
				// }
				// pbody.height(height);

				if ($.boxModel == true) {
					el.height(opts.height - (el.outerHeight() - el.height()));
					pbody.height(el.height() - pheader.outerHeight()
							- (pbody.outerHeight() - pbody.height()));
				} else {
					el.height(opts.height);
					pbody.height(el.height() - pheader.outerHeight());
				}
			} else {
				// wangkun 2011/1/12
				// pbody.height('auto');
				if (el.parent()[0] == $('body')[0]) {
					pbody
							.height($(window).height() - pheader.outerHeight()
									- 2);
				} else {
					pbody.css({
								height : el.parent().height()
							});
				}
			}
			el.css('height', null);

			opts.onResize.apply(el, [opts.width, opts.height]);

			el.find('>div.panel-body>div').triggerHandler('_resize');

		},

		/**
		 * 加载指定内容
		 */
		load : function(content) {
			if (content) {
				this.options.contentMode = 'content';
				this.options.content = content;
			}
			var opts = this.options;
			var el = this.el;
			if (!this.loaded || !opts.cache) {
				var pbody = el.find('>div.panel-body'), tempHtml = "";
				if (pbody.children().length > 0 || $.trim(pbody.html()) != "") {
					tempHtml = pbody.html();
				}

				var s = 'paneltemp=' + new Date().getTime();
				switch (opts.contentMode) {
					case 'iframe' :
						pbody.html($('<div class="panel-loading"></div>')
								.html(opts.loadingMessage));
						pbody
								.html('<iframe width="100%" height="100%" frameborder="0" src="'
										+ $.woo.urlAppend(opts.href, s)
										+ '"></iframe>');
						break;
					case 'content' :
						pbody.html($('<div class="panel-loading"></div>')
								.html(opts.loadingMessage));
						if (opts.content || tempHtml != "") {
							if (opts.content) {
								pbody.html(opts.content);
								if ($.woo.autoParse) {
									$.woo.parse(pbody);
								}
							} else {
								pbody.html(tempHtml);
							}
						} else {
							pbody.load(opts.href, null, function() {
										if ($.woo.autoParse) {
											$.woo.parse(pbody);
										}
										opts.onLoad.apply(this.el, arguments);
									});
						}
						break;
					case 'component' :
						break;
					default :
						if ($.parser) {
							$.parser.parse(pbody);
						}
						break;
				}
				this.loaded = true;
			}
		},
		panel : function() {
			return this.el;
		},
		/**
		 * 获取面板的头部
		 */
		getHeader : function() {
			return this.el.find('>div.panel-header');
		},
		/**
		 * 获取面板的主题
		 */
		getBody : function() {
			return this.el.find('>div.panel-body');
		},
		/**
		 * 设置面板标题
		 */
		setTitle : function(title) {
			this.getHeader().find('div.panel-title').html(title);
		},
		/**
		 * 打开面板
		 */
		open : function(forceOpen) {
			var opts = this.options;
			if (forceOpen != true) {
				if (opts.onBeforeOpen.call(this.el) == false)
					return;
			}
			this.el.show();
			opts.closed = false;
			opts.onOpen.call(this.el);

			if (opts.maximized == true)
				this.maximize();
			if (opts.minimized == true)
				this.minimize();
			if (opts.collapsed == true)
				this.collapse();

			if (!opts.collapsed) {
				this.load();
			}
		},
		close : function(forceClose) {
			var opts = this.options;

			if (forceClose != true) {
				if (opts.onBeforeClose.call(this.el) == false)
					return;
			}
			this.el.hide();
			opts.closed = true;
			opts.onClose.call(this.el);
		},
		refresh : function() {
			var cache = this.options.cache;
			this.options.cache = false;
			this.load();
			this.options.cache = cache;
		},
		move : function(param) {
			var opts = this.options;
			if (param) {
				if (param.left != null)
					opts.left = param.left;
				if (param.top != null)
					opts.top = param.top;
			}
			this.el.css({
						left : opts.left,
						top : opts.top
					});
			opts.onMove.apply(this.el, [opts.left, opts.top]);
		},
		maximize : function() {
			var opts = this.options;
			var tool = this.el.find('>div.panel-header .panel-tool-max');

			if (tool.hasClass('panel-tool-restore'))
				return;

			tool.addClass('panel-tool-restore');
			this.original = {
				width : opts.width,
				height : opts.height,
				left : opts.left,
				top : opts.top,
				fit : opts.fit
			};
			opts.left = 0;
			opts.top = 0;
			opts.fit = true;
			this.resize();
			opts.minimized = false;
			opts.maximized = true;
			this.el.css("position", "absolute");
			opts.onMaximize.call(this.el);
		},
		minimize : function() {
			var opts = this.options;
			var el = this.el;
			this.original = {
				width : opts.width,
				height : opts.height,
				left : opts.left,
				top : opts.top,
				fit : opts.fit
			};
			el.hide();
			opts.minimized = true;
			opts.maximized = false;
			this.el.css("position", "static");
			opts.onMinimize.call(this.el);
		},
		restore : function() {
			var opts = this.options;
			var el = this.el;
			var tool = el.find('>div.panel-header .panel-tool-max');
			// wangkun 2011/1/5
			// 修改原因：当未初始化最大化，最小化按钮时，则无法触发容器大小还原的事件
			// if (!tool.hasClass('panel-tool-restore')&&!opts.minimized)
			// return;

			el.show();
			tool.removeClass('panel-tool-restore');
			var original = this.original;
			opts.width = original.width;
			opts.height = original.height;
			opts.left = original.left;
			opts.top = original.top;
			opts.fit = original.fit;
			this.resize();
			opts.minimized = false;
			opts.maximized = false;
			this.el.css("position", "static");
			opts.onRestore.call(this.el);
		},
		collapse : function(animate) {
			var opts = this.options;
			var el = this.el;
			var body = el.find('>div.panel-body');
			var tool = el.find('>div.panel-header .panel-tool-collapse');

			if (tool.hasClass('panel-tool-expand'))
				return;

			body.stop(true, true); // stop animation
			if (opts.onBeforeCollapse.call(this.el) == false)
				return;

			tool.addClass('panel-tool-expand');
			if (animate == true) {
				body.slideUp('normal', function() {
							opts.collapsed = true;
							opts.onCollapse.call(this.el);
						});
			} else {
				body.hide();
				opts.collapsed = true;
				opts.onCollapse.call(this.el);
			}
		},
		expand : function(animate) {
			var self = this;
			var opts = this.options;
			var el = this.el;
			var body = el.find('>div.panel-body');
			var tool = el.find('>div.panel-header .panel-tool-collapse');

			if (!tool.hasClass('panel-tool-expand'))
				return;

			body.stop(true, true); // stop animation
			if (opts.onBeforeExpand.call(this.el) == false)
				return;

			tool.removeClass('panel-tool-expand');
			if (animate == true) {
				body.slideDown('normal', function() {
							opts.collapsed = false;
							opts.onExpand.call(this.el);
							self.load();
						});
			} else {
				body.show();
				opts.collapsed = false;
				opts.onExpand.call(this.el);
				self.load();
			}
		},
		destroy : function() {
		}
	});
})(jQuery);

/**
 * /** layout - wooUI 基于jQuery1.4.2+
 * 
 * 依赖于以下组件: resizable panel
 * 
 * 需求： 可以像extjs的border布局一样，分center、north、south、east、west5块 可以嵌套布局
 * 可以在某一个组件内布局（window、tab等等） 当某一块面板宽度改变，则
 */
(function($) {
	$.woo.component.subclass('woo.layout', {
				options : {
					resizing : false
				},
				_create : function() {
					
					var container = this.el; 
					state = $.data(container, "layout");
					if (!state) {
						var opts = $.extend({}, {
									fit : container.attr("fit") == "true"
								});
						$.data(container, "layout", {

									options : opts,

									panels : layoutInit(container)
								});
						this._bindEvents(this);
					}
					setSize(container);
				},
				_bindEvents : function() {
					var container = this.el;

					var panels = $.data(container, "layout").panels;
					var cc = $(container);
					if (panels.east.length) {
						panels.east.panel("panel").bind("mouseover", "east",
								collapsePanel);
					}
					if (panels.west.length) {
						panels.west.panel("panel").bind("mouseover", "west",
								collapsePanel);
					}
					if (panels.north.length) {
						panels.north.panel("panel").bind("mouseover", "north",
								collapsePanel);
					}
					if (panels.south.length) {
						panels.south.panel("panel").bind("mouseover", "south",
								collapsePanel);
					}
					panels.center.panel("panel").bind("mouseover", "center",
							collapsePanel);
					var self = this;
					function collapsePanel(e) {
						if (self.options.resizing == true) {
							return;
						}
						if (e.data != "east" && isVisible(panels.east) &&

						isVisible(panels.expandEast)) {
							panels.east.panel("panel").animate({
										left : cc.width()
									}, function() {
										panels.east.panel("close");
									});
						}
						if (e.data != "west" && isVisible(panels.west) &&

						isVisible(panels.expandWest)) {
							panels.west.panel("panel").animate({
										left : -panels.west.panel("options").width
									}, function() {
										panels.west.panel("close");
									});
						}
						if (e.data != "north" && isVisible(panels.north) &&

						isVisible(panels.expandNorth)) {
							panels.north.panel("panel").animate({
										top : -panels.north.panel("options").height
									}, function() {
										panels.north.panel("close");
									});
						}
						if (e.data != "south" && isVisible(panels.south) &&

						isVisible(panels.expandSouth)) {
							panels.south.panel("panel").animate({
										top : cc.height()
									}, function() {
										panels.south.panel("close");
									});
						}
						return false;
					};
				},
				/**
				 * 
				 */
				resize : function(param) {

				},

				bindEvents : function(panel) {

				},

				/**
				 * 
				 */
				getPanel : function(panel) {

				},
				/**
				 * 
				 */
				collapse : function(panel) {

				},
				/**
				 * 
				 */
				expand : function(panel) {

				},

				/**
				 * 
				 */
				showPanel : function(name) {
					expandPanel(this.el, name);
					return $(this);
				},

				hidePanel : function(name) {
					collapsePanel(this.el, name);
					return $(this);
				},
				/**
				 * 
				 */
				hide : function(name) {
					panelHide(this.el, name);
				},
				/**
				 * 
				 */
				show : function(name) {
					panelShow(this.el, name);
				},
				/**
				 * 
				 */
				layout : function() {
					
				}
			});
	/**
	 * @desc:		
	 */
	function setSize(container) {
		var opts = $.data(container, "layout").options;
		var panels = $.data(container, "layout").panels;
		var cc = $(container);
		if (opts.fit == true) {
			var p = cc.parent();
			if(p[0] == $('body')){
				p = $(window);
			}
			
			cc.width(p.width()).height(p.height());
		}
		var cpos = {
			top : 0,
			left : 0,
			width : cc.outerWidth(),
			height : cc.outerHeight()
		};
		function setNorthSize(pp) {
			var temp = 0;
			if (pp) {
				pp.panel("resize", {
						width : cc.width(),
						height : pp.panel("option", "height"),
						left : 0,
						top : 0
					});
					temp = pp.panel("option", "height");
			}
			
			cpos.top += temp;
			cpos.height -= temp;
		};
		if (!isVisible(panels.north)) {
			setNorthSize(panels.expandNorth);
		} else {
			setNorthSize(panels.north);
		}
		function setSouthSize(pp) {
			var temp = 0;
			if (pp) {
				pp.panel("resize", {
							width : cc.width(),
							height : pp.panel("option", "height"),
							left : 0,
							top : cc.height() - pp.panel("option", "height")
						});
				temp = pp.panel("option", "height");
			}
			cpos.height -= temp;
		};
		if (!isVisible(panels.south)) {
			setSouthSize();
		} else {
			setSouthSize(panels.south);
		}
		function setEastSize(pp) {
			var temp = 0;
			if (pp && pp.length != 0) {
				pp.panel("resize", {
						width : pp.panel("option", "width"),
						height : cpos.height,
						left : cc.width() - pp.panel("option", "width"),
						top : cpos.top
					});
					temp = pp.panel("option", "width");
			}
			
			cpos.width -= temp
		};
		if (isVisible(panels.expandEast)) {
			setEastSize(panels.expandEast);
		} else {
			if(isVisible(panels.east)){
				setEastSize(panels.east);
			}else{
				setEastSize();
			}
		}
		function setWestSize(pp) {
			var temp = 0;
			if (pp && pp.length != 0) {
				pp.panel("resize", {
						width : pp.panel("option", "width"),
						height : cpos.height,
						left : 0,
						top : cpos.top
					});
					temp = pp.panel("option", "width");
			}
			
			cpos.left += temp;
			cpos.width -= temp;
		};
		if (isVisible(panels.expandWest)) {
			setWestSize(panels.expandWest);
		} else {
			if(isVisible(panels.west)){
				setWestSize(panels.west);
			}else{
				setWestSize();
			}
		}
		panels.center.panel("resize", cpos);
	};
	function layoutInit(container) {
		var cc = $(container);
		if (cc[0].tagName == "BODY") {
			$("html").css({
						height : "100%",
						overflow : "hidden"
					});
			$("body").css({
						height : "100%",
						overflow : "hidden",
						border : "none"
					});
		}
		cc.addClass("layout");
		cc.css({
					margin : 0,
					padding : 0
				});
		function createPanel(dir) {
			var pp = $(">div[region=" + dir + "]", container)
					.addClass("layout-body");
			var toolCls = null;
			if (dir == "north") {
				toolCls = "layout-button-up";
			} else {
				if (dir == "south") {
					toolCls = "layout-button-down";
				} else {
					if (dir == "east") {
						toolCls = "layout-button-right";
					} else {
						if (dir == "west") {
							toolCls = "layout-button-left";
						}
					}
				}
			}
			var cls = "layout-panel layout-panel-" + dir;
			if (pp.attr("split") == "true") {
				cls += " layout-split-" + dir;
			}
			// Deasel:
			var panelHtml = pp.html(), //
			ppClass = pp.attr("class"), //
			ppStyle = pp.attr("style"),
			ppTitle = "";
			if(dir.toLocaleLowerCase() == "north" || dir.toLocaleLowerCase() == "south"){
				ppTitle = "";
			}else{
				ppTitle = pp.attr("title");
			}
			pp.panel({
						// Deasel
						title : ppTitle,
						contentMoe : "content",
						content : panelHtml,
						width : pp.outerWidth(),
						height : pp.outerHeight(),
						cls : cls,
						doSize : false,
						border : (pp.attr("border") == "false" ? false : true),
						tools : [{
									iconCls : toolCls,
									handler : function(panel, e) {
										collapsePanel(container, dir);
									}
								}]
					});

			// Deasel:
			$(" > div.panel-body", pp).addClass(ppClass).attr("style", ppStyle);
			pp.css({
						margin : "0",
						padding : "0"
					});

			if (pp.attr("split") == "true") {
				var panel = pp.panel("panel");
				var handles = "";
				if (dir == "north") {
					handles = "s";
				}
				if (dir == "south") {
					handles = "n";
				}
				if (dir == "east") {
					handles = "w";
				}
				if (dir == "west") {
					handles = "e";
				}
				panel.resizable({
					handles : handles,
					onStartResize : function(e) {
						resizing = true;
						if (dir == "north" || dir == "south") {
							var proxy = $(">div.layout-split-proxy-v",
									container);
						} else {
							var proxy = $(">div.layout-split-proxy-h",
									container);
						}
						var top = 0, left = 0, width = 0, height = 0;
						var pos = {
							display : "block"
						};
						if (dir == "north") {
							pos.top = parseInt(panel.css("top"))
									+ panel.outerHeight() - proxy.height();
							pos.left = parseInt(panel.css("left"));
							pos.width = panel.outerWidth();
							pos.height = proxy.height();
						} else {
							if (dir == "south") {
								pos.top = parseInt(panel.css("top"));
								pos.left = parseInt(panel.css("left"));
								pos.width = panel.outerWidth();
								pos.height = proxy.height();
							} else {
								if (dir == "east") {
									pos.top = parseInt(panel.css("top")) || 0;
									pos.left = parseInt(panel.css("left")) || 0;
									pos.width = proxy.width();
									pos.height = panel.outerHeight();
								} else {
									if (dir == "west") {
										pos.top = parseInt(panel.css("top"))
												|| 0;
										pos.left = panel.outerWidth()
												- proxy.width();
										pos.width = proxy.width();
										pos.height = panel.outerHeight();
									}
								}
							}
						}
						proxy.css(pos);
						$("<div class=\"layout-mask\"></div>").css({
									left : 0,
									top : 0,
									width : cc.width(),
									height : cc.height()
								}).appendTo(cc);
					},
					onResize : function(e) {
						if (dir == "north" || dir == "south") {
							var proxy = $(">div.layout-split-proxy-v",
									container);
							proxy.css("top", e.pageY
											- $(container).offset().top
											- proxy.height() / 2);
						} else {
							var proxy = $(">div.layout-split-proxy-h", _274);
							proxy.css("left", e.pageX
											- $(container).offset().left
											- proxy.width() / 2);
						}
						return false;
					},
					onStopResize : function() {
						$(">div.layout-split-proxy-v", container).css(
								"display", "none");
						$(">div.layout-split-proxy-h", container).css(
								"display", "none");
						var opts = pp.panel("options");
						opts.width = panel.outerWidth();
						opts.height = panel.outerHeight();
						opts.left = panel.css("left");
						opts.top = panel.css("top");
						pp.panel("resize");
						setSize(container);
						resizing = false;
						cc.find(">div.layout-mask").remove();
					}
				});
			}
			return pp;
		};
		$("<div class=\"layout-split-proxy-h\"></div>").appendTo(cc);
		$("<div class=\"layout-split-proxy-v\"></div>").appendTo(cc);
		var panels = {
			center : createPanel("center")
		};
		panels.north = createPanel("north");
		panels.south = createPanel("south");
		panels.east = createPanel("east");
		panels.west = createPanel("west");
		$(container).bind("_resize", function() {
					var opts = $.data(container, "layout").options;
					if (opts.fit == true) {
						setSize(container);
					}
					return false;
				});
		$(window).resize(function() {
					setSize(container);
				});
		return panels;
	};
	//
	function collapsePanel(container, region) {
		var panels = $.data(container, "layout").panels;
		var cc = $(container);
		function createExpandPanel(dir) {
			var icon;
			if (dir == "east") {
				icon = "layout-button-left";
			} else {
				if (dir == "west") {
					icon = "layout-button-right";
				} else {
					if (dir == "north") {
						icon = "layout-button-down";
					} else {
						if (dir == "south") {
							icon = "layout-button-up";
						}
					}
				}
			}
			var p = $("<div></div>").appendTo(cc).panel({
						cls : "layout-expand",
						title : "&nbsp;",
						closed : true,
						doSize : false,
						tools : [{
									iconCls : icon,
									handler : function() {
										expandPanel(container, region);
									}
								}]
					});
			p.panel("panel").hover(function() {
						$(this).addClass("layout-expand-over");
					}, function() {
						$(this).removeClass("layout-expand-over");
					});
			return p;
		};
		if (region == "east") {
			// onBeforeClose
			if (panels.east.panel("option", "onBeforeClose").call(panels.east) == false) {
				return;
			}
			panels.center.panel("resize", {
						width : panels.center.panel("option", "width")
								+ panels.east.panel("option", "width") - 28
					});
			if (!panels.expandEast) {
				panels.expandEast = createExpandPanel("east");
				panels.expandEast.panel("panel").click(function() {
					panels.east.panel("open").panel("resize", {
								left : cc.width()
							});
					panels.east.panel("panel").animate({
						left : cc.width()
								- panels.east.panel("option", "width")
					});
					return false;
				});
			}
			panels.east.panel("panel").animate({
						left : cc.width()
					}, function() {
						panels.east.panel("close");
						panels.expandEast.panel("open").panel("resize", {
									top : panels.east.panel("option", "top"),
									left : cc.width() - 28,
									width : 28,
									height : panels.east.panel("option",
											"height")
								});
						panels.east.panel("option", "onCollapse")
								.call(panels.east);
					});
		} else {
			if (region == "west") {
				if (panels.west.panel("option", "onBeforeClose")
						.call(panels.west) == false) {
					return;
				}
				panels.center.panel("resize", {
							width : panels.center.panel("option", "width")
									+ panels.west.panel("option", "width") - 28,
							left : 28
						});
				if (!panels.expandWest) {
					panels.expandWest = createExpandPanel("west");
					panels.expandWest.panel("panel").click(function() {
						panels.west.panel("open").panel("resize", {
									left : -panels.west
											.panel("option", "width")
								});
						panels.west.panel("panel").animate({
									left : 0
								});
						return false;
					});
				}
				panels.west.panel("panel").animate({
							left : -panels.west.panel("option", "width")
						}, function() {
							panels.west.panel("close");
							panels.expandWest.panel("open").panel("resize", {
										top : panels.west
												.panel("option", "top"),
										left : 0,
										width : 28,
										height : panels.west.panel("option",
												"height")
									});
							panels.west.panel("option", "onCollapse")
									.call(panels.west);
						});

			} else {
				if (region == "north") {
					if (panels.north.panel("option", "onBeforeCollapse")
							.call(panels.north) == false) {
						return;
					}
					var hh = cc.height() - 28;
					if (isVisible(panels.expandSouth)) {
						hh -= panels.expandSouth.panel("option", "height");
					} else {
						if (isVisible(panels.south)) {
							hh -= panels.south.panel("option", "height");
						}
					}
					panels.center.panel("resize", {
								top : 28,
								height : hh
							});
					panels.east.panel("resize", {
								top : 28,
								height : hh
							});
					panels.west.panel("resize", {
								top : 28,
								height : hh
							});
					if (isVisible(panels.expandEast)) {
						panels.expandEast.panel("resize", {
									top : 28,
									height : hh
								});
					}
					if (isVisible(panels.expandWest)) {
						panels.expandWest.panel("resize", {
									top : 28,
									height : hh
								});
					}
					panels.north.panel("panel").animate({
								top : -panels.north.panel("option", "height")
							}, function() {
								panels.north.panel("close");
								panels.expandNorth.panel("open").panel(
										"resize", {
											top : 0,
											left : 0,
											width : cc.width(),
											height : 28
										});
								panels.north.panel("option", "onCollapse")
										.call(panels.north);
							});
					if (!panels.expandNorth) {
						panels.expandNorth = createExpandPanel("north");
						panels.expandNorth.panel("panel").click(function() {
							panels.north.panel("open").panel("resize", {
										top : -panels.north.panel("option",
												"height")
									});
							panels.north.panel("panel").animate({
										top : 0
									});
							return false;
						});
					}
				} else {
					if (region == "south") {
						if (panels.south.panel("option", "onBeforeCollapse")
								.call(panels.south) == false) {
							return;
						}
						var hh = cc.height() - 28;
						if (isVisible(panels.expandNorth)) {
							hh -= panels.expandNorth.panel("option", "height");
						} else {
							if (isVisible(panels.north)) {
								hh -= panels.north.panel("option", "height");
							}
						}
						panels.center.panel("resize", {
									height : hh
								});
						panels.east.panel("resize", {
									height : hh
								});
						panels.west.panel("resize", {
									height : hh
								});
						if (isVisible(panels.expandEast)) {
							panels.expandEast.panel("resize", {
										height : hh
									});
						}
						if (isVisible(panels.expandWest)) {
							panels.expandWest.panel("resize", {
										height : hh
									});
						}
						panels.south.panel("panel").animate({
									top : cc.height()
								}, function() {
									panels.south.panel("close");
									panels.expandSouth.panel("open").panel(
											"resize", {
												top : cc.height() - 28,
												left : 0,
												width : cc.width(),
												height : 28
											});
									panels.south.panel("option", "onCollapse")
											.call(panels.south);
								});
						if (!panels.expandSouth) {
							panels.expandSouth = createExpandPanel("south");
							panels.expandSouth.panel("panel").click(function() {
								panels.south.panel("open").panel("resize", {
											top : cc.height()
										});
								panels.south.panel("panel").animate({
									top : cc.height()
											- panels.south.panel("option",
													"height")
								});
								return false;
							});
						}
					}
				}
			}
		}
	};

	/**
	 * @desc: 隐藏某一区域
	 */
	function panelShow(target, region) {
		var panels = $.data(target, "layout").panels;
		panels[region].panel("open");
		setSize(target);
	}
	/**
	 * @desc: 显示某一区域
	 */
	function panelHide(target, region) {
		var panels = $.data(target, "layout").panels;
		panels[region].panel("close");
		setSize(target);
	}
	function expandPanel(container, region) {
		var panels = $.data(container, "layout").panels;
		var cc = $(container);
		if (region == "east" && panels.expandEast) {
			if (panels.east.panel("option", "onBeforeExpand").call(panels.east) == false) {
				return;
			}
			panels.expandEast.panel("close");
			panels.east.panel("panel").stop(true, true);
			panels.east.panel("open").panel("resize", {
						left : cc.width()
					});
			panels.east.panel("panel").animate({
						left : cc.width()
								- panels.east.panel("option", "width")
					}, function() {
						setSize(container);
						panels.east.panel("option", "onExpand")
								.call(panels.east);
					});
		} else {
			if (region == "west" && panels.expandWest) {
				if (panels.west.panel("option", "onBeforeExpand")
						.call(panels.west) == false) {
					return;
				}
				panels.expandWest.panel("close");
				panels.west.panel("panel").stop(true, true);
				panels.west.panel("open").panel("resize", {
							left : -panels.west.panel("option", "width")
						});
				panels.west.panel("panel").animate({
							left : 0
						}, function() {
							setSize(container);
							panels.west.panel("option", "onExpand")
									.call(panels.west);
						});
			} else {
				if (region == "north" && panels.expandNorth) {
					if (panels.north.panel("option", "onBeforeExpand")
							.call(panels.north) == false) {
						return;
					}
					panels.expandNorth.panel("close");
					panels.north.panel("panel").stop(true, true);
					panels.north.panel("open").panel("resize", {
								top : -panels.north.panel("option", "height")
							});
					panels.north.panel("panel").animate({
								top : 0
							}, function() {
								setSize(container);
								panels.north.panel("option", "onExpand")
										.call(panels.north);
							});
				} else {
					if (region == "south" && panels.expandSouth) {
						if (panels.south.panel("option", "onBeforeExpand")
								.call(panels.south) == false) {
							return;
						}
						panels.expandSouth.panel("close");
						panels.south.panel("panel").stop(true, true);
						panels.south.panel("open").panel("resize", {
									top : cc.height()
								});
						panels.south.panel("panel").animate({
							top : cc.height()
									- panels.south.panel("option", "height")
						}, function() {
							setSize(container);
							panels.south.panel("option", "onExpand")
									.call(panels.south);
						});
					}
				}
			}
		}
	};
	function isVisible(pp) {
		if (!pp) {
			return false;
		}
		if (pp.length) {
			return pp.panel("panel").is(":visible");
		} else {
			return false;
		}
	}
})(jQuery);
/**
 * linkbutton - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 */
(function($){
	
	function createButton(target) {

		var opts = $.data(target, 'linkbutton').options;
		$(target).empty();
		$(target).addClass('l-btn');
		if (opts.id){
			$(target).attr('id', opts.id);
		} else {
			$(target).removeAttr('id');
		}
		if (opts.plain){
			$(target).addClass('l-btn-plain');
		} else {
			$(target).removeClass('l-btn-plain');
		}
		if (opts.text){
			$(target).html(opts.text).wrapInner(
					'<span class="l-btn-left">' +
					'<span class="l-btn-text">' +
					'</span>' +
					'</span>'
			);
			if (opts.iconCls){
				$(target).find('.l-btn-text').addClass(opts.iconCls).css('padding-left', '20px');
			}
		} else {
			$(target).html('&nbsp;').wrapInner(
					'<span class="l-btn-left">' +
					'<span class="l-btn-text">' +
					'<span class="l-btn-empty"></span>' +
					'</span>' +
					'</span>'
			);
			if (opts.iconCls){
				$(target).find('.l-btn-empty').addClass(opts.iconCls);
			}
		}
		
		setDisabled(target, opts.disabled);
	}
	
	function setDisabled(target, disabled){
		var state = $.data(target, 'linkbutton');
		if (disabled){
			state.options.disabled = true;
			var href = $(target).attr('href');
			if (href){
				state.href = href;
				$(target).attr('href', 'javascript:void(0)');
			}
			var onclick = $(target).attr('onclick');
			if (onclick) {
				state.onclick = onclick;
				$(target).attr('onclick', null);
			}
			$(target).addClass('l-btn-disabled');
		} else {
			state.options.disabled = false;
			if (state.href) {
				$(target).attr('href', state.href);
			}
			if (state.onclick) {
				target.onclick = state.onclick;
			}
			$(target).removeClass('l-btn-disabled');
		}
	}
	
	$.fn.linkbutton = function(options){
		if (typeof options == 'string'){
			switch(options){
			case 'options':
				return $.data(this[0], 'linkbutton').options;
			case 'enable':
				return this.each(function(){
					setDisabled(this, false);
				});
			case 'disable':
				return this.each(function(){
					setDisabled(this, true);
				});
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'linkbutton');
			if (state){
				$.extend(state.options, options);
			} else {
				var t = $(this);
				$.data(this, 'linkbutton', {
					options: $.extend({}, $.fn.linkbutton.defaults, {
						id: t.attr('id'),
						disabled: (t.attr('disabled') ? true : undefined),
						plain: (t.attr('plain') ? t.attr('plain') == 'true' : undefined),
						text: $.trim(t.html()),
						iconCls: t.attr('icon')
					}, options)
				});
				t.removeAttr('disabled');
			}
			createButton(this);
		});
	};
	
	$.fn.linkbutton.defaults = {
			id: null,
			disabled: false,
			plain: false,
			text: '',
			iconCls: null
	};
	
})(jQuery);
/**
 * pagination - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	linkbutton
 * 
 */
(function($){
	function buildToolbar(target){
		var opts = $.data(target, 'pagination').options;
		
		var pager = $(target).addClass('pagination').empty();
		var t = $('<table cellspacing="0" cellpadding="0" border="0"><tr></tr></table>').appendTo(pager);
		var tr = $('tr', t);
		
		if (opts.showPageList){
			var ps = $('<select class="pagination-page-list"></select>');
			for(var i=0; i<opts.pageList.length; i++) {
				$('<option></option>')
						.text(opts.pageList[i])
						.attr('selected', opts.pageList[i]==opts.pageSize ? 'selected' : '')
						.appendTo(ps);
			}
			$('<td></td>').append(ps).appendTo(tr);
			
			opts.pageSize = parseInt(ps.val());
			
			$('<td><div class="pagination-btn-separator"></div></td>').appendTo(tr);
		}
		
		$('<td><a href="javascript:void(0)" icon="pagination-first"></a></td>').appendTo(tr);
		$('<td><a href="javascript:void(0)" icon="pagination-prev"></a></td>').appendTo(tr);
		$('<td><div class="pagination-btn-separator"></div></td>').appendTo(tr);
		
		$('<span style="padding-left:6px;"></span>')
				.html(opts.beforePageText)
				.wrap('<td></td>')
				.parent().appendTo(tr);
		$('<td><input class="pagination-num" type="text" value="1" size="2"></td>').appendTo(tr);
		$('<span style="padding-right:6px;"></span>')
//				.html(opts.afterPageText)
				.wrap('<td></td>')
				.parent().appendTo(tr);
		
		$('<td><div class="pagination-btn-separator"></div></td>').appendTo(tr);
		$('<td><a href="javascript:void(0)" icon="pagination-next"></a></td>').appendTo(tr);
		$('<td><a href="javascript:void(0)" icon="pagination-last"></a></td>').appendTo(tr);
		
		if (opts.showRefresh){
			$('<td><div class="pagination-btn-separator"></div></td>').appendTo(tr);
			$('<td><a href="javascript:void(0)" icon="pagination-load"></a></td>').appendTo(tr);
			
//			if (opts.loading) {
//				$('<td><a class="pagination-refresh" href="javascript:void(0)" icon="pagination-loading"></a></td>').appendTo(tr);
//			} else {
//				$('<td><a class="pagination-refresh" href="javascript:void(0)" icon="pagination-load"></a></td>').appendTo(tr);
//			}
		}
		
		if (opts.buttons){
			$('<td><div class="pagination-btn-separator"></div></td>').appendTo(tr);
			for(var i=0; i<opts.buttons.length; i++){
				var btn = opts.buttons[i];
				if (btn == '-') {
					$('<td><div class="pagination-btn-separator"></div></td>').appendTo(tr);
				} else {
					var td = $('<td></td>').appendTo(tr);
					$('<a href="javascript:void(0)"></a>')
							.addClass('l-btn')
							.css('float', 'left')
							.text(btn.text || '')
							.attr('icon', btn.iconCls || '')
							.bind('click', eval(btn.handler || function(){}))
							.appendTo(td)
							.linkbutton({plain:true});
				}
			}
		}
		
		$('<div class="pagination-info"></div>').appendTo(pager);
		$('<div style="clear:both;"></div>').appendTo(pager);
		
		
		$('a[icon^=pagination]', pager).linkbutton({plain:true});
		
		pager.find('a[icon=pagination-first]').unbind('.pagination').bind('click.pagination', function(){
			if (opts.pageNumber > 1) selectPage(target, 1);
		});
		pager.find('a[icon=pagination-prev]').unbind('.pagination').bind('click.pagination', function(){
			if (opts.pageNumber > 1) selectPage(target, opts.pageNumber - 1);
		});
		pager.find('a[icon=pagination-next]').unbind('.pagination').bind('click.pagination', function(){
			var pageCount = Math.ceil(opts.total/opts.pageSize);
			if (opts.pageNumber < pageCount) selectPage(target, opts.pageNumber + 1);
		});
		pager.find('a[icon=pagination-last]').unbind('.pagination').bind('click.pagination', function(){
			var pageCount = Math.ceil(opts.total/opts.pageSize);
			if (opts.pageNumber < pageCount) selectPage(target, pageCount);
		});
		pager.find('a[icon=pagination-load]').unbind('.pagination').bind('click.pagination', function(){
			if (opts.onBeforeRefresh.call(target, opts.pageNumber, opts.pageSize) != false){
				selectPage(target, opts.pageNumber);
				opts.onRefresh.call(target, opts.pageNumber, opts.pageSize);
			}
		});
		pager.find('input.pagination-num').unbind('.pagination').bind('keydown.pagination', function(e){
			if (e.keyCode == 13){
				var pageNumber = parseInt($(this).val()) || 1;
				selectPage(target, pageNumber);
			}
		});
		pager.find('.pagination-page-list').unbind('.pagination').bind('change.pagination', function(){
			opts.pageSize = $(this).val();
			opts.onChangePageSize.call(target, opts.pageSize);
			
			var pageCount = Math.ceil(opts.total/opts.pageSize);
			selectPage(target, opts.pageNumber);
		});
	}
	
	function selectPage(target, page){
		var opts = $.data(target, 'pagination').options;
		var pageCount = Math.ceil(opts.total/opts.pageSize);
		var pageNumber = page;
		if (page < 1) pageNumber = 1;
		if (page > pageCount) pageNumber = pageCount;
		opts.onSelectPage.call(target, pageNumber, opts.pageSize);
		opts.pageNumber = pageNumber;
		showInfo(target);
	}
	
	function showInfo(target){
		var opts = $.data(target, 'pagination').options;
		
		var pageCount = Math.ceil(opts.total/opts.pageSize);
		var num = $(target).find('input.pagination-num');
		num.val(opts.pageNumber);
		num.parent().next().find('span').html(opts.afterPageText.replace(/{pages}/, pageCount));
		
		var pinfo = opts.displayMsg;
		pinfo = pinfo.replace(/{from}/, opts.pageSize*(opts.pageNumber-1)+1);
		pinfo = pinfo.replace(/{to}/, Math.min(opts.pageSize*(opts.pageNumber), opts.total));
		pinfo = pinfo.replace(/{total}/, opts.total);
		
		$(target).find('.pagination-info').html(pinfo);
		
		$('a[icon=pagination-first],a[icon=pagination-prev]', target).linkbutton({
			disabled: (opts.pageNumber == 1)
		});
		$('a[icon=pagination-next],a[icon=pagination-last]', target).linkbutton({
			disabled: (opts.pageNumber == pageCount)
		});
		
		if (opts.loading){
			$(target).find('a[icon=pagination-load]').find('.pagination-load').addClass('pagination-loading');
		} else {
			$(target).find('a[icon=pagination-load]').find('.pagination-load').removeClass('pagination-loading');
		}
	}
	
	function setLoadStatus(target, loading){
		var opts = $.data(target, 'pagination').options;
		opts.loading = loading;
		if (opts.loading){
			$(target).find('a[icon=pagination-load]').find('.pagination-load').addClass('pagination-loading');
		} else {
			$(target).find('a[icon=pagination-load]').find('.pagination-load').removeClass('pagination-loading');
		}
	}
	
	$.fn.pagination = function(options) {
		if (typeof options == 'string'){
			switch(options){
				case 'options':
					return $.data(this[0], 'pagination').options;
				case 'loading':
					return this.each(function(){
						setLoadStatus(this, true);
					});
				case 'loaded':
					return this.each(function(){
						setLoadStatus(this, false);
					});
			}
		}
		
		options = options || {};
		return this.each(function(){
			var opts;
			var state = $.data(this, 'pagination');
			if (state) {
				opts = $.extend(state.options, options);
			} else {
				opts = $.extend({}, $.fn.pagination.defaults, options);
				$.data(this, 'pagination', {
					options: opts
				});
			}
			
			buildToolbar(this);
			showInfo(this);
			
		});
	};
	
	$.fn.pagination.defaults = {
		total: 1,
		pageSize: 10,
		pageNumber: 1,
		pageList: [10,20,30,50],
		loading: false,
		buttons: null,
		showPageList: true,
		showRefresh: true,
		
		onSelectPage: function(pageNumber, pageSize){},
		onBeforeRefresh: function(pageNumber, pageSize){},
		onRefresh: function(pageNumber, pageSize){},
		onChangePageSize: function(pageSize){},
		
		beforePageText: 'Page',
		afterPageText: 'of {pages}',
		displayMsg: 'Displaying {from} to {to} of {total} items'
	};
})(jQuery);/**
 * /** menu - wooUI 基于jQuery1.4.2+
 * 
 * 需求： 1、可以构建层级菜单 2、菜单项前面可以加图标 3、菜单项可以分组 4、菜单项的点击事件可以自定义
 * 5、菜单要能盖住iframe、flash文件、下拉菜单
 */
(function($) {
	$.woo.component.subclass('woo.menu', {
		options : {
			/**
			 * @desc: ajax获取菜单内容的方式，默认为post提交
			 * @type: {String}
			 */
			method : "post",
			/**
			 * @desc: ajax获取菜单内容的路径，默认为空字符串
			 * @type: {String}
			 */
			url : "menu_data.json",
			/**
			 * @desc: ajax获取菜单内容的传入参数，默认为空字符串
			 * @type: {Object}
			 */
			data : {},
			/**
			 * @desc: 菜单组件的纵轴z-index
			 * @type: {Number}
			 */
			zIndex : 110000,
			/**
			 * @desc: 菜单显示位置的横坐标
			 * @type: {Number}
			 */
			left : 0,
			/**
			 * @desc: 菜单显示位置的纵坐标
			 * @type: {Number}
			 */
			top : 0,
			/**
			 * @desc: ajax获取菜单内容的路径，默认为空字符串
			 * @type: {String}
			 */
			prefixID : 'menu-',
			/**
			 * @desc: ajax获取菜单内容的路径，默认为空字符串
			 * @type: {String}
			 */
			defaultTarget : '_blank',
			/**
			 * @type {String}
			 */
			tempType : "",
			/**
			 * @desc 菜单显示时触发的事件
			 * @type {function}
			 */
			onShow : $.noop,
			/**
			 * @desc 菜单隐藏时触发的事件
			 * @type {function}
			 */
			onHide : $.noop
		},

		_create : function() {
			var self = this, target = this.el;
			target.appendTo('body').addClass('menu-top').attr("tabIndex", "-1"); // the
			// top
			// menu
			// 判断菜单初始化时的dom结构是否已给定。若未给定，则使用ajax方式获取json串进行解析
			if (target.children().length <= 0) {
				if ($.data(target[0], "menuData")) {
					self.loadData($.data(target[0], "menuData"));
				} else {
					// 若DOM结构为空，且url也为空，则无法初始化菜单内容，直接退出方法
					if ($.trim(self.options.url) === "") {
						return false;
					}
					self.getDomByAjax();
				}
			}
			// 菜单初始化
			this.menus = [];
			this.adjust(target);
			var menu = null;
			for (var i = 0; i < this.menus.length; i++) {
				menu = this.menus[i];
				this.wrapMenu(menu);
				menu.find('>div.menu-item').each(function() {
							self.bindMenuItemEvent($(this));
						});
				// menu.find('div.menu-item').click(function() {
				// // only the sub menu clicked can hide all menus
				// if (!this.submenu) {
				// // self.hide();
				// // if($(' > div.menu-rightarrow',$(this)).length
				// // > 0){
				// // //$(this).trigger("mouseover");
				// // }else{
				// $('div.menu, div.menu-shadow').css({
				// display : 'none'
				// });
				// // }
				// }
				// return false;
				// });
			}
			var self = this;
			target.css({
						left : this.options.left,
						top : this.options.top
					}).blur(function() {
				if ($('div[conid="' + self.el.attr('id') + '"]:visible').length <= 0) {
					self.hide();
				} else {
					return false;
				}
			});
			this.bindClickEvent();
		},
		/**
		 * @desc: 绑定当前菜单的点击事件
		 */
		bindClickEvent : function() {
			var self = this;
			this.el.click(function(e) {
						var eTarget = e.target, item = null;
						//若事件源为内容div（div.menu-text），则换成菜单对象
						if($(eTarget).hasClass('menu-text')){
							eTarget = $(e.target).parent()[0];
						}
					
						$(' > div.menu-item', self.el).each(function() {
									item = $(this)[0];
									if (item == eTarget) {
										if ($.data(item, 'action')) {
											($.data(item, 'action'))();
											return false;
										}
									}
								});

						$('div.menu, div.menu-shadow').css({
									display : 'none'
								});
					});
		},
		/**
		 * @desc: 通过ajax的方式获取菜单的json串，并解析为DOM结构追加入页面中
		 */
		getDomByAjax : function() {
			var self = this, opts = this.options, html = "";
			$.ajax({
						type : opts.method,
						url : opts.url,
						async : false,
						data : opts.data,
						dataType : 'plain',
						success : function(data) {
							var json = eval("(" + data + ")");
							self.loadData(json);
						},
						error : function() {
							alert("ajax error");
						}
					});
		},
		/**
		 * @desc： 根据json数据初始化DOM结构
		 * 
		 * @param: {Object} json {Number} 当前菜单的层级
		 * @return: {String} 解析完json串后，返回的DOM串
		 */
		loadData : function(data) {
			var tempHtml = [], target = this.el, item = null;
			for (var i = 0, len = data.length; i < len; i++) {
				item = data[i];

				if (item.id) {
					tempHtml = ['<div id="' + item.id + '"',
							' iconCls="' + item.iconCls + '">'];
					if (item.children) {
						tempHtml.push('<span>' + item.text + '</span><div');
						tempHtml.push(' style="width:120px;"></div>');
					} else {
						if (item.url) {
							tempHtml.push('<a href="' + item.url + '" target="'
									+ this.options.defaultTarget + '">');
							tempHtml.push(item.text + '</a></div>');
						} else {
							tempHtml.push(item.text + '</div>');
						}
					}
					target.append(tempHtml.join(""));
					var m = $("#" + item.id);
					if (item.children) {
						$.data(m[0], "childData", item.children);
						// $("#" + item.id).data("childData", item.children);
					}
					// 菜单方法的绑定
					if (item.action) {
						$.data(m[0], 'action', item.action);
						// $("#" + item.id).data('action', item.action);
					}
				} else {
					target.append('<div class="menu-sep"></div>');
				}
			}
		},
		/**
		 * @desc:
		 */
		add : function(item) {

		},
		/**
		 * @desc: 遍历子节点，追加入DOM结构中
		 * @param: {Object}
		 */
		adjust : function(menu) {
			var self = this, item = null;
			this.menus.push(menu);
			menu.find('>div').each(function() {
						item = $(this), submenu = item.find('>div');
						if (submenu.length) {
							submenu.insertAfter(self.el);
							item[0].submenu = submenu;
							// self.adjust(submenu);
						}
					});
		},
		/**
		 * @desc: 为菜单绑定事件
		 * @param: {JqueryObj} 菜单对象
		 */
		bindMenuItemEvent : function(item) {
			var self = this, itemId = item.attr('id');
			// 菜单的悬停事件
			item.hover(function(e) {
				// self.el.focus();
				// 隐藏其他菜单，显示当前菜单
				item.addClass('menu-active').siblings()
						.removeClass('menu-active');
				// 判断是否有子菜单，若有子菜单则初始化并显示
				if ($(" > div.menu-rightarrow", item).length > 0) {
					if ($.data(item[0], "childData")) {
						// 初始化子菜单
						var itemObj = $('<div id="' + itemId + '-son" conid="'
								+ self.el.attr('id')
								+ '" style="width:120px;"></div>');
						$(" > div.menu-text", item).append(itemObj);
						$.data(itemObj[0], "menuData", item.data("childData"));
						itemObj.menu({
									zIndex : self.options.zIndex + 1
								}).css({
									zIndex : self.options.zIndex + 1
								});

						// 初始化菜单之后，销毁子菜单数据
						item.removeData('childData');
						// 显示子菜单
						itemObj.menu('show', {
									left : item.offset().left + self.el.width(),
									top : item.offset().top
								});
					} else {
						$('div#' + itemId + '-son').menu('show', {
									left : item.offset().left + self.el.width(),
									top : item.offset().top
								});
					}
				} else {
					$('div[conid="' + self.el.attr('id') + '"]').each(
							function() {
								$(this).menu('hide');
							});
				}
			}, function() {
				item.removeClass('menu-active');
			});
		},
		/**
		 * @desc: 隐藏指定菜单的子菜单
		 */
		hideSubMenu : function(item) {
			item.css({
						display : 'none'
					});
			// 当前菜单失去焦点时，判断其子菜单是否显示
			// 若子菜单未显示，则隐藏当前菜单
			if ($('#' + item.attr('id') + '-son').css("display") == "none") {
				item.menu('hide');
				var itemIndex = parseInt(item.css("z-index")), menuIndex = 0;
				item.siblings().each(function() {
							menuIndex = parseInt($(this).css("z-index"))
							if (menuIndex > itemIndex) {
								$(this).css({
											display : "none"
										});
							}
						});
			}
		},
		/**
		 * wrap a menu and set it's status to hidden the menu not include sub
		 * menus
		 */
		wrapMenu : function(menu) {
			menu.addClass('menu').find('>div').each(function() {
				var item = $(this);
				if (item.hasClass('menu-sep')) {
					item.html('&nbsp;');
				} else {
					var text = item.addClass('menu-item').html();
					item.empty().append($('<div class="menu-text"></div>')
							.html(text));
					var icon = item.attr('icon');
					if (icon) {
						$('<div class="menu-icon"></div>').addClass(icon)
								.appendTo(item);
					}
					if (item[0].submenu) {
						$('<div class="menu-rightarrow"></div>').appendTo(item); // has
						// sub
						// menu
					}

					if ($.boxModel == true) {
						var height = item.height();
						item.height(height
								- (item.outerHeight() - item.height()));
					}
				}
			});
			menu.hide();
		},

		onDocClick : function(e) {
			var target = e.data;
			this.hide();
			return false;
		},
		/**
		 * @desc: 显示当前菜单
		 */
		showMenu : function(menu, pos, callback) {
			var self = this;
			if (!menu)
				return;

			if (pos) {
				menu.css(pos);
			}
			menu.show(1, function() {
						if (!menu[0].shadow) {
							menu[0].shadow = $('<div id="' + menu.attr('id')
									+ '-shadow" class="menu-shadow"></div>')
									.insertAfter(menu);
						}
						menu[0].shadow.css({
									display : 'block',
									zIndex : self.options.zIndex++,
									left : menu.css('left'),
									top : menu.css('top'),
									width : menu.outerWidth(),
									height : menu.outerHeight()
								});
						menu.css('z-index', self.options.zIndex++);

						if (callback) {
							callback();
						}
					});
		},
		/**
		 * @desc: 隐藏当前菜单
		 */
		hideMenu : function(menu) {
			var self = this;
			if (!menu)
				return;
			hideit(menu);

			// menu.find('div.menu-item').each(function() {
			// if (this.submenu) {
			// self.hideMenu(this.submenu);
			// }
			// $(this).removeClass('menu-active');
			// });

			// 当前菜单隐藏时，同时隐藏当前菜单的子菜单
			$('div[conid="' + this.el.attr('id') + '"]').each(function() {
						// $(this).css({display : 'none'});
						// $('#' + $(this).attr('id') + '-shadow').css({display
						// : 'none'});
						$(this).menu('hide');
					});

			// 隐藏菜单及其阴影
			function hideit(m) {
				if (m[0].shadow) {
					m[0].shadow.hide();
				}
				m.hide();
			}
		},
		/**
		 * @desc： 显示菜单（对外接口）
		 * @param：{Object} pos 菜单显示的横纵坐标 example： {left:0,top:0}
		 */
		// TODO:菜单显示的边界判断，若菜单在当前显示范围内无法显示，则换位置显示
		show : function(pos) {
			var self = this;
			var opts = this.options;
			if (pos) {
				opts.left = pos.left;
				opts.top = pos.top;
			}
			this.showMenu($(this.el), {
						left : opts.left,
						top : opts.top
					}, function() {
						// $(document).bind('click.menu', self.el,
						// opts.onDocClick);
						// opts.onShow.call(self.el);
					});
			this.el.focus();
		},
		/**
		 * @desc： 隐藏菜单（对外接口）
		 */
		hide : function() {
			var opts = this.options, self = this;
			this.hideMenu(this.el);
			$(document).unbind('.menu');
			opts.onHide.call(this.el);
			hideFaMenu(this.el);

			// 判断父级菜单中是否有选中的项，若无选中项，隐藏父菜单
			function hideFaMenu(comp) {
				if (comp.attr('conid')) {
					var faMenu = $('div#' + comp.attr('conid'));

					if ($('div.menu-active', faMenu).length <= 0) {
						faMenu.css({
									display : 'none'
								});
						$('div#' + comp.attr('conid') + '-shadow').css({
									display : 'none'
								});
						if (faMenu.attr('conid')) {
							arguments.callee(faMenu);
						}
					}
				}
			}

			return false;
		}
	});
})(jQuery);
/**
 * tabs - tui
 * 基于jQuery1.4.2+
 * 
 * 功能说明：
 * tab头和tab页内容可以分开定义。
 * tabs的头部导航方便，右键功能包括：刷新、关闭其他、关闭所有、锁定/解除锁定。
 * tab内容要可以加载各种资源，包括dom片段、iframe等。
 * tab要点击激活才加载内容。
 * 双击关闭tab
 * 
 * 待完善：
 * 
 * 关闭时判断是否有active的tab，如果没有则active前一个
 * tabs太多时css控制不换行
 * tabs导航
 * 拖动改变位置、可拖放
 */
(function($){
	var idSeed=0;//组件id种子初始值 add by chengl
	$.woo.component.subclass('woo.tabs',{
		options: {
			width: 'auto',
			height: 'auto',
			//idSeed: 0,
			idPrefix : 'woo-tabs-',
			plain: false,
			fit: false,
			closable:true,
			
			border: true,
			scrollIncrement: 100,
			scrollDuration: 400,
			
			cache : false,//是否缓存已加载数据
			
			onLoad: $.noop,
			
			//tabs头部的容器
			hc: null,
			//tabs内容区的容器
			bc:null,
			
			/**
			 * tabs组件的tab
			 * [{
			 * 	id:id,
			 * 	title:title,
			 * 	closable:true,
			 *  selected:true,
			 *  content:''	//tab下的内容
			 * },{
			 * 
			 * }]
			 */
			items : null,
			/**
			 * 选中tab时触发
			 * @param {String} title
			 */
			onSelect: $.noop,
			
			/**
			 * 关闭tab时触发
			 * @param {String} title
			 */
			onClose: $.noop
		},
		
		
		_create : function(){
			this.hc = $(this.options.hc || this.el);
			this.bc = $(this.options.bc || this.el);
			this._inithtml();
		},
		//激活默认选定的tab
		_init : function(){
			this.setSize();
			this.active();
		},
		
		id : function(){
			return this.options.idPrefix + (idSeed++);
		},
		
		//创建tabs的头部，但不创建每一个tab的内容主体，只有在一个tab被激活的时候，才去创建tab的内容
		_inithtml : function(){
			var self = this;

			var hcChildren=this.hc.children();//拿到头部的孩子，在头部后面进行追加 add by chengl
			
			this.hc.addClass('tabs-container');
			this.bc.append('<div class="tabs-panels'+(!this.options.border?' tabs-panels-noborder':'')+'"></div>');
			var buf = ['<div class="tabs-header',
						!this.options.border?' tabs-header-noborder':'',
						'">',
						'<div class="tabs-scroller-left"></div>',
						'<div class="tabs-scroller-right"></div>',
						'<div class="tabs-wrap">',
							this.options.title?'<div class="tabs-title">'+this.options.title+'</div>':'',
							'<ul class="tabs">',
							
							'</ul>',
						'</div>',
					'</div>'].join('');
			buf=$(buf);
			
			if(hcChildren.size()>0){
				buf.find(".tabs-wrap").append(hcChildren);
			}
			buf.prependTo(this.hc);
			for(var i = 0,l = this.options.items.length;i<l;i++){
				this.add(this.options.items[i]);
			}
			this._initEvent();
		},
		
		/**
		 * 初始化tabs组件的事件
		 */
		_initEvent : function(){
			var self = this,tabs = $('ul.tabs',this.hc);
				
			
			//初始化tab的点击事件
			$('li',this.hc).unbind('.tabs').live('click.tabs',function(){
				$('.tabs-selected',self.hc).removeClass('tabs-selected');
				$(this).addClass('tabs-selected');
				$(this).blur();
				$('>div.tabs-panels>div', self.bc).css('display', 'none');
				
				var wrap = $('.tabs-wrap', this.hc);
				var leftPos = self._getTabLeftPosition(this);
				var left = leftPos - wrap.scrollLeft();
				var right = left + $(this).outerWidth();
				if (left < 0 || right > wrap.innerWidth()) {
					var pos = Math.min(
							leftPos - (wrap.width()-$(this).width()) / 2,
							self._getMaxScrollWidth()
					);
					wrap.animate({scrollLeft:pos}, self.options.scrollDuration);
				}
				
				var tabAttr = $.data(this, 'tabs.tab');
				var panel = $('#' + tabAttr.id);
				panel.css('display', 'block');
				
				self.loadContent(this);
				self.fitContent();
				
				self.options.onSelect.call(panel, tabAttr.title);
				
			}).live('dblclick.tabs',function(e){
				e.stopPropagation();
				self.closeTab(this);
			});
			//初始化tab的关闭事件
			$('a.tabs-close', tabs).unbind('.tabs').live('click.tabs', function(e){
				var elem = $(this).parent()[0];
				//var tabAttr = $.data(elem, 'tabs.tab');
				e.stopPropagation();
				self.closeTab(elem);
			});
			
			
			var header = $('>div.tabs-header', this.hc);
			$('.tabs-scroller-left, .tabs-scroller-right', header).hover(
				function(){$(this).addClass('tabs-scroller-over');},
				function(){$(this).removeClass('tabs-scroller-over');}
			);
			//设置容器的resize事件
			//TODO
			$(this.hc).bind('_resize', function(){
				if (this.options.fit == true){
					this.setSize();
					this.fitContent();
				}
				return false;
			});
		},
		
		fitContent : function(){
		//	debugger;
			var tab = $('>div.tabs-header ul.tabs li.tabs-selected', this.hc);
			if (tab.length){
				var panelId = $.data(tab[0], 'tabs.tab').id;
				var panel = $('#'+panelId);
				var panels = $('>div.tabs-panels', this.bc);
				if (panels.css('height').toLowerCase() != 'auto'){
					//TODO $.boxModel 已不建议使用
					if ($.boxModel == true){
						panel.height(panels.height() - (panel.outerHeight()-panel.height()));
						panel.width(panels.width() - (panel.outerWidth()-panel.width()));
					} else {
						panel.height(panels.height());
						panel.width(panels.width());
					}
				}
				$('>div', panel).triggerHandler('_resize');
			}
		},
		
		/**
		 *	初始化交互操作 
		 */
		_initAction : function(){
			
		},
		
		 //设置tabs滚动按钮是否显示,依赖于tabs的数量和宽度
		_setScrollers : function(){
			var tabsWidth = 0;
			$('ul.tabs li', this.hc).each(function(){
				tabsWidth += $(this).outerWidth(true);
			});
			
			if (tabsWidth > this.hc.width()) {
				$('.tabs-scroller-left', this.hc).css('display', 'block');
				$('.tabs-scroller-right', this.hc).css('display', 'block');
				$('.tabs-wrap', this.hc).addClass('tabs-scrolling');
				
				$('.tabs-wrap', this.hc).css('left',$.support.boxModel?2:0);

				var width = this.hc.width()
					- $('.tabs-scroller-left', this.hc).outerWidth()
					- $('.tabs-scroller-right', this.hc).outerWidth();
				$('.tabs-wrap', this.hc).width(width);
				
			} else {
				$('.tabs-scroller-left', this.hc).css('display', 'none');
				$('.tabs-scroller-right', this.hc).css('display', 'none');
				$('.tabs-wrap', this.hc).removeClass('tabs-scrolling').scrollLeft(0);
				$('.tabs-wrap', this.hc).width(this.hc.width());
				$('.tabs-wrap', this.hc).css('left',0);
				
			}
		},
		
		_getTabLeftPosition : function(tab){
			var w = 0;
			var b = true;
			$('>div.tabs-header ul.tabs li', this.hc).each(function(){
				if (this == tab) {
					b = false;
				}
				if (b == true) {
					w += $(this).outerWidth(true);
				}
			});
			return w;
		},
		
		// 获取tabs最大的滚动宽度
		_getMaxScrollWidth : function() {
			var header = $('>div.tabs-header', this.hc);
			var tabsWidth = 0;	// all tabs width
			$('ul.tabs li', header).each(function(){
				tabsWidth += $(this).outerWidth(true);
			});
			var wrapWidth = $('.tabs-wrap', header).width();
			var padding = parseInt($('.tabs', header).css('padding-left'));
			
			return tabsWidth - wrapWidth + padding;
		},
		
		setSize : function(){
			var opts = this.options;
			var cc = $(this.el);
			if (opts.fit == true){
				var p = cc.parent();
				opts.width = p.width();
				opts.height = p.height();
			}
			cc.width(opts.width);//.height(opts.height);
			/*cc.width(isNaN(opts.width)?this.hc.width():opts.width)
				.height(isNaN(opts.height)?this.hc.height():opts.height);
			*/
			var header = $('>div.tabs-header', this.hc);
			//TODO $.boxModel 已不建议使用
			if ($.boxModel == true) {
				var delta = header.outerWidth() - header.width();
	//			var delta = header.outerWidth(true) - header.width();
				header.width(cc.width() - delta);
			} else {
				header.width(cc.width());
			}
			//设置宽度自适应，则与this.el宽度一致 这样在动态更改this.el宽度的时候header宽度也会更改 add by chengl 
			header.width("auto");
			if(opts.bc){
				cc.height(header.height());
			}else{
				cc.height(opts.height);
			}
		//	this._setScrollers();
			
			var panels = $('>div.tabs-panels', this.bc);
			var height = opts.height;
			if (!isNaN(height)) {
				//TODO $.boxModel 已不建议使用
				if ($.boxModel == true) {
					var delta = panels.outerHeight() - panels.height();
					panels.css('height', (height - header.outerHeight() - delta) || 'auto');
				} else {
					panels.css('height', height - header.outerHeight());
				}
			} else {
				panels.height('auto');
			}
			var width = opts.width;
			if (!isNaN(width)){
				//TODO $.boxModel 已不建议使用
				if ($.boxModel == true) {
					var delta = panels.outerWidth() - panels.width();
	//				var delta = panels.outerWidth(true) - panels.width();
					panels.width(width - delta);
				} else {
					panels.width(width);
				}
			} else {
				panels.width('auto');
			}
			
/*			if ($.parser){
				$.parser.parse(container);
			}
*/		},
		
		
		/**
		 * @param {Object} option 格式为：
		 * {
		 * 	id:id,
		 * 	title:title,
		 * 	closable:true,
		 * 	disable:false,
		 *  iconCls:'icon-cls',
		 *  tabCls:'tab-cls',
		 *  content:''	//tab下的内容
		 * }
		 * @param {Number} pos 插入的位置，计数从0开始，默认为最后一个位置
		 */
		add : function(option,pos){
			option = $.extend({
				id : this.id(),
				title : '',
				href : null,
				tabCls : null,
				iconCls : null,
				closable : this.options.closable,
				selected : false,
				height : this.options.height,
				width : this.options.width
				
			},option || {});
			pos = pos || 0;
//				/<li class=""><a href="javascript:void(0)" class="tabs-inner">
//			<span class="tabs-title tabs-closable">Tab2</span>
//			<span class="tabs-icon"></span>
//			</a><a href="javascript:void(0)" class="tabs-close"></a>
//			</li>
			var tabBuf = ['<li class="',
							option.selected?'tabs-selected':'',
							option.tabCls?' ' + option.tabCls : '',
							'">',
						'<a class="tabs-inner" ','href="javascript:void(0)">',
							'<span class="',
							option.closable?'tabs-closable':'',
							option.iconCls?' tabs-with-icon':'',
							'">',
							option.title,
							'</span>',
							option.iconCls?'<span class="tabs-icon '+option.iconCls + '"></span>':'',
						'</a>',
						option.closable?'<a href="javascript:void(0)" class="tabs-close"></a>':'',
					'</li>'];
			var panelBuf = ['<div id="',
						option.id,
					'" style="display:none;"></div>'];
			
			var hc =  $('ul.tabs',this.hc);
			var bc =  $('>div.tabs-panels', this.bc);
			var lis = $('>li',hc);
			var panels = $('>div',bc);
			
			var tab;
			if(pos >0 && lis.length>0){
				tab = $(tabBuf.join('')).insertAfter(lis.get(pos-1));	
			}else{
				tab = $(tabBuf.join('')).appendTo(hc);
				
			}
			if(pos >0 && panels.length > 0){
				$(panelBuf.join('')).insertAfter(panels.get(pos-1));
			}else{
				//设置panel的高宽
				$(panelBuf.join('')).height(isNaN(this.options.height)?'100%':this.options.height).width(isNaN(this.options.width)?'100%':this.options.width).appendTo(bc);	
			}
			tab.data('tabs.tab',{
				id: option.id,
				title: option.title,
				closable:option.closable,
				url: option.url,//如果有url，则不加载content
				content:option.content,
				loaded: false,
				cache: option.cache
			});
		},
		
		active : function(title){
			if (title) {
				var elem = $('>div.tabs-header li:has(a span:contains("' + title + '"))', this.el)[0];
				if (elem) {
					$(elem).trigger('click');
				}
			} else {
				var tabs = $('>div.tabs-header ul.tabs', this.hc);
				if ($('.tabs-selected', tabs).length == 0) {
					$('li:first', tabs).trigger('click');
				} else {
					$('.tabs-selected', tabs).trigger('click');
				}
			}
		},
		
		loadContent : function(elem){
			//debugger;
			var data = $.data(elem, 'tabs.tab');
			if(data && data.loaded){
				return;
			}
			var panel = $('#' + data.id);
			panel.css('display', 'block');
			if (data.url && !data.cache) {
				/*panel.html(data.url, null, function(){
					if ($.parser){
						$.parser.parse(panel);
					}
					this.options.onLoad.apply(this, arguments);
					data.loaded = true;
				});*/
				panel.html('<iframe style="width:100%;height:100%" frameborder="0" src="'+data.url+'"></iframe>');
			}
			if(!data.url && $.woo.isString(data.content)){
				panel.html(data.content);	
			}
			//设置为已加载
			data.loaded = true;
		},
		
		/**
		 * 设置title
		 */
		setTitle : function(title){
			$('li.tabs-title',this.hc).text(title);
		},
		
		/**
		 * 获取激活的tab分页
		 */
		getActiveTab : function(){
			
		},
		
		 /**
		  * 获取指定的分页
		  * @param {String} id
		  */
		getTab : function(id){
			
		},
		
		/**
		 * 关闭一个分页
		 */
		closeTab : function(elem){
			//var elem = $('>div.tabs-header li:has(a span:contains("' + title + '"))', container)[0];
			if (!elem) return;
			
			var tabAttr = $.data(elem, 'tabs.tab');
			if(!tabAttr.closable) return;
			var panel = $('#' + tabAttr.id);
			
			if (this.options.onClose.call(panel, tabAttr) == false) return;
			
			var selected = $(elem).hasClass('tabs-selected');
			$.removeData(elem, 'tabs.tab');
			$(elem).remove();
			panel.remove();
			
			this.setSize();
			if (selected) {
				this.active();
			} else {
				var wrap = $('>div.tabs-header .tabs-wrap', this.hc);
				var pos = Math.min(
						wrap.scrollLeft(),
						this._getMaxScrollWidth()
				);
				wrap.animate({scrollLeft:pos}, this.options.scrollDuration);
			}
		},
		
		/**
		 * 判断是否已经存在
		 */
		exists : function(title){
			//TODO 不能通过title来判断
			return $('>div.tabs-header li:has(a span:contains("' + title + '"))', this.el).length > 0;
		}
	});
})(jQuery);/**
 * grid - wooUI 基于jQuery1.4.2+
 * 
 * 依赖于以下组件:: resizable
 * 
 */
(function($) {
	$.woo.component.subclass('woo.grid', {
		options : {
			/**
			 * @desc: 是否根据父容器大小自适应
			 * @type： {Boolean}
			 */
			fit : false,
			/**
			 * @desc: 全选功能
			 * @type： {Object}
			 */
			frozenColumns : null,
			/**
			 * @desc: 列字段
			 * @type： {Object}
			 */
			columns : null,
			/**
			 * @desc: 工具栏
			 * @type： {Object}
			 */
			toolbar : null,
			/**
			 * @desc: 是否为行记录添加条纹
			 * @type： {Boolean}
			 */
			striped : false,
			/**
			 * @desc: 是否在同一行当中显示所有数据
			 * @type： {Boolean}
			 */
			nowrap : true,
			/**
			 * @desc: 列表中的唯一标示字段
			 * @type： {String}
			 */
			idField : '',
			/**
			 * @desc: ajax获取数据源的url参数
			 * @type： {String}
			 */
			url : null,
			/**
			 * @desc: ajax获取数据源的提交方式
			 * @type： {String}
			 */
			method : "post",
			/**
			 * @desc: 数据加载过程中的提示文字
			 * @type： {String}
			 */
			loadMsg : "正在加载中..........",
			/**
			 * @desc:
			 * @type： {Boolean}
			 */
			rownumbers : false,
			/**
			 * @desc: 是否仅允许单行选中
			 * @type： {Boolean}
			 */
			singleSelect : false,
			/**
			 * @desc: 是否在列表底部显示分页栏
			 * @type： {Boolean}
			 */
			pagination : true,
			/**
			 * @desc: 分页栏初始化时初始的页数
			 * @type： {Number}
			 */
			pageNumber : 1,
			/**
			 * @desc: 每页最大的记录条数
			 * @type： {Number}
			 */
			pageSize : 10,
			/**
			 * @desc: 调整每页最大记录条数的下拉框选项
			 * @type： {Array}
			 */
			pageList : [10, 20, 30, 40, 50],
			/**
			 * @desc: ajax获取远程数据时的传入参数
			 * @type： {Object}
			 */
			queryParams : {},
			/**
			 * @desc: 定义排序的字段
			 * @type： {String}
			 */
			sortName : null,
			/**
			 * @desc: 字段排序的方式，默认为升序
			 * @type： {String}
			 */
			sortOrder : "asc",
			/**
			 * @desc: 远程获取数据时是否对结果集进行排序
			 * @type： {Boolean}
			 */
			remoteSort : true,

			/**
			 * @desc: 是否可编辑
			 * @type： {Boolean}
			 */
			editable : false,
			/**
			 * @desc: 编辑器对象
			 * @type： {Object}
			 */
			editors : $.woo.editors,
			/**
			 * @desc: 装载数据之前执行的方法
			 * @type： {Function}
			 */
			onBeforeLoad : function(params) {
			},
			/**
			 * @desc: 更改列表框宽高的方法
			 * @type： {Function}
			 */
			onResize : $.noop,
			/**
			 * @desc: 装载数据成功后执行的方法
			 * @type： {Function}
			 */
			onLoadSuccess : $.noop,
			/**
			 * @desc: 展开列表内容时执行的方法
			 * @type： {Function}
			 */
			onExpand : $.noop,
			/**
			 * @desc: 装载信息出错时触发的方法
			 * @type： {Function}
			 */
			onLoadError : function() {
			},
			/**
			 * @desc: 单击列表某一行时触发的方法
			 * @type： {Number} rowIndex 指定行的索引 {Array} rowData 所有的记录集合
			 */
			onClickRow : function(rowIndex, rowData) {
			},
			/**
			 * @desc: 双击列表某一行时触发的方法
			 * @type： {Number} rowIndex 指定行的索引 {Array} rowData 所有的记录集合
			 */
			onDblClickRow : function(rowIndex, rowData) {
			},
			/**
			 * @desc: 为某一列排序时触发的方法
			 * @type： {Function}
			 * @param： {String} sortName 排序的字段名称 {String} order 排序的方式
			 */
			onSortColumn : function(sortName, order) {
			},
			/**
			 * @desc: 修改列宽高时触发的方法
			 * @type： {Object} field 需要修改宽度的字段对象 {Number} width 修改后的宽度
			 */
			onResizeColumn : function(field, width) {
			},
			/**
			 * @desc: 根据索引选中某一行记录
			 * @type： {Function}
			 * @param： {Number} rowIndex 指定行的索引 {Array} rowData 所有的记录集合
			 */
			onSelect : function(rowIndex, rowData) {
			},
			/**
			 * @desc: 根据索引取消指定行的选中状态
			 * @type： {Function}
			 * @param： {Number} rowIndex 指定行的索引 {Array} rowData 所有的记录集合
			 */
			onUnselect : function(rowIndex, rowData) {
			},
			/**
			 * @desc: 全选功能
			 * @type： {Function}
			 * @param： {Array} rows 所有的记录集合
			 */
			onSelectAll : function(rows) {
			},
			/**
			 * @desc: 清除所有行的选中状态
			 * @type： {Function}
			 * @param： {Array} rows 所有的记录集合
			 */
			onUnselectAll : function(rows) {
			},
			/**
			 * @desc: 修改记录之前触发的方法
			 * @type： {Function}
			 */
			onBeforeEdit : function(_4b1, _4b2) {
			},
			/**
			 * @desc: 修改记录之后触发的方法
			 * @type： {Function}
			 */
			onAfterEdit : function(_4b3, _4b4, _4b5) {
			},
			/**
			 * @desc: 取消修改时触发的方法
			 * @type： {Function}
			 */
			onCancelEdit : function(_4b6, _4b7) {
			}
		},
		/**
		 * @desc:
		 */
		_create : function() {
			gridInit(this);
//			this.bindResizeEvent(this);
		},
		bindResizeEvent : function(comp){
			var opts = comp.options,
				target = comp.el;
			if(opts.fit === true){
				var p = target.parent();
				if(p[0] == $('body')[0]){
					p = $(window);
				}else{
					p = target.parent('div.panel-body');
				}
				p.resize(function(){
					setSize(target[0], {
						width : p.width(),
						height : p.height()
					});
				});
			}
		},
		getPanel : function() {
			return $.data(this.el[0], "grid-data").panel;
		},
		getPager : function() {
			return $.data(this.el[0], "grid-data").panel.find("div.grid-pager");
		},
		getColumnFields : function(columns) {
			return getColumnFields(this.el[0], columns);
		},
		getColumnOption : function(field) {
			return getColumnOption(this.el[0], field);
		},
		resize : function(param) {
			setSize(this.el[0], param);
		},
		reload : function(param) {
			request(this.el[0], param);
		},
		fixColumnSize : function() {
			fixColumnSize(this.el[0]);
		},
		loadData : function(data) {
			loadData(this.el[0], data, true);
			initData(this.el[0]);
		},
		getData : function() {
			return $.data(this.el[0], "grid-data").data;
		},
		getRows : function() {
			return $.data(this.el[0], "grid-data").data.rows;
		},
		getRowIndex : function(id) {
			return getRowIndex(this.el[0], id);
		},
		getSelected : function() {
			var rows = getSelected(this.el[0]);
			return rows;
		},
		getSelections : function() {
			return getSelected(this.el[0]);
		},
		clearSelections : function() {
			clearSelections(this.el[0]);
		},
		selectAll : function() {
			selectAll(this.el[0]);
		},
		unselectAll : function() {
			unselectAll(this.el[0]);
		},
		selectRow : function(index) {
			selectRow(this.el[0], index);
		},
		selectRecord : function(id) {
			selectRecord(this.el[0], id);
		},
		unselectRow : function(index) {
			unselectRow(this.el[0], index);
		},
		beginEdit : function(_49a) {
			beginEdit(this.el[0], _49a);
		},
		endEdit : function(_49b) {
			cellEdit(this.el[0], _49b, false);
		},
		cancelEdit : function(_49c) {
			cellEdit(this.el[0], _49c, true);
		},
		getEditors : function(_49d) {
			return getEditors(this.el[0], _49d);
		},
		getEditor : function(_49e) {
			return getEditor(this.el[0], _49e);
		},
		refreshRow : function(_49f) {
			refreshRow(this.el[0], _49f);
		},
		validateRow : function(_4a0) {
			validateRow(this.el[0], _4a0);
		},
		appendRow : function(row) {
			appendRow(this.el[0], row);
		},
		deleteRow : function(index) {
			if (index) {
				deleteRow(this.el[0], index);
			} else {
				var selArr = getSelected(this.el[0]);
				if (selArr.length <= 0) {
					return false;
				}
				for (var i = 0, len = selArr.length; i < len; i++) {
					deleteRow(this.el[0], getRowIndex(this.el[0],
									selArr[i][this.options.idField]));
				}
			}
		},
		getChanges : function(rowType) {
			return getChanges(this.el[0], rowType);
		},
		acceptChanges : function() {
			acceptChanges(this.el[0]);
		},
		rejectChanges : function() {
			rejectChanges(this.el[0]);
		},
		mergeCells : function(param) {
			mergeCells(this.el[0], param);
		},
		addRow : function(records, index) {
			addRow(this, records, index);
		}

	});
	/**
	 * @desc: 组件初始化
	 */
	function gridInit(comp) {
		var opts = comp.options;
		comp.el.css({
					'width' : null,
					'height' : null
				});
		var wrapResult = wrapGrid(comp, opts.rownumbers);
		if (!opts.columns) {
			opts.columns = wrapResult.columns;
		}
		if (!opts.frozenColumns) {
			opts.frozenColumns = wrapResult.frozenColumns;
		}
		$.data(comp.el[0], "grid-data", {
					options : opts,//
					panel : wrapResult.panel,
					selectedRows : [],
					data : {
						total : 0,
						rows : []
					},
					originalRows : [],
					updatedRows : [],
					insertedRows : [],
					deletedRows : [],
					editingRows : []
				});

		loadData(comp.el[0], wrapData(comp.el[0]), true);
		initData(comp.el[0]);

		wrapGridView(comp.el[0]);
		fixColumnSize(comp.el[0]);
		setSize(comp.el[0]);
		if (opts.url) {
			request(comp.el[0]);
		}
	}

	function setSize(target, param) {
		var opts = $.data(target, "grid-data").options;
		var panel = $.data(target, "grid-data").panel;
		if (param) {
			if (param.width) {
				opts.width = param.width;
			}
			if (param.height) {
				opts.height = param.height;
			}
		}
		// wangkun：自适应判断
		if (opts.fit == true) {
			// var p = panel.panel("panel").parent();
			var p = $(target).parent("div.panel-body");
			opts.width = p.width();
			opts.height = p.height();
		}
		panel.panel("resize", {
					width : opts.width,
					height : opts.height
				});
	};

	/**
	 * @private setGridBodyHeight
	 */
	function setGridBodyHeight(target) {
		var opts = $.data(target, "grid-data").options, wrap = $.data(target,
				"grid-data").panel.find('>div.grid-wrap'), pWidth = wrap
				.width(), pHeight = wrap.height(), view = wrap
				.find("div.grid-view"), view1 = view.find("div.grid-view1"), view2 = view
				.find("div.grid-view2");
		view.width(pWidth);
		view1.width(view1.find("table").width());
		view2.width(pWidth - view1.outerWidth());
		view1.find(">div.grid-header,>div.grid-body").width(view1.width());
		view2.find(">div.grid-header,>div.grid-body").width(view2.width());
		var hh, v1Header = view1.find(">div.grid-header"), v2Header = view2
				.find(">div.grid-header"), v1Table = v1Header.find("table"), v2Table = v2Header
				.find("table");
		v1Header.css("height", null);
		v2Header.css("height", null);
		v1Table.css("height", null);
		v2Table.css("height", null);
		hh = Math.max(v1Table.height(), v2Table.height());
		v1Table.height(hh);
		v2Table.height(hh);
		if ($.boxModel == true) {
			v1Header.height(hh - (v1Header.outerHeight() - v1Header.height()));
			v2Header.height(hh - (v2Header.outerHeight() - v2Header.height()));
		} else {
			v1Header.height(hh);
			v2Header.height(hh);
		}
		var body = view.find("div.grid-body");
		if (opts.height == "auto") {
			body.height(view2.find("div.grid-body table").height() + 18);
		} else {
			body.height(pHeight
					- $(">div.grid-header", view2).outerHeight(true)
					- $(">div.grid-toolbar", wrap).outerHeight(true)
					- $(">div.grid-pager", wrap).outerHeight(true));
		}
		// Deasel:bodyHeight
		view.height(view2.height());
		view2.css("left", view1.outerWidth());
	};

	/**
	 * @private
	 */
	function setRowHeight(target, rowIndex) {
		var rows = $.data(target, "grid-data").data.rows;
		var opts = $.data(target, "grid-data").options;
		var panel = $.data(target, "grid-data").panel;
		var view = panel.find(">div.grid-view");
		var view1 = view.find(">div.grid-view1");
		var view2 = view.find(">div.grid-view2");
		if (opts.rownumbers
				|| (opts.frozenColumns && opts.frozenColumns.length > 0)) {
			if (rowIndex >= 0) {
				setRowHeight(rowIndex);
			} else {
				for (var i = 0; i < rows.length; i++) {
					setRowHeight(i);
				}
			}
		}
		if (opts.height == "auto") {
			var _356 = view2.find("div.grid-body table").height() + 18;
			view1.find("div.grid-body").height(_356);
			view2.find("div.grid-body").height(_356);
			view.height(view2.height());
		}
		function setRowHeight(index) {
			var tr1 = view1.find("tr[grid-row-index=" + index + "]");
			var tr2 = view2.find("tr[grid-row-index=" + index + "]");
			tr1.css("height", null);
			tr2.css("height", null);
			var mh = Math.max(tr1.height(), tr2.height());
			tr1.css("height", mh);
			tr2.css("height", mh);
		};
	};
	/**
	 * @desc： 初始化列表外框
	 */
	function wrapGrid(comp, rownumbers) {
		function getColumns(target) {
			var tempArr = [];
			$("tr", target).each(function() {
				var cols = [];
				$("th", this).each(function() {
							var th = $(this);
							var col = {
								title : th.html(),
								align : th.attr("align") || "left",
								sortable : th.attr("sortable") == "true" || false,
								checkbox : th.attr("checkbox") == "true" || false
							};
							if (th.attr("field")) {
								col.field = th.attr("field");
							}
							if (th.attr("formatter")) {
								col.formatter = eval(th.attr("formatter"));
							}
							if (th.attr("editor")) {
								var s = $.trim(th.attr("editor"));
								if (s.substr(0, 1) == "{") {
									col.editor = eval("(" + s + ")");
								} else {
									col.editor = s;
								}
							}
							if (th.attr("rowspan")) {
								col.rowspan = parseInt(th.attr("rowspan"));
							}
							if (th.attr("colspan")) {
								col.colspan = parseInt(th.attr("colspan"));
							}
							if (th.attr("width")) {
								col.width = parseInt(th.attr("width"));
							}
							cols.push(col);
						});
				tempArr.push(cols);
			});
			return tempArr;
		};
		panel = comp.el;
		var target = comp.el[0];

		// wangkun：自适应判断
		if (comp.options.fit == true) {
			// var p = panel.panel("panel").parent();
			var p = $(target).parent("div.panel-body");
			comp.options.width = p.width();
			comp.options.height = p.height();
		}
		panel.panel(comp.options);
		panel.find('>div.panel-body').addClass('grid-wrap').append([
				'<div class="grid-view">',
				'<div class="grid-view1"><div class="grid-header">',
				'<div class="grid-header-inner"></div></div>',
				'<div class="grid-body">',
				'<div class="grid-body-inner"></div></div></div>',
				'<div class="grid-view2">', '<div class="grid-header">',
				'<div class="grid-header-inner"></div></div>',
				'<div class="grid-body"></div></div>',
				'<div class="grid-resize-proxy"></div></div>'].join(""));
		panel.panel("panel").addClass("grid").bind("_resize", function() {
					var opts = $.data(target, "grid-data").options;
					if (opts.fit == true) {
						setSize(target);
						setTimeout(function() {
									fixColumnSize(target);
								}, 0);
					}
					return false;
				});
		// $(target).hide().appendTo($(">div.grid-view", panel));
		var frozenColumns = getColumns($("thead[frozen=true]", target));
		var otherCols = getColumns($("thead[frozen!=true]", target));

		return {
			panel : panel,
			frozenColumns : frozenColumns,
			columns : otherCols
		};
	};
	/**
	 * @desc: 此方法中已存在table标签的操作，因此判断该方法应该是在wrapGridView方法之后
	 */
	function wrapData(target) {
		var data = {
			total : 0,
			rows : []
		};
		var fields = getColumnFields(target, true).concat(getColumnFields(
				target, false));

		var col = null;
		$(target).find("tbody tr").each(function() {
					data.total++;
					col = {};
					for (var i = 0; i < fields.length; i++) {
						col[fields[i]] = $("td:eq(" + i + ")", this).html();
					}
					data.rows.push(col);
				});
		return data;
	};

	/**
	 * @private 包装grid的内部结构
	 */
	function wrapGridView(target) {
		var opts = $.data(target, "grid-data").options;
		var panel = $.data(target, "grid-data").panel;
		panel.panel($.extend({}, opts, {
					doSize : false,
					onResize : function(_36a, _36b) {
						setTimeout(function() {
									setGridBodyHeight(target);
									opts.onResize.call(panel, _36a, _36b);
								}, 0);
					},
					onExpand : function() {
						setGridBodyHeight(target);
						opts.onExpand.call(panel);
					}
				}));
		if (opts.frozenColumns) {
			var t = wrapHeader(opts.frozenColumns);
			if (opts.rownumbers) {
				var td = $("<td rowspan=\"" + opts.frozenColumns.length
						+ "\"><div class=\"grid-header-rownumber\"></div></td>");
				if ($("tr", t).length == 0) {
					td.wrap("<tr></tr>").parent().appendTo($("tbody", t));
				} else {
					td.prependTo($("tr:first", t));
				}
			}
			$("div.grid-view1 div.grid-header-inner", panel).html(t);
		}
		if (opts.columns) {
			var t = wrapHeader(opts.columns);
			$("div.grid-view2 div.grid-header-inner", panel).html(t);
		}
		$("div.grid-toolbar", panel).remove();
		if (opts.toolbar) {
			var tb = $("<div class=\"grid-toolbar\"></div>").prependTo(panel
					.find('>div.panel-body'));
			for (var i = 0; i < opts.toolbar.length; i++) {
				var btn = opts.toolbar[i];
				if (btn == "-") {
					$("<div class=\"grid-btn-separator\"></div>").appendTo(tb);
				} else {
					var tool = $("<a href=\"javascript:void(0)\"></a>");
					tool[0].onclick = eval(btn.handler || function() {
					});
					tool.css("float", "left").appendTo(tb).linkbutton($.extend(
							{}, btn, {
								plain : true
							}));
				}
			}
		}
		$("div.grid-pager", panel).remove();
		if (opts.pagination) {
			var pager = $("<div class=\"grid-pager\"></div>").appendTo(panel
					.find('>div.panel-body'));
			pager.pagination({
						pageNumber : opts.pageNumber,
						pageSize : opts.pageSize,
						pageList : opts.pageList,
						onSelectPage : function(pageNumber, pageSize) {
							opts.pageNumber = pageNumber;
							opts.pageSize = pageSize;
							request(target);
						}
					});
			opts.pageSize = pager.pagination("options").pageSize;
		}
	};
	/**
	 * @private 生成grid行头的DOM结构
	 */
	function wrapHeader(colums) {
		var t = $("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody></tbody></table>");
		for (var i = 0; i < colums.length; i++) {
			var tr = $("<tr></tr>").appendTo($("tbody", t));
			var cols = colums[i];
			for (var j = 0; j < cols.length; j++) {
				var col = cols[j];
				var attr = "";
				if (col.rowspan) {
					attr += "rowspan=\"" + col.rowspan + "\" ";
				}
				if (col.colspan) {
					attr += "colspan=\"" + col.colspan + "\" ";
				}
				var td = $("<td " + attr + "></td>").appendTo(tr);
				if (col.checkbox) {
					td.attr("field", col.field);
					$("<div class=\"grid-header-check\"></div>")
							.html("<input type=\"checkbox\"/>").appendTo(td);
				} else {
					if (col.field) {
						td.attr("field", col.field);
						td
								.append("<div class=\"grid-cell\"><span></span><span class=\"grid-sort-icon\"></span></div>");
						$("span", td).html(col.title);
						$("span.grid-sort-icon", td).html("&nbsp;");
						$("div.grid-cell", td).width(col.width);
						// $("div.grid-cell", td).css("text-align", (col.align
						// || "left"));
						$("div.grid-cell", td).css("text-align", "center");
					} else {
						$("<div class=\"grid-cell-group\"></div>")
								.html(col.title).appendTo(td);
					}
				}
			}
		}
		return t;
	};
	/**
	 * @private 给行绑定事件
	 */
	function bindRowEvent(target) {
		var panel = $.data(target, "grid-data").panel;
		var opts = $.data(target, "grid-data").options;
		var data = $.data(target, "grid-data").data;
		var body = panel.find("div.grid-body");
		if (opts.striped) {
			body.find("tr:odd").addClass("grid-row-alt");
		}
		var row_index = null;
		body.find("tr").unbind(".grid").bind("mouseenter.grid", function() {
			body.find("tr[grid-row-index=" + $(this).attr("grid-row-index")
					+ "]").addClass("grid-row-over");
		}).bind("mouseleave.grid", function() {
			body.find("tr[grid-row-index=" + $(this).attr("grid-row-index")
					+ "]").removeClass("grid-row-over");
		}).bind("click.grid", function() {
					row_index = $(this).attr("grid-row-index");
					if (opts.singleSelect == true) {
						clearSelections(target);
						selectRow(target, row_index);
					} else {
						if ($(this).hasClass("grid-row-selected")) {
							unselectRow(target, row_index);
						} else {
							selectRow(target, row_index);
						}
					}
					if (opts.onClickRow) {
						opts.onClickRow.call(target, row_index,
								data.rows[row_index]);
					}
				}).bind("dblclick.grid", function() {
			row_index = $(this).attr("grid-row-index");
			if (opts.onDblClickRow) {
				opts.onDblClickRow
						.call(target, row_index, data.rows[row_index]);
			}
			if (opts.editable) {
				beginEdit(target, row_index);
			}
		});
		body.find("div.grid-cell-check input[type=checkbox]").unbind(".grid")
				.bind("click.grid", function(e) {
					var _379 = $(this).parent().parent().parent()
							.attr("grid-row-index");
					if (opts.singleSelect) {
						clearSelections(target);
						selectRow(target, _379);
					} else {
						if ($(this).attr("checked")) {
							selectRow(target, _379);
						} else {
							unselectRow(target, _379);
						}
					}
					e.stopPropagation();
				});
		var gheader = panel.find("div.grid-header");
		gheader.find("td:has(div.grid-cell)").unbind(".grid").bind(
				"mouseenter.grid", function() {
					$(this).addClass("grid-header-over");
				}).bind("mouseleave.grid", function() {
					$(this).removeClass("grid-header-over");
				});
		gheader.find("div.grid-cell").unbind(".grid").bind("click.grid",
				function() {
					var field = $(this).parent().attr("field");
					var opt = getColumnOption(target, field);
					if (!opt.sortable) {
						return;
					}
					opts.sortName = field;
					opts.sortOrder = "asc";
					var c = "grid-sort-asc";
					if ($(this).hasClass("grid-sort-asc")) {
						c = "grid-sort-desc";
						opts.sortOrder = "desc";
					}
					gheader.find("div.grid-cell")
							.removeClass("grid-sort-asc grid-sort-desc");
					$(this).addClass(c);
					if (opts.onSortColumn) {
						opts.onSortColumn.call(target, opts.sortName,
								opts.sortOrder);
					}
					if (opts.remoteSort) {
						request(target);
					} else {
						loadData(target, data, true);
					}
				});
		gheader.find("input[type=checkbox]").unbind(".grid").bind("click.grid",
				function() {
					if (opts.singleSelect) {
						return false;
					}
					if ($(this).attr("checked")) {
						selectAll(target);
					} else {
						unselectAll(target);
					}
				});
		var view = panel.find("div.grid-view");
		var view1 = view.find(">div.grid-view1");
		var view2 = view.find(">div.grid-view2");
		var header = view2.find("div.grid-header");
		var v1body = view1.find("div.grid-body");
		view2.find("div.grid-body").unbind(".grid").bind("scroll.grid",
				function() {
					header.scrollLeft($(this).scrollLeft());
					v1body.scrollTop($(this).scrollTop());
				});
		gheader.find("div.grid-cell").resizable({
			handles : "e",
			minWidth : 50,
			onStartResize : function(e) {
				var proxy = view.find(">div.grid-resize-proxy");
				proxy.css({
							left : e.pageX - $(panel).offset().left - 1
						});
				proxy.css("display", "block");
			},
			onResize : function(e) {
				var proxy = view.find(">div.grid-resize-proxy");
				proxy.css({
							display : "block",
							left : e.pageX - $(panel).offset().left - 1
						});
				return false;
			},
			onStopResize : function(e) {
				fixColumnSize(target, this);
				var view2 = panel.find("div.grid-view2");
				view2.find("div.grid-header").scrollLeft(view2
						.find("div.grid-body").scrollLeft());
				view.find(">div.grid-resize-proxy").css("display", "none");
				opts.onResizeColumn.call(target,
						$(this).parent().attr("field"), $(this).outerWidth());
			}
		});
		$("div.grid-view1 div.grid-header div.grid-cell", panel).resizable({
			onStopResize : function(e) {
				fixColumnSize(target, this);
				var view2 = panel.find("div.grid-view2");
				view2.find("div.grid-header").scrollLeft(view2
						.find("div.grid-body").scrollLeft());
				view.find(">div.grid-resize-proxy").css("display", "none");
				opts.onResizeColumn.call(target,
						$(this).parent().attr("field"), $(this).outerWidth());
				setSize(target);
			}
		});
	};

	/**
	 * @private 修正列的宽度
	 */
	function fixColumnSize(target, cell) {
		var panel = $.data(target, "grid-data").panel;
		var opts = $.data(target, "grid-data").options;
		var body = panel.find("div.grid-body");
		if (cell) {
			fix(cell);
		} else {
			$("div.grid-header div.grid-cell", panel).each(function() {
						fix(this);
					});
		}
		setCellWidth(target);
		setTimeout(function() {
					setRowHeight(target);
					setEditorWidth(target);
				}, 0);
		function fix(cell) {
			var headerCell = $(cell);
			if (headerCell.width() == 0) {
				return;
			}
			var field = headerCell.parent().attr("field");
			body.find("td[field=" + field + "]").each(function() {
						var td = $(this);
						var colspan = td.attr("colspan") || 1;
						if (colspan == 1) {
							var innerCell = td.find("div.grid-cell");
							if ($.boxModel == true) {
								innerCell.width(headerCell.width());
							} else {
								innerCell.width(headerCell.outerWidth());
							}
						}
					});
			var col = getColumnOption(target, field);
			col.width = $.boxModel == true ? headerCell.width() : headerCell
					.outerWidth();
		};
	};
	/**
	 * @private 设置单元格的宽度
	 */
	function setCellWidth(target) {
		var panel = $.data(target, "grid-data").panel;
		var header = panel.find("div.grid-header");
		panel.find("div.grid-body td.grid-td-merged").each(function() {
					var td = $(this);
					var colspan = td.attr("colspan") || 1;
					var field = td.attr("field");
					var cell = header.find("td[field=" + field + "]");
					var w = cell.width();
					for (var i = 1; i < colspan; i++) {
						cell = cell.next();
						w += cell.outerWidth();
					}
					var cell = td.find(">div.grid-cell");
					if ($.boxModel == true) {
						cell.width(w - (cell.outerWidth() - cell.width()));
					} else {
						cell.width(w);
					}
				});
	};
	/**
	 * @private 设置编辑器的宽度
	 */
	function setEditorWidth(target) {
		var panel = $.data(target, "grid-data").panel;
		panel.find("div.grid-editable").each(function() {
					var ed = $.data(this, "grid.editor");
					if (ed.actions.resize) {
						ed.actions.resize(ed.target, $(this).width());
					}
				});
	};
	/**
	 * @private 获取列配置的属性
	 */
	function getColumnOption(target, field) {
		var opts = $.data(target, "grid-data").options;
		if (opts.columns) {
			for (var i = 0; i < opts.columns.length; i++) {
				var cols = opts.columns[i];
				for (var j = 0; j < cols.length; j++) {
					var col = cols[j];
					if (col.field == field) {
						return col;
					}
				}
			}
		}
		if (opts.frozenColumns) {
			for (var i = 0; i < opts.frozenColumns.length; i++) {
				var cols = opts.frozenColumns[i];
				for (var j = 0; j < cols.length; j++) {
					var col = cols[j];
					if (col.field == field) {
						return col;
					}
				}
			}
		}
		return null;
	};
	function getColumnFields(target, frozen) {
		var opts = $.data(target, "grid-data").options;
		var columns = (frozen == true)
				? (opts.frozenColumns || [[]])
				: opts.columns;
		if (columns.length == 0) {
			return [];
		}
		function getFields(ridx, cidx, count) {
			var fields = [];
			while (fields.length < count) {
				var col = columns[ridx][cidx];
				if (col.colspan && parseInt(col.colspan) > 1) {
					var ff = getFields(ridx + 1, _39e(ridx, cidx),
							parseInt(col.colspan));
					fields = fields.concat(ff);
				} else {
					if (col.field) {
						fields.push(col.field);
					}
				}
				cidx++;
			}
			return fields;
		};
		function getSubColIndex(ridx, cidx) {
			var index = 0;
			for (var i = 0; i < cidx; i++) {
				var colspan = parseInt(colspan[ridx][i].colspan || "1");
				if (colspan > 1) {
					index += colspan;
				}
			}
			return index;
		};
		var fields = [];
		for (var i = 0; i < columns[0].length; i++) {
			var col = columns[0][i];
			if (col.colspan && parseInt(col.colspan) > 1) {
				var ff = getFields(1, getSubColIndex(0, i),
						parseInt(col.colspan));
				fields = fields.concat(ff);
			} else {
				if (col.field) {
					fields.push(col.field);
				}
			}
		}
		return fields;
	};
	/**
	 * @desc: 装载列表数据
	 */
	function loadData(target, data, isSort) {
		var opts = $.data(target, "grid-data").options;
		var wrap = $.data(target, "grid-data").panel.find('>div.grid-wrap');
		var selectedRows = $.data(target, "grid-data").selectedRows;
		// wangkun: 2011/1/8
		// 根据后台人员需要，暂时增加一段判断代码，实现如下功能：
		// 将json数据中的collection属性更改成rows
		if (!data.rows) {
			data = {
				total : data.total,
				rows : data.collection
			}
		}
		//
		// if(data.total > opts.pageSize){
		// data.rows = data.rows.slice(0, opts.pageSize);
		// }
		var rows = data.rows.slice(0, opts.pageSize);
		$.data(target, "grid-data").data = data;
		if (!opts.remoteSort) {
			var opt = getColumnOption(target, opts.sortName);
			if (opt) {
				var isBigger = opt.sorter || function(a, b) {
					return (a > b ? 1 : -1);
				};
				if (isSort) {
					data.rows.sort(function(r1, r2) {
								return isBigger(r1[opts.sortName],
										r2[opts.sortName])
										* (opts.sortOrder == "desc" ? 1 : -1);
							});
				}
			}
		}
		var view = wrap.find(">div.grid-view");
		var view1 = view.find(">div.grid-view1");
		var view2 = view.find(">div.grid-view2");
		var columnFields = getColumnFields(target, false);
		view2.find(">div.grid-body").html(getTBody(columnFields));
		if (opts.rownumbers
				|| (opts.frozenColumns && opts.frozenColumns.length > 0)) {
			var idFields = getColumnFields(target, true);
			view1.find(">div.grid-body>div.grid-body-inner").html(getTBody(
					idFields, opts.rownumbers));
		}
		opts.onLoadSuccess.call(target, data);
		view2.find(">div.grid-body").scrollLeft(0).scrollTop(0);
		var pager = $(">div.grid-pager", wrap);
		if (pager.length) {
			if (pager.pagination("option", 'total') != data.total) {
				pager.pagination({
							total : data.total
						});
			}
		}
		setRowHeight(target);
		bindRowEvent(target);
		function getTBody(fields, rownumbers) {
			function isSelected(row) {
				if (!opts.idField) {
					return false;
				}
				for (var i = 0; i < selectedRows.length; i++) {
					if (selectedRows[i][opts.idField] == row[opts.idField]) {
						selectedRows[i] = row;
						return true;
					}
				}
				return false;
			};
			var tableHtml = ["<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				var isSelect = isSelected(row);
				if (i % 2 && opts.striped) {
					tableHtml.push("<tr grid-row-index=\"" + i
							+ "\" class=\"grid-row-alt");
				} else {
					tableHtml.push("<tr grid-row-index=\"" + i + "\" class=\"");
				}
				if (isSelect == true) {
					tableHtml.push(" grid-row-selected");
				}
				tableHtml.push("\">");
				if (rownumbers) {
					var _3b1 = i + 1;
					if (opts.pagination) {
						_3b1 += (opts.pageNumber - 1) * opts.pageSize;
					}
					tableHtml
							.push("<td class=\"grid-td-rownumber\"><div class=\"grid-cell-rownumber\">"
									+ _3b1 + "</div></td>");
				}
				for (var j = 0; j < fields.length; j++) {
					var field = fields[j];
					var col = getColumnOption(target, field);
					if (col) {
						var _3b3 = "width:" + (col.width) + "px;";
						_3b3 += "text-align:center;";
						// _3b3 += "text-align:" + (col.align || "left") + ";";
						_3b3 += opts.nowrap == false
								? "white-space:normal;"
								: "";
						tableHtml.push("<td field=\"" + field + "\">");
						tableHtml.push("<div style=\"" + _3b3 + "\" ");
						if (col.checkbox) {
							tableHtml.push("class=\"grid-cell-check ");
						} else {
							tableHtml.push("class=\"grid-cell ");
						}
						tableHtml.push("\">");
						if (col.checkbox) {
							if (isSelect) {
								tableHtml
										.push("<input type=\"checkbox\" checked=\"checked\"/>");
							} else {
								tableHtml.push("<input type=\"checkbox\"/>");
							}
						} else {
							if (col.formatter) {
								tableHtml.push(col
										.formatter(row[field], row, i));
							} else {
								tableHtml.push(row[field]);
							}
						}
						tableHtml.push("</div>");
						tableHtml.push("</td>");
					}
				}
				tableHtml.push("</tr>");
			}
			tableHtml.push("</tbody></table>");
			return tableHtml.join("");
		};
	};
	function getRowIndex(target, row) {
		var opts = $.data(target, "grid-data").options;
		var rows = $.data(target, "grid-data").data.rows;
		if (typeof row == "object") {
			return rows.indexOf(row);
		} else {
			for (var i = 0; i < rows.length; i++) {
				if (rows[i][opts.idField] == row) {
					return i;
				}
			}
			return -1;
		}
	};
	function getSelected(target) {

		var opts = $.data(target, "grid-data").options;
		var panel = $.data(target, "grid-data").panel;
		var data = $.data(target, "grid-data").data;
		if (opts.idField) {
			var deletedRows = $.data(target, "grid-data").deletedRows;
			var selectedRows = $.data(target, "grid-data").selectedRows;
			var rows = [];
			for (var i = 0; i < selectedRows.length; i++) {
				(function() {
					var row = selectedRows[i];
					for (var j = 0; j < deletedRows.length; j++) {
						if (row[opts.idField] == deletedRows[j][opts.idField]) {
							return;
						}
					}
					rows.push(row);
				})();
			}
			return rows;
		}
		var rows = [];
		$("div.grid-view2 div.grid-body tr.grid-row-selected", panel).each(
				function() {
					var _3bb = parseInt($(this).attr("grid-row-index"));
					if (data.rows[_3bb]) {
						rows.push(data.rows[_3bb]);
					}
				});
		return rows;
	};
	function clearSelections(_3bd) {
		unselectAll(_3bd);
		var _3bf = $.data(_3bd, "grid-data").selectedRows;
		while (_3bf.length > 0) {
			_3bf.pop();
		}
	};
	function selectAll(_3c1) {
		var opts = $.data(_3c1, "grid-data").options;
		var _3c2 = $.data(_3c1, "grid-data").panel;
		var data = $.data(_3c1, "grid-data").data;
		var _3c3 = $.data(_3c1, "grid-data").selectedRows;
		var rows = data.rows;
		var body = _3c2.find("div.grid-body");
		$("tr", body).addClass("grid-row-selected");
		$("div.grid-cell-check input[type=checkbox]", body).attr("checked",
				true);
		for (var _3c4 = 0; _3c4 < rows.length; _3c4++) {
			if (opts.idField) {
				(function() {
					var row = rows[_3c4];
					for (var i = 0; i < _3c3.length; i++) {
						if (_3c3[i][opts.idField] == row[opts.idField]) {
							return;
						}
					}
					_3c3.push(row);
				})();
			}
		}
		opts.onSelectAll.call(_3c1, rows);
	};
	function unselectAll(target) {
		var opts = $.data(target, "grid-data").options;
		var panel = $.data(target, "grid-data").panel;
		var data = $.data(target, "grid-data").data;
		var selectedRows = $.data(target, "grid-data").selectedRows;
		$("div.grid-body tr.grid-row-selected", panel)
				.removeClass("grid-row-selected");
		$("div.grid-body div.grid-cell-check input[type=checkbox]", panel)
				.attr("checked", false);
		if (opts.idField) {
			for (var _3c8 = 0; _3c8 < data.rows.length; _3c8++) {
				var id = data.rows[_3c8][opts.idField];
				for (var i = 0; i < selectedRows.length; i++) {
					if (selectedRows[i][opts.idField] == id) {
						selectedRows.splice(i, 1);
						break;
					}
				}
			}
		}
		opts.onUnselectAll.call(target, data.rows);
	};
	function selectRow(target, index) {
		var panel = $.data(target, "grid-data").panel;
		var opts = $.data(target, "grid-data").options;
		var data = $.data(target, "grid-data").data;
		var selectedRows = $.data(target, "grid-data").selectedRows;
		if (index < 0 || index >= data.rows.length) {
			return;
		}
		var tr = $("div.grid-body tr[grid-row-index=" + index + "]", panel);
		var ck = $("div.grid-cell-check input[type=checkbox]", tr);
		tr.addClass("grid-row-selected");
		ck.attr("checked", true);
		var view2 = panel.find("div.grid-view2");
		var headerHeight = view2.find("div.grid-header").outerHeight();
		var body = view2.find("div.grid-body");
		var top = tr.position().top - headerHeight;
		if (top <= 0) {
			body.scrollTop(body.scrollTop() + top);
		} else {
			if (top + tr.outerHeight() > body.height() - 18) {
				body.scrollTop(body.scrollTop() + top + tr.outerHeight()
						- body.height() + 18);
			}
		}
		if (opts.idField) {
			var row = data.rows[index];
			for (var i = 0; i < selectedRows.length; i++) {
				if (selectedRows[i][opts.idField] == row[opts.idField]) {
					return;
				}
			}
			selectedRows.push(row);
		}
		opts.onSelect.call(target, index, data.rows[index]);
	};
	function selectRecord(_3d2, _3d3) {
		var opts = $.data(_3d2, "grid-data").options;
		var data = $.data(_3d2, "grid-data").data;
		if (opts.idField) {
			var _3d4 = -1;
			for (var i = 0; i < data.rows.length; i++) {
				if (data.rows[i][opts.idField] == _3d3) {
					_3d4 = i;
					break;
				}
			}
			if (_3d4 >= 0) {
				selectRow(_3d2, _3d4);
			}
		}
	};
	function unselectRow(target, row_index) {
		var opts = $.data(target, "grid-data").options;
		var panel = $.data(target, "grid-data").panel;
		var data = $.data(target, "grid-data").data;
		var selectedRows = $.data(target, "grid-data").selectedRows;
		if (row_index < 0 || row_index >= data.rows.length) {
			return;
		}
		var body = panel.find("div.grid-body");
		var tr = $("tr[grid-row-index=" + row_index + "]", body);
		var ck = $("tr[grid-row-index=" + row_index
						+ "] div.grid-cell-check input[type=checkbox]", body);
		tr.removeClass("grid-row-selected");
		ck.attr("checked", false);
		var row = data.rows[row_index];
		if (opts.idField) {
			for (var i = 0; i < selectedRows.length; i++) {
				var row1 = selectedRows[i];
				if (row1[opts.idField] == row[opts.idField]) {
					for (var j = i + 1; j < selectedRows.length; j++) {
						selectedRows[j - 1] = selectedRows[j];
					}
					selectedRows.pop();
					break;
				}
			}
		}
		opts.onUnselect.call(target, row_index, row);
	};
	function beginEdit(target, rowIndex) {
		var opts = $.data(target, "grid-data").options;
		var panel = $.data(target, "grid-data").panel;
		var data = $.data(target, "grid-data").data;
		var editingRows = $.data(target, "grid-data").editingRows;
		var tr = $("div.grid-body tr[grid-row-index=" + rowIndex + "]", panel);
		if (tr.hasClass("grid-row-editing")) {
			return;
		}
		if (opts.onBeforeEdit.call(target, rowIndex, data.rows[rowIndex]) == false) {
			return;
		}
		tr.addClass("grid-row-editing");
		initEditor(target, rowIndex);
		setEditorWidth(target);
		editingRows.push(data.rows[rowIndex]);
		setEditorValue(target, rowIndex, data.rows[rowIndex]);
		validateRow(target, rowIndex);
	};
	function cellEdit(target, rowIndex, _3e5) {
		var opts = $.data(target, "grid-data").options, panel = $.data(target,
				"grid-data").panel, data = $.data(target, "grid-data").data, updatedRows = $
				.data(target, "grid-data").updatedRows, insertedRows = $.data(
				target, "grid-data").insertedRows, editingRows = $.data(target,
				"grid-data").editingRows, row = data.rows[rowIndex], tr = $(
				"div.grid-body tr[grid-row-index=" + rowIndex + "]", panel);
		if (!tr.hasClass("grid-row-editing")) {
			return;
		}
		if (!_3e5) {
			if (!validateRow(target, rowIndex)) {
				return;
			}
			var _3ea = false;
			var _3eb = {};
			var nd = getEditorValue(target, rowIndex);
			for (var _3ed in nd) {
				if (row[_3ed] != nd[_3ed]) {
					row[_3ed] = nd[_3ed];
					_3ea = true;
					_3eb[_3ed] = nd[_3ed];
				}
			}
			if (_3ea) {
				if (insertedRows.indexOf(row) == -1) {
					if (updatedRows.indexOf(row) == -1) {
						updatedRows.push(row);
					}
				}
			}
		}
		tr.removeClass("grid-row-editing");
		editingRows.remove(row);
		initEditors(target, rowIndex);
		refreshRow(target, rowIndex);
		if (!_3e5) {
			opts.onAfterEdit.call(target, rowIndex, row, _3eb);
		} else {
			opts.onCancelEdit.call(target, rowIndex, row);
		}
	};
	/**
	 * @private setEditorValue
	 */
	function setEditorValue(target, _3f1, data) {
		var panel = $.data(target, "grid-data").panel;
		var tr = $("div.grid-body tr[grid-row-index=" + _3f1 + "]", panel);
		if (!tr.hasClass("grid-row-editing")) {
			return;
		}
		tr.find("div.grid-editable").each(function() {
					var field = $(this).parent().attr("field");
					var ed = $.data(this, "grid.editor");
					ed.actions.setValue(ed.target, data[field]);
				});
	};
	/**
	 * @private getEditorValue
	 */
	function getEditorValue(target, _3f5) {
		var _3f6 = $.data(target, "grid-data").panel;
		var tr = $("div.grid-body tr[grid-row-index=" + _3f5 + "]", _3f6);
		if (!tr.hasClass("grid-row-editing")) {
			return {};
		}
		var data = {};
		tr.find("div.grid-editable").each(function() {
					var _3f7 = $(this).parent().attr("field");
					var ed = $.data(this, "grid.editor");
					data[_3f7] = ed.actions.getValue(ed.target);
				});
		return data;
	};
	function getEditors(_3f9, _3fa) {
		var _3fb = [];
		var _3fc = $.data(_3f9, "grid-data").panel;
		var tr = $("div.grid-body tr[grid-row-index=" + _3fa + "]", _3fc);
		tr.find(">td").each(function() {
					var cell = $(this).find("div.grid-editable");
					if (cell.length) {
						var ed = $.data(cell[0], "grid.editor");
						_3fb.push(ed);
					}
				});
		return _3fb;
	};
	function getEditor(_3fe, _3ff) {
		var _400 = getEditors(_3fe, _3ff.index);
		for (var i = 0; i < _400.length; i++) {
			if (_400[i].field == _3ff.field) {
				return _400[i];
			}
		}
		return null;
	};
	/**
	 * @private initEditor
	 */
	function initEditor(target, rowIndex) {
		var opts = $.data(target, "grid-data").options;
		var panel = $.data(target, "grid-data").panel;
		var tr = $("div.grid-body tr[grid-row-index=" + rowIndex + "]", panel);
		tr.find(">td").each(function() {
			var cell = $(this).find("div.grid-cell");
			var field = $(this).attr("field");
			var col = getColumnOption(target, field);
			if (col && col.editor) {
				var editorType, editorOptions;
				if (typeof col.editor == "string") {
					editorType = col.editor;
				} else {
					editorType = col.editor.type;
					editorOptions = col.editor.options;
					// wangkun 2011/1/6
					// 若需要初始化combobox时，为当前的选项添加“selectd”属性
					if (editorType == "combobox") {
						var selCol = $(this).children("div").html(), textfield = col.editor.options.textField;
						item = null;
						for (var i = 0, len = col.editor.options.data.length; i < len; i++) {
							item = col.editor.options.data[i];
							if (item[textfield] == selCol) {
								item["selected"] = true;
								break;
							}
						}
					}
				}
				var ed = opts.editors[editorType];
				if (ed) {
					var ow = cell.outerWidth();
					cell.addClass("grid-editable");
					if ($.boxModel == true) {
						cell.width(ow - (cell.outerWidth() - cell.width()));
					}
					cell
							.html("<table style='width:100%;' border=\"0\" cellspacing=\"0\" cellpadding=\"1\"><tr><td></td></tr></table>");
					cell.find("table").attr("align", col.align);
					$.data(cell[0], "grid.editor", {
								actions : ed,
								target : ed
										.init(cell.find("td"), editorOptions),
								field : field,
								type : editorType
							});
				}
			}
		});
		setRowHeight(target, rowIndex);
	};
	/**
	 * @private initEditor
	 */
	function initEditors(target, rowIndex) {
		var panel = $.data(target, "grid-data").panel, tr = $(
				"div.grid-body tr[grid-row-index=" + rowIndex + "]", panel), cellHeight = null;
		tr.find(">td").each(function() {
					var cell = $(this).find("div.grid-editable");
					if (cell.length) {
						var ed = $.data(cell[0], "grid.editor");
						if (ed.actions.destroy) {
							ed.actions.destroy(ed.target);
						}
						$.removeData(cell[0], "grid.editor");
						var _40c = cell.outerWidth();
						cell.removeClass("grid-editable");
						if ($.boxModel == true) {
							cell.width(_40c
									- (cell.outerWidth() - cell.width()));
						}
					}
				});
	};
	function validateRow(target, rowIndex) {
		var panel = $.data(target, "grid-data").panel;
		var tr = $("div.grid-body tr[grid-row-index=" + rowIndex + "]", panel);
		if (!tr.hasClass("grid-row-editing")) {
			return true;
		}
		var vbox = tr.find(".validatebox-text");
		vbox.validatebox("validate");
		vbox.trigger("mouseleave");
		var _410 = tr.find(".validatebox-invalid");
		return _410.length == 0;
	};
	function getChanges(target, rowType) {
		var insertedRows = $.data(target, "grid-data").insertedRows;
		var deletedRows = $.data(target, "grid-data").deletedRows;
		var updatedRows = $.data(target, "grid-data").updatedRows;
		if (!rowType) {
			var rows = [];
			rows = rows.concat(insertedRows);
			rows = rows.concat(deletedRows);
			rows = rows.concat(updatedRows);
			return rows;
		} else {
			if (rowType == "inserted") {
				return insertedRows;
			} else {
				if (rowType == "deleted") {
					return deletedRows;
				} else {
					if (rowType == "updated") {
						return updatedRows;
					}
				}
			}
		}
		return [];
	};
	function refreshRow(target, rowIndex) {
		var panel = $.data(target, "grid-data").panel;
		var data = $.data(target, "grid-data").data;
		panel.find("div.grid-body tr[grid-row-index=" + rowIndex + "] td")
				.each(function() {
					var cell = $(this).find("div.grid-cell");
					var field = $(this).attr("field");
					var col = getColumnOption(target, field);
					if (col) {
						if (col.formatter) {
							cell.html(col.formatter(data.rows[rowIndex][field],
									data.rows[rowIndex], rowIndex));
						} else {
							cell.html(data.rows[rowIndex][field]);
						}
					}
				});
		setRowHeight(target, rowIndex);
	};
	function deleteRow(target, rowIndex) {
		var data = $.data(target, "grid-data").data;
		var insertedRows = $.data(target, "grid-data").insertedRows;
		var deletedRows = $.data(target, "grid-data").deletedRows;
		var editingRows = $.data(target, "grid-data").editingRows;
		var selectedRows = $.data(target, "grid-data").selectedRows;
		var row = data.rows[rowIndex];
		data.total -= 1;
		if (insertedRows.indexOf(row) >= 0) {
			insertedRows.remove(row);
			selectedRows.remove(row);
		} else {
			deletedRows.push(row);
		}
		if (editingRows.indexOf(row) >= 0) {
			editingRows.remove(row);
			initEditors(target, rowIndex);
		}
		var tempArr = [];
		for (var i = 0; i < editingRows.length; i++) {
			var idx = data.rows.indexOf(editingRows[i]);
			tempArr.push(getEditorValue(target, idx));
			initEditors(target, idx);
		}
		data.rows.remove(row);
		loadData(target, data, false);
		var indexArr = [];
		for (var i = 0; i < editingRows.length; i++) {
			var idx = data.rows.indexOf(editingRows[i]);
			indexArr.push(idx);
		}
		editingRows.splice(0, editingRows.length);
		for (var i = 0; i < indexArr.length; i++) {
			beginEdit(target, indexArr[i]);
			setEditorValue(target, indexArr[i], tempArr[i]);
		}
	};
	function appendRow(target, row) {
		if (!row) {
			return;
		}
		var panel = $.data(target, "grid-data").panel;
		var data = $.data(target, "grid-data").data;
		var insertedRows = $.data(target, "grid-data").insertedRows;
		var editingRows = $.data(target, "grid-data").editingRows;
		data.total += 1;
		data.rows.push(row);
		insertedRows.push(row);
		var _429 = [];
		for (var i = 0; i < editingRows.length; i++) {
			var idx = data.rows.indexOf(editingRows[i]);
			_429.push(getEditorValue(target, idx));
			initEditors(target, idx);
		}
		loadData(target, data, true);
		var _42a = [];
		for (var i = 0; i < editingRows.length; i++) {
			var idx = data.rows.indexOf(editingRows[i]);
			_42a.push(idx);
		}
		editingRows.splice(0, editingRows.length);
		for (var i = 0; i < _42a.length; i++) {
			beginEdit(target, _42a[i]);
			setEditorValue(target, _42a[i], _429[i]);
		}
		var _42b = $("div.grid-view2 div.grid-body", panel);
		var _42c = _42b.find(">table");
		var top = _42c.outerHeight() - _42b.outerHeight();
		_42b.scrollTop(top + 20);
	};
	function initData(target) {
		var data = $.data(target, "grid-data").data;
		var rows = data.rows;
		var tempRows = [];
		for (var i = 0; i < rows.length; i++) {
			tempRows.push($.extend({}, rows[i]));
		}
		$.data(target, "grid-data").originalRows = tempRows;
		$.data(target, "grid-data").updatedRows = [];
		$.data(target, "grid-data").insertedRows = [];
		$.data(target, "grid-data").deletedRows = [];
		$.data(target, "grid-data").editingRows = [];
	};
	/**
	 * @desc: 保存修改后的数据
	 */
	function acceptChanges(target) {
		var data = $.data(target, "grid-data").data;
		var ok = true;
		for (var i = 0, len = data.rows.length; i < len; i++) {
			if (validateRow(target, i)) {
				cellEdit(target, i, false);
			} else {
				ok = false;
			}
		}
		if (ok) {
			initData(target);
		}
	};
	function rejectChanges(target) {
		var opts = $.data(target, "grid-data").options;
		var originalRows = $.data(target, "grid-data").originalRows;
		var insertedRows = $.data(target, "grid-data").insertedRows;
		var deletedRows = $.data(target, "grid-data").deletedRows;
		var updatedRows = $.data(target, "grid-data").updatedRows;
		var selectedRows = $.data(target, "grid-data").selectedRows;
		var data = $.data(target, "grid-data").data;
		for (var i = 0; i < data.rows.length; i++) {
			cellEdit(target, i, true);
		}
		var rows = [];
		var _439 = {};
		if (opts.idField) {
			for (var i = 0; i < selectedRows.length; i++) {
				_439[selectedRows[i][opts.idField]] = true;
			}
		}
		selectedRows.splice(0, selectedRows.length);
		for (var i = 0; i < originalRows.length; i++) {
			var row = $.extend({}, originalRows[i]);
			rows.push(row);
			if (_439[row[opts.idField]]) {
				selectedRows.push(row);
			}
		}
		data.total += deletedRows.length - insertedRows.length;
		data.rows = rows;
		loadData(target, data, true);
		$.data(target, "grid-data").updatedRows = [];
		$.data(target, "grid-data").insertedRows = [];
		$.data(target, "grid-data").deletedRows = [];
		$.data(target, "grid-data").editingRows = [];
	};
	/**
	 * @desc: 请求列表数据
	 */
	function request(target, param) {
		var panel = $.data(target, "grid-data").panel;
		var opts = $.data(target, "grid-data").options;
		if (param) {
			opts.queryParams = param;
		}
		if (!opts.url) {
			return;
		}
		var params = $.extend({}, opts.queryParams);
		if (opts.pagination) {
			$.extend(params, {
						page : opts.pageNumber,
						rows : opts.pageSize
					});
		}
		if (opts.sortName) {
			$.extend(params, {
						sort : opts.sortName,
						order : opts.sortOrder
					});
		}
		if (opts.onBeforeLoad.call(target, params) == false) {
			return;
		}
		setPager();
		setTimeout(function() {
					getDataByAjax();
				}, 0);
		function getDataByAjax() {
			$.ajax({
						type : opts.method,
						url : opts.url,
						data : params,
						dataType : "json",
						success : function(data) {
							setTimeout(function() {
										pagerInit();
									}, 0);
							loadData(target, data, true);
							setTimeout(function() {
										initData(target);
									}, 0);
						},
						error : function() {
							setTimeout(function() {
										pagerInit();
									}, 0);
							if (opts.onLoadError) {
								opts.onLoadError.apply(target, arguments);
							}
						}
					});
		};
		function setPager() {
			$("div.grid-pager", panel).pagination("loading");
			if (opts.loadMsg) {
				var wrap = panel.find('>div.grid-wrap');
				$("<div class=\"grid-mask\"></div>").css({
							display : "block",
							width : wrap.width(),
							height : wrap.height()
						}).appendTo(wrap);
				$("<div class=\"grid-mask-msg\"></div>").html(opts.loadMsg)
						.appendTo(wrap).css({
							display : "block",
							left : (wrap.width() - $("div.grid-mask-msg", wrap)
									.outerWidth())
									/ 2,
							top : (wrap.height() - $("div.grid-mask-msg", wrap)
									.outerHeight())
									/ 2
						});
			}
		};

		function pagerInit() {
			panel.find("div.grid-pager").pagination("loaded");
			panel.find("div.grid-mask-msg").remove();
			panel.find("div.grid-mask").remove();
		};
	};
	function mergeCells(target, param) {
		var rows = $.data(target, "grid-data").data.rows;
		var panel = $.data(target, "grid-data").panel;
		param.rowspan = param.rowspan || 1;
		param.colspan = param.colspan || 1;
		if (param.index < 0 || param.index >= rows.length) {
			return;
		}
		if (param.rowspan == 1 && param.colspan == 1) {
			return;
		}
		var _445 = rows[param.index][param.field];
		var tr = panel.find("div.grid-body tr[grid-row-index=" + param.index
				+ "]");
		var td = tr.find("td[field=" + param.field + "]");
		td.attr("rowspan", param.rowspan).attr("colspan", param.colspan);
		td.addClass("grid-td-merged");
		for (var i = 1; i < param.colspan; i++) {
			td = td.next();
			td.hide();
			rows[param.index][td.attr("field")] = _445;
		}
		for (var i = 1; i < param.rowspan; i++) {
			tr = tr.next();
			var td = tr.find("td[field=" + param.field + "]").hide();
			rows[param.index + i][td.attr("field")] = _445;
			for (var j = 1; j < param.colspan; j++) {
				td = td.next();
				td.hide();
				rows[param.index + i][td.attr("field")] = _445;
			}
		}
		setTimeout(function() {
					setCellWidth(target);
				}, 0);
	};
	/**
	 * @desc: 添加一条记录（默认状态为在列表的末尾处追加）
	 * @param: {Jquery} target 当前的grid对象 {Array} records 需要添加的记录数组 {Number}
	 *         index 在第几条记录后插入（若index为空，则在最后追加记录）
	 */
	function addRow(obj, records, index) {
		var target = obj.el[0];
		if (!records || records.length < 0) {
			return false;
		}
		// 获取当前存在的所有记录集合
		var rows = $.data(target, "grid-data").data.rows, opts = $.data(target,
				"grid-data").options;

		if (!index || index > rows.length || index < 0) {
			index = rows.length;
		} else {
			index = Math.round(index);
		}
		var frontArr = rows.slice(0, index), endArr = rows.slice(index);
		// 添加新纪录
		frontArr = frontArr.concat(records);
		rows = frontArr.concat(endArr);
		$.data(target, "grid-data").data.rows = rows.slice(0,
				obj.options.pageSize);

		loadData(target, $.data(target, "grid-data").data, false);
		initData(target);

	}
})(jQuery);/**
 * tree - wooUI 基于jQuery1.4.2+
 * 
 * 依赖一下组件： draggable,droppable Node 数据: { id:'id', text:'text', checked:false,
 * attributes:{}, target:DOM }
 */
(function($) {
	$.woo.component.subclass('woo.tree', {
		options : {
			url : null,
			animate : false,
			checkbox : false,
			onLoadSuccess : $.noop,
			onLoadError : $.noop,
			onClick : $.noop, // node: id,text,attributes,target
			onDblClick : $.noop
		},

		_create : function() {
			this.el.addClass('tree');
			if (this.options.url) {
				this.request(this.el, this.el);
			}
			this.bindTreeEvents();
			/*
			 * var opts = this.options; if (opts.url){ this.request(this, this); }
			 */
		},

		/**
		 * @desc 生成树的DOM结构
		 * @param {String}
		 *            ul 子节点DOM结构
		 * @param {String}
		 *            children 子节点的数据结构 "children":[{
		 *            "id":4,"text":"File3","attributes":{"p1":"value1",
		 *            "p2":"value2"},"checked":true,"iconCls":"icon-reload"},
		 *            {"id": 8,"text":"Async Folder","state":"closed"}]
		 * @param {Number}
		 *            depth 节点的层级
		 * @return
		 */
		_wrapNode : function(ul, children, depth) {
			var opts = this.options;
			for (var i = 0; i < children.length; i++) {
				var li = $('<li></li>').appendTo(ul);
				var item = children[i];

				// the node state has only 'open' or 'closed' attribute
				if (item.state != 'open' && item.state != 'closed') {
					item.state = 'open';
				}

				var node = $('<div class="tree-node"></div>').appendTo(li);
				node.attr('node-id', item.id);

				// store node attributes
				$.data(node[0], 'tree-node', {
					id : item.id,
					text : item.text,
					attributes : item.attributes
				});

				$('<span class="tree-title"></span>').html(item.text)
						.appendTo(node);
				if (opts.checkbox) {
					if (item.checked) {
						$('<span class="tree-checkbox tree-checkbox1"></span>')
								.prependTo(node);
					} else {
						$('<span class="tree-checkbox tree-checkbox0"></span>')
								.prependTo(node);
					}
				}
				if (item.children) {
					var subul = $('<ul></ul>').appendTo(li);
					if (item.state == 'open') {
						$('<span class="tree-folder tree-folder-open"></span>')
								.addClass(item.iconCls).prependTo(node);
						$('<span class="tree-hit tree-expanded"></span>')
								.prependTo(node);
					} else {
						$('<span class="tree-folder"></span>')
								.addClass(item.iconCls).prependTo(node);
						$('<span class="tree-hit tree-collapsed"></span>')
								.prependTo(node);
						subul.css('display', 'none');
					}
					this._wrapNode(subul, item.children, depth + 1);
				} else {
					if (item.state == 'closed') {
						$('<span class="tree-folder"></span>')
								.addClass(item.iconCls).prependTo(node);
						$('<span class="tree-hit tree-collapsed"></span>')
								.prependTo(node);
					} else {
						// $('<input type="checkbox"
						// style="vertical-align:bottom;margin:0;height:18px;">').prependTo(node);
						$('<span class="tree-file"></span>')
								.addClass(item.iconCls).prependTo(node);
						$('<span class="tree-indent"></span>').prependTo(node);
					}
				}
				for (var j = 0; j < depth; j++) {
					$('<span class="tree-indent"></span>').prependTo(node);
				}
			}
		},

		/**
		 * @desc 加载树组件的数据
		 * @param {String}
		 *            target 目标HTML元素
		 * @param {String}
		 *            ul 树组件的DOM结构
		 * @param {Object}
		 *            data 生成树组件的数据
		 * @return
		 */
		loadData : function(target, ul, data) {
			// 当数据加载到根节点时，把子元素（包括文本节点）删除
			if (target == ul) {
				$(target).empty();
			}
			var depth = $(ul).prev().find('>span.tree-indent,>span.tree-hit').length;
			this._wrapNode(ul, data, depth);
		},

		/**
		 * @desc 获取树组件的数据
		 * @param {String}
		 *            target 目标HTML元素
		 * @param {String}
		 *            ul 树组件的DOM结构
		 * @param {Object}
		 *            param 生成树组件的数据
		 * @return
		 */
		request : function(target, ul, param) {
			var self = this, opts = this.options;
			if (!opts.url)
				return;

			param = param || {};

			var folder = $(ul).prev().find('>span.tree-folder');
			folder.addClass('tree-loading');
			$.ajax({
				type : 'post',
				url : opts.url,
				data : param,
				dataType : 'json',
				success : function(data) {
					folder.removeClass('tree-loading');
					self.loadData(target, ul, data);
					self.bindTreeEvents(target);
					if (opts.onLoadSuccess) {
						opts.onLoadSuccess.apply(this, arguments);
					}
				},
				error : function() {
					folder.removeClass('tree-loading');
					if (opts.onLoadError) {
						opts.onLoadError.apply(this, arguments);
					}
				}
			});
		},

		/**
		 * @desc 绑定树组件的事件行为
		 * @param {String}
		 *            target 目标HTML元素
		 * @return
		 */
		bindTreeEvents : function(target) {
			var el = this.el, self = this, opts = this.options;
			$('.tree-node', el).unbind('.tree').bind('dblclick.tree',
					function() {
						$('.tree-node-selected', el)
								.removeClass('tree-node-selected');
						$(this).addClass('tree-node-selected');

						if (opts.onDblClick) {
							var target = this; // the target HTML DIV element
							var data = $.data(this, 'tree-node');
							opts.onDblClick.call(this, {
								id : data.id,
								text : data.text,
								attributes : data.attributes,
								target : target
							});
						}
					}).bind('click.tree', function() {
				$('.tree-node-selected', el).removeClass('tree-node-selected');
				$(this).addClass('tree-node-selected');

				if (opts.onClick) {
					var target = this; // the target HTML DIV element
					var data = $.data(this, 'tree-node');
					opts.onClick.call(this, {
						id : data.id,
						text : data.text,
						attributes : data.attributes,
						target : target
					});
				}
					// return false;
				}).bind('mouseenter.tree', function() {
				$(this).addClass('tree-node-hover');
				return false;
			}).bind('mouseleave.tree', function() {
				$(this).removeClass('tree-node-hover');
				return false;
			});

			$('.tree-hit', el).unbind('.tree').bind('click.tree', function() {
				var node = $(this).parent();
				self.toggleNode(node);
				return false;
			}).bind('mouseenter.tree', function() {
				if ($(this).hasClass('tree-expanded')) {
					$(this).addClass('tree-expanded-hover');
				} else {
					$(this).addClass('tree-collapsed-hover');
				}
			}).bind('mouseleave.tree', function() {
				if ($(this).hasClass('tree-expanded')) {
					$(this).removeClass('tree-expanded-hover');
				} else {
					$(this).removeClass('tree-collapsed-hover');
				}
			});

			$('.tree-checkbox', el).unbind('.tree').bind('click.tree',
					function() {
						if ($(this).hasClass('tree-checkbox0')) {
							$(this).removeClass('tree-checkbox0')
									.addClass('tree-checkbox1');
						} else if ($(this).hasClass('tree-checkbox1')) {
							$(this).removeClass('tree-checkbox1')
									.addClass('tree-checkbox0');
						} else if ($(this).hasClass('tree-checkbox2')) {
							$(this).removeClass('tree-checkbox2')
									.addClass('tree-checkbox1');
						}
						setParentCheckbox($(this).parent());
						setChildCheckbox($(this).parent());
						return false;
					});

			function setChildCheckbox(node) {
				var childck = node.next().find('.tree-checkbox');
				childck
						.removeClass('tree-checkbox0 tree-checkbox1 tree-checkbox2')
				if (node.find('.tree-checkbox').hasClass('tree-checkbox1')) {
					childck.addClass('tree-checkbox1');
				} else {
					childck.addClass('tree-checkbox0');
				}
			}

			function setParentCheckbox(node) {
				var pnode = self.getParent(target, node[0]);
				if (pnode) {
					var ck = $(pnode.target).find('.tree-checkbox');
					ck
							.removeClass('tree-checkbox0 tree-checkbox1 tree-checkbox2');
					if (isAllSelected(node)) {
						ck.addClass('tree-checkbox1');
					} else if (isAllNull(node)) {
						ck.addClass('tree-checkbox0');
					} else {
						ck.addClass('tree-checkbox2');
					}
					setParentCheckbox($(pnode.target));
				}

				function isAllSelected(n) {
					var ck = n.find('.tree-checkbox');
					if (ck.hasClass('tree-checkbox0')
							|| ck.hasClass('tree-checkbox2'))
						return false;
					var b = true;
					n.parent().siblings().each(function() {
						if (!$(this).find('.tree-checkbox')
								.hasClass('tree-checkbox1')) {
							b = false;
						}
					});
					return b;
				}
				function isAllNull(n) {
					var ck = n.find('.tree-checkbox');
					if (ck.hasClass('tree-checkbox1')
							|| ck.hasClass('tree-checkbox2'))
						return false;
					var b = true;
					n.parent().siblings().each(function() {
						if (!$(this).find('.tree-checkbox')
								.hasClass('tree-checkbox0')) {
							b = false;
						}
					});
					return b;
				}
			}
		},

		/**
		 * @desc 折叠展开树节点
		 * @param {Object}
		 *            node 根节点DOM结构jQuery对象 $('<div class="tree-node"></div>').appendTo(li);
		 * @return
		 */
		toggleNode : function(node) {
			var hit = $('>span.tree-hit', node);
			if (hit.length == 0)
				return; // is a leaf node

			if (hit.hasClass('tree-expanded')) {
				this.collapse(node);
			} else {
				this.expand(node);
			}
		},

		/**
		 * @desc 展开树节点
		 * @param {Object}
		 *            node 根节点DOM结构jQuery对象 $('<div class="tree-node"></div>').appendTo(li);
		 * @return
		 */
		expand : function(node) {
			var el = this.el, opts = this.options;

			var hit = $('>span.tree-hit', node);
			if (hit.length == 0)
				return; // is a leaf node

			if (hit.hasClass('tree-collapsed')) {
				hit.removeClass('tree-collapsed tree-collapsed-hover')
						.addClass('tree-expanded');
				hit.next().addClass('tree-folder-open');
				var ul = $(node).next();
				if (ul.length) {
					if (opts.animate) {
						ul.slideDown();
					} else {
						ul.css('display', 'block');
					}
				} else {
					var id = $.data($(node)[0], 'tree-node').id;
					var subul = $('<ul></ul>').insertAfter(node);
					this.request(node, subul, {
						id : id
					}); // request children nodes data
				}
			}
		},

		/**
		 * @desc 展开树组件所有节点
		 * @return
		 */
		expandAll : function() {
			var _55 = this.getDirectChildren(this.el);
			for (var i = 0; i < _55.length; i++) {
				this.expand(_55[i].target);
				var _57 = this.getChildren(_55[i].target);
				for (var j = 0; j < _57.length; j++) {
					this.expand(_57[j].target);
				}
			}

		},

		/**
		 * @desc 折叠树节点
		 * @param {Object}
		 *            node 根节点DOM结构jQuery对象 $('<div class="tree-node"></div>').appendTo(li);
		 * @return
		 */
		collapse : function(node) {
			var opts = this.options;

			var hit = $('>span.tree-hit', node);
			if (hit.length == 0)
				return; // is a leaf node

			if (hit.hasClass('tree-expanded')) {
				hit.removeClass('tree-expanded tree-expanded-hover')
						.addClass('tree-collapsed');
				hit.next().removeClass('tree-folder-open');
				if (opts.animate) {
					$(node).next().slideUp();
				} else {
					$(node).next().css('display', 'none');
				}
			}
		},

		/**
		 * @desc 折叠树组件所有节点
		 * @return
		 */
		collapseAll : function() {
			var _5f = this.getDirectChildren(this.el);
			for (var i = 0; i < _5f.length; i++) {
				this.collapse(_5f[i].target);
				var _60 = this.getChildren(_5f[i].target);
				for (var j = 0; j < _60.length; j++) {
					this.collapse(_60[j].target);
				}
			}
		},

		/**
		 * @desc 获取当前节点的父节点
		 * @param {String}
		 *            target 目标HTML元素
		 * @param {Object}
		 *            param 生成树组件的数据
		 * @return
		 */
		getParent : function(target, param) {
			var node = $(param).parent().parent().prev();
			if (node.length) {
				return $.extend({}, $.data(node[0], 'tree-node'), {
					target : node[0],
					checked : node.find('.tree-checkbox')
							.hasClass('tree-checkbox1')
				});
			} else {
				return null;
			}
		},

		/**
		 * @desc 获取当前选中的节点
		 * @return
		 */
		getChecked : function() {
			var nodes = [];
			$(this.el).find('.tree-checkbox1').each(function() {
				var node = $(this).parent();
				nodes.push($.extend({}, $.data(node[0], 'tree-node'), {
					target : node[0],
					checked : node.find('.tree-checkbox')
							.hasClass('tree-checkbox1')
				}));
			});
			return nodes;
		},

		/**
		 * @desc 获取选中的节点的数据
		 * @return
		 */
		getSelected : function() {
			var node = this.el.find('div.tree-node-selected');
			if (node.length) {
				return $.extend({}, $.data(node[0], 'tree-node'), {
					target : node[0],
					checked : node.find('.tree-checkbox')
							.hasClass('tree-checkbox1')
				});
			} else {
				return null;
			}
		},

		/**
		 * @desc 附加节点到树组件
		 * @param {Object}
		 *            param 生成树组件的数据
		 * @return
		 */
		appendNodes : function(param) {
			var node = $(param.parent);
			var ul = node.next();
			if (ul.length == 0) {
				ul = $('<ul></ul>').insertAfter(node);
			}

			// ensure the node is a folder node
			if (param.data && param.data.length) {
				var nodeIcon = node.find('span.tree-file');
				if (nodeIcon.length) {
					nodeIcon.removeClass('tree-file').addClass('tree-folder');
					var hit = $('<span class="tree-hit tree-expanded"></span>')
							.insertBefore(nodeIcon);
					if (hit.prev().length) {
						hit.prev().remove();
					}
				}
			}

			this.loadData(node, ul, param.data);
			this.bindTreeEvents(node);
		},

		/**
		 * @desc 从树组件删除节点
		 * @param {String}
		 *            target 目标HTML元素
		 * @return
		 */
		remove : function(target) {
			var node = $(target);
			var li = node.parent();
			var ul = li.parent();
			li.remove();
			if (ul.find('li').length == 0) {// 如果节点被删除以后，其父节点已经没有下级节点，则修改父节点为叶子节点
				var node = ul.prev();
				node.find('.tree-folder').removeClass('tree-folder')
						.addClass('tree-file');
				node.find('.tree-hit').remove();
				$('<span class="tree-indent"></span>').prependTo(node);
				if (ul[0] != target) {
					ul.remove();
				}
			}
		},

		/**
		 * @desc 选择指定的节点
		 * @param {String}
		 *            target 目标HTML元素
		 * @param {Object}
		 *            param DOM对象，表示该节点被选中
		 * @return
		 */
		selectNode : function(target, param) {
			$('div.tree-node-selected', target)
					.removeClass('tree-node-selected');
			$(param).addClass('tree-node-selected');
		},

		/**
		 * @desc 选择指定的节点是否是叶子节点
		 * @param {String}
		 *            target 目标HTML元素
		 * @param {Object}
		 *            param DOM对象，表示该节点被选中
		 * @return
		 */
		isLeaf : function(param) {
			var node = $(param);
			var hit = $('>span.tree-hit', node);
			return hit.length == 0;
		},

		/**
		 * @desc 重新加载树组件数据
		 * @return
		 */
		reload : function() {
			this.el.empty();
			this.request(this.el, this.el);
		},

		/**
		 * @desc 获取子节点
		 * @param {String}
		 *            target 目标HTML元素
		 * @param {Object}
		 *            node 当前节点的DOM结构
		 * @return
		 */
		getChildren : function(target, node) {
			var children = [];
			if (node) {
				findChildren($(node));
			} else {
				var roots = this.getDirectChildren($(target).next());
				for (var i = 0; i < roots.length; i++) {
					children.push(roots[i]);
					findChildren($(roots[i].target));
				}
			}
			function findChildren(nodeEl) {
				nodeEl.next().find("div.tree-node").each(function() {
					children.push($.extend({}, $.data(this, "tree-node"), {
						target : this,
						checked : $(this).find(".tree-checkbox")
								.hasClass("tree-checkbox1")
					}));
				});
			};
			return children;
		},

		/**
		 * @desc 获取第一个叶子节点
		 * @param {Object}
		 *            node 当前节点的DOM结构
		 * @return
		 */
		getFirstChild : function(node) {
			var children = getDirectChildren(node);
			if (children.length) {
				return children[0];
			} else {
				return null;
			}
		},

		/**
		 * @desc 获取指定的叶子节点
		 * @param {String}
		 *            target 目标HTML元素
		 * @return
		 */
		getDirectChildren : function(target) {
			var roots = [];
			$(target).find(">li").each(function() {
				var _66 = $(this).find(">div.tree-node");
				roots.push($.extend({}, $.data(_66[0], "tree-node"), {
					target : _66[0],
					checked : _66.find(".tree-checkbox")
							.hasClass("tree-checkbox1")
				}));
			});
			return roots;
		},

		/**
		 * @desc 更新树组件
		 * @param {Object}
		 *            node 当前节点的DOM结构
		 * @return
		 */
		update : function(node) {
			var nodeDom = $(node.target);
			var nodeData = $.data(node.target, "tree-node");
			if (nodeData.iconCls) {
				nodeDom.find(".tree-icon").removeClass(nodeData.iconCls);
			}
			$.extend(nodeData, node);
			$.data(node.target, "tree-node", nodeData);
			nodeDom.attr("node-id", nodeData.id);
			nodeDom.find(".tree-title").html(nodeData.text);
			if (nodeData.iconCls) {
				nodeDom.find(".tree-icon").addClass(nodeData.iconCls);
			}
			var ck = nodeDom.find(".tree-checkbox");
			ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
			if (nodeData.checked) {
				ck.addClass("tree-checkbox1");
			} else {
				ck.addClass("tree-checkbox0");
			}
		}

	});
})(jQuery);
/**
 * tabletree - wooUI 基于jQuery1.4.2+
 * 
 * 
 * 依赖于以下组件:: grid
 * 
 */

(function($) {
	$.woo.component.subclass('woo.treegrid', {
				options : {
					/**
					 * @desc: 全选功能
					 * @type： {Object}
					 */
					frozenColumns : null,
					/**
					 * @desc: 列字段
					 * @type： {Object}
					 */
					columns : null,
					/**
					 * @desc: 工具栏
					 * @type： {Object}
					 */
					toolbar : null,
					/**
					 * @desc: 是否为行记录添加条纹
					 * @type： {Boolean}
					 */
					striped : false,
					/**
					 * @desc: 是否在同一行当中显示所有数据
					 * @type： {Boolean}
					 */
					nowrap : true,
					/**
					 * @desc: 列表中的唯一标示字段
					 * @type： {String}
					 */
					idField : '',
					/**
					 * @desc: ajax获取数据源的url参数
					 * @type： {String}
					 */
					url : null,
					/**
					 * @desc: ajax获取数据源的提交方式
					 * @type： {String}
					 */
					method : "post",
					/**
					 * @desc: 数据加载过程中的提示文字
					 * @type： {String}
					 */
					loadMsg : "正在加载中..........",
					/**
					 * @desc: 数据加载过程中的提示文字
					 * @type： {String}
					 */
					rownumbers : false,
					singleSelect : false,
					pagination : false,
					pageNumber : 1,
					pageSize : 10,
					pageList : [10, 20, 30, 40, 50],
					queryParams : {},
					sortName : null,
					sortOrder : "asc",
					remoteSort : true,
					// editors : editors,
					/**
					 * 
					 */
					statBar : false,
					statfn : function(grid) {

					},

					onBeforeLoad : function(_4a5) {
					},
					onResize : $.noop,
					onLoadSuccess : $.noop,
					onExpand : $.noop,
					onLoadError : function() {
					},
					onClickRow : function(rowIndex, rowData) {
					},
					onDblClickRow : function(rowIndex, rowData) {
					},
					onSortColumn : function(sort, order) {
					},
					onResizeColumn : function(_4ab, _4ac) {
					},
					onSelect : function(rowIndex, rowData) {
					},
					onUnselect : function(rowIndex, rowData) {
					},
					onSelectAll : function(rows) {
					},
					onUnselectAll : function(rows) {
					},
					onBeforeEdit : function(_4b1, _4b2) {
					},
					onAfterEdit : function(_4b3, _4b4, _4b5) {
					},
					onCancelEdit : function(_4b6, _4b7) {
					},

					treeField : null,

					/**
					 * @event 加载数据前执行，如果该回调函数返回false，则退出加载
					 * @param {Object}
					 *            row 数据
					 * @param {Object}
					 *            param 请求参数
					 * @return {Boolean} 返回true，加载数据的动作继续，返回false则退出加载
					 */
					onBeforeLoad : $.noop,

					/**
					 * @event 加载数据成功后执行
					 * @param {Object}
					 *            row 数据
					 */
					onLoadSuccess : $.noop,

					/**
					 * @event 加载数据失败后执行
					 */
					onLoadError : $.noop,

					/**
					 * @event 行折叠前执行，如果该回调函数返回false，则取消折叠
					 * @param {Object}
					 *            row 数据
					 * @return {Boolean} 返回true，折叠动作继续，返回false则取消折叠
					 */
					onBeforeCollapse : $.noop,

					/**
					 * @event 行折叠后执行
					 * @param {Object}
					 *            row 数据
					 */
					onCollapse : $.noop,

					/**
					 * @event 行展开前执行，如果该回调函数返回false，则取消展开
					 * @param {Object}
					 *            row 数据
					 * @return {Boolean} 返回true，展开动作继续，返回false则取消展开
					 */
					onBeforeExpand : $.noop,

					/**
					 * @event 行展开后执行
					 * @param {Object}
					 *            row 数据
					 */
					onExpand : $.noop
				},

				_create : function() {
					var target = this.el[0];
					$.data(target, 'treegrid-data', {
								options : this.options,
								data : []
							});
					initGrid(target);
					load(target);
				},
				resize : function(param) {
					$(this).grid("resize", param);
				},
				loadData : function(data) {
					loadData(this, null, data);
				},
				reload : function(id) {
					if (id) {
						var node = $(this).treegrid("find", id);
						if (node.children) {
							node.children.splice(0, node.children.length);
						}
						var body = $(this).grid("getPanel")
								.children("div.grid-wrap")
								.find("div.grid-body");
						var tr = body.find("tr[node-id=" + id + "]");
						tr.next("tr.treegrid-tr-tree").remove();
						var hit = tr.find("span.tree-hit");
						hit.removeClass("tree-expanded tree-expanded-hover")
								.addClass("tree-collapsed");
						expand(this.el[0], id);
					} else {
						load(this.el[0]);
					}
				},
				getData : function() {
					return $.data(this.el[0], "treegrid-data").data;
				},
				getRoot : function() {
					return getRoot(this.el[0]);
				},
				getRoots : function() {
					return getRoots(this.el[0]);
				},
				getParent : function(id) {
					return getParent(this.el[0], id);
				},
				getChildren : function(id) {
					return getChildren(this.el[0], id);
				},
				getSelected : function() {
					return getSelected(this.el[0]);
				},
				getSelections : function() {
					return getSelections(this.el[0]);
				},
				find : function(id) {
					return find(this.el[0], id);
				},
				select : function(id) {
					select(this.el[0], id);
				},
				unselect : function(id) {
					unselect(this.el[0], id);
				},
				selectAll : function() {
					selectAll(this.el[0]);
				},
				unselectAll : function() {
					unselectAll(this.el[0]);
				},
				collapse : function(id) {
					collapse(this.el[0], id);
				},
				expand : function(id) {
					expand(this.el[0], id);
				},
				toggle : function(id) {
					toggle(this.el[0], id);
				},
				collapseAll : function(id) {
					collapseAll(this.el[0], id);
				},
				expandAll : function(id) {
					expandAll(this.el[0], id);
				},
				expandTo : function(id) {
					expandTo(this.el[0], id);
				},
				append : function(_59d) {
					append(this.el[0], _59d);
				},
				remove : function(id) {
					remove(this.el[0], id);
				},
				refresh : function(id) {
					refresh(this.el[0], id);
				}
			});

	function initGrid(target) {
		var opts = $.data(target, "treegrid-data").options;
		$(target).grid($.extend({}, opts, {
					url : null,
					onLoadSuccess : function() {
					},
					onResizeColumn : function(_50c, _50d) {
						setSize(target);
						opts.onResizeColumn.call(target, _50c, _50d);
					}
				}));
	};
	function setSize(target, nodeId) {
		var opts = $.data(target, "grid-data").options;
		var panel = $.data(target, "grid-data").panel;
		var view = panel.children("div.grid-view");
		var view1 = view.children("div.grid-view1");
		var view2 = view.children("div.grid-view2");
		if (opts.rownumbers
				|| (opts.frozenColumns && opts.frozenColumns.length > 0)) {
			if (nodeId) {
				setTrHeight(nodeId);
				view2.find("tr[node-id=" + nodeId + "]")
						.next("tr.treegrid-tr-tree").find("tr[node-id]").each(
								function() {
									setTrHeight($(this).attr("node-id"));
								});
			} else {
				view2.find("tr[node-id]").each(function() {
							setTrHeight($(this).attr("node-id"));
						});
			}
		}
		if (opts.height == "auto") {
			var v2Body = view2.find("div.grid-body table").height() + 18;
			view1.find("div.grid-body").height(v2Body);
			view2.find("div.grid-body").height(v2Body);
			view.height(view2.height());
		}
		// 设定表格中每一行的高度
		function setTrHeight(nodeId) {
			var tr1 = view1.find("tr[node-id=" + nodeId + "]");
			var tr2 = view2.find("tr[node-id=" + nodeId + "]");
			tr1.css("height", null);
			tr2.css("height", null);
			var trHeight = Math.max(tr1.height(), tr2.height());
			tr1.css("height", trHeight);
			tr2.css("height", trHeight);
		};
	};
	function setRowNumber(target) {
		var opts = $.data(target, "treegrid-data").options;
		if (!opts.rownumbers) {
			return;
		}
		$(target).grid("getPanel").children("div.grid-wrap")
				.find("div.grid-view1 div.grid-body div.grid-cell-rownumber")
				.each(function(i) {
							$(this).html(i + 1);
						});
	};
	function bindEvents(target) {
		var opts = $.data(target, "treegrid-data").options;
		var gridWrap = $(target).grid("getPanel").children("div.grid-wrap");
		var body = gridWrap.find("div.grid-body");
		body.find("span.tree-hit").unbind(".treegrid").bind("click.treegrid",
				function() {
					var tr = $(this).parent().parent().parent();
					var id = tr.attr("node-id");
					toggle(target, id);
					return false;
				}).bind("mouseenter.treegrid", function() {
					if ($(this).hasClass("tree-expanded")) {
						$(this).addClass("tree-expanded-hover");
					} else {
						$(this).addClass("tree-collapsed-hover");
					}
				}).bind("mouseleave.treegrid", function() {
					if ($(this).hasClass("tree-expanded")) {
						$(this).removeClass("tree-expanded-hover");
					} else {
						$(this).removeClass("tree-collapsed-hover");
					}
				});
		body.find("tr[node-id]").unbind(".treegrid").bind(
				"mouseenter.treegrid", function() {
					var id = $(this).attr("node-id");
					body.find("tr[node-id=" + id + "]")
							.addClass("grid-row-over");
				}).bind("mouseleave.treegrid", function() {
					var id = $(this).attr("node-id");
					body.find("tr[node-id=" + id + "]")
							.removeClass("grid-row-over");
				}).bind("click.treegrid", function() {
					var id = $(this).attr("node-id");
					if (opts.singleSelect) {
						unselectAll(target);
						select(target, id);
					} else {
						if ($(this).hasClass("grid-row-selected")) {
							unselect(target, id);
						} else {
							select(target, id);
						}
					}
					opts.onClickRow.call(target, find(target, id));
					return false;
				}).bind("dblclick.treegrid", function() {
					var id = $(this).attr("node-id");
					opts.onDblClickRow.call(target, find(target, id));
					return false;
				}).bind("contextmenu.treegrid", function(e) {
					var id = $(this).attr("node-id");
					opts.onContextMenu.call(target, e, find(target, id));
				});
		body.find("div.grid-cell-check input[type=checkbox]")
				.unbind(".treegrid").bind("click.treegrid", function(e) {
					var id = $(this).parent().parent().parent().attr("node-id");
					if (opts.singleSelect) {
						unselectAll(target);
						select(target, id);
					} else {
						if ($(this).attr("checked")) {
							select(target, id);
						} else {
							unselect(target, id);
						}
					}
					e.stopPropagation();
				});
		var gridHeader = gridWrap.find("div.grid-header");
		gridHeader.find("input[type=checkbox]").unbind().bind("click.treegrid",
				function() {
					if (opts.singleSelect) {
						return false;
					}
					if ($(this).attr("checked")) {
						selectAll(target);
					} else {
						unselectAll(target);
					}
				});
	};
	function getHitArr(target, nodeId) {
		var opts = $.data(target, "grid-data").options, view = $(target)
				.grid("getPanel").children("div.grid-wrap")
				.children("div.grid-view"), view1 = view
				.children("div.grid-view1"), view2 = view
				.children("div.grid-view2"), tr1 = view1
				.children("div.grid-body").find("tr[node-id=" + nodeId + "]"), tr2 = view2
				.children("div.grid-body").find("tr[node-id=" + nodeId + "]"), tr1Tree = tr1
				.next("tr.treegrid-tr-tree"), tr2Tree = tr2
				.next("tr.treegrid-tr-tree"), div1 = tr1Tree.children("td")
				.find("div"), div2 = tr2Tree.children("td").find("div"), td1 = tr1
				.find("td[field=" + opts.treeField + "]"), td2 = tr2
				.find("td[field=" + opts.treeField + "]"), hitNum = td1
				.find("span.tree-indent,span.tree-hit").length
				+ td2.find("span.tree-indent,span.tree-hit").length;
		return [div1, div2, hitNum];
	};
	function initTrHtml(target, nodeId) {
		var opts = $.data(target, "treegrid-data").options;
		var view = $(target).grid("getPanel").children("div.grid-wrap")
				.children("div.grid-view");
		var view1 = view.children("div.grid-view1");
		var view2 = view.children("div.grid-view2");
		var tr1 = view1.children("div.grid-body").find("tr[node-id=" + nodeId
				+ "]");
		var tr2 = view2.children("div.grid-body").find("tr[node-id=" + nodeId
				+ "]");
		var _52d = $(target).grid("getColumnFields", true).length
				+ (opts.rownumbers ? 1 : 0);
		var _52e = $(target).grid("getColumnFields", false).length;
		_52f(tr1, _52d);
		_52f(tr2, _52e);
		function _52f(tr, _530) {
			$("<tr class=\"treegrid-tr-tree\">"
					+ "<td style=\"border:0px\" colspan=\"" + _530 + "\">"
					+ "<div></div>" + "</td>" + "</tr>").insertAfter(tr);
		};
	};
	/**
	 * @desc: 解析ajax获取的数据，并实现数据的初始化
	 */
	function loadData(target, nodeID, data, _534) {
		var opts = $.data(target, "treegrid-data").options;
		var wrap = $.data(target, "grid-data").panel.children("div.grid-wrap");
		var view = wrap.children("div.grid-view");
		var view1 = view.children("div.grid-view1");
		var view2 = view.children("div.grid-view2");
		var frozenColumn = $(target).grid("getColumnFields", true);
		var otherColumns = $(target).grid("getColumnFields", false);

		setParentId(data, nodeID);
		var node = find(target, nodeID);
		if (node) {
			if (node.children) {
				node.children = node.children.concat(data);
			} else {
				node.children = data;
			}
			var hitArr = getHitArr(target, nodeID);

			var cc1 = hitArr[0];
			var cc2 = hitArr[1];
			var hitNum = hitArr[2];
		} else {
			$.data(target, "treegrid-data").data = $.data(target,
					"treegrid-data").data.concat(data);
			var cc1 = view1.children("div.grid-body")
					.children("div.grid-body-inner");
			var cc2 = view2.children("div.grid-body");
			var hitNum = 0;
		}

		if (!_534) {
			$.data(target, "treegrid-data").data = data;
			cc1.empty();
			cc2.empty();
		}
		var wData = wrapData(data, hitNum);
		// Todo:
		cc1.html(cc1.html() + wData[0].join(""));
		cc2.html(cc2.html() + wData[1].join(""));

		// $('table tr', target).height(26);

		opts.onLoadSuccess.call(target, node, data);
		setSize(target);
		setRowNumber(target);
		setCellSize();
		bindEvents(target);
		function setParentId(gData, nId) {
			for (var i = 0; i < gData.length; i++) {
				var row = gData[i];
				row._parentId = nId;
				if (row.children && row.children.length) {
					setParentId(row.children, row[opts.idField]);
				}
			}
		};
		function wrapData(data, hideNum) {
			var treeTable = ["<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
			var gridTable = ["<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
			var tableBody = [treeTable, gridTable];
			for (var i = 0; i < data.length; i++) {
				var row = data[i];
				if (row.state != "open" && row.state != "closed") {
					row.state = "open";
				}
				tableBody[0] = tableBody[0].concat(initTableHtml(row,
						frozenColumn, hideNum, opts.rownumbers));
				tableBody[1] = tableBody[1].concat(initTableHtml(row,
						otherColumns, hideNum));
				if (row.children && row.children.length) {
					var tt = wrapData(row.children, hideNum + 1);
					var v = row.state == "closed" ? "none" : "block";
					tableBody[0]
							.push("<tr class=\"treegrid-tr-tree\"><td style=\"border:0px\" colspan="
									+ (frozenColumn.length + (opts.rownumbers
											? 1
											: 0))
									+ "><div style=\"display:"
									+ v + "\">");
					tableBody[0] = tableBody[0].concat(tt[0]);
					tableBody[0].push("</div></td></tr>");
					tableBody[1]
							.push("<tr class=\"treegrid-tr-tree\"><td col='"
									+ i + "' style=\"border:0px\" colspan="
									+ otherColumns.length
									+ "><div style=\"display:" + v + "\">");
					tableBody[1] = tableBody[1].concat(tt[1]);
					tableBody[1].push("</div></td></tr>");
				}
			}
			tableBody[0].push("</tbody></table>");
			tableBody[1].push("</tbody></table>");
			return tableBody;
		};
		function initTableHtml(row, columns, hideNum, isShowNum) {
			var tempArr = ["<tr node-id=" + row[opts.idField] + ">"];
			if (isShowNum) {
				tempArr
						.push("<td class=\"grid-td-rownumber\"><div class=\"grid-cell-rownumber\">0</div></td>");
			}
			for (var i = 0; i < columns.length; i++) {
				var field = columns[i];
				var col = $(target).grid("getColumnOption", field);
				if (col) {
					var styleStr = "width:" + (col.width) + "px;";
					styleStr += "text-align:" + (col.align || "left") + ";";
					styleStr += opts.nowrap == false
							? "white-space:normal;"
							: "";
					tempArr.push("<td field=\"" + field + "\">");
					tempArr.push("<div style=\"" + styleStr + "\" ");
					if (col.checkbox) {
						tempArr.push("class=\"grid-cell-check ");
					} else {
						tempArr.push("class=\"grid-cell ");
					}
					tempArr.push("\">");
					if (col.checkbox) {
						if (row.checked) {
							tempArr
									.push("<input type=\"checkbox\" checked=\"checked\"/>");
						} else {
							tempArr.push("<input type=\"checkbox\"/>");
						}
					}
					var val = null;
					if (col.formatter) {
						val = col.formatter(row[field], row);
					} else {
						val = row[field] || "&nbsp;";
					}
					if (field == opts.treeField) {
						for (var j = 0; j < hideNum; j++) {
							tempArr.push("<span class=\"tree-indent\"></span>");
						}
						if (row.state == "closed") {
							tempArr
									.push("<span class=\"tree-hit tree-collapsed\"></span>");
							tempArr.push("<span class=\"tree-icon tree-folder "
									+ (row.iconCls ? row.iconCls : "")
									+ "\"></span>");
						} else {
							if (row.children && row.children.length) {
								tempArr
										.push("<span class=\"tree-hit tree-expanded\"></span>");
								tempArr
										.push("<span class=\"tree-icon tree-folder tree-folder-open "
												+ (row.iconCls
														? row.iconCls
														: "") + "\"></span>");
							} else {
								tempArr
										.push("<span class=\"tree-indent\"></span>");
								tempArr
										.push("<span class=\"tree-icon tree-file "
												+ (row.iconCls
														? row.iconCls
														: "") + "\"></span>");
							}
						}
						tempArr.push("<span class=\"tree-title\">" + val
								+ "</span>");
					} else {
						tempArr.push(val);
					}
					tempArr.push("</div>");
					tempArr.push("</td>");
				}
			}
			tempArr.push("</tr>");
			return tempArr;
		};
		function setCellSize() {
			var header = view.find("div.grid-header");
			var body = view.find("div.grid-body");
			var headerCheck = header.find("div.grid-header-check");
			if (headerCheck.length) {
				var ck = body.find("div.grid-cell-check");
				if ($.boxModel) {
					ck.width(headerCheck.width());
					ck.height(headerCheck.height());
				} else {
					ck.width(headerCheck.outerWidth());
					ck.height(headerCheck.outerHeight());
				}
			}
		};
	};
	/**
	 * @desc: 数据解析并初始化DOM结构
	 * @param: {Object} target this.el {String} nodeID 单个树节点的ID { } param {}
	 *         {Function} fn 单个回调函数
	 */
	function load(target, nodeID, param, _553, fn) {
		var opts = $.data(target, "treegrid-data").options;
		var body = $(target).grid("getPanel").find("div.grid-body");
		if (param) {
			opts.queryParams = param;
		}
		var dataArr = $.extend({}, opts.queryParams);
		var row = find(target, nodeID);
		if (opts.onBeforeLoad.call(target, row, dataArr) == false) {
			return;
		}
		if (!opts.url) {
			return;
		}
		var spanLoader = body.find("tr[node-id=" + nodeID
				+ "] span.tree-folder");
		spanLoader.addClass("tree-loading");
		$.ajax({
					type : opts.method,
					url : opts.url,
					data : dataArr,
					dataType : "json",
					success : function(data) {
						spanLoader.removeClass("tree-loading");
						loadData(target, nodeID, data, _553);
						if (fn) {
							fn();
						}
					},
					error : function() {
						spanLoader.removeClass("tree-loading");
						opts.onLoadError.apply(target, arguments);
						if (fn) {
							fn();
						}
					}
				});
	};
	function getRoot(target) {
		var rows = getRoots(target);
		if (rows.length) {
			return rows[0];
		} else {
			return null;
		}
	};
	function getRoots(target) {
		return $.data(target, "treegrid-data").data;
	};
	function getParent(target, nodeId) {
		var row = find(target, nodeId);
		if (row._parentId) {
			return find(target, row._parentId);
		} else {
			return null;
		}
	};
	function getChildren(target, nodeId) {
		var opts = $.data(target, "treegrid-data").options;
		var body = $(target).grid("getPanel")
				.find("div.grid-view2 div.grid-body");
		var _561 = [];
		if (nodeId) {
			_562(nodeId);
		} else {
			var _563 = getRoots(target);
			for (var i = 0; i < _563.length; i++) {
				_561.push(_563[i]);
				_562(_563[i][opts.idField]);
			}
		}
		function _562(nodeId) {
			var _565 = find(target, nodeId);
			if (_565 && _565.children) {
				for (var i = 0, len = _565.children.length; i < len; i++) {
					var _566 = _565.children[i];
					_561.push(_566);
					_562(_566[opts.idField]);
				}
			}
		};
		return _561;
	};
	function getSelected(_568) {
		var rows = getSelections(_568);
		if (rows.length) {
			return rows[0];
		} else {
			return null;
		}
	};
	function getSelections(_56a) {
		var rows = [];
		var _56b = $(_56a).grid("getPanel").children("div.grid-wrap");
		_56b.find("div.grid-view2 div.grid-body tr.grid-row-selected").each(
				function() {
					var id = $(this).attr("node-id");
					rows.push(find(_56a, id));
				});
		return rows;
	};
	function find(target, nodeId) {
		var opts = $.data(target, "treegrid-data").options;
		var data = $.data(target, "treegrid-data").data;
		var cc = [data];
		while (cc.length) {
			var c = cc.shift();
			for (var i = 0; i < c.length; i++) {
				var node = c[i];
				if (node[opts.idField] == nodeId) {
					return node;
				} else {
					if (node["children"]) {
						cc.push(node["children"]);
					}
				}
			}
		}
		return null;
	};
	function select(target, nodeId) {
		var body = $(target).grid("getPanel").children("div.grid-wrap")
				.find("div.grid-body");
		var tr = body.find("tr[node-id=" + nodeId + "]");
		tr.addClass("grid-row-selected");
		tr.find("div.grid-cell-check input[type=checkbox]").attr("checked",
				true);
	};
	function unselect(target, nodeId) {
		var body = $(target).grid("getPanel").children("div.grid-wrap")
				.find("div.grid-body");
		var tr = body.find("tr[node-id=" + nodeId + "]");
		tr.removeClass("grid-row-selected");
		tr.find("div.grid-cell-check input[type=checkbox]").attr("checked",
				false);
	};
	function selectAll(target) {
		var tr = $(target).grid("getPanel").children("div.grid-wrap")
				.find("div.grid-body tr[node-id]");
		tr.addClass("grid-row-selected");
		tr.find("div.grid-cell-check input[type=checkbox]").attr("checked",
				true);
	};
	function unselectAll(target) {
		var tr = $(target).grid("getPanel").children("div.grid-wrap")
				.find("div.grid-body tr[node-id]");
		tr.removeClass("grid-row-selected");
		tr.find("div.grid-cell-check input[type=checkbox]").attr("checked",
				false);
	};
	function collapse(target, nodeId) {
		var opts = $.data(target, "treegrid-data").options;
		var body = $(target).grid("getPanel").children("div.grid-wrap")
				.find("div.grid-body");
		var row = find(target, nodeId);
		var tr = body.find("tr[node-id=" + nodeId + "]");
		var hit = tr.find("span.tree-hit");
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("tree-collapsed")) {
			return;
		}
		if (opts.onBeforeCollapse.call(target, row) == false) {
			return;
		}
		hit.removeClass("tree-expanded tree-expanded-hover")
				.addClass("tree-collapsed");
		hit.next().removeClass("tree-folder-open");
		row.state = "closed";
		tr = tr.next("tr.treegrid-tr-tree");
		var cc = tr.children("td").children("div");
		if (opts.animate) {
			cc.slideUp("normal", function() {
						opts.onCollapse.call(target, row);
					});
		} else {
			cc.hide();
			opts.onCollapse.call(target, row);
		}
	};
	/**
	 * @desc: 展开树形菜单
	 */
	function expand(target, nodeId) {
		var opts = $.data(target, "treegrid-data").options;
		var body = $(target).grid("getPanel").children("div.grid-wrap")
				.find("div.grid-body");
		var tr = body.find("tr[node-id=" + nodeId + "]");
		var hit = tr.find("span.tree-hit");
		var row = find(target, nodeId);
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("tree-expanded")) {
			return;
		}
		if (opts.onBeforeExpand.call(target, row) == false) {
			return;
		}
		hit.removeClass("tree-collapsed tree-collapsed-hover")
				.addClass("tree-expanded");
		hit.next().addClass("tree-folder-open");
		var tree = tr.next("tr.treegrid-tr-tree");
		if (tree.length) {
			var cc = tree.children("td").children("div");
			expandChildren(cc);
		} else {
			initTrHtml(target, row[opts.idField]);
			// var tree = tr.next("tr.treegrid-tr-tree");
			var cc = tree.children("td").children("div");
			cc.hide();
			load(target, row[opts.idField], {
						id : row[opts.idField]
					}, true, function() {
						expandChildren(cc);
					});
		}
		function expandChildren(tgrid) {
			row.state = "open";
			if (opts.animate) {
				tgrid.slideDown("normal", function() {
							setSize(target, nodeId);
							opts.onExpand.call(target, row);
						});
			} else {
				tgrid.show();
				setSize(target, nodeId);
				opts.onExpand.call(target, row);
			}
		};
	};
	function toggle(target, nodeId) {
		var body = $(target).grid("getPanel").children("div.grid-wrap")
				.find("div.grid-body");
		var tr = body.find("tr[node-id=" + nodeId + "]");
		var hit = tr.find("span.tree-hit");
		if (hit.hasClass("tree-expanded")) {
			collapse(target, nodeId);
		} else {
			expand(target, nodeId);
		}
	};
	function collapseAll(target, _583) {
		var opts = $.data(target, "treegrid-data").options;
		var _584 = getChildren(target, _583);
		if (_583) {
			_584.unshift(find(target, _583));
		}
		for (var i = 0; i < _584.length; i++) {
			collapse(target, _584[i][opts.idField]);
		}
	};
	function expandAll(_586, _587) {
		var opts = $.data(_586, "treegrid-data").options;
		var _588 = getChildren(_586, _587);
		if (_587) {
			_588.unshift(find(_586, _587));
		}
		for (var i = 0; i < _588.length; i++) {
			expand(_586, _588[i][opts.idField]);
		}
	};
	function expandTo(target, nodeID) {
		var opts = $.data(target, "treegrid-data").options;
		var ids = [];
		var p = getParent(target, nodeID);
		while (p) {
			var id = p[opts.idField];
			ids.unshift(id);
			p = getParent(target, id);
		}
		for (var i = 0; i < ids.length; i++) {
			expand(target, ids[i]);
		}
	};
	function append(target, _58e) {
		var opts = $.data(target, "treegrid-data").options;
		if (_58e.parent) {
			var body = $(target).grid("getPanel").children("div.grid-wrap")
					.find("div.grid-body");
			var tr = body.find("tr[node-id=" + _58e.parent + "]");
			if (tr.next("tr.treegrid-tr-tree").length == 0) {
				initTrHtml(target, _58e.parent);
			}
			var cell = tr.children("td[field=" + opts.treeField + "]")
					.children("div.grid-cell");
			var _58f = cell.children("span.tree-icon");
			if (_58f.hasClass("tree-file")) {
				_58f.removeClass("tree-file").addClass("tree-folder");
				var hit = $("<span class=\"tree-hit tree-expanded\"></span>")
						.insertBefore(_58f);
				if (hit.prev().length) {
					hit.prev().remove();
				}
			}
		}
		loadData(target, _58e.parent, _58e.data, true);
	};
	function remove(target, nodeId) {
		var opts = $.data(target, "treegrid-data").options;
		var body = $(target).grid("getPanel").children("div.grid-wrap")
				.find("div.grid-body");
		var tr = body.find("tr[node-id=" + nodeId + "]");
		tr.next("tr.treegrid-tr-tree").remove();
		tr.remove();
		var rdata = del(nodeId);
		if (rdata) {
			if (rdata.children.length == 0) {
				tr = body.find("tr[node-id=" + rdata[opts.treeField] + "]");
				var cell = tr.children("td[field=" + opts.treeField + "]")
						.children("div.grid-cell");
				cell.find(".tree-icon").removeClass("tree-folder")
						.addClass("tree-file");
				cell.find(".tree-hit").remove();
				$("<span class=\"tree-indent\"></span>").prependTo(cell);
			}
		}
		setRowNumber(target);
		function del(id) {
			var cc;
			var rowData = getParent(target, nodeId);
			if (rowData) {
				cc = rowData.children;
			} else {
				cc = $(target).treegrid("getData");
			}
			for (var i = 0; i < cc.length; i++) {
				if (cc[i][opts.treeField] == id) {
					cc.splice(i, 1);
					break;
				}
			}
			return rowData;
		};
	};
	function refresh(target, nodeId) {
		var row = find(target, nodeId);
		var opts = $.data(target, "treegrid-data").options;
		var body = $(target).grid("getPanel").children("div.grid-wrap")
				.find("div.grid-body");
		var tr = body.find("tr[node-id=" + nodeId + "]");
		tr.children("td").each(function() {
					var cell = $(this).find("div.grid-cell");
					var field = $(this).attr("field");
					var col = $(target).grid("getColumnOption", field);
					if (col) {
						var val = null;
						if (col.formatter) {
							val = col.formatter(row[field], row);
						} else {
							val = row[field] || "&nbsp;";
						}
						if (field == opts.treeField) {
							cell.children("span.tree-title").html(val);
							var cls = "tree-icon";
							var icon = cell.children("span.tree-icon");
							if (icon.hasClass("tree-folder")) {
								cls += " tree-folder";
							}
							if (icon.hasClass("tree-folder-open")) {
								cls += " tree-folder-open";
							}
							if (icon.hasClass("tree-file")) {
								cls += " tree-file";
							}
							if (row.iconCls) {
								cls += " " + row.iconCls;
							}
							icon.attr("class", cls);
						} else {
							cell.html(val);
						}
					}
				});
		setSize(target, nodeId);
	};
})(jQuery);/**
 * combo - wooUI 基于jQuery1.4.2+
 * 
 * 依赖以下组件： panle,validateBox 数据: { id:'id', text:'text', checked:false,
 * attributes:{}, target:DOM }
 */
(function($) {
	$.woo.component.subclass('woo.combo', {
		options : {
			/**
			 * @desc 组合框的默认宽度
			 * @type {String}
			 */
			width : 'auto',

			/**
			 * @desc 组合框的内容面板的默认宽度
			 * @type {String}
			 */
			panelWidth : null,

			/**
			 * @desc 组合框的内容面板的默认高度
			 * @type {String}
			 */
			panelHeight : 200,

			/**
			 * @desc 是否允许多重字段
			 * @type {Boolean}
			 */
			multiple : false,

			/**
			 * @desc 字段分隔符
			 * @type {String}
			 */
			separator : ',',

			/**
			 * @desc 是否可以编辑
			 * @type {Boolean}
			 */
			editable : true,

			/**
			 * @desc 是否不可编辑
			 * @type {Boolean}
			 */
			disabled : false,

			/**
			 * @desc 是否为必填字段
			 * @type {Boolean}
			 */
			required : false,

			/**
			 * @desc 必填提示信息
			 * @type {String}
			 */
			missingMessage : '此项为必填项',

			/**
			 * @event
			 * @desc 选择前一项触发事件
			 * @return
			 */
			selectPrev : $.noop,

			/**
			 * @event
			 * @desc 选择下一项触发事件
			 * @return
			 */
			selectNext : $.noop,

			/**
			 * @event
			 * @desc 选择当前项触发事件
			 * @return
			 */
			selectCurr : $.noop,

			/**
			 * @event
			 * @desc 过滤器触发事件
			 * @return
			 */
			filter : $.noop,

			/**
			 * @event
			 * @desc 选项改变后触发事件
			 * @return
			 */
			onChange : $.noop
		},

		_create : function() {
			var self = this;
			init(self);
			self.el.removeAttr('disabled');
			$('input.combo-text', self.el).attr('readonly',
					!self.options.editable);
			setDisabled(self, self.options.disabled);
			setSize(self);
			bindEvents(self);
			validate(self);
		},

		/**
		 * @desc 解析配置项
		 * @param
		 * @return 返回配置项
		 */
		parseOptions : function() {
			return parseOptions(this);
		},

		/**
		 * @desc 取得组合框内容面板
		 * @param
		 * @return 返回内容面板DOM结构
		 */
		getPanel : function() {
			return this.panel.children();
		},

		/**
		 * @desc 取得组合框的输入框
		 * @return 返回输入框对象
		 */
		textbox : function() {
			return this.combo.find('input.combo-text');
		},

		/**
		 * @desc 销毁组件
		 * @return
		 */
		destroy : function() {
			destroy(this);
		},

		/**
		 * @desc 设置combo组件的尺寸大小
		 * @param {Object}
		 *            comp 组合框对象自身
		 * @param {Number}
		 *            width 传递的组合框的宽度
		 * @return
		 */
		resize : function(comp, width) {
			setSize(comp, width);
		},

		/**
		 * @desc 显示内容面板
		 * @return
		 */
		showPanel : function() {
			showPanel(this);
		},

		/**
		 * @desc 隐藏内容面板
		 * @return
		 */
		hidePanel : function() {
			return hidePanel(this);
		},

		/**
		 * @desc 设置组合框不可用
		 * @return
		 */
		disable : function() {
			setDisabled(this, true);
			bindEvents(this);
		},

		/**
		 * @desc 设置组合框可用
		 * @return
		 */
		enable : function() {
			setDisabled(this, false);
			bindEvents(this);
		},

		/**
		 * @desc 清除值域和文本字段值
		 * @param {Object}
		 *            comp 组合框对象自身
		 * @return
		 */
		clear : function(comp) {
			clear(comp);
		},

		/**
		 * @desc 获取组合框文本字段
		 * @param {Object}
		 *            comp 组合框对象自身
		 * @return
		 */
		getText : function(comp) {
			return getText(comp);
		},

		/**
		 * @desc 设置组合框文本字段
		 * @param {String}
		 *            text 传递的文本字段
		 * @return
		 */
		setText : function(text) {
			setText(this, text);
		},

		/**
		 * @desc 获取所有的值
		 * @param {Object}
		 *            comp 组合框对象自身
		 * @return
		 */
		getValues : function() {
			return getValues();
		},

		/**
		 * @desc 赋予组合框新的值（数组）
		 * @param {Object}
		 *            values 值域数组
		 * @return
		 */
		setValues : function(values) {
			setValues(this, values);
		},

		/**
		 * @desc 获取组合框的值
		 * @param {Object}
		 *            comp 组合框对象自身
		 * @return
		 */
		getValue : function(comp) {
			return getValue(comp);
		},

		/**
		 * @desc 赋予组合框新的值
		 * @param {String}
		 *            value 要赋予的单个值
		 * @return
		 */
		setValue : function(value) {
			setValue(this, value);
		}
	});

	/**
	 * @desc 初始化combo组合框的尺寸高宽
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @param {Number}
	 *            width combobox组件的宽度设置
	 * @return
	 */
	function setSize(comp, width) {
		var opts = comp.options, combo = comp.combo, panel = comp.panel;

		if (width)
			opts.width = width;

		if (isNaN(opts.width)) {
			opts.width = combo.find('input.combo-text').outerWidth();
		}
		var arrowWidth = combo.find('.combo-arrow').outerWidth();
		var width = opts.width - arrowWidth;
		combo.find('input.combo-text').width(width);

		panel.panel('resize', {
			width : (opts.panelWidth ? opts.panelWidth : combo.outerWidth()),
			height : opts.panelHeight
		});
	}

	/**
	 * @desc 生成combo组合框的DOM结构
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @return
	 */
	function init(comp) {
		$(comp.el).hide();
		var combo = $('<span class="combo"></span>').insertAfter(comp.el);
		var input = $('<input type="text" class="combo-text">').appendTo(combo);
		$('<span><span class="combo-arrow"></span></span>').appendTo(combo);
		$('<input type="hidden" class="combo-value">').appendTo(combo);
		var panel = $('<div class="combo-panel"></div>').appendTo('body');
		panel.panel({
			doSize : false,
			closed : true,
			style : {
				position : 'absolute'
			},
			onOpen : function() {
				$(this).panel('resize');
			}
		});

		var target = comp.el;

		var name = $(target).attr('name');
		if (name) {
			combo.find('input.combo-value').attr('name', name);
			$(target).removeAttr('name').attr('comboName', name);
		}
		input.attr('autocomplete', 'off');
		comp.combo = combo;
		comp.panel = panel;
	}

	/**
	 * @desc 销毁combo组合框
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @return
	 */
	function destroy(comp) {
		comp.panel.panel('destroy');
		comp.combo.remove();
		$(comp).remove();
	}

	/**
	 * @desc 绑定combo组合框的所有事件
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @return
	 */
	function bindEvents(comp) {
		var opts = comp.options, combo = comp.combo, panel = comp.panel, input = combo
				.find('.combo-text'), arrow = combo.find('.combo-arrow'), target = comp.el;

		$(document).unbind('.combo');
		combo.unbind('.combo');
		panel.unbind('.combo');
		input.unbind('.combo');
		arrow.unbind('.combo');

		if (!opts.disabled) {
			$(document).bind('mousedown.combo', function(e) {
				$('div.combo-panel').panel('close');
			});
			panel.bind('mousedown.combo', function(e) {
				return false;
			});

			input.bind('focus.combo', function() {
				showPanel(comp);
			}).bind('mousedown.combo', function(e) {
				e.stopPropagation();
			}).bind('keyup.combo', function(e) {
				switch (e.keyCode) {
					case 37 : // left
					case 38 : // up
						opts.selectPrev.call(target);
						break;
					case 39 : // right
					case 40 : // down
						opts.selectNext.call(target);
						break;
					case 13 : // enter
						opts.selectCurr.call(target);
						break;
					case 27 : // esc
						hidePanel(target);
						break;
					default :
						if (opts.editable) {
							opts.filter.call(target, $(this).val());
						}
				}
				return false;
			});

			arrow.bind('click.combo', function() {
				input.focus();
			}).bind('mouseenter.combo', function() {
				$(this).addClass('combo-arrow-hover');
			}).bind('mouseleave.combo', function() {
				$(this).removeClass('combo-arrow-hover');
			});
		}
	}

	/**
	 * @desc 显示combo组合框的内容面板
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @return
	 */
	function showPanel(comp) {
		var combo = comp.combo;
		var panel = comp.panel;

		panel.panel('open');

		(function() {
			if (panel.is(':visible')) {
				var top = combo.offset().top + combo.outerHeight();
				if (top + panel.outerHeight() > $(window).height()
						+ $(document).scrollTop()) {
					top = combo.offset().top - panel.outerHeight();
				}
				if (top < $(document).scrollTop()) {
					top = combo.offset().top + combo.outerHeight();
				}
				panel.panel('move', {
					left : combo.offset().left,
					top : top
				});
				setTimeout(arguments.callee, 200);
			}
		})();
	}

	/**
	 * @desc 隐藏combo组合框的内容面板
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @return
	 */
	function hidePanel(comp) {
		var panel = comp.panel;
		panel.panel('close');
	}

	/**
	 * @desc 验证combo组合框的文本内容
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @param {Boolean}
	 *            doit 是否需要验证
	 * @return
	 */
	function validate(comp, doit) {
		if ($.fn.validatebox) {
			var opts = comp.options;
			var input = comp.combo.find('input.combo-text');
			input.validatebox(opts);
			if (doit) {
				input.validatebox('validate');
				input.trigger('mouseleave');
			}
		}
	}

	/**
	 * @desc 设置combo组合框的可用与不可用
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @param {Boolean}
	 *            disabled 是否不可用
	 * @return
	 */
	function setDisabled(comp, disabled) {

		var opts = comp.options, combo = comp.combo, target = comp.el;

		if (disabled) {
			opts.disabled = true;
			$(target).attr('disabled', true);
			combo.find('.combo-value').attr('disabled', true);
			combo.find('.combo-text').attr('disabled', true);
		} else {
			opts.disabled = false;
			$(target).removeAttr('disabled');
			combo.find('.combo-value').removeAttr('disabled');
			combo.find('.combo-text').removeAttr('disabled');
		}
	}

	/**
	 * @desc 清除值域和文本字段值
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @return
	 */
	function clear(comp) {
		var combo = $.data(comp, 'combo').combo;
		combo.find('input.combo-value:gt(0)').remove();
		combo.find('input.combo-value').val('');
		combo.find('input.combo-text').val('');
	}

	/**
	 * @desc 获取combo组合框的文本字段
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @return
	 */
	function getText(comp) {
		//var combo = comp.combo;
		return $(comp.el).next().find('input.combo-text').val();
	}

	/**
	 * @desc 设置combo组合框的文本字段
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @param {String}
	 *            text 要赋予的文本值
	 * @return
	 */
	function setText(comp, text) {
		//var combo = comp.combo;
		$(comp.el).next().find('input.combo-text').val(text);
		validate(comp, true);
	}

	/**
	 * @desc 获取combo组合框的所有值
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @return
	 */
	function getValues() {
		var values = [];
		$(this.el).next().find('input.combo-value').each(function() {
			values.push($(this).val());
		});
		return values;
	}

	/**
	 * @desc 赋予combo组合框的新的值域
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @param {Object}
	 *            values 组合框的值域数组
	 * @return
	 */
	function setValues(comp, values) {
		var opts = comp.options;
		var oldValues = getValues(comp);

		var combo = comp.combo;
		combo.find('input.combo-value').remove();
		var name = $(comp.el).attr('comboName');
		for (var i = 0; i < values.length; i++) {
			var input = $('<input type="hidden" class="combo-value">')
					.appendTo(combo);
			if (name)
				input.attr('name', name);
			input.val(values[i]);
		}

		var tmp = [];
		for (var i = 0; i < oldValues.length; i++) {
			tmp[i] = oldValues[i];
		}
		var aa = [];
		for (var i = 0; i < values.length; i++) {
			for (var j = 0; j < tmp.length; j++) {
				if (values[i] == tmp[j]) {
					aa.push(values[i]);
					tmp.splice(j, 1);
					break;
				}
			}
		}

		if (aa.length != values.length || values.length != oldValues.length) {
			if (opts.multiple) {
				opts.onChange.call(comp, values, oldValues);
			} else {
				opts.onChange.call(comp, values[0], oldValues[0]);
			}
		}
	}

	/**
	 * @desc 获取combo组合框的值
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @return
	 */
	function getValue(comp) {
		var values = getValues(comp);
		return values[0];
	}

	/**
	 * @desc 设置combo组合框的值
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @param {String}
	 *            value 传递的给组合框的值
	 * @return
	 */
	function setValue(comp, value) {
		setValues(comp, [value]);
	}

	/**
	 * @desc 解析combo组合框的配置项
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @return
	 */
	function parseOptions(comp) {
		var t = $(comp);
		return {
			width : (parseInt(comp.style.width) || undefined),
			panelWidth : (parseInt(t.attr('panelWidth')) || undefined),
			panelHeight : (t.attr('panelHeight') == 'auto'
					? 'auto'
					: parseInt(t.attr('panelHeight')) || undefined),
			separator : (t.attr('separator') || undefined),
			multiple : (t.attr('multiple') ? (t.attr('multiple') == 'true' || t
					.attr('multiple') == true) : undefined),
			editable : (t.attr('editable')
					? t.attr('editable') == 'true'
					: undefined),
			disabled : (t.attr('disabled') ? true : undefined),
			required : (t.attr('required') ? (t.attr('required') == 'true' || t
					.attr('required') == true) : undefined),
			missingMessage : (t.attr('missingMessage') || undefined)
		};
	}
})(jQuery);/**
 * combobox - wooUI 基于jQuery1.4.2+
 * 
 * 依赖以下组件： panel,combo
 * 
 * Dependencies: combobox
 * 
 */
(function($) {
	$.woo.component.subclass('woo.combobox', {
				options : {
					/**
					 * @desc combobox的对应值配置项
					 * @type {String}
					 */
					valueField : 'value',

					/**
					 * @desc combobox的对应文本值配置项
					 * @type {String}
					 */
					textField : 'text',

					/**
					 * @desc combobox的远程数据来源
					 * @type {String}
					 */
					url : null,

					/**
					 * @desc combobox的数据
					 * @type {Object}
					 */
					data : null,

					/**
					 * @event
					 * @desc 选择前一项触发事件
					 * @return
					 */
					selectPrev : function() {
						selectPrev(this);
					},

					/**
					 * @event
					 * @desc 选择下一项触发事件
					 * @return
					 */
					selectNext : function() {
						selectNext(this);
					},

					/**
					 * @event
					 * @desc 选择当前项触发事件
					 * @return
					 */
					selectCurr : function() {
						selectCurr(this);
					},

					/**
					 * @event
					 * @desc 过滤器触发事件
					 * @return
					 */
					filter : function(query) {
						filter(this, query);
					},

					/**
					 * @event
					 * @desc 加载数据成功后所触发事件
					 * @return
					 */
					onLoadSuccess : $.noop,

					/**
					 * @event
					 * @desc 加载数据失败后所触发事件
					 * @return
					 */
					onLoadError : $.noop,

					/**
					 * @event
					 * @desc 选择选项后触发事件
					 * @return
					 */
					onSelect : function(record) {
					},

					/**
					 * @event
					 * @desc 去除选择后触发事件
					 * @return
					 */
					onUnselect : function(record) {
					}
				},
				_create : function(options) {
					// var opts = this.options;
					var state = $.data(this, 'combobox');
					state = $.data(this, 'combobox', {
								options : $.extend({},
										$.woo.combo.prototype.options,
										//wangkun：2011/1/12
										//old:	parseOptions(this), options,
										options,
										this.options)
							});
					if (state.options.url) {
						create(this);
						request(this);
					} else if (state.options.data) {
						loadData(this, state.options.data);
					} else {
						create(this);
						loadData(this, transformData(this));
					}
				},

				/**
				 * @desc 解析配置项
				 * @param
				 * @return 返回配置项
				 */
				parseOptions : function() {
					return parseOptions(this);
				},

				/**
				 * @desc 获取数据
				 * @param
				 * @return 返回数据
				 */
				getData : function() {
					return this.data;
				},

				/**
				 * @desc 获取值
				 * @param
				 * @return
				 */
				getValue : function() {
					var values = [];
					$(this.el).next().find('input.combo-value').each(
							function() {
								values.push($(this).val());
							});
					return values;
				},

				/**
				 * @desc 赋予组合框新的值（数组）
				 * @param {Object}
				 *            values 值域数组
				 * @return
				 */
				setValues : function(values) {
					setValues(this, values);
				},

				/**
				 * @desc 赋予组合框新的值
				 * @param {String}
				 *            value 要赋予的单个值
				 * @return
				 */
				setValue : function(value) {
					setValue(this, value);
				},

				/**
				 * @desc 设置组合框不可用
				 * @return
				 */
				disable : function() {
					$(this.el).combo('disable');
				},

				/**
				 * @desc 设置组合框可用
				 * @return
				 */
				enable : function() {
					$(this.el).combo('enable');
				},

				/**
				 * @desc 加载数据
				 * @return
				 */
				loadData : function() {
					loadData(this, data);
				},

				/**
				 * @desc 重新加载远程数据
				 * @param {String}
				 *            url 数据来源地址
				 * @return
				 */
				reload : function(url) {
					request(this, url);
				},

				/**
				 * @desc 选择指定的值
				 * @param {String}
				 *            value 指定的值
				 * @return
				 */
				select : function(value) {
					select(this, value);
				},

				/**
				 * @desc 去除选择指定的值
				 * @param {String}
				 *            value 指定的值
				 * @return
				 */
				unselect : function(value) {
					unselect(this, value);
				}
			});

	/**
	 * @desc 设置combo组合框的可用与不可用
	 * @param {Object}
	 *            comp 组合框对象自身
	 * @param {Boolean}
	 *            disabled 是否不可用
	 * @return
	 */
	function setDisabled(comp, disabled) {

		var opts = comp.options, combo = $(comp.el).next(), target = comp.el;

		if (disabled) {
			opts.disabled = true;
			$(target).attr('disabled', true);
			combo.find('.combo-value').attr('disabled', true);
			combo.find('.combo-text').attr('disabled', true);
		} else {
			opts.disabled = false;
			$(target).removeAttr('disabled');
			combo.find('.combo-value').removeAttr('disabled');
			combo.find('.combo-text').removeAttr('disabled');
		}
	}

	/**
	 * @desc 选中前一项
	 * @param {Object}
	 *            comp 组件对象自身
	 * @return
	 */
	function selectPrev(comp) {
		var panel = $(comp.el).combo('getPanel');
		var values = $(comp.el).combo('getValues');
		var item = panel.find('div.combobox-item[value=' + values.pop() + ']');
		if (item.length) {
			var prev = item.prev(':visible');
			if (prev.length) {
				item = prev;
			}
		} else {
			item = panel.find('div.combobox-item:visible:last');
		}
		var value = item.attr('value');
		setValues(comp, [value]);

		if (item.position().top <= 0) {
			var h = panel.scrollTop() + item.position().top;
			panel.scrollTop(h);
		} else if (item.position().top + item.outerHeight() > panel.height()) {
			var h = panel.scrollTop() + item.position().top
					+ item.outerHeight() - panel.height();
			panel.scrollTop(h);
		}
	}

	/**
	 * @desc 选中下一项
	 * @param {Object}
	 *            comp 组件对象自身
	 * @return
	 */
	function selectNext(comp) {
		var panel = $(comp.el).combo('getPanel');
		var values = $(comp.el).combo('getValues');
		var item = panel.find('div.combobox-item[value=' + values.pop() + ']');
		if (item.length) {
			var next = item.next(':visible');
			if (next.length) {
				item = next;
			}
		} else {
			item = panel.find('div.combobox-item:visible:first');
		}
		var value = item.attr('value');
		setValues(comp, [value]);

		if (item.position().top <= 0) {
			var h = panel.scrollTop() + item.position().top;
			panel.scrollTop(h);
		} else if (item.position().top + item.outerHeight() > panel.height()) {
			var h = panel.scrollTop() + item.position().top
					+ item.outerHeight() - panel.height();
			panel.scrollTop(h);
		}
	}

	/**
	 * @desc 选择当前一项
	 * @param {Object}
	 *            comp 组件对象自身
	 * @return
	 */
	function selectCurr(comp) {
		var panel = $(comp.el).combo('getPanel');
		var item = panel.find('div.combobox-item-selected');
		setValues(comp, [item.attr('value')]);
		$(comp.el).combo('hidePanel');
	}

	/**
	 * @desc 选择指定一项
	 * @param {Object}
	 *            comp 组件对象自身
	 * @param {String}
	 *            value 指定选项的值
	 * @return
	 */
	function select(comp, value) {
		// var opts = comp.options;
		// var data = comp.data;
		var opts = $.data(comp, 'combobox').options;
		var data = comp.data;
		if (opts.multiple) {
			var values = $(comp.el).combo('getValues');
			for (var i = 0; i < values.length; i++) {
				if (values[i] == value)
					return;
			}
			values.push(value);
			setValues(comp, values);
		} else {
			setValues(comp, [value]);
			$(comp.el).combo('hidePanel');
		}

		for (var i = 0; i < data.length; i++) {
			if (data[i][opts.valueField] == value) {
				opts.onSelect.call(comp, data[i]);
				return;
			}
		}
	}

	/**
	 * @desc 去除选择指定一项
	 * @param {Object}
	 *            comp 组件对象自身
	 * @param {String}
	 *            value 指定选项的值
	 * @return
	 */
	function unselect(comp, value) {
		// var opts = comp.options;
		// var data = comp.data;
		var opts = $.data(comp, 'combobox').options;
		var data = comp.data;
		var values = $(comp.el).combo('getValues');
		for (var i = 0; i < values.length; i++) {
			if (values[i] == value) {
				values.splice(i, 1);
				setValues(comp, values);
				break;
			}
		}
		for (var i = 0; i < data.length; i++) {
			if (data[i][opts.valueField] == value) {
				opts.onUnselect.call(comp, data[i]);
				return;
			}
		}
	}
	/**
	 * @desc 赋予组合框新的值（数组）
	 * @param {Object}
	 *            comp 组件对象自身
	 * @param {Object}
	 *            values 指定选项的值
	 * @return
	 */
	function setValues(comp, values, remainText) {
		// var opts = comp.options;
		// var data = comp.data;
		var opts = $.data(comp, 'combobox').options;
		var data = comp.data;
		var panel = $(comp.el).combo('getPanel');

		panel.find('div.combobox-item-selected')
				.removeClass('combobox-item-selected');
		var vv = [], ss = [];
		for (var i = 0; i < values.length; i++) {
			var v = values[i];
			var s = v;
			for (var j = 0; j < data.length; j++) {
				if (data[j][opts.valueField] == v) {
					s = data[j][opts.textField];
					break;
				}
			}
			vv.push(v);
			ss.push(s);
			panel.find('div.combobox-item[value=' + v + ']')
					.addClass('combobox-item-selected');
		}
		//wangkun		2011/1/13
		$(comp.el).combo('setValues', vv);
		if (!remainText) {
			$(comp.el).combo('setText', ss.join(opts.separator));
		}
	}

	/**
	 * @desc 赋予组合框新的值
	 * @param {Object}
	 *            comp 组件对象自身
	 * @param {Object}
	 *            value 指定选项的值
	 * @return
	 */
	function setValue(comp, value) {
		// var opts = comp.options;
		var opts = $.data(comp, 'combobox').options;
		var v = value;
		if (typeof value == 'object') {
			v = value[opts.valueField];
		}
		setValues(comp, [v]);
	}

	/**
	 * @desc 转换组件的数据
	 * @param {Object}
	 *            comp 组件对象自身
	 * @return
	 */
	function transformData(comp) {
		// var opts = comp.options;
		var opts = $.data(comp, 'combobox').options;
		var data = [];
		$('>option', comp.el).each(function() {
					var item = {};
					item[opts.valueField] = $(this).attr('value')
							|| $(this).html();
					item[opts.textField] = $(this).html();
					item['selected'] = $(this).attr('selected');
					data.push(item);
				});
		return data;
	}

	/**
	 * @desc 加载数据，旧的数据会被替换
	 * @param {Object}
	 *            comp 组件对象自身
	 * @param {Object}
	 *            data 传递的数据
	 * @return
	 */
	function loadData(comp, data) {
		// var opts = comp.options;
		var opts = $.data(comp, 'combobox').options;
		var panel = $(comp.el).combo('getPanel');

		comp.data = data;
		var selected = [];
		panel.empty(); // 清除旧数据
		for (var i = 0; i < data.length; i++) {
			// wangkun 2011/1/7
			// old:
			// var item = $('<div
			// class="combobox-item"></div>').appendTo(panel);
			// item.html(data[i][opts.textField]);

			var item = $('<div class="combobox-item">'
					+ data[i][opts.textField] + '</div>').appendTo(panel);
			item.attr('value', data[i][opts.valueField]);
			if (data[i]['selected']) {
				selected.push(data[i][opts.valueField]);
			}
		}
		if (opts.multiple) {
			setValues(comp, selected);
		} else {
			if (selected.length) {
				setValues(comp, [selected[0]]);
			} else {
				setValues(comp, []);
			}
		}

		// opts.onLoadSuccess.call(comp, data);

		$('.combobox-item', panel).hover(function() {
					$(this).addClass('combobox-item-hover');
				}, function() {
					$(this).removeClass('combobox-item-hover');
				}).click(function() {
					var item = $(this);
					if (opts.multiple) {
						if (item.hasClass('combobox-item-selected')) {
							unselect(comp, item.attr('value'));
						} else {
							select(comp, item.attr('value'));
						}
					} else {
						select(comp, item.attr('value'));
					}
				});
	}

	/**
	 * @desc 与后台请求加载数据
	 * @param {Object}
	 *            comp 组件对象自身
	 * @param {String}
	 *            url 远程数据地址
	 * @return
	 */
	function request(comp, url) {
		// var opts = comp.options;
		var opts = $.data(comp, 'combobox').options;
		if (url) {
			opts.url = url;
		}
		if (!opts.url)
			return;

		$.ajax({
					async : false,
					url : opts.url,
					dataType : 'json',
					success : function(data) {
						loadData(comp, data);
					},
					error : function() {
						opts.onLoadError.apply(this, arguments);
					}
				})
	}

	/**
	 * @desc 查询过滤方法
	 * @param {Object}
	 *            comp 组件对象自身
	 * @param {String}
	 *            query 查询关键字
	 * @return
	 */
	function filter(comp, query) {
		$(comp.el).combo('showPanel');
		// var data = comp.data;
		var data = comp.data;
		var panel = $(comp.el).combo('getPanel');
		setValues(comp, [], true);
		panel.find('div.combobox-item').each(function() {
					var item = $(this);
					if (item.text().indexOf(query) == 0) {
						item.show();
						if (item.text() == query) {
							item.addClass('combobox-item-selected');
						}
					} else {
						item.hide();
					}
				});
	}

	/**
	 * @desc 继承combo组件的options配置项
	 * @param {String}
	 *            comp 组件对象自身
	 * @return
	 */
	function create(comp) {
		//var opts = comp.options;
		var opts = $.data(comp, 'combobox').options;
		$(comp.el).combo(opts);
	}

	/**
	 * @desc 解析combo组件的options配置项
	 * @param {String}
	 *            comp 组件对象自身
	 * @return
	 */
	function parseOptions(comp) {
		var t = $(comp.el);
		return $.extend({}, t.combo('parseOptions'), {
					valueField : t.attr('valueField'),
					textField : t.attr('textField'),
					url : t.attr('url')
				});
	}
})(jQuery);/**
 * combogrid - wooUI 基于jQuery1.4.2+
 * 
 * 依赖以下组件： combo,datagrid
 * 
 * Dependencies:
 *   combogrid
 * 
 */
(function($){
	$.woo.component.subclass('woo.combogrid',{
		options : $.extend({}, $.woo.combo.prototype.options,
			$.woo.grid.prototype.options, {
				loadMsg : null,
				idField : null,
				textField : null,
				mode : "local",
				keyHandler : {
					up : function() {
						selectRow(this, -1);
					},
					down : function() {
						selectRow(this, 1);
					},
					enter : function() {
						selectRow(this, 0);
						$(this.el).combo("hidePanel");
					},
					query : function(value) {
						query(this, value);
					}
				},
				filter : function(value, row) {
					var opts = $(this).combogrid("options");
					return row[opts.textField].indexOf(value) == 0;
				}
			}),
		_create : function(options){
			//Deasel:2011/1/4
			//old:
			$.data(this, "combogrid",{
				options : null,
				grid : null,
				remainText : false				
			});
			
			var opts = this.options;
			var state = $.data(this, "combogrid");
			var t = $(this);
			state = $.data(this, "combogrid", {
					options : $.extend({}, opts, {
						width: (parseInt(this.el[0].style.width) || undefined),
						idField: (t.attr('idField') || undefined),
						textField: (t.attr('textField') || undefined),
						separator: (t.attr('separator') || undefined),
						multiple: (t.attr('multiple') ? (t.attr('multiple') == 'true' || t.attr('multiple') == true) : undefined),
						editable: (t.attr('editable') ? t.attr('editable') == 'true' : undefined),
						disabled: (t.attr('disabled') ? true : undefined),
						required: (t.attr('required') ? (t.attr('required') == 'true' || t.attr('required') == true) : undefined),
						missingMessage: (t.attr('missingMessage') || undefined)
					}, options)
				});
			create(this);
		},
		/**
		 * @desc 设置组合框不可用
		 * @return
		 */
		disable : function() {
			$(this.el).combo('disable');
		},

		/**
		 * @desc 设置组合框可用
		 * @return
		 */
		enable : function() {
			$(this.el).combo('enable');
		}
	});
	function create(comp) {
		var opts = $.data(comp, "combogrid").options;
		var grid = $.data(comp, "combogrid").grid;
		$(comp.el).addClass("combogrid-f");
		$(comp.el).combo(opts);
		var panel = $(comp.el).combo("getPanel");
		if (!grid) {
			//Deasel：2011/1/4
			//old：grid = $("<table></table>").appendTo(panel);
			grid = $("<div id='test'></div>").appendTo(panel);
			
			$.data(comp, "combogrid").grid = grid;
		}
		grid.grid($.extend({}, opts, {
			border : false,
			fit : true,
			singleSelect : (!opts.multiple),
			onLoadSuccess : function(_6) {
				var remainText = $.data(comp, "combogrid").remainText;
				var values = $(comp.el).combo("getValues");
				setValues(comp, values, remainText);
				$.data(comp, "combogrid").remainText = false;
				opts.onLoadSuccess.apply(this, arguments);
			},
			onClickRow : selectRow,
			onSelect : function(_a, _b) {
				retrieveValues();
				opts.onSelect.call(this, _a, _b);
			},
			onUnselect : function(_d, _e) {
				retrieveValues();
				opts.onUnselect.call(this, _d, _e);
			},
			onSelectAll : function(_f) {
				retrieveValues();
				opts.onSelectAll.call(this, _f);
			},
			onUnselectAll : function(_10) {
				retrieveValues();
				opts.onUnselectAll.call(this, _10);
			}
		}));
		//Deasel:2011/1/4
		//old:function selectRow(comp, row) {
		function selectRow(rowId, record) {
			$.data(this, "combogrid").remainText = false;
			retrieveValues();
			if (!opts.multiple) {
				$(comp.el).combo("hidePanel");
			}
			opts.onClickRow.call(this, comp, row);
		};
		function retrieveValues() {
			var remainText = $.data(comp, "combogrid").remainText;
			var getSelections = grid.grid("getSelections");
			var vv = [], ss = [];
			for (var i = 0; i < getSelections.length; i++) {
				vv.push(getSelections[i][opts.idField]);
				ss.push(getSelections[i][opts.textField]);
			}
			$(comp.el).combo("setValues", vv);
			if (!vv.length && !opts.multiple) {
				$(comp.el).combo("setValues", [""]);
			}
			if (!remainText) {
				$(comp.el).combo("setText", ss.join(opts.separator));
			}
		};
	};
	function selectRow(comp, step) {
		var opts = $.data(comp, "combogrid").options;
		var grid = $.data(comp, "combogrid").grid;
		var rowLength = grid.grid("getRows").length;
		$.data(comp, "combogrid").remainText = false;
		var index;
		var selections = grid.grid("getSelections");
		if (selections.length) {
			index = grid.grid("getRowIndex", selections[selections.length - 1][opts.idField]);
			index += step;
			if (index < 0) {
				index = 0;
			}
			if (index >= rowLength) {
				index = rowLength - 1;
			}
		} else {
			if (step > 0) {
				index = 0;
			} else {
				if (step < 0) {
					index = rowLength - 1;
				} else {
					index = -1;
				}
			}
		}
		if (index >= 0) {
			grid.grid("clearSelections");
			grid.grid("selectRow", index);
		}
	};
	function setValues(comp, values, remainText) {
		var opts = $.data(comp, "combogrid").options;
		var grid = $.data(comp, "combogrid").grid;
		var rows = grid.grid("getRows");
		var ss = [];
		grid.grid("clearSelections");
		for (var i = 0; i < values.length; i++) {
			var v = grid.grid("getRowIndex", values[i]);
			if (v >= 0) {
				grid.grid("selectRow", v);
				ss.push(rows[v][opts.textField]);
			} else {
				ss.push(values[i]);
			}
		}
		$(comp.el).combo("setValues", values);
		if (!remainText) {
			$(comp.el).combo("setText", ss.join(opts.separator));
		}
	};
	function query(comp, value) {
		var opts = $.data(comp, "combogrid").options;
		var grid = $.data(comp, "combogrid").grid;
		$.data(comp, "combogrid").remainText = true;
		if (opts.multiple && !value) {
			setValues(comp, [], true);
		} else {
			setValues(comp, [value], true);
		}
		if (opts.mode == "remote") {
			grid.grid("reload", {
				value : value
			});
		} else {
			if (!value) {
				return;
			}
			var rows = grid.grid("getRows");
			for (var i = 0; i < rows.length; i++) {
				if (opts.filter.call(comp, value, rows[i])) {
					grid.grid("clearSelections");
					grid.grid("selectRow", i);
					return;
				}
			}
		}
	};
	function parseOptions(comp){
    	var t = $(comp);
		return $.extend({}, $(comp.el).combo("parseOptions",comp), $(comp.el).grid("parseOptions",comp), {
			idField : (t.attr("idField") || undefined),
			textField : (t.attr("textField") || undefined),
			mode : t.attr("mode")
		});
    };
})(jQuery);/**
 * combotree - wooUI 基于jQuery1.4.2+
 * 
 * 依赖以下组件： combo,tree
 * 
 * Dependencies:
 *   combotree
 * 
 */
(function($){
	$.woo.component.subclass('woo.combotree',{
		/**
		 * @desc 继承combo、tree组件的配置项
		 */
		options : $.extend({},
	    $.woo.combo.prototype.options, $.woo.tree.prototype.options, {
	        editable: false
	    }),
		_create : function(options, value){
			var opts = this.options;
			if (typeof options == 'string'){
				switch(options){
				case 'setValue':
					setValue(this, value);
				case 'reload':
					reload(this, opts.url);
				}
			}
			var state = $.data(this, 'combotree');
			state = $.data(this, 'combotree', {
				options: $.extend({}, opts, parseOptions(this), options)
			});
            setSize(this);
		},
		
		/**
		 * @desc 设置组合框不可用
		 * @return
		 */
		disable : function() {
			$(this.el).combo('disable');
		},

		/**
		 * @desc 设置组合框可用
		 * @return
		 */
		enable : function() {
			$(this.el).combo('enable');
		}
	});
	/**
	 * @desc 设置combotree组件的尺寸大小
	 * @param {Object}
	 *            comp combotree组件自身
	 * @return
	 */
	function setSize(comp){
		var opts = $.data(comp, "combotree").options;
        var tree = $.data(comp, "combotree").tree;
        $(comp.el).addClass("combotree-f");
        $(comp.el).combo(opts);
        var panel = $(comp.el).combo("getPanel");
        if (!tree) {
            tree = $("<ul></ul>").appendTo(panel);
            $.data(comp, "combotree").tree = tree;
        }
        tree.tree($.extend({},
        opts, {
            checkbox: opts.multiple,
            onLoadSuccess: function(_6, _7) {
                var combotreeValues = $(comp).combotree("getValues");
                if (opts.multiple) {
                    var treeChecked = tree.tree("getChecked");
                    for (var i = 0; i < treeChecked.length; i++) {
                        var id = treeChecked[i].id;
                        (function() {
                            for (var i = 0; i < combotreeValues.length; i++) {
                                if (id == combotreeValues[i]) {
                                    return;
                                }
                            }
                            combotreeValues.push(id);
                        })();
                    }
                }
                $(comp).combotree("setValues", combotreeValues);
                opts.onLoadSuccess.call(this, _6, _7);
            },
            onClick: function(_a) {
                getCS(comp);
                $(comp.el).combo("hidePanel");
                opts.onClick.call(this, _a);
            },
            onCheck: function(_b, _c) {
                getCS(comp);
                opts.onCheck.call(this, _b, _c);
            }
        }));
	};
	/**
	 * @desc 生成combotree组件的DOM结构
	 * @param {Object}
	 *            comp combotree组件自身
	 * @return combotree 上面的下拉录入框,conten放置树型组件的内容面板
	 */
	function getCS(comp) {
        var opts = $.data(comp, "combotree").options;
        var tree = $.data(comp, "combotree").tree;
        var vv = [],
        ss = [];
        if (opts.multiple) {
            var treeChecked = tree.tree("getChecked");
            for (var i = 0; i < treeChecked.length; i++) {
                vv.push(treeChecked[i].id);
                ss.push(treeChecked[i].text);
            }
        } else {
            var treeSelected = tree.tree("getSelected");
            if (treeSelected) {
                vv.push(treeSelected.id);
                ss.push(treeSelected.text);
            }
        }
        $(comp.el).combo("setValues", vv).combo("setText", ss.join(opts.separator));
    };
    /**
	 * @desc 设置combo组件的值的数组、去除原始值的数组赋予新的值的数组，
	 * 		 同时加载新的input控件标签元素到组件下，
	 * 		 赋予新值同时设置comboName属性，如果开始设置了name属性,和多重选择处理
	 * @param {Object}
	 *            comp 生成组件对象自身
	 * @param {Object}
	 *            values 数组类型，要设置的值的数组
	 * @return
	 */
    function setValues(comp, values) {
        var opts = $.data(comp, "combotree").options;
        var tree = $.data(comp, "combotree").tree;
        tree.find("span.tree-checkbox").addClass("tree-checkbox0").removeClass("tree-checkbox1 tree-checkbox2");
        var vv = [],
        ss = [];
        for (var i = 0; i < values.length; i++) {
            var v = values[i];
            var s = v;
            var node = tree.tree("find", v);
            if (node) {
                s = node.text;
                tree.tree("check", node.target);
                tree.tree("select", node.target);
            }
            vv.push(v);
            ss.push(s);
        }
        $(comp.el).combo("setValues", vv).combo("setText", ss.join(opts.separator));
    };
    /**
	 * @desc 解析combo组件的options配置项
	 * @param {String}
	 *            comp 组件对象自身
	 * @return
	 */
    function parseOptions(comp){
    	return $.extend({},
        $(comp.el).combo("parseOptions",comp), $(comp.el).tree("parseOptions",comp));
    };
})(jQuery);/**
 * dialog - wooUI
 * 基于jQuery1.4.2+
 *
 * 依赖于以下组件:
 *   window
 */

(function($){
	/**
	 * wrap dialog and return content panel.
	 */
	function wrapDialog(target){
		var t = $(target);
		t.wrapInner('<div class="dialog-content"></div>');
		var contentPanel = t.find('>div.dialog-content');
		
		contentPanel.css('padding', t.css('padding'));
		t.css('padding', 0);
		
		contentPanel.panel({
			border:false
		});
		
		return contentPanel;
	}
	
	/**
	 * build the dialog
	 */
	function buildDialog(target){
		var opts = $.data(target, 'dialog').options;
		var contentPanel = $.data(target, 'dialog').contentPanel;
		
		$(target).find('div.dialog-toolbar').remove();
		$(target).find('div.dialog-button').remove();
		if (opts.toolbar){
			var toolbar = $('<div class="dialog-toolbar"></div>').prependTo(target);
			for(var i=0; i<opts.toolbar.length; i++){
				var p = opts.toolbar[i];
				if (p == '-'){
					toolbar.append('<div class="dialog-tool-separator"></div>');
				} else {
					var tool = $('<a href="javascript:void(0)"></a>').appendTo(toolbar);
					tool.css('float','left').text(p.text);
					if (p.iconCls) tool.attr('icon', p.iconCls);
					if (p.handler) tool[0].onclick = p.handler;
					tool.linkbutton({
						plain: true,
						disabled: (p.disabled || false)
					});
				}
			}
			toolbar.append('<div style="clear:both"></div>');
		}
		
		if (opts.buttons){
			var buttons = $('<div class="dialog-button"></div>').appendTo(target);
			for(var i=0; i<opts.buttons.length; i++){
				var p = opts.buttons[i];
				var button = $('<a href="javascript:void(0)"></a>').appendTo(buttons);
				if (p.handler) button[0].onclick = p.handler;
				button.linkbutton(p);
			}
		}
		
		if (opts.href){
			contentPanel.panel({
				href: opts.href,
				onLoad: opts.onLoad
			});
			
			opts.href = null;
		}
		
		$(target).window($.extend({}, opts, {
			//TODO:
			//wangkun 	2011/1/12
			//以下方法会不停的弹出
			onResize:function(width, height){
				var wbody = $(target).panel('panel').find('>div.panel-body');
				
				contentPanel.panel('resize', {
					width: wbody.width(),
					height: (height=='auto') ? 'auto' :
							wbody.height()
							- wbody.find('>div.dialog-toolbar').outerHeight()
							- wbody.find('>div.dialog-button').outerHeight()
				});
				
				if (opts.onResize) opts.onResize.call(target, width, height);
			}
		}));
	}
	
	function refresh(target){
		var contentPanel = $.data(target, 'dialog').contentPanel;
		contentPanel.panel('refresh');
	}
	
	$.fn.dialog = function(options, param){
		if (typeof options == 'string'){
			switch(options){
			case 'options':
				return $(this[0]).window('options');
			case 'dialog':
				return $(this[0]).window('window');
			case 'setTitle':
				return this.each(function(){
					$(this).window('setTitle', param);
				});
			case 'open':
				return this.each(function(){
					$(this).window('open', param);
				});
			case 'close':
				return this.each(function(){
					$(this).window('close', param);
				});
			case 'destroy':
				return this.each(function(){
					$(this).window('destroy', param);
				});
			case 'refresh':
				return this.each(function(){
					refresh(this);
				});
			case 'resize':
				return this.each(function(){
					$(this).window('resize', param);
				});
			case 'move':
				return this.each(function(){
					$(this).window('move', param);
				});
			case 'maximize':
				return this.each(function(){
					$(this).window('maximize');
				});
			case 'minimize':
				return this.each(function(){
					$(this).window('minimize');
				});
			case 'restore':
				return this.each(function(){
					$(this).window('restore');
				});
			case 'collapse':
				return this.each(function(){
					$(this).window('collapse', param);
				});
			case 'expand':
				return this.each(function(){
					$(this).window('expand', param);
				});
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'dialog');
			if (state){
				$.extend(state.options, options);
			} else {
				var t = $(this);
				var opts = $.extend({}, $.fn.dialog.defaults, {
					title:(t.attr('title') ? t.attr('title') : undefined),
					href:t.attr('href'),
					collapsible: (t.attr('collapsible') ? t.attr('collapsible') == 'true' : undefined),
					minimizable: (t.attr('minimizable') ? t.attr('minimizable') == 'true' : undefined),
					maximizable: (t.attr('maximizable') ? t.attr('maximizable') == 'true' : undefined),
					resizable: (t.attr('resizable') ? t.attr('resizable') == 'true' : undefined)
				}, options);
				$.data(this, 'dialog', {
					options: opts,
					contentPanel: wrapDialog(this)
				});
			}
			buildDialog(this);
		});
	};
	
	$.fn.dialog.defaults = {
		title: 'New Dialog',
		href: null,
		collapsible: false,
		minimizable: false,
		maximizable: false,
		resizable: false,
		
		toolbar:null,
		buttons:null
	};
})(jQuery);/**
 * window - wooUI 基于jQuery1.4.2+
 * 
 * 依赖于以下组件: resizable draggable panel
 */

(function($) {
	$.woo.component.subclass('woo.window', {
		options : {
			/**
			 * @desc：	窗体的原始宽高
			 * @type:		{Object}
			 */
			originalSize : {},
			/**
			 * @desc: 窗体的z-index
			 * @type {Number}
			 */
			zIndex : 9000,
			/**
			 * @desc: 是否支持拖拽
			 * @type {Boolean}
			 */
			draggable : true,
			/**
			 * @desc: 是否支持缩放
			 * @type {Boolean}
			 */
			resizable : true,
			/**
			 * @desc: 是否显示阴影
			 * @type {Boolean}
			 */
			shadow : true,
			/**
			 * @desc: 是否显示遮罩层
			 * @type {Boolean}
			 */
			modal : false,
			/**
			 * @desc: 窗体绝对定位时的横坐标
			 * @type {Number}
			 */
			left : 0,
			/**
			 * @desc: 窗体绝对定位时的纵坐标
			 * @type {Number}
			 */
			top : 0,
			// window's property which difference from panel
			/**
			 * @desc: 窗体的标题
			 * @type {String}
			 */
			title : 'New Window',
			/**
			 * @desc: 是否显示收缩与展开按钮
			 * @type {Boolean}
			 */
			collapsible : true,
			/**
			 * @desc: 是否显示最小化按钮
			 * @type {Boolean}
			 */
			minimizable : false,
			/**
			 * @desc: 是否显示最大化按钮
			 * @type {Boolean}
			 */
			maximizable : false,
			/**
			 * @desc: 是否显示关闭按钮
			 * @type {Boolean}
			 */
			closable : true,
			/**
			 * @desc: 是否在初始化窗体之后立即关闭窗体
			 * @type {Boolean}
			 */
			closed : false
		},

		/**
		 * @desc: window组件的初始化方法 用于初始化窗体DOM结构，并绑定事件
		 */
		_create : function() {
			this.state = {
			// options /*引用window内的panel的options*/
			// opts /*引用window内的panel的options的初始备份*/
			// window /*引用window自身dom元素*/
			// mask /*引用window的mask*/
			// shadow /*引用window的shadow*/
			// proxy/**/
			};
			this.winInit();
			this.setProperties();
			// when window resize, reset the width and height of the window's
			// mask
			var self = this, options = this.options;
			$(window).resize(function() {
				$('div.window-mask').css({
							width : self.getPageArea().width,
							height : self.getPageArea().height
						});

					// 判断给定窗体的定位，若未给定，则默认居中显示
					// self.el.panel('resize', {
					// left : options.left == 0 ? Math.round(($(window)
					// .width() - options.width)
					// / 2) : options.left,
					// top : options.top == 0 ? Math.round(($(window)
					// .height() - options.height)
					// / 2) : options.left
					// });
				});
		},
		/**
		 * @desc: 初始化window窗体的DOM结构
		 */
		winInit : function() {

			var target = this.el, options = this.options/* window自身的配置属性 */, state = this.state, opts/* 作为panel的属性 */;

			var t = $(target);
			opts = $.extend({}, this.options, {
						title : t.attr('title'),
						collapsible : (t.attr('collapsible') ? t
								.attr('collapsible') == 'true' : undefined),
						minimizable : (t.attr('minimizable') ? t
								.attr('minimizable') == 'true' : undefined),
						maximizable : (t.attr('maximizable') ? t
								.attr('maximizable') == 'true' : undefined),
						closable : (t.attr('closable')
								? t.attr('closable') == 'true'
								: undefined),
						closed : (t.attr('closed')
								? t.attr('closed') == 'true'
								: undefined),
						shadow : (t.attr('shadow')
								? t.attr('shadow') == 'true'
								: undefined),
						modal : (t.attr('modal')
								? t.attr('modal') == 'true'
								: undefined)
					}, options);
			$(target).attr('title', '');
			// 初始化窗体
			var newOptions = $.extend({}, opts, {
						border : false,
						doSize : true,
						closed : false, // close the panel
						cls : 'window',
						headerCls : 'window-header',
						bodyCls : 'window-body',
						onBeforeDestroy : function() {
							if (opts.onBeforeDestroy) {
								if (opts.onBeforeDestroy.call(target) == false)
									return false;
							}
							if (state.shadow)
								state.shadow.remove();
							if (state.mask)
								state.mask.remove();
						},
						onClose : function() {
							if (state.shadow)
								state.shadow.hide();
							if (state.mask)
								state.mask.hide();

							if (opts.onClose)
								opts.onClose.call(target);
						},
						onOpen : function() {
							if (state.mask) {
								state.mask.css({
											display : 'block',
											zIndex : opts.zIndex++
										});
							}
							if (state.shadow) {
								state.shadow.css({
											display : 'block',
											zIndex : opts.zIndex++,
											left : state.options.left,
											top : state.options.top,
											width : state.window.outerWidth(),
											height : state.window.outerHeight()
										});
							}
							$(state.window).css('z-index', opts.zIndex++);

							if (opts.onOpen)
								opts.onOpen.call(target);
						},
						onResize : function(width, height) {
							if (state.shadow) {
								state.shadow.css({
											left : state.options.left,
											top : state.options.top,
											width : state.window.outerWidth(),
											height : state.window.outerHeight()
										});
							}

							if (opts.onResize) {
								alert(opts.onResize);
								opts.onResize.call(target, width, height);
							}
						},
						onMove : function(left, top) {
							if (state.shadow) {
								state.shadow.css({
											left : state.options.left,
											top : state.options.top
										});
							}

							if (opts.onMove)
								opts.onMove.call(target, left, top);
						},
						onMinimize : function() {
							if (state.shadow)
								state.shadow.hide();
							if (state.mask)
								state.mask.hide();

							if (opts.onMinimize)
								opts.onMinimize.call(target);
						},
						onBeforeCollapse : function() {
							if (opts.onBeforeCollapse) {
								if (opts.onBeforeCollapse.call(target) == false)
									return false;
							}
							if (state.shadow)
								state.shadow.hide();
						},
						onExpand : function() {
							if (state.shadow)
								state.shadow.show();
							if (opts.onExpand)
								opts.onExpand.call(target);
						}
					});
			var win = $(target).panel(newOptions);
			// 保存窗体的基本数据
			state.options = win.data('panel').options;
			state.opts = opts;
			state.window = win.panel('panel');

			// 判断给定窗体的定位，若未给定，则默认居中显示
			win.panel('resize', {
						left : options.left == 0 ? Math.round(($(window)
								.width() - options.width)
								/ 2) : options.left,
						top : options.top == 0 ? Math
								.round(($(window).height() - options.height)
										/ 2) : options.left
					});

			var self = this;
			// 初始化遮罩层
			if (state.mask)
				state.mask.remove();
			if (opts.modal == true) {
				state.mask = $('<div class="window-mask"></div>')
						.appendTo('body');
				state.mask.css({
							zIndex : self.options.zIndex++,
							width : self.getPageArea().width,
							height : self.getPageArea().height,
							display : 'none'
						});
			}

			// 初始化窗体阴影
			if (state.shadow)
				state.shadow.remove();
			if (opts.shadow == true) {
				state.shadow = $('<div class="window-shadow"></div>')
						.insertAfter(state.window);
				state.shadow.css({
							zIndex : self.options.zIndex++,
							display : 'none'
						});
			}

			state.window.css('z-index', self.options.zIndex++);

			// if require center the window
			if (state.options.left == null) {
				var width = state.options.width;
				if (isNaN(width)) {
					width = state.window.outerWidth();
				}
				state.options.left = ($(window).width() - width) / 2
						+ $(document).scrollLeft();
			}
			if (state.options.top == null) {
				var height = state.window.height;
				if (isNaN(height)) {
					height = state.window.outerHeight();
				}
				state.options.top = ($(window).height() - height) / 2
						+ $(document).scrollTop();
			}
			win.window('move');

			if (this.options.closed == false) {
				this.open();
			} else {
				this.close();
			}
			this.state = state;
		},
		/**
		 * @desc: 为窗体绑定事件 主要实现窗体的拖拽和缩放
		 */
		setProperties : function() {
			var state = this.state,
			options = this.options,
			target = this.el,
			self = this;
			
			//wangkun		2011/1/5
			//标题栏双击最大化功能绑定
			//若窗体非最大，则双击最大化，否则居中显示
			$(">div.panel-header>div.panel-title",$(this.el)).bind("dblclick.window",function(){
				if($(target).panel('option','maximized') == false){
					$(target).panel('maximize');
				}else{
					$(target).panel('restore');
				}
			});			
			
			// 窗体的拖放
			state.window.draggable({
				handle : '>div.panel-header>div.panel-title',
				disabled : state.options.draggable == false,
				onStartDrag : function(e) {
					if (state.mask)
						state.mask.css('z-index', options.zIndex++);
					if (state.shadow)
						state.shadow.css('z-index', options.zIndex++).css({
									display : 'none'
								});
					state.window.css('z-index', options.zIndex++);

					if (!state.proxy) {
						state.proxy = $('<div class="window-proxy"></div>')
								.insertAfter(state.window);
					}
					state.proxy.css({
								display : 'none',
								zIndex : options.zIndex++,
								left : e.data.left,
								top : e.data.top,
								width : ($.boxModel == true
										? (state.window.outerWidth() - (state.proxy
												.outerWidth() - state.proxy
												.width()))
										: state.window.outerWidth()),
								height : ($.boxModel == true
										? (state.window.outerHeight() - (state.proxy
												.outerHeight() - state.proxy
												.height()))
										: state.window.outerHeight())
							});
					setTimeout(function() {
								if (state.proxy)
									state.proxy.show();
							}, 500);
				},
				onDrag : function(e) {
					state.proxy.css({
								display : 'block',
								left : e.data.left,
								top : e.data.top
							});
					return false;
				},
				onStopDrag : function(e) {
					state.options.left = e.data.left;
					state.options.top = e.data.top;
					$(this.el).window('move');
					state.proxy.remove();
					state.proxy = null;
					if (state.shadow) {
						state.shadow.css({
									display : "block"
								});
					}
				}
			});

			var self = this;
			// 窗体的缩放
			state.window.resizable({
				options : {
					edge : 10
				},
				disabled : state.options.resizable == false,
				onStartResize : function(e) {
					var d = e.data;
					if (!state.proxy) {
						state.proxy = $('<div class="window-proxy"></div>')
								.insertAfter(state.window);
					}
					state.proxy.css({
						zIndex : options.zIndex++,
						left : d.left,
						top : d.top,
						width : ($.boxModel == true ? (d.width - (state.proxy
								.outerWidth() - state.proxy.width())) : d.width),
						height : ($.boxModel == true
								? (d.height - (state.proxy.outerHeight() - state.proxy
										.height()))
								: d.height)
					});
				},
				onResize : function(e) {
					var d = e.data;
					state.proxy.css({
						left : d.left,
						top : d.top,
						width : ($.boxModel == true ? (d.width - (state.proxy
								.outerWidth() - state.proxy.width())) : d.width),
						height : ($.boxModel == true
								? (d.height - (state.proxy.outerHeight() - state.proxy
										.height()))
								: d.height)
					});
					return false;
				},
				onStopResize : function(e) {
					var d = e.data;
					state.options.left = d.left;
					state.options.top = d.top;
					state.options.width = d.width;
					state.options.height = d.height;
					self.setSize();
					state.proxy.remove();
					state.proxy = null;
				}
			});
		},
		/**
		 * @desc: 获取当前页面的宽高，主要用于设定遮罩层的宽高
		 * @param:
		 * @return: {width:0,height:0} 当前页面的宽高
		 */
		getPageArea : function() {

			if (document.compatMode == 'BackCompat') {
				return {
					width : Math.max(document.body.scrollWidth,
							document.body.clientWidth),
					height : Math.max(document.body.scrollHeight,
							document.body.clientHeight)
				}
			} else {
				return {
					width : Math.max(document.documentElement.scrollWidth,
							document.documentElement.clientWidth),
					height : Math.max(document.documentElement.scrollHeight,
							document.documentElement.clientHeight)
				}
			}

		},
		/**
		 * @desc: 调整窗体的大小（函数内部使用）
		 * @param：
		 * @return:
		 */
		setSize : function() {
			this.el.panel('resize');
		},
		/**
		 * @desc: 调整窗体的宽高及相对位置（对外接口）
		 * @param: { width:0, 窗体宽度 height:0, 窗体高度 top:0, 相对于当前页面位置的纵坐标 left:0
		 *         相对于当前页面位置的横坐标 }
		 */
		resize : function(param) {
			this.el.panel('resize', param);
		},
		open : function() {
			var self = this;
			this.el.panel("open");
			// 判断给定窗体的定位，若未给定，则默认居中显示
			this.el.panel('resize', {
						left : Math.round(($(window).width() - self.el.panel(
								"option", "width"))
								/ 2),
						top : Math.round(($(window).height() - self.el.panel(
								"option", "height"))
								/ 2)
					});
		},
		close : function() {
			this.el.panel("close");
		}

	});
})(jQuery);