/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Challenge;

/**
 *
 * @author dbdtoan
 */
public class ChallengeDao extends DBContext {

    public boolean createChallenge(String filename, String hint) {
        String query = "INSERT INTO challenges (filename, hint) VALUES (?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, filename);
            st.setString(2, hint);
            st.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Challenge> getChallenges() {
        List<Challenge> challenges = new ArrayList<>();
        String query = "SELECT * FROM challenges";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                challenges.add(new Challenge(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)));
            }
        } catch (SQLException e) {
        }
        return challenges;
    }

    public Challenge getChallengeById(String id) {
        String query = "SELECT * FROM challenges WHERE id =?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return new Challenge(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public static void main(String[] args) {
        ChallengeDao challengeDao = new ChallengeDao();
        List<Challenge> challenges = challengeDao.getChallenges();
        for (Challenge challenge : challenges) {
            System.out.println(challenge.getFilename());
        }
    }
}
