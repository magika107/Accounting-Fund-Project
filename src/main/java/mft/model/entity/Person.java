package mft.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder

public class Person {
    private int id;
    private String name;
    private String family;
    private String username;
    private String password;
    private LocalDate birthDate;
    private String phoneNumber;
}
