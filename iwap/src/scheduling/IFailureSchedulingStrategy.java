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
    Schedules a task after a failed execution of it. For instance, you might want
 to retry 3 times with 5 minutes in between.
 */
public interface IFailureSchedulingStrategy {

    /**
     * Returns true if this IFailureSchedulingStrategy implementation can reschedule
     * this task. For instance, in case a failure strategy is to retry 3 times, then
     * give up and reschedule normally, this method would read the execution results
     * and see if 3 retries had already been attempted, or not.
     *
     * @param taskContext The task registration of the task to reschedule.
     * @return
     */
    public boolean canReschedule(ScheduledTaskContext taskContext);
    public long    scheduleTask(ScheduledTaskContext taskContext);

}
