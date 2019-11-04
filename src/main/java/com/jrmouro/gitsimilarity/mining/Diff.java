/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.gitsimilarity.mining;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

/**
 *
 * @author ronaldo
 */
public class Diff {
    
    final public Commit commit1, commit2;
    public int time;
    public int changedfiles = 0, insertions = 0, deletions = 0;

    public Diff(Commit c1, Commit c2) {
        this.commit1 = c1;
        this.commit2 = c2;
        this.time = Math.abs(c2.date - c1.date);
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    
    

    @Override
    public String toString() {
        
        return  //"Commit1: " + this.commit1.hash + "\n" +
                //"Commit2: " + this.commit2.hash + "\n" +
                "ChangedFiles: " + String.valueOf(this.changedfiles)
                + " - Insertions: " + String.valueOf(this.insertions)
                + " - Deletions: " + String.valueOf(this.deletions)
                 + " - Time: " + String.valueOf(this.time);
        
    }

    public Diff(Commit c1, Commit c2, int changedfiles, int insertions, int deletions) {
        this.changedfiles = changedfiles;
        this.insertions = insertions;
        this.deletions = deletions;
        this.commit1 = c1;
        this.commit2 = c2;
        this.time = Math.abs(c2.date - c1.date);
    }
    
    public static Diff parse(Diff diff, String diffStr) {

        if (diff != null) {

            String[] aux = diffStr.split(",");

            for (String string : aux) {

                String a = string.trim();
                String[] b = a.split(" ");
                if (b[1].charAt(0) == 'f') {
                    diff.changedfiles = Integer.parseInt(b[0]);
                }
                if (b[1].charAt(0) == 'i') {
                    diff.insertions = Integer.parseInt(b[0]);
                }
                if (b[1].charAt(0) == 'd') {
                    diff.deletions = Integer.parseInt(b[0]);
                }

            }
        }

        return diff;
    }   
    
    static public Diff gitDiff(Commit c1, Commit c2, Integer time, Path pathRep) throws IOException, InterruptedException {
        
        Diff ret = new Diff(c1, c2);
        ret.setTime(time);
        
        Process process = Runtime.getRuntime().exec("git diff " + c1.hash + " " + c2.hash + " --shortstat", null, new File(pathRep.toString()));
        
        StringBuilder error = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        BufferedReader stdError = new BufferedReader(
                new InputStreamReader(process.getErrorStream()));

        String line = reader.readLine();
        if (line != null) {
            Diff.parse(ret, line);
        }

        while ((line = stdError.readLine()) != null) {
            error.append(line).append("\n");
        }

        int exitVal = process.waitFor();

        if (exitVal == 0 && error.toString().length() > 0) {

            System.out.println("Error: " + error.toString());

        }

        return ret;
    }
    
    public static class NormalizedDiff{
        
        public final double time, changedfiles, insertions, deletions;

        public NormalizedDiff(double time, double changedfiles, double insertions, double deletions) {
            this.time = time;
            this.changedfiles = changedfiles;
            this.insertions = insertions;
            this.deletions = deletions;
        }

        @Override
        public String toString() {
            return "time=" + time + ", changedfiles=" + changedfiles + ", insertions=" + insertions + ", deletions=" + deletions;
        }
        
    }

}
