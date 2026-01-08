# CLAUDE.md - AI Assistant Guide for CodeLibs CoreLib

Java 21ユーティリティライブラリ。Mavenビルドシステム、Apache License 2.0。

## Repository Structure

```
src/main/java/org/codelibs/core/
├── beans/       # Bean操作・イントロスペクション (converter/, factory/, impl/, util/)
├── collection/  # 拡張コレクション (LruHashMap, CaseInsensitiveMap)
├── convert/     # 型変換 (*ConversionUtil)
├── exception/   # ランタイム例外ラッパー
├── io/          # I/O・リソースユーティリティ
├── lang/        # リフレクション・言語ユーティリティ
├── misc/        # AssertionUtil, Base64Util等
├── text/        # テキスト処理 (JSON, Tokenizer)
└── ...          # その他 (crypto, log, net, xml, zip等)
src/test/java/   # テストクラス (mainと同構造)
```

## Development Commands

```bash
mvn test                              # テスト実行
mvn test -Dtest=ClassName#methodName  # 特定テスト実行
mvn clean package                     # ビルド
mvn formatter:format                  # コードフォーマット
mvn license:format                    # ライセンスヘッダー適用
mvn verify                            # カバレッジレポート生成
```

## Code Conventions

### Class Structure

- **ユーティリティクラス**: `abstract`クラス + `protected`コンストラクタ
- **定数クラス**: 同様のパターン

### Argument Validation

メソッド入口で`AssertionUtil`を使用:
- `AssertionUtil.assertArgumentNotNull("argName", value)`
- `AssertionUtil.assertArgumentNotEmpty("argName", value)`

### Exception Handling

- チェック例外を`org.codelibs.core.exception`のランタイム例外でラップ
- エラーコード使用 (例: "ECL0008" = null引数)

### Test Structure

- JUnit 4 + Hamcrestマッチャー
- テストクラス: `{ClassName}Test.java`
- テストメソッド: `test{MethodName}`

## Naming Conventions

| 種類 | 命名規則 |
|------|----------|
| ユーティリティ | `{Feature}Util.java` |
| 変換 | `{Type}ConversionUtil.java` |
| 例外 | `{Name}RuntimeException.java` / `Cl{Name}Exception.java` |
| 実装 | `{Interface}Impl.java` |

## Important Notes

1. 変更後は必ず `mvn test` を実行
2. コミット前に `mvn formatter:format` を実行
3. 新規ファイルには `mvn license:format` でライセンスヘッダー追加
4. 後方互換性維持 - 削除前に非推奨化
5. 既存パターンに従う - 類似クラスを参考にする
