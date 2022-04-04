import java.io.File;
import java.util.Scanner;

import net.datastructures.*;

public class Scheduler {
    static int maxWaitingTime;
    static SortedPriorityQueue<Integer,Job> processQ = new SortedPriorityQueue<>();

    public static void readInJobs(String file) {
        try {
            File jobs = new File(file);
            Scanner scan = new Scanner(jobs);

            while (scan.hasNextLine()) {
                String jobDescription = scan.nextLine();
                String[] jobDesc = jobDescription.split(" ");

                Job newJob = new Job(getJobName(jobDesc), getPriority(jobDesc), getLength(jobDesc));
                processQ.insert(getPriority(jobDesc), newJob);
            }
            System.out.println(processQ);
            System.out.printf("Done!");
            scan.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static int getPriority(String[] jobDesc) {
        int p = 0;
        for (int i = 0; i < jobDesc.length - 1; i++) {
            if (jobDesc[i].equals("length")) {
                return p = Integer.parseInt(jobDesc[i + 1]);
            }
        }
        return p;
    }

    public static int getLength(String[] jobDesc) {
        int l = 0;
        for (int i = 0; i < jobDesc.length - 1; i++) {
            if (jobDesc[i].equals("priority")) {
                return l = Integer.parseInt(jobDesc[i + 1]);
            }
        }
        return l;
    }

    public static String getJobName(String[] jobDesc) {
        String n = null;
        for (int i = 0; i < jobDesc.length - 1; i++) {
            if (jobDesc[i].equals("job")) {
                return n = jobDesc[i + 1];
            }
        }
        return n;
    }

}

