package com.phocos.studio.util;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phocos.photoService.model.ReferencePicture;

public interface StudioRepository extends JpaRepository<Studio, Integer> {


}
