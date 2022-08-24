package com.keygenqt.ps.db.migration

import com.keygenqt.ps.db.models.ArticleCategory
import com.keygenqt.ps.db.models.Articles
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
                it[category] = ArticleCategory.OTHER
                it[title] = "Алгоритмы. Основы."
                it[publicImage] = "https://keygenqt.com/images/blog/601076d399c15.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Плохо написанные хорошие задачи."
                it[publicImage] = "https://keygenqt.com/images/blog/5ef881c28b6c6.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Количество станет качеством."
                it[publicImage] = "https://keygenqt.com/images/blog/5eb1e22f20315.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Ментальная усталость."
                it[publicImage] = "https://keygenqt.com/images/blog/5ec6e29c20719.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Становление философии."
                it[publicImage] = "https://keygenqt.com/images/blog/5ee7e4486df16.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Оптимизация процессов."
                it[publicImage] = "https://keygenqt.com/images/blog/5ed26eec8b8cd.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Логика. Основы."
                it[publicImage] = "https://keygenqt.com/images/blog/5e5d66c8b82dc.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Деградируй или проиграешь."
                it[publicImage] = "https://keygenqt.com/images/blog/5f0519630f1dd.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Красная шапочка."
                it[publicImage] = "https://keygenqt.com/images/blog/5e5d656a012f7.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Безумие прогресса."
                it[publicImage] = "https://keygenqt.com/images/blog/5e5d5f715bb0d.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Когда ты хороший."
                it[publicImage] = "https://keygenqt.com/images/blog/5e5d6042aa75b.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Смотрите под ноги."
                it[publicImage] = "https://keygenqt.com/images/blog/5e5e06b1245c3.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Философский пароход."
                it[publicImage] = "https://keygenqt.com/images/blog/5e5e0d10466c7.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Углеродная форма."
                it[publicImage] = "https://keygenqt.com/images/blog/5e5cc904a805d.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Работа над собой."
                it[publicImage] = "https://keygenqt.com/images/blog/5e5d6662883b3.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Articles.insert {
                it[category] = ArticleCategory.OTHER
                it[title] = "Пчелиный рой."
                it[publicImage] = "https://keygenqt.com/images/blog/5ea5d50695947.jpg"
                it[description] = "Description"
                it[content] = markdown
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }
        }
    }
}
