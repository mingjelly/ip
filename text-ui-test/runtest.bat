@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java

REM no error here, errorlevel == 0
SET tests=1,2,3

FOR %%i IN (%tests%) DO (
    echo Running test %%i
    if exist ACTUAL%%i.TXT del ACTUAL%%i.TXT

    REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
    java -classpath ..\bin Jellicent < input%%i.txt > ACTUAL%%i.TXT

    FC ACTUAL%%i.TXT EXPECTED%%i.TXT
    IF ERRORLEVEL 1 (
        echo Test%%i FAILED
    ) ELSE (
        echo Test%%i PASSED
    )
)

echo ALL TESTS COMPLETED.
pause