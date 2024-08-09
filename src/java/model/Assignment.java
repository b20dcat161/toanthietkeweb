/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author dbdtoan
 */
public class Assignment {
    private String id, title, filePath,teacher;

    public Assignment() {
    }

    public Assignment(String id, String title, String filePath, String teacher) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.teacher = teacher;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTeacher() {
        return teacher;
    }
    
}
