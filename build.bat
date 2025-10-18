@echo off
setlocal ENABLEDELAYEDEXPANSION
echo Building E-Commerce Application...

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

REM Generate sources list recursively (Windows-safe)
echo Generating sources list...
set SOURCES_FILE=.sources.txt
if exist %SOURCES_FILE% del %SOURCES_FILE%
for /R src\main\java %%f in (*.java) do (
  echo %%f>> %SOURCES_FILE%
)

REM Compile all Java files
echo Compiling Java files...
javac -cp "lib/*" -d bin @%SOURCES_FILE%
set BUILD_STATUS=%ERRORLEVEL%

if %BUILD_STATUS% EQU 0 (
    echo Build successful!
    echo.
    echo To run the application:
    echo java -cp "bin;lib/*" com.ecommerce.ECommerceApplication
) else (
    echo Build failed! Please check the error messages above.
)

exit /b %BUILD_STATUS%
