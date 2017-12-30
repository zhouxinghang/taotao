manager-web:8081

manager-service:8080

content:8083

portal:8082

search-service:

search-web:8085

### bug

*.html的url，SpringMVC不能将其响应为json数据，只能是一个html页面，否者会报406 Not Acceptable
如果是*.action的url，SpringMVC可以将其转换为json数据。