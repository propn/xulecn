<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head><meta http-equiv=Content-Type content='text/html; charset=GBK'> 
    <title></title>
    <link href="Themes/Default/main.css" rel="stylesheet" type="text/css" />
    <link href="Themes/Default/contextmenu.css"  rel="stylesheet" type="text/css" />
    <ui:zx css="false" tree = "true" window = "true"></ui:zx> 
    <style type="text/css">
        .target 
        { 
            position:absolute;
            left:200px;
            top:400px;
            border:solid 1px #ffccee;   
            padding:5px;
            background-color:Blue;
            color:#fff;
         }
    </style>  
    <script src="\CrmWeb\public\widgets\rightmenu\Javascripts\Plugins\jquery.contextmenu.js"  type="text/javascript"></script>  
    <script src="\CrmWeb\public\widgets\rightmenu\Javascripts\contextmenu.js"  type="text/javascript"></script>  

</head>
<body>
      <div class="info">右键菜单示例</div>
        <div id="target"  class="target">在这里右击[所有菜单]</div>
        <div id="target2"  class="target" style="left:600px;top:100px;">在这里右击[disable某些项]</div>
        <div id="target3"  class="target" style="left:400px;top:500px;">在这里右击[这个右键不能显示]</div>
</body>
<script type="text/javascript"> 
        function menuAction() {
                alert(this.data.alias);
            }
        function applyrule(menu) {               
                if (this.id == "target2") {
                    menu.applyrule({ name: "target2",
                        disable: true,
                        items: ["1-1"]
                    });
                }
                else {
                    menu.applyrule({ name: "all",
                        disable: true,
                        items: []
                    });
                }
            }
        function BeforeContextMenu() {
                return this.id != "target3";
            }
        var testmenu = new ContextMenu();
      	testmenu.createMenu(150,applyrule,BeforeContextMenu); 
        testmenu.addItemObj("第一项","images/icons/ico1.gif","","1-1",menuAction,null);
        testmenu.bind(["target","target2","target3"] );
        testmenu.call();  
    </script>
</html>

