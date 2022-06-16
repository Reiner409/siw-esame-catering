# siw-esame-catering
 
Progetto d'esame fornito dal professor Merialdo per quanto riguarda il corso Sistemi Informativi sul Web.
A seguire vi sono le specifiche fornite e qualche caso d'uso.

Versione finale rilasciata mediante Heroku.
Il sito web è il seguente: 
- https://siw-esame.herokuapp.com/

## Catering

- Dobbiamo progettare e implementare il sistema informativo di una società che offre servizi di catering.
- La società offre diversi buffet: ogni buffet è proposto da uno chef e contiene uno o più piatti. Ogni buffet ha un nome ed una descrizione. Uno cef può proporre uno o più buffet. Per ogni chef sono di interesse nome, cognome, nazionalità.
- Per ogni piatto proposto in un buffet sono di interesse il nome, una descrizione, l'elenco degli ingredienti. Per ogni ingrediente è di interesse il nome, l'origine, una descrizione.
- Le specifiche possono essere completate dal candidato, laddove ritenuto necessario.

## Spring Boot
- Possono accedere al sistema utenti generici e un amministratore.
- L'amministratore, previa autenticazione, può inserire, modificare, cancellare le informazioni relative ai buffet, agli chef, ai piatti.
- L'utente generico può accedere alle informazioni della società attraverso diversi percorsi di navigazione, opportunamente predisposti (ad esempio, per chef, oppure per buffet, etch.)
- Progettare il sistema, definendone casi d'uso, class diagram (con indicazioni utili alla progettazione dello strato di persistenza).
- Implementare almeno 4 casi d'uso:
- -  almeno due che abbiano come attore l'amministratore
- - almeno due che abbiano come attore l'utente generico
- Implementare il sistema con Spring Boot. Saranno oggetto di valutazione anche la qualità del codice e la qualità dell'interfaccia HTML+CSS.
- Il deploy su una piattaforma cloud (ad esempio AWS) e l'autenticazione tramite Oauth saranno considerati un plus.

## Casi d'uso
UC1: Registrazione utente - Attore primario: un Utente (non autenticato)
- Un Utente vuole effettuare la registrazione al portale.
- L'Utente inserisce il proprio Username, Password, Nome e Cognome
- Il Sistema registra le informazioni fornite dall'utente e, da quel momento in poi, gli sarà permesso effettuare l'accesso al sito Web.

UC2: Navigazione dei Buffet - Attore primario: un Utente (non autenticato)
- Un Utente vuole effettuare la navigazione dei vari Buffet proposti.
- L'Utente clicca sul pulsante "Visualizza i Buffet"
- Il Sistema risponde mostrando tutti i buffet inseriti nel portale.
- L'utente clicca sul Buffet desiderato.
- Il Sistema risponde mostrando i dati appartenenti al Buffet (Nome, Descrizione, Chef, Lista di Piatti) 

UC3: Inserimento di un nuovo Buffet - Attore primario: un Amministratore (autenticato)
- Un Amministratore vuole proporre un nuovo Buffet sul portale.
- L'Amministratore entra nella schermata di selezione operazione per Amministratori.
- Il Sistema mostra le possibili operazioni.
- L'Amministratore seleziona l'operazione "Crea nuovo Buffet"
- Il Sistema mostra la schermata di creazione di un nuovo buffet
- L'Amministratore compila tutti i campi (Nome, Descrizione, Chef, Piatti) e conferma la creazione
- Il Sistema registra le informazioni riguardanti il buffet e da quel momento in poi, il buffet sarà visibile a tutti gli utenti (autenticati e non).

UC4: Modifica di un Buffet presente - Attore primario: un Amministratore (autenticato)
- Un Amministratore vuole modificare un Buffet già inserito nel Sistema.
- L'Amministratore clicca sul pulsante "Visualizza i Buffet"
- Il Sistema risponde mostrando tutti i buffet inseriti.
- L'Amministratore selezione il Buffet che vuole modificare.
- Il Sistema risponde mostrando la schermata per modificare il Buffet precompilata con i dati attuali del Buffet.
- L'Amministratore compila i campi modificando quelli già presenti in memoria e conferma la modifica.
- Il Sistema modifica le informazioni riguardanti il Buffet salvato in memoria e risponde mostrando la schermata di Visualizzazione del Buffet.
