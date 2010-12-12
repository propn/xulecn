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

 TODO see if the notifyXXX methods and the scheduleXXX methods could be simplified, since much of the logic in these methods is the same.
 */
public class SchedulerState {
    protected List<ScheduledTaskContext>  waitingTasks   = new ArrayList<ScheduledTaskContext>();
    protected List<ScheduledTaskContext>  executingTasks = new ArrayList<ScheduledTaskContext>();

    protected List<IScheduledTaskListener> taskListeners = new ArrayList<IScheduledTaskListener>();


    public synchronized void addScheduledTaskListener(IScheduledTaskListener listener){
        this.taskListeners.add(listener);
    }

    public synchronized void removeScheduledTaskListener(IScheduledTaskListener listener){
        this.taskListeners.remove(listener);
    }

    public synchronized void addScheduledTask(IScheduledTask task, ISchedulingStrategy schedulingStrategy)  {
        addScheduledTask(task, schedulingStrategy, null);
    }

    public synchronized void addScheduledTask(IScheduledTask task, ISchedulingStrategy schedulingStrategy,
                                              IFailureSchedulingStrategy failureSchedulingStrategy)  {
        ScheduledTaskContext taskContext = new ScheduledTaskContext();
        taskContext.setScheduledTask(task);
        taskContext.setSchedulingStrategy(schedulingStrategy);
        taskContext.setFailureSchedulingStrategy(failureSchedulingStrategy);
        this.waitingTasks.add(taskContext);

        schedule(taskContext, null);
    }

    public synchronized void removeScheduledTask(ScheduledTaskContext taskContext){
        this.waitingTasks.remove(taskContext);
        this.executingTasks.remove(taskContext); // in case task is currently being executed.
    }


    public synchronized List<ScheduledTaskContext> getReadyTasks(long timeNow) {
        List<ScheduledTaskContext> readyTasks = new ArrayList<ScheduledTaskContext>();
        
        //find tasks which are ready to be executed.
        for(ScheduledTaskContext context : this.waitingTasks){
            if(context.nextExecutionTime <= timeNow){
                readyTasks.add(context);
            }
        }

        //move ready tasks out of wating task list, into executing task list, to avoid double execution.
        for(ScheduledTaskContext context : readyTasks){
            this.waitingTasks.remove(context);
            this.executingTasks.add(context);
        }

        return readyTasks;
    }

    public synchronized void schedule(ScheduledTaskContext taskContext, ScheduledTaskExecutionResult result){

        //if result == null, then the task has not yet been executed.
        if(result != null){
            //done inhere, because this needs to be done in a synchronized block.
            taskContext.getExecutionResults().add(result);
            notifyListenersOfExecution(taskContext);
        }

        long nextExecutionTime = -1;
        if(    result.getStatus() == ScheduledTaskExecutionResult.ERROR
            && taskContext.getFailureSchedulingStrategy() != null
            && taskContext.getFailureSchedulingStrategy().canReschedule(taskContext)) {

            nextExecutionTime = taskContext.getFailureSchedulingStrategy().scheduleTask(taskContext);
            notifyListenersOfFailureScheduling(taskContext);
        } else {
            nextExecutionTime = taskContext.getSchedulingStrategy().scheduleTask(taskContext);
            notifyListenersOfScheduling(taskContext);
        }

        taskContext.setNextExecutionTime(nextExecutionTime);



    }


    private void moveFromExecutingToWaitingList(ScheduledTaskContext taskContext) {

        // If an executing task is not present in the executing task list,
        // it has either been removed, or has not yet been executed for the first time,
        // and should not be moved back into the waiting list.
        if(this.executingTasks.contains(taskContext)){
            this.executingTasks.remove(taskContext);
            this.waitingTasks.add(taskContext);
        }
    }

    protected void notifyListenersOfExecution(ScheduledTaskContext taskContext){
        for(IScheduledTaskListener taskListener : this.taskListeners){
            taskListener.taskExecuted(taskContext);
        }
    }

    protected void notifyListenersOfScheduling(ScheduledTaskContext taskContext){
        for(IScheduledTaskListener taskListener : this.taskListeners){
            taskListener.taskScheduled(taskContext);
        }
    }

    protected void notifyListenersOfFailureScheduling(ScheduledTaskContext taskContext){
        for(IScheduledTaskListener taskListener : this.taskListeners){
            taskListener.taskFailureScheduled(taskContext);
        }
    }

}
