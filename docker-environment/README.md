# Introduction
The `docker-compose.yaml` file contains docker compose configuration for:
* a `mysql` server with following parameters:
  * default schema: `db`
  * a `root` user with password `dokidoki`
  * a `sudoku_club` user with password `dokidoki`
  * exposed on the `localhost:3306` port.

Docker compose will launch the above resources as containers on your host machine.

## Requirements
* `Docker` installation

## Launching the resources
Using the command-line:
```shell
docker compose -f "./docker-compose.yaml" up
```

