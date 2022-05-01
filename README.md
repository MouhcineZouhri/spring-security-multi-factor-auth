# spring-security-multi-factor-auth

#Flow  : 

1 - user enter username and password  to get opt code .
![image](https://user-images.githubusercontent.com/99892846/166134687-7fdfeff6-5a85-4d95-ab89-3ea4b123b9aa.png)


2 - use code to obtain an access token and refresh token .
![image](https://user-images.githubusercontent.com/99892846/166134711-01efdc38-2253-4640-a612-0217bbbdbc25.png)

2' - can use refresh token to get new access token .
![image](https://user-images.githubusercontent.com/99892846/166134720-7cf69957-499e-4cc7-8e6f-e460bcc0afd5.png)

3 - then user can access to hello endpoint by providing access token .
![image](https://user-images.githubusercontent.com/99892846/166134737-0fafe64e-6a63-481e-9441-8b7769c72502.png)
