/**
* Name:Assignment 2
* Author: D'Asia Harvey
* Date: 4/10/22
* Bugs: updated priorities off by 1 in the second test run
*/
public class Job {
    int priority = 0;
    int length = 1;
    int timeWaiting = 0;
    String name = null;

    /**
     *  crate job with passed in values
     * @param n name of job
     * @param p priority
     * @param l length
     */
    public Job(String n, int p, int l) {
        name = n;
        priority = p;
        length = l;
    }

    /**
     * Get priority of job
     * @param jobDesc array to look through
     * @return priority of array
     */
    public static int getPriority(String[] jobDesc) {
        int p = 0;
        for (int i = 0; i < jobDesc.length - 1; i++) {
            if (jobDesc[i].equals("priority")) {//check for word priority in array
                return p = Integer.parseInt(jobDesc[i + 1]);//grab next word and convert to int
            }
        }
        return p;
    }

    /**
     * get name of job
     * @param jobDesc array to look through
     * @return name of job
     */
    public static String getJobName(String[] jobDesc) {
        String n = jobDesc[2];//grab first part of job name

        int i = 3;
        while (!jobDesc[i].equals("with")) {// while not the last word after job name
            n += " " + jobDesc[i];
            i++;
        }

        return n;
    }

    /**
     * get length of job
     * @param jobDesc array to look through
     * @return time slice/length of the job
     */
    public static int getLength(String[] jobDesc) {
        int l = 0;
        for (int i = jobDesc.length; i >= 0; i--) {
            if (jobDesc[i - 1].equals("length")) {//look for word length in array
                return l = Integer.parseInt(jobDesc[i]);//grab the value
            }
        }
        return l;
    }

}