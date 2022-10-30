package testtask.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testtask.model.Task;

@AllArgsConstructor
public class TaskService {
    private final ClanService clanService;
    final Logger log = LoggerFactory.getLogger(TaskService.class);

    public void completeTask(int clanId, long taskId) {
        Task task = getTask(taskId);
        if (task.isComplete()) {
            int old = clanService.getClan(clanId).getGold().get();
            int result = clanService.getClan(clanId).getGold().change(task.getReward());
            log.info("ClanId#{} complete taskId#{}. Old value of gold{}. New value of gold {}", clanId, taskId, old, result);
        }
    }

    public Task getTask(long taskId) {
        return new Task();
    }
}
