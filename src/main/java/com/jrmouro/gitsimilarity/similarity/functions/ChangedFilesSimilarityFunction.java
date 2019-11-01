/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.gitsimilarity.similarity.functions;

import com.jrmouro.gitsimilarity.project.Project;
import com.jrmouro.gitsimilarity.similarity.ProjectSimilarityFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ronaldo
 */
public class ChangedFilesSimilarityFunction extends ProjectSimilarityFunction{
    
    private double value = 0.0;

    public ChangedFilesSimilarityFunction(Project project, Object param) {
        super(project, param);
        
        if(param instanceof Double)
            value = 0.0;
        else
            try {
                throw new Exception("DeletionsSimilarityFunction works only with Double param");
            } catch (Exception ex) {
                Logger.getLogger(DeletionsSimilarityFunction.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public Double getValue() {
        return this.value;
    }
    
}
