# Структура сервиса
```css
src/
 └── main/
     ├── java/
     │   └── org.example.b14w4r/
     │       └── ShortLinkApplication.java
		     └── model/
			     └── Link.java
		     └── controller/
			     └── LinkController.java
		     └── service/
			     └── LinkService.java
			 └── controller/
			     └── LinkController.java
     └── resources/
         ├── application.properties
         └── static/
         └── templates/

```

# Описание функционала
## Этап создания короткой ссылки

Сервис принимает POST-запрос с датой в формате json:
```json
{

"longUrl": "ya.ru",

"clicks": 5,

"expiryHours": 10

}
```
Сервис возвращает json с сокращённой ссылкой и UUID:
```json
{
    "shortUrl": "b14420de3780",
    "userUUID": "f44ad1ea-14f5-4b40-87f4-108dbd2f6c1d"
}
```
## Этап использования короткой ссылки

Сервис принимет GET-запрос с эндпойнтом - короткой ссылкой и UUID в хэдере


Сервис возвращает оригинальную ссылку:
```json
{
    "longUrl": "ya.ru"
}
```
редирект был реализован так:
```java
    return ResponseEntity.status(302)
        .header("Location", longUrl)
        .build();

```

но это не работает, потому что происходит редирект по тому же эндпойнту и получается, что мы пытаемся получить новую ссылку, с ключом - старой ссылкой