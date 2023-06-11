# JVM - Priority Queue with $O(logn)$ removals

A JVM Priority Queue implementation that supports $O(logn)$ remove operations.

## Asymptotic analysis

Our implementation uses $O(n)$ extra space to maintain the indexes of all the elements in the heap
and supports arbitrary element removals $O(logn)$.

|                             | Insert    | Poll      | Peek   | Remove    |
|-----------------------------|-----------|-----------|--------|-----------|
| **Our implementation**      | $O(logn)$ | $O(logn)$ | $O(1)$ | $O(logn)$ |
| **Original Implementation** | $O(logn)$ | $O(logn)$ | $O(1)$ | $O(n)$    |

>Disclaimer: Even though our implementation is asymptotically faster,
it has a much higher constant factor and thus is not suggested to be used in programs
that do not perform multiple remove operations, or that work on small amounts of data.

## How to test

In order to test our results follow these steps:
* Execute `test_generators/Main.java`. This will generate a `GeneratedTests` folder that contains all the generated instruction files.
* Clone the original JVM and replace the `PriorityQueue.java` class with our implementation (`src/implementation/PriorityQueue.java`). A tutorial on how to do that can be found [here](https://foivos.zakkak.net/tutorials/getting_started_with_openjdk_development/). After completing this step, use the both the original JVM and the modified one to run the tests.
* To run the tests, execute `benchmark/Main.java`. This will benchmark all the generated files and produce a `Results/results.csv` file.
  * You should use the following VM parameters:
    * `-Xms15G` - This will set the initial heap size to be 15G.
    * `-Xmx15G` - This will set the maximum heap size to be 15G. (You can increase or decrease these values if you change the Generator data sizes)
    * `-XX:+UnlockExperimentalVMOptions` - To allow experimental VM options.
    * `-XX:+UseEpsilonGC` - Use EpsilonGC as a garbage collector to avoid GC.
  * This benchmark must be executed once on the original JVM and once on our implementation.
  * To create plots out of the results, transfer the generated `Results/results.csv` into the `plotter` folder. **The `Results/results.csv` file generated on our implementation should be transferred to `plotter/our_results.csv` and the results generated in the original JVM should be transferred to `plotter/original_results.csv`**. Then just execute the `main.py` script and the plots will be generated.


## More information
For more details about the project, please refer to the report that can be found at [`report/report.pdf`](report/report.pdf)
