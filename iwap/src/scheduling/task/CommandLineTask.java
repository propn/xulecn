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



package scheduling.task;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import scheduling.IScheduledTask;
import scheduling.ScheduledTaskExecutionResult;

/**

 */
public class CommandLineTask implements IScheduledTask {


    protected String    commandLine = null;
    protected Exception exception   = null;
    protected String    output      = null;
    protected int       exitCode    = 0;

    public CommandLineTask(String commandLine) {
        this.commandLine = commandLine;
    }

    /*
    public String getCommandLine() {
        return commandLine;
    }

    public String getOutput() {
        return output;
    }

    public int getExitCode(){
        return this.exitCode;
    }
    */

    public void execute(ScheduledTaskExecutionResult result) throws Throwable {
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(this.commandLine);
            StringBuilder builder = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = reader.readLine();
            while(line != null){
                //System.out.println(line);
                builder.append(line);
                builder.append('\n');
                line = reader.readLine();
            }
            this.output = builder.toString();
            process.waitFor();
            this.exitCode = process.exitValue();
            reader.close();
        } catch (IOException e) {
            this.exception = e;
        } catch (InterruptedException e) {
            this.exception = e;
        }

        if(this.exitCode == 0){
            result.setStatus(ScheduledTaskExecutionResult.SUCCESS);
        } else {
            result.setStatus(ScheduledTaskExecutionResult.ERROR);
        }

        result.setMessage(this.output);
        result.setErrorCause(this.exception);
    }
}

