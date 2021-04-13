package mx.ipn.upiicsa.web.classroom.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity(name = "Student")
@Table
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "student_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    private String firstName;
    @Column(columnDefinition = "TEXT")
    private String secondName;
    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    private String firstSurname;
    @Column(columnDefinition = "TEXT")
    private String secondSurname;
    @Email
    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    public Student(String firstName, String secondName, String firstSurname, String secondSurname, String email, Gender gender) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.email = email;
        this.gender = gender;
    }
}
