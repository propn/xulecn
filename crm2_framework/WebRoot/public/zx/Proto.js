/*
 * Prototype JavaScript framework, version 1.5.0_rc1 (c) 2005 Sam Stephenson
 * <sam@conio.net>
 * 
 * Prototype is freely distributable under the terms of an MIT-style license.
 * For details, see the Prototype web site: http://prototype.conio.net/
 * 
 * /*--------------------------------------------------------------------------
 */

var Prototype = {
	Version : '1.5.0_rc1',
	ScriptFragment : '(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)',

	emptyFunction : function() {
	},
	K : function(x) {
		return x
	}
}

var Class = {
	create : function() {
		return function() {
			this.initialize.apply(this, arguments);
		}
	}
}

var Abstract = new Object();

Object.extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
	return destination;
}

Object.extend(Object, {
			inspect : function(object) {
				try {
					if (object == undefined)
						return 'undefined';
					if (object == null)
						return 'null';
					return object.inspect ? object.inspect() : object
							.toString();
				} catch (e) {
					if (e instanceof RangeError)
						return '...';
					throw e;
				}
			},

			keys : function(object) {
				var keys = [];
				for (var property in object)
					keys.push(property);
				return keys;
			},

			values : function(object) {
				var values = [];
				for (var property in object)
					values.push(object[property]);
				return values;
			},

			clone : function(object) {
				return Object.extend({}, object);
			}
		});

var $A = Array.from = function(iterable) {
	if (!iterable)
		return [];
	if (iterable.toArray) {
		return iterable.toArray();
	} else {
		var results = [];
		for (var i = 0; i < iterable.length; i++)
			results.push(iterable[i]);
		return results;
	}
}

Function.prototype.bind = function() {
	var __method = this, args = $A(arguments), object = args.shift();
	return function() {
		return __method.apply(object, args.concat($A(arguments)));
	}
}

Function.prototype.bindAsEventListener = function(object) {
	var __method = this, args = $A(arguments), object = args.shift();
	return function(event) {
		return __method.apply(object, [(event || window.event)].concat(args)
						.concat($A(arguments)));
	}
}

Object.extend(Number.prototype, {
			toColorPart : function() {
				var digits = this.toString(16);
				if (this < 16)
					return '0' + digits;
				return digits;
			},

			succ : function() {
				return this + 1;
			},

			times : function(iterator) {
				$R(0, this, true).each(iterator);
				return this;
			}
		});

var Try = {
	these : function() {
		var returnValue;

		for (var i = 0; i < arguments.length; i++) {
			var lambda = arguments[i];
			try {
				returnValue = lambda();
				break;
			} catch (e) {
			}
		}

		return returnValue;
	}
}

function $() {
  return  document.getElementById(arguments[0]);
}


/*--------------------------------------------------------------------------*/

