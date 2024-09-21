## Question 1 (Survey Representation)
1. Design a system which stores the structure of a form.
The structure is then can be read and be displayed.
The form is a survey, it contains:
   1. A **title** which is the name of the survey
   2. 1 or more questions, each question can be:
      * Open Question
      * Close Question - each closed question have a list of 1 
      or more options to choose from
2. Represent it using Objects/Classes in a programming language
of your choice.
3. Represent it using RDBMS tables

#### Representation in Code
```kotlin
data class Survey(val title: String, val questions: List<Question>)

sealed abstract class Question(protected val question: String)

data class ClosedQuestion(question: String,
                          val options: List<String>) : Question(question)

data class OpenQuestion(question: String,
                        placeHolder: String = "Enter your answer here")

```

Reasons for Choosing this Representation:
1. Currently there are only 2 types of questions, but in the future we might want to introduce new types of questions. Choosing a *sealed class hierarchy* will make sure that every *concrete* type of question is well defined an accountant for.
2. Since we don't know exactly how many question types we will have beforehand there is no point in defining a separate field for each type in the *Survey* class.
3. Since a *survey* can have any number of questions from any type in any order, keeping them in a single list is a close representation of how the data is displayed.

#### Representation in RDBMS Tables

1. A table for holding the survey itself
*surveys* Table - 
(id [pk varchar not null], title [varchar null] , description [text null])

Since we can *re-use* questions in multiple surveys, it is implied therefore that we have a here a many-to-many relation between questions and surveys.
2. A table for holding the question itself, since we don't know how many type of questions there can be we will represent all types of questions in a single table. We can use an embeded *JSON* to elaborate the specific type and configuration of the question. 
*questions* Table -
(id [pk varchar not null], type [varchar not null], configuration [JSON/Text not null])
3. For representing the *many-to-many* relation we will another table to link between a *question* and a *survey*
*survey_to_question_mapping* Table -
(id [pk varchar not null], survey_id [fk varchar not null], question_id [fk varchar not null])
unique index constraint on the *survey_id* and *question_id* pair
We can also optionally add another call called order to fix the order of questions but this is not required.

## Question 2 (URL Shortening Service)
Design a URL shortening system, like *Tiny URL* or *bitly*

Basic Use-cases
1. A user accessed the system with a URL *x*, the system creates a shortened URL *y* and gives it back to the user.
2. A user clicks on the shortened URL *y* and the system redirects the user to original URL *x*


#### Basic Implementation

* The first use case is a POST request which contains a simple JSON containing the original URL {"url": "www.original.url.com"}. The system will give back a shortenend version of the given URL.

* For the 2nd user case, every time a user clicks on a shortened URL the, the browser will access the services using a simple GET request and will receive back a *re-direct* response (status code 3xx) to the matching actual URL.

* Since we want to be able to represent lots of mappings and still keep the URL short we can decide that each shortened URL will be the characters [0-9][a-z][A-Z] which is english alphanumeric lower and upper case letters. Which gives us 62 possible values (10 + 26 + 26). We can decide we want each URL to be 5 characters long, with the representation this will give us 62^5 approx 900 million possible unique values, if we go up to 7 characters this will give us 62^7 which is approx more than 3 trillion possible unique values. So we will go with 7.

* We can generate a random value by basing it on a UUID and translating it to our representation of base62 7 characters with minimal collisions.

* The system will compose of a simple REST webservice and a backing RDBMS. The RDBMS will contain a single table, for now, which will contain the following: (id [pk varchar not null auto-generated], shortened_url [varchar(7) not null unique index], original_url (varchar not null unique index))

#### Scaling
* We can *scale-out* the system by first adding a load balancer (such as Nginx) and spinout multiple replicas of the webservice (Using K8s for instance). Since the webservice currently has no inner *state*, scaling it out is easy. *Routing policy* for the load balancer can be a simple *Round-Robin* or more complex load based load balancing (webservice with the least requests). 
* We also need to *scale-out* the database in order to enable multiple webservices to access it efficiently to retrive the data. Most DBs support out-of-the-box *partitioning* or *sharding*. *Sharding* allows for multiple db instances to handle different ranges of the incoming input by spreading the load using a *hash* function. This also allows for redundancy, when an instance fails the system will automatically re-route its values to the remaining instances, this is called *re-balancing*, *Kafka* is also a system that uses this and is not a db.
> Additional Talking Points - 
>*  Robustness can also be implemented by using *replicas* for each *shard*. Where there is the working *shard* called the *primary* and 1 or more *replicas* on stand-by.
>* Re-disributing the keys between instances is a heavy operation which includes lots of I/O. This can be reduced by using *Consistent Hashing*.

* Each webservice can be added either an in-memory cache that is part the webservice itself, for instance *Caffeine* cache for *JVM* based webservices or an in-memory application local to the webservice like *Redis* or *Memcached*. *Redis* also offers a cluster of cache where cached values can be shared accross all instances.

