/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author dbdtoan
 */
public class Submit {
    private String id, assId, studentId,filePath;

    public Submit() {
    }

    public Submit(String id, String assId, String studentId, String filePath) {
        this.id = id;
        this.assId= assId;
        this.studentId = studentId;
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public String getAssId() {
        return assId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFilePath() {
        return filePath;
    }
     public Submit getAssSubmit(List<Submit> submits, String assId){
        for(Submit submit : submits){
            if(submit.getAssId().equals(assId)) return submit;
        }
        return null;
     }
}
