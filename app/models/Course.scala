package models

case class Course (
  idCourse: Option[Int],
  idSpecialization: Int,
  idUser: Option[Int],
  name: String,
  year: Int
)
