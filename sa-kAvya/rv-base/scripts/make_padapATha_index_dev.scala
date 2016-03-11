import scala.collection.mutable
import scala.io.Source
import java.io._

val infile = "/home/vvasuki/stardict-sanskrit/sa-head/rv-base/mUlam/rv-dev.txt"
val outfile_padapATha = "/home/vvasuki/stardict-sanskrit/sa-head/rv-padapATha-dev/rv-padapATha-dev.tsv"
val outfile_padasvara = "/home/vvasuki/stardict-sanskrit/sa-head/rv-padasvara-dev/rv-padasvara-dev.tsv"
val src = Source.fromFile(infile, "utf8")
// val destination_padapATha = new PrintWriter(new File(outfile_padapATha))
val destination_padasvara = new PrintWriter(new File(outfile_padasvara))

val hkPattern = """\{#(.+?)#\}""".r
val numPattern = """(\d+?\.)""".r
val book_id_pattern = "<BB>(.+)".r
val hymn_id_pattern = "<HH>(.+)".r
val verse_id_pattern = "<VV>(.+)".r
val foot_id_pattern = "<HV>(.+)".r
val samhitA_pattern = "<SA>(.+)".r
val padapATha_pattern = "<PP>(.+)".r

var book = ""
var hymn = ""
var verse = ""
var foot = ""
var samhitA = ""
var padapATha = ""
var pAda_index = mutable.HashMap[String, List[String]]()
var word_index = mutable.HashMap[String, List[String]]()
var unmatched_lines = mutable.Set[String]()

src.getLines.foreach(line => {
  // println("line " + line)
  line match {
    case book_id_pattern(book_match) => {
      book = book_match
    }
    case hymn_id_pattern(hymn_match) => {
      assert(book != "", "Invalid book!" + List(book, hymn, verse, foot, samhitA, padapATha).mkString("|||||||"))
      hymn = hymn_match
    }
    case verse_id_pattern(verse_match) => {
      assert(hymn != "", "Invalid hymn!" + List(book, hymn, verse, foot, samhitA, padapATha).mkString("|||||||"))
      verse = verse_match
    }
    case foot_id_pattern(foot_match) => {
      assert(verse != "", "Invalid verse!" + List(book, hymn, verse, foot, samhitA, padapATha).mkString("|||||||"))
      foot = foot_match
    }
    case samhitA_pattern(samhitA_match) => {
      samhitA = samhitA_match
    }
    case padapATha_pattern(padapATha_match) => {
      // println("line " + line)
      assert(book != "", "Invalid book!" + List(book, hymn, verse, foot, samhitA, padapATha).mkString("|||||||"))
      assert(hymn != "", "Invalid hymn!" + List(book, hymn, verse, foot, samhitA, padapATha).mkString("|||||||"))
      assert(verse != "", "Invalid verse!" + List(book, hymn, verse, foot, samhitA, padapATha).mkString("|||||||"))
      assert(foot != "", "Invalid foot!" + List(book, hymn, verse, foot, samhitA, padapATha).mkString("|||||||"))
      assert(samhitA != "", "Invalid samhitA!" + List(book, hymn, verse, foot, samhitA, padapATha).mkString("|||||||"))
      padapATha = padapATha_match
      val verse_locus = List(book, hymn, verse, foot).mkString(" ")
      pAda_index += (samhitA -> List(verse_locus, samhitA, padapATha))
      val pAda_key = samhitA.replace("॒", "").replace("॑", "").replace("।", "").replace("॥", "")
      // destination_padapATha.println(f"$pAda_key%s\t" + pAda_index(samhitA).mkString("\\n"))
      // println(f"$pAda_key%s\t" + pAda_index(samhitA).mkString("\\n"))
      val words = padapATha.split(" ").filterNot(x => x == "।" || x == "॥")
      words.foreach(word => {
        word_index += (word -> word_index.getOrElse(word, List()).++(List(verse_locus)))
      } )
      foot = ""
      samhitA = ""
      padapATha = ""
    }
    case somethingElse => {
      if(!unmatched_lines.contains(somethingElse)) {
        println("Got:" + somethingElse)
        unmatched_lines += somethingElse
      }
    }
  }
})
word_index.keys.foreach(word => {
  val word_key = word.replace("॒", "").replace("॑", "")
  destination_padasvara.println(word_key + "\t" + f"$word\\n" + word_index(word).mkString(", "))
  println(word_key + "\t" + f"$word\\n" + word_index(word).mkString(", "))
})

// destination_padapATha.close()
destination_padasvara.close()
println("")
