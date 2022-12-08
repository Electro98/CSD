# <center>CSD
## Описание
Работа Турова Евгения по предмету "РКИС"

Текущие теги:
- `work-3`
- `work-4`
- `work-5`
- `work-6`
- `work-7`

## Инструкция по развёртыванию
### Подготовка

Для запуска необходимо:
- `Apache Tomcat` версии не ниже 8.5.83
- `ActiveMQ` версии не ниже 5.17.3

После установки всех зависимостей указанных в `pom.xml`.
Необходимо запустить следующую команду в терминале:
```shell
$ mvn clean package
```

Она сгенерирует файл `csd.var` в папке `target/`,
его необходимо скопировать в папку, где установлен apache tomcat.
По пути: `CATALINA_HOME\webapps\`.

Клиент ожидает, что сервер будет запущен на порте `8081`,
изменить при необходимости в `CATALINA_HOME/conf/server.xml`.

### Запуск

Запустить `apache tomcat` с помощью `startup.sh` или `startup.bat`.

Запустить `ActiveMQ` на порте `8161`.

Запустить файл `Client.java`.
