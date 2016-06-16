package models

import org.joda.time.DateTime

case class AccountHash (
  idHash: Option[Int],
  idUser: Int,
  hash: String,
  date: DateTime
)