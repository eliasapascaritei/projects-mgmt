package helpers

import com.typesafe.plugin.MailerPlugin
import models.Contact

object EmailSender {
  lazy val mail = play.Play.application.plugin(classOf[MailerPlugin]).email
  lazy val adminEmail = "support@sefaira.com"
  lazy val wrapBody = wrap("<html>", _: String, "</html>" )
  lazy val wrapDiv = wrap("<div>", _: String, "</div>")
  lazy val wrapP = wrap("<p>", _: String, "</p>")

  def sendCreateUserEmail(email: String, url: String) = {

    mail.setSubject("Create Auth User")
    mail.setRecipient(email)
    mail.setFrom(adminEmail)

    mail.sendHtml(wrapBody("Welcome to Work-App!" + "<br><br>" +
      "Please access the following link to set a password for your account: " + "<br>" + url)
    )
  }

  def sendContact(contact: Contact) = {
    mail.setSubject("Mentenance " + contact.name)
    mail.setRecipient("eliasapascaritei@gmail.com")
    mail.setCc("sofronia.ciprian@gmail.com")
    mail.setFrom(adminEmail)

    mail.sendHtml(wrapBody("User: " + contact.email + "<br>" +
      "Reported: <br> " + contact.description))
  }

  def wrap(prefix: String, html: String, suffix: String) = {
    prefix + html + suffix
  }
}
