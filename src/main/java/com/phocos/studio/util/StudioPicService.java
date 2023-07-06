package com.phocos.studio.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.photoService.model.ReferencePicture;
import com.phocos.studio.util.Studio;
import com.phocos.studio.util.StudioRepository;
import com.phocos.studio.util.StudioPic;
import com.phocos.studio.util.StudioPicRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudioPicService {

	
	@Autowired
	private StudioPicRepository spRepo;
	
	@Autowired
	private StudioRepository sRepo;
	
	
	public StudioPic createEntry(StudioPic pic) {
		StudioPic savedPicture = spRepo.save(pic);
		return savedPicture;
	}
	
	
	
	
	public StudioPic readOneEntry(int studioPicID) {
		Optional<StudioPic> optional = spRepo.findById(studioPicID);
		
		if (optional.isEmpty()) {
			return null;
		}else {
			return optional.get();
		}
	}
	
	
	
	public List<StudioPic> readMultiPicture(List<Integer> studioPicIDList){
		
		ArrayList<StudioPic> pictureList = new ArrayList<>();
		for (Integer studioPicID : studioPicIDList) {
			Optional<StudioPic> optional = spRepo.findById(studioPicID);
			if (optional.isPresent()) {
				pictureList.add(optional.get());
			}
		}
		
		return pictureList;
	}
	
	
	public StudioPic readFirstByStudioID(int studioID) {
	    Optional<Studio> optional = sRepo.findById(studioID);
	    if (optional.isEmpty()) {
	        System.out.println("Cannot find the Studio!");
	        return null;
	    }

	    List<StudioPic> sPic = spRepo.findAllByStudioID(studioID);
	    if (sPic.isEmpty()) {
	        System.out.println("Cannot find related ref Pics!");
	        return null;
	    }

	    System.out.println("Returning the first ref pic! " + sPic + " " + studioID);
	    return sPic.get(0);
	}
//    public List<StudioPic> findPicByShedID(Integer shedID) {
//        // 根据shedID查找相关的StudioPic
//        List<StudioPic> studioPics = spRepo.findPicByShedID(shedID.toString());
//        return studioPics;
//    }
	
	
//    public List<StudioPic> findPicByShedID(Integer shedID) {
//        return StudioPicRepository.findAllByShedID(shedID);
//    }
	
//	public List<StudioPic> findPicByShedID(Integer shedID) {
//		
//		System.out.println("checking if "+shedID+" exist......");
//		Optional<Studio> optional = sRepo.findById(shedID);
//		if (optional.isEmpty()) {
//			System.out.println("Cannot find the Studio!");
//			return null;
//		}
//		
//		System.out.println("looking for Ref Pic IDs for: "+optional.get().getStudioPicID());
//		List<StudioPic> sPicsList = spRepo.findAllByStudioPicID(optional.get().getStudioPicID());
//		if (sPicsList.size()==0) {
//			System.out.println("Cannot find related ref Pics!");
//			return null;
//		}
//		
//		return sPicsList;
//	}
	
	
	public StudioPic deleteStudioPic(int studioPicID) {
		Optional<StudioPic> optional = spRepo.findById(studioPicID);
		
		if (optional.isEmpty()) { return null; }
		
		spRepo.deleteById(optional.get().getStudioPicID());
		return optional.get();
	}

	
    
	public List<StudioPic> getStudioPicsByShedID(Integer shedID) {
	    return spRepo.findAllByShedID(shedID);
	}




	public void deleteByStudioPicID(Integer studioPicID) {
		spRepo.deleteById(studioPicID);
		
	}




	public List<StudioPic> getStudioPicsByStudioID(Integer studioID) {
	    return spRepo.findAllByStudioID(studioID);
	}
    
//    public List<Integer> findPicIDByShedID(Shed shed) {
//        Integer shedID = shed.getShedID();a
//        return spRepo.findPicIDByShedID(shedID);
//    }


//    public List<Integer> findPicIDByShedID(Shed shedID) {
//        List<StudioPic> studioPics = spRepo.findPicIDByShedID(shedID);
//        List<Integer> studioPicIDs = new ArrayList<>();
//        
//        for (StudioPic studioPic : studioPics) {
//            studioPicIDs.add(studioPic.getStudioPicID());
//        }
//        
//        return studioPicIDs;
//    }
}
