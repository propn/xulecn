/**
 * jQuery-wooUI @VERSION
 */

(function($){
	if(!$) return;
	
	//静态构造器
	$.woo = $.woo || {version:"@VERSION"};
	
	$.extend($.woo,{
		
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
})(jQuery);