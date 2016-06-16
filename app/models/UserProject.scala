package models

case class UserProject (
  idProject: Option[Int],
  idUser: Int,
  idCourse: Int,
  name: String,
  description: Option[String],
  isTaken: Boolean,
  isAccepted: Boolean,
  s3Name: Option[String] = None,
  userName: Option[String]
)

object UserProject {
  def applyNow(project: Project, username: Option[String], idUser: Int): UserProject = {
    UserProject(
      idProject = project.idProject,
      idUser = idUser,
      idCourse = project.idCourse,
      name = project.name,
      description = project.description,
      isTaken = project.isTaken,
      isAccepted = project.isAccepted,
      s3Name = project.s3Name,
      userName = username
    )
  }
}
