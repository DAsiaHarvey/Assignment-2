import java.io.File;
import java.util.Scanner;

import net.datastructures.*;

public class Scheduler {
    int maxWaitingTime = 0;
    SortedPriorityQueue<Integer, Job> processQ = new SortedPriorityQueue<>();

    public void readInJobs(String file) {
        try {
            File jobs = new File(file);
            Scanner scan = new Scanner(jobs);

            while (scan.hasNextLine()) {
                String jobDescription = scan.nextLine();
                String[] jobDesc = jobDescription.split(" ");
                int length;
                if (!isValidTimeSlice(jobDesc)) {
                    processing();
                    continue;
                } else {
                    length = Job.getLength(jobDesc);
                }

                String name = Job.getJobName(jobDesc);
                int priority = Job.getPriority(jobDesc);

                Job jobToAdd = new Job(name, priority, length);
                processQ.insert(priority, jobToAdd);

                processing();
            }
            while (!processQ.isEmpty()) {
                processing();
            }
            System.out.printf("Done!");
            scan.close();
        } catch (Exception e) {
            System.out.printf("Something went wrong");
        }
    }

    public void processing() {
        if (!processQ.isEmpty()) {
            String n = processQ.min().getValue().name;
            int p = processQ.min().getValue().priority;
            int l = processQ.min().getValue().length - 1;
            processQ.removeMin();

            if (!processQ.isEmpty()) {
                processQ.min().getValue().timeWaiting++;
                if (processQ.min().getValue().timeWaiting > maxWaitingTime) {
                    processQ = increasePriorities(processQ);
                }
            }
            System.out.printf("%s (priority: %d, time remaining: %d)\n", n, p, l);
            if (l > 0) {
                Job updatedJob = new Job(n, p, l);
                processQ.insert(p, updatedJob);
            }
        }
    }

    public SortedPriorityQueue<Integer, Job> increasePriorities(SortedPriorityQueue<Integer, Job> pq) {
        SortedPriorityQueue<Integer, Job> updatedQ = new SortedPriorityQueue<>();
        while (!pq.isEmpty()) {
            String n = pq.min().getValue().name;
            int p = pq.min().getValue().priority;
            int l = pq.min().getValue().length;
            if (p != -20) {
                p-=1;
                Job tempJob = new Job(n, p, l);
                tempJob.timeWaiting = 0;
                pq.removeMin();
                updatedQ.insert(p, tempJob);
            }
            else{
                Job tempJob = new Job(n, p, l);
                tempJob.timeWaiting = 0;
                pq.removeMin();
                updatedQ.insert(p, tempJob);
            }
        }

        return updatedQ;
    }

    public boolean isValidTimeSlice(String[] jobDesc) {
        if (jobDesc[0].equals("no")) {
            return false;
        }

        for (int i = jobDesc.length; i >= 0; i--) {
            if (jobDesc[i - 1].equals("length")) {
                if (isNumeric(jobDesc[i])) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
