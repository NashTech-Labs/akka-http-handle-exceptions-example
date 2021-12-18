# Akka Http Handle Exceptions Example

- Exceptions may be thrown during route execution, and they are different from Rejections.
- This template is about handling exceptions in akka-http routes.

In this template, I have shown two ways of handling the exceptions in akka-http routes:
1. By explicitly defining the Exception Handlers
2. By using an implicit Custom Exception Handler

To test these Exception Handlers, run the individual example apps and hit the specified endpoints(via postman or through curl) which will throw some exception that will be handled by our exception handlers.

## Prerequisites

- Scala Build Tool(SBT), version 1.5.7
- Scala, version 2.12.12
- Akka-Http, version 10.1.7
