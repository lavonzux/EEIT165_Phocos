package com.phocos.studio.util;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShedRepository extends JpaRepository<Shed, Integer> {
	List<Shed> findAllByStudioID(Integer studioID);

	Shed findByShedID(Integer shedID);
	
    @Query("SELECT s FROM Shed s WHERE s.shedType LIKE %:style%")
    List<Shed> findByStyle(String style);
    
    @Query("SELECT s FROM Shed s WHERE s.shedFee <= :maxPrice")
    List<Shed> findByPrice(@Param("maxPrice") int maxPrice);
}
