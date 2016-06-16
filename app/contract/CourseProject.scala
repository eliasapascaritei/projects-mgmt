package contract

import models.Project

case class ProjectUser (
  idProject: Option[Int],
  projectName: Option[String],
  idUser: Option[Int],
  userName: Option[String]
)

case class CourseProject (
  idCourse: Option[Int],
  idSpecialization: Int,
  idUser: Option[Int],
  name: String,
  year: Int,
  projects: List[ProjectUser]
)
