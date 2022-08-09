# Publisher/subscriber Pattern
Publisher/subscriber pattern is a software design technique realized in many software development
products including Apache Kafka, Rabbit MQ, etc. Please read more about this pattern at
https://en.wikipedia.org/wiki/Publish%E2%80%93subscribe_pattern
Pub/sub systems can be used in various scenarios involving IoT, system monitoring, DB back-up and
replication, log management. Please read more at
https://www.bmc.com/blogs/pub-sub-publish-subscribe/
In this project, you will develop a distributed system that imitates pub/sub based on the
requirements listed below.
* There are 3 types of agents: publisher, broker and subscriber. Your solution will support
multiple publishers and subscribers and a single, centralized broker. Each entity will be a
process of its own.
* Assume that all publishers and subscribers know the IP address and port number of the
broker. Use TCP-sockets for communication. You may separate the traffic of publishers
and subscribers (onto the broker) into two distinct port numbers.
* A publisher does not know nor care about who its subscribers are (as a matter of fact,
this is the idea behind pub/sub). All communication of published messages to subscribers
are managed by the broker.
* Broker’s responsibility is to actively pull messages from publishers and push these over
to subscribers based on their preferences on topics. Every message is associated with
one and only one topic.
* Any publisher could post a message on any topic. Similarly, a subscriber may register for
one or more topics, which will occur upon connection to the broker. Besides this setup
task, topic management will be excluded entirely from this project.
* A subscriber should be online to get messages. A publisher should be online to post
messages. While subscribers and publishers enter and go as they like, broker will be
online all the time.

* Especially on the broker application, you will need to use multi-threading excessively.
Make sure that all state-related updates are posted on the console neatly (i.e., using
mutual exclusion per thread access to the console).
* Subscribers will be implemented as dummy sinks. Their sole responsibility will be to
receive messages from particular topics and display them on the console. Consequently,
user interaction will be limited to the setup phase (i.e., topic registration) for subscriber
applications.
* Publishers will keep listening for message posts from the end user over the console. For
each message, the user will enter: (a) topic, (b) body.

Here are some details on certain event described above.
* A new publisher wakes up with the command


_publisher 192.168.0.1:4444 pub1 sports;news_

In this command, the first token is the publisher program’s executable name. The rest are
what we call command line arguments. The first argument is the broker’s socket connection
string (IP and port#), the second is this publisher’s name and the last one is the list of topics.
Notice that the list may contain multiple topic names separated with semi-column.
- Publisher then provides two options to its end user: (1) publish, (2) exit. For the choice
publish, the end user should specify the topic and the body of the message. Exit case is
obvious.
- Broker is a daemon process – it does not interact with any human user. Yet, in this project
the broker will be displaying various messages received from the existing publishers, sent to
the existing subscribers and state statistics like # of alive subs and pubs. Please implement
the broker with proper console output messages displayed periodically.
- A new subscriber wakes up with a command similar to the publisher. As mentioned before,
on the broker side, you may want to set a separate port# for subscribers.
- Subscriber does nothing beyond displaying the received messages. Notice that messages may
arrive from various publishers (based on the topics registered) concurrently. The subscriber
should manage access to console carefully.
- The end user may terminate a subscriber by shutting down the process. This should not
disrupt the processing on the broker side (i.e., causing an exception).