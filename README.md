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

| Bit Length | Points                                | Hash Value       | Hamming Distance between the Full Hash Values |
|------------|---------------------------------------|------------------|-----------------------------------------------|
| 3          | `80` and `A0`                         | `C0`             | 80                                            |
| 4          | `00` and `50`                         | `50`             | 81                                            |
| 5          | `58` and `E0`                         | `C0`             | 75                                            |
| 6          | `58` and `E0`                         | `C0`             | 75                                            |
| 7          | `3C` and `90`                         | `C4`             | 82                                            |
| 8          | `02` and `3C`                         | `C4`             | 83                                            |
| 9          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 10         | `B9C0` and `E500`                     | `4E40`           | 65                                            |
| 11         | `0000` and `7040`                     | `1480`           | 71                                            |
| 12         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 13         | `8FD8` and `F790`                     | `2338`           | 73                                            |
| 14         | `4DC4` and `C0DC`                     | `79F8`           | 74                                            |
| 15         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 16         | `0DF7` and `298F`                     | `484D`           | 75                                            |
| 17         |                                       |                  |                                               |
| 18         | `A48BC0` and `E21940`                 | `4FA900`         | 70                                            |
| 19         | `0510A0` and `A58460`                 | `2FC8A0`         | 62                                            |
| 20         |                                       |                  |                                               |
| 21         | `44CE30` and `BBFB70`                 | `FD6750`         | 73                                            |
| 22         | `35507C` and `E08504`                 | `3D0514`         | 68                                            |
| 23         | `BC1BF0` and `DBF6B0`                 | `8FE7CE`         | 73                                            |
| 24         | `9B8FD1` and `FDA184`                 | `D760BE`         | 74                                            |
| 25         | `4A0CD180` and `8553E680`             | `119BCF00`       | 65                                            |
| 26         | `A035A140` and `AC30F200`             | `DEA50900`       | 73                                            |
| 27         | `6182AE40` and `C1D96340`             | `43BC9CA0`       | 65                                            |
| 28         | `08A2F0C0` and `4FA0E450`             | `992B8720`       | 72                                            |
| 29         | `5488F588` and `FA2D1CB8`             | `D55352B8`       | 66                                            |
| 30         | `9D4D5B10` and `9F95172C`             | `93F85F54`       | 70                                            |
| 31         | `ACB23AEA` and `C7E8735C`             | `8F603976`       | 51                                            |
| 32         | `21E72897` and `28670599`             | `D8C2E59B`       | 64                                            |
| 33         | `05B4E6B680` and `C01BB31600`         | `207C0B8D00`     | 63                                            |
| 34         | `2981ED9A40` and `E693204780`         | `F5F08989C0`     | 61                                            |
| 35         | `47334C4080` and `E8B39B1BC0`         | `2BEFC6AEE0`     | 63                                            |
| 36         | `333BCBDC00` and `E81CE75AA0`         | `2A7ADCF5E0`     | 63                                            |
| 37         | `80B94985F8` and `DEB3D00688`         | `A2D429C9C8`     | 53                                            |
| 38         | `CF528F70F0` and `E1B78EC250`         | `1D84F5E36C`     | 57                                            |
| 39         | `432C523A9C` and `65A2D6A508`         | `638856A48A`     | 62                                            |
| 40         | `3399BD40DC` and `A7F7B62182`         | `4FFDC01CB7`     | 61                                            |
| 41         | `A44815DC6280` and `BB809E886400`     | `C5C45F01F300`   | 59                                            |
| 42         | `365369F50E80` and `41D83DFF1740`     | `C48B1A1075C0`   | 60                                            |
| 43         | `512B5B3652C0` and `70785ED19D40`     | `F77C498F7180`   | 47                                            |
| 44         | `68E4A2DA8FD0` and `D5850D48F390`     | `FBBA72E3B310`   | 59                                            |
| 45         | `03DA927D0EF0` and `5E3D28A5CED0`     | `4EAE7B87DA60`   | 44                                            |
| 46         | `19DC8EB77CAC` and `EA8FDEA0CDBC`     | `EBE7781A0E38`   | 61                                            |
| 47         | `9D962B47E0FA` and `F8B57FB5C90E`     | `49F9475CA84E`   | 57                                            |
| 48         | `41A6BA9A6C45` and `4B95CCEF416B`     | `DC3CD50087EF`   | 66                                            |
| 49         |                                       |                  |                                               |
| 50         |                                       |                  |                                               |
| 51         |                                       |                  |                                               |
| 52         |                                       |                  |                                               |
| 53         |                                       |                  |                                               |
| 54         |                                       |                  |                                               |
| 55         |                                       |                  |                                               |
| 56         | `0A21F652E2A8F6` and `5CDEA5DF5B8C99` | `345D67FC70AE2A` |                                               |

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

