package com.douzone.pingpong.domain.part;

import com.douzone.pingpong.domain.post.Post;
import com.douzone.pingpong.domain.team.Team;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Part {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "part")
    List<Post> posts = new ArrayList<>();

    private String name;
    private LocalDateTime date;



}
