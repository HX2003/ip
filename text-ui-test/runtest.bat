@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\quark\*.java ..\src\main\java\quark\ui\*.java ..\src\main\java\quark\task\*.java ..\src\main\java\quark\save\*.java ..\src\main\java\quark\parser\*.java ..\src\main\java\quark\exception\*.java ..\src\main\java\quark\command\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from cleanup.txt file
REM which will remove any persistent file from previous runs
REM and redirect the output to the ACTUAL-CLEANUP.TXT

java -classpath ..\bin quark/Quark < cleanup.txt > ACTUAL-CLEANUP.TXT

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin quark/Quark < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
