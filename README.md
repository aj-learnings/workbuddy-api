# [Worbuddy Application](https://www.youtube.com/watch?v=0F-E_kD6Spw)

Here, we have the APIs of a workbuddy app.

With the help of this app, you can add/delete/update a workitem and also add comments to it.

<br>

Below are the steps to run these apis in a virtual-machine or local-system.
## Running Guide

### Prerequisites

Before running the project, ensure you have the following prerequisites set up:

1. [Java](https://www.java.com/en/download/help/download_options.html)
2. [MongoDB Server](https://www.mongodb.com/try/download/community) and [Redis](https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/)
   
   You can set up MongoDB and Redis in one of the following ways:
   
   a.  Local Installation - Download and install both MongoDB and Redis on your system.

   b. [Docker](https://www.docker.com/products/docker-desktop/)

   c. Managed Db - You can opt to use cloud-based [MongoDB](https://www.mongodb.com/products/platform/atlas-database) and [Redis](https://app.redislabs.com/) services.

### Steps :

1. **Clone the Repository**

   Clone the repository using the following command:

   ```bash
   git clone https://github.com/aj-learnings/workbuddy-api.git
   cd workbuddy-api
   ```
   
2. **Install mongodb and redis with docker** (This step is needed when you don't have mongo and redis in your system and neither you are using managed dbs)

   ```bash
   docker-compose up -d
   ```
   Note:
   
   If you are not using docker, then you need to update the mongodb and redis details in the [properties](https://github.com/aj-learnings/workbuddy-api/blob/master/src/main/resources/application.yaml) file.

   
3. **Run the application**

   a. If you have any IDE like IntelliJ or Eclipse, follow the below steps :
   
   1. Open the project in that IDE.
   2. Navigate to the main file [WorkBuddyApplication.java](https://github.com/aj-learnings/workbuddy-api/blob/master/src/main/java/com/ajlearnings/workbuddy/WorkBuddyApplication.java)
   3. Right-click on the file and click the **Run** button.

   b. If you wish to run the application without an IDE i.e., from command line, execute the following commands :
   
      ```bash
      ./mvnw clean package
      java -jar "target/workbuddy-api-0.0.1-SNAPSHOT.jar"
      ```

4. **The application will be available at http://localhost:9401/api/workbuddy**

## Docker image
This code is also deployed as a docker image. Use the below command to run it as a container
```bash
docker run -d -p <your-port>:9401 --name workbuddy-api ajlearnings/workbuddy-api:v1
```
Check [here](https://hub.docker.com/r/ajlearnings/workbuddy-api) for the environment variables.

## Documentation

The [postman collection and environment](https://github.com/aj-learnings/workbuddy-api/tree/master/postman) is present in the repository. You can import that in [Postman](https://www.postman.com/downloads/).