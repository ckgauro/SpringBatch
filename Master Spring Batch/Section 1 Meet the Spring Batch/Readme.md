### 1. Introduction

# What is the Batch Processing?
It is defined as the processing of an amount of data without interaction or interruption.
The most common scenario for batch application is exporting data to file from one system and processing them in another.

Spring Boot Batch provides reusable functions that are essential in processing large volumes of records, including logging/tracing, transaction management, job processing statistics, job restart, skip, and resource management

### Explain Spring Batch framework.
Spring Batch is a lightweight, comprehensive batch framework designed to enable the development of robust batch applications vital for the daily operations of enterprise systems. Spring Batch builds upon the productivity, POJO-based development approach, and general ease of use capabilities people have come to know from the Spring Framework, while making it easy for developers to access and leverage more advanced enterprise services when necessary.


### Batch Processing
1.  ### Process large data Volume
    -   Able to handle large volumes of data to import, export or compute
2.  ### Automation
    -   Run without human interaction except for serious problem
3.  ### Robustness
    -   Handle invalid data without crashing or aborting prematurely
4.  ### Reliability 
    -   Keep track of what goes wrong and when

5.  ### Performance
    -   Must perform well to finish processing in a dedicated time window
    -   Avoid disturbing any other applications running simultaneously.


### When to use Spring Batch?
Consider an environment where users have to do a lot of batch processing. This will be quite different from a typical web application which has to work 24/7. But in classic environments it's not unusual to do the heavy lifting for example during the night when there are no regular users using your system. Batch processing includes typical tasks like reading and writing to files, transforming data, reading from or writing to databases, create reports, import and export data and things like that. Often these steps have to be chained together or you have to create more complex workflows where you have to define which job steps can be run in parallel or have to be run sequentially etc. That's where a framework like Spring Batch can be very handy. Spring Boot Batch provides reusable functions that are essential in processing large volumes of records, including logging/tracing, transaction management, job processing statistics, job restart, skip, and resource management. It also provides more advanced technical services and features that will enable extremely high-volume and high performance batch jobs though optimization and partitioning techniques.Simple as well as complex, high-volume batch jobs can leverage the framework in a highly scalable manner to process significant volumes of information.

### Spring Batch provides, among others, the next features:

-	Transaction management, to allow you to focus on business processing.
-	Chunk based processing, to process a large value of data by dividing it in small pieces.
-	Start/Stop/Restart/Skip/Retry capabilities, to handle non-interactive management of the process.
-	Web based administration interface (Spring Batch Admin), it provides an API for administering tasks.
-	Based on Spring framework, so it includes all the configuration options, including Dependency Injection.
-	Compliance with JSR 352: Batch Applications for the Java Platform.

### How Spring Batch works?

-   **step** - A Step that delegates to a Job to do its work. This is a great tool for managing dependencies between jobs, and also to modularise complex step logic into something that is testable in isolation. The job is executed with parameters that can be extracted from the step execution, hence this step can also be usefully used as the worker in a parallel or partitioned execution.
-   **ItemReader** - Strategy interface for providing the data. Implementations are expected to be stateful and will be called multiple times for each batch, with each call to read() returning a different value and finally returning null when all input data is exhausted. Implementations need not be thread-safe and clients of a ItemReader need to be aware that this is the case. A richer interface (e.g. with a look ahead or peek) is not feasible because we need to support transactions in an asynchronous batch.
-   **ItemProcessor** - Interface for item transformation. Given an item as input, this interface provides an extension point which allows for the application of business logic in an item oriented processing scenario. It should be noted that while it's possible to return a different type than the one provided, it's not strictly necessary. Furthermore, returning null indicates that the item should not be continued to be processed.
-   **ItemStreamWriter** - Basic interface for generic output operations. Class implementing this interface will be responsible for serializing objects as necessary. Generally, it is responsibility of implementing class to decide which technology to use for mapping and how it should be configured. The write method is responsible for making sure that any internal buffers are flushed. If a transaction is active it will also usually be necessary to discard the output on a subsequent rollback. The resource to which the writer is sending data should normally be able to handle this itself.


### Spring Batch
-   #### Based on Spring Framework Foundation
    -   Enterprise support
    -   Dependency injection
-   #### Batch-oriented processing
    -   Enforce the best practices when reading and writing the data
-   #### Ready to use component
    -   Out of boxes components allow you address common batch scenarios with minimum coding
-   #### Robustness and reliability
    -   Allows for declarative skipping and retry
    -   Enable restart after failure        
-   #### Lightweight and very easy to learn
-   #### POJO -based, it is testable!        
-   #### Usability , maintainability and extensibility
    -   Usability is about the code, easily extend an common component and add new features.
-   #### Scalability 

<img width="1000" height="800" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="images/SpringBatchFlow.png" style="border: 1px solid black"></img>


-------------
2. Hello Spring Batch
-   Create project
[Hello Project](https://github.com/ckgauro/SpringBatch/tree/master/Master%20Spring%20Batch/Section%201%20Meet%20the%20Spring%20Batch/project/helloworld)

-   **@EnableBatchProcessing** :The **@EnableBatchProcessing** annotation enables Spring Batch features and provides a base configuration for setting up batch jobs.
-   **@SpringBootApplication** : The @SpringBootApplication annotation comes from the Spring Boot project that provides standalone, production-ready, Spring-based applications. It specifies a configuration class that declares one or more Spring beans and also triggers auto-configuration and Spring’s component scanning.

-   **Tasklet** :A **org.springframework.batch.core.step.tasklet.Tasklet** supports a simple interface that has only one method, execute(), which is called repeatedly until it either returns RepeatStatus.FINISHED or throws an exception to signal a failure. Each call to the Tasklet is wrapped in a transaction.

```java
public interface Tasklet {

	/**
	 * Given the current context in the form of a step contribution, do whatever
	 * is necessary to process this unit inside a transaction. Implementations
	 * return {@link RepeatStatus#FINISHED} if finished. If not they return
	 * {@link RepeatStatus#CONTINUABLE}. On failure throws an exception.
	 * 
	 * @param contribution mutable state to be passed back to update the current
	 * step execution
	 * @param chunkContext attributes shared between invocations but not between
	 * restarts
	 * @return an {@link RepeatStatus} indicating whether processing is
	 * continuable. Returning {@code null} is interpreted as {@link RepeatStatus#FINISHED}
	 *
	 * @throws Exception thrown if error occurs during execution.
	 */
	@Nullable
	RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception;

}

```

-   **RepeatStatus** : It is enum Indicates that processing can continue or that processing is finished (either successful or unsuccessful)
-   **JobBuilderFactory** : org.springframework.batch.core.configuration.annotation.JobBuilderFactory
    Creates a job builder and initializes its job repository. Note that if the builder is used to create a @Bean definition then the name of the job and the bean name might be different.

```java
public class JobBuilderFactory {

	private JobRepository jobRepository;

	public JobBuilderFactory(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	/**
	 * Creates a job builder and initializes its job repository. Note that if the builder is used to create a &#64;Bean
	 * definition then the name of the job and the bean name might be different.
	 * 
	 * @param name the name of the job
	 * @return a job builder
	 */
	public JobBuilder get(String name) {
		JobBuilder builder = new JobBuilder(name).repository(jobRepository);
		return builder;
	}

}

```

-   **StepBuilderFactory** :org.springframework.batch.core.configuration.annotation.StepBuilderFactory 
Creates a step builder and initializes its job repository and transaction manager. Note that if the builder is used to create a &#64;Bean definition then the name of the step and the bean name might be different.



3. Review Hello World Batch

[Revise PPT]

4. Where is the sample code

