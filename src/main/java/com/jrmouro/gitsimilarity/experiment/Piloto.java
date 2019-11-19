/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.gitsimilarity.experiment;

import com.jrmouro.genetic.chromosome.ChromosomeAbstract;
import com.jrmouro.genetic.chromosome.ChromosomeDouble;
import com.jrmouro.genetic.evolutionstrategies.chromosome.ChromosomeOne;
import com.jrmouro.genetic.evolutionstrategies.evolution.EvolutionScoutSniffer;
import com.jrmouro.genetic.fitnessfunction.FitnessFunction;
import com.jrmouro.genetic.fitnessfunction.SimilarityFitnessFunction;
import com.jrmouro.gitsimilarity.mining.CanonicalPath;
import com.jrmouro.gitsimilarity.project.Project;
import com.jrmouro.gitsimilarity.similarity.FactorySimilarytyFunction;
import com.jrmouro.gitsimilarity.similarity.LinearSystemSimilarityEquation;
import com.jrmouro.gitsimilarity.similarity.ParamClassFunction;
import com.jrmouro.gitsimilarity.similarity.SimilarityEquation;
import com.jrmouro.gitsimilarity.similarity.SimilarityFunction;
import com.jrmouro.gitsimilarity.similarity.WeightFunction;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ronaldo
 */
public class Piloto implements Experiment {

    //private final List<ParamClassFunction> paramclassFunctions;
    public final List<Project> projectRef;
    public final Project project;
    public final double[] aval;
    //private LinearSystemSimilarityEquation lsse;
    //private final double fatorNormalizedDiffs;

    public Piloto(
            //List<ParamClassFunction> paramclassFunctions, 
            double[] aval,
            List<Project> projectRef,
            Project project) {
        //this.paramclassFunctions = paramclassFunctions;
        this.projectRef = projectRef;
        this.project = project;
        this.aval = aval;
        //this.fatorNormalizedDiffs = fatorNormalizedDiffs;
    }

    /*public class Fitness implements FitnessFunction<Double> {

        private final LinearSystemSimilarityEquation lsse;

        public Fitness(LinearSystemSimilarityEquation lsse) {
            this.lsse = lsse;
        }

        @Override
        public double fitness(ChromosomeAbstract<Double> ca) {

            lsse.setWeights(ca.getRepresentation());

            return Math.abs(lsse.getValue());
        }

    }*/
    @Override
    public void run() {

        try {
            
            double[] result = new double[projectRef.size()];

            int i = 0;
            for (Project project : projectRef) {
                result[i++] = project.result;
            }

            double[][] matrix = new double[projectRef.size()][aval.length/* * 3*/];

            
            System.out.println("matrix: ");
            i = 0;
            for (Project project : projectRef) {
                int j = 0;
                for (double d : aval) {
                    matrix[i][j++] = project.avalChangedFiles(d);
                    //matrix[i][j++] = project.avalInsertions(d);
                    //matrix[i][j++] = project.avalDeletions(d);
                }
                //Arrays.toString(matrix[i]);
                i++;
            }
            
            DecimalFormat formatter = new DecimalFormat("#0.00");
            
            for (double[] ds : matrix) {
                
                for (double d : ds) {
                    
                    System.out.print(formatter.format(d));
                    System.out.print("   ");
                    
                }
                System.out.print("\n");
                
            }


            FitnessFunction<Double> fitness = new SimilarityFitnessFunction(matrix, result);

            double[] weight = new double[aval.length/* * 3*/];
            double[] vector = new double[aval.length/* * 3*/];

            
            for (double d : weight)
                d = 1.0;
            
            int j = 0;
            for (double d : aval) {
                vector[j++] = project.avalChangedFiles(d);
                //vector[j++] = project.avalInsertions(d);
                //vector[j++] = project.avalDeletions(d);
            }

            // um cromossomo inicial
            ChromosomeDouble c = new ChromosomeOne(weight, fitness, 0.1);

            c = (ChromosomeDouble) new EvolutionScoutSniffer(100, 0.001).evolve(c, 6000, true);

            System.out.println("Chromosome: " + c);
            
            double s = 0.0;
            for (int k = 0; k < vector.length; k++) {
                s += c.getRepresentation().get(k) * vector[k];
                weight[k] = c.getRepresentation().get(k);
            }

            System.out.println("weights: " + Arrays.toString(weight));
            System.out.println("similarity: " + s);

        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Piloto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IOException, InterruptedException, ParseException {

       
        //Projetos de referência
        URL url1 = new URL("https://api.github.com/repos/google/deepvariant");
        URL url2 = new URL("https://api.github.com/repos/EpistasisLab/tpot");
        //URL url3 = new URL("https://api.github.com/repos/giacomelli/GeneticSharp");

        Path projRef = Paths.get(CanonicalPath.getPath("temp").toString() + "/projRef");
        Path proj = Paths.get(CanonicalPath.getPath("temp").toString() + "/proj");

        boolean clone = false;

        if (clone) {
            CanonicalPath.deleteDir("temp");
            CanonicalPath.createDir("temp");
            CanonicalPath.createDir(projRef);
            CanonicalPath.createDir(proj);
        }

        List<Project> projectList = new ArrayList();
        projectList.add(new Project(url1, Paths.get(projRef.toString(), "ref1"), 1.0, 10, clone));
        projectList.add(new Project(url2, Paths.get(projRef.toString(), "ref2"), 1.0, 10, clone));
       // projectList.add(new Project(url3, Paths.get(projRef.toString(), "ref3"), 1.0, 10, clone));

        //Projeto a ser analisado
        URL gitMining = new URL("https://api.github.com/repos/jrmouro/GitMining");

        double[] aval = {.2,.3,.4/*,.5,.6,.7,.8,.9*/};
        
        //Experimento "Piloto"
        Piloto piloto = new Piloto(
                aval, 
                projectList,
                new Project(gitMining, Paths.get(proj.toString(), "proj"), 0.0, 10, clone));

        piloto.run();

    }

    //funções auxiliares
    private static LinearSystemSimilarityEquation getLSSE(List<Project> projects, List<ParamClassFunction> functions) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        LinearSystemSimilarityEquation lsse = new LinearSystemSimilarityEquation();

        for (Project p : projects) {
            lsse.add(Piloto.getSE(p, functions));
        }

        return lsse;

    }

    private static SimilarityEquation getSE(Project project, List<ParamClassFunction> functions) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        SimilarityEquation ret = new SimilarityEquation(project.result);

        for (ParamClassFunction function : functions) {
            SimilarityFunction sf = FactorySimilarytyFunction.getSimilarityFunction(function, project);
            if (sf != null) {
                ret.add(new WeightFunction(sf));
            }
        }

        return ret;
    }

}
