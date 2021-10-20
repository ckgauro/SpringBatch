### 5. Task-Based Step Vs Chunk-Based Step

### Task-based Step vs Chunk-based Step
-   A tasklet-based step is the more simple of the two.
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

-   A chunk-based step is a bit more rigid in its structure, but is intended for item-based processing. Each chunk-based has up to three main parts:
    -   an ItemReader,
    -   an ItemProcessor
    -   an ItemWriter

### 6. Spring Batch Architecture

-   A Job as one to many steps
-   Each step simply perform a task
-   Each of which has exactly one ItemReader, one IemProcessor and One ItemWriter.
-   A job needs to be launched (with JobLauncher), and metadata about the currently running process needs to be stored (in JobRepository)

[Spring Batch](http://malsolo.com/blog4java/?p=260)


### 7. What is the Job, Job Instance and Job Execution?
-   A Job is an entity that encapsulates an entire batch process.
-   The job configuration contains.
-   The simple name of the job.
-   Definition and ordering of Step instances

```java
 @Bean
    public Job helloWorldJob() {
        return jobs.get("helloWorldJob").start(step1()).build();
        
    }
```
JobInstance
-   Job => JobInstance => JobExecution

    -   A Job Instance refers to the concept of a logic job run.
    -   The definition of a JobInstance has absolutely no bearing on the data to be loaded.

-   JobParameters
    -   How is one JobInstance distinguished from another? - JobParameters
-   JobInstance
    -   A Job Instance refers to the concept of a logic job run.
    -   The definition of a JobInstance has absolutely no bearing on the data to be loaded.    
-   JobExecution:
    -   Refers to the concept of a single attempt to run a Job.
    -   An execution may end in failure or success.   
    -   The JobInstance corresponding to a given execution is not considered to be complete unless the execution completes successfully.
    -   A storage (JobExecutionContext a map) stores what actually happened during a run. For example:
        -   Status, StartTime, endTime 

-----------------

### 8. What is the Step, Step Execution, and Execution Context

-   Job=>
        ->  Step->  StepExecution
        ->  JobInstance-> JobExecution ->StepExecution
-   Step
    -   A Step is a domain object that encapsulates an independent, sequential phase of a batch job,
    -   Every Job is composed entirely of one or more steps.
    -   A Step contains all of the information necessary to define and control the actual batch processing.
    -   A Step has an individual StepExecution that correlates with a unique JobExecution.


-   StepExecution
    -   A StepExecution represents a single attempt to execute a Step.
    -   A new StepExecution is created each time a Step is run, similar to JobExecution.
    -   If a step fails to execute because the step before it fails, no execution is persisted for it.
    -   A StepExecution is created only when its Step is actually started.

-   ExecutionContext
    -   An ExecutionContext represents a collection of key/value pairs that are persisted and controlled by the framework in order to allow developers a place to store persistent state that is scoped to a StepExecution object or JobExecution object.
    -   An ExecutionContext is a map
    - Allow you to pass values between steps    




9. Spring Batch Architecture - Summary



10. Put into Practice - understand the Spring Batch concept (Lab)



11. Create a Chun-Based Step



12. Create a Customized Item Read and Item Writer



13. Summary - Customized Item Reader and Item Writer



14. Using MySQL as Job Repository - Download and Inst…
14. Using MySQL as Job Repository - Download and Install MySQL



15. Download the Spring Batch Schema Creation script



16. Configure MySQL to the project



17. Add MySQL Driver to the project



18. Lab - Understand Job Instance



19. Lab -Understand Job Parameters



20. Lab - Make your Job Parameter different each time



21. Summary - Spring Batch Core Concept
