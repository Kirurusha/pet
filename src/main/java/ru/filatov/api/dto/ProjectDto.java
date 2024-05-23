package ru.filatov.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.FetchProfile;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDto {


    @NonNull
    Long id;

    @NonNull
    String name;

    @NonNull
    @JsonProperty("updated_at")
    Instant updatedAt;

    @NonNull
    @JsonProperty("created_at")
    Instant createdAt;
}
