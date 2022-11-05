FROM maven:3.8.3-openjdk-17 AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package -Dmaven.test.skip=true 
RUN java -Djarmode=layertools -jar /root/target/EnergyMonitoringSystem-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/EnergyMonitoringSystem-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:17-alpine



COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./

EXPOSE 3001

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]    