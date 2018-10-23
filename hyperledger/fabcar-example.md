## fabcar example
    https://hyperledger-fabric.readthedocs.io/en/latest/write_first_app.html

## 순서
   1) 개발 환경 구성 
   2) 앱에서 사용할 샘플 스마트 계약의 매개 변수를 학습합니다
   3) 원장에서 자산을 쿼리하고 업데이트 할 수있는 응용 프로그램 개발
   
   
### Install the clients & launch the network

   ./startFabric.sh
   
### docker log 출력

    docker logs -f ca.example.com
   
### 어드민 추가

    npm cache clean --force
    
    npm insall
 
    node enrollAdmin.js
    
    성공 로그 
    2018/10/22 13:36:05 [INFO] signed certificate with serial number 340191001829120559181204968664371397362060633466
    2018/10/22 13:36:05 [INFO] 172.19.0.1:51074 POST /api/v1/enroll 201 0 "OK"
    
### Register and Enroll user1
    새로 생성 된 admin eCert를 사용하여 이제 CA 서버와 통신하여 새 사용자를 등록하고 등록합니다
    
    node registerUser.js
    
    성공 로그
    2018/10/22 13:39:25 [INFO] 172.19.0.1:51080 POST /api/v1/register 201 0 "OK"
    2018/10/22 13:39:25 [INFO] signed certificate with serial number 357433460539293339108612625914030457375640499572
    2018/10/22 13:39:25 [INFO] 172.19.0.1:51084 POST /api/v1/enroll 201 0 "OK"


### 조회
    
    node query.js
    
###

    
    