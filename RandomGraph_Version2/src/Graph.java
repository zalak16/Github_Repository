
public class Graph
{
	private int size;
	private byte [][]adjacencyMatrix;
	private Vertex v[];
	private int degreeSequence[];
	long edgesCount;
	/**
	 * @param size
	 * @param adjacencyMatrix
	 * @param v
	 */
	public Graph(int size)
	{
		super();
		this.size = size;
		this.adjacencyMatrix = new byte[size][size]; 
		this.v = new Vertex[size];
		this.degreeSequence = new int[size];
		this.edgesCount = 0;
		
		for(int i= 0; i< size; i++)
		{
			v[i] = new Vertex(size); 	
		}
	}
	/**
	 * @return the edgesCount
	 */
	public long getEdgesCount()
	{
		return edgesCount;
	}
	/**
	 * @param edgesCount the edgesCount to set
	 */
	public void setEdgesCount(long edgesCount)
	{
		this.edgesCount = edgesCount;
	}
	/**
	 * @return the size
	 */
	public int getSize()
	{
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size)
	{
		this.size = size;
	}
	/**
	 * @return the adjacencyMatrix
	 */
	public byte[][] getAdjacencyMatrix()
	{
		return adjacencyMatrix;
	}
	/**
	 * @param adjacencyMatrix the adjacencyMatrix to set
	 */
	public void setAdjacencyMatrix(byte[][] adjacencyMatrix)
	{
		this.adjacencyMatrix = adjacencyMatrix;
	}
	/**
	 * @return the v
	 */
	public Vertex[] getV()
	{
		return v;
	}
	/**
	 * @param v the v to set
	 */
	public void setV(Vertex v[])
	{
		this.v = v;
	}
	/**
	 * @return the degreeSequence
	 */
	public int[] getDegreeSequence()
	{
		return degreeSequence;
	}
	/**
	 * @param degreeSequence the degreeSequence to set
	 */
	public void setDegreeSequence(int degreeSequence[])
	{
		this.degreeSequence = degreeSequence;
	}
}
