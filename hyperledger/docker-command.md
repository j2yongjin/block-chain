
### active container stop

    docker stop $(docker ps -qa)
    
### active container kill

    docker rm -f $(docker ps -a -q)
    
    
### clear cached network

    docker network prune
    
### docker exec

    docker exec -it peer0.org1.example.com /bin/bash
    
### docker log
    docker logs -f peer0.org1.chaincode.com