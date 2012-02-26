# Description

This project measures he sorting duration for a text file
of a parallelized MergeSort with one, two and four threads.

After building with ant:

> ant

You find an executable jar file in:

${PROJECT_HOME}/build/jar/

Then you can start the performance test with:

java -jar MergeSort.jar <filename>

This prints the sorting durations for one, two and four parallel
threads on the console. The sorted output will be saved in
<filename>_single.out, <filename>_dual.out, <filename>_quad.out.

The <filename> should be a big text file in order to get meaningful
test results.

The MergeSort supports all data structures that implement the
Comparable-Interface not only strings.