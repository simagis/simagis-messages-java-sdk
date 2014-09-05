Simagis Live API SDK 
=========================

The purpose of Simagis Live API is to provide data interface between Simagis Live server and third party systems such as LIS.
The communication is based on HTTP POST method with JSON content and BASIC authorization 

Example of Message Generation in JAVA
---------

[SendMessage.java](messages-samples/src/main/java/com/simagis/live/messages/samples/SendMessage.java)

This code will invoke API, update attributes for a folder on Simagis Live server and return results of transaction   


Test messages-samples.jar file
-----------
This executable may be used for API testing

Before download and testing you must register user account at <https://host.simagis.com/live/register>   

Download [messages-samples.jar](http://host.simagis.com/live/users/#w:messages/f:3e196188-22f4-40b2-b4e6-932cfdc03c65/s:p:ae5c2027-f2ce-460c-89a1-5ee5b74f8631)

Example of usage:

    java -cp messages-samples.jar com.simagis.live.messages.samples.SendMessage https://userId:password@host.simagis.com/live/messages SimpleMessage.json

or

    java -cp messages-samples.jar com.simagis.live.messages.samples.SendMessage https://userId:password@host.simagis.com/live/messages


Example of JSON argument statement
-----------
[SimpleMessage.json](messages-samples/src/main/resources/com/simagis/live/messages/samples/SimpleMessage.json)

    {
        "message": {
            "workspace": "messages",
            "folder": "message-test",
            "folderAutoCreate": true
        },
    
        "attributes": [
            {
                "class": "com.simagis.live.attr.med.PatientID",
                "value": "John Smith"
            },
            {
                "class": "com.simagis.live.attr.med.PatientAge",
                "value": 75
            }
        ]
    }
This JSON statement will update target attributes on Simagis Live server.
