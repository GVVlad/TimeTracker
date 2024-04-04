package ua.hryshko.timetracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.hryshko.timetracker.model.entity.Task;

/**
 * Repository for storing and retrieving objects of type Task.
 * Used for interacting with the database for these objects.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    /**
     * Returns a list of all tasks with the specified status.
     *
     * @param status The status to find tasks by
     * @return A list of Task objects that have the specified status
     */
    List<Task> findAllByStatus(String status);
}
