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
@Table(name = "users")
public class UserEntity {
    @Id
    private Long user_id;

    @NotNull
    @Column(name = "user_email")
    private String user_email;

    @NotNull
    @Column(name = "user_nickname")
    private String user_nickname;

    @NotNull
    @Column(name = "user_password")
    private String user_password;
}
