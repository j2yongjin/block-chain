
## hyperledger 메뉴얼

    https://hyperledger-fabric.readthedocs.io/en/latest/tutorials.html

## hyperledger 사전 설치 가이드

   https://hyperledger-fabric.readthedocs.io/en/latest/prereqs.html

   ### OS
        
        VirtualBox , Ubuntu-18.04 
          
   ### docker 설치
   
   참고 자료
   
   https://docs.docker.com/get-started/
   https://subicura.com/2017/01/19/docker-guide-for-beginners-2.html
   
    
   docker 설치 자동 스크립트
        
        curl -fsSL https://get.docker.com/ | sudo sh
        
   sudo 없이 사용하기 
       
        sudo usermod -aG docker $USER # 현재 접속중인 사용자에게 권한주기
        sudo usermod -aG docker your-user # your-user 사용자에게 권한주기
        
        
   ### docker-compose 설치
   
        curl -L "https://github.com/docker/compose/releases/download/1.9.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
        chmod +x /usr/local/bin/docker-compose
   
        docker-compose version
        
   ### go 설치
    go 는 hyperledger  각 componet 개발의 메인 언어이다.
    
    wget -c https://storage.googleapis.com/golang/go1.11.1.linux-amd64.tar.gz
    
    tar -C ~/ -xzf go1.11.1.linux-amd64.tar.gz
    
    vim  설치
    
    apt-get install vim
    
    vi ~/.profile   사용자 환경 변수 설정
    export GOPATH=$HOME/go
    export PATH=$PATH:$GOPATH/bin
     
     
   ### nodejs 설치
   
   wget -qO- https://deb.nodesource.com/setup_8.x | sudo -E bash -
   sudo apt-get install -y nodejs
   
   npm install npm@5.6.0 -g
   
   node -v   // version 확인
   
## 개발 환경 구성

  Install Sample , Binary , Docker images
  
  curl -sSL http://bit.ly/2ysbOFE | bash -s 1.3.0
   
  curl -sSL http://bit.ly/2ysbOFE | bash -s <fabric> <fabric-ca> <thirdparty>
  curl -sSL http://bit.ly/2ysbOFE | bash -s 1.3.0 1.3.0 0.4.13
   
   
   
        
   
