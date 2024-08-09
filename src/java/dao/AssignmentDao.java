/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.security.interfaces.RSAKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Assignment;
import model.Submit;

/**
 *
 * @author dbdtoan
 */
public class AssignmentDao extends DBContext {

    public List<Assignment> getAssignments() {
        List<Assignment> assignments = new ArrayList<>();
        String query = "SELECT * FROM assignments;";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                assignments.add(new Assignment(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
            }
        } catch (Exception e) {
        }
        return assignments;
    }

    public Assignment getAssignmentByFilePath(String filePath) {
        String query = "Select * from assignments WHERE filePath = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, filePath);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return new Assignment(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));

            }
        } catch (Exception e) {
        }
        return null;
    }

    public String getFilenameById(String id) {
        String query = "Select filePath from assignments WHERE id = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean saveAssignment(String teacherId, String title, String filePath) {
        String query = "INSERT INTO assignments (title, filePath, teacherId) VALUES (?, ?, ?);";
        if (getAssignmentByFilePath(filePath) != null) {
            return false;
        }
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, title);
            st.setString(2, filePath);
            st.setString(3, teacherId);
            st.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Submit> getSubmitsions(String id) {
        List<Submit> submits = new ArrayList<>();
        String query = "Select * from submissions WHERE studentId = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                submits.add(new Submit(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (Exception e) {
        }
        return submits;
    }

    public boolean saveSubmit(String assId, String id, String fileName) {
        String query = "INSERT INTO submissions (assId, studentId, filePath) VALUES (?, ?, ?);";
//        if (getAssignmentByFilePath("submits/"+fileName) != null) {
//            return false;
//        }
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, assId);
            st.setInt(2, Integer.parseInt(id));
            st.setString(3, fileName);
            st.executeUpdate();
        } catch (NumberFormatException | SQLException e) {
            return false;
        }
        return true;
    }

    public List<Submit> getSubmitsByAssId(int assignmentId) {
        List<Submit> submissions = new ArrayList<>();
        String query = "SELECT * FROM submissions WHERE assId = ?";

        try{
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, assignmentId);
            ResultSet resultSet = st.executeQuery();

            while (resultSet.next()) {
                Submit submit= new Submit(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                submissions.add(submit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return submissions;
    }

    public static void main(String[] args) {
        AssignmentDao assignmentDao = new AssignmentDao();
        List<Assignment> assignments = assignmentDao.getAssignments();
        for (Assignment a : assignments) {
            System.out.println(a.getTitle());
        }
//        if(assignmentDao.saveSubmit("1", "1", "abcde")) System.out.println("yes");
    }

}
