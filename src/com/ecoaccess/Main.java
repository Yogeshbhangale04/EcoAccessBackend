package com.ecoaccess;

import java.sql.Connection;

import com.ecoaccess.util.DBConnection;

public class Main {

    public static void main(String[] args) {

        System.out.println("EcoAccess Backend Started");

        try (Connection connection = DBConnection.getConnection()) {

            System.out.println("PostgreSQL Connected Successfully!");

        } catch (Exception e) {

            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
    }
}