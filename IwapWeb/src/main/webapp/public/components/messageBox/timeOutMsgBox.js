   	//模拟提示信息
   	/*
   	*  MessageBox.Show(null, "提示信息: "+errVO.errorMessage, null, 'OK', "Warning", 1, null, errVO.stackInfo);
   	*/
   	function timeOutAlert(){
   	
   		window.dialogWidth = "435px"; 
		window.dialogHeight = "204px";
		
   		var alertHtml = "";
   		var el = document.createElement('div');
   		el.setAttribute("bgcolor","E7E7E7");   		
   		document.body.innerHTML = "";
   		document.body.appendChild(el);
			alertHtml += "<table width='429' border='0' cellspacing='0' cellpadding='0'>";
				alertHtml += "<tr>";
					alertHtml += "<td width='150' valign='top' background='./public/skins/bsn/messageBox/info_fill.gif'>";
						alertHtml += "<img id='_icon' src='./public/skins/"+themeId+"/messageBox/info_advise.gif' width='150' height='138'>";
					alertHtml += "</td>";
					alertHtml += "<td valign='top' background='./public/skins/bsn/messageBox/info_fill.gif' style='padding-top:28px;padding-right:10px;'>";
						alertHtml += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
							alertHtml += "<tr>";
								alertHtml += "<td id='alertText'>";
									alertHtml += " 提示信息: 登陆已超时，请您重新登陆。";
								alertHtml += "</td>";
							alertHtml += "</tr>";
						alertHtml += "</table>";
					alertHtml += "</td>";
				alertHtml += "</tr>";
			alertHtml += "</table>";	
			alertHtml += "<table width='429' border='0' cellpadding='0' cellspacing='0'>";
				alertHtml += "<tr>";
					alertHtml += "<td  align='right' bgcolor='E7E7E7' style='padding:10px;'>";				
					alertHtml += "</td>";
					alertHtml += "<td align='right' bgcolor='E7E7E7' style='padding:10px;'>";
					alertHtml += "</td>";
					alertHtml += "<td align='right' bgcolor='E7E7E7' style='padding:10px;'>";			
						alertHtml += "<table border='0' cellspacing='0' cellpadding='0'>";
							alertHtml += "<tr id='_buttons'>";
								alertHtml += "<td align='center' name='__msgbox_button' ";//class='button'";
								
								alertHtml += " style='borderTop:solid 1px #5198dd; border-bottom:#596CA5 1px solid;border-left: #8596CA 1px solid;border-right: #596CA5 1px double;border-top: #8596CA 1px solid;padding-left: 6px;padding-right: 6px;font-size:9pt;text-align:center;vertical-align:center;height:19px;cursor:pointer;background-image: url(./public/skins/bsn/messageBox/button_fill_up.gif);'";
											
								alertHtml += " onclick='MessageBoxAction.MB_TIMEOUT()' width='34px'>确 定</td> ";								
							alertHtml += "</tr>";
						alertHtml += "</table>";
					alertHtml += "</td>";
				alertHtml += "</tr>";
			alertHtml += "</table>";
			
		el.innerHTML = alertHtml;
   	}
   	   	

   	
   	