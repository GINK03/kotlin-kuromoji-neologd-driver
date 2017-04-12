//import com.atilika.kuromoji.ipadic.Token
//import com.atilika.kuromoji.ipadic.Tokenizer
import java.io.BufferedReader
import java.io.InputStreamReader
import com.atilika.kuromoji.ipadic.neologd.Tokenizer
import com.atilika.kuromoji.ipadic.neologd.Token
import com.atilika.kuromoji.ipadic.Tokenizer.Builder
import java.util.stream.Collectors
fun main(args: Array<String> ) { 
  println("Hello, Kuromoji Streaming.")
  val tokenizer = Tokenizer()
  val sentences = BufferedReader(InputStreamReader(System.`in`)).lines().map { input ->
    val ret = tokenizer.tokenize(input).map { x ->
      Pair(x.getSurface(), x.getAllFeatures())
    }.map { x -> 
      val (term, feats) = x 
      term
    }.toList<String>()
    //sentences.add(ret)
    ret 
  }.collect(Collectors.toList())
  //出力 
  sentences.map { x -> 
    println(x)
  }
}
