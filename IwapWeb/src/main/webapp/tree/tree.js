/**
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
