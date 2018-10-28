
### active container kill

    docker rm -f $(docker ps -aq)
    
    
### clear cached network

    docker network prune
    
### docker exec

    docker exec -it peer0.org1.example.com /bin/bash