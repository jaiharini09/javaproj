@echo off
if "%JAVA_HOME%"=="" set "JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.13.21-hotspot"
if "%DB_PASSWORD%"=="" for /f "tokens=2*" %%a in ('reg query "HKCU\Environment" /v DB_PASSWORD 2^>nul') do set "DB_PASSWORD=%%b"
echo ========================================================
echo Starting SkillBridge - Student Skill Exchange Platform
echo Java Home: %JAVA_HOME%
echo ========================================================
call .\mvnw.cmd spring-boot:run
pause
