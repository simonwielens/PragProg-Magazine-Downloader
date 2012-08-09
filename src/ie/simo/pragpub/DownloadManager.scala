package ie.simo.pragpub
import java.io.File
import java.io.FileInputStream
import scala.io.Source
import java.net.URL
import java.net.HttpURLConnection
import java.io.InputStream
import java.io.BufferedOutputStream
import java.io.FileOutputStream

class Downloader {
  
	val urlPath = "http://pragprog.com/magazines/download/%d.mobi"
	def nextUrl = new URL(urlPath.format(nextIssue))
	var count = 0
	def nextIssue = { count += 1; count}
	
	def saveFile(url:URL, filename:String) = {
		var file = new File(filename)
		println("Opening connection...")
		val uc = url.openConnection()
		val connection = uc.asInstanceOf[HttpURLConnection]
		connection.setRequestMethod("GET")
		val buffer = new Array[Byte](1024)
		var numRead = 0
		val in:InputStream = connection.getInputStream()
		val out = new BufferedOutputStream(new FileOutputStream(file))
		Iterator.continually(in.read(buffer)).takeWhile(_ != -1).foreach(n => out.write(buffer, 0, n))
		
		connection.disconnect();		
		in.close();
		out.close();
	}

}

object DownloadManager {
  def main(args:Array[String]) {    
    val dl = new Downloader()
    for(x <- 1 to 38){ 
      dl.saveFile(dl.nextUrl, "Issue_%d.mobi".format(x)); println("Issue " + x + " completed")
    }
  }
}