var PeriodicalExecuter = Class.create();
PeriodicalExecuter.prototype = {
	initialize : function(callback, frequency) {
		this.callback = callback;
		this.frequency = frequency;
		this.currentlyExecuting = false;

		this.registerCallback();
	},

	registerCallback : function() {
		this.timer = setInterval(this.onTimerEvent.bind(this), this.frequency
						* 1000);
	},

	stop : function() {
		if (!this.timer)
			return;
		clearInterval(this.timer);
		this.timer = null;
	},

	onTimerEvent : function() {
		if (!this.currentlyExecuting) {
			try {
				this.currentlyExecuting = true;
				this.callback(this);
			} finally {
				this.currentlyExecuting = false;
			}
		}
	}
}
Object.extend(String.prototype, {
			gsub : function(pattern, replacement) {
				var result = '', source = this, match;
				replacement = arguments.callee.prepareReplacement(replacement);

				while (source.length > 0) {
					if (match = source.match(pattern)) {
						result += source.slice(0, match.index);
						result += (replacement(match) || '').toString();
						source = source.slice(match.index + match[0].length);
					} else {
						result += source, source = '';
					}
				}
				return result;
			},

			sub : function(pattern, replacement, count) {
				replacement = this.gsub.prepareReplacement(replacement);
				count = count === undefined ? 1 : count;

				return this.gsub(pattern, function(match) {
							if (--count < 0)
								return match[0];
							return replacement(match);
						});
			},

			scan : function(pattern, iterator) {
				this.gsub(pattern, iterator);
				return this;
			},

			truncate : function(length, truncation) {
				length = length || 30;
				truncation = truncation === undefined ? '...' : truncation;
				return this.length > length ? this.slice(0, length
								- truncation.length)
						+ truncation : this;
			},

			strip : function() {
				return this.replace(/^\s+/, '').replace(/\s+$/, '');
			},

			stripTags : function() {
				return this.replace(/<\/?[^>]+>/gi, '');
			},

			stripScripts : function() {
				return this.replace(
						new RegExp(Prototype.ScriptFragment, 'img'), '');
			},

			extractScripts : function() {
				var matchAll = new RegExp(Prototype.ScriptFragment, 'img');
				var matchOne = new RegExp(Prototype.ScriptFragment, 'im');
				return (this.match(matchAll) || []).map(function(scriptTag) {
							return (scriptTag.match(matchOne) || ['', ''])[1];
						});
			},

			evalScripts : function() {
				return this.extractScripts().map(function(script) {
							return eval(script)
						});
			},

			escapeHTML : function() {
				var div = document.createElement('div');
				var text = document.createTextNode(this);
				div.appendChild(text);
				return div.innerHTML;
			},

			unescapeHTML : function() {
				var div = document.createElement('div');
				div.innerHTML = this.stripTags();
				return div.childNodes[0] ? div.childNodes[0].nodeValue : '';
			},

			toQueryParams : function() {
				var pairs = this.match(/^\??(.*)$/)[1].split('&');
				return pairs.inject({}, function(params, pairString) {
							var pair = pairString.split('=');
							var value = pair[1]
									? decodeURIComponent(pair[1])
									: undefined;
							params[decodeURIComponent(pair[0])] = value;
							return params;
						});
			},

			toArray : function() {
				return this.split('');
			},

			camelize : function() {
				var oStringList = this.split('-');
				if (oStringList.length == 1)
					return oStringList[0];

				var camelizedString = this.indexOf('-') == 0 ? oStringList[0]
						.charAt(0).toUpperCase()
						+ oStringList[0].substring(1) : oStringList[0];

				for (var i = 1, len = oStringList.length; i < len; i++) {
					var s = oStringList[i];
					camelizedString += s.charAt(0).toUpperCase()
							+ s.substring(1);
				}

				return camelizedString;
			},

			inspect : function(useDoubleQuotes) {
				var escapedString = this.replace(/\\/g, '\\\\');
				if (useDoubleQuotes)
					return '"' + escapedString.replace(/"/g, '\\"') + '"';
				else
					return "'" + escapedString.replace(/'/g, '\\\'') + "'";
			}
		});

String.prototype.gsub.prepareReplacement = function(replacement) {
	if (typeof replacement == 'function')
		return replacement;
	var template = new Template(replacement);
	return function(match) {
		return template.evaluate(match)
	};
}

String.prototype.parseQuery = String.prototype.toQueryParams;

var Template = Class.create();
Template.Pattern = /(^|.|\r|\n)(#\{(.*?)\})/;
Template.prototype = {
	initialize : function(template, pattern) {
		this.template = template.toString();
		this.pattern = pattern || Template.Pattern;
	},

	evaluate : function(object) {
		return this.template.gsub(this.pattern, function(match) {
					var before = match[1];
					if (before == '\\')
						return match[2];
					return before + (object[match[3]] || '').toString();
				});
	}
}

var $break = new Object();
var $continue = new Object();

var __S = [200, 300];
var __M = [300, 400];
var __L = [400, 500];
var __XL = [500, 600];
var __XXL = [600, 700];


// 窗口弹出窗口
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
// 显示悬浮弹出窗口
function openModelessDialog(url, arg, sizeConfig, dialogWidth, dialogHeight) {
	var sizeConfigResult = "";
	if (!sizeConfig || sizeConfig == "") {
		sizeConfigResult = "dialogWidth:" + dialogWidth + "px;dialogHeight:"
				+ dialogHeight + "px;";
	} else {
		var __config = eval("__" + sizeConfig);
		sizeConfigResult = "dialogWidth:" + __config[0] + "px;dialogHeight:"
				+ __config[1] + "px;";
	}
	return window.showModelessDialog(url, arg, "status:no;help:no;scroll:no;"
					+ sizeConfigResult);
}
