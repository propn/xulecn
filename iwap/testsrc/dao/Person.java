package dao;

public class Person {
	private String name;
	private String threadId;
	private String threadName;

	public String getName() {
		return name;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

}