| Bit Length | Points                                | Hash Value       | Hamming Distance between the Full Hash Values |
|------------|---------------------------------------|------------------|-----------------------------------------------|
| 1          | `00` and `80`                         | `80`             | 190                                           |
| 2          | `00` and `80`                         | `80`             | 190                                           |
| 3          | `00` and `60`                         | `A0`             | 192                                           |
| 4          | See [issue 13](https://github.com/filipvanlaenen/jcrk/issues/13) | |                                     |
| 5          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 6          | `1C` and `5C`                         | `C8`             | 195                                           |
| 7          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 8          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 9          | `0400` and `1280`                     | `A880`           | 190                                           |
| 10         | `3D80` and `7680`                     | `6E80`           | 191                                           |
| 11         | `8C80` and `EBA0`                     | `2140`           | 181                                           |
| 12         | `68B0` and `C580`                     | `1A60`           | 188                                           |
| 13         | `C098` and `C4F0`                     | `D700`           | 181                                           |
| 14         | `21EC` and `7820`                     | `C7BC`           | 179                                           |
| 15         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 16         | `A352` and `A876`                     | `8607`           | 198                                           |
| 17         | `1D9280` and `E77B80`                 | `ED2D80`         | 186                                           |
| 18         | `845C80` and `E239C0`                 | `F4D800`         | 170                                           |
| 19         | `13AEC0` and `3C1A60`                 | `7159E0`         | 194                                           |
| 20         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 21         | `575148` and `6B5C18`                 | `D18E78`         | 181                                           |
| 22         | `07D394` and `8E7D7C`                 | `DBF490`         | 173                                           |
| 23         | `D43A22` and `DF81F0`                 | `A6038C`         | 185                                           |
| 24         | `763F9E` and `DA2ABE`                 | `D493BE`         | 199                                           |
| 25         | `02609280` and `7403FD00`             | `7977DE00`       | 177                                           |
| 26         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 27         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 28         | `11868EE0` and `9F14AE70`             | `CBE9BA20`       | 177                                           |
| 29         | `12411C68` and `95CF1778`             | `09436C00`       | 154                                           |
| 30         | `1A871A38` and `26ED083C`             | `0ED962B4`       | 171                                           |
| 31         | `C29A1EE4` and `DB364166`             | `C45BAFE0`       | 177                                           |
| 32         | `B2E89556` and `CE9C28C1`             | `77EFF04A`       | 173                                           |
| 33         | `EEA55CDF80` and `F333C07480`         | `3504172580`     | 175                                           |
| 34         | `00692D74C0` and `C642B36AC0`         | `1C359B8A40`     | 177                                           |
| 35         | `32D27C53A0` and `CC9A1621A0`         | `1B8E6D9600`     | 186                                           |
| 36         | `BAE53B69D0` and `C9C9E5CB00`         | `6518814D40`     | 175                                           |
| 37         | `0131C7F0D0` and `91A14512B0`         | `C9A900B890`     | 174                                           |
| 38         | `451C2195F8` and `53A570372C`         | `25B2E98EC0`     | 165                                           |
| 39         | `69BAB2F3AE` and `A3C4EEEA00`         | `6E7BBB22D2`     | 163                                           |
| 40         | `CC7B481271` and `F6D2CF640C`         | `BDF8CA50B1`     | 161                                           |
| 41         | `A426C941AA80` and `DAF95F3AD880`     | `8C1295427200`   | 173                                           |
| 42         | `A74A82DDCE40` and `EA273A84C200`     | `346D2BE12880`   | 163                                           |
| 43         | `4BD5D1951200` and `686D61AD9100`     | `92D323AE39A0`   | 160                                           |
| 44         | `40122E9FD6E0` and `C4C620D4FC70`     | `F452B2340FC0`   | 174                                           |
| 45         | `952EB7DD6150` and `EC2B196B5A08`     | `993879023068`   | 182                                           |
| 46         | `0F4A383A54F4` and `E5DB5291E43C`     | `D708B5F98974`   | 174                                           |
| 47         | `23AFD2C49554` and `93B24DA2EF98`     | `F2D6F326BBCA`   | 182                                           |
| 48         | `025BA0250892` and `46BE11BF4CA2`     | `7A39A0A5C4F9`   | 162                                           |
| 49         | `4CA693F0210780` and `764D1D0255CB00` | `2F779ABD6B9900` | 166                                           |
| 50         | `122BBE2AD42840` and `BE98601F8F8FC0` | `9D9ABBAD441CC0` | 170                                           |
| 51         | `09339662817140` and `240EF4D7527360` | `6A9F057EADDAA0` | 158                                           |
| 52         | `951DDDEDAD78B0` and `E109500EFD6300` | `9D05360304C610` | 158                                           |
| 53         | `70649AD8C683E0` and `C2813A1FC1CD70` | `2FF804F4911010` | 171                                           |
| 54         | `642CBEA6310260` and `A3426997143CD0` | `9391906B5C8E44` | 154                                           |
| 55         | `1733D209B6F0DA` and `D66FD950BDE27C` | `2888A74143C4D4` | 161                                           |
| 56         | `8819474D63E685` and `FA09E326DBA0A2` | `3041d7827d5161` | 178                                           |

### SHA-512

| Bit Length | Points                                | Hash Value       |
|------------|---------------------------------------|------------------|
| 3          | `60` and `A0`                         | `60`             |
