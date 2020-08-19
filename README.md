#### Требуемые технологии:
* Maven 3 (v.3.3.9) (для сборки проекта);
* Tomcat8 (для тестирования своего приложения);
* Результат выложить на GitHub.
#### Задача
Создать сервис по выгрузке файлов из произвольного каталога. 

Сервис должен предоставлять доступ к файлам на основании ролевой модели доступа.
По умолчанию все пользователи могут скачивать только файлы с
расширением **txt**, для получения доступа к другим расширениям пользователь
должен авторизоваться. Доступа к функции настройки ролей должен быть у
пользователя **admin**.

Требуется реализовать данный модуль, используя технологию
SERVLET, а также управление ролями и доступом через javax.servlet.Filter,
javax.servlet.Listener. ВЕБ интерфейс простейший.

##### Maven dependencies
* javax.servlet-api:4.0.1
* javax.servlet.jsp-api:2.3.3
* jstl:1.2
* mysql-connector-java:5.1.34
* hibernate-core:5.4.20.Final
* hibernate-entitymanager:5.4.19.Final
* lombok:1.18.12
* jbcrypt:0.4

##### REST Docs
- /                                     [GET] - index page
- /login                                [GET] - login page
- /api/login                            [POST] - api for log in user (need username & password params)
- /register                             [GET] - register page
- /api/register                         [POST] - api for register user (need username & password params)
- /admin                                [GET] - admin page
- /admin/user/{username}                [GET] - admin page for user edit
- /api/user/{username}/add              [POST|PUT] - add user permission (need permission,value & weight params)
- /api/user/{username}/remove           [POST|DELETE] - remove user permission (need permission,value & weight params)
- /admin/group/{roleName}               [GET] - admin page for role edit
- /api/group/{username}/add             [POST|PUT] - add role permission (need permission,value & weight params)
- /api/group/{username}/remove          [POST|DELETE] - remove role permission (need permission,value & weight params)