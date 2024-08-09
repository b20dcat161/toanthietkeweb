/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author dbdtoan
 */
public class Challenge {
    private String id, filename, hint;

    public Challenge() {
    }

    public Challenge(String id, String filename, String hint) {
        this.id = id;
        this.filename = filename;
        this.hint = hint;
    }

    public String getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getHint() {
        return hint;
    }
    
}
