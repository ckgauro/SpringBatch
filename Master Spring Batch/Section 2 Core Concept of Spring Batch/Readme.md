### 5. Task-Based Step Vs Chunk-Based Step

### Task-based Step vs Chunk-based Step

- A tasklet-based step is the more simple of the two.
  - It is used for initialization of a procedure, copy a file, sending a notification to so on.

```java
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

```

- A chunk-based step is a bit more rigid in its structure, but is intended for item-based processing. Each chunk-based has up to three main parts:

  - an ItemReader,
  - an ItemProcessor
  - an ItemWriter

  - chunk base to step generally doing like reading a database process it and writing to the file or reading a file and process or writing a database or message

reading a message.

### 6. Spring Batch Architecture

- A Job as one to many steps
- Each step simply perform a task
- Each of which has exactly one ItemReader, one IemProcessor and One ItemWriter.
- A job needs to be launched (with JobLauncher), and metadata about the currently running process needs to be stored (in JobRepository)

[Spring Batch](http://malsolo.com/blog4java/?p=260)

<img  Job>

- **Job Repository** - The job repository is a main component that is responsible for maintaining the state of a job as well as various processing metrics such as start time, end time, status, number of items being read, write, etc.. .Typically, a job repository is implemented by a relational database. **job repository** is shared by virtually by all the main components in our Hello world example,

- **JobLauncher** - The next one is a job lunch, a job lunch, job launcher is responsible for the execution of the job? Joe launcher does not only calling job.execute to launch a job, it also validating a job should be run, should be executed. If a job being failed, or can a job be a restarted, also validating the job of parameters. All of these tasks, however, depend on implementation. In the spring bot, but if we use the spring patch with spring boot, this component, the job launcher, you don't need to direct your work with because spring boot to provide a facility to launch a job out of box.

- **Job** - spring boot to provide a facility to launch a job out of box. One's job is a launched. Right, the job executes each step. Within a job. there are multiple steps, jobs will execute step one after another. as each step being executed. Job repository get updated. with the current state. What a step is executed, its current state status, how many items being read, processed and write

- **Step** -one step can only have one item reader, one item processor and one item writer there, a job need to be launched and a metadata account is running, a process needs to be stored in a job repository.

### 7. What is the Job, Job Instance and Job Execution?

- A Job is an entity that encapsulates an entire batch process.
- The job configuration contains.
- The simple name of the job.
- Definition and ordering of Step instances

```java
 @Bean
    public Job helloWorldJob() {
        return jobs.get("helloWorldJob").start(step1()).build();

    }
```

JobInstance

- Job => JobInstance => JobExecution

  - A Job Instance refers to the concept of a logic job run.
  - The definition of a JobInstance has absolutely no bearing on the data to be loaded.

- JobParameters
  - How is one JobInstance distinguished from another? - JobParameters
- JobInstance
  - A Job Instance refers to the concept of a logic job run.
  - The definition of a JobInstance has absolutely no bearing on the data to be loaded.
- JobExecution:
  - Refers to the concept of a single attempt to run a Job.
  - An execution may end in failure or success.
  - The JobInstance corresponding to a given execution is not considered to be complete unless the execution completes successfully.
  - A storage (JobExecutionContext a map) stores what actually happened during a run. For example:
    - Status, StartTime, endTime

---

### 8. What is the Step, Step Execution, and Execution Context

<img step>
-   Job=>
        ->  Step->  StepExecution
        ->  JobInstance-> JobExecution ->StepExecution
-   **Step**
    -   A Step is a domain object that encapsulates an independent, sequential phase of a batch job,
    -   Every Job is composed entirely of one or more steps.
    -   A Step contains all of the information necessary to define and control the actual batch processing.
    -   A Step has an individual StepExecution that correlates with a unique JobExecution.

    So let's look at what is the step?
    A step is a domain object that represents independent sequential phase of a batch job write, a sequential independent phase of a batch job, a job can have multiple steps. Therefore, every job is compose either one or many steps. And the steps it contains, all the information that necessary to define and control the actual batch processing. The step can be a simple or complex, it depends on developers. A simple step might just load data from the database requires a little or no effort or no code, right And more complex. step may have a complicated business rules inside as part of processing. For each step of each job instance, they all have a step execution and each step execution always linked back to a unique job execution. Let's look at the step execution, Every step has individual step execution, a link back to the job execution.

- **StepExecution**

  - A StepExecution represents a single attempt to execute a Step.
  - A new StepExecution is created each time a Step is run, similar to JobExecution.
  - If a step fails to execute because the step before it fails, no execution is persisted for it.
  - A StepExecution is created only when its Step is actually started.

  So what is the step execution, right? It represents a single attempt to execute a step.A new step execution is critical when each time when a step is run similar to execution. However, if a step failed. Because of the previous step, a failed no execution will be created, no execution will be persisted, right. So step execution always create. Always created when the step is actually started, so that means when step failed because of previous step were failed this step execution will not be created. Step execution represented by a object called a step execution class, each execution contains a reference, of course, on this step and the job and the job execution and the transaction related data, such as a commit, rollback counts start, end time, etc. Additionally, each step execution always contains the execution context, which contains that data that developer may be interested.

- **ExecutionContext**

  - An ExecutionContext represents a collection of key/value pairs that are persisted and controlled by the framework in order to allow developers a place to store persistent state that is scoped to a StepExecution object or JobExecution object.
  - An ExecutionContext is a map
  - Allow you to pass values between steps

  So the last one, what is execution context, we mentioned these were a couple of times in the previous. In previous slide, execution context is basically a map, it contains a collection of key and value pairs that persist and controlled by the framework that allows developer to place and store the persistent state, that is the scope either in the scope of state execution or Job execution objects. So in spring batch, there are two execution contexts. one belongs to . step scope Another one belongs to job execution, Step execute execution context always is a map allow you pass the values between the steps. If you want to pass value between the steps, which one execution context are you going to use? You're going to use job execution context , not the step Execution context because step execution will be disappeared after the step is complete. So job execution. context will be perceived, will be always available in a course during the course of execution.

9. Spring Batch Architecture - Summary

So let's review this again, the spring batch architecture, so the first one would be job repository or job repositories is a persistence, persistence mechanism that allow the all the stereotype. We mentioned a job, lunchers, that's all going to be persisted into this job repository. it provides a create update, the delete operation for a job launcher or steps and jobs implementations. When Job is the first launched, job execution will be obtained from a job repository and during the course of execution, a step execution and job execution will be passing along the data from the context to the job repository. So when using Java configuration enable batch processing annotation to provide a job repository is a one of components automatically out of box. Next one would be job lunch, job lunch and represents a single interface. The Next one will be the job launcher in a job with a given job parameters. So we have a job, we have a job.Instance, each instance have multiple job execution's . Each job have multiple steps where each step have a step execution, every step execution, have a step execution context. So the execution context could be used passing a store and pass back in the values between the steps or in a store the value back to the job repository. And there are two type of steps is the task based step. One is chunk based step That chunk of staff generally has three items. a item reader, item processor. an item writer

Until now, you have all the knowledge of the spring batch architecture right now when you look at example,how how these terminologies get implemented into the spring batch.

------------
10. Put into Practice - understand the Spring Batch concept (Lab) Ongoing

Hello Wold Example
com.swt.helloworld.config

-   **HwJobExecutionListener** : It is job execution which execute Job
    - 

```java
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

```
-   Explanation :
    -   getJobInstance




--------------
11. Create a Chun-Based Step

<img Chunk>
[revise video]

12. Create a Customized Item Read and Item Writer

InMemeReader: It reads one item at time.

13. Summary - Customized Item Reader and Item Writer

14. Using MySQL as Job Repository - Download and Inst…
15. Using MySQL as Job Repository - Download and Install MySQL

16. Download the Spring Batch Schema Creation script

17. Configure MySQL to the project

18. Add MySQL Driver to the project

19. Lab - Understand Job Instance

20. Lab -Understand Job Parameters

21. Lab - Make your Job Parameter different each time

22. Summary - Spring Batch Core Concept
