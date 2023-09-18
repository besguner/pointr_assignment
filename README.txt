Prerequisites
-Chrome and Firefox browsers must be installed, the test are assuming the browsers are not portable and added to env path in os level
-Maven and java should be installed and added to path
-The paths used for files and folders follow the Windows file path structure.

Config
-The config.txt is located under /src/text. It has two entries, the first one contains credentials and search term for the success scenario and the second one contains the same data for the negative tests. It is assumed that each entry is followed by an empty line.

Run
-Run “mvn test” inside the assignment folder where the pom.xml is located
-The failed login test cases fail on some occasions due pending response from 3rd party analytics or cdn on login button press. In this case wait for the timeout.

Results
-A total of 8 test cases will be run (4 test cases x 2 browsers), the test report will be located under /target/surefire-reports
-For the 4 test cases that produces results.txt and screenshots, 4 folders will be created under /target/artifacts. Each folder will contain a screenshot and a .txt file.