#### Advanced Use-Cases and Scenarios -
1. We should not allow shortening an already shortened URL. If we do we can cause a chain of redirections. For instance shortenend URL x maps to original URL url. Shortening x will gives us a new shortenend URL y. We can repeat the process how many times we want with each new shortenend URL. But say a user clicks on y, this will redirect him to x, but x will cause another redirection to url. This is for 2 levels but by alone it will double the amount of work the services are doing. So shortening a shortened URL, i.e a URL of which is already present as a key in the table will cause an error.
2. We can lower the latency of a request to generate a shortened URL by parallelizing the returning of the key and saving it to the DB. But if saving the key to the DB fails, this will mean that we gave the user a shortenend URL which doesn't lead anywhere making unusable. So we must make sure the insertion into the DB succeeded before returning the key.

##### New Scenarios/Requirements
* Say we want to starting charging money for this service. So *premium* users will get additional features for their shortenend URLs for instance, analytics and usage data about users clicking on their URLs. For instance where do most users come from, if they are using their phones for access or laptops, what was the most clicked on link during a span of 30 days.

**Implementation**
1. First we need to add a new table for premium users and connecting their id to the mapping. For this we will add a new column to the shortening table which will be a foreign key to the users table. This must be a 1 to many relation since a user can have multiple links but each link can belong to a single user only. The fk column can be null since, a shortening map can be created by both free and premium users.
2. Whenever someone clicks a shortenend link belonging the a user, which we can determine since we are fetching the mapping entry containing also the user id it belongs to, we will send this data to a new separate analytics service.
3. Since the analytics service does not require the same performance and availabilty as the shortening webservice we can send the data to it *asynchronously*. For that we can use any *Messaging* service we want, for instance *Kafka*, *RabbitMQ* and more.
4. Since analytics usually requires complicated aggregational queries we need to choose a database which supports these kinds of queries. Also, since the data we can take from each clicking user varies and doesn't necessarily has a defined schema, using an RDBMS might prove problematic. For that we can choose various NOSQL database, like timeseries DBs, such as *Influx* or even *Elasticsearch*.

## Question 3 (Aggregating Event Stream)
Design a system for aggregating events.
Each event contains a **timestamp** and 0 or more **labels**. Labels can be anything
it can be a geolocation, agent, device and much more. You want to aggregate the events
coming into the stream based on their timestamp but also using the labels for "grouping".
The amount of labels in each event can be very large and not known beforehand.

#### Implementation
Say we're getting all the events through a single **Kafka** topic. We can divide
the system into 2 parts. A first consumer which listens to events in the topic
and parses the incoming message (assuming each event is a JSON). For each parsed JSON
the consumer will extract the **set** of labels and timestamp, other data can be discarded
at this point. Since we are using the labels for grouping, their order is irrelevant.
So we can look at each of them as a subset without any internal ordering.
The consumer will create a new message containing the labels and timestamp. And then
use the set of labels to create a key, which will be used as the partitioning key
for the next Kafka topic.
This way we can make sure that events with them same permutations will be sent to
the same consumer.
Each downstream consumer can then work in batches and do optimizations and
insert it into Elasticsearch or any NoSQL database which has aggregation capabilities.

#### Implementing a Limiter
Say we decide to limit each client with the amount of possible labels we allow
him to use. Each client can have a different limit depending on how much he pays.

Implementing - 
The simplest solution would be to use a **Bloom Filter**. Bloom filters are non-deterministic
data structures. Meaning they can give an answer about whether a value is 
present or not within a probability. For instance if value *is not* present in
set, the Bloom filter can answer deterministically. If a value *is* present in
the set, it will answer within a probability (usually 95% - 98% rate of success).
The biggest advantage of a Bloom filter is that it's memory footprint is bounded
regardless of the size of the set it contains.
So we can just implement a Bloom filter in the first **gateway** consumer and
throwing out message for clients that passed their allowed labels.

## Question 4 - Word Count for a Collection of Files on S3
1. You have a directory containing numerous text files, each housing job postings from our extensive job corpus.
2. Calculate the overall frequency of each keyword across all job postings.
3. If the number of unique keywords is less than 1000, print the output; otherwise, store the results in a file.
4. Format the output with column1 representing the keyword and column2 indicating its frequency.
5. Ensure your program is designed to efficiently handle a substantial amount of data, such as a dataset containing 1 TB of job postings.

```scala
//we can implement the aggregation using Spark

val jobs = spark.read().textFile(input_path);

val aggRdd = jobs.flatMap(_.split(" ").toRdd) //splitting each sentence and flat
//mapping it for an RDD of all the words
        .map(word => (word, 1)) // each word is turned into a tuple of the word and 1
        .reduce(value1, value2 -> value1 + value2 ) //aggregating using the key as the word
        .persist(); //since we do more than 1 terminal operation (size and write)
        //we can persist data on the executers to save time on doing the operations
        //again.
        
        //reduce is a shuffle operation

if (aggRdd.size < 1000) {
        aggRdd.collect.forEach { (word, occ) -> //collect is safe here
        //since we only have 1000 items
        println("$word $occ")
} else {
    aggRdd.repartition(5).write(path) //using write we can make each
    //of the executers write it's own data after doing the shuffle of the reduce
    //we could've also transfer all the data back to the driver
    //but this couldv'e overwhelm it
}

```

