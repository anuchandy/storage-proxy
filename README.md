# storage-proxy

1. Open the pom file in intelliJ, File -> Open -> browse-path-to-pom -> Select pom.xml
2. Compile the project
3. Open Dependencies settings, File -> Project Structure -> project Settings -> Modules -> Dependencies Tab

![Alt text](/images/Dependencies.png?raw=true "Dependencies")

4. Select Add Jar option, + -> Jars Or Directories -> Select 2.0.1.-SNAPSHOT directory
5. Remove the depedency Maven:com.microsoft.rest.v2:client-runtime:2.0.2 (see red square)

![Alt text](/images/Add_Rm_Dep.png?raw=true "add_RM_Dependencies")

6. Build and run

7. If you look at the output window, you can see the dependencies in the class path which includes path to the snapshot dependency added in step 4

```
/Library/Java/JavaVirtualMachines/jdk-11.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=59146:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/anuthomaschandy/work/anu_git/jva/storage-proxy/target/classes:/Users/anuthomaschandy/.m2/repository/com/microsoft/azure/azure-storage-blob/10.5.0/azure-storage-blob-10.5.0.jar:/Users/anuthomaschandy/.m2/repository/io/netty/netty-handler/4.1.28.Final/netty-handler-4.1.28.Final.jar:/Users/anuthomaschandy/.m2/repository/io/netty/netty-transport/4.1.28.Final/netty-transport-4.1.28.Final.jar:/Users/anuthomaschandy/.m2/repository/io/netty/netty-resolver/4.1.28.Final/netty-resolver-4.1.28.Final.jar:/Users/anuthomaschandy/.m2/repository/io/netty/netty-codec/4.1.28.Final/netty-codec-4.1.28.Final.jar:/Users/anuthomaschandy/.m2/repository/io/netty/netty-handler-proxy/4.1.28.Final/netty-handler-proxy-4.1.28.Final.jar:/Users/anuthomaschandy/.m2/repository/io/netty/netty-codec-socks/4.1.28.Final/netty-codec-socks-4.1.28.Final.jar:/Users/anuthomaschandy/.m2/repository/io/netty/netty-buffer/4.1.28.Final/netty-buffer-4.1.28.Final.jar:/Users/anuthomaschandy/.m2/repository/io/netty/netty-common/4.1.28.Final/netty-common-4.1.28.Final.jar:/Users/anuthomaschandy/.m2/repository/io/netty/netty-codec-http/4.1.28.Final/netty-codec-http-4.1.28.Final.jar:/Users/anuthomaschandy/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.8.11/jackson-datatype-jsr310-2.8.11.jar:/Users/anuthomaschandy/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.8.0/jackson-annotations-2.8.0.jar:/Users/anuthomaschandy/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.8.11/jackson-core-2.8.11.jar:/Users/anuthomaschandy/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.8.11/jackson-databind-2.8.11.jar:/Users/anuthomaschandy/.m2/repository/com/fasterxml/jackson/dataformat/jackson-dataformat-xml/2.8.11/jackson-dataformat-xml-2.8.11.jar:/Users/anuthomaschandy/.m2/repository/com/fasterxml/jackson/module/jackson-module-jaxb-annotations/2.8.11/jackson-module-jaxb-annotations-2.8.11.jar:/Users/anuthomaschandy/.m2/repository/org/codehaus/woodstox/stax2-api/3.1.4/stax2-api-3.1.4.jar:/Users/anuthomaschandy/.m2/repository/com/fasterxml/woodstox/woodstox-core/5.0.3/woodstox-core-5.0.3.jar:/Users/anuthomaschandy/.m2/repository/io/reactivex/rxjava2/rxjava/2.2.0/rxjava-2.2.0.jar:/Users/anuthomaschandy/.m2/repository/org/reactivestreams/reactive-streams/1.0.2/reactive-streams-1.0.2.jar:/Users/anuthomaschandy/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/Users/anuthomaschandy/work/anu_git/jva/storage-proxy/2.0.1-SNAPSHOT/client-runtime-2.0.1-SNAPSHOT.jar storage.proxy.App
```

