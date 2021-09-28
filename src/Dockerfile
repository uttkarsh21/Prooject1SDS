FROM bellsoft/liberica-openjdk-alpine-musl:17 AS client-build
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac client/*.java

FROM bellsoft/liberica-openjdk-alpine-musl:17 AS server-build
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac server/*.java
# cmd to run server locally - java server.ServerApp 1111 5555
CMD ["java", "server.udpServer", "1234"]

