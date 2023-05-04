FROM openjdk:11.0.16-slim
WORKDIR /app
COPY ./target/*.jar /app.jar
# 修改时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone && touch /app.jar
# 环境变量
# docker run -e JAVA_OPTS="-Xmx512m -Xms64m" -e PARAMS="--spring.profiles.active=dev --server.port=8080" xxx
ENV JAVA_OPTS=""
ENV PARAMS=""
# 运行 jar 包
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /app.jar $PARAMS" ]