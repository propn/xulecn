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



package org.leixu.iwap.scheduling.task;


import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.IOException;

import org.leixu.iwap.scheduling.IScheduledTask;
import org.leixu.iwap.scheduling.ScheduledTaskExecutionResult;


/**

 */
public class UrlTask implements IScheduledTask {

    protected String url = null;

    public UrlTask(String url) {
        this.url = url;
    }

    public void execute(ScheduledTaskExecutionResult result) throws Throwable {

        try {
            URL url = new URL(this.url);
            URLConnection connection = url.openConnection();

            InputStream inputStream = connection.getInputStream();

            StringBuilder builder = new StringBuilder();

            int data = inputStream.read();
            while(data != -1){
                builder.append((char) data);
                data = inputStream.read();
            }
            inputStream.close();

            result.setStatus(ScheduledTaskExecutionResult.SUCCESS);
            result.setMessage(builder.toString());
        } catch (IOException e) {
            result.setStatus(ScheduledTaskExecutionResult.ERROR);
            result.setErrorCause(e);
            result.setMessage(e.getMessage());
        } 

    }
}
