package sbu.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CPU_Simulator
{
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
            // Find the task with the shortest processing time
            Task shortestTask = tasks.get(0);
            for (int i = 1; i < tasks.size(); i++) {
                if (tasks.get(i).processingTime < shortestTask.processingTime) {
                    shortestTask = tasks.get(i);
                }
            }

            // Start a new thread to run the shortest task
            Thread taskThread = new Thread(shortestTask);
            taskThread.start();

            // Wait for the shortest task to finish and add it to the executed tasks list
            try {
                taskThread.join();
                executedTasks.add(shortestTask.ID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Remove the shortest task from the list of remaining tasks
            tasks.remove(shortestTask);
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

            // Start the simulation and print the list of executed tasks
            CPU_Simulator simulator = new CPU_Simulator();
            ArrayList<String> executedTasks = simulator.startSimulation(tasks);
            System.out.println("Executed tasks: " + Arrays.toString(executedTasks.toArray()));
        }
    }
