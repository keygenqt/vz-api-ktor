package com.keygenqt.features.ps.db.migration

import com.keygenqt.features.ps.db.models.Articles
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

const val markdown = """
Adminka
===================

![alt text](https://raw.githubusercontent.com/keygenqt/vz-site-admin-react/main/data/logo160.png)

Well, what site can exist without an admin panel?
This is the admin panel for [my personal website](https://github.com/keygenqt/vz-site-react) and open source
projects.

**Used:** [React](https://reactjs.org/) + [MUI](https://mui.com/) :heart:

### Demo

[https://adminka.keygenqt.com](https://adminka.keygenqt.com/)

### Preview

![alt text](https://raw.githubusercontent.com/keygenqt/vz-site-admin-react/main/data/preview.png)

# License

```
Copyright 2022 Vitaliy Zarubin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
""";

@Suppress(
    "unused",
    "ClassName"
)
class V1__create_articles : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {

            SchemaUtils.create(Articles)

            Articles.insert {
                it[title] = "Алгоритмы. Основы."
                it[icon] = "https://keygenqt.com/images/blog/601076d399c15.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = ""
                it[icon] = "https://keygenqt.com/images/blog/601076d399c15.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Плохо написанные хорошие задачи."
                it[icon] = "https://keygenqt.com/images/blog/5ef881c28b6c6.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Количество станет качеством."
                it[icon] = "https://keygenqt.com/images/blog/5eb1e22f20315.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Ментальная усталость."
                it[icon] = "https://keygenqt.com/images/blog/5ec6e29c20719.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Становление философии."
                it[icon] = "https://keygenqt.com/images/blog/5ee7e4486df16.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Оптимизация процессов."
                it[icon] = "https://keygenqt.com/images/blog/5ed26eec8b8cd.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Логика. Основы."
                it[icon] = "https://keygenqt.com/images/blog/5e5d66c8b82dc.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Деградируй или проиграешь."
                it[icon] = "https://keygenqt.com/images/blog/5f0519630f1dd.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Красная шапочка."
                it[icon] = "https://keygenqt.com/images/blog/5e5d656a012f7.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Безумие прогресса."
                it[icon] = "https://keygenqt.com/images/blog/5e5d5f715bb0d.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Когда ты хороший."
                it[icon] = "https://keygenqt.com/images/blog/5e5d6042aa75b.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Смотрите под ноги."
                it[icon] = "https://keygenqt.com/images/blog/5e5e06b1245c3.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Философский пароход."
                it[icon] = "https://keygenqt.com/images/blog/5e5e0d10466c7.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Углеродная форма."
                it[icon] = "https://keygenqt.com/images/blog/5e5cc904a805d.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Работа над собой."
                it[icon] = "https://keygenqt.com/images/blog/5e5d6662883b3.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }

            Articles.insert {
                it[title] = "Пчелиный рой."
                it[icon] = "https://keygenqt.com/images/blog/5ea5d50695947.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[dateUpdated] = System.currentTimeMillis()
            }
        }
    }
}
