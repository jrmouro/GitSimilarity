/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.gitsimilarity.project;

import com.jrmouro.genetic.chromosome.ChromosomeAbstract;
import com.jrmouro.genetic.fitnessfunction.FitnessFunction;
import com.jrmouro.genetic.integer.CompositeStoppingCondition;
import com.jrmouro.genetic.integer.IntegerChromosome;
import com.jrmouro.genetic.integer.IntegerGeneticAlgorithm;
import com.jrmouro.genetic.integer.VectorPointsIntegerCrossover;
import com.jrmouro.gitsimilarity.mining.CanonicalPath;
import com.jrmouro.gitsimilarity.mining.Mining;
import com.jrmouro.grammaticalevolution.operators.Cos;
import com.jrmouro.grammaticalevolution.operators.Div;
import com.jrmouro.grammaticalevolution.operators.OpGenerator;
import com.jrmouro.grammaticalevolution.operators.Less;
import com.jrmouro.grammaticalevolution.operators.Ln;
import com.jrmouro.grammaticalevolution.operators.Mult;
import com.jrmouro.grammaticalevolution.operators.One;
import com.jrmouro.grammaticalevolution.operators.Op;
import com.jrmouro.grammaticalevolution.operators.Pi;
import com.jrmouro.grammaticalevolution.operators.Rnd;
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
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.genetics.StoppingCondition;
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
    public final Integer changedFilesGeneration, deletionsGeneration, insertionsGeneration;

    class GA extends IntegerGeneticAlgorithm {
        
    
        
        private IntegerChromosome best = null;
        private Integer[] representation = null;
        private final Fitness fitnessFunction;
        private Op op;

        @Override
        public String toString() {
            return "GA{\n" + "\tbest=" + best + ", \n\trepresentation=" + Arrays.toString(representation) + ", \n\top=" + op + "\n}";
        }
        
        

        public GA(Fitness fitnessFunction, StoppingCondition stoppingCondition) throws OutOfRangeException {
            super(  50, //population size
                    15,
                    50, //population limit
                    fitnessFunction, // fitness function
                    64, //chromosome size
                    0, // left bound chromosome
                    Integer.MAX_VALUE - 1, // right bound chormosome
                    stoppingCondition,
                    new VectorPointsIntegerCrossover(100, 1),                   
                    .5, // crossover rate
                    .5, // mutation rate
                    .3, // mutation rate2
                    2);
            this.fitnessFunction = fitnessFunction;
        }

        public IntegerChromosome getBest() {
            return best;
        }

        public Op getOp() {
            return op;
        }
        
        

        @Override
        public IntegerChromosome run() {

            this.best = super.run();

            representation = new Integer[this.best.getRepresentation().size()];

            int i = 0;
            for (Integer integer : this.best.getRepresentation()) {
                representation[i++] = integer;
            }

            return this.best;
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
                OpGenerator generator = this.fitnessFunction.generator;
                this.op = generator.generate(this.getRepresentation(run));
            }

            return this.op;

        }
        
    }

    class Fitness implements FitnessFunction<Integer> {

        public final double[][] dados;
        final OpGenerator generator;

        public Fitness(double[][] dados, OpGenerator generator) {
            this.dados = dados;
            this.generator = generator;
        }

        public OpGenerator getGenerator() {
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

                ret -= Math.abs(a - dado[1]);

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

    public Project(URL url, Path clonePath, double result, Integer fatorNormalizedDiffs, boolean clone) throws IOException, InterruptedException, ParseException {

        this.var = new Var("x");
        
        Mining mining = new Mining(clonePath, url, fatorNormalizedDiffs, clone);
        this.nameProject = mining.name();
        this.result = result;

        Op[] ops = {
            new VarOp(var),
            new Sum(),
            new Sub(),
            new One(),
            new Pi(),
            new Rnd(),
            //new Exp(),
            new Less(),
            new Sin(),
            new Cos(),
            new Mult(),
            new Div(),
            //new ExpE(),
            new Ln()};

        OpGenerator generator = new OpGenerator(ops,var, 8);
        
        CompositeStoppingCondition scC = new CompositeStoppingCondition(1200, 0.01);
        CompositeStoppingCondition scD = new CompositeStoppingCondition(1200, 0.01);
        CompositeStoppingCondition scI = new CompositeStoppingCondition(1200, 0.01);
        
         
        

        GA gaC = new GA(new Fitness(mining.getnDiffs().getChangedFilesData(), generator), scC);
        GA gaD = new GA(new Fitness(mining.getnDiffs().getDeletionsData(), generator), scD);
        GA gaI = new GA(new Fitness(mining.getnDiffs().getInsertionsData(), generator), scI);
        
        
        
        this.opChangedFiles = gaC.getOp(true);
        this.opDeletions = gaD.getOp(true);
        this.opInsertions = gaI.getOp(true);
        
        this.changedFilesData = gaC.getFitnessFunction().dados;
        this.deletionsData = gaD.getFitnessFunction().dados;
        this.insertionsData = gaI.getFitnessFunction().dados;
        
        this.changedFilesGeneration = scC.numGenerations;
        this.insertionsGeneration = scI.numGenerations;
        this.deletionsGeneration = scD.numGenerations;
        
        this.plotChangedFiles();
        this.plotDeletions();
        this.plotInsertions();
        
        System.out.println("gaC:\n"+gaC);
        System.out.println("gaI:\n"+gaI);
        System.out.println("gaD:\n"+gaD);

    }

}
