package com.gauro.helloworld.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class HwJobExecutionListener  implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before starting the Job - Job Name :"+ jobExecution.getJobInstance().getJobName());
        System.out.println("Before starting the Job:"+ jobExecution.getExecutionContext().toString());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("after starting the Job - Job Execution Context "+ jobExecution.getExecutionContext().toString());

    }
}
