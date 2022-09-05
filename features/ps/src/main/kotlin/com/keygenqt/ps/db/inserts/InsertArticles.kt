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
        host: String,
    ) {
        val listImage = dirFiles.createFileUploadArticle("list.jpg")
        val viewImage = dirFiles.createFileUploadArticle("view.jpg")

        val articleImage0 = dirFiles.createFileUploadArticle("06d97c0e5102ced1db05ada3cb55b4c1.jpg")
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
Have you ever been in a situation where the solved problem did not fit the conditions of execution time or resource usage?
Often, to solve a problem, it is not enough to get only the result, it is also important to get an effective way to solve it.
Many programmers devote a lot of time to finding an effective algorithm for solving a given problem,
thereby reducing the resource consumption of the devices used and increasing your salary :v:

![Sink about it]($host/api/ps/file/${articleImage0?.fileName})

### Efficiency

Efficiency is expressed in the amount of time spent and memory allocated for the implementation.
*Working time* is the most important parameter.
Differences in the execution time of the optimal solution and the solution in the "forehead" can differ very significantly.
*Memory allocations* is another important performance parameter.
By the way, it is highly likely that if you do not meet the time, you are unlikely to be able to meet the memory.
The algorithm should be *easy to implement*, be *reusable*, don't forget you or your colleague may still have to work with this code.

### Asymptotic complexity

The *asymptotic complexity* (or simply *complexity*) of an algorithm is defined by a function that indicates how the performance of the algorithm worsens as the problem becomes more complex.
It is defined by a function, which is written in parentheses with the letter O.

For example, `O(N²)`, or you can also write it like this `O(N^2)`, means that with an increase in the number of input data by 2 times, the execution will slow down by 4 times, by 3 times - it will slow down by 9 times.

<div class="PrettyImage">
  <img src="$host/api/ps/file/${articleImage1?.fileName}"/>
</div>

#### Function `O(1)`

An algorithm with this performance runs in the same period of time, regardless of the number of elements.
As a rule, we are talking about a limited range of commands, since for `O (1)` it is impossible even to sort through the incoming data.

```javascript
const arr = [1, 2, 3, 4, 5];
console.log(arr[2]) // => 3
```

#### Function `O(log N)`

The `O(log N)` performance algorithm divides the number of elements by a fixed factor at each step.
For example, if it takes 1 second to calculate elements, it will take 2 seconds to calculate 10 elements, 3 seconds to calculate 100 elements, and so on.

```javascript
const count = 100
for (let i = 1; i < count; i *= 2) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Function `O(N)`

An algorithm with this complexity grows linearly with respect to the number of elements.
That is, proportional to the number of elements.
If it takes 1 second to calculate elements, it will take 10 seconds to calculate 10 elements, 100 to calculate 100 elements, and so on.

```javascript
const count = 100
for (let i = 1; i < count; i++) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Function `O(N log N)`

An algorithm with such performance performs element processing at each step.
Any algorithm that repeatedly divides a data set in half and then processes those halves independently with a sub algorithm,
which has a time complexity of `O(N)` will have a total time complexity of `O(N log N)`.

```javascript
const count = 100
for (let i = 1; i <= count; i++) {
    for (let j = 1; j < count; j = j * 2) {
        console.log("Hey - I'm busy looking at: " + i + " and " + j);
    }
}
```

#### Function `O(N^2)`

The algorithm with `O(N^2)` performance performs an iteration of each element, where each element is iterated over each element.
Other powers of N are possible, such as `O(N^3)` or `O(N^4)`, they are much slower.

```javascript
const count = 100
for (let i = 1; i <= count; i++) {
    for (let j = 1; j <= count; j++) {
        console.log("Hey - I'm busy looking at: " + i + " and " + j);
    }
}
```

#### Function `O(2^N)`

Exponential function, increases with lightning speed is applicable for a narrow range of tasks.
You can often see on [heuristic algorithm](https://en.wikipedia.org/wiki/Heuristic_(computer_science)) - giving an acceptable result, but not the best.

```javascript
const count = 100
for (let i = 1; i <= Math.pow(2, count); i++) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Function `O(N!)`

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

### Worst-case complexity and Average-case complexity

The available data is not always the same and is performed with the same complexity.
If the algorithm depends on the type of data, how to compose the exact complexity function?
In such situations, [worst-case complexity](https://en.wikipedia.org/wiki/Worst-case_complexity) or [average-case complexity](https://en.wikipedia.org/wiki/Average-case_complexity) are most often used.
In olympiad problems, the word "complexity" is usually understood as pessimistic complexity.
In such cases, the worst result is taken from the possible variants of the input data.
            """.trimIndent()

            // content ru
            this.titleRu = "Сложность"
            this.descriptionRu = """
                Часто, для решения задач недостаточно получить только результат, так же важно получить эффективный способ ее решения. 
                Статья о сложностях в решаемых вами задачах.
            """.trimIndent()

            this.contentRu =
                """
Бывали ли в ситуации когда решенная задача не подходила по условиям времени выполнения или использования ресурсов?
Часто, для решения задачи недостаточно получить только результат, так же важно получить эффективный способ ее решения.
Многие программисты уделяют много времени поиску эффективного алгоритма решения поставленной задачи,
тем самым сокращая потребление ресурсов используемых устройств и увеличивая себе зарплату :v:

![Sink about it]($host/api/ps/file/${articleImage0?.fileName})

### Эффективность

Эффективность выражается в количестве затраченного времени и выделения памяти под реализацию.
*Время работы* - важнейший параметр.
Отличия времени выполнения оптимального решения и решения в "лоб" может отличаться очень значительно.
*Выделения памяти* - другой важный параметр определяющий эффективность.
Кстати, велика вероятность того что если вы не уложились по времени вряд ли вам удастся уложиться по памяти.
Алгоритм должен быть *прост в реализации*, иметь возможность *повторного использования*, не забывайте вам или вашему коллеге возможно еще придется работать с этим кодом.

### Асимптотическая сложность

*Асимптотическая сложность* (или просто *сложность*) алгоритма определяется функцией, которая указывает как ухудшается работа алгоритма при усложнении поставленной задачи.
Определяется функцией, которую пишут в круглых скобках с буквой O.

Например `O(N²)`, или еще можно записать так `O(N^2)`, означает что при увеличении количества входных данных в 2 раза выполнение замедлится в 4 раза, в 3 раза - замедлится в 9 раз.

<div class="PrettyImage">
  <img src="$host/api/ps/file/${articleImage1?.fileName}"/>
</div>

#### Функция `O(1)`

Алгоритм с такой производительностью выполняется за одинаковый период времени независимо от количества элементов.
Как правило речь идет об ограниченном круге команд, поскольку за `O(1)` невозможно даже перебрать поступающие данные.

```javascript
const arr = [1, 2, 3, 4, 5];
console.log(arr[2]) // => 3
```

#### Функция `O(log N)`

Алгоритм с производительностью функциии `O(log N)` делит количество элементов на фиксированный коэффициент при каждом шаге.
Например, если для вычисления элементов требуется 1 секунда, для вычисления 10 элементов потребуются 2 секунды, для вычисления 100 элементов — 3 секунды и так далее.

```javascript
const count = 100
for (let i = 1; i < count; i *= 2) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Функция `O(N)`

Алгоритм с такой сложностью возрастает линейно относительно количества элементов. 
То eсть пропорционально количеству элементов.
Если для вычисления элементов требуется 1 секунда, для вычисления 10 элементов потребуются 10 секунд, для вычисления 100 элементов — 100 и так далее.

```javascript
const count = 100
for (let i = 1; i < count; i++) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Функция `O(N log N)`

Алгоритм с такой производительностью и на каждом шаге выполняет обработку элемента.
Любой алгоритм, который многократно делит набор данных пополам, а затем обрабатывает эти половины независимо с помощью под алгоритма, который имеет временную сложность `O(N)`, будет иметь общую временную сложность `O(N log N)`.

```javascript
const count = 100
for (let i = 1; i <= count; i++) {
    for (let j = 1; j < count; j = j * 2) {
        console.log("Hey - I'm busy looking at: " + i + " and " + j);
    }
}
```

#### Функция `O(N^2)`

В алгоритме с производительностью `O(N^2)` выполняется перебор каждого элемента при котором для каждого элемента осуществляется перебор каждого элемента.
Возможны и другие степени N например `O(N^3)` или `O(N^4)`, они значительно медленнее.

```javascript
const count = 100
for (let i = 1; i <= count; i++) {
    for (let j = 1; j <= count; j++) {
        console.log("Hey - I'm busy looking at: " + i + " and " + j);
    }
}
```

#### Функция `O(2^N)`

Экспоненциальная функция, возрастает молниеносно применим для узкого круга задач.
Часто можно заметить на [эвристический алгоритм](https://ru.wikipedia.org/wiki/Эвристический_алгоритм) — дающий приемлемый результат, но не наилучший.

```javascript
const count = 100
for (let i = 1; i <= Math.pow(2, count); i++) {
    console.log("Hey - I'm busy looking at: " + i);
}
```

#### Функция `O(N!)`

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

Не всегда имеющиеся данные одинаковы и выполняются с единой сложностью.
Если алгоритм зависит от типа данных как составить точную функцию сложности?
В таких ситуациях чаще всего используются [пессимистичная сложность](https://en.wikipedia.org/wiki/Worst-case_complexity), либо [средняя сложность](https://ru.wikipedia.org/wiki/Сложность_алгоритма_в_среднем).
В олимпиадных задачах под словом “сложность” обычно понимают пессимистичную сложность.
В таких случаях берется худший результат из возможных вариантов входящих данных.

                """.trimIndent()
        }
    }
}
