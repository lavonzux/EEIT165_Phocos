package com.phocos.studio.util;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShedRepository extends JpaRepository<Shed, Integer> {
	List<Shed> findAllByStudioID(Integer studioID);

}
