package edu.uw.nemo.motifSignificant.explicitMethod.SwitchingAlgorithm;

import edu.uw.nemo.io.Parser;
import edu.uw.nemo.model.Mapping;
import org.junit.*;

import java.util.HashSet;
import java.util.Hashtable;

import static org.junit.Assert.*;

/**
 * Created by Zalak on 5/6/2015.
 */
public class SwitchingAlgoirthmGenerateGraphTest
{
    private static final String FileName = "InputGraph2.csv";

    @BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code
    }

    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
    }

    @Before
    public void setUp() {
//        collection = new ArrayList();
    }

    @After
    public void tearDown() {
  //      collection.clear();
    }
    @Test
    public void testGenerateGraph() throws Exception {

    }

    @Test
    public void testGenerateGraph1() throws Exception {

    }

    @Test
    public void testGetQE() throws Exception {

    }

    @Test
    public void testGetNextEdge() throws Exception {

    }

    @Test
    public void testIsEdgeUnique() throws Exception {

    }

    @Test
    public void testCheckEdgeExistence() throws Exception
    {
        Hashtable<Integer, HashSet<Integer>> adjList = new Hashtable<Integer, HashSet<Integer>>();
        int vertexCount = 3;
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        adjList.put(0, set) ;

        set = new HashSet<Integer>();
        set.add(0);
        adjList.put(1, set);

        set = new HashSet<Integer>();
        set.add(0);
        adjList.put(2, set);

        int[] vertex = new int[2];
        vertex[0] = 0;
        vertex[1] = 2;

        SwitchingAlgoirthmGenerateGraph generateGraph = new SwitchingAlgoirthmGenerateGraph();
        assertTrue(generateGraph.checkEdgeExistence(vertex,adjList));

        vertex[0] = -1;
        vertex[1] = 2;
        assertFalse(generateGraph.checkEdgeExistence(vertex, adjList));
    }

    @Test
    public void testGetToFromVertex() throws Exception
    {
       Parser parser = new Parser();
       Mapping inputGraph = parser.parser(FileName);
       SwitchingAlgoirthmGenerateGraph generateGraph = new SwitchingAlgoirthmGenerateGraph(inputGraph);
       int [] vertex = generateGraph.getToFromVertex();
        assertTrue(vertex.length == 2);
        assertTrue(vertex[0] >= 0 && vertex[0] < inputGraph.getNodeCount());
        assertTrue(vertex[1] >= 0 && vertex[1] < inputGraph.getNodeCount());
        assertTrue(vertex[0] != vertex[1]);

    }

    @Test
    public void testRandomNumberGenerator() throws Exception {

        Parser parser = new Parser();
        Mapping inputMapping = parser.parser(FileName);
        SwitchingAlgoirthmGenerateGraph generateGraph = new SwitchingAlgoirthmGenerateGraph(inputMapping);
        assertNotNull(inputMapping);
        int n = generateGraph.randomNumberGenerator(inputMapping.getNodeCount());
        assertTrue(n >= 0 && n < inputMapping.getNodeCount());
        assertFalse(n >= inputMapping.getNodeCount());
    }

    @Test
    public void testSwap() throws Exception
    {
        Hashtable<Integer, HashSet<Integer>> adjList = new Hashtable<Integer, HashSet<Integer>>();

        HashSet<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        adjList.put(0, set) ;

        set = new HashSet<Integer>();
        set.add(0);
        set.add(3);
        adjList.put(1, set);

        set = new HashSet<Integer>();
        set.add(0);
        adjList.put(2, set);

        set = new HashSet<Integer>();
        set.add(1);
        adjList.put(3, set);


        int[] vertex_edge1 = new int[2];
        vertex_edge1[0] = 0;
        vertex_edge1[1] = 2;

        int[] vertex_edge2 = new int[2];
        vertex_edge2[0] = 1;
        vertex_edge2[1] = 3;

        SwitchingAlgoirthmGenerateGraph generateGraph = new SwitchingAlgoirthmGenerateGraph();
        assertEquals(true,generateGraph.swap(vertex_edge1, vertex_edge2, adjList,0));

        assertEquals(false,generateGraph.swap(vertex_edge1, vertex_edge2, adjList, 0) );




    }

    @Test
    public void testAddEdges() throws Exception
    {

    }

    @Test
    public void testDeleteEdges() throws Exception
    {


    }
}