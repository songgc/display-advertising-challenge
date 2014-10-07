Display Advertising Challenge
=============================

Description
-----------
This is the code was written for the [Kaggle Criteo Competition of CTR prediction](https://www.kaggle.com/c/criteo-display-ad-challenge). 

Since the data are highly sparse, the basic methodology is to use logistic regression with appropriate quadratic/polynomial feature generation and regularization to make sophisticated and over-fitting-tractable models. [Vowpal Wabbit](https://github.com/JohnLangford/vowpal_wabbit) is the major machine learning software used for this project. Since the data size is challenging in terms of my personal workstation (a single quad-core CPU), the techniques of feature selection and model training are selected based on the trade off between performance and CPU/RAM resource limit.

Dependencies and requirements
-----------------------------
Please note that the code was written for my personal learning and practice in new features of Java 8 and Python 3.4 in Ubuntu 14.04. The code cannot be run in early versions of these two languages or other OSs. Compatibility is not considered here.

* Java 8
* Python 3.4
* Maven 3
* Redis 2.8
* Pandas 0.14
* Vowpal Wabbit 7.7
* Java-based open source projects: (Maven will install them automatically)
  - guava 17.0
  - jedis 2.5.1
  - commons-lang3 3.3.2
 

How to run
----------
- Copy train and test data file (train.csv, test.csv) to data folder
- Compile the Java code by
```
$ cd display-ad-java
$ mvn package # or mvn install
```
- Make sure a redis instance running at localhost:6379
- Set the path of binary vw (VW_BIN) in run.sh, such as 
```
export VW_BIN=/path/to/vw/binary
```
- Finally, 
```
$ cd work
$ ../run.sh
```


