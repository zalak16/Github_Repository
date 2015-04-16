import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Random;

import Typedef.Edge;
import Typedef.EdgeType;
import Typedef.Variables;
import Typedef.Vertex;


public class MFind
{
	
	static int digits(BigInteger data)
	{
		int ret = 0;
		while (!data.equals(BigInteger.ZERO))
		{
			//data /= 10;++ ret;
			data = data.divide(BigInteger.valueOf(10));
			++ret;
		}
		return ret;
	}
	
	
	
	public static void main(String[] args)
	{
		System.out.println("\n-------------------------------------\n");
		System.out.println("\nBenderCalc (2005, Sebastian Wernicke)\n");
		System.out.println("\n-------------------------------------\n");
		
		// Process flags and check correct usage
		System.out.println("1. Process Input Parameters and Initialize\n\n");
		
		//boolean directed = true;
		Variables var = new Variables();
		//long SMPLS =  1000000; //uint64
		short G_N = 3;
		//randlib::rand rand(time(NULL));
		long currentTime = System.currentTimeMillis();
		Random rand = new Random(currentTime);
		Init init = new Init();
		int ret = init.process_flags(args.length, args, G_N, var);
		if(ret != -1)
		{
			return;
		}
		
		
		//Initialize Graph structure from file
		System.out.println("\n2. Build Graph\n");
//		 long n,m, num_nodes, num_lonely_nodes, num_single_edges, num_mutual_edges;
//		    hash_map < edge, edgetype > edges;
//		    vector <long> num_neighbours;
//		boolean directed = true;
		
		//var.directed = directed;
		Graph64 g= new Graph64();
		try
		{
			g.read_graph(args[0], var);
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage() + "\n");
			//return 1;
		}
		
		
		//Build the degree sequence
		System.out.println(" - Building degree sequence for graph with \n");
		System.out.print(var.n + " vertices and " + var.m + " connections \n");
		
		long[] degree_r = new long[(int)var.n];
		long[] degree_c = new long[(int)var.n];
		
		
		for(Edge e : var.edges.keySet())
		{
			Vertex u = g.edge_get_u(e);
			Vertex v = g.edge_get_v(e);
			EdgeType etype = var.edges.get(e); 
			
			switch(etype.data)
			{
				case 1: //DIR_U_T_V
					degree_r[(int) u.data]++; 
					degree_c[(int)v.data]++;
					break;

				case 2: //DIR_V_T_U
					degree_r[(int)v.data]++;
					degree_c[(int)u.data]++;
					break;
					
				case 3: //UNDIR_U_V
					degree_r[(int)u.data]++; degree_c[(int)v.data]++;
					degree_r[(int)v.data]++; degree_c[(int)u.data]++;
					break;
			}
		}
		
		//##################################################################################################
		
		
		System.out.println("\n 3. Sampling \n");
		
