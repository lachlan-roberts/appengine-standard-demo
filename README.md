
## Running Locally
Use the command `mvn appengine:stage` to build the project.
Then use `./debug.sh` script to deploy locally.

You may need to update `APPENGINE_JAVA_STANDARD` and `APP_LOCATION` vars inside the `debug.sh` file.

## Remote Deployment

Run `gcloud init` from project root.


Use this in maven plugin configuration to pick up credential set from `gcloud init`.
```
<plugin>
    <groupId>com.google.cloud.tools</groupId>
    <artifactId>appengine-maven-plugin</artifactId>
    <version>2.4.4</version>
    <configuration>
        <projectId>GCLOUD_CONFIG</projectId>
        <version>GCLOUD_CONFIG</version>
    </configuration>
</plugin>
```


May need to run `gcloud auth login` to allow permissions from browser.

Run `mvn clean package appengine:stage` to build application locally at `target/appengine-staging`.

Run `mvn clean package appengine:deploy` to deploy application. This by default uses the `default` service within the project.


To deploy with a custom runtime you need to put `runtime-deployment` jars inside of `webapp` directory (`runtime-impl.jar`, `runtime-main.jar`, `runtime-shared.jar`). Then deploy with the following inside the `appengine-web.xml` file.

```
<entrypoint>
  java
  --add-opens java.base/java.lang=ALL-UNNAMED
  --add-opens java.base/java.nio.charset=ALL-UNNAMED
  -showversion -XX:+PrintCommandLineFlags
  -Djava.class.path=runtime-main.jar
  -Dclasspath.runtimebase=.:
  com/google/apphosting/runtime/JavaRuntimeMainWithDefaults
  --fixed_application_path=.
  .
</entrypoint>
```


To view appengine deployments and logs go to
https://console.cloud.google.com/appengine