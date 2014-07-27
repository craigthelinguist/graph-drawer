
Graph Drawer
============

A visual aide for various graphing algorithms.

This program lets you draw a graph on the screen. You can select an algorithm, choose the inputs to the algorithm and then watch what it is doing iteration-by-iteration.

This program began as my personal homework-solver. I had two classes that touched on graphing algorithms and found it tedious having to work out by-hand what the solution to a particular instance of the problem might be, so wrote a few programs to do a couple of the common algorithms for me. In July 2014 I brought those programs together and started working on them to make a genuine learning aide.

Working On
==========
- Moving cut vertices/articulation points and A* algorithms to the refactored version.
- Moving weighted edges to the refactored version.

Things To Add
=============
- Annotations, so that each iteration will also have a text output describing what has happened (E.g.: we visited the highlighted node, but it has already been visited, so we do nothing).
- Moar algortihms.
- Directed edges.
- Checks that the inputs for each algorithm is valid (e.g.: no performing A* on a graph with negative edges).
- Checking for lack of solution in the example of algorithms where there may not be one (e.g.: Hamiltonian Cycle, Eulerian Path).
- Panning the screen! To allow for even bigger graphs.
- Saving and loading of graphs (particularly to give some useful pre-generated graphs, such as bipartite graphs, which are required for specific algorithms).
