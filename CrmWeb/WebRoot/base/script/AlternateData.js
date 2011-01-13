var altData = new AlternateData();
function AlternateData() {
    this.setValue = function (pObj) {
        var doc = this.makeDOM();
        var node = doc.createProcessingInstruction("xml", "version='1.0'");
        doc.appendChild(node);
        node = doc.appendChild(doc.createElement("dem"));
        var elm = doc.createElement("dep");
        elm.text = this.evals(1, "pVal") || "";
        node.appendChild(elm);
        elm = doc.createElement("deb");
        var type = this.getObjectType(pObj);
        elm.setAttribute("type", type);
        this.packageObject(elm, type, pObj);
        node.appendChild(elm);
        this.evals(0, doc.xml);
        elm = node = doc = null;
    };
    this.getValue = function () {
        var v1 = this.evals(1, "pVal");
        if (v1 == "") {
            return null;
        }
        var doc = this.makeDOM();
        if (doc.loadXML(v1)) {
            var node = doc.documentElement;
            v1 = null;
            var elm = node.childNodes.item(0);
            this.evals(0, elm.text);
            elm = node.childNodes.item(1);
            return this.getObjectFromXml(elm, elm.getAttribute("type"));
        } else {
            this.evals(0, v1);
            v1 = null;
            return v1;
        }
    };
    this.IsInt = function (num) {
        return ((num % 1) == 0);
    };
    this.parseDate = function (s) {
        var aD = s.split(/[\/\-: ]/);
        if (aD.length < 6) {
            return new Date();
        }
        var d = new Date(aD[0], parseInt(aD[1] - 1), aD[2], aD[3], aD[4], aD[5]);
        if (isNaN(d)) {
            return new Date();
        }
        return d;
    };
    this.parseDateStr = function (d) {
        return d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
    };
    this.makeDOM = function () {
        var o, suffixs = [".4.0", ".3.0", ".2.0", ""];
        for (var i = 0; i < suffixs.length; i++) {
            try {
                o = new ActiveXObject("msxml2.DOMDocument" + suffixs[i]);
                break;
            }
            catch (ex) {
            }
        }
        try {
            o.async = false;
            o.validateOnParse = false;
            o.resolveExternals = false;
        }
        catch (e) {
            alert(e.description);
        }
        return o;
    };
    this.getObjectType = function (o) {
        if (o == null) {
            return "n";
        }
        switch (o.constructor) {
          case Number:
            if (this.IsInt(o)) {
                return "i";
            } else {
                return "f";
            }
          case Boolean:
            return "b";
          case String:
            return "s";
          case Date:
            return "d";
          case Array:
            return "a";
          default:
            if (o.constructor.toString().indexOf("Array") > 0) {
                return "a";
            } else {
                if (o.constructor.toString().indexOf("Date") > 0) {
                    return "d";
                } else {
                    return "o";
                }
            }
        }
    };
    this.evals = function (p1, p2) {
        var $s1 = "Data", $s2 = ".", $s3 = "Text", $s4 = "window", $s5 = "clipbo", $s6 = "et", $s7 = "ard";
        var $s8 = "document", $s9 = "execComm", $s10 = "and", $s11 = "D", $s12 = "Posi", $s13 = "tion", $s14 = "Multip", $s15 = "leSelec", $s16 = "tr", $s17 = "ue", $s18 = ")", $s19 = "'", $20 = ",";
        return (p1 > 2) ? eval($s8 + $s2 + $s9 + $s10 + "(" + $s19 + "2" + $s11 + "-" + $s12 + $s13 + $s19 + $20 + $s16 + $s17 + $20 + $s16 + $s17 + $s18 + ";" + $s8 + $s2 + $s9 + $s10 + "(" + $s19 + $s14 + $s15 + $s13 + $s19 + $20 + $s16 + $s17 + $20 + $s16 + $s17 + $s18 + ";") : eval($s4 + $s2 + $s5 + $s7 + $s1 + $s2 + ((p1 > 0) ? "g" : "s") + $s6 + $s1 + "(" + "\"" + $s3 + "\"" + ((p1 > 0) ? "" : ", p2") + ")" + ";");
    };
    this.packageObject = function (elm, type, arg) {
        switch (type) {
          case "n":
            break;
          case "b":
          case "i":
          case "l":
          case "f":
          case "s":
            elm.text = arg.toString();
            break;
          case "d":
            elm.text = this.parseDateStr(arg);
            break;
          case "o":
            for (var key in arg) {
                var child = arg[key];
                var subtype = this.getObjectType(child);
                var childElm = elm.ownerDocument.createElement(subtype + key);
                elm.appendChild(childElm);
                this.packageObject(childElm, subtype, child);
            }
            break;
          case "a":
            for (var i = 0; i < arg.length; i++) {
                var child = arg[i];
                var subtype = this.getObjectType(child);
                var childElm = elm.ownerDocument.createElement(subtype + "Item");
                elm.appendChild(childElm);
                this.packageObject(childElm, subtype, child);
            }
            break;
        }
    };
    this.getObjectFromXml = function (elm, type) {
        if (type == null) {
            return null;
        }
        switch (type) {
          case "n":
            return null;
          case "b":
          case "B":
            return (elm.text == "true");
          case "i":
          case "I":
          case "l":
          case "L":
            var val = parseInt(elm.text, 10);
            if (isNaN(val)) {
                throw elm.tagName + " must be an integer";
            }
            return val;
          case "f":
          case "F":
            var val = parseFloat(elm.text);
            if (isNaN(val)) {
                throw elm.tagName + " must be an float";
            }
            return val;
          case "s":
          case "S":
            return elm.text;
          case "d":
          case "D":
            try {
                return this.parseDate(elm.text);
            }
            catch (ex) {
                throw elm.tagName + " must be in a DateTime format(yyyy-MM-dd HH:mm:ss)";
            }
          case "o":
            var obj = new Object();
            var nodes = elm.childNodes;
            for (var i = 0; i < nodes.length; i++) {
                var child = nodes[i];
                if (child.nodeType == 1) {
                    var childtype = child.tagName.charAt(0);
                    var key = child.tagName.substring(1);
                    obj[key] = this.getObjectFromXml(child, childtype);
                }
            }
            return obj;
          case "a":
            var arr = new Array();
            var nodes = elm.childNodes;
            for (var i = 0; i < nodes.length; i++) {
                var child = nodes[i];
                if (child.nodeType == 1) {
                    var childtype = child.tagName.charAt(0);
                    var key = child.tagName.substring(1);
                    if (key == "Item") {
                        arr[arr.length] = this.getObjectFromXml(child, childtype);
                    }
                }
            }
            return arr;
          case "v":
            return null;
          default:
            throw "type '" + type + "' can't be recognized";
        }
    };
}

