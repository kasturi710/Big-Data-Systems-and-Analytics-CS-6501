# HADOOP MAP-REDUCE
## Project Overview
This project aims to analyse the Hadoop map-reduce framework and explore following problems:
* Count word frequency for a given dataset
* Top K frequently occuring words in N files

I explore these problems for different datasets and different implementations of Map-Reduce program, and provide a comparitive analysis.
## Pre-requisites
* Ensure that Hadoop is installed. We use single-node setup for the execution of the above programs. More Details: [Single Node Setup](https://github.com/matiassingers/awesome-readme).

## Task 1: Word Count Program
I evaluate map-reduce word count program on three datasets:
* [Quora Insincere Questions Classification Dataset](https://drive.google.com/file/d/1fcip8PgsrX7m4AFgvUPLaac5pZ79mpwX/view):~100K records
* [IMDB Dataset of 50K Movie Reviews](https://www.kaggle.com/lakshmi25npathi/imdb-dataset-of-50k-movie-reviews):~50K records
* [SMS Spam Collection Dataset](https://www.kaggle.com/uciml/sms-spam-collection-dataset):~5k records

### Execution of Word Count map-reduce program for Quora Insincere Questions Classification Dataset:
* First create an input directory in HDFS
```
hadoop fs -mkdir /quora_dataset
hadoop fs -mkdir /quora_dataset/input
```
* Add file_quora_dataset.txt (containing 100K records) inside the input directory in HDFS.
````
hadoop fs -put './datasets/file_quora_dataset.txt' /quora_dataset/input
````
* Run .word_count/word_count_classes/word_count.jar and store the results in an output directory in HDFS.
````
hadoop jar ./word_count/word_count_classes/word_count.jar WordCount  /quora_dataset/input  /quora_dataset/output
````

### Execution of Word Count map-reduce program for IMDB Movie Reviews Dataset:
* First create an input directory in HDFS
```
hadoop fs -mkdir /imdb_dataset
hadoop fs -mkdir /imdb_dataset/input
```
* Installl the IMBD dataset from the (link)[https://drive.google.com/file/d/15Az12-R3TJIhYbs1oSXAo1ee4wWa0Q43/view?usp=sharing] and create a txt file (datasets/file_imdb_dataset.txt) of all the 100K reviews. Add file_imdb_dataset.txt (containing 100K records) inside the input directory in HDFS.
````
hadoop fs -put './datasets/file_imdb_dataset.txt' /imdb_dataset/input
````
* Run ./word_count/word_count_classes/word_count.jar and store the results in an output directory in HDFS.
````
hadoop jar ./word_count/word_count_classes/word_count.jar WordCount  /imdb_dataset/input  /imdb_dataset/output
````
### Execution of Word Count map-reduce program for SMS Spam Collection Dataset:
* First create an input directory in HDFS
```
hadoop fs -mkdir /spam_dataset
hadoop fs -mkdir /spam_dataset/input
```
* Add file_quora_dataset.txt (containing 5K records) inside the input directory in HDFS.
````
hadoop fs -put './datasets/file_spam_dataset.txt' /spam_dataset/input
````
* Run ./word_count/word_count_classes/word_count.jar and store the results in an output directory in HDFS.
````
hadoop jar ./word_count/word_count_classes/word_count.jar WordCount  /spam_dataset/input  /spam_dataset/output
````
## Task 2: Top K frequently occuring words in N files
We take 20 large text files given in the (link )::::::::: and add it to the folder 20_Files. Our aim is to calculate top 100 most frequently occuring words in these files. We have 3 implementations for this problem.
### Solution 1:
This is a basic solution. We simply implement the Work count program for our 20 files given above and find the top 100 most frequntly occuring words using the command below:
````
hadoop fs -mkdir /Top_100
hadoop fs -mkdir /Top_100/input
hadoop fds -put './20_Files' /Top_100/input
hadoop jar ./word_count/word_count_classes/word_count.jar WordCount /Top_100/input/20_Files  /Top_100/output
hadoop fs -cat  /top_100_words/output/* | sort -n -k2 -r | head -n100
````
### Solution 2:
We map all words in 20 files to <1,w1>, <1,w2>, <1,w3>, <1,w4>..... <1,wn>. Further, the reducer receives <1,\[w1,w2,w3....wn\]>.
We use a HashMap in the reducer to maintain the count of all the words in the form <w1,c1>,<w2,c2>,<w3,c3>...<wn,cn>. 
Finally, we add all the entries of the hasmap to a treemap in the form <c1,w1> <c2,w2> <c3,w3>...<cn,wn>, to sort the entries according to their count. Finally we write the top 100 values from the treemap.
````
hadoop fs -mkdir /Top_100_part_2
hadoop fs -mkdir /Top_100_part_2/input
hadoop fs -put './20_Files' /Top_100_part_2/input
hadoop jar ./top_100/top_100_classes/top_100_count.jar Top100WordCountDriver  /Top_100_part_2/input/20_Files  /Top_100_part_2/output

````
### Solution 3 (OPTIMAL):
We first map all the words as <w1,1>, <w2,1>,<w3,1> ...<wn,1>. At reducer, we receive <w1,\[c11,c12,c12\]>,<w2,\[c21,c22\]> ...<wn,\[cn1,cnn2..\]> where c11, c12, c13 belong to the counts of w1 and so on. Further, we mainitain a TreeMap of size 100 inside the reducer context. As we evealuate the word count at reducer <wk, Ck>, we add <Ck,wk> to the tree map. As soon as the size of TreeMap crosses 100, we pop the minimum element. This way we are left with top 100 elements in the TreeMap after proceesing. While context cleanup, we write all the elements from our treemap. 
````
hadoop fs -mkdir /Top_100_part_3
hadoop fs -mkdir /Top_100_part_3/input
hadoop fs -put './20_Files' /Top_100_part_3/input
hadoop jar ./top_100/top_100_classes_part_2/top_100_count.jar TopKWordCountDriver  /Top_100_part_3/input/20_Files  /Top_100_part_3/output
````

## Acknowledgments
Following link was helpul while completing the project (HADOOP OFFICIAL TUTORIAL)[https://hadoop.apache.org/docs/r1.2.1/mapred_tutorial.html]

