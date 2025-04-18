package uax.madm.devops.campaigns_demo.application.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import uax.madm.devops.campaigns_demo.application.services.WorkerService;
import uax.madm.devops.campaigns_demo.domain.model.Worker;
import uax.madm.devops.campaigns_demo.infrastructure.persistence.WorkerRepository;

@Service
public class DefaultWorkerService implements WorkerService {
    private WorkerRepository workerRepository;

    public DefaultWorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public List<Worker> findWorkers() {
        return workerRepository.findAll();
    }

    @Override
    public Optional<Worker> findById(Long id) {
        return workerRepository.findById(id);
    }

    @Override
    public Worker save(Worker worker) {
        worker.setId(null);
        return workerRepository.save(worker);
    }

    @Override
    public Optional<Worker> updateWorker(Long id, Worker worker) {
        return workerRepository.findById(id)
                .map(base -> updateFields(base, worker))
                .map(workerRepository::save);
    }

    private Worker updateFields(Worker base, Worker updatedWorker) {
        base.setFirstName(updatedWorker.getFirstName());
        base.setLastName(updatedWorker.getLastName());
        return base;
    }
}