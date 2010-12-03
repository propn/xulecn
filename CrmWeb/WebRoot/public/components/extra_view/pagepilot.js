function _initPagePilot(pilot) {

	Control.initElementDataset(pilot);
	pilot.window = window;
	Object.extend(pilot, PagePilot);
	var dataset=Control.getElementDataset(pilot);
	pilot.refresh();
	
}

var PagePilot = {

  refreshPagePilot : function(pilot) {
	  pilot.refresh();
  },

	gotoPage : function(dataset, pageIndex) {
		dataset.flushData(pageIndex);
	},
	
	refresh : function() {
		function genLinkCell(cell, index, pageIndex) {
			if (index == dataset.pageIndex) {
				cell.innerHTML = "<b>" + index + "</b>";
			}
			else {
				cell.innerHTML = "<div onclick=\"return PagePilot.gotoPage(" + dataset.id + "," + index + ");\" " +
					"style=\"color: blue; cursor: hand; text-decoration: underline;\">" + index + "</div>";
			}
		}
	
		var pilot = this;
		var dataset=Control.getElementDataset(pilot);
		if (dataset) {
			var row=pilot.tBodies[0].rows[0];
			if (row) {
				row.removeNode(true);
			}
	
			var maxLink = System.getInt(pilot.maxLink);
			var startIndex = dataset.pageIndex - System.getInt(maxLink / 2);
	
			if (startIndex > (dataset.pageCount - maxLink + 1)) {
			  startIndex = dataset.pageCount - maxLink + 1;
			}
			if (startIndex < 1) {
			  startIndex = 1;
			}
	
			var endIndex = startIndex + maxLink - 1;
			if (endIndex > dataset.pageCount) {
			  endIndex = dataset.pageCount;
			}
	
			row = pilot.tBodies[0].insertRow();
	
			if (startIndex > 1) {
				var cell = row.insertCell();
				genLinkCell(cell, 1, dataset.pageIndex);
	
				if (startIndex > 2) {
					var cell = row.insertCell();
					cell.innerHTML = "...";
				}
			}
	
			for (var i = startIndex; i <= endIndex; i++) {
				var cell = row.insertCell();
				genLinkCell(cell, i, dataset.pageIndex);
			}
	
			if (endIndex < dataset.pageCount) {
				if (endIndex < dataset.pageCount - 1) {
					var cell = row.insertCell();
					cell.innerHTML = "...";
				}
	
				var cell = row.insertCell();
				genLinkCell(cell, dataset.pageCount, dataset.pageIndex);
			}
		}
	}
}