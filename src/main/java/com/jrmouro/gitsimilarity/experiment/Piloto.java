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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ronaldo
 */
public class Piloto implements Experiment {

    private final List<ParamClassFunction> paramclassFunctions;
    public final List<Project> projectRef;
    public final Project project;
    private LinearSystemSimilarityEquation lsse;
    //private final double fatorNormalizedDiffs;

    public Piloto(List<ParamClassFunction> paramclassFunctions, List<Project> projectRef, Project project) {
        this.paramclassFunctions = paramclassFunctions;
        this.projectRef = projectRef;
        this.project = project;
        //this.fatorNormalizedDiffs = fatorNormalizedDiffs;
    }

    public class Fitness implements FitnessFunction<Double> {

        private final LinearSystemSimilarityEquation lsse;

        public Fitness(LinearSystemSimilarityEquation lsse) {
            this.lsse = lsse;
        }

        @Override
        public double fitness(ChromosomeAbstract<Double> ca) {

            lsse.setWeights(ca.getRepresentation());

            return Math.abs(lsse.getValue());
        }

    }

    @Override
    public void run() {

        try {
            //
            // Gera o Sistema de Equações de Similaridade
            lsse = Piloto.getLSSE(projectRef, paramclassFunctions);
            
            System.out.println(lsse);
            
            // FitnessFunction
            Fitness fitness = new Fitness(lsse);
            
            // um cromossomo inicial
            ChromosomeDouble c = new ChromosomeOne(lsse.getWeights(), fitness, 0.1);
            
            c = (ChromosomeDouble) new EvolutionScoutSniffer(100, 0.001).evolve(c, 100, false);
            
            SimilarityEquation res = getSE(project, paramclassFunctions);
            
            res.setWeights(c.getRepresentation());
            
            System.out.println("weights: " + c);
            
            System.out.println("similarity: " + res.getValue());
            
            
        } catch (ClassNotFoundException | 
                NoSuchMethodException | 
                InstantiationException | 
                IllegalAccessException | 
                IllegalArgumentException | 
                InvocationTargetException ex) {
            Logger.getLogger(Piloto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IOException, InterruptedException, ParseException {

        String deletions = "com.jrmouro.gitsimilarity.similarity.functions.DeletionsSimilarityFunction";
        String changedFiles = "com.jrmouro.gitsimilarity.similarity.functions.ChangedFilesSimilarityFunction";
        String insertions = "com.jrmouro.gitsimilarity.similarity.functions.InsertionsSimilarityFunction";

        List<ParamClassFunction> funcoes = new ArrayList();

        for (double d = 0.1; d < 1.0; d = d + 0.1) {
            funcoes.add(new ParamClassFunction(Class.forName(deletions), d));
            funcoes.add(new ParamClassFunction(Class.forName(insertions), d));
            funcoes.add(new ParamClassFunction(Class.forName(changedFiles), d));
        }

        //Projetos de referência
        URL url1 = new URL("https://api.github.com/repos/bcoin-org/bcoin");
        URL url2 = new URL("https://api.github.com/repos/ptwobrussell/Mining-the-Social-Web");
        URL url3 = new URL("https://api.github.com/repos/zone117x/node-open-mining-portal");

        CanonicalPath.deleteDir("temp");
        CanonicalPath.createDir("temp");
        Path projRef = Paths.get(CanonicalPath.getPath("temp").toString() + "/projRef");
        Path proj = Paths.get(CanonicalPath.getPath("temp").toString() + "/proj");
        CanonicalPath.createDir(projRef);
        CanonicalPath.createDir(proj);

        List<Project> projectList = new ArrayList();
        projectList.add(new Project(url1, Paths.get(projRef.toString(), "ref1"), 1.0, 0.1));
        projectList.add(new Project(url2, Paths.get(projRef.toString(), "ref2"), 1.0, 0.1));
        projectList.add(new Project(url3, Paths.get(projRef.toString(), "ref3"), 1.0, 0.1));

        //Projeto a ser analisado
        URL gitMining = new URL("https://api.github.com/repos/jrmouro/GitMining");

        //Experimento "Piloto"
        Piloto piloto = new Piloto(funcoes, projectList, new Project(gitMining, Paths.get(proj.toString(), "proj"), 1.0, 0.1));

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
