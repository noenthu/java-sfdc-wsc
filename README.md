# Sample Project to call SFDC SOAP API using Partner WSDL from Java

## DESCRIPTION

This repo contains sample code and the partner_stub generated from force.com wsc jar

To Run this code

```
java -cp target/wsc-app-1.0-SNAPSHOT.jar com.noenthu.wscapp.App
```
Or

```
mvn exec:java -Dexec.mainClass="com.noenthu.wscapp.App"
```

## PREREQS

Clone this repo  
```
git clone https://github.com/noenthu/java-sfdc-wsc.git && cd java-sfdc-wsc
```

Build the Project
```
mvn clean
mvn package
```


