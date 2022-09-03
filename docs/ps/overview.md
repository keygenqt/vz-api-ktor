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
* Upload files, files management
* Ktor client for GitHub API
* Execution requests to the server
* Migrations with uploads files
* Roles Guest, Admin
* Validation request [Jakarta](https://beanvalidation.org/)
* DI - [Koin](https://insert-koin.io/)

## Preview projects

#### Website

* [To Project](https://version2.keygenqt.com/)
* [GitHub](https://github.com/keygenqt/vz-site-react)

![alt text](https://github.com/keygenqt/vz-site-react/blob/main/data/preview-1000.png?raw=true)

#### Admin Panel

* [To Project](https://adminka.keygenqt.com/)
* [GitHub](https://github.com/keygenqt/vz-site-admin-react)

![alt text](https://github.com/keygenqt/vz-site-admin-react/blob/main/data/preview-1000.png?raw=true)

<style>
  .md-content__button {
    display: none;
  }
  img {
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
  }
</style>