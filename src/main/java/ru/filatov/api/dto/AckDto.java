package ru.filatov.api.dto;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(level = AccessLevel.PRIVATE)
public class AckDto {

    Boolean answer;
    public static AckDto makeDefault(Boolean answer) {
        return builder()
                .answer(answer).build();
    }

}
