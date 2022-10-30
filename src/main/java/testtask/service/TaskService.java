package testtask.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testtask.model.Task;
import testtask.repository.TaskRepository;
import testtask.repository.TaskRepositoryImpl;

@AllArgsConstructor
public class TaskService {
    private final ClanService clanService;
    private final TaskRepository repository = new TaskRepositoryImpl();
    final Logger log = LoggerFactory.getLogger(TaskService.class);

    public void completeTask(int clanId, int taskId) {
        Task task = getTask(taskId);
        if (task.isComplete()) {
            int old = clanService.getClan(clanId).getGold().get();
            int result = clanService.getClan(clanId).getGold().change(task.getReward());
            log.info("ClanId#{} complete taskId#{}. Old value of gold{}. New value of gold {}", clanId, taskId, old, result);
        }
    }

    public Task getTask(int taskId) {
        return repository.getTask(taskId);
    }

    public Task createTask(Task task) {
        return repository.createTask(task);
    }

    public Task updateTask(Task task) {
        return repository.changeTask(task);
    }

    public void deleteTask(int taskId) {
        repository.deleteTask(taskId);
    }
}
