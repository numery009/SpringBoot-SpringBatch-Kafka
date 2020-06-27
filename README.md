# SpringBoot-SpringBatch-Kafka
  Following are the steps to quick start of Kafka on ubutu--
  https://kafka.apache.org/quickstart
  
  Step 1: Download the code 
    
    > tar -xzf kafka_2.12-2.5.0.tgz
    > cd kafka_2.12-2.5.0
    
  Step 2: Start the server
  Kafka uses ZooKeeper so you need to first start a ZooKeeper server if you don't already have one. You can use the             convenience script packaged with kafka to get a quick-and-dirty single-node ZooKeeper instance.
  
    > bin/zookeeper-server-start.sh config/zookeeper.properties
        
   Now start the Kafka server:
   
    > bin/kafka-server-start.sh config/server.properties
   
   Step 3: Create a topic
   Let's create a topic named "test" with a single partition and only one replica:         
   
    > bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test

   Step 4: Send some messages
   Kafka comes with a command line client that will take input from a file or from standard input and send it out as            messages to the Kafka cluster. By default, each line will be sent as a separate message
   
   Run the producer and then type a few messages into the console to send to the server.
      
    > bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic test
    This is a message
    This is another message
   
  
   
