class TaskByStart extends Task{
    public TaskByStart(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }
    public void print() {
        System.out.println(" start: " + start + " deadline: " + deadline + " duration: " + duration);
    }

    @Override
    public int compareTo(Task taskToCompare) {
        if (start > taskToCompare.start) {
            return 1;
        }
        else if (start < taskToCompare.start) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
