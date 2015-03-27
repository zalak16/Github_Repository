import java.io.FileNotFoundException;
import java.util.List;

public class GraphGeneratorTest
{
	Graph input;
	GenerateGraph g = new GenerateGraph();
	RandomGraphGenerator randomG;
	void print(Graph g)
	{
		for(int i= 0; i< g.getSize(); i++)
		{
			System.out.println("\n");
			for(int j = 0; j <g.getSize(); j++)
			{
				System.out.print(g.getAdjacencyMatrix()[i][j] + " ");
			}
		}
		
		for(int i=0; i < g.getDegreeSequence().length; i++)
		{
			System.out.print("\n" +  i + " : " + g.getDegreeSequence()[i]);
		}
		
		System.out.println("\n Edges: " + g.getEdgesCount());
	}
	
	public static void main(String args[]) throws FileNotFoundException
	{
		long startTime = System.currentTimeMillis();
		GraphGeneratorTest obj = new GraphGeneratorTest();
		//obj.input = new Graph(5131);
		obj.input = new Graph(13);
		//obj.g.generateInputGraph(obj.input, "Scere2014Oct_index.txt");
		obj.g.generateInputGraph(obj.input, "inputGraph1.txt");
		
		obj.print(obj.input);
	
		System.out.println("\n-------------------------------------------------------------\n");
		
		obj.randomG = new RandomGraphGenerator(obj.input);
		Graph random = obj.randomG.generateGraph();
		obj.print(random);
		
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (endTime - startTime));
	}
}
 