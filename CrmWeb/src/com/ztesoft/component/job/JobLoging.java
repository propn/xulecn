package com.ztesoft.component.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.component.job.vo.CrmJobVO;

public class JobLoging {
	private static JobLoging logger = null;

	private String JobLogingPath = "./logs/";

	private String JobFileMaxSize = "";

	private static long FileMaxSize = 1 * 1000 * 1000;

	private static String fileName = "job.log";

	private static int maxBackupIndex = 10;

	private JobLoging() {
		super();
	}

	public static JobLoging getLogger() {
		if (logger == null) {
			logger = new JobLoging();
			logger.rollOver();
		}
		return logger;
	}

	public static JobLoging getLogger(String jobLogingPath, String jobFileMaxSize) {
		if (logger == null) {

			logger = new JobLoging();

			if (jobFileMaxSize.equalsIgnoreCase("")) {
				logger.setJobFileMaxSize("1");
			} else {
				logger.setJobFileMaxSize(jobFileMaxSize);
			}
			logger.setJobLogingPath(jobLogingPath);

			logger.rollOver();
		}

		return logger;
	}

	public synchronized void loging(String msg) {
		if (msg == null) {
			return;
		}
		File logDir = new File(JobLogingPath);
		if(!logDir.exists()) logDir.mkdir();
		File file = new File(JobLogingPath, fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		} else {
			FileInputStream inf = null;
			try {
				inf = new FileInputStream(file);

				if (inf.available() > FileMaxSize) {
					rollOver();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inf != null) {
					try {
						inf.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		try {
			FileOutputStream out = new FileOutputStream(file, true);
			String logger = "[" + DateFormatUtils.getFormatedDateTime() + "]\t\t" + msg + "\n";
			out.write(logger.getBytes());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void loging(CrmJobVO crmJob, String msg) {
		String logger = crmJob.getJobName() + "\t\t" + msg + "";
		if (msg.length() < 200) {
			CrmJobBo.jobLoging(crmJob, msg);
		} else {
			CrmJobBo.jobLoging(crmJob, msg.substring(0, 200));
		}
		loging(logger);
	}

	public String getJobFileMaxSize() {
		return JobFileMaxSize;
	}

	public synchronized void setJobFileMaxSize(String jobFileMaxSize) {
		JobFileMaxSize = jobFileMaxSize;
		FileMaxSize = Long.parseLong(JobFileMaxSize) * 1000 * 1000;
	}

	public String getJobLogingPath() {
		return JobLogingPath;
	}

	public synchronized void setJobLogingPath(String jobLogingPath) {
		JobLogingPath = jobLogingPath;
	}

	public synchronized void rollOver() {
		if (maxBackupIndex > 0) {
			File file = new File(JobLogingPath, fileName + '.' + maxBackupIndex);
			if (file.exists())
				file.delete();
			File target;
			for (int i = maxBackupIndex - 1; i >= 1; i--) {
				file = new File(JobLogingPath, fileName + "." + i);
				if (file.exists()) {
					target = new File(JobLogingPath, fileName + '.' + (i + 1));
					file.renameTo(target);
				}
			}

			target = new File(JobLogingPath, fileName + "." + 1);

			file = new File(JobLogingPath, fileName);
			file.renameTo(target);
		}

	}
}
