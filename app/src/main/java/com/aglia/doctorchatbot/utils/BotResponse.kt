package com.aglia.doctorchatbot.utils

import com.aglia.doctorchatbot.ui.MainActivity

val test = IntArray(5) { 0 } //array dove verranno inserite le risposte del test
/*
0 = vuoto
1 = domanda posta
2 = si
3 = no

0) Febbre o brividi
1) Tosse secca
2) Affaticamento
3) Perdita del gusto o dell'olfatto
4) Difficoltà respiratorie o respiro affannoso
 */

val domande = arrayOf(
    "Avverte Febbre o brividi?",
    "Avverte tosse secca?",
    "Avverte affaticamento?",
    "Avverte perdita del gusto o dell'olfatto?",
    "Avverte difficoltà respiratorie o respiro affannoso?"
)

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
                for (i in test.indices) {
                    test[i] = 0
                }
                test[0]=1; //imposta la posizione della domanda come domanda posta
                "Perfetto, iniziamo, "+domande[0] //domanda
            }

            message.contains("si") -> {
                if(test.indexOf(1)!=-1){
                    val  i = test.indexOf(1)
                    test[i]=2; //imposta la posizione della domanda come si

                    if(i+1 != domande.size) {
                        test[i + 1] = 1;
                        domande[i + 1];
                    }else{
                        "Debug: Il risultato salvato in array test è: "+test.joinToString(", ")
                    }
                }else{
                    "Errore nessun test iniziato"
                }
            }

            message.contains("no") -> {
                if(test.indexOf(1)!=-1){
                    val i = test.indexOf(1)
                    test[i]=3; //imposta la posizione della domanda come no
                    if(i+1 != domande.size) {
                        test[i + 1] = 1;
                        domande[i + 1];
                    }else{
                        "Debug: Il risultato salvato in array test è: "+test.joinToString(", ")
                    }
                }else{
                    "Errore nessun test iniziato"
                }
            }

            //Funzione di debug da rimuovere ---------------------------------------------------------------------------------------------------------------------
            message.contains("debug") -> {
                "Debug: debug, arraytest, loginfalse, logintrue"
            }
            //Funzione di debug da rimuovere ---------------------------------------------------------------------------------------------------------------------
            message.contains("arraytest") -> {
                "Debug: Array test è: "+test.joinToString(", ")
            }
            //Funzione di debug da rimuovere ---------------------------------------------------------------------------------------------------------------------
            message.contains("loginfalse") -> {
                MainActivity.login=false
                "Debug: login è: "+MainActivity.login
            }
            //Funzione di debug da rimuovere ---------------------------------------------------------------------------------------------------------------------
            message.contains("logintrue") -> {
                MainActivity.login=true
                "Debug: login è: "+MainActivity.login
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
