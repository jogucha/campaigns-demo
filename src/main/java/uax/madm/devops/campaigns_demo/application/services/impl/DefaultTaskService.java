package uax.madm.devops.campaigns_demo.application.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import uax.madm.devops.campaigns_demo.application.services.TaskService;
import uax.madm.devops.campaigns_demo.domain.model.Task;
import uax.madm.devops.campaigns_demo.domain.model.Worker;
import uax.madm.devops.campaigns_demo.domain.model.enums.TaskStatus;
import uax.madm.devops.campaigns_demo.infrastructure.persistence.TaskRepository;


@Service
public class DefaultTaskService implements TaskService {
    private TaskRepository taskRepository;

    public DefaultTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> searchTasks(String nameSubstring, Long assigneeId) {
        return taskRepository.findByNameContainingAndAssigneeId(nameSubstring != null ? nameSubstring : "", assigneeId);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        task.setId(null);
        task.setStatus(TaskStatus.TO_DO);
        //task.setAssignee(null);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> updateTask(Long id, Task task) {
        return taskRepository.findById(id)
                .map(base -> updateFields(base, task))
                .map(taskRepository::save);
    }

    private Task updateFields(Task base, Task updatedTask) {
        base.setName(updatedTask.getName());
        base.setDescription(updatedTask.getDescription());
        base.setDueDate(updatedTask.getDueDate());
        base.setStatus(updatedTask.getStatus());
        base.setAssignee(updatedTask.getAssignee());
        base.setCampaign(updatedTask.getCampaign());
        return base;
    }

    @Override
    public Optional<Task> updateStatus(Long id, TaskStatus status) {
        return taskRepository.findById(id)
                .map(base -> {
                    base.setStatus(status);
                    return taskRepository.save(base);
                });
    }

    @Override
    public Optional<Task> updateAssignee(Long id, Worker assignee) {
        return taskRepository.findById(id)
                .map(base -> {
                    base.setAssignee(assignee);
                    return taskRepository.save(base);
                });
    }
}
