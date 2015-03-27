import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GenerateGraph
{
	
	
	/**
	 * @param target
	 * @param filename
	 * @throws FileNotFoundException
	 */
	void generateInputGraph(Graph target, String filename) throws FileNotFoundException
	{
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNext())
		{
			int to = scanner.nextInt();
			int from = scanner.nextInt();
			addEdges(target, to, from);
		}
		
		setGraphDegreeSequence(target);
		setNumberOfEdges(target);
		scanner.close();
	}

	/**
	 * @param graph
	 * @param to
	 * @param from
	 */
	public void addEdges(Graph graph, int to, int from) 
	{
		if(to > graph.getSize() || from > graph.getSize())
		{
			System.out.println("Invalid input: " + to + " " + from);
			return;
		}
		
		graph.getAdjacencyMatrix()[to][from] = 1;
		graph.getAdjacencyMatrix()[from][to] = 1;
		
		setConnectedVertex(graph, to, from, 1);
		setVertexDegree(graph,to, from, 1);
	}
	
	/**
	 * @param graph
	 * @param to
	 * @param from
	 */
	public void deleteEdges(Graph graph, int to, int from) 
	{
		if(to > graph.getSize() || from > graph.getSize())
		{
			System.out.println("Invalid input");
		}
		
		graph.getAdjacencyMatrix()[to][from] = 0;
		graph.getAdjacencyMatrix()[from][to] = 0;
		
		setConnectedVertex(graph, to, from, 0);
		setVertexDegree(graph,to, from, -1);
	}
	
	
	/**
	 * @param graph
	 * @param to
	 * @param from
	 */
	public void setConnectedVertex(Graph graph, int to, int from, int value)
	{
		graph.getV()[to].getConnectedVertex()[from] = value;
		graph.getV()[from].getConnectedVertex()[to] = value;
	}

	/**
	 * @param graph
	 * @param to
	 * @param from
	 */
	public void setVertexDegree(Graph graph, int to, int from, int value)
	{
		int degree = graph.getV()[to].getDegree() + value;
		graph.getV()[to].setDegree(degree);
		
		degree = graph.getV()[from].getDegree() + value;
		graph.getV()[from].setDegree(degree);
		
	}

	/**
	 * @param graph
	 */
	void setGraphDegreeSequence(Graph graph)
	{
		int []temp = new int[graph.getSize()];
		graph.setDegreeSequence(temp);
		
		for(int i= 0; i<graph.getSize(); i++)
		{
			int degree = graph.getV()[i].getDegree();
			graph.getDegreeSequence()[degree]++;
		}
	}
	
	/**
	 * @param graph
	 */
	void setNumberOfEdges(Graph graph)
	{
		long edges = 0;
		for(int i= 0; i< graph.getDegreeSequence().length; i++)
		{
			edges = edges + (i *graph.getDegreeSequence()[i]);
		}
		
		graph.setEdgesCount(edges/2);
	}
}
