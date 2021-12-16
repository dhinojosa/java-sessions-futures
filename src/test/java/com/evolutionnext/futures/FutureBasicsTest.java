package com.evolutionnext.futures;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class FutureBasicsTest {

    /**
     * Demo 1: Basic Futures
     */
    @Test
    public void testBasicFuture() throws ExecutionException,
        InterruptedException {
        ExecutorService fixedThreadPool =
            Executors.newFixedThreadPool(5);

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Inside the future: " +
                    Thread.currentThread());
                System.out.println("Future priority: "
                    + Thread.currentThread().getPriority());
                Thread.sleep(5000);
                return 5 + 3;
            }
        };

        System.out.println("In test:" +
            Thread.currentThread());
        System.out.println("Main priority" +
            Thread.currentThread().getPriority());

        Future<Integer> future = fixedThreadPool.submit(callable);

        //This will block
        Integer result = future.get(); //block
        System.out.println("result = " + result);

        fixedThreadPool.shutdown();
    }

    /**
     * Challenge 1 : Parameterize a Future, that accepts a number and
     * adds 100
     */
    @Test
    public void testParameterizeFuture() throws ExecutionException,
        InterruptedException {

    }

    /**
     * Challenge 2 : Lazy a Future that returns 100 with a Lambda
     */
    @Test
    public void testLazyAFuture() throws ExecutionException,
        InterruptedException {
    }

    /**
     * Demo 2 : Async the Old Way
     */
    @Test
    public void testBasicFutureAsync() throws ExecutionException,
        InterruptedException {
        ExecutorService cachedThreadPool =
            Executors.newCachedThreadPool();

        Callable<Integer> callable = () -> {
            Thread.sleep(3000);
            return 5 + 3;
        };

        Future<Integer> future = cachedThreadPool.submit(callable);

        //This will not block
        while (!future.isDone()) {
            System.out.println("I am doing something else on thread: " +
                Thread.currentThread().getName());
        }

        Integer result = future.get();
        System.out.println("result = " + result);
    }


    /**
     * Demo 3: Futures with Parameters
     */
    @Test
    public void testGettingUrl() throws ExecutionException,
        InterruptedException {


    }

    /**
     * Demo 4: FutureTasks
     */
    @Test
    public void testFutureTasksUsingThreadPool()
        throws InterruptedException, ExecutionException {

        ExecutorService cachedThreadPool =
            Executors.newCachedThreadPool();

        FutureTask<Integer> futureTask =
            new FutureTask<>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(5000);
                    return 510 + 40;
                }
            });


        System.out.println("Running Future Task");
        cachedThreadPool.execute(futureTask);
        while (!futureTask.isDone()) {
            Thread.sleep(1000);
            System.out.println("Doing Something Else");
        }
        System.out.println(futureTask.get());
        Thread.sleep(1000);
    }


    @Test
    public void testFutureTaskAsRunnableInThread() throws ExecutionException,
        InterruptedException {

        FutureTask<Integer> futureTask =
            new FutureTask<Integer>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(5000);
                    return 510 + 40;
                }
            });

        Thread thread = new Thread(futureTask);
        thread.start();

        Integer result = futureTask.get();  //Block
        System.out.println("result = " + result);
        Thread.sleep(5000);
    }


    @Test
    public void testFutureTaskAsRunnableDirect() throws ExecutionException,
        InterruptedException {
        FutureTask<Integer> futureTask =
            new FutureTask<Integer>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(5000);
                    return 510 + 40;
                }
            });


        ExecutorService executorService =
            Executors.newFixedThreadPool(4);
        executorService.submit(futureTask);
        System.out.println("Submitted Future Task");
        Integer result = futureTask.get(); //Block!
        System.out.println("result = " + result);
        Thread.sleep(5000);
    }


    @Test
    public void testFutureTaskUsingExecute() throws ExecutionException,
        InterruptedException {
        FutureTask<Integer> futureTask =
            new FutureTask<Integer>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(5000);
                    return 510 + 40;
                }
            });

        ExecutorService service =
            Executors.newFixedThreadPool(3);
        System.out.println("Starting task!");
        service.execute(futureTask);
        System.out.println("result = " + futureTask.get()); //Block
        System.out.println("isDone = " + futureTask.isDone());
        Thread.sleep(5000);
    }
}
