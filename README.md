# AndroidApiApp

AndroidApiAppは、バックエンドAPIと連携してTodoを管理するAndroidアプリケーションです。MVVMアーキテクチャを採用し、モダンなAndroid開発ライブラリを活用しています。

## 機能

- **端末登録**: 初回起動時に一意のデバイスIDを生成し、サーバーに登録します。
- **Todo一覧表示**: サーバーから取得したTodoを一覧で表示します（SwipeRefreshLayoutによる更新対応）。
- **Todo詳細表示**: 選択したTodoの詳細情報を表示します。
- **Todo登録**: 新しいTodoを作成してサーバーに保存します。
- **Todo編集・削除**: 既存のTodoの内容更新や削除が行えます。
- **完了状態の切り替え**: Todoの完了/未完了状態を切り替えられます。

## 使用技術

- **言語**: [Kotlin](https://kotlinlang.org/)
- **DI (Dependency Injection)**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **非同期処理**: [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html), [Flow](https://kotlinlang.org/docs/flow.html)
- **ネットワーク**: [Retrofit](https://square.github.io/retrofit/), [Moshi](https://github.com/square/moshi)
- **アーキテクチャ**: MVVM (ViewModel, Repository, DataStore)
- **UI**: View Binding, [Navigation Component](https://developer.android.com/guide/navigation), SwipeRefreshLayout
- **データ保存**: [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore)

## プロジェクト構造

```
app/src/main/java/com/takaobrog/androidapiapp/
├── data/           # APIサービスおよびリポジトリの実装
├── di/             # Hiltモジュール
├── domain/         # モデル定義およびリポジトリのインターフェース
├── presentation/   # Activity, Fragment, ViewModel (UIレイヤー)
└── util/           # ログ、時刻提供などのユーティリティ
```

## ビルド方法

このプロジェクトには2つのプロダクトフレーバー（`local`, `prod`）が設定されています。

### ローカル環境 (`local`)
ローカルサーバー（`http://10.0.2.2:8765/`）に接続します。

```bash
./gradlew assembleLocalDebug
```

### 本番環境 (`prod`)
本番サーバー（`https://takaopres.blog/android_api_server_app/`）に接続します。

```bash
./gradlew assembleProdDebug
```

## ライセンス

[LICENSE](./LICENSE) ファイルを参照してください（※存在する場合）。
