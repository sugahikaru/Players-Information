# 野球選手情報システム管理

***

## 概要

野球選手情報システムは、CRUDアプリでREAD・CREATE・UPDATE・DELETE機能を実装可能です。
CRUD処理とは選手情報の登録、更新、取得、削除が可能ということです

---

## 構成要件

* Java 17
* SpringBoot 3.2.7
* MySQL 8.0.37
* Docker 25.0.0
* JUnit
* Mockito

---

## 機能一覧

| 機能   | 詳細               | URL           |
|------|------------------|---------------|
| 全件取得 | 選手一覧を返す          | /players      |
| 選手検索 | idを入力して検索        | /players/{id} |
| 新規登録 | 新しい選手を登録する       | /players      |
| 選手更新 | 指定したidの選手情報を変更する | /players/{id} |
| 選手削除 | 指定したidの選手情報を削除する | /players/{id} |

## DBテーブル

テーブル名：players

| **カラム名** |  **データ型**   | **NotNull** | **キー** | **備考**  |
|:--------:|:-----------:|:-----------:|:------:|:-------:|
|    id    |     int     |  NOT NULL   |  主キー   | ID,自動生成 |
|   name   | VARCHAR(20) |  NOT NULL   |        |   選手名   |
|  number  |     int     |  NOT NULL   |        |   背番号   |
|   team   | VARCHAR(20) |  NOT NULL   |        |  チーム名   |

---

## 実行結果


<details>
<summary><h4> 1-1. GET /　選手一覧表示 </h4></summary>

![スクリーンショット 2024-07-08 220118 all1](https://github.com/sugahikaru/sanple1/assets/163830579/116d6202-1174-4639-a8ff-2594b1930bde)

</details>
<details>
<summary><h4> 1-2. GET / 選手のid検索 </h4></summary>

![スクリーンショット 2024-07-08 220038 get3](https://github.com/sugahikaru/sanple1/assets/163830579/9fc65e5c-e7fa-4767-9f15-ffc27d91cedf)

</details>

<details>
<summary><h4> 1-3. GET / 選手のチームで検索 </h4></summary>

![スクリーンショット 2024-07-08 220453 クエリ文字](https://github.com/sugahikaru/sanple1/assets/163830579/3d3e6c05-8c6b-4276-bd2f-7b7961ee6097)


</details>
<details>
<summary><h4> 1-4. GETの例外・エラー処理　</h4></summary>

![スクリーンショット 2024-07-08 221120 get 例外](https://github.com/sugahikaru/sanple1/assets/163830579/33f1a56b-df9c-4585-a156-83d4cbdcf7e5)

![スクリーンショット 2024-07-09 125319 クエリ文字例外](https://github.com/sugahikaru/sanple1/assets/163830579/803b96a9-a2a4-4603-b0ab-8687eae62af5)

</details>
<details>
<summary><h4> 2-1. POST / 選手新規登録</h4></summary>

![sq](https://github.com/sugahikaru/sanple1/assets/163830579/cf8523b2-0910-4429-8a16-30ee897ef7e9)

</details>
<details>
<summary><h4> 3-1. PATCH / 選手更新</h4></summary>

![スクリーンショット 2024-06-17 174402 sss](https://github.com/sugahikaru/sanple1/assets/163830579/93fe8d8a-7e83-495a-9772-361555ed751d)

</details>
<details>
<summary><h4> 3-2. PATCHの例外・エラー処理　</h4></summary>

![スクリーンショット 2024-06-17 215530 SSSS](https://github.com/sugahikaru/sanple1/assets/163830579/89ccce6a-7e46-4b37-bb8f-61c5001b7f16)

</details>

<details>
<summary><h4>4-1. DELETE  選手削除</h4></summary>

![スクリーンショット 2024-07-08 221530 削除](https://github.com/sugahikaru/sanple1/assets/163830579/fb9a47d2-0518-4545-9ee4-dceb6530483f)

</details>
<details>
<summary><h4>4-2. DELETEの例外・エラー処理　</h4></summary>

![スクリーンショット 2024-07-08 221209 削除例外](https://github.com/sugahikaru/sanple1/assets/163830579/59dad3c5-e8a0-4bba-aff1-6b4878dd8e19)

</details>