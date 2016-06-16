package models

case class CreateAccount (
  idUser: Option[Int] = None,
  idSpecialization: Option[Int],
  email: String,
  firstName: String,
  lastName: String
)
