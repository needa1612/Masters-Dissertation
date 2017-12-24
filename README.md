# Masters-Dissertation
One of the classic algorithms in the field of matchings under preferences is the GaleShapley algorithm, which is used to obtain stable matchings between two sets of elements. 
The algorithm finds its applications in the assignment of junior doctors to hospitals and students to schools and colleges. 
When trying to understand the use of this algorithm to solve these problems, users look forward to understanding every step of the algorithm execution and how it results into stable matchings. 
Since algorithms run in the background and directly produce the result, it can be hard to understand for a user who tries to gain an insight of its working. 
An animated representation of the execution acts as a visual aid in learning the algorithm better and would prove to be beneficial even for a naïve user.  

The project describes the design and implementation of a Java application that animates an extension of the Gale-Shapley algorithm, allowing users to apprehend minute details of the algorithm execution like deletions, intermediate matchings, list of free proposers and current stage (line) of execution of the algorithm. 
Input is either provided through a file or generated randomly, the algorithm can then be either run automatically at a desired speed or in steps; to observe and understand each step of the execution and finally record the results for future references. 

Extended Gale-Shapley algorithm involves two sets of participants, which was either provided by the users or generated randomly. 
Each set would have a preference list according to which its allocations are done. 
The algorithm could either be run automatically at a specific speed or in steps and as the execution progressed, users could see the current participant, the current step of algorithm, matchings made, they could go back to a previous step, etc. 
Various functionalities in the project allowed users to keep track of each step of the algorithm execution which in turn helped in simplifying the understanding of its working.

Main logic:
The algorithm logic is implemented in JAVA using Swings for the user interface

User interface:
Swings - consists of panels, labels, scroll panes, buttons, labels, text fields and sliders

Animation logic:
- An algorithm can be animated in two ways : 
1. Stopping main algorithm thread (this will allow the algorithm to move only in the forward direction, not backward. Further, algorithm execution is fast – pausing it or slowing down the thread is difficult).
2. Save algorithm states in an array of data structures(frames) (execution then involves sequential display of these frames).
Timers can used to schedule the display of frames. The advantage here is, unlike threads, they need not be interrupted/waken up after a specific time interval.
The second approach was follwed because of its advantages.

-	Highlighting – done using DefualtHighlighter class (addHighlight, removeHighlight, etc)

- The algorithm can be run either sequentially or in steps: 
1.	Sequentially – slider(actionListener) used to control execution speed and button to pause/start.
2.	Stepwise -  buttons to move ahead and back one step at a time .

Use of MVC architecture:                                                 
Model: main algorithm logic (GSAlgo.java).
View: User interface (GUI.java).
Controller: ActionListeners (actionPerformed() in GUI.java).

Object Oriented programming:
Inheritance implemented with person.java as base class and acceptor.java and proposer.java as derived classes.

Input – taken from file or generated randomly.

Why use Swings for user interface?
-	Because all its components are written in Java, it is platform-independent. 
(Swing provides a native look and feel that emulates the look and feel of several platforms, and also supports a pluggable look and feel that allows applications to have a look and feel unrelated to the underlying platform)
-	In addition to familiar components such as buttons, check boxes and labels, Swing provides several advanced components such as tabbed panel, scroll panes, trees, tables, and lists.


