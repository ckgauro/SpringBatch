package com.gauro.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@EnableBatchProcessing
@SpringBootApplication
public class HelloworldApplication {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;


    public Tasklet helloWorldTasklet(String str) {
        return (new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println(str);
                Thread.sleep(1000);
                return RepeatStatus.FINISHED;
            }
        });
    }


    @Bean
    public Step step1() {
        return steps.get("step1")
                .tasklet(helloWorldTasklet("Step 1: Hello world"))
				//.tasklet(helloWorldTasklet("Step 2: This is new Step 1 "))
                .build();
    }

    @Bean
    public Step step2() {
        return steps.get("step2")
                .tasklet(helloWorldTasklet("Hello Step 2"))
                .build();
    }

    @Bean
    public Job helloWorldJob() {
        //return jobs.get("helloWorldJob").start(step1()).build();
        return jobs.get("helloWorldJob").start(step1()).next(step2()).build();
    }


    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
    }

}
