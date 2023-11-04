import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment5 {
    public static void main(String[] args) {
        //simpleQueueTest();
        //scheduleTasks("taskset1.txt");
        //scheduleTasks("taskset2.txt");
        //scheduleTasks("taskset3.txt");
        scheduleTasks("taskset4.txt");
        scheduleTasks("taskset5.txt");
    }

    public static void scheduleTasks(String taskFile) {
        // TODO: Uncomment code here as you complete the tasks and scheduling algorithm
        ArrayList<Task> tasksByDeadline = new ArrayList<>();
        ArrayList<Task> tasksByStart = new ArrayList<>();
        ArrayList<Task> tasksByDuration = new ArrayList<>();

        readTasks(taskFile, tasksByDeadline, tasksByStart, tasksByDuration);

        //schedule("Deadline Priority : "+ taskFile, tasksByDeadline);
        //schedule("Startime Priority : " + taskFile, tasksByStart);
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
            System.out.println("This is correct");
        }
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
