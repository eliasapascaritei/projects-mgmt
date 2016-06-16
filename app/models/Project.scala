package models

case class Project (
  idProject: Option[Int],
  idUser: Int,
  idCourse: Int,
  name: String,
  description: Option[String],
  isTaken: Boolean,
  isAccepted: Boolean,
  s3Name: Option[String] = None
)
