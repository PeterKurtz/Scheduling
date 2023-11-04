class TaskByDeadline extends Task{
    public TaskByDeadline(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }
    public void print() {
        System.out.println(" start: " + start + " deadline: " + deadline + " duration: " + duration);
    }

    @Override
    public int compareTo(Task taskToCompare) {
        if (deadline > taskToCompare.deadline) {
            return 1;
        }
        else if (deadline < taskToCompare.deadline) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
