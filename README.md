# java

# ЛР №3. Обробка винятків і власні підкласи Exception

**Предметна область:** електронний облік пацієнтів.

## Сутність `Patient`

| Поле         | Тип      | Опис                  | Правило                         |
|--------------|----------|-----------------------|---------------------------------|
| `cardNumber` | `String` | номер медичної картки | не порожній                     |
| `fullName`   | `String` | ПІБ пацієнта          | —                               |
| `birthYear`  | `int`    | рік народження        | від 1900 до поточного року      |
| `weight`     | `double` | вага, кг              | від 0,5 до 400 кг               |
| `diagnosis`  | `String` | поточний діагноз      | —                               |

Конструктор перевіряє правила і кидає власні винятки, тому некоректний пацієнт не може бути створений.

## Ієрархія власних винятків

```
Exception
└── PatientException            (checked; поле fieldName — у якому полі помилка)
    ├── InvalidBirthYearException   (поля invalidYear, minYear, maxYear)
    └── InvalidWeightException      (поле invalidWeight)
```

Обрано **checked** (`extends Exception`): некоректні дані пацієнта — очікувана ситуація при введенні, і компілятор змушує код, що створює пацієнта, її обробити.

## Що реалізовано

**Рівень 1**
- `InputMismatchException` — текст замість числа при введенні кількості пацієнтів і року народження.
- `ArithmeticException` — середній вік пацієнтів, старших за N, коли таких немає (ділення на нуль).
- `ArrayIndexOutOfBoundsException` — перегляд пацієнта за номером, якого немає у списку.
- `finally` — закриття `Scanner` і підсумкове повідомлення в `main`; повідомлення про завершення аналізу у `printAverageAgeOlderThan`.

**Рівень 2**
- Власний checked-виняток з конструктором `super(message)` і додатковим полем (значення, яке спричинило помилку).
- Кидається в конструкторі `Patient` при порушенні правил, перехоплюється окремим `catch`.

**Рівень 3**
- Кілька `catch` в одному `try` (у `main`, при введенні пацієнта): `InvalidBirthYearException` → `InvalidWeightException` → `PatientException` → `InputMismatchException` → `NumberFormatException` — від специфічних до загальних.
- Re-throw: `registerPatient()` перехоплює `PatientException`, пише в журнал і прокидає далі (`throw e;`), обробка відбувається в `main`.
- `demoExceptionHierarchy()` — один `catch (PatientException e)` перехоплює обидва підтипи й базовий виняток.

## Запуск

Потрібен JDK 17 або новіший.

```bash
javac -encoding UTF-8 *.java
java Main
```

На Windows перед запуском у консолі виконайте `chcp 65001`, щоб коректно відображалася кирилиця.
