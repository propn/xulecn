ZX.Bean.Field = function(config) {
	this.id = '';
	this.name = '';
	this.text = '';
	this.value = '';
	this.originalValue = '';
	this.element = null;
	this.type = 'text';// text,checkbox,radiobox,select;
	this.bean = null;
	this.actions = new ZX.Array();
	ZX.apply(this, config);
	this.init();
}
ZX.extend(ZX.Bean.Field, ZX.Basis, { // zx.bean.field����defalut���еķ���,��field�̳�zx.basis���еķ���,��superClassָ��basic
	bind : false,// �������Ƿ�󶨵�����ؼ���Ĭ������ǲ��󶨵�
	minLength : 0,// ��С����
	maxLength : Number.MAX_VALUE,// ��󳤶�
	minLengthText : '��С���Ȳ���С�� {0} ���ַ�!',
	maxLengthText : '��󳤶Ȳ��ܳ��� {0} ���ַ�!',
	selectOnFocus : false,
	moreNullId : null, // ����ֶβ���ͬʱΪ�յ���֤,element id�ö��ŷָ�
	showPosition : "right", // ������ʾ��Ϣ��ʾ��λ�� Ŀǰֻ��right down,up
	moreNullText : "", // ��ʾ����Ϣ
	allowEmpty : true,
	emptyText : 'ֵ������Ϊ��',
	tipText : '',
	needFoucs : true, // ������ʾ��Ϣʱ,�ı����Ƿ���Ҫ���㼯��
	validator : null,
	vtype : null,
	vtypeText : null,
	regex : null,
	regexText : '',
	validationEvent : false,
	validateOnBlur : false,
	rendered : false,
	readonly : false,
	disabled : false,
	hidden : false,
	hasFocus : false,
	viewWithAlert : false,
	lastHideError : false,
	blurValidate : true, // ����뿪ʱ���ж��Ƿ���Ҫ��֤
	fieldErrorMessArr : [],
	init : function() { // ��ʼ��ʱ����tipTextֵ,���ǲ���Ҫ��ֵ֤
		ZX.Bean.Field.superclass.constructor.call(this); // ���ø���Ĺ��캯��
		if (this.getId() == null || this.getId() == "") {
			this.id = this.getName();
		}
		this.render();

		this.addEvents('focus', 'blur', 'change', 'changevalue', 'focustext'); // ��Ĭ���¼�

		this.initValue();

		this.setReadonly(this.readonly);
		this.setDisabled(this.disabled);

		this.setVisible(this.hidden);

		// this.registerEvents();
	},
	registerEvents : function() { // �����¼��Ĺ�������
		var tmpArray = new ZX.Array();
		var me = this;
		this.actions.each(function(act) {
					var event = act.event;
					if (!tmpArray.containsKey(event)) {
						tmpArray.add(event);
						var acts = me.getActions(event);
						me.on(event, function() {
									ZX.each(acts, function(act) {
												act.exe(me);
											});
								});
					}
				});
	},
	getActions : function(event) { // ��ȡ�����Ķ���
		var acts = [];
		this.actions.each(function(act) {
					if (act.event == event) {
						acts[acts.length] = act;
					}
				});
		return acts;
	},
	render : function() {
		if (!this.bind)
			return;
		el = ZX.getDom(this.id);
		if (!el)
			return;

		this.rendered = true;
		this.element = new ZX.Element(this.id);
		var tagName = el.tagName.toLowerCase();
		if (tagName == 'select') {
			this.type = 'select';
		} else {
			if (el.getAttribute('type')) {
				var _type = el.getAttribute('type').toLowerCase();
				if (_type)
					this.type = _type;
			}
		}
	},
	getElement : function() {
		return this.element;
	},
	getId : function() {
		return this.id;
	},
	getName : function() {
		return this.name;
	},
	setBean : function(bean) {
		this.bean = bean;
	},
	getBean : function() {
		return this.bean;
	},
	initValue : function() {
		if (this.rendered && this.element.dom.value != this.tipText
				&& !!this.tipText) {
			this.setValue(this.tipText, "initElement");
		}

		this.originalValue = this.getValue();

	},
	reset : function() {
		if (this.rendered) {
			var firstValue = this.element.getFirstValue(); // ��ȡĬ��ֵ,������Ĭ��ֵ
			if (firstValue) {
				this.setValue(firstValue);
				return;
			}
		}
		this.setValue(this.tipText);
	},
	focus : function() { // ���㼯��ʱ�����ÿ�ʼֵ

		this.hideErrorInfo(); // ���ش�����ʾ��Ϣ

		// ���㼯����������Ϣ��ʱ�����������Ϣ
		if (this.element.dom.value && this.element.dom.value == this.tipText) {
			this.element.dom.style.color = "#154273"; // 154273
			this.setValue("", "focusElement"); // ���㼯��ʱ���tipTextֵ
		}
		if (!this.hasFocus) {
			this.hasFocus = true;

			this.startValue = this.getValue();
			this.fireEvent("focustext", this);
		}

		// ���ý����뿪�ı���ı���ɫ

		if (this.element.dom) {

			jQ(this.element.dom).select(); // �����ı��򽹵㼯��

			if (this.type != "select") {
				jQ(this.element.dom).css("background-color", "#FFF");

			} else {

				jQ(this.element.dom).find("option").each(function() {
							jQ(this).removeClass("bindInputBlur");
							jQ(this).addClass("bindInputFoucs");

						});

			}
			var oldWidth = jQ(this.element.dom).css("width");
			jQ(this.element.dom).css("width", oldWidth);
			// ���ý��㼯��,��ӰЧ��
			if (jQ(this.element.dom)) {
				if (this.type != "select") {
					jQ(this.element.dom).parent("td").css({
								"filter" : "glow(color:#62B3FE,strength=1)"
							});
				}

			}

		}

	},
	blur : function() {

		// �����뿪ʱ,�ж����õ�ֵ�Ƿ�Ϊ��,Ϊ�վ͸���ʾֵ
		if (this.element
				&& (this.element.dom.value == "" || this.element.dom.value == null)
				|| (this.element.dom.value
						&& this.element.dom.value == this.tipText && !this.tipText)) {
			this.setValue(this.tipText, "initElement");

		} else {

			this.element.dom.style.color = "#154273";
		}

		this.hasFocus = false;
		if (this.blurValidate) { // �жϹ���뿪ʱ�Ƿ���Ҫ��֤

			this.validate();
		}

		var v = this.getValue();

		if (String(v) !== String(this.startValue)) {

			this.fireEvent('changevalue', this); // �õط�����һ��bug onblur��ʱ��ִ������
		}
		// ���ý����뿪�ı���ı���ɫ

		if (this.type != "select") {
			jQ(this.element.dom).css("background-color", "#FFFFFF");
		} else {
			jQ(this.element.dom).find("option").each(function() {
						jQ(this).removeClass("bindInputFoucs");
						jQ(this).addClass("bindInputBlur");

					});
		}
		var oldWidth = jQ(this.element.dom).css("width");

		jQ(this.element.dom).css("width", oldWidth);

		if (jQ(this.element.dom)) {
			if (this.type != "select") {

				jQ(this.element.dom).parent("td").css({
							"filter" : ""

						});

			}
		}

	},
	change : function() { // �����¼����¼������ĺ�����changevalue ע����¼�����
		this.hasFocus = false;
		if (this.validationEvent !== false && this.validateOnBlur
				&& this.validationEvent != "blur") {
			this.validate();
		}
		var v = this.getValue();
		if (!!this.startValue) { // add by wui ��ֵ����ֵ�ı��¼�
			if (String(v) !== String(this.startValue)) {
				// this.fireEvent('changevalue', this);
			}
		}
	},
	changevalue : function() {

	},
	focustext : function() {

	},
	// ���ڴ�����Ϣ��ʱ����ʾ��ʾ���
	markInvalid : function(msg) {

		if (!this.rendered) {
			return;
		}

		// var t = ZX.getDom(this.msgTarget);

		this.fieldErrorMessArr[this.fieldErrorMessArr.length] = [msg];

		if (!this.viewWithAlert) {

			this.showErrorInfo(msg);

		}

	},
	showErrorInfo : function(msg) { // ��ʾ������Ϣ,���ݴ�����Ϣ����������Ϣ��ʾ��

		if (!!msg && !!this.element.dom) {

			var inputObj = jQ(this.element.dom);

			var offset = inputObj.offset();

			var inputTop = offset.top;
			var inputLeft = offset.left;
			var inputWidth = inputObj.outerWidth(true);
			var inputHeight = inputObj.outerHeight(true);

			// ����ǳ���Ϊ0,�����ø�Ĭ��ֵ
			if (inputWidth == 0)
				inputWidth = 100;

			var divClass = "hint";
			var divPointerClass = "hint-pointer";
			// ������ʾ���
			var divContent = null;

			if (this.showPosition == "right") { // Ĭ�����ұ���ʾ

			} else if (this.showPosition == "down") {

				divClass = "hint-down";
				divPointerClass = "hint-down-pointer";
			} else if (this.showPosition == "up") {

				divClass = "hint-up";
				divPointerClass = "hint-up-pointer";
			}

			divContent = jQ("." + divClass, jQ("body"));
			var parentDiv = false;
			if (!divContent.html()) {
				divContent = jQ("." + divClass, window.parent.document.body);
				parentDiv = true; // ��������ʱ
			}

			divContent = divContent.parent();
			if (this.showPosition == "right") { // Ĭ�����ұ���ʾ
				divContent.css({
							top : parentDiv
									? inputTop + inputHeight + 5
									: inputTop,
							left : inputLeft + inputWidth + 10

						});
			} else if (this.showPosition == "down") {
				divContent.css({
							top : parentDiv
									? inputTop + inputHeight + 45
									: inputTop + inputHeight + 12,
							left : inputLeft + inputWidth / 4 - 20

						});
			} else if (this.showPosition == "up") {
				divContent.css({
							top : parentDiv
									? inputTop - inputHeight - 20
									: inputTop - inputHeight - 50,
							left : inputLeft + inputWidth / 4 - 20

						});
			}

			divContent.find("div").empty();

			divContent.find("div").append(msg + "<div class='"
					+ divPointerClass + "'></div>");
			if (inputWidth > 150)
				inputWidth = 150;

			divContent.width(inputWidth);
			var iwidth = divContent.find("div").outerWidth();
			var iheight = divContent.find("div").outerHeight();
			if (!(divContent.find("iframe").length > 0)) {

				var iframeHtml = "<iframe  frameborder='0' framespacing='0' src='javascript:false' style='margin:0;padding:0;position:absolute; visibility:inherit; width:"
						+ iwidth
						+ "; height:"
						+ iheight
						+ "; z-index:-1; filter=\"progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\";'></iframe>";
				divContent.prepend(iframeHtml);
			}

			// alert(divContent.get(0).outerHTML);
		}

	},
	hideErrorInfo : function() {

		if (!!jQ("div[name='errMsg_name']", jQ("body")).html()) {
			jQ("div[name='errMsg_name']").parent().css({
						"top" : "-100px",
						"left" : "-100px"
					});

		} else {
			jQ("div[name='errMsg_name']", window.parent.document.body).parent()
					.css({
								"top" : "-100px",
								"left" : "-100px"
							});
		}

	},
	clearInvalid : function() { // ���������Ϣ
		if (!this.rendered) {
			return;
		}
		this.fieldErrorMess = "";
		if (!this.viewWithAlert) {
			this.hideErrorInfo();
		}

	},
	validateValue : function(value) {
	
		if (this.element) { // �������ص��ı�����Ҫ��֤
			var ff = this.element.dom;
			while(ff){
				if(ff.style.display=="none"){
					return true;
					break;
				}
				if(ff.tagName=="TD"){
					ff = ff.parentElement;
				}else{
					ff = ff.offsetParent;
				}
			}
		}
		
		if (value != undefined) {

			// �Զ��庯������֤
			if (ZX.isFunction(this.validator)) {
				var msg = this.validator(value);
				if (msg !== true) {

					this.markInvalid(msg);
					return false;
				}
			}
			
				function trim(string)
			    {
			        return string.replace(/(^\s*)|(\s*$)/g, "");
			    }
    
			// ������ʽ��֤			
			if (this.regex && this.regexText && value) {
				
				try{//�Ȱ����ͼ��
					var vt = ZX.VTypes;
					var func = eval("vt."+this.regex);
					if(typeof(func)=="function"){
						var checkBack = func(value, this);
						if(this.regex=="idCard"){//���֤���⴦��
							if(checkBack && (checkBack.indexOf("��֤ͨ��")==-1)){
								this.markInvalid(checkBack);
								return false;
							}
						}else{//��������
							if (!checkBack) {
								this.markInvalid(vt[this.regex + 'Text']);
								return false;
							}
						}
						return true;
					}
				}catch(e){}
				
				try{
					//alert(this.name+":"+trim(this.regex)+":"+value);
				
					var result = eval("/"+trim(this.regex)+"/").test(value);
					if (!result) {
						this.markInvalid(this.regexText);
						return false;
					}
				}catch(e){
						return true; //��֤�������棬��������ȥ
				}
				
			}

			// ����ֶζ���Ϊ����֤
			if (!!this.moreNullId) {
				var idArr = this.moreNullId.split(",");

				var result = false;
				for (var i = 0; i < idArr.length; i++) {
					if (!!ZX.getDom(idArr[i]).value) {
						result = true;
					}
				}

				this.needFoucs = true;
				!result && this.markInvalid(this.moreNullText || "�ֶβ���ͬʱΪ��!");
				return result;

			}

			// ���ֶε���֤
			if (value.length < 1 || value == this.emptyText || value == "null") { // if it's blank

				if (this.allowEmpty == true || this.allowEmpty == "true") {

					this.clearInvalid();
					return true;
				} else {

					this.markInvalid(this.emptyText);
					return false;
				}
			}

			// ��С���ȵ���֤
			if (value.length < this.minLength) {

				this.markInvalid(String.format(this.minLengthText, // format(��С����Ϊ{6}λ,20)
						// ��ʽ��Ϊ:��С����Ϊ20λ
						this.minLength));
				return false;
			}

			// ��󳤶ȵ���֤
			if (value.length > this.maxLength) {

				this.markInvalid(String.format(this.maxLengthText,
						this.maxLength));
				return false;
			}

			// ������ʽ����֤
			if (this.vtype) {

				var vt = ZX.VTypes;
				if (!vt[this.vtype](value, this)) {
					this.markInvalid(this.vtypeText || vt[this.vtype + 'Text']);
					return false;
				}
			}

		}

		return true;
	},
	getRawValue : function() { // ��ȡ��Ҫ��֤��ֵ

		var v = this.rendered ? this.element.getValue() : this.value;

		if (v === this.emptyText) {
			v = '';
		}
		if (v == this.tipText && v) {
			v = '';
		}

		return v;
	},
	validate : function() {
		// ����ֶζ���Ϊ����֤	
		this.fieldErrorMessArr = [];

		var validateResult = this.validateValue(this.getRawValue());

		if (validateResult) { // this.disabled || ����disabled��Ҳ��Ҫ��֤
			this.clearInvalid();
			return true;
		}

		return false;
	},
	getValue : function() {

		if (!this.rendered) {
			return this.value;
		}
		var v = this.element.getValue();
		if (v === this.emptyText || v === undefined || v === this.tipText) {
			v = '';
		}
		return v;
	},
	setValue : function(v) { // ��element��ֵ,��field��ֵ
		this.value = v; // ����ֵ

		if (this.tipText && v == this.tipText && this.element) { // ���������ı���������ɫ
			this.element.dom.style.color = "#C0C0C0";
		}

		if (this.rendered) {

			this.element.setValue((ZX.isEmpty(v) ? '' : v)); // element��ֵ

			if (!!arguments[1]
					&& !(arguments[1] == "initElement" || arguments[1] == "focusElement")) {

				this.validate();

			}
		}
		this.fireEvent('changevalue', this);
		return this;
	},
	disedit : function() {
		if (this.rendered) {
			this.element.setReadonly(true);
		}
		this.readonly = true;
		return this;
	},
	enedit : function() {
		if (this.rendered) {

			this.element.setReadonly(false);
		}
		this.readonly = false;
		return this;
	},
	disable : function() {

		if (this.rendered && this.element.dom
				&& this.element.dom.tagName.toLowerCase() != "dt") {

			// jQ(this.element.dom).removeClass("fieldInputFFFFFF");
			// jQ(this.element.dom).addClass("fieldInput");
			if (this.type != "select") {
				jQ(this.element.dom).css("background-color", "#F1F5FA");
				jQ(this.element.dom).css("border", "1px solid #B5B8C8");
			} else {
				jQ(this.element.dom).addClass("fieldInput");
				jQ(this.element.dom).removeClass("fieldInputFFFFFF");
				jQ(this.element.dom).removeClass("bindInputBlur");
				jQ(this.element.dom).removeClass("bindInputFoucs");
				jQ(this.element.dom).find("option").each(function() {
							jQ(this).addClass("fieldInput");
							jQ(this).removeClass("fieldInputFFFFFF");
							jQ(this).removeClass("bindInputBlur");
							jQ(this).removeClass("bindInputFoucs");

						});

			}
			this.element.setDisabled(true);
		}
		this.disabled = true;
		return this;
	},
	enable : function() {
		if (this.rendered && this.element.dom
				&& this.element.dom.tagName.toLowerCase() != "dt") {

			// jQ(this.element.dom).removeClass("fieldInput");
			// jQ(this.element.dom).addClass("fieldInputFFFFFF");
			if (this.type != "select") {
				jQ(this.element.dom).css("background-color", "#FFFFFF");
				jQ(this.element.dom).css("border", "1px solid #7F9DB9");
			} else {
				jQ(this.element.dom).addClass("fieldInputFFFFFF");
				jQ(this.element.dom).removeClass("fieldInput");
				jQ(this.element.dom).removeClass("bindInputBlur");
				jQ(this.element.dom).removeClass("bindInputFoucs");
				jQ(this.element.dom).find("option").each(function() {
							jQ(this).addClass("fieldInputFFFFFF");
							jQ(this).removeClass("fieldInput");
							jQ(this).removeClass("bindInputBlur");
							jQ(this).removeClass("bindInputFoucs");

						});

			}

			this.element.setDisabled(false);
		}
		this.disabled = false;
		return this;
	},
	hide : function() {
		this.doHide();
		this.fireEvent('hide', this);
		return this;
	},
	doHide : function() {
		this.hidden = true;
		if (this.rendered) {
			this.element.setVisible('hidden');
		}
	},
	show : function() {
		this.hidden = false;
		if (this.rendered) {
			this.element.setVisible('visible');
		}
		return this;
	},

	setVisible : function(visible) {
		if (ZX.isBoolean(visible)) {
			this.hidden = visible;
		} else {
			this.hidden = (visible == "false" ? false : true);
		}
		return this[!this.hidden ? 'show' : 'hide']();

	},
	setDisabled : function(_disabled) {
		if (ZX.isBoolean(_disabled)) {
			this.disabled = _disabled;
		} else {
			this.disabled = (_disabled == "false" ? false : true);
		}
		return this[this.disabled ? 'disable' : 'enable']();
	},
	setAllowEmpty : function(empty) {
		if (ZX.isBoolean(empty)) {
			this.allowEmpty = empty;
		} else {
			this.allowEmpty = (empty == "false" ? false : true);
		}
		// return this[this.disabled ? 'disable' : 'enable']();
	},
	setReadonly : function(_readonly) {

		if (ZX.isBoolean(_readonly)) {
			this.readonly = _readonly;
		} else {
			this.readonly = (_readonly == "false" ? false : true);
		}
		return this[this.readonly ? 'disedit' : 'enedit']();
	},
	addAction : function() {

		this.actions.addAll(Array.prototype.slice.call(arguments, 0));
		return this;
	},
	setTipText : function(tipText) {
		
		this.tipText = tipText;
		this.setValue(this.tipText, "initElement");
		
		
		
	},
	setLabel:function(label,empty){
	
		var dom = this.getElement().getDom();
		
		var labelId='label_'+dom.id;
		
		var labelDom=ZX.getDom(labelId);
		if(labelDom)
			if(empty!=null && empty){
				labelDom.innerText=label;
			}else{
				labelDom.innerHTML=label+"<font color='red'>*</font>";
			}
	}

});
