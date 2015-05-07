#!/bin/bash

 javac -cp `hadoop classpath`:.  src/main/java/edu/uw/nemo/model/AdjacentVertexWithEdge.java src/main/java/edu/uw/nemo/model/AdjacencyMapping.java src/main/java/edu/uw/nemo/model/Mapping.java src/main/java/edu/uw/nemo/io/Parser.java src/main/java/edu/uw/nemo/motifSignificant/mapreduce/SwitchingAlgorithmGenerateGraphMapper.java src/main/java/edu/uw/nemo/motifSignificant/mapreduce/SwitchingAlgorithmGenerateGraphReducer.java src/main/java/edu/uw/nemo/motifSignificant/mapreduce/GraphGeneratorJob.java src/main/java/edu/uw/nemo/motifSignificant/mapreduce/NemoRandomGraphMain.java 
 
  jar -cvf nemo.jar src/main/java/edu/uw/nemo/model/*.class src/main/java/edu/uw/nemo/io/Parser.class src/main/java/edu/uw/nemo/motifSignificant/mapreduce/*.class
 