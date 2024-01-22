# pub-sub-java

### Maven Architypeでプロジェクト生成
```bash
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

### pom.xmlの編集
PubSubの追加
```xml
<dependency>
    <groupId>com.google.cloud</groupId>
    <artifactId>google-cloud-pubsub</artifactId>
    <version>1.125.13</version>
</dependency>
```
### アプリケーションの作成

###　環境変数の作成
/pub-sub-java/my-app/src/main/resources/config.propertiesに以下の設定をする。
```
project.id=
topic.id=
subscription.id=
```

### MavenのBuild
```bash
mvn clean package
```

### アプリケーションの実行
```bash
java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.CreateTopicExample   
```

```bash
java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.PublishMessageExample  
```

```bash
zjava -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.SubscribeMessageExample 
```
