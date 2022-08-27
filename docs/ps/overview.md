Personal Site REST API
===================

API for my personal website written in React. The site is the site itself and its admin
panel ([Personal Site](https://version2.keygenqt.com/), [Personal Admin Panel](https://adminka.keygenqt.com/)).
Includes the main functionality of web applications: authentication, database connect, migration, roles. Actions: get
list models, get view model, update model, create model, upload files.

## Contains

#### Auth

* [Session authentication](https://ktor.io/docs/session-auth.html)
* [JSON Web Tokens](https://ktor.io/docs/jwt.html)

#### Database

* DB [MySQL](https://www.mysql.com/) + [JetBrains/Exposed](https://github.com/JetBrains/Exposed)
* Migration [Flyway](https://flywaydb.org/)

#### Other
* [Serialization](https://kotlinlang.org/docs/serialization.html)