/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.gitsimilarity.project;

import com.jrmouro.genetic.integer.CompositeStoppingCondition;
import com.jrmouro.genetic.integer.IntegerCrossover;
import com.jrmouro.genetic.integer.VectorPointsIntegerCrossover;
import com.jrmouro.gitsimilarity.mining.Mining;
import com.jrmouro.operator.simple.ConstOp;
import com.jrmouro.operator.simple.Cos;
import com.jrmouro.operator.simple.Div;
import com.jrmouro.operator.simple.Exp;
import com.jrmouro.operator.genetic.GenOpCoeffOp;
import com.jrmouro.operator.simple.Ln;
import com.jrmouro.operator.simple.Mul;
import com.jrmouro.operator.simple.Operator;
import com.jrmouro.operator.plot.PlotOp;
import com.jrmouro.operator.genetic.RangeValidity;
import com.jrmouro.operator.simple.Sin;
import com.jrmouro.operator.simple.Sub;
import com.jrmouro.operator.simple.Sum;
import com.jrmouro.operator.simple.Var;
import com.jrmouro.operator.simple.VarOp;
import com.jrmouro.operator.generator.Generator;
import com.jrmouro.operator.generator.TreeGenerator;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ronaldo
 */
public final class Project {

    public final String nameProject;
    public final double result;
    public final Var var;
    public Operator opChangedFiles, opDeletions, opInsertions;
    public final double[][] changedFilesData, deletionsData, insertionsData;
    //public final Integer changedFilesGeneration, deletionsGeneration, insertionsGeneration;

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

        new PlotOp(
                this.changedFilesData,
                this.opChangedFiles,
                this.nameProject + " - ChangedFiles",
                "tempo",
                "volume",
                0.0,
                1.0,
                0.0,
                1.0).plot();

    }

    public void plotDeletions() throws IOException {

        new PlotOp(
                this.deletionsData,
                this.opDeletions,
                this.nameProject + " - Deletions",
                "tempo",
                "volume",
                0.0,
                1.0,
                0.0,
                1.0).plot();

    }

    public void plotInsertions() throws IOException {

        new PlotOp(
                this.insertionsData,
                this.opInsertions,
                this.nameProject + " - Insertions",
                "tempo",
                "volume",
                0.0,
                1.0,
                0.0,
                1.0).plot();

    }

    public Project(URL url, Path clonePath, double result, Integer fatorNormalizedDiffs, boolean clone) throws IOException, InterruptedException, ParseException {

        this.var = new Var("x");

        Mining mining = new Mining(clonePath, url, fatorNormalizedDiffs, clone);
        this.nameProject = mining.name();
        this.result = result;

        
        
        this.changedFilesData = mining.getnDiffs().getChangedFilesData();
        this.insertionsData = mining.getnDiffs().getInsertionsData();
        this.deletionsData = mining.getnDiffs().getDeletionsData();


        Operator[] ops = {
            
            new Sum(),
            new Mul(),
            new Div(),
            new Sub(),
            new Exp(),
            new Sin(),
            new Cos(),
            new Ln(),
            new Sum(new VarOp(var)),
            new Mul(new VarOp(var)),
            new Div(new VarOp(var)),
            new Sub(new VarOp(var)),
            new Exp(new VarOp(var)),
            new Sin(new VarOp(var)),
            new Cos(new VarOp(var)),
            new Ln(new VarOp(var)),
            new VarOp(var),
            new ConstOp(-1.0),
            new ConstOp(0.1),
            new ConstOp(2.0)       
        };

        Generator generator = new TreeGenerator(2, 5);

        double[] dom = {};

        this.opChangedFiles = new GenOpCoeffOp(
                var,
                inter(this.changedFilesData),//data
                ops,//operators
                generator,
                50,//pop size
                5,// por reuse
                80,//pop limit
                new RangeValidity(var, ops, generator, dom, -1.0, 1.0),
                100,//chrom. size
                0,//leftBoundChromosome,
                Integer.MAX_VALUE - 1,//rightBoundChromosome,
                new CompositeStoppingCondition(6000, -0.0001),
                new IntegerCrossover(80),
                0.5,//crossoverRate,
                0.5,//mutationRate,
                0.3,//mutationRateGene,
                2,// aritySelection
                6000,
                100,
                0.00001,
                0.5
        );

        this.opDeletions = new GenOpCoeffOp(
                var,
                inter(this.deletionsData),//data
                ops,//operators
                generator,
                50,//pop size
                5,// por reuse
                80,//pop limit
                new RangeValidity(var, ops, generator, dom, -1.0, 1.0),
                100,//chrom. size
                0,//leftBoundChromosome,
                Integer.MAX_VALUE - 1,//rightBoundChromosome,
                new CompositeStoppingCondition(6000, -0.0001),
                new VectorPointsIntegerCrossover(40, 3),
                0.5,//crossoverRate,
                0.5,//mutationRate,
                0.3,//mutationRateGene,
                2,// aritySelection
                6000,
                100,
                0.00001,
                0.5
        );

        this.opInsertions = new GenOpCoeffOp(
                var,
                inter(this.insertionsData),//data
                ops,//operators
                generator,
                50,//pop size
                5,// por reuse
                80,//pop limit
                new RangeValidity(var, ops, generator, dom, -1.0, 1.0),
                100,//chrom. size
                0,//leftBoundChromosome,
                Integer.MAX_VALUE - 1,//rightBoundChromosome,
                new CompositeStoppingCondition(6000, -0.0001),
                new VectorPointsIntegerCrossover(40, 3),
                0.5,//crossoverRate,
                0.5,//mutationRate,
                0.3,//mutationRateGene,
                2,// aritySelection
                6000,
                100,
                0.00001,
                0.5
        );

        System.out.println();
        System.out.println(this.nameProject);
        System.out.println("\t" + this.opChangedFiles);
        System.out.println("\t" + this.opInsertions);
        System.out.println("\t" + this.opDeletions);
        System.out.println();

        this.plotChangedFiles();
        this.plotInsertions();
        this.plotDeletions();

    }

    private static double[][] inter(double[][] data) {
        
        double[][] ret = new double[2 * data.length - 1][2];

        int i = 0;
        for (; i < data.length - 1; i++) {
            ret[i * 2][0] = data[i][0];
            ret[i * 2][1] = data[i][1];
            ret[i * 2 + 1][0] = (data[i][0] + data[i + 1][0])/2.0;
            ret[i * 2 + 1][1] = (data[i][1] + data[i + 1][1])/2.0;
        }
        
        if (i < data.length) {
            ret[i * 2][0] = data[i][0];
            ret[i * 2][1] = data[i][1];
        }

        return ret;
    }

}
