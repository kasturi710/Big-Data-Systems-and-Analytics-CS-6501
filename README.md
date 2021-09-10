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

### Execution of Word Count map-reduce program for 'Quora Insincere Questions Classification Dataset':
* First create an input directory in HDFS
```
hadoop fs -mkdir /quora_dataset
hadoop fs -mkdir /quora_dataset/input
```
* Add file__quora_dataset.txt (containing 100K records) inside the input directory in HDFS.
````
hadoop fs -put './datasets/file__quora_dataset.txt' /quora_dataset/input
````
* Run .word_count/word_count_classes/word_count.jar and store the results in an output directory in HDFS.
````
hadoop jar .word_count/word_count_classes/word_count.jar WordCount  /quora_dataset/input  /quora_dataset/output
````

## Help



## Authors



## Acknowledgments
