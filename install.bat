@echo off
echo Building project...
mvn clean package -q

echo Creating execution script...
set PROJECT_DIR=%~dp0

(
    echo @echo off
    echo java -jar "%PROJECT_DIR%target\expense-tracker-1.0-SNAPSHOT.jar" %%*
) > "%TEMP%\expense-tracker.bat"

echo Installing...
copy "%TEMP%\expense-tracker.bat" "C:\Windows\System32\expense-tracker.bat" >nul

echo Installed successfully!
echo Use: expense-tracker --help
pause