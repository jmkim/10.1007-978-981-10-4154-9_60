# ICISA 2017

## About our paper

### Title
Improving the B+-Tree Construction for Transaction Log Data in Bank System Using Hadoop

### Author
- [Cong Viet-Ngu Huynh]( mailto:hcvngu@pukyong.ac.kr )
- [Jongmin Kim]( mailto:jmkim@pukyong.ac.kr )
- [Jun-Ho Huh]( mailto:72networks@cup.ac.kr )

### Abstract
In Socialist Republic of Vietnam, applying the Big data to process any kind of data is still a challenge, especially in the banking sector. Until now, there is only one bank applied Big data to develop a data warehouse system has focused, consistent, can provide invaluable support to executives make immediate decisions, as well as planning long-term strategies, however, it still not able to solve any specific problem. Nowadays, from the fact large amounts of traditional data are still increasing significantly, if B-tree is considered as the standard data structure that manage and organize this kind of data, B+-tree is the most well-known variation of B-tree that is very suitable for applying bulk loading technique in case of data is available. However, it usually takes a lot of time to construct a B+-tree for a huge volume of data. In this paper, we propose a parallel B+-Tree construction scheme based on a Hadoop framework for Transaction log data. The proposed scheme divides the data into partitions, builds local B+-trees in parallel, and merges them to construct a B+-tree that covers the whole data set. While generating the partitions, it considers the data distribution so that each partitions have nearly equal amounts of data. Therefore the proposed scheme gives an efficient index structure while reducing the construction time. 

### DOI
10.1007/978-981-10-4154-9_60

### Links
- [SpringerLink]( https://link.springer.com/chapter/10.1007/978-981-10-4154-9_60 )


## About this code

### Summary
This code only contains B+ tree construction prototype using [MapDB]( http://www.mapdb.org ), and sample transaction data creation.


### Dependencies/Libraries
- [eclipse-collections-7.1.0]( https://mvnrepository.com/artifact/org.eclipse.collections/eclipse-collections/7.1.0 )
- [eclipse-collections-api-7.1.0]( https://mvnrepository.com/artifact/org.eclipse.collections/eclipse-collections-api/7.1.0 )
- [elsa-3.0.0-M6]( https://mvnrepository.com/artifact/org.mapdb/elsa/3.0.0-M6 )
- [guava-21.0]( https://mvnrepository.com/artifact/com.google.guava/guava/21.0 )
- [lz4-1.3.0]( https://mvnrepository.com/artifact/net.jpountz.lz4/lz4/1.3.0 )
- [mapdb-3.0.3]( https://mvnrepository.com/artifact/org.mapdb/mapdb/3.0.3 )

