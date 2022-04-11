public class Job {
    int priority = 0;
    int length = 1;
    int timeWaiting = 0;
    String name = null;

    public Job(String n, int p, int l) {
        name = n;
        priority = p;
        length = l;
    }

    public static int getPriority(String[] jobDesc) {
        int p = 0;
        for (int i = 0; i < jobDesc.length - 1; i++) {
            if (jobDesc[i].equals("priority")) {
                return p = Integer.parseInt(jobDesc[i + 1]);
            }
        }
        return p;
    }

    public static String getJobName(String[] jobDesc) {
        String n = jobDesc[2];

        int i = 3;
        while (!jobDesc[i].equals("with")) {
            n += " " + jobDesc[i];
            i++;
        }

        return n;
    }

    public static int getLength(String[] jobDesc) {
        int l = 0;
        for (int i = jobDesc.length; i >= 0; i--) {
            if (jobDesc[i - 1].equals("length")) {
                return l = Integer.parseInt(jobDesc[i]);
            }
        }
        return l;
    }

}