package com.sunriseprojects.profile.core.service;

import org.springframework.transaction.annotation.Transactional;

import com.sunriseprojects.profile.core.dao.ProfileDAO;

public class ProfileManagerImpl implements ProfileManager {

	ProfileDAO profileDAO;
	
	public void setprofileDAO(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }
	
	@Override
	@Transactional
	public String[] signup(String firstName, String lastName, String email,
			String password) {
		String[] res = new String[]{"0","Success"};
		try {
			profileDAO.insertMember(firstName, lastName, email, password);
		} catch(Exception e) {
			System.out.println(e.toString());	
			res = new String[]{"1","Error"};
		}
		return res;
	}

}
