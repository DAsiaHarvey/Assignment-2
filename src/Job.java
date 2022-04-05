public class Job {
    int priority = 0;
    int length = 1;
    int timeWaiting = 0;
    String name = null;

    public Job(String n, int p, int l){
        name = n;
        priority = p;
        length = l;
    }
}
