package models

import security.Role

case class Account (
  idUser: Option[Int] = None,
  idSpecialization: Option[Int],
  email: String,
  firstName: String,
  lastName: String,
  role: Role,
  password: String
)

object Account {
  def applyNow(create: CreateAccount, role: Role): Account ={
    Account (
      idUser  = None,
      idSpecialization = create.idSpecialization,
      email = create.email,
      firstName = create.firstName,
      lastName = create.lastName,
      role = role,
      password = "password"
    )
  }
}