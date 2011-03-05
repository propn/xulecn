
Number.prototype.typeName="number";
Object.prototype.typeName="object";
String.prototype.typeName="string";
Array.prototype.typeName="array";
Date.prototype.typeName="date";

Array.prototype.contains = function(obj)
{
	for(var i=0;i<this.length;i++)
		if(this[i]==obj) return true;
	return false;
}

String.prototype.trim = function()
{
    // Use a regular expression to replace leading and trailing 
    // spaces with the empty string
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

