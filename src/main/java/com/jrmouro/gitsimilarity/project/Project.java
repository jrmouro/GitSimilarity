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
import com.jrmouro.operator.coeff.Coeff;
import com.jrmouro.operator.coeff.CoeffOp;
import com.jrmouro.operator.simple.ConstOp;
import com.jrmouro.operator.simple.Cos;
import com.jrmouro.operator.simple.Div;
import com.jrmouro.operator.simple.Exp;
import com.jrmouro.operator.genetic.GenOpCoeffOp;
import com.jrmouro.operator.simple.Ln;
import com.jrmouro.operator.simple.Mul;
import com.jrmouro.operator.simple.Operator;
import com.jrmouro.operator.plot.PlotOp;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public Project(URL url, Path clonePath, double result, Integer fatorNormalizedDiffs, boolean clone, double[] aval) throws IOException, InterruptedException, ParseException {

        this.var = new Var("x");

        Mining mining = new Mining(clonePath, url, fatorNormalizedDiffs, clone);
        this.nameProject = mining.name();
        this.result = result;

        this.changedFilesData =  mining.getnDiffs().getChangedFilesData2();
        this.insertionsData =  mining.getnDiffs().getInsertionsData2();
        this.deletionsData =  mining.getnDiffs().getDeletionsData2();

        Operator[] cops = {
            new Sum(),
            new Mul(),
            new Exp(),
            new Sub()};

        Operator[] ops = {
            new VarOp(var),
            new VarOp(var),
            new VarOp(var),
            new VarOp(var),
            new VarOp(var),
            new Sum(),
            new Mul(),
            new Div(),
            new Sub(),
            new Exp(),
            new Sin(),
            new Cos(),
            new Ln(),
            new ConstOp(-1.0),
            new ConstOp(1.0),
        };

        Generator generator = new TreeGenerator(2, 5);

        CoeffOp[] coeffOps1 = CoeffOp.getCoeffOps(cops, ops);

        Coeff[] coeffs1 = CoeffOp.getCoeffs((CoeffOp[]) coeffOps1);

        List<Operator> list = new ArrayList();
        list.addAll(Arrays.asList(ops));
        list.addAll(Arrays.asList(coeffOps1));

        ops = new Operator[list.size()];
        ops = list.toArray(ops);

        this.opChangedFiles = new GenOpCoeffOp(
                inter(this.changedFilesData),//data
                var,
                coeffs1,
                ops,//operators
                generator,
                300,//pop size
                30,// por reuse
                300,//pop limit
                128,//chrom. size
                0,//leftBoundChromosome,
                Integer.MAX_VALUE - 1,//rightBoundChromosome,
                new CompositeStoppingCondition(500, -0.0001),
                new IntegerCrossover(32),
                0.5,//crossoverRate,
                0.2,//mutationRate,
                0.7,//mutationRateGene,
                2,// aritySelection
                3000,
                50,
                0.001,
                0.5,
                aval,
                0.0,
                20.0
        );

        
        CoeffOp[] coeffOps2 = CoeffOp.getCoeffOps(cops, ops);

        Coeff[] coeffs2 = CoeffOp.getCoeffs((CoeffOp[]) coeffOps2);

        list = new ArrayList();
        list.addAll(Arrays.asList(ops));
        list.addAll(Arrays.asList(coeffOps2));

        ops = new Operator[list.size()];
        ops = list.toArray(ops);
        
        this.opDeletions = new GenOpCoeffOp(
                inter(this.changedFilesData),//data
                var,
                coeffs2,
                ops,//operators
                generator,
                300,//pop size
                30,// por reuse
                300,//pop limit
                128,//chrom. size
                0,//leftBoundChromosome,
                Integer.MAX_VALUE - 1,//rightBoundChromosome,
                new CompositeStoppingCondition(500, -0.0001),
                new IntegerCrossover(32),
                0.5,//crossoverRate,
                0.2,//mutationRate,
                0.7,//mutationRateGene,
                2,// aritySelection
                3000,
                50,
                0.001,
                0.5,
                aval,
                0.0,
                20.0
        );
        
        CoeffOp[] coeffOps3 = CoeffOp.getCoeffOps(cops, ops);

        Coeff[] coeffs3 = CoeffOp.getCoeffs((CoeffOp[]) coeffOps3);

        list = new ArrayList();
        list.addAll(Arrays.asList(ops));
        list.addAll(Arrays.asList(coeffOps3));

        ops = new Operator[list.size()];
        ops = list.toArray(ops);

        this.opInsertions = new GenOpCoeffOp(
                inter(this.changedFilesData),//data
                var,
                coeffs3,
                ops,//operators
                generator,
                300,//pop size
                30,// por reuse
                300,//pop limit
                128,//chrom. size
                0,//leftBoundChromosome,
                Integer.MAX_VALUE - 1,//rightBoundChromosome,
                new CompositeStoppingCondition(500, -0.0001),
                new IntegerCrossover(32),
                0.5,//crossoverRate,
                0.2,//mutationRate,
                0.7,//mutationRateGene,
                2,// aritySelection
                3000,
                50,
                0.001,
                0.5,
                aval,
                0.0,
                20.0
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
            ret[i * 2 + 1][0] = (data[i][0] + data[i + 1][0]) / 2.0;
            ret[i * 2 + 1][1] = (data[i][1] + data[i + 1][1]) / 2.0;
        }

        if (i < data.length) {
            ret[i * 2][0] = data[i][0];
            ret[i * 2][1] = data[i][1];
        }

        return ret;
    }

}
