import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment5 {
    public static void main(String[] args) {
        simpleQueueTest();
        scheduleTasks("taskset1.txt");
        scheduleTasks("taskset2.txt");
        scheduleTasks("taskset3.txt");
        scheduleTasks("taskset4.txt");
        scheduleTasks("taskset5.txt");
    }

    public static void scheduleTasks(String taskFile) {
        // TODO: Uncomment code here as you complete the tasks and scheduling algorithm
        ArrayList<Task> tasksByDeadline = new ArrayList<>();
        ArrayList<Task> tasksByStart = new ArrayList<>();
        ArrayList<Task> tasksByDuration = new ArrayList<>();

        readTasks(taskFile, tasksByDeadline, tasksByStart, tasksByDuration);

        schedule("Deadline Priority : "+ taskFile, tasksByDeadline);
        schedule("Startime Priority : " + taskFile, tasksByStart);
        schedule("Duration priority : " + taskFile, tasksByDuration);
    }

    /**
     * Reads the task data from a file, and creates the three different sets of tasks for each
     */
    public static void readTasks(String filename,
                                 ArrayList<Task> tasksByDeadline,
                                 ArrayList<Task> tasksByStart,
                                 ArrayList<Task> tasksByDuration) {
        // TODO: Write your task reading code here

        File file = new File(filename);

        int id = 0;
        int start = 0;
        int deadline = 0;
        int duration = 0;

        try (Scanner input = new Scanner(file)) {
            while(input.hasNextLine())
            {
                id += 1;
                start = input.nextInt();
                deadline = input.nextInt();
                duration = input.nextInt();
                TaskByDeadline taskInput = new TaskByDeadline(id, start, deadline, duration);
                tasksByDeadline.add(taskInput);
                tasksByStart.add(taskInput);
                tasksByDuration.add(taskInput);
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read file: " + ex);
        }
    }

    /**
     * Given a set of tasks, schedules them and reports the scheduling results
     */
    public static void schedule(String label, ArrayList<Task> tasks) {

        if (label.contains("Duration")){
            PriorityQueue<TaskByDuration> durationQueue = new PriorityQueue<>();
            System.out.println(label);
            duration(tasks, durationQueue);
        }
        else if (label.contains("Start")) {
            PriorityQueue<TaskByStart> startQueue = new PriorityQueue<>();
            System.out.println(label);
            start(tasks, startQueue);
        }
        else if (label.contains("Deadline")){
            PriorityQueue<TaskByDeadline> deadlineQueue = new PriorityQueue<>();
            System.out.println(label);
            deadline(tasks, deadlineQueue);
        }
    }

    public static void duration(ArrayList<Task> tasks, PriorityQueue<TaskByDuration> queue) {

        int id = 0;

        for (Task oneTask : tasks) {
            TaskByDuration durationTask = new TaskByDuration(++id, oneTask.start, oneTask.deadline, oneTask.duration);
            queue.enqueue(durationTask);
        }

        int clock = 1;
        int late = 0;
        ArrayList<TaskByDuration> tasksToEnqueue = new ArrayList<TaskByDuration>();

        while (!queue.isEmpty()) {

            String nextTask = "Error";
            boolean isValid = false;

            //While loop to find valid minimum task. Invalid tasks are put in an arraylist to be enqueued later.
            while (!isValid) {
                if (queue.isEmpty()) {
                    nextTask = "--";
                    break;
                }

                TaskByDuration minDuration = queue.dequeue();

                if (minDuration.start > clock) {
                    tasksToEnqueue.add(minDuration);
                }
                else {
                    nextTask = minDuration.toString();
                    minDuration.duration = minDuration.duration - 1;
                    if (minDuration.duration > 0) {
                        nextTask = nextTask;
                        tasksToEnqueue.add(minDuration);
                    }

                    nextTask = checkEndDuration(nextTask, minDuration, clock);

                    if (minDuration.deadline < clock && minDuration.duration == 0){
                        late += 1;
                        nextTask += " Late " + late;
                    }


                    isValid = true;
                }
            }

            System.out.println("Time " + clock + ": " + nextTask);

            for (TaskByDuration task : tasksToEnqueue) {
                queue.enqueue(task);
            }

            tasksToEnqueue.removeAll(tasksToEnqueue);

            ++clock;

        }
        printLate(late);

    }

    public static String checkEndDuration(String taskString, TaskByDuration durationTask, int clock) {
        if (durationTask.duration == 0) {
            taskString = taskString + "**";
        }
        return taskString;
    }

    public static void start(ArrayList<Task> tasks, PriorityQueue<TaskByStart> queue) {
        int id = 0;
        for (Task oneTask : tasks) {
            TaskByStart startTask = new TaskByStart(++id, oneTask.start, oneTask.deadline, oneTask.duration);
            queue.enqueue(startTask);
        }

        int clock = 1;
        int late = 0;
        ArrayList<TaskByStart> tasksToEnqueue = new ArrayList<TaskByStart>();

        while (!queue.isEmpty()) {

            String nextTask = "Error";
            boolean isValid = false;

            //While loop to find valid minimum task. Invalid tasks are put in an arraylist to be enqueued later.
            while (!isValid) {
                if (queue.isEmpty()) {
                    nextTask = "--";
                    break;
                }

                TaskByStart minStart = queue.dequeue();

                if (minStart.start > clock) {
                    tasksToEnqueue.add(minStart);
                }

                else if (!queue.isEmpty() && minStart.start == queue.getValue().start && minStart.deadline > queue.getValue().deadline) {
                    tasksToEnqueue.add(minStart);
                }

                else {
                    nextTask = minStart.toString();
                    minStart.duration = minStart.duration - 1;
                    if (minStart.duration > 0) {
                        nextTask = nextTask;
                        tasksToEnqueue.add(minStart);
                    }

                    nextTask = checkEndStart(nextTask, minStart, clock);

                    if (minStart.deadline < clock && minStart.duration == 0){
                        late += 1;
                        nextTask += " Late " + late;
                    }

                    isValid = true;
                }
            }

            System.out.println("Time " + clock + ": " + nextTask);

            for (TaskByStart task : tasksToEnqueue) {
                queue.enqueue(task);
            }

            tasksToEnqueue.removeAll(tasksToEnqueue);

            ++clock;

        }
        printLate(late);
    }

    public static String checkEndStart(String taskString, TaskByStart startTask, int clock) {
        if (startTask.duration == 0) {
            taskString = taskString + "**";
        }
        return taskString;
    }

    public static void deadline(ArrayList<Task> tasks, PriorityQueue<TaskByDeadline> queue) {
        int id = 0;
        for (Task oneTask : tasks) {
            TaskByDeadline deadlineTask = new TaskByDeadline(++id, oneTask.start, oneTask.deadline, oneTask.duration);
            queue.enqueue(deadlineTask);
        }

        int clock = 1;
        int late = 0;
        ArrayList<TaskByDeadline> tasksToEnqueue = new ArrayList<TaskByDeadline>();

        while (!queue.isEmpty()) {

            String nextTask = "Error";
            boolean isValid = false;

            //While loop to find valid minimum task. Invalid tasks are put in an arraylist to be enqueued later.
            while (!isValid) {
                if (queue.isEmpty()) {
                    nextTask = "--";
                    break;
                }

                TaskByDeadline minDeadline = queue.dequeue();

                if (minDeadline.start > clock) {
                    tasksToEnqueue.add(minDeadline);
                }
                else {
                    nextTask = minDeadline.toString();
                    minDeadline.duration = minDeadline.duration - 1;
                    if (minDeadline.duration > 0) {
                        nextTask = nextTask;
                        tasksToEnqueue.add(minDeadline);
                    }

                    nextTask = checkEndDeadline(nextTask, minDeadline, clock);

                    if (minDeadline.deadline < clock && minDeadline.duration == 0){
                        late += 1;
                        nextTask += " Late " + late;
                    }

                    isValid = true;
                }
            }

            System.out.println("Time " + clock + ": " + nextTask);

            for (TaskByDeadline task : tasksToEnqueue) {
                queue.enqueue(task);
            }

            tasksToEnqueue.removeAll(tasksToEnqueue);

            ++clock;

        }
        printLate(late);
    }

    public static String checkEndDeadline(String taskString, TaskByDeadline deadlineTask, int clock) {
        if (deadlineTask.duration == 0) {
            taskString = taskString + "**";
        }
        return taskString;
    }

    public static void printLate(int late) {
        System.out.println("Tasks late " + late + " Total Late " + late);
        System.out.println();
    }

    public static void simpleQueueTest() {
        // TODO: Uncomment code here for a simple test of your priority queue code
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.enqueue(9);
        queue.enqueue(7);
        queue.enqueue(5);
        queue.enqueue(3);
        queue.enqueue(1);
        queue.enqueue(10);

        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }
}
