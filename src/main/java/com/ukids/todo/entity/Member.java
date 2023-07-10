package com.ukids.todo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String username;

    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;

}
