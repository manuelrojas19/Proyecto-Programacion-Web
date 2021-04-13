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
@Entity(name = "Professor")
@Table
public class Professor {
    @Id
    @SequenceGenerator(
            name = "profesor_sequence",
            sequenceName = "profesor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "profesor_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    private String firstName;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String secondName;
    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    private String firstSurname;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String secondSurname;
    @Email
    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    public Professor(String firstName, String secondName, String firstSurname, String secondSurname, String email, Gender gender) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.email = email;
        this.gender = gender;
    }
}
