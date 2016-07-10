# jCRK
## Cracking cryptographic hash functions in Java

This project's goal is to provide a Java implementation of the software needed
to find a collision in cryptographic hash functions. It should be extensible
to any cryptographic hash function, provide the administration software to
administer a concrete cracking project, and provide the building blocks for and
implementations of a variaty of segment producers. In addition, there should
also be a number of reporters to track the progress of a concrete cracking
project.

Note that for the segment producers, Java may not be the best programming
language choice because of performance. On the other hand, any implementation is
better than no implementation.

The project is inspired by the
[MD5CRK](https://en.wikipedia.org/wiki/MD5CRK) project, and uses Pollard's rho
collision search to find a collision.

## License

jCRK – Cracking cryptographic hash functions implemented in Java.

Copyright © 2016 Filip van Laenen <f.a.vanlaenen@ieee.org>

This file is part of jCRK.

jCRK is free software: you can redistribute it and/or modify it under the terms
of the GNU General Public License as published by the Free Software Foundation,
either version 3 of the License, or (at your option) any later version.
 
jCRK is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE. See the GNU General Public License for more details.
 
You can find a copy of the GNU General Public License in /doc/gpl.txt
