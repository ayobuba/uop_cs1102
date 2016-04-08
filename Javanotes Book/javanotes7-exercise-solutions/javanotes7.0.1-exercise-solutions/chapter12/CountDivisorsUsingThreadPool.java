import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This program finds the number in the range 1 to some maximum that has the 
 * largest number of divisors.  It prints that number and the number of divisors 
 * that it has.  Note that there might be several numbers that have the maximum
 * number of divisors.  Only one of them is output.
 * 
 * The program's work is divided into a large number of tasks that are executed
 * by a thread pool.  Each task consists of finding the maximum number of 
 * divisors among a sequence of 1000 integers.
 */
public class CountDivisorsUsingThreadPool {

    /**
     * The upper limit of the range of integers that is to be tested.
     * (This must be a fairly large multiple of 1000 for the thread
     * pool load-balancing strategy to be effective.)
     */
    private final static int MAX = 100000;

    /**
     * A queue to hold the tasks.  Tasks are represented as objects
     * of type Task, a nested class that is defined below.  Note
     * that queue operations must be synchronized because the
     * queue is used by multiple threads.  A ConcurrentLinkedQueue
     * handles synchronization internally.
     */
    private static ConcurrentLinkedQueue<Task> taskQueue;

    /**
     * A queue to hold the results from the tasks.  Results
     * are defined by the nested class, Result, which is defined
     * below.  This is a blocking queue since the thread
     * that takes results from the queue should block when
     * the queue is empty until a result becomes available.
     * (Note:  The Task class could have been used to represent
     * results as well; I am using a separate Result class
     * for clarity in this example.)
     */
    private static LinkedBlockingQueue<Result> resultQueue;
    
    
    /**
     * A class to represent the task of finding the number in
     * a given range of integers that has the largest number of
     * divisors.  The range is specified in the constructor.
     * The task is executed when the compute() method is 
     * called.  At the end of the compute() method, a Result
     * object is created to represent the results from this
     * task, and the result object is added to resultQueue.
     */
    private static class Task {
        int min, max; // Start and end of the range of integers for this task.
        Task(int min, int max) {
            this.min = min;
            this.max = max;
        }
        public void compute() {
            int maxDivisors = 0;
            int whichInt = 0;
            for (int i = min; i < max; i++) {
                int divisors = countDivisors(i);
                if (divisors > maxDivisors) {
                    maxDivisors = divisors;
                    whichInt = i;
                }
            }
            resultQueue.add( new Result(maxDivisors, whichInt) );
        }
    }
    
    
    /**
     * A class to represent the result from one task.  The
     * result consists of the maximum number of divisors in
     * the range of integers assigned to that task, and the
     * integer in the range that gave the maximum number of
     * divisors.
     */
    private static class Result {
        int maxDivisorFromTask;  // Maximum number of divisors found.
        int intWithMaxFromTask;  // Which integer gave that maximum number.
        Result(int maxDivisors, int whichInt) {
            maxDivisorFromTask = maxDivisors;
            intWithMaxFromTask = whichInt;
        }
    }
    
    
    /**
     * A thread belonging to this class counts the number of divisors for all
     * the integers in an assigned range of integers.  The range is specified
     * in the constructor.  The thread finds the integer in the range that 
     * has the largest number of divisors, and a number that has that many
     * divisors.  At the end of its computation, the thread reports its answer 
     * by calling the report() method.
     */
    private static class CountDivisorsThread extends Thread {
        public void run() {
            while (true) {
                Task task = taskQueue.poll();
                if (task == null)
                    break;
                task.compute();
            }
        }
    }
    

    /**
     * Finds the number in the range 1 to MAX that has the largest number of
     * divisors, dividing the work into tasks that will be executed by threads
     * in a thread pool.  This method creates the task and result queues.
     * It adds all the tasks to the task queue.  Then it creates the threads
     * for the thread pool and starts them.  (Note that this must be done
     * AFTER all the tasks are in the task queue, since the threads exit
     * when they see an empty queue.)  Finally, it reads results from
     * the result queue and combines them to get the overall answer.
     * @param numberOfThreads the number of threads in the thread pool. 
     */
    private static void countDivisorsWithThreads(int numberOfThreads) {
        
        System.out.println("\nCounting divisors using " + 
                                            numberOfThreads + " threads...");
        
        /* Create the queues and the thread pool, but don't start
         * the threads yet. */
        
        long startTime = System.currentTimeMillis();
        resultQueue = new LinkedBlockingQueue<Result>();
        
        taskQueue = new ConcurrentLinkedQueue<Task>();
        CountDivisorsThread[] workers = new CountDivisorsThread[numberOfThreads];
        for (int i = 0; i < workers.length; i++)
            workers[i] = new CountDivisorsThread();

        /* Create the tasks and add them to the task queue.  Each
         * task consists of a range of 1000 integers, so the number of
         * tasks is (MAX+999)/1000.  (The "+999"  gives the correct number
         * of tasks when MAX is not an exact multiple of 1000.  The last
         * task in that case will consist of the last (MAX%1000)) ints. */
        
        int numberOfTasks = (MAX + 999) / 1000;
        for (int i = 0; i < numberOfTasks; i++) {
            int start = i*1000 + 1;
            int end = (i+1)*1000;
            if (end > MAX)
                end = MAX;
            //System.out.println(start + " " + end);  // for testing
            taskQueue.add( new Task(start,end) );
        }
        
        /* Now that the tasks are in the task queue, start the threads. */
        
        for (int i = 0; i < numberOfThreads; i++)
            workers[i].start();
        
        /* The threads will execute the tasks and results will be placed
         * into the result queue.  This method now goes on to read all
         * the results from the result queue and combine them to give
         * the overall answer. */

        int maxDivisorCount = 0;         // Over maximum found by any task.
        int intWithMaxDivisorCount = 0;  // Which integer gave that maximum?
        for (int i = 0; i < numberOfTasks; i++) {
            try {
                Result result = resultQueue.take();
                if (result.maxDivisorFromTask > maxDivisorCount) { // new maximum.
                    maxDivisorCount = result.maxDivisorFromTask;
                    intWithMaxDivisorCount = result.intWithMaxFromTask;
                }
            }
            catch (InterruptedException e) {
            }
        }
        
        /* Report the results. */
        
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("\nThe largest number of divisors " + 
                "for numbers between 1 and " + MAX + " is " + maxDivisorCount);
        System.out.println("An integer with that many divisors is " + 
                intWithMaxDivisorCount);
        System.out.println("Total elapsed time:  " + 
                (elapsedTime/1000.0) + " seconds.\n");
        
    } // end countDivisorsWithThreads()

    
    /**
     * The main() routine just gets the number of threads from the user and 
     * calls countDivisorsWithThreads() to do the actual work.
     */
    public static void main(String[] args) {
        int numberOfThreads = 0;
        while (numberOfThreads < 1 || numberOfThreads > 10) {
            System.out.print("How many threads do you want to use  (1 to 10) ?  ");
            numberOfThreads = TextIO.getlnInt();
            if (numberOfThreads < 1 || numberOfThreads > 10)
                System.out.println("Please enter a number from 1 to 10 !");
        }
        countDivisorsWithThreads(numberOfThreads);
    }
    

    /**
     * Finds the number of divisors of the integer N.  Note that this method does
     * the counting in a stupid way, since it tests every integer in the range
     * 1 to N to see whether it evenly divides N.
     */
    private static int countDivisors(int N) {
        int count = 0;
        for (int i = 1; i <= N ; i++) {
            if ( N % i == 0 )
                count ++;
        }
        return count;
    }

} // end CountDivisorsUsingThreadPool
