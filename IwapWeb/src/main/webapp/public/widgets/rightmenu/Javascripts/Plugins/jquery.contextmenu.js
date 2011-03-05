/// <reference path="../intellisense/jquery-1.2.6-vsdoc-cn.js" />
/* --------------------------------------------------	
����˵��
option: {width:Number, items:Array, onShow:Function, rule:JSON}
��Ա�﷨(������ʽ)	-- para.items
-> {text:String, icon:String, type:String, alias:String, width:Number, items:Array}		--	�˵���
-> {text:String, icon:String, type:String, alias:String, action:Function }				--	�˵���
-> {type:String}																		--	�˵��ָ���
--------------------------------------------------*/
(function(jQ) {
    function returnfalse() { return false; };
    jQ.fn.contextmenu = function(option,type) {   
    	
        option = jQ.extend({ alias: "cmroot"+option.rootId, width: 150 }, option);
        var ruleName = null, target = null,
	    groups = {}, mitems = {}, actions = {}, showGroups = [],
        itemTpl = "<div class='b-m-jQ[type]' unselectable=on><nobr unselectable=on><img src='jQ[icon]' align='absmiddle'/><span unselectable=on>jQ[text]</span></nobr></div>";
        var gTemplet = jQ("<div/>").addClass("b-m-mpanel").attr("unselectable", "on").css("display", "none");
        var iTemplet = jQ("<div/>").addClass("b-m-item").attr("unselectable", "on");
        var sTemplet = jQ("<div/>").addClass("b-m-split");
        
        //�����˵���
        var buildGroup = function(obj) {
            groups[obj.alias] = this;
            this.gidx = obj.alias;
            this.id = obj.alias;
            if (obj.disable) {
                this.disable = obj.disable;
                this.className = "b-m-idisable";
            }
            jQ(this).width(obj.width).click(returnfalse).mousedown(returnfalse).appendTo(jQ("body"));
            obj = null;
            return this;
        };
        var buildItem = function(obj) {
            var T = this;
            T.title = obj.text;
            T.idx = obj.alias;
            T.gidx = obj.gidx;
            T.data = obj;
            T.innerHTML = itemTpl.replace(/\jQ\[([^\]]+)\]/g, function() {
                return obj[arguments[1]];
            });
            if (obj.disable) {
                T.disable = obj.disable;
                T.className = "b-m-idisable";
            }
            obj.items && (T.group = true);
            obj.action && (actions[obj.alias] = obj.action);
            mitems[obj.alias] = T;
            T = obj = null;
            return this;
        };
        //��Ӳ˵���
        var addItems = function(gidx, items) {
            var tmp = null;
            for (var i = 0; i < items.length; i++) {
                if (items[i].type == "splitLine") {
                    //�˵��ָ���
                    tmp = sTemplet.clone()[0];
                } else {
                    items[i].gidx = gidx;
                    if (items[i].type == "group") {
                        //�˵���
                        buildGroup.apply(gTemplet.clone()[0], [items[i]]);
                        arguments.callee(items[i].alias, items[i].items);
                        items[i].type = "arrow";
                        tmp = buildItem.apply(iTemplet.clone()[0], [items[i]]);
                    } else {
                        //�˵���
                        items[i].type = "ibody";
                        tmp = buildItem.apply(iTemplet.clone()[0], [items[i]]);
                        jQ(tmp).click(function(e) {
                            if (!this.disable) {
                                if (jQ.isFunction(actions[this.idx])) {
                                    actions[this.idx].call(this, target);
                                }
                                hideMenuPane();
                            }
                            return false;
                        });

                    } //Endif
                    jQ(tmp).bind("contextmenu", returnfalse).hover(overItem, outItem);
                } //Endif
                groups[gidx].appendChild(tmp);
                tmp = items[i] = items[i].items = null;
            } //Endfor
            gidx = items = null;
        };
        var overItem = function(e) {
            //����˵������          
            if (this.disable)
                return false;
            hideMenuPane.call(groups[this.gidx]);
            //����ǲ˵���
            if (this.group) {
                var pos = jQ(this).offset();
                var width = jQ(this).outerWidth();
                showMenuGroup.apply(groups[this.idx], [pos, width]);
            }
            this.className = "b-m-ifocus";
            return false;
        };
        //�˵���ʧȥ����
        var outItem = function(e) {
            //����˵������
            if (this.disable )
                return false;
            if (!this.group) {
                //�˵���
                this.className = "b-m-item";
            } //Endif
            return false;
        };
        //��ָ��λ����ʾָ���Ĳ˵���
        var showMenuGroup = function(pos, width) {
            var bwidth = jQ("body").width();
            var bheight = document.documentElement.clientHeight;
            var mwidth = jQ(this).outerWidth();
           // alert(jQ(this).outerWidth());
          //  alert(jQ(this).innerWidth())
          //   alert(jQ(this).width())
            var mheight = jQ(this).outerHeight();
          //  alert(jQ(this).innerHeight())
            pos.left = (pos.left + width + mwidth > bwidth) ? (pos.left - mwidth < 0 ? 0 : pos.left - mwidth) : pos.left + width;
            pos.top = (pos.top + mheight > bheight) ? (pos.top - mheight + (width > 0 ? 25 : 0) < 0 ? 0 : pos.top - mheight + (width > 0 ? 25 : 0)) : pos.top;
           // old_alert(jQ(this).get(0).outerHTML) 
            jQ(this).css(pos).show();
            if(!jQ(this).attr("iframeFlag")){ 
              var iframe = document.createElement("<iframe  frameborder='0' framespacing='0' src='javascript:false' style='margin:0;padding:0;position:absolute; visibility:inherit; width:"+mwidth+"; height:"+mheight+"; z-index:-1; filter=\"progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\";'></iframe>");
          	  jQ(this).prepend(iframe);
          	  jQ(this).attr("iframeFlag",true); 
          	 // old_alert(jQ(this).get(0).outerHTML) 
            }
           
            
           
            
           
            //alert("11")
            showGroups.push(this.gidx);
        };
        //���ز˵���
        var hideMenuPane = function() {
            var alias = null;
            for (var i = showGroups.length - 1; i >= 0; i--) {
                if (showGroups[i] == this.gidx)
                    break;
                alias = showGroups.pop();
                groups[alias].style.display = "none";
                mitems[alias] && (mitems[alias].className = "b-m-item");
            } //Endfor
            //CollectGarbage();
        };
        function applyRule(rule) {
            if (ruleName && ruleName == rule.name)
                return false;
            for (var i in mitems)
                disable(i, !rule.disable);
            for (var i = 0; i < rule.items.length; i++)
                disable(rule.items[i], rule.disable);
            ruleName = rule.name;
        };
        function disable(alias, disabled) {
            var item = mitems[alias];
            item.className = (item.disable = item.lastChild.disabled = disabled) ? "b-m-idisable" : "b-m-item";
        };

        /** �Ҽ��˵���ʾ */
        function showMenu(e, menutarget) {
            target = menutarget; 
            showMenuGroup.call(groups["cmroot"+option.rootId], { left: e.pageX, top: e.pageY }, 0);
            jQ(document).one('mousedown', hideMenuPane);
        }
        var jQroot = jQ("#" + option.alias);
        var root = null;
        if (jQroot.length == 0) {
            root = buildGroup.apply(gTemplet.clone()[0], [option]);
            root.applyrule = applyRule;
            root.showMenu = showMenu;
            addItems(option.alias, option.items);
        }
        else {
            root = jQroot[0];
        }  
        var me = jQ(this).each(function() { 
        	var bindType = "contextmenu";
        	if(option.callType){
        		bindType = "click";
        	}  
            return jQ(this).bind(bindType, function(e) { 
            	
                var bShowContext = (option.onContextMenu && jQ.isFunction(option.onContextMenu)) ? option.onContextMenu.call(this, e) : true;
                
                if (bShowContext) {
                    if (option.onShow && jQ.isFunction(option.onShow)) {
                        option.onShow.call(this, root);
                    }
                    root.showMenu(e, this);
                }
                return false;
            });
        });
        //������ʾ����
        if (option.rule) {
            applyRule(option.rule);
        }
        gTemplet = iTemplet = sTemplet = itemTpl = buildGroup = buildItem = null;
        addItems = overItem = outItem = null; 
        //CollectGarbage();
        return me;
    }
})(jQuery);