/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrmouro.gitsimilarity.similarity;

import com.jrmouro.gitsimilarity.project.Project;


/**
 *
 * @author ronaldo
 */
public interface SimilarityFunction {
    
    public Double getValue();
    public Object getParam();
    public Project getProject();
    
}
