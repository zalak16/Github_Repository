package edu.uw.nemo.motifSignificant.explicitMethod;


import edu.uw.nemo.model.Mapping;

import java.util.List;
import java.util.Map;

/**
 * Created by Zalak on 4/8/2015.
 */
public class RandomGraphCanonicalLabelling {

    Mapping map;
    Map<String, List<Map.Entry<String, Long>>> canonicalSubgraphs;


    public RandomGraphCanonicalLabelling(Mapping map, Map<String, List<Map.Entry<String, Long>>> canonicalSubgraphs)
    {
        this.map = map;
        this.canonicalSubgraphs = canonicalSubgraphs;
    }


}
