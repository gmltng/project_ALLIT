package com.prj.edu.dao;

import java.util.ArrayList;

import com.prj.edu.dto.PhotoDTO;

public interface PhotoDAO {

	void fileWrite(String photo_original, String photo_copy, int photo_pr_num, String photo_category);
	
	void photoSave(String photo_original, String photo_copy, int recruit_idx);

	ArrayList<PhotoDTO> photoList(String idx);

}
