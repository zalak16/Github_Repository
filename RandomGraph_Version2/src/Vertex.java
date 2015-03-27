
public class Vertex
{
	private int vertexId;
	private int []connectedVertex;
	private int degree;
	/**
	 * @param vertexId
	 * @param connectedVertex
	 */
	public Vertex(int n)
	{
		super();
		this.connectedVertex = new int[n];
		this.degree = 0;
	}
	
	/**
	 * @return the degree
	 */
	public int getDegree()
	{
		return degree;
	}

	/**
	 * @param degree the degree to set
	 */
	public void setDegree(int degree)
	{
		this.degree = degree;
	}

	/**
	 * @return the vertexId
	 */
	public int getVertexId()
	{
		return vertexId;
	}
	
	/**
	 * @param vertexId the vertexId to set
	 */
	public void setVertexId(int vertexId)
	{
		this.vertexId = vertexId;
	}
	/**
	 * @return the connectedVertex
	 */
	public int[] getConnectedVertex()
	{
		return connectedVertex;
	}
	/**
	 * @param connectedVertex the connectedVertex to set
	 */
	public void setConnectedVertex(int[] connectedVertex)
	{
		this.connectedVertex = connectedVertex;
	}

	
}
