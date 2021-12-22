package com.douzone.pingpong.repository.part;

import com.douzone.pingpong.domain.part.Part;
import com.douzone.pingpong.domain.part.Part2;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PartRepository {
    private final SqlSession sqlSession;
    private final EntityManager em;

    public List<Part2> getPartList(Long teamId) {

        return sqlSession.selectList("part.getPartList",teamId);
    }

    public void addPart(Long teamId, String partName) {
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        map.put("partName",partName);

        sqlSession.insert("part.addPart", map);
    }

    public Part getPart(Long partId) {
        return em.find(Part.class, partId);
    }

    public void delPart( Long partId) {

        sqlSession.delete("part.delPart",partId);
    }

    public Long getFirstPartId(Long teamId) {
        return sqlSession.selectOne("part.getFirstPartId",teamId);
    }
}
