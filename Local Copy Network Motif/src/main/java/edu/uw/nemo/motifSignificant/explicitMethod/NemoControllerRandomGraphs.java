package edu.uw.nemo.motifSignificant.explicitMethod;

import edu.uw.nemo.esu.ESUGen;
import edu.uw.nemo.labeler.GraphFormat;
import edu.uw.nemo.labeler.GraphLabel;
import edu.uw.nemo.model.Mapping;
import edu.uw.nemo.motifSignificant.explicitMethod.SwitchingAlgorithm.SwitchingAlgoirthmGenerateGraph;
import edu.uw.nemo.motifSignificant.explicitMethod.preserveNumberOfVertexAndEdges.GenerateGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Zalak on 4/25/2015.
 */
public class NemoControllerRandomGraphs {


     public ArrayList<RandomGraphCanonicalLabelling> randomGraphGenerationSwitchingAlgorithm(Mapping mapping, int size, int nRandomGraph, double probability)
     {
        SwitchingAlgoirthmGenerateGraph graphGenerator = new SwitchingAlgoirthmGenerateGraph(mapping);
        long start = System.currentTimeMillis();
        List<Mapping> randomGraphList = graphGenerator.generateGraph(nRandomGraph);
        long end = System.currentTimeMillis();
        System.out.print("\n Time taken to generate " + nRandomGraph + " random graphs is: " + (end - start));
        ArrayList<RandomGraphCanonicalLabelling> canonicalSubgraphList = new ArrayList<RandomGraphCanonicalLabelling>();
        int count = 1;

        for (Mapping graph : randomGraphList) {
            System.out.println("############# Random Graph " + count + "####################");
            GraphLabel label = new GraphLabel(false);
            this.enumerateSubGraphs(graph, label, size, probability);
            System.out.println("Number of subgraphs enumerated: " + label.getSubgraphCount());

            // get canonical labels with GraphLabel
            Map<String, List<Map.Entry<String, Long>>> canonicalSubgraphs = label.getCanonicalLabels();
            System.out.println("Number of canonical labels for all enumerated subgraphs: " + canonicalSubgraphs.size());
            canonicalSubgraphList.add(new RandomGraphCanonicalLabelling(graph, canonicalSubgraphs));

            for (Map.Entry<String, List<Map.Entry<String, Long>>> e : canonicalSubgraphs.entrySet()) {
                System.out.println("Cannonical Label (g6) \"" + e.getKey() + "\" has following Sub Graphs:");
                Map<String, Long> subGraphCounts = GraphFormat.countDistinctGraphs(e.getValue());
                for (Map.Entry<String, Long> c : subGraphCounts.entrySet()) {
                    System.out.println("\tSubGraph (g6) \"" + c.getKey() + "\" has count: " + c.getValue());
                }
            }
            count++;
        }
        return canonicalSubgraphList;

    }

    /**
     *
     * @param mapping
     * @param size
     * @param nRandomGraph
     * @param probability
     * @return
     */
    public ArrayList<RandomGraphCanonicalLabelling> randomGraphGenerationAlgorithm1(Mapping mapping, int size, int nRandomGraph, double probability)
    {
        GenerateGraph graphGenerator = new GenerateGraph(mapping);
        List<Mapping> randomGraphList = graphGenerator.generateRandomGraph(nRandomGraph);
        ArrayList<RandomGraphCanonicalLabelling> canonicalSubgraphList = new ArrayList<RandomGraphCanonicalLabelling>();
        int count = 1;
        long start = System.currentTimeMillis();

        for (Mapping graph : randomGraphList) {
            System.out.println("############# Random Graph " + count + "####################");
            if(count == 0 || count == 100 || count == 500 || count == 400 || count ==800 || count == 1000) {
                long end = System.currentTimeMillis();
                System.out.println("Time taken to enumerate subgraph for random graph  " + (count) + " is: " + (end - start));
            }

            GraphLabel label = new GraphLabel(false);
            this.enumerateSubGraphs(graph, label, size, probability);
            System.out.println("Number of subgraphs enumerated: " + label.getSubgraphCount());

            // get canonical labels with GraphLabel
            Map<String, List<Map.Entry<String, Long>>> canonicalSubgraphs = label.getCanonicalLabels();
            System.out.println("Number of canonical labels for all enumerated subgraphs: " + canonicalSubgraphs.size());
            canonicalSubgraphList.add(new RandomGraphCanonicalLabelling(graph, canonicalSubgraphs));

//            for (Map.Entry<String, List<Map.Entry<String, Long>>> e : canonicalSubgraphs.entrySet()) {
//                System.out.println("Cannonical Label (g6) \"" + e.getKey() + "\" has following Sub Graphs:");
//                Map<String, Long> subGraphCounts = GraphFormat.countDistinctGraphs(e.getValue());
//                for (Map.Entry<String, Long> c : subGraphCounts.entrySet()) {
//                    System.out.println("\tSubGraph (g6) \"" + c.getKey() + "\" has count: " + c.getValue());
//                }
//            }
            count++;
        }
        return canonicalSubgraphList;

    }

    private void enumerateSubGraphs(Mapping mapping, GraphLabel label, int size, double probability) {
        ESUGen generator = new ESUGen(true);
        generator.enumerateSubgraphs(mapping, label, size, probability);
    }
}
