package domain

interface MailerService {

    fun sendEmail(email: String) : Boolean
}