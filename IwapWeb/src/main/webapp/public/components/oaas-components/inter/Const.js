function getDomainName()
{
	var url = "";
	var index = "";
	var domainName = "";

	url = document.URL;
	index = url.indexOf("//");
	index = url.indexOf("/",index+2);
	domainName = url.substring(0,index);

	return domainName;
}

var g_baseAppName = "/BSNWeb/busifacadeservlet";

var g_baseUrlDomain = getDomainName() + g_baseAppName;

/*var g_urlMap = {"CommonQueryService":(g_baseUrlDomain + "CommonQueryService"),

                };*/

var g_urlMap = g_baseUrlDomain;

var g_imageUrl = "../images";
