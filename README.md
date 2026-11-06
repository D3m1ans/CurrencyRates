# CurrencyRates (Курсы ЦБ РФ)

Офлайн-ориентированное Android-приложение для просмотра курсов валют Центрального Банка РФ. Приложение построено с применением Clean Architecture, Decompose и Hilt.

## Особенности

*   **Single Source of Truth**: Данные хранятся в Room. UI подписывается на базу данных.
*   **Offline-first**: При отсутствии сети отображаются сохраненные курсы с датой обновления.
*   **Clean Architecture**: Слои `data`, `domain`, `features`. `domain` не зависит от фреймворков.
*   **Decompose Navigation**: Навигация через компонентный подход без `ViewModel`/`Navigation Component`.
*   **Hilt DI**: Внедрение зависимостей через модули `DataModule` и `AppModule`.
*   **Фичи**: Поиск по валютам, конвертер валют (любая валюта → RUB).

## Архитектура
app

├── navigation (RootComponent)

└── ui (RootContent)

features

└── rates

├── component (RatesComponent)

└── ui (RatesScreen, RatesContent)

core

├── domain (UseCases, Models, Repository)

└── data (Room, Retrofit, RepositoryImpl)


## Стек технологий

*   **UI**: Jetpack Compose, Material 3
*   **Navigation**: Decompose
*   **DI**: Hilt
*   **Database**: Room
*   **Network**: Retrofit + Kotlinx Serialization
*   **Async**: Coroutines + Flow
*   **Architecture**: Clean Architecture

## Как запустить

1. Клонировать репозиторий
    ```bash
    git clone https://github.com/<your-username>/CurrencyRates.git
    ```
2. Открыть в Android Studio
3. Синхронизировать Gradle
4. Запустить на эмуляторе/устройстве API 26+
