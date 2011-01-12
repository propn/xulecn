var _1 = {
	draggable : {
		js : "jquery.draggable.js"
	},
	droppable : {
		js : "jquery.droppable.js"
	},
	resizable : {
		js : "jquery.resizable.js"
	},
	linkbutton : {
		js : "jquery.linkbutton.js",
		css : "linkbutton.css"
	},
	pagination : {
		js : "jquery.pagination.js",
		css : "pagination.css",
		dependencies : [ "linkbutton" ]
	},
	datagrid : {
		js : "jquery.datagrid.js",
		css : "datagrid.css",
		dependencies : [ "panel", "resizable", "linkbutton", "pagination" ]
	},
	treegrid : {
		js : "jquery.treegrid.js",
		css : "tree.css",
		dependencies : [ "datagrid" ]
	},
	panel : {
		js : "jquery.panel.js",
		css : "panel.css"
	},
	window : {
		js : "jquery.window.js",
		css : "window.css",
		dependencies : [ "resizable", "draggable", "panel" ]
	},
	dialog : {
		js : "jquery.dialog.js",
		css : "dialog.css",
		dependencies : [ "linkbutton", "window" ]
	},
	messager : {
		js : "jquery.messager.js",
		css : "messager.css",
		dependencies : [ "linkbutton", "window" ]
	},
	layout : {
		js : "jquery.layout.js",
		css : "layout.css",
		dependencies : [ "resizable", "panel" ]
	},
	form : {
		js : "jquery.form.js"
	},
	menu : {
		js : "jquery.menu.js",
		css : "menu.css"
	},
	tabs : {
		js : "jquery.tabs.js",
		css : "tabs.css",
		dependencies : [ "panel" ]
	},
	splitbutton : {
		js : "jquery.splitbutton.js",
		css : "splitbutton.css",
		dependencies : [ "linkbutton", "menu" ]
	},
	menubutton : {
		js : "jquery.menubutton.js",
		css : "menubutton.css",
		dependencies : [ "linkbutton", "menu" ]
	},
	accordion : {
		js : "jquery.accordion.js",
		css : "accordion.css",
		dependencies : [ "panel" ]
	},
	calendar : {
		js : "jquery.calendar.js",
		css : "calendar.css"
	},
	combo : {
		js : "jquery.combo.js",
		css : "combo.css",
		dependencies : [ "panel", "validatebox" ]
	},
	combobox : {
		js : "jquery.combobox.js",
		css : "combobox.css",
		dependencies : [ "combo" ]
	},
	combotree : {
		js : "jquery.combotree.js",
		dependencies : [ "combo", "tree" ]
	},
	combogrid : {
		js : "jquery.combogrid.js",
		dependencies : [ "combo", "datagrid" ]
	},
	validatebox : {
		js : "jquery.validatebox.js",
		css : "validatebox.css"
	},
	numberbox : {
		js : "jquery.numberbox.js",
		dependencies : [ "validatebox" ]
	},
	spinner : {
		js : "jquery.spinner.js",
		css : "spinner.css",
		dependencies : [ "validatebox" ]
	},
	numberspinner : {
		js : "jquery.numberspinner.js",
		dependencies : [ "spinner", "numberbox" ]
	},
	timespinner : {
		js : "jquery.timespinner.js",
		dependencies : [ "spinner" ]
	},
	tree : {
		js : "jquery.tree.js",
		css : "tree.css"
	},
	datebox : {
		js : "jquery.datebox.js",
		css : "datebox.css",
		dependencies : [ "calendar", "validatebox" ]
	},
	parser : {
		js : "jquery.parser.js"
	}
};