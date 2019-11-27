/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.gitsimilarity.mining;

import com.jrmouro.gitsimilarity.mining.Diff.NormalizedDiff;
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
public class Diffs implements Iterable<Diff> {

    final private List<Diff> diffs = new ArrayList();
    private int changedfiles = 0, insertions = 0, deletions = 0, time = 0;
    
    @Override
    public Iterator<Diff> iterator() {
        return this.diffs.iterator();
    }

    private void add(Diff diff) {
        this.diffs.add(diff);
        this.changedfiles += diff.changedfiles;
        this.insertions += diff.insertions;
        this.deletions += diff.deletions;
        this.time = diffs.get(diffs.size() - 1).commit2.date - diffs.get(0).commit1.date;
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

    public Diff get(int index) {
        return this.diffs.get(index);
    }

    public int size() {
        return this.diffs.size();
    }

    static public Diffs gitDiffs(Commits commits, Path pathRep) throws IOException, InterruptedException, ParseException {

        Diffs ret = new Diffs();

        for (int i = 0; i < commits.size() - 1; i++) {
            ret.add(Diff.gitDiff(commits.get(i), commits.get(i + 1), commits.get(i + 1).date - commits.get(0).date, pathRep));
        }

        return ret;

    }

    
    
    public static class NormalizedDiffs implements Iterable<Diff.NormalizedDiff>{
        
        final List<NormalizedDiff> diffs = new ArrayList();

        @Override
        public Iterator<NormalizedDiff> iterator() {
            return this.diffs.iterator();
        }

        public final void add(NormalizedDiff diff){
            this.diffs.add(diff);
        }
        
        public double[][] getChangedFilesData(){
            double[][] ret = new double[this.diffs.size() + 1][2];
            int i = 1;
            for (NormalizedDiff diff : diffs) {
                ret[i][0] = diff.time;
                ret[i++][1] = diff.changedfiles;
            }
            return ret;
        }
        
        public double[][] getInsertionsData(){
            double[][] ret = new double[this.diffs.size() + 1][2];
            int i = 1;
            for (NormalizedDiff diff : diffs) {
                ret[i][0] = diff.time;
                ret[i++][1] = diff.insertions;
            }
            return ret;
        }
        
        public double[][] getDeletionsData(){
            double[][] ret = new double[this.diffs.size() + 1][2];
            int i = 1;
            for (NormalizedDiff diff : diffs) {
                ret[i][0] = diff.time;
                ret[i++][1] = diff.deletions;
            }
            return ret;
        }
        
        public double[][] getChangedFilesData2(){
            double[][] ret = new double[this.diffs.size()][2];
            int i = 0;
            for (NormalizedDiff diff : diffs) {
                ret[i][0] = diff.time;
                ret[i++][1] = diff.changedfiles;
            }
            return ret;
        }
        
        public double[][] getInsertionsData2(){
            double[][] ret = new double[this.diffs.size()][2];
            int i = 0;
            for (NormalizedDiff diff : diffs) {
                ret[i][0] = diff.time;
                ret[i++][1] = diff.insertions;
            }
            return ret;
        }
        
        public double[][] getDeletionsData2(){
            double[][] ret = new double[this.diffs.size()][2];
            int i = 0;
            for (NormalizedDiff diff : diffs) {
                ret[i][0] = diff.time;
                ret[i++][1] = diff.deletions;
            }
            return ret;
        }
        
    }
    
    static public Diffs.NormalizedDiffs getNormalizedDiffs(Diffs diffs, Integer pass) {

        Diffs.NormalizedDiffs ret = new Diffs.NormalizedDiffs();

        if (diffs.size() > 0) {

            Double total = Double.valueOf(diffs.time);

            int h = 1;

            Double p = total / pass * h++;

            Integer ii = p.intValue();

            Integer dele = 0, chan = 0, inse = 0;

            int i = 0;

            for (; i < diffs.size(); i++) {

                dele += diffs.get(i).deletions;
                inse += diffs.get(i).insertions;
                chan += diffs.get(i).changedfiles;

                if (diffs.get(i).time >= ii) {
                    
                    ret.add(new Diff.NormalizedDiff(
                            Double.valueOf(diffs.get(i).time) / Double.valueOf(diffs.time),
                            Double.valueOf(chan) / Double.valueOf(diffs.changedfiles),
                            Double.valueOf(inse) / Double.valueOf(diffs.insertions),
                            Double.valueOf(dele) / Double.valueOf(diffs.deletions)
                    ));

                    dele = chan = inse = 0;

                    p = total / pass * h++;

                    ii = p.intValue();

                }

            }

        }

        return ret;
    }

}
