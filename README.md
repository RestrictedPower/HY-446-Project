# JVM - Priority Queue with $O(logn)$ removals

A JVM Priority Queue implementation that supports $O(logn)$ remove operations.

## Table of contents

---

* [Implementation](#implementation)
* [How to test](#generators)
* [Benchmark Pipeline](#benchmark-pipeline)
    * [Generators](#generators)
    * [Benchmark](#benchmark)
    * [Graph plotter](#generators)
* [Results](#results)

## Implementation

---

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

---
In order to test our results follow these steps:
* Execute `test_generators/Main.java`. This will generate a `GeneratedTests` folder that contains all the generated instruction files.
* Execute `benchmark/Main.java`. This will benchmark all the generated files and produce a `Results/results.csv` file.
  * You should use the following VM parameters:
    * `-Xms15G` - This will set the initial heap size to be 15G.
    * `-Xmx15G` - This will set the maximum heap size to be 15G. (You can increase or decrease these values if you change the Generator data sizes) 
    * `-XX:+UnlockExperimentalVMOptions` - To allow experimental VM options.
    * `-XX:+UseEpsilonGC` - Use EpsilonGC as a garbage collector to avoid GC.
  * This benchmark must be executed once on the original JVM and once on our implementation.
  * To create plots out of the results, transfer the generated `Results/results.csv` into the `plotter` folder. **The `Results/results.csv` file generated on our implementation should be transferred to `plotter/our_results.csv` and the results generated in the original JVM should be transferred to `plotter/original_results.csv`**. Then just execute the `main.py` script and the plots will be generated.




## Benchmark Pipeline

---


### Generators

#### The generators we use for our tests:
* ReverseRemoval - _original worst case scenario/our best case scenario_
  * This generator produces output that is the worst case scenario for the original implementation of the priority queue, and it is expected to run much faster in our implementation.
* Insert4Remove1
  * This generator inserts 4 elements, and removes 1 random that is already inside the priority queue.
* InsertRemoveRandom
  * This generator takes an integer parameter $p>=1$
    * It produces remove operations with probability $P = 1/p$
    * It produces remove operations with probability $P = 1 - 1/p$
    * We run this generator with two different p values:
      * $p = 3$ - One remove operation per 3 operations
      * $p = 5$ - One remove operation per 5 operations (should be similar to the Insert4Remove1)
* Insert4Poll1 - _original best case scenario/our worst case scenario (doesn't use remove operations)_

#### How to create your own generators:

* Move into the `test_generators` directory.
* Create a class that extends the `GenericGenerator` abstract class.
* Implement the function `getInstructionList()`. In order to generate the data, this function should call the following functions:
  * `generateInsertInstruction(int value)` - Insert element with given value.
  * `generateRemoveInstruction(int value)` - Remove element with given value.
  * `generatePollInstruction()` - Poll min element.
* Create an entry of your generator in the `getAllGenerators()` function found in the `test_generators/Main` class.

### Benchmark
To benchmark, we count the execution time of each instruction file 3 times. You can modify the `BENCHMARK_TIMES` value in the file `benchmark/BenchmarkUtilities.java` 

### Graph plotter
We use python to generate the plots by using the `results.csv` files. The benchmarks use the smallest execution time of each test.

## Results

---

### TODO...