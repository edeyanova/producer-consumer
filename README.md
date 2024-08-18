Create a prime number selector. 
For the sake of scalability, the task should be split into 2 services: A Producer and a Consumer.

Producer:
The producer should generate a stream of random numbers (up to 5 numbers per second / filled stream size of maximum 100 numbers) and send them to the consumer.
Also, please write down the content of the stream in a csv file so that the result can be verified against it.

Consumer:
The consumer should consume the stream of random numbers and identify prime numbers from it. 
The identified prime numbers should be written to a csv file. 
The order of prime numbers is not important; however, use a “,” as a delimiter between each number.
