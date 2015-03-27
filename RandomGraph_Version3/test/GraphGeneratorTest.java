import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class GraphGeneratorTest
{
	Graph input;
	GenerateGraph g = new GenerateGraph();
	RandomGraphGenerator randomG;
	Graph[] randomGraph;
	int totalRandomGraph = 1;
	
	String filePath = "C:\\Users\\Zalak\\workspace\\RandomGraph_Version3\\Output";
	File dir = new File(filePath);
	
	void print(Graph g, String filename)
	{
		File file = new File(dir, filename);
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			StringBuilder sb = new StringBuilder();
			for(int i= 0; i< g.getSize(); i++)
			{
				sb.append("\n");
				for(int j = 0; j <g.getSize(); j++)
				{
					sb.append(g.getAdjacencyMatrix()[i][j]);
					sb.append(" ");
				}
			}
			for(int i=0; i < g.getDegreeSequence().length; i++)
			{
				sb.append("\n");
				sb.append(i);
				sb.append(":");
				sb.append(g.getDegreeSequence()[i]);
			}
		
			sb.append("\n Edges: ");
			sb.append(Long.toString(g.getEdgesCount()));
			bw.append(sb.toString());
			bw.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	int calculateDifference(Graph input, Graph random)
	{
		int difference = 0;
		for(int i= 0; i< input.getAdjacencyMatrix().length; i++)
		{
			for(int j =0; j< input.getAdjacencyMatrix()[0].length; j++)
			{
				if(input.getAdjacencyMatrix()[i][j] != random.getAdjacencyMatrix()[i][j])
				{
					difference ++;
				}
			}
		}
		return difference;
	}

	void preprocessing(Graph g[])
	{
		
	}
	
	public static void main(String args[]) throws FileNotFoundException
	{

		GraphGeneratorTest obj = new GraphGeneratorTest();
		obj.input = new Graph(5131);
		//obj.input = new Graph(13);
		obj.g.generateInputGraph(obj.input, "Scere2014Oct_index.txt");
		//obj.g.generateInputGraph(obj.input, "inputGraph1.txt");
		System.out.println("\n-------------------------------------------------------------\n");
		
		obj.randomG = new RandomGraphGenerator(obj.input);
		obj.randomGraph = new Graph[obj.totalRandomGraph];
		
		for(int i=0 ;i< obj.totalRandomGraph; i++)
		{
			obj.randomGraph[i] = new Graph(obj.input.getSize());
			obj.randomGraph[i] = obj.randomG.cloneGraph(obj.randomGraph[i]);
		}
		
		
		long startTime = System.currentTimeMillis();
		
		for(int i= 0; i< obj.totalRandomGraph; i++)
		{
			//Graph random = obj.randomG.generateGraph();
			obj.randomG.generateGraph(obj.randomGraph[i]);
			//obj.randomGraphList.add(random);
		}
		
		long endTime = System.currentTimeMillis();
	 	obj.print(obj.input, "Input.txt");
		System.out.println("Time taken: " + (endTime - startTime));
		
		for(int i = 0; i< obj.randomGraph.length; i++)
		{
			obj.print(obj.randomGraph[i], ("Output" + i + ".txt"));
			System.out.println("Difference " + i + " : " + obj.calculateDifference(obj.input, obj.randomGraph[i]));
		}
	}
}
 