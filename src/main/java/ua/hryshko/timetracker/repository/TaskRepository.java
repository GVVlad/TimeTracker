package ua.hryshko.timetracker.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.hryshko.timetracker.model.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByStatus(String status);
    Optional<Task> findByTitle(String title);
}
