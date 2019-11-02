/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jrmouro.gitsimilarity.mining.CanonicalPath;
import com.jrmouro.gitsimilarity.mining.Commits;
import com.jrmouro.gitsimilarity.mining.Diff;
import com.jrmouro.gitsimilarity.mining.NormalizedDiff;
import com.jrmouro.gitsimilarity.mining.NormalizedDiffs;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ronaldo
 */
public class DiffsJUnitTest {
    
    public DiffsJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void test() throws IOException, InterruptedException, ParseException {
        
        Path pathRep = CanonicalPath.getPath("temp/projRef/ref1");
        
        Commits commits = Commits.gitCommits(pathRep, false);        
        
        NormalizedDiffs diffs = NormalizedDiffs.getNormalizedDiffs(commits, 0.1, pathRep);
        
        for (Iterator<NormalizedDiff> iterator = diffs.iterator(); iterator.hasNext();) {
            Diff next = iterator.next();
            System.out.println(next);
        }
        
    
    }
}
