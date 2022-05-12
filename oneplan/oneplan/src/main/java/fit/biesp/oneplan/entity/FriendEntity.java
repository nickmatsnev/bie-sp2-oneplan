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
@Table(name = "friends")
public class FriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "nickname")
    private String nickname;

    @NotNull
    @Column(name = "email")
    private String email;

    //@ManyToOne
    //@JoinColumn(name = "user_id")
    //private UserEntity user;
}
