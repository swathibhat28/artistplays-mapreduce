# artistplays-mapreduce

## Exercise:
Create a list of Artist/Band and number of plays on each day.

### MapReduce:

#### Pre-processing

##### Extra Punctuation
Replace everything from the Artist/Band name that is not a word character - [A-Z][a-z][0-9]

##### Stop words
For this type of analyses, we want to focus on words that carry meaning: names, nouns, and verbs. Words like the, of, and occur more than any other words in English.

Use a list of stop words to filter out those most-frequent tokens. Anything on the list is removed from the stream of input Artist/Band names before any further processing is done.

##### Time conversion
The input records have time in Unix timestamp. Convert to YYYY-MM-DD format

#### Mapper Phase
In the Mapper phase, Mapper takes records one by one and performs the pre-processing. For each record outputs Artist/Band, output date(YYYY-MM-DD) tuple as key and number of plays as value

#### Reducer Phase
In the Reducer phase, for Artist/Band, output date(YYYY-MM-DD) tuple sum up the count of number of plays. Using the tuple of  Artist/Band, output date(YYYY-MM-DD) as key ensures that all Artist/Band for a day goes to the same reducer.
