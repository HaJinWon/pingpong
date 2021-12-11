package com.douzone.pingpong.domain.member;

import com.douzone.pingpong.domain.team.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "team_member")
@Getter @Setter
@NoArgsConstructor
public class TeamMember implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public TeamMember(Member member) {
        this.member = member;
    }

    public static TeamMember createTeamMember(Member member) {
        TeamMember teamMember = TeamMember.builder()
                .member(member)
                .build();
        return teamMember;
    }
}
