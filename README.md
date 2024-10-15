# jCRK

- [Cracking cryptographic hash functions in Java](#cracking-cryptographic-hash-functions-in-java)
- [Getting Started](#getting-started)
- [Results](#results)
  - [SHA-1](#sha-1)
  - [SHA-224](#sha-224)
  - [SHA-256](#sha-256)
  - [SHA-384](#sha-384)
  - [SHA-512](#sha-512)

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

| Bit Length | Points                                | Hash Value       |
|------------|---------------------------------------|------------------|
| 3          | `80` and `A0`                         | `C0`             |
| 4          | `00` and `50`                         | `50`             |
| 5          | `58` and `E0`                         | `C0`             |
| 6          | `58` and `E0`                         | `C0`             |
| 7          | `3C` and `90`                         | `C4`             |
| 8          | `02` and `3C`                         | `C4`             |
| 9          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |
| 10         | `B9C0` and `E500`                     | `4E40`           |
| 11         | `0000` and `7040`                     | `1480`           |
| 12         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |
| 13         | `8FD8` and `F790`                     | `2338`           |
| 14         | `4DC4` and `C0DC`                     | `79F8`           |
| 15         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |
| 16         | `0DF7` and `298F`                     | `484D`           |
| 17         |                                       |                  |
| 18         | `A48BC0` and `E21940`                 | `4FA900`         |
| 19         | `0510A0` and `A58460`                 | `2FC8A0`         |
| 20         |                                       |                  |
| 21         | `44CE30` and `BBFB70`                 | `FD6750`         |
| 22         | `35507C` and `E08504`                 | `3D0514`         |
| 23         | `BC1BF0` and `DBF6B0`                 | `8FE7CE`         |
| 24         | `9B8FD1` and `FDA184`                 | `D760BE`         |
| 25         | `4A0CD180` and `8553E680`             | `119BCF00`       |
| 26         | `A035A140` and `AC30F200`             | `DEA50900`       |
| 27         | `6182AE40` and `C1D96340`             | `43BC9CA0`       |
| 28         | `08A2F0C0` and `4FA0E450`             | `992B8720`       |
| 29         | `5488F588` and `FA2D1CB8`             | `D55352B8`       |
| 30         | `9D4D5B10` and `9F95172C`             | `93F85F54`       |
| 31         | `ACB23AEA` and `C7E8735C`             | `8F603976`       |
| 32         | `21E72897` and `28670599`             | `D8C2E59B`       |
| 33         |                                       |                  |
| 34         |                                       |                  |
| 35         |                                       |                  |
| 36         |                                       |                  |
| 37         |                                       |                  |
| 38         |                                       |                  |
| 39         |                                       |                  |
| 40         | `3399BD40DC` and `A7F7B62182`         | `4FFDC01CB7`     |
| 41         |                                       |                  |
| 42         |                                       |                  |
| 43         |                                       |                  |
| 44         |                                       |                  |
| 45         |                                       |                  |
| 46         |                                       |                  |
| 47         |                                       |                  |
| 48         | `41A6BA9A6C45` and `4B95CCEF416B`     | `DC3CD50087EF`   |
| 49         |                                       |                  |
| 50         |                                       |                  |
| 51         |                                       |                  |
| 52         |                                       |                  |
| 53         |                                       |                  |
| 54         |                                       |                  |
| 55         |                                       |                  |
| 56         | `0A21F652E2A8F6` and `5CDEA5DF5B8C99` | `345D67FC70AE2A` |

### SHA-224

| Bit Length | Points                                | Hash Value       |
|------------|---------------------------------------|------------------|
| 3          | See [issue 13](https://github.com/filipvanlaenen/jcrk/issues/13) | |
| 4          | See [issue 13](https://github.com/filipvanlaenen/jcrk/issues/13) | |
| 5          | `A0` and `C8`                         | `38`             |
| 6          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |
| 7          | See [issue 13](https://github.com/filipvanlaenen/jcrk/issues/13) | |
| 8          | `38` and `FD`                         | `52`             |

### SHA-256

| Bit Length | Points                                | Hash Value       |
|------------|---------------------------------------|------------------|
| 3          | `00` and `80`                         | `60`             |
| 4          | `80` and `E0`                         | `70`             |
| 5          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |
| 6          | `6C` and `D8`                         | `AC`             |
| 7          | See [issue 13](https://github.com/filipvanlaenen/jcrk/issues/13) | |
| 8          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |

### SHA-384

| Bit Length | Points                                | Hash Value       |
|------------|---------------------------------------|------------------|
| 3          | `00` and `60`                         | `A0`             |

### SHA-512

| Bit Length | Points                                | Hash Value       |
|------------|---------------------------------------|------------------|
| 3          | `60` and `A0`                         | `60`             |
