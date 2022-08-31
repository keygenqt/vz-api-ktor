package com.keygenqt.ps.db.migration

import com.keygenqt.core.utils.AppLogger
import com.keygenqt.ps.db.models.ArticleCategory
import com.keygenqt.ps.db.models.ArticleEntity
import com.keygenqt.ps.db.models.Articles
import com.keygenqt.ps.db.models.UploadEntity
import com.keygenqt.ps.utils.Constants
import kotlinx.datetime.toInstant
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.util.*

@Suppress(
    "unused",
    "ClassName"
)
class V1__create_articles : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {

            SchemaUtils.create(Articles)

            val host = if (AppLogger.isDebug) "http://localhost:8085" else "https://api.keygenqt.com"

            computationalComplexity(host)
        }
    }
}

/**
 * Project personal site
 */
private fun computationalComplexity(host: String) {

    val uploadList = getFileRelation("computationalComplexity", "list")
    val uploadView = getFileRelation("computationalComplexity", "view")

    // for post
    val article1 = getFileRelation("computationalComplexity", "article_1")

    ArticleEntity.new {
        this.category = ArticleCategory.OTHER
        this.listImage = "$host/api/ps/file/${uploadList?.fileName}"
        this.viewImage = "$host/api/ps/file/${uploadView?.fileName}"
        this.title = "Алгоритмическая сложность"
        this.isPublished = true
        this.description =
            "Алгоритм – набор команд определённой последовательности для решения поставленной задачи."
        this.content =
            """
Алгоритм – набор команд определённой последовательности для решения поставленной задачи.

[TEST](http://localhost:3002/ps/blog/1)

В алгоритмах важно оценивать:

- Качество
- Скорость
- Требования к ресурсам
- Повторное использование

```javascript
import {read} from 'to-vfile'
import {rehype} from 'rehype'
import rehypeHighlight from 'rehype-highlight'

main()

async function main() {
  const file = await rehype()
    .data('settings', {fragment: true})
    .use(rehypeHighlight)
    .process(await read('example.html'))

  console.log(String(file))
}

// Test
await fetch('/api/ps/articles/all', {
    method: 'GET'
}).then(async (response) => {
    console.log(response)
})
```

Качество — наилучшее ли это решение, возможно есть другие. Лучший выбор ли ты сделал? 
Скорость — какова его асимптотическая сложность? 
Требования к ресурсам — хватит ли у тебя оперативки? 
Повторное использование — можешь ли ты заюзать повторно для решения схожих задач?

#### Производительность алгоритмов

<div class="PrettyImage">
  <img src="${host}/api/ps/file/${article1?.fileName}"/>
</div>

#### O(1)
Выполняется за одинаковый период времени независимо от количества элементов.

#### O(log N)
Делит количество элементов на фиксированный коэффициент при каждом шаге.

#### O(N)
Сложность возрастает линейно относительно количества элементов.

#### O(N log N)
Алгоритм выполняет операцию O(log N) и на каждом шаге выполняет обработку элемента.

#### O(N^2)
Перебор каждого элемента при котором для каждого элемента осуществляется перебор каждого элемента.

```kotlin
/**
 * Get model by id
 */
suspend fun getById(
    connectKey: String,
    id: Int
): Article? = db.transaction {
    ArticleEntity
        .isHas(ArticleEntity::isLike) { (LikesArticle.key eq connectKey) }
        .findById(id)
        ?.load(ArticleEntity::uploads)
        ?.toArticle()
}
```

#### O(2^N)
Экспоненциальная функция. Лучше не юзать. Часто можно заметь на эвристический алгоритм — дающий приемлемый результат, но не наилучший.

#### O(N!)
“N факториал”. С подобной скоростью роста функции, как правило, ищут оптимальное распределение входных данных.
            """.trimIndent()

        this.uploads = SizedCollection(
            *listOfNotNull(
                uploadList,
                uploadView,
                article1
            ).toTypedArray()
        )
        this.createAt = "2021-01-26T19:50:40Z".toInstant().epochSeconds * 1000
        this.updateAt = "2022-09-01T12:50:40Z".toInstant().epochSeconds * 1000
    }
}

/**
 * Create upload from data
 */
private fun getFileRelation(
    article: String,
    nameFile: String,
): UploadEntity? {
    val file = File("${Constants.PATH_DATA_MIGRATION}/articles/${article}/${nameFile}.jpg")
    return if (file.isFile) {
        val upload = UploadEntity.new {
            fileName = "${UUID.randomUUID()}.jpg"
            fileMime = "image/png"
            originalFileName = "migration.jpg"
            createAt = System.currentTimeMillis()
        }
        file.copyTo(File("${Constants.PATH_UPLOAD}/${upload.fileName}"))
        upload
    } else null
}