		int total_num_subgraphs_undir[] = {1,1,1,2,6,21,112,853,11117,261080};
		//int total_num_subgraphs_dir[7]   = {1,1,2,13,199,9364,1530843};
		long num_samples = var.SMPLS;
		Graph64 subgraph_library3_undir[] = {new Graph64(new BigInteger("78")), new Graph64(new BigInteger("238"))};
	    //long subgraph_library3_dir[] = {6,12,14,36,38,46,78,102,140,164,166,174,238}; //graph64
		Graph64 subgraph_library4_undir[] = {new Graph64(new BigInteger("4382")),new Graph64(new BigInteger("4932")),
											new Graph64(new BigInteger("4958")),new Graph64(new BigInteger("13260")),
											new Graph64(new BigInteger("13278")),new Graph64(new BigInteger("31710"))}; //graph64
//	    long subgraph_library4_dir[] = {14, 28, 30, 74, 76, 78, 90, 92, 94, 204, 206, 222, 280, 282, 286,  //graph64 
//			                          328, 330, 332, 334, 344, 346, 348, 350, 390, 392, 394, 396, 398, 
//			                          404, 406, 408, 410, 412, 414, 454, 456, 458, 460, 462, 468, 470, 
//			                          472, 474, 476, 478, 856, 858, 862, 904, 906, 908, 910, 922, 924, 
//			                          926, 972, 974, 990, 2184, 2186, 2190, 2202, 2204, 2206, 2252, 2254, 
//			                          2270, 2458, 2462, 2506, 2510, 2524, 2526, 3038, 4370, 4374, 4382, 
//			                          4418, 4420, 4422, 4424, 4426, 4428, 4430, 4434, 4436, 4438, 4440, 
//			                          4442, 4444, 4446, 4546, 4548, 4550, 4556, 4558, 4562, 4564, 4566, 
//			                          4572, 4574, 4678, 4682, 4686, 4692, 4694, 4698, 4700, 4702, 4740, 
//			                          4742, 4748, 4750, 4758, 4764, 4766, 4812, 4814, 4830, 4946, 4950, 
//			                          4952, 4954, 4958, 4994, 4998, 5002, 5004, 5006, 5010, 5012, 5014, 
//			                          5016, 5018, 5020, 5022, 5058, 5062, 5064, 5066, 5068, 5070, 5074, 
//			                          5076, 5078, 5080, 5082, 5084, 5086, 6342, 6348, 6350, 6356, 6358, 
//								   	  6364, 6366, 6550, 6552, 6554, 6558, 6598, 6602, 6604, 6606, 6614, 
//									  6616, 6618, 6620, 6622, 6854, 6858, 6862, 6870, 6874, 6876, 6878, 
//									  7126, 7128, 7130, 7134, 13142, 13146, 13148, 13150, 13260, 13262, 
//									  13278, 14678, 14686, 14790, 14798, 14810, 14812, 14814, 15258, 
//	                           		  15262, 15310, 15326, 31710 };
//		
//		if (directed) {
//			bender bender(n,degree_r,degree_c,rand);
//			double elapsed = 0;
//			clock_t start_time(clock());
//				if (G_N == 3) {
//					double *res = new double[13];
//					for (int i = 0; i!= 13; ++i) {
//						
//						cout << "   Motif " << i+1 << " of 13";
//						cout.flush();
//
//						res[i] = bender.motif_sample_log(subgraph_library3_dir[i],3,num_samples);
//						 
//						for (int h = 0; h != 15 + digits(i+1); ++h)
//						cout << '\b';
//						
//					}
//					elapsed = double (clock() - start_time) / CLOCKS_PER_SEC;
//					cout.precision(3);
//					cout << " - Overall time: " << elapsed << " seconds         " << endl;
//					cout << " - Time per sample: " << (elapsed/double(total_num_subgraphs_dir[G_N]))/double(num_samples)*1000000.0 
//					     << " microseconds" << endl << endl;
//					double max = -50000000.0;
//					for (int i = 0; i!= 13; ++i) {
//						if (res[i] > max && !(res[i] != res[i]))  //test for NaN!
//						{ max = res[i];	}
//					}
//					double sum = 0.0;
//					for (int i = 0; i!= 13; ++i) {
//						if (!(res[i] != res[i]))  //test for NaN!
//						{
//							//cout << res[i];
//							res[i] = exp(res[i] - max);
//							sum += res[i];
//						}
//					}
//					cout.precision(3);
//					cout << "4. Results: " << endl;
//					for (int i = 0; i!= 13; ++i) {
//						cout << "   Motif " << string((3-digits(subgraph_library3_dir[i])),' ') 
//							 << subgraph_library3_dir[i] << ": ";
//						if (!(res[i] != res[i]))
//							cout << (res[i]/sum) << endl;
//						else
//							cout << "Not found" << endl;
//					}
//				}
//
//				if (G_N == 4) {
//					double *res = new double[199];
//					for (int i = 0; i!= 199; ++i) {
//						
//						cout << "   Motif " << i+1 << " of 199";
//						cout.flush();
//
//						res[i] = bender.motif_sample_log(subgraph_library4_dir[i],4,num_samples);
//						 
//						for (int h = 0; h != 16 + digits(i+1); ++h)
//						cout << '\b';
//						
//					}
//					elapsed = double (clock() - start_time) / CLOCKS_PER_SEC;
//					cout.precision(3);
//					cout << " - Overall time: " << elapsed << " seconds         " << endl;
//					cout << " - Time per sample: " << (elapsed/double(total_num_subgraphs_dir[G_N]))/double(num_samples)*1000000.0 
//						 << " microseconds" << endl << endl;
//					double max = -50000000.0;
//					for (int i = 0; i!= 13; ++i) {
//						if (res[i] > max && !(res[i] != res[i]))  //test for NaN!
//						{ max = res[i];	}
//					}
//					double sum = 0.0;
//					for (int i = 0; i!= 199; ++i) {
//						if (!(res[i] != res[i]))  //test for NaN!
//						{
//							//cout << res[i];
//							res[i] = exp(res[i] - max);
//							sum += res[i];
//						}
//					}
//					cout.precision(3);
//					cout << "4. Results: " << endl;
//					for (int i = 0; i!= 199; ++i) {
//						cout << "   Motif " << string((5-digits(subgraph_library4_dir[i])),' ')
//							 << subgraph_library4_dir[i] << ": ";
//						if (!(res[i] != res[i]))
//							cout << (res[i]/sum) << endl;
//						else
//							cout << "Not found" << endl;
//					}
//				}
//
//		}
//		else {
		//Undirected Graph
		Bender bender = new Bender(var.n,degree_r,rand);
		double elapsed = 0;
		long start_time = System.currentTimeMillis();//clock_t start_time(clock());
		
