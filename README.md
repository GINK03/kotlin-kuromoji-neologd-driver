# Kotlin-Kuromoji-Neologd-Driver
<p align="center">
  <img width="150px" float="left" src="https://cloud.githubusercontent.com/assets/4949982/24945010/89498f66-1f98-11e7-8ce3-c53e8376e451.png">
  <img width="150px" float="left" src="https://raw.githubusercontent.com/neologd/mecab-ipadic-neologd/images/neologd-logo-September2016.png">
  <img width="150px" src="https://cloud.githubusercontent.com/assets/4949982/24944856/d49bc890-1f97-11e7-9f3e-7adfa1b9baa3.png">
</p>

## Kuromojiによる形態素解析
　Kuromojiのneologd辞書を利用して、Pure JVMで最新の形態素解析を行うものである。  
  非Linux系のOSではそもそもMeCabのインストールが困難などの問題があり、JVMで動作する形態素解析エンジンが求められている。  
  そのような背景のもと、Kuromojiの形態素解析系をKotlinに移植する。

## 辞書
　Kuromojiの辞書のデータの持ち方は、jarファイルに直接、取り込んでいるスタイルなのでclasspath追加することで、neologdの利用が可能になる。  

## セットアップ
プログラムのダウンロード
```sh
$ git clone https://github.com/GINK03/kotlin-kuromoji-neologd-driver.git
```
ディレクトリの移動
```sh
$ cd kotlin-kuromoji-neologd-driver
```
Kuromojiのjarファイルのダウンロード
```sh
$ sh download_kuromoji_jars.sh
```
コンパイル
```sh
$ sh compile.morpheme.sh
```

## 実行
```sh
$ echo "今日はどうしました、旗振る子リス" | sh run.morpheme.sh stream
今日 は どう し まし た 、 旗 振る 子 リス
```
上記の例では、echoでstdinに入力しているが、cat ${ファイル名} | sh run.morpheme.st streamなどでも処理可能。

## コード
```kotlin
import java.io.BufferedReader
import java.io.InputStreamReader
import com.atilika.kuromoji.ipadic.neologd.Tokenizer
import com.atilika.kuromoji.ipadic.neologd.Token
import com.atilika.kuromoji.ipadic.Tokenizer.Builder
import java.util.stream.Collectors

fun streaming() {
  val tokenizer = Tokenizer()
  val sentences = BufferedReader(InputStreamReader(System.`in`)).lines().map { input ->
    val ret = tokenizer.tokenize(input).map { x ->
      Pair(x.getSurface(), x.getAllFeatures())
    }.map { x ->
      val (term, feats) = x
      term
    }.toList<String>()
    ret
  }.collect(Collectors.toList())
  //出力
  sentences.map { x ->
    x.joinToString(" ")
  }.map { line ->
    println(line)
  }
}

fun sample() {
  val tokenizer = Tokenizer()
  val input = """これはサンプルです。
  適切なモードを選択して使用してください。"""
  tokenizer.tokenize(input).map { x ->
    val (term, feats) = Pair(x.getSurface(), x.getAllFeatures())
    println("${term}, ${feats}")
  }
}

fun main(args: Array<String> ) {
  val MODE = args.getOrElse(0) { "sample" }
  when(MODE) {
    "sample" -> { sample()
        println("""[stream]: stdinからの入力を分かち書きします """)
      }
    "stream" -> streaming()
  }
}
```
