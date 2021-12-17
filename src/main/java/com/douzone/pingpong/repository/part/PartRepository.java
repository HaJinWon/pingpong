package com.douzone.pingpong.repository.part;

import com.douzone.pingpong.domain.part.Part2;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PartRepository {

    @Autowired
    private SqlSession sqlSession;

    public List<Part2> getPartList(Long teamId) {

        return sqlSession.selectList("part.getPartList",teamId);
    }

    public void addPart(Long teamId, String partName) {
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        map.put("partName",partName);

        sqlSession.insert("part.addPart", map);
    }

    public void delPart( Long partId) {

        sqlSession.delete("part.delPart",partId);
    }

    public Long getFirstPartId(Long teamId) {
        return sqlSession.selectOne("part.getFirstPartId",teamId);
    }
}
