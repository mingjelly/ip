@ECHO OFF
REM ===========================================
REM Build and run Jellicent tests with packages
REM ===========================================

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM ========================
REM Compile all Java files
REM ========================
echo Compiling Java files...

javac -d ..\bin ^
..\src\main\java\jellicent\Jellicent.java ^
..\src\main\java\jellicent\command\*.java ^
..\src\main\java\jellicent\task\*.java ^
..\src\main\java\jellicent\parser\*.java ^
..\src\main\java\jellicent\storage\*.java ^
..\src\main\java\jellicent\ui\*.java

IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

echo Compilation successful.
echo.

REM ========================
REM Run Tests
REM ========================
SET tests=1,2

FOR %%i IN (%tests%) DO (
    echo Running test %%i...

    REM delete previous output if it exists
    if exist ACTUAL%%i.TXT del ACTUAL%%i.TXT
    if exist text-ui-test%%i.txt del text-ui-test%%i.txt

    REM create temporary copy of empty task file for this test
    copy data\empty-tasks.txt text-ui-test%%i.txt > nul

    REM run the program with fully-qualified class name and input redirection
    java -classpath ..\bin jellicent.Jellicent text-ui-test%%i.txt < input%%i.txt > ACTUAL%%i.TXT

    REM compare output to expected output
    FC ACTUAL%%i.TXT EXPECTED%%i.TXT
    IF ERRORLEVEL 1 (
        echo Test %%i FAILED
    ) ELSE (
        echo Test %%i PASSED
    )

    echo.
)

echo ALL TESTS COMPLETED.
pause
