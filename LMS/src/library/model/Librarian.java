package library.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Librarian extends User {
    //attribute of librarian class
    private String branchName;
    private String shiftTime;
    private List<String> assignedTask = new ArrayList<>();
    private List<String> activeTask = new ArrayList<>();
    private List<String> completedTask = new ArrayList<>();

    public Librarian(String userName, String userId, String branchName, String shiftTime, List<String> assignedTask,

                     List<String> activeTask) {
        super(userName, userId);
        this.branchName = branchName;
        this.shiftTime = shiftTime;
        this.assignedTask = assignedTask != null ? assignedTask : new ArrayList<>();
        this.activeTask = activeTask != null ? activeTask : new ArrayList<>();
    }

    //getters


    public String getBranchName() {
        return branchName;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public List<String> getAssignedTask() {
        return assignedTask;
    }

    public List<String> getActiveTask() {
        return activeTask;
    }

    public List<String> getCompletedTask() {
        return completedTask;
    }

    //setters(I know that i have set values through constructor)

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }


    //utility method


    public boolean isOnDuty(String currentTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime now = LocalTime.parse(currentTime, formatter);

        String[] parts = shiftTime.split("-");
        LocalTime start = LocalTime.parse(parts[0], formatter);
        LocalTime end = LocalTime.parse(parts[1], formatter);

        if (start.isBefore(end) || start.equals(end)) {
            // Normal shift: e.g., 09:00 - 17:00
            return !now.isBefore(start) && !now.isAfter(end);
        } else {
            // Overnight shift: e.g., 22:00 - 06:00
            return !now.isBefore(start) || !now.isAfter(end);
        }

        }


    public void markTaskCompleted(String task) {
        if (activeTask.remove(task))
            completedTask.add(task);
    }
}
