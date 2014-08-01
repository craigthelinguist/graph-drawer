
Graph Drawer
============

A visual aide for various graphing algorithms.

This program lets you draw a graph on the screen. You can select an algorithm, choose the inputs to the algorithm and then watch what it is doing iteration-by-iteration.

The program is roughly structured in the model-view-controller pattern. The model is captured by the algorithms and graph package. The view is captured by the various classes in the gui, which pass all events and interactions to the UI class, which is the controller.

You may freely copy, edit, and distribute this program per the GPL 3.0 licence: http://www.gnu.org/copyleft/gpl.html

Working On
==========
- Moving cut vertices/articulation points to the refactored version.
- Clean up UI class.

Things To Add
=============
- Annotations, so that each iteration will also have a text output describing what has happened (E.g.: we visited the highlighted node, but it has already been visited, so we do nothing).
- Allow for 'auto-pilot' mode for algorithms such as A*. Auto-pilot would the algorithm to iterate and display to the screen at selected time intervals, so you can
see A* running by itself, slowed down, without having to click 'next' all the time.
- Moar algortihms.
- Directed edges.
- Checks that the inputs for each algorithm is valid (e.g.: no performing A* on a graph with negative edges).
- Checking for lack of solution in the example of algorithms where there may not be one (e.g.: Hamiltonian Cycle, Eulerian Path).
- Panning the screen! To allow for even bigger graphs.
- Saving and loading of graphs (particularly to give some useful pre-generated graphs, such as bipartite graphs, which are required for specific algorithms).
