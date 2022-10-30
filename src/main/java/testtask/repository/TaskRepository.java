package testtask.repository;

import testtask.model.Task;

public interface TaskRepository {
    void createTableTask();

    Task createTask(Task task);

    void deleteTask(int id);

    Task changeTask(Task task);

    Task getTask(int id);
}
