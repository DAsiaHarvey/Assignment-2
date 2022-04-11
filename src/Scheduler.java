/**
* Name:Assignment 2
* Author: D'Asia Harvey
* Date: 4/10/22
* Bugs: updated priorities off by 1 in the second test run
*/
import java.io.File;
import java.util.Scanner;

import net.datastructures.*;

public class Scheduler {
    int maxWaitingTime = 0;//max time waiting before incrementing priority
    SortedPriorityQueue<Integer, Job> processQ = new SortedPriorityQueue<>();

    /**
     * where the jobs are read in and created
     * @param file file to read jobs from
     */
    public void readInJobs(String file) {
        try {
            File jobs = new File(file);//read in file
            Scanner scan = new Scanner(jobs);//read from file

            while (scan.hasNextLine()) {//while file has lines to read
                String jobDescription = scan.nextLine();//get next job for processing
                String[] jobDesc = jobDescription.split(" ");//make into string array
                int length;
                if (!isValidJob(jobDesc)) {//if job not is valid
                    processing();//ignore it and do time slice
                    continue;//go to read in next job
                } else {//if valid job
                    length = Job.getLength(jobDesc);//get length
                }

                String name = Job.getJobName(jobDesc); //get name from read in job
                int priority = Job.getPriority(jobDesc);//get priority from read in job

                Job jobToAdd = new Job(name, priority, length);// create job
                processQ.insert(priority, jobToAdd);//add job to queue

                processing();// process job
            }
            while (!processQ.isEmpty()) {//while queue isn't empty after reading all jobs from file
                processing();//continue to process
            }
            System.out.printf("Done!");
            scan.close();
        } catch (Exception e) {
            System.out.printf("Something went wrong");
        }
    }

    /**
     * where jobs are processed and printed
     */
    public void processing() {
        if (!processQ.isEmpty()) {//if there's something to be processed
            String n = processQ.min().getValue().name;//get name from first job
            int p = processQ.min().getValue().priority;//get priority
            int l = processQ.min().getValue().length - 1;//get and decrement length
            processQ.removeMin();//remove from q

            if (!processQ.isEmpty()) {
                processQ.min().getValue().timeWaiting++;//increment time waiting
                if (processQ.min().getValue().timeWaiting > maxWaitingTime) {//if waiting too long
                    processQ = increasePriorities(processQ);// make a higher priority
                }
            }
            System.out.printf("%s (priority: %d, time remaining: %d)\n", n, p, l);//print job
            if (l > 0) {//if job still has time left
                Job updatedJob = new Job(n, p, l);//update job
                processQ.insert(p, updatedJob);//add it back into the q
            }
        }
    }

    /**
     * updates priority once max time waiting is exceeded
     * @param pq queue whose values need to be updated
     * @return queue with updated priority
     */
    public SortedPriorityQueue<Integer, Job> increasePriorities(SortedPriorityQueue<Integer, Job> pq) {
        SortedPriorityQueue<Integer, Job> updatedQ = new SortedPriorityQueue<>();//new queue for updated priority
        while (!pq.isEmpty()) {//while original not empty
            String n = pq.min().getValue().name;//get name from first job
            int p = pq.min().getValue().priority;//get priority
            int l = pq.min().getValue().length;//get length
            if (p != -20) {//if priority not max priority
                p-=1;//make hogher priority
                Job tempJob = new Job(n, p, l);//make it a new job
                tempJob.timeWaiting = 0;// reset time waiting
                pq.removeMin();//remove form original queue
                updatedQ.insert(p, tempJob);//add to updated q
            }
            else{//if at max priority just add back to q
                Job tempJob = new Job(n, p, l);
                tempJob.timeWaiting = 0;
                pq.removeMin();
                updatedQ.insert(p, tempJob);
            }
        }

        return updatedQ;
    }

    /**
     * if job is valid or no
     * @param jobDesc job in array form
     * @return if job is valid or no
     */
    public boolean isValidJob(String[] jobDesc) {
        if (jobDesc[0].equals("no")) {// if job starts with no
            return false;//not valid
        }

        for (int i = jobDesc.length; i >= 0; i--) {//check for valid length
            if (jobDesc[i - 1].equals("length")) {
                if (isNumeric(jobDesc[i])) {//if length is a number
                    return true;
                } else {//length not a number
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if a number of not
     * @param str what will be evaluated
     * @return true if number, false if not
     */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);//length can convert to number
            return true;
        } catch (NumberFormatException e) {//length not a number
            return false;
        }
    }
}
