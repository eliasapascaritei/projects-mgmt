package repository

import java.io.File

import dao.Projects
import services.S3Service

object ProjectRepository {

  def uploadProject(idUser: Int, idProject: Int, file: File, extension: String) = {
    /*val result = try {
      Projects.getUserS3Name(idProject)
    }
    catch {
      case e: Exception =>
        None
    }

    result match {
      case Some(s3Name: String) =>
        if(s3Name.length == 0) {*/
          val s3Name: String = S3Service.uploadFile(file, extension)
          Projects.uploadProject(idUser, idProject, s3Name)
        /*}
        else
          throw new Exception("You already submitted a project")
      case None =>
        throw new Exception("Not Your project")
    }*/
  }

  def downloadProject(idProject: Int) = {
    Projects.getUserS3Name(idProject) match {
      case Some(s3Name: String) =>
        //S3Service.downloadProject(s3Name)
        S3Service.downloadProject("0e1fe008d9e61cf080b3e665d5c504f5.jpg")
      case None =>
        throw new Exception("User doesn't have any uploads")
    }
  }
}
