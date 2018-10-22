### container kill

    docker rm -f $(docker ps -aq)
   
### clear any cached network

    docker network prune
    
### chaincode 삭제
    docker rmi dev-peer0.org1.example.com-fabcar-1.0-5c906e402ed29f20260ae42283216aa75549c571e2e380f3615826365d8269ba
   
   
### log 출력
    docker logs -f ca.example.com
    