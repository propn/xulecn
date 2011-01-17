/*
    Copyright 2007-2008 Jenkov Development

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/



package org.leixu.iwap.scheduling;

/**

 */
public class ScheduledTaskExecutionResult {

    public static final int SUCCESS             =  0;
    public static final int ERROR               =  2;


    //protected ScheduledTaskContext taskContext   = null;    //necessary?
    protected int                       status             = SUCCESS;
    protected String                    message            = null;
    protected Throwable                 errorCause         = null;
    protected long                      scheduledStartTime = 0;
    protected long                      executionStartTime = 0;
    protected long                      executionEndTime   = 0;


//    public ScheduledTaskContext getTaskRegistration() {
//        return taskContext;
//    }
//
//    public void setTaskRegistration(ScheduledTaskContext taskContext) {
//        this.taskContext = taskContext;
//    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(Throwable errorCause) {
        this.errorCause = errorCause;
    }

    public long getExecutionStartTime() {
        return executionStartTime;
    }

    public void setExecutionStartTime(long executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public long getExecutionEndTime() {
        return executionEndTime;
    }

    public void setExecutionEndTime(long executionEndTime) {
        this.executionEndTime = executionEndTime;
    }

    public long getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledStartTime(long scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }
}
