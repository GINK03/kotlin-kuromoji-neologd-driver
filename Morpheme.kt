//import com.atilika.kuromoji.ipadic.Token
//import com.atilika.kuromoji.ipadic.Tokenizer
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
