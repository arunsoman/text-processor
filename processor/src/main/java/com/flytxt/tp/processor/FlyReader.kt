package com.flytxt.tp.processor
import com.flytxt.tp.processor.filefilter.FilterChain
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.nio.aRead
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.file.*




class FlyReader(val dumpLocation: String, val folder: String, val lp: LineProcessor, val fileFilter: FilterChain, val deleteProcessed: Boolean) {

	private val appLog  = LoggerFactory.getLogger("applicationLog")

	private val transLog = LoggerFactory.getLogger("transactionLog")

    private val LOG = LoggerFactory.getLogger(this.javaClass.name)

	private val THREAD_SLEEP_TIME = 60_000L

    internal var eol = System.lineSeparator().toByteArray() // System.lineSeparator().getBytes();

    internal val DEFAULT_BLOCK_SIZE: Int = 100000

    fun runJob():Job {
       return launch(CommonPool){
          while (isActive)
            run()
        }
    }

   suspend fun run() {
		LOG.info("dump location $dumpLocation, folder , $folder")
        var fileName: String? = null
        val folderP = Paths.get(folder)

        val stream: DirectoryStream<Path> = Files.newDirectoryStream(folderP, fileFilter)
        stream.use {
           for (path in it){

                        fileName = path.getFileName().toString()
                        lp.mf.currentObject.init(folder, fileName)
                        lp.init(fileName, path.toFile().lastModified())
                        processFile(path)
                           if (deleteProcessed) {
                               appLog.debug("Deleting file $path")
                               path.deleteFile()
                           } else {
                               path.moveTo("$dumpLocation$fileName")
                           }
                    }

           } ?: delay(THREAD_SLEEP_TIME)
        }


    @Throws(Exception::class)
    suspend private fun processFile(path: Path) {
        val inputFile = path.toString()
        val t1 = System.currentTimeMillis()
        try {
            path.openChannelForRead().use {

                val buf = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE)
                var offset =0L
                while (true) {
                    val read = it.aRead(buf, offset)
                    buf.flip()
                    if (read <= 0) {
                        break
                    } else {
                        buf.flip()
                        readLines(buf.array(), read)
                    }
                    offset += read
                }
            }
        } catch (e: Exception) {
            appLog.debug("Could not process $inputFile: cause$e.message")
            throw e
        }

        if (transLog.isInfoEnabled) {
            val fileSize = Files.size(path)
            transLog.info("$inputFile,$fileSize size,${System.currentTimeMillis() - t1}ms")
        }
    }

    private fun  readLines(array: ByteArray, readCnt: Int) {
        var eolPosition: Long
        var previousEolPosition: Long = 0
       while(read>0){
           eolPosition = getEOLPosition(array, previousEolPosition.toInt() + eol.size, readCnt)

       }
    }



    fun getEOLPosition(data: ByteArray, startIndex: Int, endIndex: Int): Long {
        try {
            var tokenIndex = 0
            var currentIndex = startIndex
            while (currentIndex <= endIndex) {
                while (tokenIndex < eol.size && eol[tokenIndex] == data[currentIndex + tokenIndex]) {
                    tokenIndex++
                }
                if (tokenIndex == eol.size) {
                    return currentIndex.toLong()
                }
                currentIndex++
            }
        } catch (e: Exception) {
        }

        return -1
    }


}

fun Path.openChannelForRead() =AsynchronousFileChannel.open(this, StandardOpenOption.READ)


fun Path.deleteFile() :Unit {
    this.toFile().delete()
}

fun Path.moveTo(destination: String) = Files.move(this, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING)
