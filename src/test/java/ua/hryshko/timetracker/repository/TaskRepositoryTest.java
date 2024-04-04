package ua.hryshko.timetracker.repository;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.hryshko.timetracker.enums.Status;
import ua.hryshko.timetracker.model.entity.Task;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskRepositoryTest {

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void testFindAllByStatus_Successful() {
        List<Task> mockTasks = new ArrayList<>();

        Task task1 = new Task();
        task1.setId(1L);
        task1.setStatus(Status.IN_PROGRESS.getName());

        Task task2 = new Task();
        task2.setId(2L);
        task2.setStatus(Status.IN_PROGRESS.getName());

        mockTasks.add(task1);
        mockTasks.add(task2);

        when(taskRepository.findAllByStatus(Status.IN_PROGRESS.getName())).thenReturn(mockTasks);

        List<Task> retrievedTasks = taskRepository.findAllByStatus(Status.IN_PROGRESS.getName());

        Assertions.assertEquals(mockTasks, retrievedTasks);
        verify(taskRepository, times(1)).findAllByStatus(Status.IN_PROGRESS.getName());
    }

    @Test
    public void testFindAllByStatus_NoTasksWithStatus() {
        List<Task> mockTasks = new ArrayList<>();

        when(taskRepository.findAllByStatus(Status.FINISHED.getName())).thenReturn(mockTasks);

        List<Task> retrievedTasks = taskRepository.findAllByStatus(Status.FINISHED.getName());

        Assertions.assertTrue(retrievedTasks.isEmpty());
        verify(taskRepository, times(1)).findAllByStatus(Status.FINISHED.getName());
    }

}