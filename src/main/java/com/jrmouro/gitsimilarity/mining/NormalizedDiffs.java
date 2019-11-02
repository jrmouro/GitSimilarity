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
public class NormalizedDiffs implements Iterable<NormalizedDiff> {

    final private List<NormalizedDiff> nomalizedDiffs = new ArrayList();

    public void add(NormalizedDiff diff) {
        this.nomalizedDiffs.add(diff);
    }

    public Integer size() {
        return this.nomalizedDiffs.size();
    }

    @Override
    public Iterator<NormalizedDiff> iterator() {
        return this.nomalizedDiffs.iterator();
    }

    public double[][] changedFilesData() {
        double[][] ret = new double[this.nomalizedDiffs.size()][2];
        int i = 0;
        for (NormalizedDiff thi : this) {
            ret[i][0] = thi.getNormalizedTime();
            ret[i++][1] = thi.getNormalizedChangedFiles();
        }
        return ret;
    }

    public double[][] deletionsData() {
        double[][] ret = new double[this.nomalizedDiffs.size()][2];
        int i = 0;
        for (NormalizedDiff thi : this) {
            ret[i][0] = thi.getNormalizedTime();
            ret[i++][1] = thi.getNormalizedDeletions();
        }
        return ret;
    }

    public double[][] insertionsData() {
        double[][] ret = new double[this.nomalizedDiffs.size()][2];
        int i = 0;
        for (NormalizedDiff thi : this) {
            ret[i][0] = thi.getNormalizedTime();
            ret[i++][1] = thi.getNormalizedInsertions();
        }
        return ret;
    }

    static public NormalizedDiffs getNormalizedDiffs(Path pathRep, boolean onlyMergesCommits) throws IOException, InterruptedException, ParseException {

        Diff diffRef = null;

        Commits commits = Commits.gitCommits(pathRep, onlyMergesCommits);

        if (commits.size() > 1) {
            diffRef = NormalizedDiff.gitDiff(commits.get(0), commits.get(commits.size() - 1), pathRep, null);
        }

        return getNormalizedDiffs(commits, pathRep, diffRef);
    }

    static public NormalizedDiffs getNormalizedDiffs(Commits commits, Path pathRep) throws IOException, InterruptedException {

        Diff diffRef = null;

        if (commits.size() > 1) {
            diffRef = NormalizedDiff.gitDiff(commits.get(0), commits.get(commits.size() - 1), pathRep, null);
        }

        return getNormalizedDiffs(commits, pathRep, diffRef);
    }

    static public NormalizedDiffs getNormalizedDiffs(Commits commits, Path pathRep, Diff diffRef) throws IOException, InterruptedException {

        NormalizedDiffs ret = new NormalizedDiffs();

        for (int i = 0; i < commits.size() - 1; i++) {
            ret.add(NormalizedDiff.gitDiff(commits.get(i), commits.get(i + 1), pathRep, diffRef));
        }

        return ret;
    }

    static public NormalizedDiffs getNormalizedDiffs(Commits commits, double pass, Path pathRep) throws IOException, InterruptedException {

        Diff diffRef = null;

        Integer timeTotal = 0;
        
        NormalizedDiffs ret = new NormalizedDiffs();

        if (commits.size() > 1) {

            timeTotal = commits.get(0).date - commits.get(commits.size() - 1).date;

            Double total = Double.valueOf(timeTotal);

            diffRef = NormalizedDiff.gitDiff(commits.get(0), commits.get(commits.size() - 1), pathRep, null);

            int h = 1;
            
            Double p = pass * total * h++;
            
            Integer ii = p.intValue();
            
            Commit atual = commits.get(0);

            int i = 0;
            
            for (; i < commits.size() && ii < timeTotal; i++) {

                if (commits.get(0).date - commits.get(i).date > ii) {
                    
                    ret.add(NormalizedDiff.gitDiff(atual, commits.get(i), pathRep, diffRef));            
                    
                    atual = commits.get(i);
                    
                    p = pass * total * h++;
                    
                    ii = p.intValue();
                    
                }
                
            }
            
            ret.add(NormalizedDiff.gitDiff(atual, commits.get(i), pathRep, diffRef));
            
            
        }

       

        return ret;
    }

    /*static public NormalizedDiffs getNormalizedDiffs(Commits commits, Integer indexFirst, Integer indexLast, double pass, Path pathRep) throws IOException, InterruptedException {

        
        Diff diffRef = null;
        
        if(commits.size() > 1)
            diffRef = NormalizedDiff.gitDiff(commits.get(0), commits.get(commits.size() - 1), pathRep, null);
        
        
        NormalizedDiffs ret = new NormalizedDiffs();
        int a = 1;
        int f = indexFirst;
        int i = f + (int)(a*pass);
        
        for (; i < commits.size() - 1 && i <= indexLast; i = (int)(a*pass)) {
            ret.add(NormalizedDiff.gitDiff(commits.get(f), commits.get(i), pathRep, diffRef));
            f = i;
            a++;
        }
        
        if(indexLast < commits.size() && f < indexLast)
           ret.add(NormalizedDiff.gitDiff(commits.get(f), commits.get(indexLast), pathRep, diffRef));
            
        return ret;
    }*/
    static public NormalizedDiffs getNormalizedDiffs(Commits commits, Integer indexFirst, Integer indexLast, Integer pass, Path pathRep, Diff diffRef) throws IOException, InterruptedException {

        NormalizedDiffs ret = new NormalizedDiffs();

        int f = indexFirst;
        int i = f + pass;

        for (; i < commits.size() - 1 && i <= indexLast; i = i + pass) {
            ret.add(NormalizedDiff.gitDiff(commits.get(f), commits.get(i), pathRep, diffRef));
            f = i;
        }

        if (indexLast < commits.size() && f < indexLast) {
            ret.add(NormalizedDiff.gitDiff(commits.get(f), commits.get(indexLast), pathRep, diffRef));
        }

        return ret;

    }

}
