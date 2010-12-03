package com.ztesoft.component.job;

import org.quartz.JobDataMap;

/**
 * 封装任务实现调度接口
 * 
 * @author Administrator
 * 
 */
public interface IJobTrans {
	public abstract boolean isLocked();

	public abstract void setLocked(boolean isLocked);

	public abstract void process();

	public abstract void begionTrans() throws Exception;

	public abstract void commitTrans() throws Exception;

	public abstract void rollbackTrans();

	public abstract void setPerQueryMaxRow(int maxRow);

	public abstract int getPerQueryMaxRow();

	public abstract int getRedoTimes();

	public abstract void setRedoTimes(int redoTime);

	public abstract IJobTrans getInstance(JobDataMap jobConext);
}
