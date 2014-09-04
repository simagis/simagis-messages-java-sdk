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

Download [messages-samples.jar](https://host.simagis.com/live/layers/attachment?project=Users%2Fmessages%2Fb6f355d5-a4e2-4cff-8908-2fa996dad03e&attachment=d7ae1e00-9b73-469e-9989-e1b678a45e7f)

Example of usage:

    java -cp messages-samples.jar com.simagis.live.messages.samples.SendMessage https://userId:password@host.simagis.com/live/messages SimpleMessage.json  

Example of JSON argument statement
-----------
[SimpleMessage.json](messages-samples/src/main/resources/com/simagis/live/messages/samples/SimpleMessage.json)

    {
        "message": {
            "workspaces": "messages",
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
