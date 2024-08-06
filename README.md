# worbuddy-app
Here, we have the API's of a workbuddy app.

With the help of this app, you can add/delete/update an workitem and also add comments to it.

<br>

Below are the steps to run these api's in a virtual-machine or local-system.
## Running Guide

### Prerequisites

You need [Java](https://www.java.com/en/download/help/download_options.html) and any one of the below installed:
- [MongoDB](https://www.mongodb.com/docs/manual/administration/install-community/) and [Redis](https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/)

   **OR**

- [Docker](https://www.docker.com/products/docker-desktop/)

### Steps :

1. **Clone the Repository**

   Clone the repository using the following command:

   ```bash
   git clone https://github.com/aj-learnings/workbuddy-api.git
   cd workbuddy-api
   ```
   
2. **Install mongodb and redis with docker** (You can skip this step if these services are already present)

   ```bash
   docker-compose up -d
   ```
   
3. **Run the application**

   a. If you have any IDE like IntelliJ or Eclipse, follow the below steps :
   
   1. Open the project in that IDE.
   2. Navigate to the main file [WorkBuddyApplication.java](https://github.com/aj-learnings/workbuddy-api/blob/master/src/main/java/com/ajlearnings/workbuddy/WorkBuddyApplication.java)
   3. Right click on the file and click the **Run** button.

   b. If you wish to run the application without an IDE i.e., from command line, execute the following commands :
   
      ```bash
      ./mvnw clean package
      java -jar "target/workbuddy-api-0.0.1-SNAPSHOT.jar"
      ```

## Documentation

The [postman collection and environment](https://github.com/aj-learnings/workbuddy-api/tree/master/postman) is present in the repository. You can import that in [Postman](https://www.postman.com/downloads/).