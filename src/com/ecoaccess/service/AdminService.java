package com.ecoaccess.service;

import com.ecoaccess.dao.AdminDAO;
import com.ecoaccess.model.Admin;

public class AdminService {

    private final AdminDAO adminDAO;

    public AdminService() {
        this.adminDAO = new AdminDAO();
    }

    public Admin login(String email, String password) {

        Admin admin = adminDAO.findByEmail(email);

        if (admin == null) {
            return null;
        }

        if (!admin.getPassword().equals(password)) {
            return null;
        }

        return admin;
    }
}