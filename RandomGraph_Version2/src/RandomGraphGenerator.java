import java.util.Random;

/**
 * @author Zalak
 *
 */
public class RandomGraphGenerator
{
	Graph inputGraph;
	GenerateGraph generateGraph;
	 long Q = 5;
	
	/**
	 * @param target
	 */
	public RandomGraphGenerator(Graph target)
	{
		super();
		this.generateGraph = new GenerateGraph();
		this.inputGraph = target;
	}

	
	public Graph generateGraph()
	{
		//Graph randomGraph = new Graph(inputGraph.getSize());
		Graph randomGraph = inputGraph;
		//randomGraph.setAdjacencyMatrix(inputGraph.getAdjacencyMatrix());
		int []vertex_edge1;
		int []vertex_edge2;
		for(long i= 0; i<getQE(); i++)
		{
			vertex_edge1 = getToFromVertex();
			vertex_edge2 =getToFromVertex();
			
			if(!checkEdgeExistence(vertex_edge1, randomGraph))
			{
				vertex_edge1 = getNextEdge(vertex_edge1, randomGraph);
				if(vertex_edge1[0] < 0 || vertex_edge1[1] < 0)
				{
					continue;
				}
			}
				
				
			if(!checkEdgeExistence(vertex_edge2, randomGraph))
			{
				vertex_edge2 = getNextEdge(vertex_edge2, randomGraph);
				if(vertex_edge2[0] < 0 || vertex_edge2[1] < 0)
				{
					continue;
				}
			}
			
			
			if (!isUnique(vertex_edge1, vertex_edge2))
			{
				i--;
				continue;
			}
	
			boolean flag = swap(vertex_edge1, vertex_edge2, randomGraph, i);
			if(!flag)
			{
				i--;
				continue;
			}
		}
		
		generateGraph.setGraphDegreeSequence(randomGraph);
		generateGraph.setNumberOfEdges(randomGraph);
		return randomGraph;
	}
	
	
	int[] getNextEdge(int []vertex_edge, Graph graph)
	{
		int to = vertex_edge[0];
		int from = vertex_edge[1];
		boolean flag = false;
		for(int i=0; i < graph.getAdjacencyMatrix()[to].length; i++)
		{
			if(graph.getAdjacencyMatrix()[to][i] == 1)
			{
				vertex_edge[1] = i;
				flag = true;
			}
		}
		if(!flag)
		{
			for(int i=0; i < graph.getAdjacencyMatrix()[from].length; i++)
			{
				if(graph.getAdjacencyMatrix()[from][i] == 1)
				{
					vertex_edge[0] = from;
					vertex_edge[1] = i;
					flag = true;
				}
			}
			
		}
		if(!flag)
		{
			vertex_edge[0] = -1;
			vertex_edge[1] = -1;
		}
		
		return vertex_edge;
	}
	
	boolean isUnique(int[] vertex_edge1, int []vertex_edge2)
	{
		if(vertex_edge1[0] == vertex_edge2[1] || vertex_edge1[0] == vertex_edge2[0])
		{
			return false;
		}
		if(vertex_edge1[1] == vertex_edge2[1] || vertex_edge1[1] == vertex_edge2[0])
		{
			return false;
		}
		
		return true;
	}
	
	boolean swap(int[] vertex_edge1, int[] vertex_edge2, Graph graph, long i)
	{
			
		if(i % 2 == 0)
		{
			if(checkEdgeExistence(new int[] {vertex_edge1[0],  vertex_edge2[0]}, graph ))
			{
				return false;
			}
			if(checkEdgeExistence(new int[] {vertex_edge1[1],  vertex_edge2[1]}, graph ))
			{
				return false;
			}
			generateGraph.addEdges(graph, vertex_edge1[0], vertex_edge2[0]);
			generateGraph.addEdges(graph, vertex_edge1[1], vertex_edge2[1]);
		}
		else
		{
			if(checkEdgeExistence(new int[] {vertex_edge1[0],  vertex_edge2[1]}, graph ))
			{
				return false;
			}
			if (checkEdgeExistence(new int[] {vertex_edge1[1],  vertex_edge2[0]}, graph ))
			{
				return false;
			}
			generateGraph.addEdges(graph, vertex_edge1[0], vertex_edge2[1]);
			generateGraph.addEdges(graph, vertex_edge1[1], vertex_edge2[0]);
		}
		generateGraph.deleteEdges(graph, vertex_edge1[0], vertex_edge1[1]);
		generateGraph.deleteEdges(graph, vertex_edge2[0], vertex_edge2[1]);
		
		return true;
	}
	
	public long getQE()
	{
		return (Q * inputGraph.getEdgesCount());
	}
	
	/**
	 * @param vertex
	 * @param graph
	 * @return
	 */
	public boolean checkEdgeExistence(int []vertex, Graph graph)
	{
		//System.out.println("edge Existence: " + graph.getAdjacencyMatrix()[vertex[0]][vertex[1]]);
		return (graph.getAdjacencyMatrix()[vertex[0]][vertex[1]] == 1)?true : false;  
	}
	
	/**
	 * @param n
	 * @return
	 */
	public int[] getToFromVertex()
	{
		int []vertex = new int[2];
		while(true)
		{
			vertex[0] = randomNumberGenerator( inputGraph.getSize());
			vertex[1] = randomNumberGenerator( inputGraph.getSize());

			if(vertex[0] != vertex[1])
				break;
		}
		
		return vertex;
	}
	
	/**
	 * @param n
	 * @return
	 */
	public int randomNumberGenerator(int n)
	{
		long seed = System.currentTimeMillis();
		Random rand = new Random(seed);
		return rand.nextInt(n);
	}
	
	
}
