public class Job {
    static int priority;
    static int length;
    static int timeWaiting = 0;
    static String name;

    public Job(String n, int p, int l){
        name = n;
        priority = p;
        length = l;
    }
}
