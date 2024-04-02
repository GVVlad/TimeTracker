package ua.hryshko.timetracker.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString

@Entity
@Table(name = "tasks")
@SequenceGenerator(name = "tasks_sequence_generator", sequenceName = "tasks_sequence")
public class Task {

    @Id
    @GeneratedValue(generator = "task_sequence_generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "started_at")
    private LocalTime startedAt;

    @Column(name = "finished_at")
    private LocalTime finishedAt;

    @Column(name = "spent_time")
    private String spentTime;

    @Column(name = "status")
    private String status;
}
