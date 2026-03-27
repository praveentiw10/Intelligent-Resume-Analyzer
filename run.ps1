# Set JAVA_HOME for this session
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH

Write-Host "Checking Java version..." -ForegroundColor Cyan
java -version

Write-Host "Starting the AI Resume Analyzer..." -ForegroundColor Green
.\mvnw.cmd spring-boot:run
