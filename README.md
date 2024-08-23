# spring-kafka-example

Этот проект демонстрирует создание и использование двух приложений (Producer и Consumer), работающих с Apache Kafka и
использующих формат данных Avro. Проект использует Maven для сборки и управления зависимостями.

## Содержание

1. [Введение](#введение)
2. [Настройка окружения](#настройка-окружения)
3. [Схема Avro](#схема-avro)
4. [Запуск приложения](#запуск-приложения)

## Введение <a name="введение"></a>

Этот проект состоит из двух приложений, использующих Apache Kafka для передачи данных в формате Avro:

- **spring-kafka-producer:** Производит данные и отправляет их в топик Kafka.
- **spring-kafka-consumer:** Потребляет данные из топика Kafka и выводит их в консоль.

## Настройка окружения <a name="настройка-окружения"></a>

1. Убедитесь, что у вас установлены следующие компоненты:
    - Java Development Kit (JDK) 17 или выше.
    - Apache Maven.
    - Apache Kafka и ZooKeeper (или используйте облачный сервис Kafka).
   
2. Запуск Kafka и ZooKeeper с помощью Docker Compose:

   В корне проекта найдите файл `docker/docker-compose.yml`. Этот файл содержит конфигурацию для запуска Kafka и ZooKeeper в Docker контейнерах.

   Запустите контейнеры с помощью следующей команды:
   ```bash
   docker-compose -f docker/docker-compose.yml up -d

## Схема Avro <a name="схема-avro"></a>

Файл avro/user.avdl содержит описание схемы данных:

```avdl
@namespace("avro")
protocol UserProtocol {
    record User {
        string name;
        int age;
    }
}
```
## Запуск приложения <a name="запуск-приложения"></a>

### 1. Сборка проекта

Сначала соберите проект с помощью Maven:

```bash
mvn clean package
```

### 2. Запуск Kafka и ZooKeeper

Для запуска Kafka и ZooKeeper с помощью Docker Compose выполните следующие шаги:

Перейдите в корень проекта, где находится файл docker/docker-compose.yml.

Запустите контейнеры:
```bash
docker-compose -f docker/docker-compose.yml up -d
```
Это создаст и запустит контейнеры для Kafka и ZooKeeper.

### 3. Запуск приложения

Для запуска модуля spring-kafka-consumer, выполните команду:
```bash
mvn -pl spring-kafka-consumer spring-boot:run
```

Для запуска модуля spring-kafka-producer, выполните команду:
```bash
mvn -pl spring-kafka-producer spring-boot:run
```