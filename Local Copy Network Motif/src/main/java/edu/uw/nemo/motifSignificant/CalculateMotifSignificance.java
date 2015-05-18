package edu.uw.nemo.motifSignificant;

import edu.uw.nemo.labeler.GraphFormat;
import edu.uw.nemo.motifSignificant.explicitMethod.RandomGraphCanonicalLabelling;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Zalak on 4/25/2015.
 */
public class CalculateMotifSignificance
{
    long estimateCount100Percent(long count, double prob)
    {
        return (long)((count * 1.0)/prob);
    }

    HashMap<String, Long> mapLabeltoCount(Map<String, List<Map.Entry<String, Long>>> graphLabel, double prob)
    {
        HashMap<String, Long> labelMapping = new HashMap<String, Long>();

        for (Map.Entry<String, List<Map.Entry<String, Long>>> e : graphLabel.entrySet())
        {
            long count =0;
            if(labelMapping.containsKey(e.getKey()))
            {
                count = labelMapping.get(e.getKey());
            }

            Map<String, Long> subGraphCounts = GraphFormat.countDistinctGraphs(e.getValue());

            for (Map.Entry<String, Long> c : subGraphCounts.entrySet())
            {
                count += c.getValue();
            }

            if(prob < 1.0 && prob > 0.0)
            {
                count = estimateCount100Percent(count, prob);
            }
            labelMapping.put(e.getKey(), count);
        }

        return labelMapping;
    }



    public void printSignificantMotif(ArrayList<RandomGraphCanonicalLabelling>randomGraphLabelList, Map<String, List<Map.Entry<String, Long>>> inputGraphLabel, double prob, int totalRandomGraph, int k)
    {
        HashMap<String, Long> inputGraphLabelCount = mapLabeltoCount(inputGraphLabel, 1.0);
        System.out.println("input Graph " +  "\n");
        print(inputGraphLabelCount);
        int index =0;
        for(RandomGraphCanonicalLabelling randomGraphLabel : randomGraphLabelList)
        {
            randomGraphLabel.labelCountMapping = mapLabeltoCount(randomGraphLabel.canonicalSubgraphs, prob);
            System.out.println("Random Graph " + index++ + "\n");
            print(randomGraphLabel.labelCountMapping);
        }

        if(totalRandomGraph >= 1000)
        {
              calculatePValue(randomGraphLabelList, inputGraphLabelCount, totalRandomGraph, k);
        }
        else
        {
            calculateZScore(randomGraphLabelList, inputGraphLabelCount,totalRandomGraph, k);
        }

    }

    double calculateMean(ArrayList<RandomGraphCanonicalLabelling> randomGraphLabelList, String motif, int N)
    {
        long count = 0;
        for (RandomGraphCanonicalLabelling randomGraphLabel : randomGraphLabelList)
        {
            if(randomGraphLabel.labelCountMapping.containsKey(motif))
            {
                count += randomGraphLabel.labelCountMapping.get(motif);
            }
        }
        return ((double)count/(double)N);
    }

    double calculateStandardDeviation(ArrayList<RandomGraphCanonicalLabelling> randomGraphLabelList, String motif, double mean, int N)
    {
        double sum = 0;
        double x=0;
        for (RandomGraphCanonicalLabelling randomGraphLabel : randomGraphLabelList)
        {
            if(randomGraphLabel.labelCountMapping.containsKey(motif))
            {
                double difference = randomGraphLabel.labelCountMapping.get(motif) - mean;
                x +=  Math.pow(difference, 2);
            }
        }
        double variance = (double)(x)/(double)(N);
        double sqrt = Math.sqrt(variance);

        DecimalFormat df = new DecimalFormat("#.###");
        return Double.valueOf(df.format(sqrt));
    }

   private void calculateZScore(ArrayList<RandomGraphCanonicalLabelling> randomGraphLabelList, HashMap<String, Long> inputGraphLabelCount, int N, int k)
    {

        HashMap<String, Double> labelZscore = new HashMap<String, Double>();
        for(Map.Entry<String, Long> inputGraph : inputGraphLabelCount.entrySet())
        {
            double mean = calculateMean(randomGraphLabelList, inputGraph.getKey(), N);
            double stdDeviation = calculateStandardDeviation(randomGraphLabelList, inputGraph.getKey(), mean, N);

            double difference = inputGraph.getValue() - mean;
            double zscore = (difference / stdDeviation);

            labelZscore.put(inputGraph.getKey(), zscore);
        }

        for(Map.Entry<String, Double> label : labelZscore.entrySet())
        {
            System.out.println("Z-Score for motif "  + label.getKey() + "for size " + k + "is:  " + label.getValue());
            if(label.getValue() >= 2.0)
            {
                System.out.println("Significant Motif for size " + k + " subgraph is " + label.getKey());
                System.out.println("Z-Score for motif " + label.getKey() + " is " + label.getValue());
            }
        }

    }

    private void calculatePValue(ArrayList<RandomGraphCanonicalLabelling> randomGraphLabelList, HashMap<String, Long> inputGraphLabelCount, int N, int k)
    {
        int cn = 0;
        HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
        for(Map.Entry<String, Long> inputGraph : inputGraphLabelCount.entrySet())
        {
            cn = 0;
            for(RandomGraphCanonicalLabelling randomGraphList : randomGraphLabelList)
            {
                for(Map.Entry<String, Long> randomGraph : randomGraphList.labelCountMapping.entrySet())
                {
                    if(inputGraph.getKey().equals(randomGraph.getKey()))
                    {
                        if(randomGraph.getValue() >= inputGraph.getValue())
                        {
                            cn++;
                        }
                    }
                }
            }
            labelCount.put(inputGraph.getKey(), cn);
        }

        for(Map.Entry<String, Integer> label : labelCount.entrySet())
        {
            double pvalue = (double)label.getValue()/(double)N;
            System.out.println("p-value for motif "  + label.getKey() + "for size " + k + "is:  " + label.getValue());
            if(pvalue < 0.01)
            {
                System.out.println("Significant Motif for size " + k + " subgraph is " + label.getKey());
                System.out.println("p-value for motif " + label.getKey() + " is " + label.getValue());
            }
        }

    }

    void print(HashMap<String, Long> graphLabelcount)
    {
       for(Map.Entry<String, Long> key: graphLabelcount.entrySet())
        {
            System.out.println(key.getKey() + " : " + key.getValue());
        }
    }
}
