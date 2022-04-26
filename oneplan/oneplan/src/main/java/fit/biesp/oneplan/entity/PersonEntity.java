package fit.biesp.oneplan.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "persons")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY)
    private UserEntity user;

    public PersonEntity(String email) {
        this.email = email;
    }
}