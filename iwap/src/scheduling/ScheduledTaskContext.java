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



package scheduling;

import java.util.List;
import java.util.ArrayList;

/**

 */
public class ScheduledTaskContext implements Comparable<ScheduledTaskContext> {

    protected IScheduledTask                     scheduledTask             = null;
    protected ISchedulingStrategy                schedulingStrategy        = null;
    protected IFailureSchedulingStrategy         failureSchedulingStrategy = null;
    protected long                               nextExecutionTime         = 0;

    protected List<ScheduledTaskExecutionResult> executionResults          = new ArrayList<ScheduledTaskExecutionResult>();


    public IScheduledTask getScheduledTask() {
        return scheduledTask;
    }

    public void setScheduledTask(IScheduledTask scheduledTask) {
        this.scheduledTask = scheduledTask;
    }

    public ISchedulingStrategy getSchedulingStrategy() {
        return schedulingStrategy;
    }

    public void setSchedulingStrategy(ISchedulingStrategy schedulingStrategy) {
        this.schedulingStrategy = schedulingStrategy;
    }

    public IFailureSchedulingStrategy getFailureSchedulingStrategy() {
        return failureSchedulingStrategy;
    }

    public void setFailureSchedulingStrategy(IFailureSchedulingStrategy failureSchedulingStrategy) {
        this.failureSchedulingStrategy = failureSchedulingStrategy;
    }

    public List<ScheduledTaskExecutionResult> getExecutionResults() {
        return executionResults;
    }

    public void setExecutionResults(List<ScheduledTaskExecutionResult> executionResults) {
        this.executionResults = executionResults;
    }

     public int compareTo(ScheduledTaskContext other) {
        if(this.nextExecutionTime < other.nextExecutionTime) return -1;
        if(this.nextExecutionTime > other.nextExecutionTime) return  1;
        return 0;
    }

    public synchronized long getNextExecutionTime() {
        return nextExecutionTime;
    }


    /**
     * Do not call this method directly yourself. The SchedulerState calls this method when it
     * schedules / reschedules this task.
     *
     * @param nextExecutionTime
     */
    public synchronized void setNextExecutionTime(long nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
    }
}
