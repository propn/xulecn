MutaSelect = {
	dataset1 : null,
	dataset2 : null,
	
	moveRecord : function(datasetA,datasetB) {
	  if (datasetA.getCurrent()) {
	    datasetB.insertRecord();
	    datasetB.copyRecord(datasetA.record);
	    datasetB.postRecord();
	    datasetA.deleteRecord();
	  }
	},  
  
  addAll : function(){
		while (MutaSelect.dataset1.getCurrent()) {
		  MutaSelect.moveRecord(MutaSelect.dataset1, MutaSelect.dataset2);
		}  	
  },
  
  addOne : function(){
    MutaSelect.moveRecord(MutaSelect.dataset1, MutaSelect.dataset2); 	
  },
  
  removeOne : function(){
    MutaSelect.moveRecord(MutaSelect.dataset2, MutaSelect.dataset1);  	
  },
  
  removeAll : function(){
		MutaSelect.dataset1.disableControls();
		MutaSelect.dataset2.disableControls();
		try{
		  while (!MutaSelect.dataset2.isFirst() || !MutaSelect.dataset2.isLast()) {
		    MutaSelect.moveRecord(MutaSelect.dataset2, MutaSelect.dataset1);
		  }
		}
		finally {
		  MutaSelect.dataset1.enableControls();
		  MutaSelect.dataset2.enableControls();
		}  	
  }   
}

function button_addAll_onClick(button) { MutaSelect.addAll(); }
function button_addOne_onClick(button) { MutaSelect.addOne(); }
function button_removeOne_onClick(button) { MutaSelect.removeOne(); }
function button_removeAll_onClick(button) { MutaSelect.removeAll(); }

function makeMutaSelect(mutaSelect){	
	mutaSelect.innerHTML =	
	   '<table cellspacing="0"  cellpadding="2"  width="100%"  height="100%"  border="0"  borderColor="black"  style="border-collapse: collapse;">'
	  +'  <tr>'
    +'  	<td><div id="sourceTableContainer" style="width:100%;height:100%;border:1px gray solid;overflow:auto;"></div></td>'
	  +'    <td width="1">'
		+'      <table cellspacing="0"  cellpadding="2"  width="100%"  border="0"  borderColor="black"  style="border-collapse: collapse;">'
		+'        <tr><td><button class="button" id="button_addAll" style="width:40;">&gt;&gt;</button></td></tr>'
		+'        <tr><td><button class="button" id="button_addOne" style="width:40;">&gt;</button></td></tr>'
		+'        <tr><td><button class="button" id="button_removeOne" style="width:40;">&lt;</button></td></tr>'
		+'        <tr><td><button class="button" id="button_removeAll" style="width:40;">&lt;&lt;</button></td></tr>'
		+'      </table>'
	  +'    </td>'
	  +'    <td><div id="targetTableContainer" style="width:100%;height:100%;border:1px gray solid;overflow:auto;"></div></td>'
	  +'  </tr>'
	  +'</table>';
	
	var sourceTableContainer = document.getElementById("sourceTableContainer");
	var targetTableContainer = document.getElementById("targetTableContainer");
	
	var sourceTable = document.getElementById(mutaSelect.sourceTable);
	var targetTable = document.getElementById(mutaSelect.targetTable);

	sourceTableContainer.appendChild(sourceTable);
	targetTableContainer.appendChild(targetTable);
	
	sourceTable.ondblclick=function(){MutaSelect.addOne();};
	targetTable.ondblclick=function(){MutaSelect.removeOne();};
	
	MutaSelect.dataset1 = eval(sourceTable.dataset);
	MutaSelect.dataset2 = eval(targetTable.dataset);
}
