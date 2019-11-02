/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.gitsimilarity.project;

import com.jrmouro.genetic.chromosome.ChromosomeAbstract;
import com.jrmouro.genetic.fitnessfunction.FitnessFunction;
import com.jrmouro.genetic.integer.IntegerChromosome;
import com.jrmouro.genetic.integer.IntegerGeneticAlgorithm;
import com.jrmouro.genetic.integer.IntegerStoppingCondition;
import com.jrmouro.gitsimilarity.mining.CanonicalPath;
import com.jrmouro.gitsimilarity.mining.Mining;
import com.jrmouro.grammaticalevolution.operators.Cos;
import com.jrmouro.grammaticalevolution.operators.Div;
import com.jrmouro.grammaticalevolution.operators.Exp;
import com.jrmouro.grammaticalevolution.operators.ExpE;
import com.jrmouro.grammaticalevolution.operators.GeneratorOp;
import com.jrmouro.grammaticalevolution.operators.Less;
import com.jrmouro.grammaticalevolution.operators.Ln;
import com.jrmouro.grammaticalevolution.operators.Mult;
import com.jrmouro.grammaticalevolution.operators.One;
import com.jrmouro.grammaticalevolution.operators.Op;
import com.jrmouro.grammaticalevolution.operators.Pi;
import com.jrmouro.grammaticalevolution.operators.Sin;
import com.jrmouro.grammaticalevolution.operators.Sub;
import com.jrmouro.grammaticalevolution.operators.Sum;
import com.jrmouro.grammaticalevolution.operators.Var;
import com.jrmouro.grammaticalevolution.operators.VarOp;
import com.jrmouro.plot.Plottable;
import com.jrmouro.plot.PointsFunctionPlottable;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ronaldo
 */
public final class Project {

    public final String nameProject;
    public final double result;
    public final Var var;
    public final Op opChangedFiles, opDeletions, opInsertions;
    public final double [][] changedFilesData, deletionsData, insertionsData;

    class GA extends IntegerGeneticAlgorithm {

        private Integer[] representation = null;
        private final Fitness fitnessFunction;
        private Op op;

        public GA(Fitness fitnessFunction) throws OutOfRangeException {
            super( 100, //population size
                    100, //population limit
                    fitnessFunction, // fitness function
                    20, //chromosome size
                    0, // left bound chromosome
                    130000, // right bound chormosome
                    new IntegerStoppingCondition(1200),
                    5, // crossover points
                    .3, // crossover rate
                    .3, // mutation rate
                    .2, // mutation rate2
                    10);
            this.fitnessFunction = fitnessFunction;
        }

        @Override
        public IntegerChromosome run() {

            IntegerChromosome c = super.run();

            representation = new Integer[c.getRepresentation().size()];

            int i = 0;
            for (Integer integer : c.getRepresentation()) {
                representation[i++] = integer;
            }

            return c;
        }

        public Integer[] getRepresentation(boolean run) {

            if (this.representation == null || run) {
                this.run();
            }

            return representation;
        }

        public Fitness getFitnessFunction() {
            return fitnessFunction;
        }
        
        

        public Op getOp(boolean run) {

            if (op == null || run) {
                GeneratorOp generator = this.fitnessFunction.generator;
                this.op = generator.generate(this.getRepresentation(run));
            }

            return this.op;

        }
        
    }

    class Fitness implements FitnessFunction<Integer> {

        public final double[][] dados;
        final GeneratorOp generator;

        public Fitness(double[][] dados, GeneratorOp generator) {
            this.dados = dados;
            this.generator = generator;
        }

        public GeneratorOp getGenerator() {
            return generator;
        }

        @Override
        public double fitness(ChromosomeAbstract<Integer> ca) {

            Integer[] v = new Integer[ca.getRepresentation().size()];

            int i = 0;
            for (Integer integer : ca.getRepresentation()) {
                v[i++] = integer;
            }

            Op op = generator.generate(v);

            Double ret = 0.0;

            for (double[] dado : dados) {
                var.value = dado[0];
                double a = op.aval();

                ret -= ((a - dado[1]) * (a - dado[1]));

                if (Double.isNaN(ret)) {
                    return -Double.MAX_VALUE;
                }
            }

            return ret;
        }

    }

