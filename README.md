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

| Bit Length | Points                                    | Hash Value         | Hamming Distance between the Full Hash Values |
|------------|-------------------------------------------|--------------------|-----------------------------------------------|
| 1          | See [issue 13](https://github.com/filipvanlaenen/jcrk/issues/13) | |                                           |
| 2          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                           |
| 3          | `00` and `80`                             | `60`               | 120                                           |
| 4          | `80` and `E0`                             | `70`               | 130                                           |
| 5          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                           |
| 6          | `6C` and `D8`                             | `AC`               | 125                                           |
| 7          | See [issue 13](https://github.com/filipvanlaenen/jcrk/issues/13) | |                                           |
| 8          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                           |
| 9          | `6380` and `9680`                         | `E000`             | 119                                           |
| 10         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                           |
| 11         | `2EC0` and `B880`                         | `5AA0`             | 126                                           |
| 12         | `7A20` and `D3A0`                         | `F400`             | 129                                           |
| 13         | `4F10` and `5A18`                         | `8DD0`             | 132                                           |
| 14         | `7FE0` and `FE24`                         | `0898`             | 119                                           |
| 15         | `DC2A` and `E8BA`                         | `CA38`             | 130                                           |
| 16         | `2B1F` and `393F`                         | `44E0`             | 124                                           |
| 17         | `79DB80` and `C7C680`                     | `2B5680`           | 117                                           |
| 18         | `021C80` and `1F53C0`                     | `010BC0`           | 117                                           |
| 19         | `143B20` and `B0FBE0`                     | `645CC0`           | 123                                           |
| 20         | `2A0EB0` and `3A7660`                     | `207940`           | 113                                           |
| 21         | `0BF7C0` and `ABA978`                     | `667000`           | 117                                           |
| 22         | `ADAD58` and `BD0654`                     | `FFAC54`           | 117                                           |
| 23         | `3016B2` and `4F6748`                     | `5F0D6E`           | 129                                           |
| 24         | `4074C0` and `930708`                     | `74EF0B`           | 117                                           |
| 25         | `1FB81400` and `5C761400`                 | `EBE77B80`         | 104                                           |
| 26         | `CE0A4F80` and `DC5F3480`                 | `F33E3CC0`         | 117                                           |
| 27         | `DE6E2400` and `E051BB60`                 | `A466C300`         | 116                                           |
| 28         | `12627910` and `DFBDDA80`                 | `1F4A97B0`         | 112                                           |
| 29         | `08CF2D78` and `2D5D27C8`                 | `E62D58B8`         | 102                                           |
| 30         | `3D15EA14` and `FE345B3C`                 | `11BE4834`         | 118                                           |
| 31         | `20ABF672` and `B1CFE3C4`                 | `745971EC`         | 117                                           |
| 32         | `402AC96A` and `E638F884`                 | `5D10BB55`         | 111                                           |
| 33         | `1B81D9CD80` and `7221844380`             | `DCE801EC80`       | 112                                           |
| 34         | `628064F5C0` and `F1C29A8300`             | `8C072CEFC0`       | 112                                           |
| 35         | `0F29FA22A0` and `5DB1890E00`             | `611F48C520`       | 118                                           |
| 36         | `AD28DF3140` and `AD5A2EEA80`             | `CA2B61A070`       | 108                                           |
| 37         | `44A121C0C8` and `ABFB075798`             | `75852B3B60`       | 112                                           |
| 38         | `5E73C8E22C` and `E0AF51DBBC`             | `76ED034224`       | 109                                           |
| 39         | `D7FC192B54` and `F2E36F94C6`             | `178831F14E`       | 109                                           |
| 40         | `8D939755A3` and `CA8E37A8C0`             | `27F458F3A3`       | 106                                           |
| 41         | `9D250BBDF700` and `E61642EF0480`         | `1A16BCB12580`     | 105                                           |
| 42         | `63BF99AAE7C0` and `69E4DF067440`         | `C5A27DF4EA40`     | 103                                           |
| 43         | `05396756D4A0` and `6948A690EAE0`         | `EB2D015C5440`     | 114                                           |
| 44         | `8347F1AC3720` and `B140C3DAA040`         | `CB994F1318E0`     | 101                                           |
| 45         | `204A4F4AF348` and `32A4A16EB1B8`         | `BF989B575B60`     | 106                                           |
| 46         | `867CA345B740` and `E0B784E396F4`         | `95A8287062E4`     | 106                                           |
| 47         | `6E5FB5055A26` and `FEB700655E78`         | `4F48099C42A4`     | 114                                           |
| 48         | `19688697E11D` and `750293FFD1BF`         | `9378A7ECB4CB`     | 109                                           |
| 49         | `2313CEE54BCF00` and `2313CEE54BCF00`     | `461E0340C9EB80`   | 93                                            |
| 50         | `11D4689EC17880` and `5DE91D9EE9C500`     | `34A71A96A6E700`   | 111                                           |
| 51         | `A890B644132C40` and `ED4AE300CD50C0`     | `A0321FF7ABC5E0`   | 109                                           |
| 52         | `A0A709CC169890` and `DBC532147B8BE0`     | `FA3F31EC8C5210`   | 98                                            |
| 53         | `B93E359F648EF0` and `FD1410BC0E2F88`     | `12BBDD8DB2D4D8`   | 100                                           |
| 54         | `217BF1FCE536DC` and `654049D779E448`     | `602DC545FCC97C`   | 100                                           |
| 55         | `5F3997CCCDD798` and `9E426FEDA38FD2`     | `2AB75045064DCE`   | 104                                           |
| 56         | `194066CE0C376C` and `E9B8071FCA5C94`     | `3D955EC11B8379`   | 95                                            |
| 57         | `8B76D455EFD2C680` and `EBEB92B82917BB80` | `A7CC7EECFED69100` | 97                                            |
| 58         | `AE5E8359214AC200` and `B9BD37A32AC98900` | `26DB0BEDAF2B1680` | 99                                            |

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

| Bit Length | Points                                | Hash Value       | Hamming Distance between the Full Hash Values |
|------------|---------------------------------------|------------------|-----------------------------------------------|
| 1          | `00` and `80`                         | `80`             | 256                                           |
| 2          | See [issue 13](https://github.com/filipvanlaenen/jcrk/issues/13) | |                                     |
| 3          | `60` and `A0`                         | `60`             | 249                                           |
| 4          | `30` and `B0`                         | `30`             | 253                                           |
| 5          | See [issue 13](https://github.com/filipvanlaenen/jcrk/issues/13) | |                                     |
| 6          | `A0` and `DC`                         | `70`             | 264                                           |
| 7          | `72` and `E8`                         | `A8`             | 236                                           |
| 8          | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 9          | `0F80` and `6480`                     | `C100`           | 233                                           |
| 10         | `0000` and `2240`                     | `5E80`           | 266                                           |
| 11         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 12         | `9B00` and `AFF0`                     | `3B40`           | 246                                           |
| 13         | `0970` and `3DC0`                     | `2260`           | 263                                           |
| 14         | `5EA4` and `E824`                     | `A920`           | 237                                           |
| 15         | `D7B4` and `F704`                     | `1438`           | 236                                           |
| 16         | `64B0` and `8AB4`                     | `EE99`           | 256                                           |
| 17         | `5E1C80` and `CB1080`                 | `1DE200`         | 219                                           |
| 18         | `0E0600` and `152AC0`                 | `E2B380`         | 240                                           |
| 19         | `9FA560` and `F36E80`                 | `6459C0`         | 238                                           |
| 20         | `03DA60` and `1AFEF0`                 | `8EEC70`         | 246                                           |
| 21         | `B926A0` and `C8BB98`                 | `07B708`         | 235                                           |
| 22         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 23         | `086E3A` and `77C636`                 | `A06772`         | 255                                           |
| 24         | See [issue 12](https://github.com/filipvanlaenen/jcrk/issues/12) | |                                     |
| 25         | `539A9080` and `F2171900`             | `4A4DB200`       | 245                                           |
| 26         | `5139E9C0` and `D8641740`             | `F4D546C0`       | 240                                           |
| 27         | `52BC4FE0` and `84819D40`             | `84BDD0C0`       | 236                                           |
| 28         | `7B4FF550` and `CCC92A70`             | `C9102DC0`       | 235                                           |
| 29         | `34575220` and `847876A8`             | `C386E7A0`       | 249                                           |
| 30         | `26EFC840` and `33F59208`             | `E18617C8`       | 246                                           |
| 31         | `77B3BD5C` and `8401B934`             | `DDB599B2`       | 223                                           |
| 32         | `2FCD62A7` and `9D3FFD54`             | `F7C1A3B0`       | 250                                           |
