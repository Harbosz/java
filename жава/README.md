# ЛР №4. Реалізація програмних інтерфейсів

**Предметна область:** електронний облік пацієнтів.

## Сутності

`Patient` — пацієнт (з ЛР №3): номер картки, ПІБ, рік народження, вага, діагноз; конструктор перевіряє дані і кидає `PatientException`.

**Варіативна поведінка — спосіб оплати прийому.** Різні пацієнти платять за один і той самий прийом по-різному, тому розрахунок вартості винесено в інтерфейс `PaymentStrategy`.

| Клас / інтерфейс | Роль |
|---|---|
| `PaymentStrategy` | інтерфейс: `calculateCost()`, `getName()`, default-метод `formatReceipt()` |
| `FullPayment` | повна оплата |
| `StateProgramPayment` | програма НСЗУ — пацієнт не платить; перевизначає `formatReceipt()` |
| `AgeDiscountPayment` | абстрактний клас: спільний `final calculateCost()`, абстрактні `isEligible()`, `discountRate()` |
| `PensionerDiscount` | 60+ років — знижка 50% |
| `ChildDiscount` | до 18 років — знижка 30% |
| `Expirable` | інтерфейс: документ із терміном дії, default `isExpired()` |
| `InsurancePayment` | страховка; реалізує **два** інтерфейси: `PaymentStrategy` і `Expirable` |
| `Appointment` | прийом — контекст паттерна Strategy, стратегію можна змінити сетером |

## Що реалізовано

- **Рівень 1.** Інтерфейс `PaymentStrategy` і 5 реалізацій. Масив `PaymentStrategy[]`, у циклі викликається `formatReceipt()` — для одного пацієнта різні суми.
- **Рівень 2.** Default-метод `formatReceipt()` (перевизначений у `StateProgramPayment` через `PaymentStrategy.super`). Абстрактний клас `AgeDiscountPayment` зі спільною логікою знижки. `InsurancePayment implements PaymentStrategy, Expirable`.
- **Рівень 3.** Паттерн Strategy: `Appointment` отримує стратегію в конструкторі, делегує їй розрахунок і дозволяє змінити її під час виконання через `setPaymentStrategy()`.

## Запуск

Потрібен JDK 17 або новіший.

```bash
javac -encoding UTF-8 *.java
java Main
```

На Windows перед запуском у консолі виконайте `chcp 65001`, щоб коректно відображалася кирилиця.
