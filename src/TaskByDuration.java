class TaskByDuration extends Task{
    public TaskByDuration(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }
    public void print() {
        System.out.println(" start: " + start + " deadline: " + deadline + " duration: " + duration);
    }

    @Override
    public int compareTo(Task taskToCompare) {
        if (duration > taskToCompare.duration) {
            return 1;
        }
        else if (duration < taskToCompare.duration) {
            return -1;
        }
        else {
            return 0;
        }
    }
}

