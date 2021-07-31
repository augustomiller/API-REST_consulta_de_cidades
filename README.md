# API REST de consulta de Cidades

## Requirementos

* Comandos Linux, Git, Java 8, Docker, IDE (IntelliJ Community)

## Banco de Dados

### Postgres

* [Postgres Docker Hub](https://hub.docker.com/_/postgres) - Vamos instalar o Docker;

Comando para criar a base de dados através de uma imagen no Docker.

```shell script
docker run --name cities-container -d -p 5432:5432 -e POSTGRES_USER=postgres_user_city -e POSTGRES_PASSWORD=super_password -e POSTGRES_DB=cities postgres
```
![Captura de Tela 2021-07-30 às 22 22 40](https://user-images.githubusercontent.com/990877/127724338-e7046418-1c24-457b-82f6-eca65991077c.png)

### Vamos popular os dados necessários no Banco de Dados criado na faze anterior. Reaproveitaremos esse projeto que já esta pronto aqui na comunidade: [DATA](https://github.com/chinnonsantos/sql-paises-estados-cidades/tree/master/PostgreSQL)


```shell script
# Crie no seu ambiente os seguintes diretórios, na forma hierarquica apresentada:

cd ~/workspace/sql-paises-estados-cidades/PostgreSQL

# Com os arquivos criados vamos pegar os arquivos .sql's do projeto DATA e colocar dentro do diretório Postgres:

workspace/sql-paises-estados-cidades/PostgreSQL 
➜ ls
cidade.sql estado.sql pais.sql

# Vamos executar o comando de forma interativa que nos permite acessar o Bash do nosso container (Postgres)

docker run -it --rm --net=host -v $PWD:/tmp postgres /bin/bash

# Agora já no bash do Docker vamos executar os comandos para criar e popular as tabelas que utilizaremos para executar as consultas relevantes;

psql -h localhost -U postgres_user_city cities -f /tmp/pais.sql
psql -h localhost -U postgres_user_city cities -f /tmp/estado.sql
psql -h localhost -U postgres_user_city cities -f /tmp/cidade.sql

psql -h localhost -U postgres_user_city cities

# Esse comando permite habilitar no nosso BD um plugin para podermos tratar as conslutas de Latitude x Longitude de uma forma mais especializada:

CREATE EXTENSION cube; 
CREATE EXTENSION earthdistance;
```
Documentações sobre as fórmulas matemáticas utilizadas para calcular a distância entre pontos longitudinais:

* [Postgres Earth distance](https://www.postgresql.org/docs/current/earthdistance.html)
* [earthdistance--1.0--1.1.sql](https://github.com/postgres/postgres/blob/master/contrib/earthdistance/earthdistance--1.0--1.1.sql)
* [OPERATOR <@>](https://github.com/postgres/postgres/blob/master/contrib/earthdistance/earthdistance--1.1.sql)
* [postgrescheatsheet](https://postgrescheatsheet.com/#/tables)
* [datatype-geometric](https://www.postgresql.org/docs/current/datatype-geometric.html)

### Acessando:

```shell script
docker exec -it cities-db /bin/bash

psql -U postgres_user_city cities
```

### Exemplo de Query que o nosso plugin do Postgres habilita para podermos utilizar valores especiais:

Point
```roomsql
select ((select lat_lon from cidade where id = 4929) <@> (select lat_lon from cidade where id=5254)) as distance;
```

Cube
```roomsql
select earth_distance(
    ll_to_earth(-21.95840072631836,-47.98820114135742), 
    ll_to_earth(-22.01740074157715,-47.88600158691406)
) as distance;
```

## Estrutura do ambiente Spring Boot

* [https://start.spring.io/](https://start.spring.io/)

![Captura de Tela 2021-07-30 às 22 45 49](https://user-images.githubusercontent.com/990877/127725001-9e39c33c-9b5e-4d61-9d83-165b6f253149.png)

### Documentção do Spring

* [jpa.query-methods](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)

### Propiedades

* [appendix-application-properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html)
* [jdbc-database-connectio](https://www.codejava.net/java-se/jdbc/jdbc-database-connection-url-for-common-databases)

### Tipos

* [JsonTypes](https://github.com/vladmihalcea/hibernate-types)
* [UserType](https://docs.jboss.org/hibernate/orm/3.5/api/org/hibernate/usertype/UserType.html)

