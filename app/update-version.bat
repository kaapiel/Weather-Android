setlocal enabledelayedexpansion
for /f "skip=1 tokens=2,2 delims==" %%g in (version.properties) do (SET var=%%g)
SET /A VAR=var-=1
SET HEADER=Edited by Jenkins
SET VERSION_CODE=VERSION_CODE=!VAR!
ECHO !HEADER!>>new_version.properties
ECHO !VERSION_CODE!>>new_version.properties
DEL version.properties
REN new_version.properties version.properties