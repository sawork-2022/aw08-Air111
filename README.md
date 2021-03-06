# aw08

Run the project with `mvn spring-boot:run` and send request to `http://localhost:8080/check`. You should see an reponses in json format like the following.

```json
{
    "icon_url": "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    "id": "kswv7NIaTCaIIErlBzODaA",
    "url": "https://api.chucknorris.io/jokes/kswv7NIaTCaIIErlBzODaA",
    "value": "Chuck Norris's shadow weighs 250 pounds and can kick your ass ."
}
```

Try to understand the provided code which demonstrates spring integration between a spring boot application with an external http service (https://api.chucknorris.io/jokes/random).

Please implement delivery as a standalone service (just like the random joke service). Refer the sample code to integrate your Micropos system with delivery service so that user can check delivery status on Micropos which actually forwards user request to delivery service on demand.

![](Micropos.svg)

Consider the advantage by doing so and write it down in your readme file.

---

lombok的生成对mapstruct不起作用，晕

优点：

1. 不像gateway只能转发http请求，应用集成能够将更加一般性的组件连接起来
2. 当应用之间数据的传输不是简单的恒等时，filter可以简便地在不引入新组件的情况下进行数据的处理，从而使得应用集成更加方便美丽
