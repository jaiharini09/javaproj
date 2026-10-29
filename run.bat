@echo off
if "%JAVA_HOME%"=="" set "JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.13.21-hotspot"
if "%DB_PASSWORD%"=="" for /f "tokens=2*" %%a in ('reg query "HKCU\Environment" /v DB_PASSWORD 2^>nul') do set "DB_PASSWORD=%%b"
if "%DB_PASSWORD%"=="" set "DB_PASSWORD=Harini@1310"

echo ========================================================
echo Starting SkillBridge - Student Skill Exchange Platform
echo Java Home: %JAVA_HOME%
echo ========================================================

rem Auto-free port 8080 if occupied by any previous background instance
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8080 ^| findstr LISTENING 2^>nul') do (
    echo Port 8080 is currently occupied by PID %%a. Freeing port...
    taskkill /F /PID %%a >nul 2>&1
)

call .\mvnw.cmd spring-boot:run
pause
