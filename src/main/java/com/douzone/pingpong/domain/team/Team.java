package com.douzone.pingpong.domain.team;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.TeamMember;
import com.douzone.pingpong.domain.post.Part;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Part> parts = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Room> rooms = new ArrayList<>();

    private String name;
    private LocalDateTime date;
    private Long host;

    @Builder
    public Team(String name, LocalDateTime date, Long host) {
        this.name = name;
        this.date = date;
        this.host = host;
    }
}
