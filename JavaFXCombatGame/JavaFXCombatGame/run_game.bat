@echo off
setlocal

echo Checking for Maven...
where mvn >nul 2>nul
if %errorlevel% equ 0 (
    echo Maven found globally.
    set MVN_CMD=mvn
) else (
    echo Maven not found globally. Checking for local portable Maven...
    
    :: Call PowerShell script to get/install Maven and return the path
    for /f "delims=" %%I in ('powershell -ExecutionPolicy Bypass -File "%~dp0setup_maven.ps1"') do set MVN_CMD=%%I
)

if "%MVN_CMD%"=="" (
    echo Failed to find or install Maven. Cannot proceed.
    pause
    exit /b 1
)

echo Using Maven at: %MVN_CMD%

echo.
echo Cleaning and Compiling...
call "%MVN_CMD%" clean javafx:run

if %errorlevel% neq 0 (
    echo.
    echo -----------------------------------------------------------------
    echo Application crashed or failed to build.
    echo -----------------------------------------------------------------
    pause
) else (
    echo.
    echo Application finished successfully.
)

endlocal
