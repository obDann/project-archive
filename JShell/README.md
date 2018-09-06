# JShell

A replication of a unix-style shell. This project is completed with collaborative effort from Sibo Dong, Victor Lee, and Shuqi (Alice) Yang

### Highlights
* Design of a large-scaled project; frequent meetings for design prior to implementation
* Agile methodology:
  * Product backlog: A composition of user stories given the client's initial and changed requirements
  * CRC Cards: Breaking down the requirements from the user
    * Generating class ideas
    * Enforcing the Single Responsibility Principle
    * Encourages easy-to-refactor design
  * Sprint Backlog: Delegation of tasks to team members
    * Each sprint lasts a week
  * "Daily" Scrum Meetings
    * Meetings were in-person
    * Each scrum meeting took place every three days
    * Each member answers the questions:
      * "What did I do last time?"
      * "What do I plan to do now?"
      * "Am I blocked on anything?"
  * Change of requirements from the client: see the [Assignment Handouts](./assignmentHandouts)
* Design Patterns used:
  * Command Design Pattern
  * Singleton Design Pattern 
  * Composite Design Pattern
* Unit testing
* Refactoring
* Basic understanding of static binding and dynamic binding

### Lessons learned after this project got evaluated
* Unit tests should not aggregate other classes as unit tests are supposed to test units in isolation
  * To combat this, dependency injection is the most suitable approach
* Always abide by verification; this project was initially evaluated with openJDK rather than Oracle's JDK
  * Oracle's JDK had the JavaFX library while openJDK did not
