package com.gdscsmwu.earthus.plogus.users.domain;

import com.gdscsmwu.earthus.plogus.plogging.domain.Plogging;
import com.gdscsmwu.earthus.plogus.quiz.domain.Quiz;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Getter
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_uuid", columnDefinition = "bigint", nullable = false)
    private Long userUuid;

    @Column(name = "username", columnDefinition = "varchar(30)", nullable = false)
    private String username;

    @Column(name = "password", columnDefinition = "varchar(255)", nullable = false)
    private String password;

    @Column(name = "email", columnDefinition = "varchar(50)", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private UsersRole role;

    @Column(name = "provider", columnDefinition = "varchar(255)")
    private String provider;

    @Column(name = "provider_id", columnDefinition = "varchar(255)")
    private String providerId;

    @CreationTimestamp
    @Column(name = "create_date", columnDefinition = "timestamp", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "user_profile", columnDefinition = "text")
    private String userProfile;

    @Column(name = "profile_uuid", columnDefinition = "varchar(255)")
    private String profileUuid;



    @OneToMany(mappedBy = "users")
    private List<Plogging> plogging = new ArrayList<Plogging>();

    @OneToMany(mappedBy = "users")
    private List<Quiz> quiz = new ArrayList<Quiz>();



    @Builder
    public Users(String username, String password, String email, UsersRole role, String provider, String providerId, LocalDateTime createDate, String userProfile, String profileUuid) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
        this.userProfile = userProfile;
        this.profileUuid = profileUuid;
    }

    // password 수정
    public void modifyPassword(String password) {
        this.password = password;
    }

    // username 수정
    public void modifyUsername(String username) {
        this.username = username;
    }

    // userProfile 수정
    public void updateUserProfile(String imageUrl, String imageUuid) {
        this.userProfile = imageUrl;
        this.profileUuid = imageUuid;
    }



    public Users update(String username, String password, String provider, String providerId, String userProfile) {

        this.username = username;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
        this.userProfile = userProfile;

        return this;

    }

}
