/**
 * JS Baen ������̬�Ķ��� ͬʱ��HTML�ؼ����а�
 */
ZX.Bean = function(config) {
	this.items = new ZX.Array();
	this.errMessageArr = [];
	ZX.apply(this, config);
	// this.init();
}
ZX.extend(ZX.Bean, ZX.Basis, {
	id : '',
	name : '',
	init : function() {

		ZX.Bean.superclass.constructor.call(this);

		this.addEvents('query', 'save');
	},
	onquery : function() {

	},
	onsave : function() {

	},
	getField : function(id) {
		return this.findField(id);
	},
	findField : function(id) {
		var field = this.items.get(id);

		if (!ZX.isObject(field)) {
			this.items.each(function(f) {
						if (f.id == id || f.name == id) {
							field = f;
							return null;
						}
					});
		}
		return field || null;
	},
	setValue : function(name, val) {
		var field = this.findField(name);
		if (field) {
			field.setValue(val);
		}

	},
	setValues : function(values) {
		if (ZX.isArray(values)) { // array of objects
			for (var i = 0, len = values.length; i < len; i++) {
				var v = values[i];
				var f = this.findField(v.id);
				if (f) {
					f.setValue(v.value);
				}
			}
		} else {
			var field, id;
			for (id in values) {
				if (!ZX.isFunction(values[id]) && (field = this.findField(id))) {
					field.setValue(values[id]);
				}
			}
		}
		return this;
	},
	getValues : function() {
		var o = {};
		var mess = "";
		this.items.each(function(f) { // name��ȡ����ֵ������name ��ȡֵ
					if (f.getName()) {
						o[f.getName()] = f.getValue();
					} else { // ����id����
						o[f.getId()] = f.getValue();
					}

				});
		return o;
	},
	validate : function() {
		this.errMessageArr = [];
		var beanObj = this;
		var result = true;
		
		this.items.each(function(f) {
					
					var oldBlurValidate  = f.blurValidate; //�����ֵ
					if (!f.validate()) {
						f.blurValidate = oldBlurValidate;
						result = false;
						return false;
					}

				});
				
		return result;

	},
	validateAll : function() { // ���е���֤��һ��ȫ����֤
		this.errMessageArr = [];
		var beanObj = this;
		this.items.each(function(f) {
			f.blurValidate = true; // ������水ťʱ��Ҫ��֤
			f.viewWithAlert = true;
			if (!f.validate()) {
				f.blurValidate = true;
				f.viewWithAlert = false;
				if (f.fieldErrorMessArr[0]) {
					beanObj.errMessageArr[beanObj.errMessageArr.length] = f.fieldErrorMessArr[0];
				}

			}
		});

		return this.errMessageArr;
	},

	getValue : function(name) {
		var field = this.findField(name);
		if (field) {
			return field.getValue();
		}

	},
	add : function() { // ���field����,fieldĬ�����Զ�����html��ǩ����һ��
		var fields = Array.prototype.slice.call(arguments, 0);
		for (var i = 0; i < fields.length; i++) {
			fields[i].setBean(this);
		}
		this.items.addAll(fields);

		return this;
	},
	remove : function(field) {
		this.items.remove(field);
		return this;
	},

	setFieldReadonly : function(name, readonly) {
		this.getField(name).setReadonly(readonly);
	},
	setFieldDisabled : function(name, disabled) {
		this.getField(name).setDisabled(disabled);
	},
	setFieldVisible : function(name, visible) {
		this.getField(name).setVisible(visible);
	},

	setReadonly : function(readonly) {
		this.items.each(function(f) {
					f.setReadonly(readonly);
				});
	},
	setDisabled : function(disabled) {
		this.items.each(function(f) {
					f.setDisabled(disabled);
				});
	},
	reset : function() {
		this.items.each(function(f) {
					f.reset();
				});
	}
});
