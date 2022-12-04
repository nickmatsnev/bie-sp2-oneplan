package fit.biesp.oneplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity extends PersonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "nickname")
    private String nickname;

    //@NotNull
    //@Column(name = "email")
    //private String email;

    @NotNull
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private Integer status;


    //@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    //@JoinColumn(name = "person_id")
    //@PrimaryKeyJoinColumn
    //private PersonEntity person;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    //private List<FriendEntity> friends;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organiser", fetch = FetchType.LAZY)
    private List<EventEntity> events;

    public UserEntity(String nickname, String email, String password) {
        this.nickname = nickname;
        //this.email = email;
        this.password = password;
        this.status = 0;
        //person.setEmail(email);
    }
}
