package com.phocos.studio.util;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.phocos.studio.util.Studio;
@Repository
public interface StudioRepository extends JpaRepository<Studio, Integer> {

    @Query("SELECT s FROM Studio s WHERE s.studioName LIKE %:keyword% OR s.studioAddress LIKE %:keyword% OR s.studioIntro LIKE %:keyword%")
    List<Studio> findByKeyword(@Param("keyword") String keyword);
    



}
