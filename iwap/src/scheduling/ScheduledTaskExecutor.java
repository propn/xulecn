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

/**

 */
public class ScheduledTaskExecutor implements Runnable{

    protected ScheduledTaskContext taskContext = null;
    protected SchedulerState schedulerState = null;

    public ScheduledTaskExecutor(ScheduledTaskContext taskContext, SchedulerState schedulerState) {
        this.taskContext = taskContext;
        this.schedulerState   = schedulerState;
    }

    public void run() {

        ScheduledTaskExecutionResult result = new ScheduledTaskExecutionResult();
        result.setScheduledStartTime(this.taskContext.getNextExecutionTime());
        result.setExecutionStartTime(System.currentTimeMillis());

        try{
            this.taskContext.getScheduledTask().execute(result);
        } catch (Throwable t){
            result.setErrorCause(t);
            result.setStatus(ScheduledTaskExecutionResult.ERROR);
            result.setMessage(t.getMessage());
        }
        finally {
            result.setExecutionEndTime(System.currentTimeMillis());

            this.schedulerState.schedule(this.taskContext, result);
        }
    }
}
