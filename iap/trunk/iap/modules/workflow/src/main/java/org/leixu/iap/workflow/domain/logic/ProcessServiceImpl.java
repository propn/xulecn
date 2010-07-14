package org.leixu.iap.workflow.domain.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.leixu.iap.workflow.domain.Process;
import org.leixu.iap.workflow.domain.ProcessResult;
import org.leixu.iap.workflow.util.PathUtil;
import org.leixu.iap.workflow.util.SimpleXMLWorkShop;
import org.leixu.iap.workflow.util.SingleIOFilenameFilter;


public class ProcessServiceImpl implements ProcessService {

    public ProcessServiceImpl() {
        this.setWebAppRootKey(ProcessServiceImpl.WEB_APP_ROOT_KEY);
        this.setBaseDirName(ProcessServiceImpl.BASE_DIR_NAME);
        this.setDefaultFileName(ProcessServiceImpl.DEFAULT_FILE_NAME);
        this.setFileType(ProcessServiceImpl.FILE_TYPE);
    }

    public List listProcess() {
        File baseDir = new File(this.getWebAppRoot(), this.getBaseDirName());
        File[] files = baseDir.listFiles(new SingleIOFilenameFilter(this
                .getFileType()));

        List list = new ArrayList();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            list.add(file);
        }

        return list;
    }

    public ProcessResult getProcess(String name) {
        ProcessResult result = new ProcessResult();

        File baseDir = new File(this.getWebAppRoot(), this.getBaseDirName());
        File file = new File(baseDir, name + PathUtil.SEPARATOR_FORMAT
                + this.getFileType());

        if (!file.exists()) {
            result.setStatus(ProcessService.FILE_NOT_FOUND);
            return result;
        }

        Process process = new Process();
        process.setName(name);
        try {
            process.setDoc(SimpleXMLWorkShop.file2Doc(file));
        } catch (IOException e) {
            result.setStatus(ProcessService.IO_ERROR);
            result.setMes(e.getMessage());
            process = null;
            log.warn("io error on getprocess:" + e.getMessage());
        } catch (JDOMException e) {
            result.setStatus(ProcessService.XML_PARSER_ERROR);
            result.setMes(e.getMessage());
            process = null;
            log.warn("jdom error on getprocess:" + e.getMessage());
        }

        result.setProcess(process);

        return result;
    }

    public ProcessResult addProcess(Process process) {
        ProcessResult result = new ProcessResult();

        File baseDir = new File(this.getWebAppRoot(), this.getBaseDirName());
        File file = new File(baseDir, process.getName()
                + PathUtil.SEPARATOR_FORMAT + this.getFileType());

        if (file.exists()) {
            result.setStatus(ProcessService.FILE_EXIST);
            return result;
        }

        Document doc = process.getDoc();

        try {
            SimpleXMLWorkShop.outputXML(doc, file);
        } catch (FileNotFoundException e) {
            result.setStatus(ProcessService.FILE_NOT_FOUND);
            result.setMes(e.getMessage());
            log.warn("file not found on addprocess:" + e.getMessage());
            return result;
        } catch (IOException e) {
            result.setStatus(ProcessService.IO_ERROR);
            result.setMes(e.getMessage());
            log.warn("io error on addprocess:" + e.getMessage());
            return result;
        }

        result.setStatus(ProcessService.SUCCESS);

        return result;
    }

    public ProcessResult deleteProcess(Process process) {
        ProcessResult result = new ProcessResult();

        File baseDir = new File(this.getWebAppRoot(), this.getBaseDirName());
        File file = new File(baseDir, process.getName()
                + PathUtil.SEPARATOR_FORMAT + this.getFileType());

        if (!file.exists()) {
            result.setStatus(ProcessService.FILE_NOT_FOUND);
            return result;
        }

        if (file.delete()) {
            result.setStatus(ProcessService.SUCCESS);
        }
        else {
            result.setStatus(ProcessService.FAIL);
        }

        return result;
    }

    public ProcessResult updateProcess(Process process) {
        ProcessResult result = new ProcessResult();

        File baseDir = new File(this.getWebAppRoot(), this.getBaseDirName());
        File file = new File(baseDir, process.getName()
                + PathUtil.SEPARATOR_FORMAT + this.getFileType());

        if (!file.exists()) {
            result.setStatus(ProcessService.FILE_NOT_FOUND);
            return result;
        }

        Document doc = process.getDoc();

        try {
            SimpleXMLWorkShop.outputXML(doc, file);
        } catch (FileNotFoundException e) {
            result.setStatus(ProcessService.FILE_NOT_FOUND);
            result.setMes(e.getMessage());
            log.warn("file not found on updateprocess:" + e.getMessage());
            return result;
        } catch (IOException e) {
            result.setStatus(ProcessService.IO_ERROR);
            result.setMes(e.getMessage());
            log.warn("io error on updateprocess:" + e.getMessage());
            return result;
        }

        result.setStatus(ProcessService.SUCCESS);

        return result;
    }

    /**
     * @return Returns the baseDirName.
     */
    public String getBaseDirName() {
        return baseDirName;
    }

    /**
     * @param baseDirName The baseDirName to set.
     */
    public void setBaseDirName(String baseDirName) {
        this.baseDirName = baseDirName;
    }

    /**
     * @return Returns the defaultFileName.
     */
    public String getDefaultFileName() {
        return this.defaultFileName;
    }

    /**
     * @param defaultFileName The defaultFileName to set.
     */
    public void setDefaultFileName(String defaultFileName) {
        this.defaultFileName = defaultFileName;
    }

    /**
     * @return Returns the fileType.
     */
    public String getFileType() {
        return this.fileType;
    }

    /**
     * @param fileType The fileType to set.
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return Returns the webAppRoot.
     */
    public String getWebAppRoot() {
        return this.webAppRoot;
    }

    /**
     * @param webAppRoot The webAppRoot to set.
     */
    public void setWebAppRoot(String webAppRoot) {
        this.webAppRoot = webAppRoot;
    }

    /**
     * @return Returns the webAppRootKey.
     */
    public String getWebAppRootKey() {
        return this.webAppRootKey;
    }

    /**
     * @param webAppRootKey The webAppRootKey to set.
     */
    public void setWebAppRootKey(String webAppRootKey) {
        this.webAppRootKey = webAppRootKey;
        this.setWebAppRoot(System.getProperty(this.getWebAppRootKey()));
    }

    private Log log = LogFactory.getLog(ProcessServiceImpl.class);

    public final static String WEB_APP_ROOT_KEY = "xiorkflow.root";

    public final static String BASE_DIR_NAME = "processes";

    public final static String DEFAULT_FILE_NAME = "default";

    public final static String FILE_TYPE = "xml";

    //
    private String webAppRootKey;

    private String baseDirName;

    private String defaultFileName;

    private String fileType;

    //
    private String webAppRoot;

	public ProcessResult addProcess(java.lang.Process process) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessResult deleteProcess(java.lang.Process process) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessResult updateProcess(java.lang.Process process) {
		// TODO Auto-generated method stub
		return null;
	}

}
