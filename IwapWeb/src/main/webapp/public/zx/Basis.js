ZX.Basis = function() {
	this.events = new ZX.Array();
}
ZX.Basis.prototype = {
	addEvent : function(eventName) {
		e = new ZX.Event(eventName);
		this.events.add(eventName, e);
	},
	getEvent : function(eventName) {
		var fieldEvent;
		this.events.each(function(e) {
					if (e.getEventName() == eventName) {
						fieldEvent = e;
						return true;

					}
				});
		return fieldEvent;
	},
	addEvents : function(o) {
		var me = this;
		me.events = me.events || {};
		if (typeof o == 'string') {
			ZX.each(arguments, function(eventName) {
						e = new ZX.Event(eventName);
						me.events.add(e);
						var fn = me[eventName];
						if (fn) { //°ó¶¨Ä¬ÈÏº¯Êý
							
							me.on(eventName, fn);
						}
					});
		}
	},
	addListener : function(el, eventName, fn) {
		var me = this;
		el = ZX.getDom(el);
		if (!el || !fn) {
			return false;
		}
		this.events.each(function(e) {
					// alert(e.getEventName());
					if (e.getEventName() == eventName) {
						e.addEventFn(fn);
						if (window.addEventListener) {
							el.addEventListener(eventName, fn, false);
						} else if (window.attachEvent) {
							el.attachEvent("on" + eventName, function() {
										me[eventName](me);
									});
							
						}
						return true;
					}
				}, this);
		return false;
	},
	on : function(eventName, fn, scope) {
		if (!eventName)
			return;
		
		if (arguments.length < 3)
			scope = this;

		if (arguments.length == 1) {
			fn = new Function();
		}
		var el = scope.element;
		this.addListener(el, eventName, fn);
	},
	removeListener : function(eventName, fn, scope) {
		var ce = this.events[eventName];
		if (ce) {
			this.events.remove(ce);
		}
	},
	fireEvent : function(eventName, data) {
		/*
		 * for(var key in this.events){ if
		 * (this.events[key].getEventName()==eventName) {
		 * this.events[key].fireEvent(data); return true; } }
		 */
		// alert(eventName);
		this.events.each(function(e) {
					if (e.getEventName() == eventName) {
						
						e.fireEvent(data);
						return true;
					}
				}, this);
		return false;
	}
}
