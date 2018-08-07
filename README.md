# ApiSample
api网关测试

基于spring cloud 的api网关

Spring Boot 版本:1.5.13.RELEASE

Spring Cloud 版本：Edgware.SR4

端口信息：

Eureka: 1111

log-sevice:8001

alarm-service:8002

fegin: 2222

zuul：8000

运行顺序：
Eureka->(log/alarm)-service->zuul

验证调用接口（postman）：

Post：

localhost:8000/log/queryInfo;请求参数：name 
localhost:8000/alarm/queryInfo;请求参数：name 

Delete：

localhost:8000/log/delete?name=1

localhost:8000/alarm/delete?name=1
