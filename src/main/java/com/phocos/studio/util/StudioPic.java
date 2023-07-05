package com.phocos.studio.util;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "StudioPic")
@Data
public class StudioPic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "studioPicID")
	private int studioPicID;
	
	
	@Column(name = "studioPicName")
	private String studioPicName;
	
	
	@Lob
	@Column(name = "studioPicFile")
	private byte[] studioPicFile;
	
	@Nullable
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studioID")
	private Studio studio;
	
	@Nullable
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shedID")
	private Shed shed;
	
	public StudioPic() {
		
	}

	public int getStudioPicID() {
		return studioPicID;
	}

	public void setStudioPicID(int studioPicID) {
		this.studioPicID = studioPicID;
	}

	public String getStudioPicName() {
		return studioPicName;
	}

	public void setStudioPicName(String studioPicName) {
		this.studioPicName = studioPicName;
	}

	public byte[] getStudioPicFile() {
		return studioPicFile;
	}

	public void setStudioPicFile(byte[] studioPicFile) {
		this.studioPicFile = studioPicFile;
	}

	public Studio getStudio() {
		return studio;
	}

	public void setStudio(Studio studio) {
		this.studio = studio;
	}

	public Shed getShed() {
		return shed;
	}

	public void setShed(Shed shed) {
		this.shed = shed;
	}
	
}
