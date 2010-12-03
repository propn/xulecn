
package com.ztesoft.component.file.handlers;

import javax.servlet.http.HttpServletRequest;

import com.ztesoft.component.file.requestcycle.UserPathBuilder;


/**
 * Handler for Connector-related properties.<br />
 * Wraps to the {@link PropertiesLoader}.
 * 
 * @version $Id: ConnectorHandler.java 2101 2008-06-22 22:00:48Z mosipov $
 */
public class ConnectorHandler {

	/**
	 * Getter for the <code>UserFilesPath</code>.
	 * 
	 * @return {@link UserPathBuilder#getUserFilesPath(HttpServletRequest)} or
	 *         the <code>DefaultUserFilePath</code> if {@link UserPathBuilder}
	 *         isn't set.
	 */
	public static String getUserFilesPath(final HttpServletRequest request) {
    	String userFilePath = null ;// 
//    	RequestCycleHandler.getUserFilePath(request);
    	return (userFilePath != null) ? userFilePath : getDefaultUserFilesPath();
    }

	/**
	 * Getter for the default handling of files with multiple extensions.
	 * 
	 * @return <code>true</code> if single extension only should be enforced
	 *         else <code>false</code>.
	 */
	public static boolean isForceSingleExtension() {
		return Boolean.valueOf(PropertiesLoader.getProperty("upload.forceSingleExtension")).booleanValue();
	}

	/**
	 * Getter for the value to instruct the connector to return the full URL of
	 * a file/folder in the XML response rather than the absolute URL.
	 * 
	 * @return <code>true</code> if the property <code>upload.fullUrl</code> is
	 *         set else <code>false</code>.
	 */
	public static boolean isFullUrl() {
		return Boolean.valueOf(PropertiesLoader.getProperty("upload.fullUrl")).booleanValue();
	}

	/**
	 * Getter for the default <code>UserFilesPath</code>.
	 * 
	 * @return <code>DefaultUserFilesPath</code> (/userfiles)
	 */
	public static String getDefaultUserFilesPath() {
		return PropertiesLoader.getProperty("upload.userFilesPath");
	}
	
	/**
	 * Getter for the value to instruct the Connector to check if the uploaded
	 * image is really an image.
	 * 
	 * @return Boolean value of the property
	 *         <code>upload.secureImageUploads</code>.
	 */
	public static boolean isSecureImageUploads() {
		return Boolean.valueOf(PropertiesLoader.getProperty("upload.secureImageUploads")).booleanValue();
	}
}
