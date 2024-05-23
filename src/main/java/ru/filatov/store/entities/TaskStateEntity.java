package ru.filatov.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "task_state")
public class TaskStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    String name;


    @Builder.Default
    long ordinal;

    @Builder.Default
    Instant createdAt = Instant.now();

    @Builder.Default
    @OneToMany
            @JoinColumn(name = "task_state_id",referencedColumnName = "id")
    List<TaskEntity> taskStates = new ArrayList<>();
}
