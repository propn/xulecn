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



package scheduling.strategy;


import java.util.Calendar;
import java.util.GregorianCalendar;

import scheduling.ISchedulingStrategy;
import scheduling.ScheduledTaskContext;

/**

 */
public class WeeklySchedulingStrategy implements ISchedulingStrategy {

    protected int weekDay = 0;
    protected int hour    = 0;
    protected int minute  = 0;
    protected int second  = 0;

    public WeeklySchedulingStrategy(int weekDay) {
        this.weekDay = weekDay;
    }

    public WeeklySchedulingStrategy(int weekDay, int hour) {
        this.weekDay = weekDay;
        this.hour = hour;
    }

    public WeeklySchedulingStrategy(int weekDay, int hour, int minute) {
        this.weekDay = weekDay;
        this.hour = hour;
        this.minute = minute;
    }

    public WeeklySchedulingStrategy(int weekDay, int hour, int minute, int second) {
        this.weekDay = weekDay;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public long scheduleTask(ScheduledTaskContext taskContext) {
        Calendar calendar = new GregorianCalendar();

        calendar.set(Calendar.DAY_OF_WEEK, this.weekDay);
        calendar.set(Calendar.HOUR_OF_DAY, this.hour);
        calendar.set(Calendar.MINUTE     , this.minute);
        calendar.set(Calendar.SECOND     , this.second);
        calendar.set(Calendar.MILLISECOND , 0);
        

        //if time of day is ealier than now, set next execution time to next week.
        if(calendar.getTimeInMillis() <= System.currentTimeMillis()){
            calendar.add(Calendar.DAY_OF_MONTH, 7);
        }

        return calendar.getTimeInMillis();
    }
}