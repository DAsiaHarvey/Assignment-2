import java.io.File;
import java.util.Scanner;

import net.datastructures.*;

public class Scheduler {
    int maxWaitingTime = 2;
    SortedPriorityQueue<Integer, Job> processQ = new SortedPriorityQueue<>();

    public void readInJobs(String file) {
        try {
            File jobs = new File(file);
            Scanner scan = new Scanner(jobs);

            while (scan.hasNextLine()) {
                String jobDescription = scan.nextLine();
                String[] jobDesc = jobDescription.split(" ");

                if (!isValidTimeSlice(jobDesc)) {
                    continue;
                }

                String name = getJobName(jobDesc);
                int priority = getPriority(jobDesc);
                int length = getLength(jobDesc);

                Job job = new Job(name, priority, length);
                processQ.insert(priority, job);
            }
            scan.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void processing() {
        while (!processQ.isEmpty()) {
            String n = processQ.min().getValue().name;
            int p = processQ.min().getValue().priority;
            int l = processQ.min().getValue().length - 1;
            processQ.removeMin();

            if (!processQ.isEmpty()) {
                processQ.min().getValue().timeWaiting++;
                if (processQ.min().getValue().timeWaiting == maxWaitingTime) {
                    processQ = increasePriorities(processQ);
                }
            }
            System.out.printf("%s (priority: %d, time remaining: %d)\n", n, p, l);
            if (l > 0) {
                Job updatedJob = new Job(n, p, l);
                processQ.insert(p, updatedJob);
            }
        }
        System.out.printf("Done!");
    }

    public SortedPriorityQueue<Integer, Job> increasePriorities(SortedPriorityQueue<Integer, Job> pq) {
        SortedPriorityQueue<Integer, Job> updatedQ = new SortedPriorityQueue<>();
        while (!pq.isEmpty()) {
            String n = pq.min().getValue().name;
            int p = pq.min().getValue().priority - 1;
            int l = pq.min().getValue().length;
            Job tempJob = new Job(n, p, l);
            tempJob.timeWaiting = 0;
            pq.removeMin();
            updatedQ.insert(p, tempJob);
        }

        return updatedQ;
    }

    public boolean isValidTimeSlice(String[] jobDesc) {
        if (jobDesc[0].equals("no")) {
            return false;
        }
        return true;
    }

    public int getPriority(String[] jobDesc) {
        int p = 0;
        for (int i = 0; i < jobDesc.length - 1; i++) {
            if (jobDesc[i].equals("priority")) {
                return p = Integer.parseInt(jobDesc[i + 1]);
            }
        }
        return p;
    }

    public int getLength(String[] jobDesc) {
        int l = 0;
        for (int i = jobDesc.length; i >=0 ; i--) {
            if (jobDesc[i-1].equals("length")) {
                return l = Integer.parseInt(jobDesc[i]);
            }
        }
        return l;
    }

    public String getJobName(String[] jobDesc) {
        String n = jobDesc[2];

        int i = 3;
        while(!jobDesc[i].equals("with")){
            n+=" "+jobDesc[i];
            i++;
        }
        
        return n;
    }

}
