/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.gitsimilarity.experiment;

import com.jrmouro.genetic.chromosome.ChromosomeDouble;
import com.jrmouro.genetic.chromosome.ValidityRepresentation;
import com.jrmouro.genetic.evolutionstrategies.chromosome.ChromosomeOne;
import com.jrmouro.genetic.evolutionstrategies.evolution.EvolutionScoutSniffer;
import com.jrmouro.genetic.fitnessfunction.FitnessFunction;
import com.jrmouro.genetic.fitnessfunction.SimilarityFitnessFunction;
import com.jrmouro.gitsimilarity.mining.CanonicalPath;
import com.jrmouro.gitsimilarity.project.Project;
import java.io.IOException;
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
public class ExperimentOne implements Experiment {

    public final List<Project> projectRef;
    public final Project project;
    public final double[] aval;

    public ExperimentOne( 
            double[] aval,
            List<Project> projectRef,
            Project project) {
        this.projectRef = projectRef;
        this.project = project;
        this.aval = aval;
    }
    @Override
    public void run() {

        try {
            
            double[] result = new double[projectRef.size()];

            int i = 0;
            for (Project project : projectRef) {
                result[i++] = project.result;
            }

            double[][] matrix = new double[projectRef.size()][aval.length];

            
            System.out.println("matrix: ");
            i = 0;
            for (Project project : projectRef) {
                int j = 0;
                for (double d : aval) {
                    matrix[i][j++] = project.avalInsertions(d);
                }
                i++;
            }
            
            
            
            for (double[] ds : matrix) {
                
                for (double d : ds) {
                    
                    System.out.print(d);
                    System.out.print("   ");
                    
                }
                System.out.print("\n");
                
            }


            FitnessFunction<Double> fitness = new SimilarityFitnessFunction(matrix, result);

            double[] weight = new double[aval.length];
            double[] vector = new double[aval.length];

            
            for (double d : weight)
                d = 1.0;
            
            int j = 0;
            for (double d : aval) {
                vector[j++] = project.avalInsertions(d);
                
            }

            // um cromossomo inicial
            ChromosomeDouble c = new ChromosomeOne(weight, fitness, 0.1, new ValidityRepresentation<Double>(){
                @Override
                public boolean isValid(List<Double> representation) {
                    return true;
                }
                
            });

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
            Logger.getLogger(ExperimentOne.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IOException, InterruptedException, ParseException {

       
        //Projetos de referência
        URL url1 = new URL("https://api.github.com/repos/google/deepvariant");
        URL url3 = new URL("https://api.github.com/repos/giacomelli/GeneticSharp");

        Path projRef = Paths.get(CanonicalPath.getPath("temp").toString() + "/projRef");
        Path proj = Paths.get(CanonicalPath.getPath("temp").toString() + "/proj");

        boolean clone = false;

        if (clone) {
            CanonicalPath.deleteDir("temp");
            CanonicalPath.createDir("temp");
            CanonicalPath.createDir(projRef);
            CanonicalPath.createDir(proj);
        }
        
        double[] aval = {.2,.3,.4,.5,.6,.7,.8,.9};

        List<Project> projectList = new ArrayList();
        projectList.add(new Project(url1, Paths.get(projRef.toString(), "ref1"), 0.0, 5, clone, aval));
        projectList.add(new Project(url3, Paths.get(projRef.toString(), "ref3"), 1.0, 5, clone, aval));

        //Projeto a ser analisado
        URL gitMining = new URL("https://api.github.com/repos/jrmouro/GitMining");

        
        
        //Experimento "Piloto"
        ExperimentOne piloto = new ExperimentOne(
                aval, 
                projectList,
                new Project(gitMining, Paths.get(proj.toString(), "proj"), 0.0, 5, clone, aval));

        piloto.run();

    }

   

}
