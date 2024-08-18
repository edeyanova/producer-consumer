# Producer and Consumer Microservices

## Overview

This project contains two microservices: Producer and Consumer. 

The Producer generates random numbers and sends them to the Consumer, which identifies prime numbers and writes them to a CSV file. 
The consumer consumes the stream of random numbers and identifies prime numbers from it.
The identified prime numbers are written to a csv file with “,” as a delimiter between each number.

Both services are containerized using Docker and orchestrated with Docker Compose.

## Prerequisites

- Docker
- Docker Compose

## Setup Instructions

### 1. Clone the Repository

First, clone this repository to your local machine:

<code>git clone https://github.com/edeyanova/producer-consumer.git</code>

<code>cd your-repo-name</code>

### 2. Build the Docker Images
Before running the services, build the Docker images for both Producer and Consumer:

<code>docker-compose build</code>

This command builds the images using the Dockerfiles located in the ./producer and ./consumer directories.

### 3. Start the Services
Start both services using Docker Compose:
<code>docker-compose up</code>

Producer will be accessible on port 8080.

Consumer will be accessible on port 8081.

### 4. Stop the Services
To stop the services, use:

<code>docker-compose down</code>

This command stops and removes all containers defined in the docker-compose.yml file.

### 5. Accessing Logs
To view the logs for all services:

<code>docker-compose logs</code>

To view logs for a specific service, use:

<code>docker-compose logs producer</code>

<code>docker-compose logs consumer</code>
