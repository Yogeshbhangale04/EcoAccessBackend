package com.ecoaccess.service;

import com.ecoaccess.dao.StaffDAO;
import com.ecoaccess.model.Staff;

public class StaffService {

    private final StaffDAO staffDAO;

    public StaffService() {
        this.staffDAO = new StaffDAO();
    }

    public Staff login(String employeeId, String password) {

        Staff staff = staffDAO.findByEmployeeId(employeeId);

        if (staff == null) {
            return null;
        }

        if (!staff.getPassword().equals(password)) {
            return null;
        }

        return staff;
    }


    public boolean isAvailable(Staff staff) {

        return staff != null
                && "Available".equalsIgnoreCase(staff.getStatus());
    }
}