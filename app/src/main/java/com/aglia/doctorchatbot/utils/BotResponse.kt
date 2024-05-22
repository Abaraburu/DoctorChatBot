package com.aglia.doctorchatbot.utils

object BotResponse {

    fun basicResponses(_message: String): String {

        val message = _message.lowercase()

        return when {

            //Ciao
            message.contains("ciao") || message.contains("buongiorno") -> {
                when ((0..2).random()) {
                    0 -> "Ciao!"
                    1 -> "Salve!"
                    2 -> "Buongiorno!"
                    else -> "Error"
                }
            }

            message.contains("nuovo test") -> {
                "Perfetto, iniziamo, Avverte Febbre o Brividi?"
            }

            //Se non è tra le opzioni
            else -> {
                when ((0..2).random()) {
                    0 -> "Non ho capito."
                    1 -> "Non posso soddisfare questa richiesta."
                    2 -> "Prova a formulare la tua richiesta in modo diverso."
                    else -> "Error"
                }
            }
        }
    }
}
