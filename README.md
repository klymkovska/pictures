# Pictures
Export products pictures Cron Job

Requisites:

```
Hybris version 1905.15
Java version 11
MySQL version 8
mysql-connector 8.0.15
```

## :hot_face: How to execute

- Run docker container with a command

```
docker run --name pic \
    -p 13306:3306 \
    -e MYSQL_ROOT_PASSWORD=pictures \
    -e MYSQL_DATABASE=pic \
    -e MYSQL_USER=hybris \
    -e MYSQL_PASSWORD=hybris \
    -d mysql:latest
```
- download source code with ```git clone```
- put OOTB hybris folders ```modules``` and ```platform``` to the ```bin``` directory
- put mysql connector to a directory ```/pictures/bin/platform/lib/dbdriver```
- add a property to your empty ```local.properties``` that refers to a directory with a file ```10-local.properties``` :
```hybris.optional.config.dir=/pathToProject/pictures/config/envs/local```
- run ```. ./setantenv.sh``` command
- run ```ant clean all``` command
- run ```ant initialize``` command
- run ```./hybrisserver.sh``` command
- check ```http://localhost:9002/hac``` and ```http://localhost:9002/backoffice```

## :bomb: How to test

- go to Backoffice  ```http://localhost:9002/backoffice```, navigate to the list of cron jobs
- search for ```exportPicturesJob```
- go to a tab ```FILE UPLOAD```
- apload file ```products.csv``` in the section ```General```
- click an action icon "sync" to save this file as a media to a field ```productFile```
- run the cron job
- when it's finished look for a zip archive in the field ```imaged```
