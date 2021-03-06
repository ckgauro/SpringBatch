package com.gauro.helloworld.config;

import com.gauro.helloworld.listener.HwJobExecutionListener;
import com.gauro.helloworld.listener.HwStepExecutionListiner;
import com.gauro.helloworld.processor.InMemeItemProcessor;
import com.gauro.helloworld.reader.InMemReader;
import com.gauro.helloworld.writer.ConsoleItemWriter;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BatchCondifguration {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;
    @Autowired
    private HwJobExecutionListener hwJobExecutionListener;

    @Autowired
    private HwStepExecutionListiner hwStepExecutionListiner;

    @Autowired
    private InMemeItemProcessor inMemeItemProcessor;

    public Tasklet helloWorldTasklet(){
        return (new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Hello World");
                return RepeatStatus.FINISHED;
            }
        });
    }

    @Bean
    public Step step1(){
        return steps.get("step1").listener(hwStepExecutionListiner).tasklet(helloWorldTasklet())
                .build();
    }

    @Bean
    public InMemReader reader(){
        return  new InMemReader();
    }

    @Bean
    public Step step2(){
        return steps.get("step2").<Integer,Integer>chunk(3)
                .reader(reader())
                .processor(inMemeItemProcessor)
                .writer(new ConsoleItemWriter())
                .build();
    }

    @Bean
    public Job hellowWorldJob(){
        return jobs.get("hellowWorldJob")
                .listener(hwJobExecutionListener)
                .start(step1())
                .next(step2())
                .build();

    }





}
