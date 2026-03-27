@echo off
SET "JAVA_HOME=C:\Program Files\Java\jdk-21"
SET "PATH=%JAVA_HOME%\bin;%PATH%"

echo Checking Java version...
java -version

echo Starting the AI Resume Analyzer...
call mvnw.cmd spring-boot:run
pause
