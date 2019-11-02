/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.gitsimilarity.mining;


import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ronaldo
 */
public class Diffs implements Iterable<Diff>{
    
    final private List<Diff> diffs = new ArrayList();
    private int changedfiles = 0, insertions = 0, deletions = 0, time = 0;
    
    @Override
    public Iterator<Diff> iterator() {
        return this.diffs.iterator();
    }
    
    private void add(Diff diff){
        this.diffs.add(diff);
        this.changedfiles += diff.changedfiles;
        this.insertions += diff.insertions;
        this.deletions += diff.deletions;
        this.time += Math.abs(diff.commit1.date - diff.commit2.date);
    }

    public int getChangedfiles() {
        return changedfiles;
    }

    public int getInsertions() {
        return insertions;
    }

    public int getDeletions() {
        return deletions;
    }

    public int getTime() {
        return time;
    }
    
    public Diff get(int index){
        return this.diffs.get(index);
    }
    
    public double getNormalizedChangedfiles(int index){
        return (double)this.diffs.get(index).changedfiles/(double)this.changedfiles;
    }
    
    public double getNormalizedInsertions(int index){
        return (double)this.diffs.get(index).insertions/(double)this.insertions;
    }
    
    public double getNormalizedDeletions(int index){
        return (double)this.diffs.get(index).deletions/(double)this.deletions;
    }
    
        
    
    public int size(){
        return this.diffs.size();
    }
        
    static public Diffs gitDiffsList(Commits commits, Path pathRep) throws IOException, InterruptedException, ParseException {

        Diffs ret = new Diffs();        
        
        for (int i = 0; i < commits.size() - 1; i++) {
            ret.add(Diff.gitDiff(commits.get(i), commits.get(i + 1), pathRep));
        }

        return ret;
        
    }
    
    
}
