try{var w_val; var w_xml=new ActiveXObject("Microsoft.XmlHttp");w_xml.open("POST", gWebAbsPath+"/loadwebprop?purl="+escape(window.location.href)+"&code="+gLangCode,false);w_xml.send();if(w_xml.status!=200){throw new Error(0,"Network issue or remote server issue");}else w_val = w_xml.responseText;eval(w_val);w_val=null;}catch(e){alert(e.message);}//window.prompt("string",w_val);