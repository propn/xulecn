echo off
set AXIS2_HOME=D:\java\axis2-1.5

#Generating the Service using ADB
%AXIS2_HOME%\bin\WSDL2Java -uri sayHello.wsdl -p org.leixu.services.hello.client -d adb -s -ss -sd -ssi -o build\service

#Generating a Client using ADB
%AXIS2_HOME%\bin\WSDL2Java -uri sayHello.wsdl -p org.leixu.services.hello.client -d adb -s -o build\client





