package com.douzone.pingpong.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "test_member")
public class TestMember {
    @Id
    @GeneratedValue
    @Column(name = "test_id")
    private Long id;

    private String name;

    public TestMember(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
