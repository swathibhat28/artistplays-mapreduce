# artistplays-mapreduce

## Exercise:
Create a list of Artist/Band and number of plays on each day.

### MapReduce:

#### Pre-processing

##### Extra Punctuation
Replace everything from the Artist/Band name that is not a word character - [A-Z][a-z][0-9]

##### Stop words
For this type of analyses, we want to focus on words that carry meaning: names, nouns, and verbs. Words like the, of, and occur more than any other words in English.

Use a list of stop words to filter out those most-frequent tokens. Anything on the list is removed from the stream of input Artist/Band names before further processing.The list is currently maintained in the code but can be read as a cache file from the command line.

##### Time conversion
The input records have time in Unix timestamp. Convert to YYYY-MM-DD format

#### Mapper Phase
In the Mapper phase, Mapper takes records one by one:
1. Normalize Artist/Band name 
2. Convert output date to YYYY-MM-DD format
3. Mapper output - Key: Artist Tuple(artist name,date), Value: number of plays per artist 

#### Reducer Phase
In the Reducer phase:
1. For each Artist/Band tuple (Artist name,output date(YYYY-MM-DD)) sum up the count of number of plays. 
2. Using the tuple of  Artist/Band, output date(YYYY-MM-DD) as key ensures that all Artist/Band for a day goes to the same reducer.
3. Reducer output - Key: Text(Artist name, Date), Value: Number of plays per artist per day

#### Instructions

git clone git@github.com:swathibhat28/artistplays-mapreduce.git

Import as Eclipse Java Project

Go to Project Properties window and in "Java Build Path" section, click on "Add External Jars"

In the JAR Selection dialog, select the following jars from the extracted Hadoop tar.gz file.

1. share/hadoop/common/hadoop-common-*.jar
2. share/hadoop/mapreduce/hadoop-mapreduce-client-core-*.jar
3. share/hadoop/mapreduce/hadoop-mapreduce-client-jobclient-*.jar

Additional jars required are:

1. org-apache-commons-logging.jar
2. google-collections-0.9.jar
3. org.apache.common.collections.jar
4. commons-cli-2.0.jar
5. com.google.guava_1.6.0 2.jar

#### Execution

hadoop jar ArtistPlaysMapreduce.jar \<input\> \<output\> -artistNames \<artistNamesFile\>

All the fields above are required.

#### Assumptions
1. Assuming 1 reducer which is the default. This can be configured when required as an optional parameter. Suggested number is 1 reducer for 1K records
2. Stop words can be dropped - using a stop word list to filter out irrelevant words from artist names
3. Normalization applied only through A-Za-z0-9. 
4. Looking for exact matches with Artist names not performing any confidence matches with this version
