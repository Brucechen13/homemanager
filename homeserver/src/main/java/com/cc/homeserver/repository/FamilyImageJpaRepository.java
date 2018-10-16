package com.cc.homeserver.repository;

import com.cc.homeserver.entity.FamilyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface FamilyImageJpaRepository extends JpaRepository<FamilyImage, Long> {

    @Query(value = "SELECT a.bookpkid,a.bookcode,a.bookname,b.authorname FROM BookInfo a " +
            "LEFT JOIN AuthorInfo b ON a.authorid=b.authorid " +
            "WHERE a.bookpkid=:uid", nativeQuery = true)
    List<Object[]> selectByUid(@Param("pkid") BigDecimal uid);
}
