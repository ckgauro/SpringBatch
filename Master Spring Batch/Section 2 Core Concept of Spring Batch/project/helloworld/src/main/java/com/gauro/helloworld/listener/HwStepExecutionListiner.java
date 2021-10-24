package com.gauro.helloworld.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class HwStepExecutionListiner implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("This is from Before Step Execution: "+stepExecution.getJobExecution().getExecutionContext());
        System.out.println("In side Step -print paramerts :"+stepExecution.getJobExecution().getJobParameters());

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("This is from After Step Execution :"+stepExecution.getJobExecution().getExecutionContext());
        return null;
    }
}