    public double getResult() {
        return result;
    }

    public double avalChangedFiles(double x) {
        this.var.value = x;
        return this.opChangedFiles.aval();
    }
    
    public double avalDeletions(double x) {
        this.var.value = x;
        return this.opDeletions.aval();
    }
    
    public double avalInsertions(double x) {
        this.var.value = x;
        return this.opInsertions.aval();
    }
    
    public void plotChangedFiles() throws IOException {
        
        List<String> sets = new ArrayList();        
                
        sets.add("title '" + this.nameProject + " - ChangedFiles'");
        sets.add("xlabel 'time'");
        sets.add("ylabel 'volume'");
        sets.add("grid");
        sets.add("xrange [0:1]");
        sets.add("yrange [0:1]");
        sets.add("style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2");
        
        Plottable p = new PointsFunctionPlottable(
                this.changedFilesData, 
                this.opChangedFiles.toString(), 
                sets,
                CanonicalPath.getPath(this.nameProject +"_changedFilesData.txt"),
                CanonicalPath.getPath(this.nameProject +"_opChangedFiles.plot"));
        
        p.plot();
        
    }
    
    public void plotDeletions() throws IOException {
        
        List<String> sets = new ArrayList();        
                
        sets.add("title '" + this.nameProject + " - Deletions'");
        sets.add("xlabel 'time'");
        sets.add("ylabel 'volume'");
        sets.add("grid");
        sets.add("xrange [0:1]");
        sets.add("yrange [0:1]");
        sets.add("style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2");
        
        Plottable p = new PointsFunctionPlottable(
                this.deletionsData, 
                this.opDeletions.toString(), 
                sets,
                CanonicalPath.getPath(this.nameProject +"_deletionsData.txt"),
                CanonicalPath.getPath(this.nameProject +"_opDeletions.plot"));
        
        p.plot();
        
    }
    
    public void plotInsertions() throws IOException {
        
        List<String> sets = new ArrayList();        
                
        sets.add("title '" + this.nameProject + " - Insertions'");
        sets.add("xlabel 'time'");
        sets.add("ylabel 'volume'");
        sets.add("grid");
        sets.add("xrange [0:1]");
        sets.add("yrange [0:1]");
        sets.add("style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2");
        
        Plottable p = new PointsFunctionPlottable(
                this.insertionsData, 
                this.opInsertions.toString(), 
                sets,
                CanonicalPath.getPath(this.nameProject +"_insertionsData.txt"),
                CanonicalPath.getPath(this.nameProject +"_opInsertions.plot"));
        
        p.plot();
    }

    public Project(URL url, Path clonePath, double result, double fatorNormalizedDiffs) throws IOException, InterruptedException, ParseException {

        this.var = new Var("x");
        
        Mining mining = new Mining(clonePath, url, fatorNormalizedDiffs);
        this.nameProject = mining.name();
        this.result = result;

        Op[] ops = {
            new VarOp(var),
            new Sum(),
            new Sub(),
            new One(),
            new Pi(),
            new Exp(),
            new Less(),
            new Sin(),
            new Cos(),
            new Mult(),
            new Div(),
            new ExpE(),
            new Ln()};

        GeneratorOp generator = new GeneratorOp(ops, 4);

        GA gaC = new GA(new Fitness(mining.getrNdd().changedFilesData(), generator));
        GA gaD = new GA(new Fitness(mining.getrNdd().deletionsData(), generator));
        GA gaI = new GA(new Fitness(mining.getrNdd().insertionsData(), generator));
        
        this.opChangedFiles = gaC.getOp(true);
        this.opDeletions = gaD.getOp(true);
        this.opInsertions = gaI.getOp(true);
        
        this.changedFilesData = gaC.getFitnessFunction().dados;
        this.deletionsData = gaD.getFitnessFunction().dados;
        this.insertionsData = gaI.getFitnessFunction().dados;
        
        this.plotChangedFiles();
        this.plotDeletions();
        this.plotInsertions();

    }

}
