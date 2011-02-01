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


import java.util.Calendar;
import java.util.GregorianCalendar;

import org.leixu.iwap.scheduling.ISchedulingStrategy;
import org.leixu.iwap.scheduling.ScheduledTaskContext;


/**

 */
public class MonthlySchedulingStrategy implements ISchedulingStrategy {

    protected int date    = 0;
    protected int hour    = 0;
    protected int minute  = 0;
    protected int second  = 0;

    public MonthlySchedulingStrategy(int date) {
        this.date = date;
    }

    public MonthlySchedulingStrategy(int date, int hour) {
        this.date = date;
        this.hour = hour;
    }

    public MonthlySchedulingStrategy(int date, int hour, int minute) {
        this.date = date;
        this.hour = hour;
        this.minute = minute;
    }

    public MonthlySchedulingStrategy(int date, int hour, int minute, int second) {
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
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

        // if is first scheduling
        calendar.set(Calendar.DAY_OF_MONTH, this.date);
        calendar.set(Calendar.HOUR_OF_DAY , this.hour);
        calendar.set(Calendar.MINUTE      , this.minute);
        calendar.set(Calendar.SECOND      , this.second);
        calendar.set(Calendar.MILLISECOND , 0);
        

        //if time of day is ealier than now, set next execution time to next month.
        if(calendar.getTimeInMillis() <= System.currentTimeMillis()){
            calendar.add(Calendar.MONTH, 1);
        }

        return calendar.getTimeInMillis();
    }
}