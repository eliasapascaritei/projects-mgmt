package services

import javax.activation.MimetypesFileTypeMap

import fly.play.s3.{PUBLIC_READ, S3Exception, BucketFile, S3}
import helpers.BCryptService
import org.apache.commons.io.{IOUtils, FilenameUtils}
import play.api.{Application, Logger}
import play.api.Play.current
import java.io.{FileInputStream, File}
import fly.play.aws.auth.AwsCredentials
import play.api.libs.concurrent._
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.duration._

import scala.concurrent.{Await, Future}

object S3Service {
  private val bucketName = play.Play.application.configuration.getString("s3.bucketName")
  private val bucket = S3(bucketName, "")
  private val bucketDir = "uploads/"

  def uploadFile(file: File, extension: String): String = {
    val fileExtension = "." + extension
    val fileName = BCryptService.hashString(file.getName) + fileExtension
    val contentType = new MimetypesFileTypeMap().getContentType(file)
    Logger.debug("upload file content type: " + contentType)

    val byteArray = org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(file))

    val result = bucket add BucketFile(bucketDir + fileName, contentType, byteArray)

    result.map { unit =>
      Logger.info("Saved the file")
    }.recover {
      case S3Exception(status, code, message, originalXml) =>
        Logger.info("Error: " + message + " status: " + status + " code: " + code)
        throw new Exception(message)
    }

    fileName
  }

  def downloadProject(fileName: String) = {
    val result = bucket get fileName

    val file = Await.result(result, scala.concurrent.duration.Duration(10, SECONDS))
    val BucketFile(name, contentType, content, acl, headers) = file

    Logger.debug("name: " + name)
    Logger.debug("content Tyle: " + contentType)
    Logger.debug("content: " + content)
    Logger.debug("acl: " + acl)
    Logger.debug("headers: " + headers)
  }
}
