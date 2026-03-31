# YouTube & Spotify APIs con Spring Boot

APIs de RapidAPI para búsqueda de videos en YouTube y canciones en Spotify.
La API de YouTube permite buscar videos por palabras clave y obtener información como título, canal e ID del video.
La API de Spotify (Musixmatch) permite buscar canciones por artista y título, obteniendo información del track y álbum.
Los resultados de ambas APIs se almacenan en una base de datos PostgreSQL en la nube (Neon).

## 1. APIs Utilizadas

**RapidAPI - YT-API**
Búsqueda de Videos en YouTube

**RapidAPI - Spotify Web API**
Búsqueda de Canciones y Artistas en Spotify

## 2. Spring Boot

- Java: JDK 17
- IDE: IntelliJ IDEA | Visual Studio Code | Codespace
- Maven: Apache Maven
- Frameworks: Spring Boot

## 3. Maven Dependencias

- spring-boot-starter-webflux
- spring-boot-starter-data-r2dbc
- lombok
- reactor-test
- r2dbc-postgresql
- springdoc-openapi-starter-webflux-ui

Dependencias Spring WebFlux + PostgreSQL (SQL)

Spring WebFlux | Data R2DBC | Project Reactor | R2DBC PostgreSQL

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-r2dbc</artifactId>
</dependency>
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>r2dbc-postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

Dependencias Swagger para Spring WebFlux

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
    <version>2.0.2</version>
</dependency>
```
