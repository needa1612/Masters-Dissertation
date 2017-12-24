# Masters-Dissertation
One of the classic algorithms in the field of matchings under preferences is the GaleShapley algorithm, which is used to obtain stable matchings between two sets of elements. 
The algorithm finds its applications in the assignment of junior doctors to hospitals and students to schools and colleges. 
When trying to understand the use of this algorithm to solve these problems, users look forward to understanding every step of the algorithm execution and how it results into stable matchings. 
Since algorithms run in the background and directly produce the result, it can be hard to understand for a user who tries to gain an insight of its working. 
An animated representation of the execution acts as a visual aid in learning the algorithm better and would prove to be beneficial even for a naïve user.  
The project describes the design and implementation of a Java application that animates an extension of the Gale-Shapley algorithm, allowing users to apprehend minute details of the algorithm execution like deletions, intermediate matchings, list of free proposers and current stage (line) of execution of the algorithm. 
Input is either provided through a file or generated randomly, the algorithm can then be either run automatically at a desired speed or in steps; to observe and understand each step of the execution and finally record the results for future references. 

Main logic:
The algorithm logic was implemented in JAVA using Swings for the user interface

User interface:
Swings - consisted of panels, labels, scroll panes, buttons, labels, text fields and sliders

Animation logic:
-	Stopping main algorithm thread – only forward movement, no backward. Further, algorithm execution is fast – pausing it or slowing down the thread is difficult.
-	Save algorithm states in an array of data structures(frames), execution then involves sequential display of these frames.
Timers – used to schedule the display of frames – advantage – like threads, they need not be interrupted/waken up after a specific time interval 
-	Highlighting – done using DefualtHighlighter class (addHighlight, removeHighlight, etc)

Sequentially or in steps
1.	Sequentially – slider(actionListener) used to control execution speed and button to pause/start.
2.	Stepwise -  buttons to move ahead and back one step at a time 

Use of MVC architecture:                                                 
Model: main algorithm logic
View: User interface
Controller: ActionListeners

Object Oriented programming:
Person – proposers and acceptors

Input – file input or generate randomly

Use of Swings for user interface:
-	Java Swing and Abstract Window Toolkit(AWT). Used Swings because all its components are written in Java, it is platform-independent whereas AWT is platform-specific. 
(Swing provides a native look and feel that emulates the look and feel of several platforms, and also supports a pluggable look and feel that allows applications to have a look and feel unrelated to the underlying platform)
-	In addition to familiar components such as buttons, check boxes and labels, Swing provides several advanced components such as tabbed panel, scroll panes, trees, tables, and lists.

Extended Gale-Shapley algorithm involves two sets of participants, which was either provided by the users or generated randomly. 
Each set would have a preference list according to which its allocations are done. 
The algorithm could either be run automatically at a specific speed or in steps and as the execution progressed, users could see the current participant, the current step of algorithm, matchings made, they could go back to a previous step, etc. 
Various functionalities in the project allowed users to keep track of each step of the algorithm execution which in turn helped in simplifying the understanding of its working.
