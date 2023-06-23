package com.phocos.photoService.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoServiceRepository extends JpaRepository<PhotoService, Integer> {

}
