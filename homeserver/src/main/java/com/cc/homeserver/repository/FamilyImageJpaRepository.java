package com.cc.homeserver.repository;

import com.cc.homeserver.entity.FamilyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface FamilyImageJpaRepository extends JpaRepository<FamilyImage, Long> {

    @Query(value = "SELECT picUrl FROM family_image fi\n" +
            "JOIN tmp_user_family tuf ON fi.family_id = tuf.familyId WHERE tuf.uid==:uid " +
            "UNION " +
            "SELECT picUrl FROM family_image fi wheWHEREe fi.type = 'PUBLIC';", nativeQuery = true)
    List<Object[]> selectByUid(@Param("uid") BigDecimal uid);
}
