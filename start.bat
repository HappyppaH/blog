@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-17

echo ===== 启动博客系统 =====
echo.

echo [1/2] 启动后端 (Spring Boot :8080)...
start "Blog-Backend" cmd /k "cd /d E:\1Tool\代码\blog && set JAVA_HOME=C:\Program Files\Java\jdk-17 && mvnw spring-boot:run"

echo [2/2] 启动前端 (Vite :5173)...
start "Blog-Frontend" cmd /k "cd /d E:\1Tool\代码\blog\blog-frontend && npm run dev"

echo.
echo 后端启动中，约 10 秒后浏览器打开 http://localhost:5173/ 即可
echo 需要 MySQL 和 Redis 已在运行
