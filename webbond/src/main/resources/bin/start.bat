@echo off
set ENV=dev
if "%1" == "" goto doit
set ENV=%1
:doit
set JAVA_OPT= -Xms512M -Xmx1024M -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:.\gc.log
set SYS_OPT=
java %JAVA_OPT% %SYS_OPT% -Dspring.config.name=application-%ENV%.properties -cp "..\conf;..\lib\*" com.sumscope.cdh.webbond.App