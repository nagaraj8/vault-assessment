**Challenge:**

In finance, it's common for accounts to have so-called "velocity limits". In this task, you'll create a Java Spring boot application that accepts or declines attempts to load funds into customers' accounts in real-time.

Each attempt to load funds will come as a single-line JSON payload, structured as follows:

{
"id": "1234",
"customer_id": "1234",
"load_amount": "$123.45",
"time": "2018-01-01T00:00:00Z"
}
Each customer is subject to three limits:

A maximum of $5,000 can be loaded per day
A maximum of $20,000 can be loaded per week
A maximum of 3 loads can be performed per day, regardless of amount
As such, a user attempting to load $3,000 twice in one day would be declined on the second attempt, as would a user attempting to load $400 four times in a day.

For each load attempt, you should return a JSON response indicating whether the fund load was accepted based on the user's activity, with the structure:

{ "id": "1234", "customer_id": "1234", "accepted": true }
You can assume that the input arrives in ascending chronological order and that if a load ID is observed more than once for a particular user, all but the first instance can be ignored (i.e. no response given). Each day is considered to end at midnight UTC, and weeks start on Monday (i.e. one second after 23:59:59 on Sunday).

**Solution:**

Clone the project and then import it as maven spring boot project to your IDE.

Project is built on Java - SpringBoot with maven.
Execute the command to build the project.
`mvn clean install`

src/main/resources directory of vault-assessment project contains input.txt file which contains input data request.
VaultAssessmentApplicationTests.java file uses this file as input by default. 
For testing with customized input, please edit the input.txt file or provide a custom file inside VaultAssessmentApplicationTests.java file.
Then just run the application using the test file **VaultAssessmentApplicationTests.java** file.

Output will be generated inside src/main/resources/test_result.txt file of vault-assessment project.
As part of the test this generated file will be compared against output.txt file provided under same directory.

In case of custom file input, output.txt file needs to be changed or new file has to be added(provide the expected output file path in VaultAssessmentApplicationTests.java file) for expected result.

Currently test_result.txt file contains the generated output for the given input.txt file.

vault-assessment project has unit test cases(junits) added which will cover most of the methods of the project.

Limitations / Enhancements:

- I have used the Map data structure as this will work fine with around 1000 to 2000 records of data.
- vault-assessment project can be further enhanced to have the database where the changes could be minimal.
- Also, we can enhance the project using caching mechanism instead of using the Map. 
- In this case the changes are very minimal, and we can just replace the map with the cache.
