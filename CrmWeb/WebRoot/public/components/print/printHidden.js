
/**
  * ҳ���ӡ��
  *
  */
  
  
//���ô�ӡ����
if (printIsNativeSupport()) {
    window.print2 = window.print;
}
window.print = printFrame;

//��ӡ������ڣ�ͨ��iframe����Ҫ��ӡ��ҳ�����ݡ�
function printHidden(url) {
    document.body.insertAdjacentHTML("beforeEnd", "<iframe name=printHiddenFrame width=0 height=0></iframe>");
    doc = printHiddenFrame.document;
    doc.open();
    doc.write("<frameset onload='parent.onprintHiddenFrame()' rows=\"100%\">" + "<frame name=printMe src=\"" + url + "\">" + "</frameset>");
    doc.close();
}

//��ҳ���ж������µ�page_onLoad()����
/*
function page_onLoad(){
	onprintHiddenFrame();
}
*/

//ִ�д�ӡ�ķ���
function onprintHiddenFrame() {
    function onfinish() {
        printHiddenFrame.outerHTML = "";
        if (window.onprintcomplete) {
            window.onprintcomplete();
        }
    }
    printFrame(printHiddenFrame.printMe, onfinish);
}

//��ӡ�ķ���
function printFrame(frame, onfinish) {
    if (!frame) {
        frame = window;
    }
    function execOnFinish() {
        switch (typeof (onfinish)) {
          case "string":
            execScript(onfinish);
            break;
          case "function":
            onfinish();
        }
        if (focused && !focused.disabled) {
            focused.focus();
        }
    }
    if (frame.document.readyState !== "complete" && !confirm("The document to print is not downloaded yet! Continue with printing?")) {
        execOnFinish();
        return;
    }
    var eventScope = printGetEventScope(frame);
    var focused = document.activeElement;
    window.printHelper = function () {
        printWB.ExecWB(6, 11);
        printFireEvent(frame, eventScope, "onafterprint");
        printWB.outerHTML = "";
        execOnFinish();
        window.printHelper = null;
    };
    document.body.insertAdjacentHTML("beforeEnd", "<object id=\"printWB\" width=0 height=0 classid=\"clsid:8856F961-340A-11D0-A96B-00C04FD705A2\"></object>");
    printFireEvent(frame, eventScope, "onbeforeprint");
    frame.focus();
    window.printHelper = printHelper;
    setTimeout("window.printHelper()", 0);
}

// helpers
function printIsNativeSupport() {
    var agent = window.navigator.userAgent;
    var i = agent.indexOf("MSIE ") + 5;
    return parseInt(agent.substr(i)) >= 5 && agent.indexOf("5.0b1") < 0;
}

function printFireEvent(frame, obj, name) {
    var handler = obj[name];
    switch (typeof (handler)) {
      case "string":
        frame.execScript(handler);
        break;
      case "function":
        handler();
    }
}

function printGetEventScope(frame) {
    var frameset = frame.document.all.tags("FRAMESET");
    if (frameset.length) {
        return frameset[0];
    }
    return frame.document.body;
}


