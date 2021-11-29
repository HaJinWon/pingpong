package com.douzone.pingpong.domain.member;

import com.douzone.pingpong.domain.post.Part;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Part> parts = new ArrayList<>();

    private String name;
    private LocalDateTime date;
    private Long host;
}
