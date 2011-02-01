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



package org.leixu.iwap.scheduling.strategy;

import org.leixu.iwap.scheduling.IFailureSchedulingStrategy;
import org.leixu.iwap.scheduling.ScheduledTaskContext;
import org.leixu.iwap.scheduling.ScheduledTaskExecutionResult;

/**

 */
public class RetryFailureSchedulingStrategy implements IFailureSchedulingStrategy {

    protected long timeBetweenRetries = 0;
    protected int  maxRetryCount      = 0;

    public RetryFailureSchedulingStrategy(long timeBetweenRetries, int maxRetryCount) {
        this.timeBetweenRetries = timeBetweenRetries;
        this.maxRetryCount = maxRetryCount;
    }

    public boolean canReschedule(ScheduledTaskContext taskContext) {
        //determine how many failed retries have occurred now.
        int currentRetryCount = determineCurrentRetryCount(taskContext);
        return currentRetryCount < this.maxRetryCount;
    }

    public long scheduleTask(ScheduledTaskContext taskContext) {
        return taskContext.getNextExecutionTime() + this.timeBetweenRetries;
    }

    /**
     *  This method iterates backwards through execution history (results) and increment currentRetryCount
        //until a successful execution is met.

     * @param taskContext
     * @return
     */
    private int determineCurrentRetryCount(ScheduledTaskContext taskContext) {
        int currentRetryCount = 0;

        for(int i = taskContext.getExecutionResults().size() -1; i >=0; i--){
            ScheduledTaskExecutionResult latestResult = taskContext.getExecutionResults().get(i);
            if(latestResult.getStatus() == ScheduledTaskExecutionResult.ERROR){
                currentRetryCount++;
            } else {
                break;
            }
        }

        return currentRetryCount;
    }
}
