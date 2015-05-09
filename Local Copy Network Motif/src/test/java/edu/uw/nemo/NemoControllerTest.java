package edu.uw.nemo;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import static junit.framework.Assert.assertEquals;

public class NemoControllerTest {

    @Test
    public void assertValidMotifCount() {
        NemoController target = new NemoController(/*new Parser(), new ESUGen(), new NautyLabeler(), new DirectCalculator()*/);
        Map<String, List<Map.Entry<String, Long>>> actual = target.extract("full_scere_20140427.csv", 3, 50, 0.7);
//        Map<String, List<Map.Entry<String, Long>>> actual = target.extract("InputGraph2.csv", 3);
//        assertEquals(23, actual.size());
    }

}