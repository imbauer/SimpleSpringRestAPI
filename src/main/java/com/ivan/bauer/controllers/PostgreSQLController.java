package com.ivan.bauer.controllers;

import com.ivan.bauer.beans.Student;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import com.ivan.bauer.dao.PostgreSQLdatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@RestController
@EnableAutoConfiguration
public class PostgreSQLController {

    @RequestMapping(method = RequestMethod.GET, value="/database/records")

    @ResponseBody
    public ArrayList<Student> getStudents() {

        String strSelect = "SELECT * FROM postgres.records.students";
        PostgreSQLdatabase conn = new PostgreSQLdatabase();
        Connection connection = conn.postgreSQLConnection();

        ArrayList<Student> students = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rset = stmt.executeQuery(strSelect);

            while(rset.next()) {   // Move the cursor to the next row, return false if no more row
                Student student = new Student(
                        rset.getString("student_id"),
                        rset.getString("name"),
                        rset.getString("fin"),
                        rset.getString("age")
                );
                students.add(student);
            }

            return students;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}


