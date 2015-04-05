
import java.lang.Object;

import Util.LogGamma;
public class Bender
{
	public static final double EXP =  2.71828182845904523536028747135;
	
	public final long MSKSEG[] = {  0x0000000000000000L,
									0xFF00000000000000L,
									0x00FF000000000000L,
									0x0000FF0000000000L,
									0x000000FF00000000L,
									0x00000000FF000000L,
									0x0000000000FF0000L,
									0x000000000000FF00L,
									0x00000000000000FFL};
	
	public final long DELSEG[] = {  0x0000000000000000L,
									0x0000FFFFFFFFFFFFL,
									0xFF0000FFFFFFFFFFL,
									0xFFFF0000FFFFFFFFL,
									0xFFFFFF0000FFFFFFL,
									0xFFFFFFFF0000FFFFL,
									0xFFFFFFFFFF0000FFL,
									0xFFFFFFFFFFFF0000L};
	
	public final long MSKBIT[] = {  0x0000000000000000L,
									0x8080808080808080L,
									0x4040404040404040L,
									0x2020202020202020L,
									0x1010101010101010L,
									0x0808080808080808L,
									0x0404040404040404L,
									0x0202020202020202L,
									0x0101010101010101L};

	public final long DELBIT[] = {  0x0000000000000000L,
									0x3F3F3F3F3F3F3F3FL,
									0x9F9F9F9F9F9F9F9FL,
									0xCFCFCFCFCFCFCFCFL,
									0xE7E7E7E7E7E7E7E7L,
									0xF3F3F3F3F3F3F3F3L,
									0xF9F9F9F9F9F9F9F9L,
									0xFCFCFCFCFCFCFCFCL};
	
	private boolean directed;
	private long n;
	private long[]r;
	private long []c;
	private double b;
	private long f;
	private double[] logfac_r;
	private double[] logfac_c;
    private double[] logfac_f;
	private double[] logfac_cache;
	private long sqsum_r;
	private long sqsum_c;
	private long maxdeg;
	private LogGamma lgamma;
	//randlib::rand rand;
	Random rand;
	
	// for directed grpah constructor
//	explicit bender(long n, long *r, long *c, randlib::rand rand) {
//	      this->directed = true;
//		  this->n = n;
//		  this->r = r;
//		  this->c = c;
//		  uint64 tmp = 0;
//		  this->rand = rand;
//		  f = 0;
//		  sqsum_r = 0;
//		  sqsum_c = 0;
//		  logfac_r = new double[n];
//		  logfac_c = new double[n];
//		  maxdeg = 0;
//		  for (int i = 0; i != n; ++i) {
//			tmp += (c[i]) * (r[i]);
//			logfac_r[i] = lgamma(double(r[i]+1));
//			logfac_c[i] = lgamma(double(c[i]+1));
//			if (r[i] > maxdeg) {
//				maxdeg = r[i];
//			}
//			if (c[i] > maxdeg) {
//				maxdeg = c[i];
//			}
//			f+=r[i];
//			sqsum_r += r[i] * r[i] - r[i];
//			sqsum_c += c[i] * c[i] - c[i];
//		  }
//		  logfac_f = new double[64];
//		  for (int i = 0; i!=64; ++i) {
//			  logfac_f[i] = lgamma(double(f-i+1));
//		  }
//		  logfac_cache = new double[maxdeg+1];
//		  for (int i = 0; i<= maxdeg; ++i)
//		  {
//			  logfac_cache[i] = lgamma(double(i+1));
//		  }
//		  this->b = double(tmp)/double(f);
//	    }
	
	
		public Bender(long n, long[] r, Random rand) {
	      this.directed = false;
		  this.n = n;
		  this.r = r;
		  this.b = 0;
		  this.rand = rand;
		  this.lgamma = new LogGamma();
		  f = 0;
		  sqsum_r = 0;
		  logfac_r = new double[(int) n];
		  for (int i = 0; i != n; ++i)
		  {
			  
			logfac_r[i] = lgamma.logGamma((double)(r[i]+1)); 
			f+=r[i];
			sqsum_r += r[i] * r[i] - r[i];
		  }
	    }
	
	
}
