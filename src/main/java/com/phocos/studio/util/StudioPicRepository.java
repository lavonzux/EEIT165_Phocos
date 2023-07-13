package com.phocos.studio.util;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.phocos.studio.util.StudioPic;
import com.phocos.studio.util.Studio;

@Repository
public interface StudioPicRepository extends JpaRepository<StudioPic, Integer> {

	@Query("SELECT sp FROM StudioPic sp WHERE sp.studio.studioID = :studioID")
	List<StudioPic> findAllByStudioID(@Param("studioID") Integer studioID);
    
    @Query("SELECT sp FROM StudioPic sp WHERE sp.shed.shedID = :shedID")
    List<StudioPic> findAllByShedID(@Param("shedID") Integer shedID);




////	public List<StudioPic> findPicIDByShedID(Shed shedID);
//
//
//
//
////	List<StudioPic> findPicIDByShedID(Integer shedID);
//
//
//
//    @Query("SELECT sp FROM StudioPic sp JOIN sp.shed s WHERE s.shedID = :shedID")
//    List<StudioPic> findAllByShedID(@Param("shedID") Integer shedID);
//
//
////	public List<StudioPic> findPicIDByShedID(Integer shedId);


}
