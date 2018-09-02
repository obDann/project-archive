# Matrix Design

Designing linear algebra matrices with set client requirements or set implementation requirements

### Highlights from part 1
* Initial exposure to user stories
* Design through UML diagrams
* Extensive documentation for interfaces
* Larger-scaled unit tests

### Highlights from part 2
* Change of requirements from the client (from part 1)
* Initial exposure to representation invariants
* Linked list implementation where each node contains two pointers (down and right)
* Introductory ideas to "efficiency" (i.e. there is a default value in the matrix)


### Lessons learned after this project got evaluated
* Matrix multiplication could have been done more efficiently
  * With this current implementation, the product of two matrices results in a matrix that consists of all nodes, but no default value
  * An idea to counteract this could have been to search through the resulting matrix and finding the most frequent value, assigning the most frequent value as the default value