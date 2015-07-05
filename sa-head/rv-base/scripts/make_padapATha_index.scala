import scala.collection.mutable
import scala.io.Source
import java.io._

val infile = "/home/vvasuki/stardict-sanskrit/sa-head/rv-base/mUlam/rv-titus.txt"
val outfile = "/home/vvasuki/stardict-sanskrit/en-head/rv-padapATha-hk/rv-padapATha.tsv"
val src = Source.fromFile(infile, "iso-8859-1")
val destination = new PrintWriter(new File(outfile))

val hkPattern = """\{#(.+?)#\}""".r
val numPattern = """(\d+?\.)""".r
val verse_id_pattern = "(\\|c.+)".r
val foot_id_pattern = "(\\|p.+)".r
val samhitA_pattern = ".*(\\(.+\\)).*".r
val pAda_pattern = ".*(<.+>).*".r
val padapATha_pattern = ".*(\\[.+\\]).*".r

var foot = ""
var verse = ""
var samhitA = ""
var pAda = ""
var padapATha = ""
var pAda_index = mutable.HashMap[String, List[String]]()
var word_index = mutable.HashMap[String, List[String]]()
var unmatched_lines = mutable.Set[String]()

src.getLines.foreach(line => {
  // println("line " + line)
  line match {
  case verse_id_pattern(verse_match) => {
    verse = verse_match
  }
  case foot_id_pattern(foot_match) => {
    assert(verse != "", "Invalid verse!" + List(verse, foot, samhitA, padapATha, pAda).mkString("|||||||"))
    foot = foot_match
  }
  case samhitA_pattern(samhitA_match) => {
    samhitA = samhitA_match
  }
  case pAda_pattern(pAda_match) => {
    pAda = pAda_match
  }
  case padapATha_pattern(padapATha_match) => {
    // println("line " + line)
    assert(verse != "", "Invalid verse!" + List(verse, foot, samhitA, padapATha, pAda).mkString("|||||||"))
    assert(foot != "", "Invalid foot!" + List(verse, foot, samhitA, padapATha, pAda).mkString("|||||||"))
    assert(samhitA != "", "Invalid samhitA!" + List(verse, foot, samhitA, padapATha, pAda).mkString("|||||||"))
    assert(pAda != "", "Invalid pAda!" + List(verse, foot, samhitA, padapATha, pAda).mkString("|||||||"))
    padapATha = padapATha_match
    pAda_index += (pAda -> List(verse, foot, samhitA, padapATha, pAda))
    val pAda_key = pAda.replace("<", "").replace(">", "").replace("/", "").replace("~N", "n").replace("~n", "")
    destination.println(f"$pAda_key%s\t" + pAda_index(pAda).mkString("\\n"))

    foot = ""
    // samhitA = ""
    pAda = ""
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
destination.close()
println("")