		if (G_N == 3)
		{
			double[] res = new double[2];
			for (int i = 0; i!= 2; ++i)
			{
				System.out.println("\n   Motif " + i+1  + " of 2");
//				cout.flush();
				res[i] = bender.motif_sample_log(subgraph_library3_undir[i],(short) 3,num_samples);
				for (int h = 0; h != (14 + digits(BigInteger.valueOf((i+1)))); ++h)
					System.out.print("\b"); //cout << '\b';
			}
			
			elapsed = (double) (System.currentTimeMillis() - start_time) / 1000; //Clocks_Per_sec = 1000
			//cout.precision(3);
			System.out.println(" - Overall time: " + elapsed + " seconds  \n");
			DecimalFormat df = new DecimalFormat("#.##");
			System.out.println(" - time per sample: " + df.format((elapsed/(double)(total_num_subgraphs_undir[G_N]))/(double)(num_samples)*1000000.0)+ " microseconds \n");
			
			double max = -50000000.0;
			for (int i = 0; i!= 2; ++i) 
			{
				if (res[i] > max && !(res[i] != res[i]))  //test for NaN!
				{
					max = res[i];	
				}
			}
			
			double sum = 0.0;
			for (int i = 0; i!= 2; ++i) 
			{
				if (!(res[i] != res[i]))  //test for NaN!
				{
					//cout << res[i];
					res[i] = Math.exp(res[i] - max);
					sum += res[i];
				}
			}
			
			System.out.println("4. Results: \n");
			
			for(int i=0; i!= 2; ++i)
			{
				System.out.print(" Motif " + df.format((3 - (digits(subgraph_library3_undir[i].data)))) + " " + subgraph_library3_undir[i].data + " : ");
				if(!(res[i] != res[i]))
				{
					System.out.println(df.format((res[i]/sum)) + " \n");
					//System.out.println((res[i]/sum) + " \n");
				}
				else
				{
					System.out.println("Not found\n");
				}
			}
			
		}	
		
		if(G_N == 4)
		{
			double[] res = new double[6];
			for(int i =0; i!=6 ; ++i)
			{
				System.out.println("\n Motif " + (i+1) + " of 6");
				
				res[i] = bender.motif_sample_log(subgraph_library4_undir[i], (short)4, num_samples);
				
				for(int h= 0; h!= (14 + digits(new BigInteger(Integer.toString((i+1))))); ++h)
				{
					System.out.println("\b");
				}
				
			}
				elapsed= (double)(System.currentTimeMillis() - start_time) / 1000; //CLOCKS_PER_SEC;
				
				System.out.println(" - Overall time: " + elapsed + " seconds  \n");
				System.out.println(" - time per sample: " + (elapsed/(double)(total_num_subgraphs_undir[G_N]))/(double)(num_samples)*1000000.0 + " microseconds \n");
				
				double max = -50000000.0;
				
				for(int i= 0; i!=13; ++i)
				{
					if(res[i] > max && !(res[i] != res[i]))
					{
						max = res[i];
					}
				}
				double sum = 0.0;
				
				for(int i=0; i!=6; ++i)
				{
					if(!(res[i] != res[i]))
					{
						res[i] = Math.exp(res[i] - max);
						sum += res[i];
					}
				}
				
				System.out.println("\n  4.Results: \n");
				
				for(int i=0; i!=6; ++i)
				{
					System.out.println("\n Motif " + (5-(digits(subgraph_library4_undir[i].data))) + " " + subgraph_library4_undir[i] + " : ");
					
					if(!(res[i] != res[i]))
					{
						System.out.println((res[i]/ sum) + " \n");
					}
					else
					{
						System.out.println("Not found \n");
					}
					
				}
				
			}
		//return 0;
	}	
}
