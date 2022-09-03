**Overview and Installation Steps**

The repository contains Full Stack code for services that can used to book a session/slot.
myfresher/myapi -> BE code
myfresher/amyapi-fe -> FE code
myfresher/StudentSessionApp -> Android Code

## To start BE :

1. Install python3.
2. Install dependencies from requirements.txt
3. run : cd myfresher/
4. run : python3 manage.py mygrate
5. run : python3 manage.py runserver
6. BE web server starts at http://127.0.0.1:8000/
7. run : redis-server
8. redis server starts at http://127.0.0.1:6379/

---

## To state FE (assuming you're in the root directory)

1. run : cd myfresher/myapi-fe
2. run : nvm use
3. run : yarn install
4. run : yarn start
5. FE web server starts at http://127.0.0:3000/
