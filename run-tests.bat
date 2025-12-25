@echo off
setlocal 

echo.
echo Step 1: Cleaning previous build...
call mvn clean
if %errorlevel% neq 0 (
    echo ERROR: Maven clean failed
    pause
    exit /b 1
)

echo.
echo Step 2: Compiling the project...
call mvn compile
if %errorlevel% neq 0 (
    echo ERROR: Maven compile failed
    pause
    exit /b 1
)

echo.
echo Step 3: Running tests with detailed logging...
call mvn test -Dlog4j.configurationFile=src/test/resources/log4j2.xml
set TEST_RESULT=%errorlevel%

echo.
echo ========================================
echo Test Execution Completed!
echo ========================================

if %TEST_RESULT% equ 0 (
    echo ✅ All tests completed successfully
) else (
    echo ⚠️ Some tests may have failed - Check reports for details
)

echo.

echo 💡 Tips:
echo    - Check console output above for detailed API request/response logs
echo    - Review logs\automation.log for complete execution details
echo    - ExtentReport provides the most comprehensive test results
echo.

pause
