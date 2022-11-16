package com.gangwondog.openAPI.domain.user;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@ToString
@Table(name = "member")
@Entity
public class UserEntityP {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public UserEntityP(String email, String password, Authority authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
    }
}
