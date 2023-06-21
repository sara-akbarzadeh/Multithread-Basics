package sbu.cs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DualCoreCPU_Simulator {
    public static class Task implements Runnable {
        long processingTime;
        String ID;

        public Task(String ID, long processingTime) {
            this.ID = ID;
            this.processingTime = processingTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> startSimulation(ArrayList<Task> tasks) {
        ArrayList<String> executedTasks = new ArrayList<>();
        while (!tasks.isEmpty()) {
            // Find the two tasks with the shortest processing times
            Task[] shortestTasks = new Task[2];
            for (int i = 0; i < 2; i++) {
                shortestTasks[i] = tasks.get(0);
                for (int j = 1; j < tasks.size(); j++) {
                    if (tasks.get(j).processingTime < shortestTasks[i].processingTime) {
                        shortestTasks[i] = tasks.get(j);
                    }
                }
                tasks.remove(shortestTasks[i]);
            }

            // Start two threads to run the shortest tasks
            Thread taskThread1 = new Thread(shortestTasks[0]);
            Thread taskThread2 = new Thread(shortestTasks[1]);
            taskThread1.start();
            taskThread2.start();

            // Wait for both tasks to finish and add them to the executed tasks list
            try {
                taskThread1.join();
                taskThread2.join();
                executedTasks.add(shortestTasks[0].ID);
                executedTasks.add(shortestTasks[1].ID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return executedTasks;
    }

    public static void main(String[] args) {
        // Create an arraylist of tasks with random processing times
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task 1", 3000));
        tasks.add(new Task("Task 2", 2000));
        tasks.add(new Task("Task 3", 1000));
        tasks.add(new Task("Task 4", 4000));
        tasks.add(new Task("Task 5", 5000));
        tasks.add(new Task("Task 6", 1500));
        tasks.add(new Task("Task 7", 2500));
        tasks.add(new Task("Task 8", 3500));
        tasks.add(new Task("Task 9", 4500));
        tasks.add(new Task("Task 10", 5500));

        // Start the simulation and print the list of executed tasks
        DualCoreCPU_Simulator simulator = new DualCoreCPU_Simulator();
        ArrayList<String> executedTasks = simulator.startSimulation(tasks);
        System.out.println("Executed tasks: " + Arrays.toString(executedTasks.toArray()));
    }
}