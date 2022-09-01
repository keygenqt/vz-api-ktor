package com.keygenqt.ps.db.inserts

import com.keygenqt.ps.db.models.ArticleCategory
import com.keygenqt.ps.db.models.ArticleEntity
import com.keygenqt.ps.extension.createFileUploadArticle
import kotlinx.datetime.toInstant
import org.jetbrains.exposed.sql.SizedCollection

object InsertArticles {

    /**
     * Article
     */
    fun insertComputationalComplexity(
        dirFiles: String,
        host: String,
    ) {
        val listImage = dirFiles.createFileUploadArticle("list.jpg")
        val viewImage = dirFiles.createFileUploadArticle("view.jpg")

        val articleImage1 = dirFiles.createFileUploadArticle("article_1.jpg")

        ArticleEntity.new {

            // base
            this.isPublished = true
            this.uploads = SizedCollection(
                *listOfNotNull(
                    listImage,
                    viewImage,
                    articleImage1,
                ).toTypedArray()
            )

            // times
            this.createAt = "2021-01-26T19:50:40Z".toInstant().epochSeconds * 1000
            this.updateAt = "2022-09-01T12:50:40Z".toInstant().epochSeconds * 1000

            // info
            this.title = "Алгоритмическая сложность"
            this.category = ArticleCategory.OTHER
            this.listImage = "$host/api/ps/file/${listImage?.fileName}"
            this.viewImage = "$host/api/ps/file/${viewImage?.fileName}"

            // content
            this.description = """
                Алгоритм – набор команд определённой последовательности для решения поставленной задачи.
            """.trimIndent()

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
  <img src="${host}/api/ps/file/${articleImage1?.fileName}"/>
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
        }
    }
}