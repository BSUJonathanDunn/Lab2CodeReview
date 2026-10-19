# Project #2: Stardew Valley

* Author: Jonathan Dunn
* Class: CS321 Section #4001
* Semester: Fall 2026

## Overview

Simulates a priority based task management system by simulating the
Stardew Valley video game where the player is in charge of doing
tasks to gain money or befriend the other characters. This program
simulates tasks done within a given number of days by managing
task generation, energy management, player status and money generation
depending on the task generated, what time it was generated and its
current priority amongst the other waiting tasks.


## Reflection

This project was very fun, yet very challenging and I felt was a great
project to get used to the maxheap data structure as well as implementing
it within a priority based event system. Thankfully, the information from
the textbook and lectures made it easy to conceptualize how a max heap was
supposed to work. The pseudocode given, and when adjusted to start at
an index of 0, made it so much easier to write the actual code for the
data structure class. The only problem I could say that I had was at
some point, I somehow stopped treating the heap as an actual heap
(where most indexes have parents, left, and right children) and started
treating each element was tied to each other in sequence (0 to n), rather
than parent to child (ex 1 to 2 and 3,etc). This caused some problems with
heapify later, but was caught during testing.

Another problem I had was trying to figure out the percentage at which
tasks should be added, I knew that we passed a probability double
to task generator, but it didn't click that we just had to use a random
number generator, generate a random value between 0 and 1 and compare it 
to the task-generation probability. For some reason, I thought it would
be more complicated than that. Lastly, trying to make the MaxHeap class generic
was a lot more challenging than I first thought. The only thing I knew for sure
was that the elements within the heap had to be comparable. I somehow mixed up
Task class specific key functions with generic, and couldn't figure out how to
make it work with other classes. Until I remembered that each class, if comparable,
must have their own compareTo() method, so that would have been the key.
I then ran into a problem with increaseKey, because we couldn't know if an element's
key was its own value or stored within the object and how it could be accessed.
The workaround I found was to create a new element with the given priority, compare
it to the original element and then replace it with the new element restore the 
max-heap properties.

## Compiling and Using

To use Stardew Valley, the user should compile the program using this
command in the terminal:
```
    javac MyLifeInStardew.java
```

The program accepts the following command-line arguments:
```
    java MyLifeInStardew <max-priority-level> <time-to-increment-priority> 
                         <total-simulation-time> <task-generation-probability>
                         [<seed>]
```
Where:
```
-<max-priority-level>: Integer representing the maximum priority that a task has, the higher the priority,
                       the more likely the task would be completed next. Must be 1 or greater.
-<time-to-increment-priority>: Integer representing amount of time needed until a task's priority is increased
                               if it has not been completed.
-<total-simulation-time>: Integer representing the total number of days the simulation will last.
-<task-generation-probability>: Double from 0 to 1, that is used to decide if a new task is created each
                                hour.
-[seed]: Optional. User can give a random seed, used to reproduce results for testing purposes.
```

## Results 

Tests for this program included a custom testing class: MaxHeapTest.java and a run-tests script.
The run-tests script tests the MyLifeInStardew simulation.
The provided test cases were run using the run-tests.sh script:
```
    ./run-tests.sh
```
We were able to get these results:

```
creud@Creudence MINGW64 /d/BSU/CS321/p2-BSUJonathanDunn (master)
$ ./run-tests.sh
===============================================================================
Running Test 1: java MyLifeInStardew 5 1 1 0.5 123  
 ---- Test 1 output matches! ---- 
===============================================================================
Running Test 2: java MyLifeInStardew 10 1 5 0.5 1234  
 ---- Test 2 output matches! ---- 
===============================================================================
Running Test 3: java MyLifeInStardew 10 1 5 0.75 -1234  
 ---- Test 3 output matches! ---- 
===============================================================================
Running Test 4: java MyLifeInStardew 10 5 5 0.75 1  
 ---- Test 4 output matches! ---- 
===============================================================================
Running Test 5: java MyLifeInStardew 100 1 10 0.9 123456789  
 ---- Test 5 output matches! ---- 
===============================================================================
Running Test 6: java MyLifeInStardew 100 5 10 0.99 -12345  
 ---- Test 6 output matches! ---- 
===============================================================================
```
<br>
All six provided test cases produced matching output.
<br>
The custom testing class: MaxHeapTest.java uses the JUnit Jupiter API and when 
run using the Visual Studio Code Testing panel:
<br>

```
    MaxHeapTest

    ✓ increaseKeyTwoTest
    ✓ increaseKeyNegativeIndexTest
    ✓ increaseKeyPastHeapTest
    ✓ insertNullTest
    ✓ insertDescendingTest
    ✓ emptyTestTrue
    ✓ heapExpansionTest
    ✓ insertRandomTest
    ✓ integerHeapTest
    ✓ extractMaxOneTest
    ✓ insertAscendingTest
    ✓ extractMaxEmptyTest
    ✓ emptyTestFalse
    ✓ extractMaxTwoTest
    ✓ insertTest
    ✓ arrayToHeapTest
    ✓ maxEmptyTest
    ✓ increaseKeyDecreaseTest
    ✓ increaseKeyOneTest

    19 tests passed
    Runtime: 168 ms
```

All tests passed when testing the functionality of the max-heap data structure. 
These tests included creating empty heaps using the default and overloaded constructors, 
inserting elements, constructing heaps from arrays, extracting the maximum element, increasing 
element keys, and verifying error handling.

## Sources used
The only other sources used were an outside website for getting the class type of
an element within the heap to help with making MaxHeap more generic and the
official oracle documentation for arrays and creating new instances to help
with the generic functions fo the MaxHeap class rather than writing it
solely for the Task class.

https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html

https://www.tutorialspoint.com/java/lang/class_getcomponenttype.htm
