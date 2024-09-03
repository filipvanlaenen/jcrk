# jCRK

- [Cracking cryptographic hash functions in Java](#cracking-cryptographic-hash-functions-in-java)
- [Getting Started](#getting-started)
- [Results](#results)

## Cracking cryptographic hash functions in Java

This project's goal is to provide a Java implementation of the software needed to find a collision in cryptographic hash
functions. It should be extensible to any cryptographic hash function, provide the administration software to administer
a concrete cracking project, and provide the building blocks for and implementations of a variaty of segment producers.
In addition, there should also be a number of reporters to track the progress of a concrete cracking project.

Note that for the segment producers, Java may not be the best programming language choice because of performance. On the
other hand, any implementation is better than no implementation.

The project is inspired by the [MD5CRK](https://en.wikipedia.org/wiki/MD5CRK) project, and uses Pollard's rho collision
search to find a collision.

## Getting Started

First of all, you need to obtain a copy of the source code and compile it into an executable. Run the following commands
to do this:

```
git clone git@github.com:filipvanlaenen/jcrk.git
cd jcrk
mvn clean compile assembly:single
```

If everything works well, you'll find a JAR file in the `target` directory with all dependencies included. Let's test it
out, using no input parameters:

```
java -jar jcrk-1.0-jar-with-dependencies.jar
```

This should produce a short report displaying how to use the program.

## Results

### SHA-1

| Bit Length | Points            | Hash Value |
|------------|-------------------|------------|
| 3          | `80` and `A0`     | `C0`       |
| 4          | `00` and `50`     | `50`       |
| 5          | `58` and `E0`     | `C0`       |
| 6          | `58` and `E0`     | `C0`       |
| 7          | `3C` and `90`     | `C4`       |
| 8          | `02` and `3C`     | `C4`       |
| 9          |                   |            |
| 10         | `B9C0` and `E500` | `4E40`     |
| 11         | `0000` and `7040` | `1480`     |
