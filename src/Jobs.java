import java.io.File;
import java.util.Scanner;

import net.datastructures.SortedPriorityQueue;

public class Jobs {
    static int priority;
    static int length;
    static int timeWaiting = 0;
    static int maxWaitingTime;
    static String name;
    SortedPriorityQueue processQ = new SortedPriorityQueue<>();

    public static void readInJobs(){
        try {
            File jobs = new File("C:\\Users\\xdrag\\Desktop\\Assignment 2\\src\\jobs.txt");
            Scanner scan = new Scanner(jobs);

            while(scan.hasNextLine()){
                String job
            }
            scan.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public static int getPriority(){
        return priority;
    }

    public static int getLength(){
        return length;
    }

    public static String getJobName(){
        return name;
    }



}
