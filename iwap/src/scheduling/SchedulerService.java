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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import scheduling.strategy.DailySchedulingStrategy;
import scheduling.strategy.MonthlySchedulingStrategy;
import scheduling.strategy.TimeIntervalSchedulingStrategy;
import scheduling.strategy.WeeklySchedulingStrategy;

/**

 */
public class SchedulerService implements Runnable {

    protected boolean isStopped = false;

    protected long    sleepTime = 1 * 1000; //1 second

    protected SchedulerState schedulerState = new SchedulerState();
    protected Thread                          schedulerThread = null;
    protected ExecutorService threadPool    = null;

    public SchedulerService() {
        this.threadPool = Executors.newFixedThreadPool(1);
    }

    public synchronized long getSleepTime() {
        return sleepTime;
    }

    public synchronized void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    /*
    public void removeScheduledTask(ScheduledTaskContext taskContext){
        this.schedulerState.removeScheduledTask(taskContext);
    }
    */

    public void addScheduledTaskListener(IScheduledTaskListener taskListener){
        this.schedulerState.addScheduledTaskListener(taskListener);
    }

    public void removeScheduledTaskListener(IScheduledTaskListener taskListener){
        this.schedulerState.removeScheduledTaskListener(taskListener);
    }

    public void addScheduledTask(IScheduledTask task, ISchedulingStrategy schedulingStrategy)  {
        this.schedulerState.addScheduledTask(task, schedulingStrategy);
    }

    public void addScheduledTask(IScheduledTask task, ISchedulingStrategy schedulingStrategy, IFailureSchedulingStrategy failureSchedulingStrategy)  {
        this.schedulerState.addScheduledTask(task, schedulingStrategy, failureSchedulingStrategy);
    }


    public void addIntervalScheduledTask(IScheduledTask task, long timeInterval){
        this.schedulerState.addScheduledTask(task, new TimeIntervalSchedulingStrategy(timeInterval));
    }

    public void addIntervalScheduledTask(IScheduledTask task, long timeInterval, IFailureSchedulingStrategy failureSchedulingStrategy){
        this.schedulerState.addScheduledTask(task, new TimeIntervalSchedulingStrategy(timeInterval), failureSchedulingStrategy);
    }

    public void addDailyScheduledTask(IScheduledTask task, int hour, int minutes, int seconds){
        this.schedulerState.addScheduledTask(task, new DailySchedulingStrategy(hour, minutes, seconds));
    }

    public void addDailyScheduledTask(IScheduledTask task, int hour, int minutes, int seconds, IFailureSchedulingStrategy failureSchedulingStrategy){
        this.schedulerState.addScheduledTask(task, new DailySchedulingStrategy(hour, minutes, seconds), failureSchedulingStrategy);
    }

    public void addWeeklyScheduledTask(IScheduledTask task, int weekDay, int hour, int minutes, int seconds){
        this.schedulerState.addScheduledTask(task, new WeeklySchedulingStrategy(weekDay, hour, minutes, seconds));
    }

    public void addWeeklyScheduledTask(IScheduledTask task, int weekDay, int hour, int minutes, int seconds, IFailureSchedulingStrategy failureSchedulingStrategy){
        this.schedulerState.addScheduledTask(task, new WeeklySchedulingStrategy(weekDay, hour, minutes, seconds), failureSchedulingStrategy);
    }

    public void addMonthlyScheduledTask(IScheduledTask task, int dayOfMonth, int hour, int minutes, int seconds){
        this.schedulerState.addScheduledTask(task, new MonthlySchedulingStrategy(dayOfMonth, hour, minutes, seconds));
    }

    public void addMonthlyScheduledTask(IScheduledTask task, int dayOfMonth, int hour, int minutes, int seconds, IFailureSchedulingStrategy failureSchedulingStrategy){
        this.schedulerState.addScheduledTask(task, new MonthlySchedulingStrategy(dayOfMonth, hour, minutes, seconds), failureSchedulingStrategy);
    }




    public void run(){

        synchronized (this) {
            this.schedulerThread = Thread.currentThread();
        }

        while(! isStopped()){
            sleep();
            List<ScheduledTaskContext> readyTasks = this.schedulerState.getReadyTasks(System.currentTimeMillis());

            for(ScheduledTaskContext taskContext : readyTasks) {
                ScheduledTaskExecutor executor = new ScheduledTaskExecutor(taskContext, this.schedulerState);
                this.threadPool.execute(executor);
            }
        }
    }



    protected void sleep() {
        try {
            Thread.sleep(this.sleepTime);
        } catch (InterruptedException e) {
            // don't do anything. If interrupted, wake up.
        }
    }

    public synchronized void stop() {
        this.isStopped = false;
    }

    public synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized Thread getSchedulerThread() {
        return schedulerThread;
    }


}
