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

    @Enumerated(EnumType.STRING)
    private Include include;

    public TeamMember(Member member) {
    }

    // == 생성자 메서드 == //
    // 팀 "생성"시 호출
    public static TeamMember createTeamMember(Member member) {
        TeamMember teamMember = new TeamMember();
        teamMember.setMember(member);
        teamMember.setInclude(Include.Y);
        return teamMember;
    }

    // 팀 초대받았을때 호출
    public static TeamMember inviteTeamMember(Team team, Member member) {
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(team);
        teamMember.setMember(member);
        teamMember.setInclude(Include.N);
        return teamMember;
    }

    // == 연관관계 메서드 == //
    public void setTeam(Team team) {
        this.team = team;
        team.getTeamMembers().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getTeamMembers().add(this);
    }

}
