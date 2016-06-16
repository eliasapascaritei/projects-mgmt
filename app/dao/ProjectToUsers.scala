package dao

import helpers.PostgresSupport
import models.ProjectToUser
import play.api.db.slick.Config.driver.simple._

object ProjectToUsers extends PostgresSupport {
  lazy val projectToUsers = TableQuery[ProjectToUserTable]

  class ProjectToUserTable(tag: Tag) extends Table[ProjectToUser](tag, "project_to_user"){
    def idProjectToUser = column[Int]("id_project_to_user", O.PrimaryKey, O.AutoInc)
    def idProject = column[Int]("id_project", O.NotNull)
    def idUser = column[Int]("id_user", O.NotNull)

    def * = (idProjectToUser.?, idProject, idUser) <> (ProjectToUser.tupled, ProjectToUser.unapply _)
  }

  def save(projectToUser: ProjectToUser) = {
    projectToUsers += projectToUser
  }

  def findProjects(idUser: Int): List[ProjectToUser] = {
    val query = for {
      pu <- projectToUsers if pu.idUser === idUser
    } yield pu

    query.list
  }

}
