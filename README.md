# 🤿 Diving Reservation System

Java desktop application (Swing) to manage scuba diving trips and bookings. The diving company publishes its dives, and divers book a spot by providing their full name, certification level and number of people.

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![GUI](https://img.shields.io/badge/GUI-Swing-blue)
![Storage](https://img.shields.io/badge/Storage-Text%20file-lightgrey)

## Screenshots

| Diver management | Dive catalog |
|---|---|
| ![Divers](docs/screenshots/buceadores.png) | ![Dives](docs/screenshots/inmersiones.png) |

## Features

- **Diver management:** register divers with their name and certification level (e.g. Open Water, Advanced, Divemaster).
- **Dive catalog:** list of available dives with dive site, date, depth, maximum spots, remaining spots and required certification level.
- **Bookings:** divers reserve a dive indicating full name, certification and number of people.
- **Data persistence:** bookings are saved to a plain text file.
- **Custom GUI:** sidebar navigation (Home, Divers, Dives, Bookings, Save data) built with Swing.

## Tech stack

- Java 25
- Java Swing (`javax.swing`)
- File I/O for persistence (`.txt`)
- Developed in Visual Studio Code

## Project structure

```
src/
├── Excepciones/   # Custom exceptions
├── GUI/           # Swing user interface (VentanaPrincipal5)
├── Interfaces/    # Interfaces
├── Model/         # Domain classes (Buceador, Inmersion, Nivel, Reserva)
├── Services/      # Business logic (SistemaReservas)
├── Utils/         # Helpers (FicheroUtils: file read/write)
└── MainBuceo.java # Application entry point
```

The code is organized in layers, separating the interface (`GUI`), the domain model (`Model`), the business logic (`Services`) and file handling (`Utils`).

## How to run

**Requirements:** JDK 25 and Visual Studio Code with the *Extension Pack for Java*.

```bash
git clone https://github.com/JorgeVII/REPOSITORY-NAME.git
```

1. Open the cloned folder in VS Code.
2. Open `src/MainBuceo.java`.
3. Click **Run** above the `main` method.

## Author

**Jorge** — Web Application Development (DAW) graduate.

[GitHub](https://github.com/JorgeVII)
