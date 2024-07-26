# ScreenMatch API

ScreenMatch é uma API RESTful desenvolvida em Java Spring para fornecer informações sobre séries online. 
Esta API é utilizada por um frontend de uma plataforma de séries, permitindo que os usuários naveguem por séries, vejam detalhes, episódios e temporadas.

<br>

## Funcionalidades

- Listar todas as séries.
- Listar as 5 melhores séries.
- Listar lançamentos recentes de séries.
- Obter detalhes de uma série específica.
- Listar episódios de uma temporada específica de uma série.
- Listar os 5 melhores episódios de uma série.
- Listar séries por gênero.

<br>

## Endpoints

### Listar todas as séries

```java
GET /series
```

Retorna uma lista de todas as séries.

### Listar as 5 melhores séries

```java
GET /series/top5
```

Retorna uma lista das 5 séries mais bem avaliadas.

### Listar lançamentos recentes

```java
GET /series/releases
```

Retorna uma lista dos lançamentos recentes de séries.

### Obter detalhes de uma série específica

```java
GET /series/{id}
```

Parâmetros:
- `id`: ID da série.

Retorna os detalhes de uma série específica.

### Listar episódios de uma temporada específica

```java
GET /series/{id}/seasons/{seasonNumber}
```

Parâmetros:
- `id`: ID da série.
- `seasonNumber`: Número da temporada.

Retorna uma lista de episódios de uma temporada específica de uma série.

### Listar os 5 melhores episódios de uma série

```java
GET /series/{id}/seasons/top
```

Parâmetros:
- `id`: ID da série.

Retorna uma lista dos 5 melhores episódios de uma série específica.

### Listar séries por gênero

```java
GET /series/category/{genre}
```

Parâmetros:
- `genre`: Gênero da série.

Retorna uma lista de séries de um gênero específico.

<br>

## Instalação

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/screenmatch-api.git
    ```

2. Navegue até o diretório do projeto:
    ```bash
    cd screenmatch-api
    ```

3. Compile o projeto usando Gradle:
    ```bash
    ./gradlew build
    ```

4. Execute a aplicação:
    ```bash
    ./gradlew bootRun
    ```

A API estará disponível em `http://localhost:8080`.

<br>

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Gradle
