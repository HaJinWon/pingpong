package com.douzone.pingpong.domain.member;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "team_member")
@Getter
public class TeamMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

//    private Long memberId;
//    private Long teamId;
}
