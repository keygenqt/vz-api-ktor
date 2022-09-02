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
            this.category = ArticleCategory.OTHER
            this.listImage = "$host/api/ps/file/${listImage?.fileName}"
            this.viewImage = "$host/api/ps/file/${viewImage?.fileName}"

            // content default
            this.title = "Complexity"
            this.description = """
                Often, to solve problems, it is not enough to get only the result, it is also important to get an effective way to solve it. 
                An article about the difficulties in the tasks you solve.
            """.trimIndent()
            this.content = """
Have you ever been in a situation where the solved problem did not fit the conditions of time or use of resources?
Often, to solve problems, it is not enough to get only the result, it is also important to get an effective way to solve it.
Many programmers devote a lot of time to the efficiency of algorithms, reducing machine hours and thereby increasing their salary.

Efficiency is expressed in the amount of time spent and memory allocated for the implementation.
*Working time* is the most important parameter.
Differences in the execution time of the optimal solution and the solution in the "forehead" can differ very significantly.
*Memory allocations* is another important performance parameter.
There is a high probability that if you do not fit in on time, it is unlikely that you will be able to meet from memory.
Also, when solving a problem, you need to take into account the possibility of * reusing * code.
The algorithm should be *easy to implement*, don't forget you or your colleague may still have to work with this code.

### Asymptotic complexity

The *asymptotic complexity* (or simply *complexity*) of an algorithm is defined by a function that indicates how the performance of the algorithm worsens as the problem becomes more complex.
It is defined by a function, which is written in parentheses with the letter O.

For example, `O(N²)`, or you can also write it like this `O(N^2)`, means that with an increase in the number of input data by 2 times, the execution will slow down by 4 times, by 3 times - it will slow down by 9 times.

<div class="PrettyImage">
  <img src="$host/api/ps/file/${articleImage1?.fileName}"/>
</div>

#### Complexity `O(1)`

An algorithm with this performance runs in the same period of time, regardless of the number of elements.
As a rule, we are talking about a limited range of commands, since for `O (1)` it is impossible even to sort through the incoming data.

```javascript
const arr = [1, 2, 3, 4, 5];
console.log(arr[2]) // => 3
```

#### Complexity `O(log N)`

Divides the number of elements by a fixed factor at each step.
If it takes 1 second to calculate elements, it will take 2 seconds to calculate 10 elements, 3 seconds to calculate 100 elements, and so on.

```javascript
const count = 100
for (let i = 1; i < count; i *= 2) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Complexity `O(N)`

Complexity increases linearly with respect to the number of elements. That is, proportional to the number of elements.
If it takes 1 second to calculate elements, it will take 10 seconds to calculate 10 elements, 100 to calculate 100 elements, and so on.

```javascript
const count = 100
for (let i = 1; i < count; i++) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Complexity `O(N log N)`

The algorithm performs an O(log N) operation and performs element processing at each step.
It is any algorithm that repeatedly divides a dataset in half and then processes those halves independently with a sub algorithm,
which has O(N) time complexity will have a total time complexity of `O(N log N)`.

```javascript
const count = 100
for (let i = 1; i <= count; i++) {
    for (let j = 1; j < count; j = j * 2) {
        console.log("Hey - I'm busy looking at: " + i + " and " + j);
    }
}
```

#### Complexity `O(N^2)`

Iterate over each element in which each element is iterated over for each element.
Other powers of N are possible, such as `O(N^3)` or `O(N^4)`, and are much slower.

```javascript
const count = 100
for (let i = 1; i <= count; i++) {
    for (let j = 1; j <= count; j++) {
        console.log("Hey - I'm busy looking at: " + i + " and " + j);
    }
}
```

#### Complexity `O(2^N)`

Exponential function, increases with lightning speed is applicable for a narrow range of tasks.
You can often notice a heuristic algorithm - giving an acceptable result, but not the best.

```javascript
const count = 100
for (let i = 1; i <= Math.pow(2, count); i++) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Complexity `O(N!)`

"N factorial". With a similar growth rate, functions tend to look for the optimal distribution of input data.
It grows much faster than the exponential function `O(2^N)`.

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

### Pessimistic and medium difficulty

Not all input parameters are the same, there is a problem with that.
If the algorithm depends on the type of output data, it is difficult to compose an exact complexity function.
In such situations, pessimistic complexity (worst-case complexity) or average complexity (average complexity) is most often used.
In olympiad problems, the word "complexity" is usually understood as pessimistic complexity.
In such cases, the worst result is taken from the possible variants of the incoming data.
            """.trimIndent()

            // content ru
            this.titleRu = "Сложность"
            this.descriptionRu = """
                Часто, для решения задач недостаточно получить только результат, так же важно получить эффективный способ ее решения. 
                Статья о сложностях в решаемых вами задачах.
            """.trimIndent()

            this.contentRu =
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
