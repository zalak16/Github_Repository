package edu.uw.nemo;

import edu.uw.nemo.esu.ESUGen;
import edu.uw.nemo.io.Parser;
import edu.uw.nemo.labeler.GraphFormat;
import edu.uw.nemo.labeler.GraphLabel;
import edu.uw.nemo.model.Mapping;
import edu.uw.nemo.motifSignificant.explicitMethod.preserveNumberOfVertexAndEdges.GenerateGraph;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Extracts significant subgraphs using ESU algorithm and DIRECT calculation of concentration
 * @author joglekaa
 * @author vartikav
 */
public class NemoController {

    /**
     * Enumerates the sub-graphs of given size from the graph specified by
     * the input file. Then it computes the string representation of each of the sub-graph
     * using g6 representation as defined by nauty library. Finally it computes
     * the canonical label of each sub-graph and aggregates them based on it. Thus,
     * creating a map from canonically labeled sub-graph to a list of all the representative
     * sub-graphs for that canonical label with the frequency of occurrence.
     * @param fileName A file containing the graph as list of edges. Each line is an edge represented by a pair of vertices.
     * @param size The size of the sub-graphs to generate.
     * @return The map of all the canonically labeled sub-graphs of given size found.
     */
    public Map<String, List<Map.Entry<String, Long>>> extract(String fileName, int size) {
        // build Mapping with parser
        Mapping mapping = this.parseFile(fileName);
        
        // generate motifs with ESUGen for the input subgraph size
        GraphLabel label = new GraphLabel(false);
        this.enumerateSubGraphs(mapping, label, size);
        System.out.println("Number of subgraphs enumerated: " + label.getSubgraphCount());
        
        // get canonical labels with GraphLabel
        long start = System.currentTimeMillis();
        Map<String, List<Map.Entry<String, Long>>> canonicalSubgraphs = label.getCanonicalLabels();
        System.out.println("Generating canonical labels took " + (System.currentTimeMillis() - start) + " milliseconds.");
        System.out.println("Number of canonical labels for all enumerated subgraphs: " + canonicalSubgraphs.size());
        for ( Map.Entry<String, List<Map.Entry<String, Long>>> e : canonicalSubgraphs.entrySet()) {
            System.out.println("Cannonical Label (g6) \"" + e.getKey() + "\" has following Sub Graphs:");
            Map<String, Long> subGraphCounts = GraphFormat.countDistinctGraphs(e.getValue());
            for (Map.Entry<String, Long> c : subGraphCounts.entrySet()) {
                System.out.println("\tSubGraph (g6) \"" + c.getKey() + "\" has count: " + c.getValue());
            }
        }
        
        // for each label, get standard concentration with DirectCalc
        // todo: fix standard conc integration
        // Map<String, List<Integer>> conc = calculator.standardConcentrations(canonicalSubgraphs);
        long startTime = System.currentTimeMillis();
        this.randomGraphGeneration(mapping, size);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for Random graph: " + (endTime - startTime));



        return canonicalSubgraphs;
    }
    
    private Mapping parseFile(String fileName) {
        Parser parser = new Parser();
        Mapping mapping = null;
        try {
            mapping = parser.parser(fileName);
        }
        catch (IOException ioe) {
            System.err.println("Exception while parsing file for mapping. " + ioe.getLocalizedMessage());
        }
        catch (URISyntaxException urie) {
            System.err.println("Exception while parsing file for mapping. " + urie.getLocalizedMessage());
        }
        
        return mapping;
    }

    private void randomGraphGeneration(Mapping mapping, int size)
    {
        GenerateGraph graphGenerator = new GenerateGraph(mapping);
        List<Mapping> randomGraphList = graphGenerator.generateRandomGraph(10);
        for(Mapping graph: randomGraphList)
        {
            GraphLabel label = new GraphLabel(false);
            this.enumerateSubGraphs(graph, label, size);
            System.out.println("Number of subgraphs enumerated in random Graph: " + label.getSubgraphCount());
        }

    }


    private void enumerateSubGraphs(Mapping mapping, GraphLabel label, int size) {
        ESUGen generator = new ESUGen(true);
        generator.enumerateSubgraphs(mapping, label, size);
    }
}