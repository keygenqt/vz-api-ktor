/*
 * Copyright 2022 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    fun insertAsymptoticComplexity(
        dirFiles: String,
        host: String
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
                    articleImage1
                ).toTypedArray()
            )

            // times
            this.createAt = "2021-01-26T19:50:40Z".toInstant().epochSeconds * 1000
            this.updateAt = "2022-09-01T12:50:40Z".toInstant().epochSeconds * 1000

            // info
            this.title = "Сложность"
            this.category = ArticleCategory.OTHER
            this.listImage = "$host/api/ps/file/${listImage?.fileName}"
            this.viewImage = "$host/api/ps/file/${viewImage?.fileName}"

            // content
            this.description = """
                Часто, для решения задач недостаточно получить только результат, так же важно получить эффективный способ ее решения. Статья о сложностях в решаемых вами задачах.
            """.trimIndent()

            this.content =
                """
Бывали ли в ситуации когда решенная задача не подходила по условиям времени или использования ресурсов?
Часто, для решения задач недостаточно получить только результат, так же важно получить эффективный способ ее решения.
Многие программисты уделяют много времени эффективности алгоритмов, сокращая машино-часы и увеличивая тем самым себе зарплату.

Эффективность выражается в количестве затраченного времени и выделения памяти под реализацию.
*Время работы* - важнейший параметр.
Отличия времени выполнения оптимального решения и решения в "лоб" может отличаться очень значительно.
*Выделения памяти* - другой важный параметр определяющий эффективность.
Велика вероятность того что если вы не укладываетесь по времени вряд ли вам удастся уложиться по памяти.
Так же при решения задачи нужно учитывать возможность *повторного использования* кода.
Алгоритм должен быть *прост в реализации*, не забывайте вам или вашему коллеге возможно еще придется работать с этим кодом.

### Асимптотическая сложность

*Асимптотическая сложность* (или просто *сложность*) алгоритма определяется функцией, которая указывает как ухудшается работа алгоритма при усложнении поставленной задачи.
Определяется функцией, которую пишут в круглых скобках с буквой O.

Например `O(N²)`, или еще можно записать так `O(N^2)`, означает что при увеличении количества входных данных в 2 раза выполнение замедлится в 4 раза, в 3 раза - замедлится в 9 раз.

<div class="PrettyImage">
  <img src="$host/api/ps/file/${articleImage1?.fileName}"/>
</div>

#### Сложность `O(1)`

Алгоритм с такой производительностью выполняется за одинаковый период времени независимо от количества элементов.
Как правило речь идет об ограниченном круге команд, поскольку за `O(1)` невозможно даже перебрать поступающие данные.

```javascript
const arr = [1, 2, 3, 4, 5];
console.log(arr[2]) // => 3
```

#### Сложность `O(log N)`

Делит количество элементов на фиксированный коэффициент при каждом шаге.
Если для вычисления элементов требуется 1 секунда, для вычисления 10 элементов потребуются 2 секунды, для вычисления 100 элементов — 3 секунды и так далее.

```javascript
const count = 100
for (let i = 1; i < count; i *= 2) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Сложность `O(N)`

Сложность возрастает линейно относительно количества элементов. То eсть пропорционально количеству элементов.
Если для вычисления элементов требуется 1 секунда, для вычисления 10 элементов потребуются 10 секунд, для вычисления 100 элементов — 100 и так далее.

```javascript
const count = 100
for (let i = 1; i < count; i++) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Сложность `O(N log N)`

Алгоритм выполняет операцию O(log N) и на каждом шаге выполняет обработку элемента.
Это любой алгоритм, который многократно делит набор данных пополам, а затем обрабатывает эти половины независимо с помощью под алгоритма, который имеет временную сложность O(N), будет иметь общую временную сложность `O(N log N)`.

```javascript
const count = 100
for (let i = 1; i <= count; i++) {
    for (let j = 1; j < count; j = j * 2) {
        console.log("Hey - I'm busy looking at: " + i + " and " + j);
    }
}
```

#### Сложность `O(N^2)`

Перебор каждого элемента при котором для каждого элемента осуществляется перебор каждого элемента.
Возможны и другие степени N например `O(N^3)` или `O(N^4)` и они значительно медленнее.

```javascript
const count = 100
for (let i = 1; i <= count; i++) {
    for (let j = 1; j <= count; j++) {
        console.log("Hey - I'm busy looking at: " + i + " and " + j);
    }
}
```

#### Сложность `O(2^N)`

Экспоненциальная функция, возрастает молниеносно применим для узкого круга задач.
Часто можно заметить на эвристический алгоритм — дающий приемлемый результат, но не наилучший.

```javascript
const count = 100
for (let i = 1; i <= Math.pow(2, count); i++) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Сложность `O(N!)`

“N факториал”. С подобной скоростью роста функции, как правило, ищут оптимальное распределение входных данных.
Она возрастает намного быстрее чем экспоненциальная функция `O(2^N)`.

```javascript
const count = 100

const factorial = (num) => {
    if (num < 0)
        return -1;
    else if (num === 0)
        return 1;
    else {
        return (num * factorial(num - 1));
    }
}

for (let i = 1; i <= factorial(count); i++) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

### Пессимистичная и средняя сложность

Не все входные параметры одинаковы, в этом есть проблема.
Если алгоритм зависит от типа выходных данных сложно составить точную функцию сложности.
В таких ситуациях чаще всего используются пессимистичная сложность (worst-case complexity), либо средняя сложность (average complexity).
В олимпиадных задачах под словом “сложность” обычно понимают пессимистичную сложность.
В таких случаях берется худший результат из возможных вариантов поступающих данных.

                """.trimIndent()
        }
    }
}
