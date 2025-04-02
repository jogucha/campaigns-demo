package uax.madm.devops.campaigns_demo.application.services;

import java.util.Optional;
import uax.madm.devops.campaigns_demo.domain.model.Worker;

public interface WorkerService {

    Optional<Worker> findById(Long id);

    Worker save(Worker worker);

    Optional<Worker> updateWorker(Long id, Worker worker);
}
