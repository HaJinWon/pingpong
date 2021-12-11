package com.douzone.pingpong.domain.team;

import com.douzone.pingpong.domain.chat.Room;
import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.member.TeamMember;
import com.douzone.pingpong.domain.post.Part;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Team implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
    private List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Part> parts = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Room> rooms = new ArrayList<>();

    private String name;
    private LocalDateTime date;
    private Long host;

    @Builder
    private Team(String name, TeamMember teamMember) {
        this.name = name;
        this.date = LocalDateTime.now();
        this.host = teamMember.getMember().getId();
    }

    @Builder
    public static Team createTeam(String name, TeamMember teamMember) {
            Team team = Team.builder()
                    .name(name)
                    .teamMember(teamMember)
                    .build();
            team.addTeamMember(teamMember);
            return team;
    }

    //== 연관관계 메서드 ==//
    public void addTeamMember(TeamMember teamMember) {
        teamMembers.add(teamMember);
        teamMember.setTeam(this);
    }
}
