@echo off
if "%JAVA_HOME%"=="" set "JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.13.21-hotspot"
echo Starting SkillBridge with Java 21...
call .\mvnw.cmd spring-boot:run
pause
