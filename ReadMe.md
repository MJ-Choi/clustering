## Test Clustering using Hazelcast

Change Origin to like this: 
- Convert Maven to Gradle
- Adapt AOP

---

### Test Script using Container
#### Precondition
- Docker
- Built Jar file

#### Process
1st. Run Script: `./runCluster`
```
# Copy JAR to same Path with Dockerfile
cp {LOCAL_PATH_WHERE_YOU_GIT_CLONED}/clustering/build/libs/clustering-0.0.1-SNAPSHOT.jar .
chmod +x clustering-0.0.1-SNAPSHOT.jar

# Create Container Image
docker build --tag cluster:0.1 ./

# Run Container cluster01
docker run -idt --name cluster01 cluster:0.1

# Run Container cluster02
docker run -idt --name cluster02 cluster:0.1
```

Dockerfile
```$xslt
# cent-jdk8 is LocalRepository Container Image
# if you don't have jdk container pull container image
FROM cent-jdk8

ADD clustering-0.0.1-SNAPSHOT.jar clustering.jar

ENTRYPOINT ["java", "-jar", "/clustering.jar"]
```

2nd. Test

2-1. Check Log using `$ docker logs -ft cluster01`

2-2. Call Endpoint
Access Conatiner using `$ docker exec -it container01 bash`
- Is This Node Leader : `$ curl localhost:8080/cluster/leader`

    result is:
    ```$xslt
    LEADER
  
    or
  
    CANDIDATE
    ```
- Get Cluster Members : `$ curl localhost:8080/cluster/members`
    result is:
    ```$xslt
    [root@b3bbf6bae4e3 /]# curl localhost:8080/cluster/members
    {"size":2,"members":[{"ip":"[172.17.0.5]:5701","role":"LEADER"},{"ip":"[172.17.0.6]:5701","role":"CANDIDATE"}]}
    ```
3rd. Remove Container
remove container and container images
Run Srcipt: `$ ./destoryCluster`
```$xslt
# Stop Container
docker stop cluster02 cluster01
docker ps -a | grep cluster

# Remove Container
docker rm cluster01 cluster02
docker ps -a | grep cluster

# Remove Container Images
docker rmi cluster:0.1
```