import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Concert {
    private String name;
    private Date date;
    private String time;
    private List<Section> sections;

    public Concert(String name, Date date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.sections = new ArrayList<>();
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        sections.add(section);
    }
}

class Section {
    private String name;
    private int numRows;
    private int numSeatsPerRow;
    private double price;
    private boolean[][] seats;

    public Section(String name, int numRows, int numSeatsPerRow, double price) {
        this.name = name;
        this.numRows = numRows;
        this.numSeatsPerRow = numSeatsPerRow;
        this.price = price;
        this.seats = new boolean[numRows][numSeatsPerRow];
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumSeatsPerRow() {
        return numSeatsPerRow;
    }

    public void setNumSeatsPerRow(int numSeatsPerRow) {
        this.numSeatsPerRow = numSeatsPerRow;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean[][] getSeats() {
        return seats;
    }

    public void setSeats(boolean[][] seats) {
        this.seats = seats;
    }

    public void bookSeat(int row, int seat) {
        seats[row][seat] = true;
    }

    public void cancelSeat(int row, int seat) {
        seats[row][seat] = false;
    }

    public boolean isSeatAvailable(int row, int seat) {
        return !seats[row][seat];
    }

    public int getAvailableSeatsCount() {
        int count = 0;
        for (int row = 0; row < numRows; row++) {
            for (int seat = 0; seat < numSeatsPerRow; seat++) {
                if (!seats[row][seat]) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getBookedSeatsCount() {
        int count = 0;
        for (int row = 0; row < numRows; row++) {
            for (int seat = 0; seat < numSeatsPerRow; seat++) {
                if (seats[row][seat]) {
                    count++;
                }
            }
        }
        return count;
    }

    public double getRevenue() {
        return getBookedSeatsCount() * price;
    }
}

public class ConcertApp {
    private List<Concert> concerts;
    private Scanner scanner;
    private Map<String, String> users;
    private String currentUser;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String CLIENT_USERNAME = "cliente";
    private static final String CLIENT_PASSWORD = "cliente";

    public ConcertApp() {
        this.concerts = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.users = new HashMap<>();
        this.currentUser = null;

    }

    public void run() {
        loadDefaultConcerts();

        while (true) {
            System.out.println("=== Menú Principal ===");
            System.out.println("1. Login");
            System.out.println("2. Registrar");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
                    break;
            }
        }
    }

    private void loadDefaultConcerts() {
        // Concierto 1
        Concert concert1 = new Concert("Concierto 1", new Date(), "18:00");

        Section plateaIzquierda = new Section("Platea Izquierda", 3, 3, 100);
        Section campo = new Section("Campo", 4, 4, 80);
        Section plateaDerecha = new Section("Platea Derecha", 3, 3, 100);
        Section palcoVIP = new Section("Palco VIP", 4, 4, 150);

        concert1.addSection(plateaIzquierda);
        concert1.addSection(campo);
        concert1.addSection(plateaDerecha);
        concert1.addSection(palcoVIP);

        concerts.add(concert1);

        // Concierto 2
        Concert concert2 = new Concert("Concierto 2", new Date(), "20:00");

        plateaIzquierda = new Section("Platea Izquierda", 3, 3, 120);
        campo = new Section("Campo", 4, 4, 100);
        plateaDerecha = new Section("Platea Derecha", 3, 3, 120);
        palcoVIP = new Section("Palco VIP", 4, 4, 180);

        concert2.addSection(plateaIzquierda);
        concert2.addSection(campo);
        concert2.addSection(plateaDerecha);
        concert2.addSection(palcoVIP);

        concerts.add(concert2);

        // Concierto 3
        Concert concert3 = new Concert("Concierto 3", new Date(), "22:00");

        plateaIzquierda = new Section("Platea Izquierda", 3, 3, 90);
        campo = new Section("Campo", 4, 4, 70);
        plateaDerecha = new Section("Platea Derecha", 3, 3, 90);
        palcoVIP = new Section("Palco VIP", 4, 4, 120);

        concert3.addSection(plateaIzquierda);
        concert3.addSection(campo);
        concert3.addSection(plateaDerecha);
        concert3.addSection(palcoVIP);

        concerts.add(concert3);
    }

    private void buyTicket() {
        System.out.print("Seleccione el número de concierto: ");
        int selectedConcertIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (selectedConcertIndex <= 0 || selectedConcertIndex > concerts.size()) {
            System.out.println("Número de concierto inválido.");
            return;
        }

        Concert concert = concerts.get(selectedConcertIndex - 1);

        System.out.println("=== Secciones del concierto ===");
        int sectionIndex = 1;
        for (Section section : concert.getSections()) {
            System.out.println(sectionIndex + ". " + section.getName());
            sectionIndex++;
        }

        System.out.print("Seleccione el número de sección: ");
        int selectedSectionIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (selectedSectionIndex <= 0 || selectedSectionIndex > concert.getSections().size()) {
            System.out.println("Número de sección inválido.");
            return;
        }

        Section selectedSection = concert.getSections().get(selectedSectionIndex - 1);

        System.out.println("=== Asientos disponibles ===");
        for (int row = 0; row < selectedSection.getNumRows(); row++) {
            for (int seat = 0; seat < selectedSection.getNumSeatsPerRow(); seat++) {
                if (selectedSection.isSeatAvailable(row, seat)) {
                    System.out.println("Fila " + (row + 1) + ", Asiento " + (seat + 1));
                }
            }
        }

        System.out.print("Ingrese el número de fila: ");
        int selectedRow = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Ingrese el número de asiento: ");
        int selectedSeat = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (selectedRow <= 0 || selectedRow > selectedSection.getNumRows() ||
                selectedSeat <= 0 || selectedSeat > selectedSection.getNumSeatsPerRow()) {
            System.out.println("Número de fila o asiento inválido.");
            return;
        }

        if (selectedSection.isSeatAvailable(selectedRow - 1, selectedSeat - 1)) {
            selectedSection.bookSeat(selectedRow - 1, selectedSeat - 1);
            System.out.println("¡Entrada comprada exitosamente!");
        } else {
            System.out.println("El asiento seleccionado no está disponible.");
        }
    }

    private void displayAvailableSeats() {
        System.out.print("Ingrese el número de concierto: ");
        int selectedConcertIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (selectedConcertIndex <= 0 || selectedConcertIndex > concerts.size()) {
            System.out.println("Número de concierto inválido.");
            return;
        }

        Concert concert = concerts.get(selectedConcertIndex - 1);

        System.out.println("=== Asientos disponibles en cada sección ===");
        for (Section section : concert.getSections()) {
            System.out.println("Sección: " + section.getName());
            System.out.println("Asientos disponibles: " + section.getAvailableSeatsCount());
        }
    }

    private void generateSalesStatistics() {
        System.out.print("Ingrese el número de concierto: ");
        int selectedConcertIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (selectedConcertIndex <= 0 || selectedConcertIndex > concerts.size()) {
            System.out.println("Número de concierto inválido.");
            return;
        }

        Concert concert = concerts.get(selectedConcertIndex - 1);

        System.out.println("=== Estadísticas de ventas ===");
        System.out.println("Concierto: " + concert.getName());
        for (Section section : concert.getSections()) {
            System.out.println("Sección: " + section.getName());
            System.out.println("Asientos vendidos: " + section.getBookedSeatsCount());
            System.out.println("Ingresos generados: " + section.getRevenue());
            System.out.println("-----------------------------");
        }
    }

    private void viewConcerts() {
        System.out.println("=== Conciertos disponibles ===");
        int concertIndex = 1;
        for (Concert concert : concerts) {
            System.out.println(concertIndex + ". " + concert.getName());
            System.out.println("   Fecha: " + concert.getDate());
            System.out.println("   Hora: " + concert.getTime());
            System.out.println("-----------------------------");
            concertIndex++;
        }
    }

    private void addConcert() {
        System.out.print("Ingrese el nombre del concierto: ");
        String name = scanner.nextLine();

        System.out.print("Ingrese la fecha del concierto (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            System.out.println("Fecha inválida. El concierto no se pudo agregar.");
            return;
        }

        System.out.print("Ingrese la hora del concierto (HH:mm): ");
        String time = scanner.nextLine();

        Concert newConcert = new Concert(name, date, time);

        System.out.print("Ingrese el número de secciones del concierto: ");
        int numSections = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numSections; i++) {
            System.out.println("=== Sección " + (i + 1) + " ===");
            System.out.print("Ingrese el nombre de la sección: ");
            String sectionName = scanner.nextLine();

            System.out.print("Ingrese el número de filas de la sección: ");
            int numRows = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Ingrese el número de asientos por fila de la sección: ");
            int numSeatsPerRow = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Ingrese el precio de la sección: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            Section section = new Section(sectionName, numRows, numSeatsPerRow, price);
            newConcert.addSection(section);
        }

        concerts.add(newConcert);
        System.out.println("El concierto ha sido agregado exitosamente.");
    }

    private void editConcert() {
        System.out.print("Ingrese el número de concierto a editar: ");
        int selectedConcertIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (selectedConcertIndex <= 0 || selectedConcertIndex > concerts.size()) {
            System.out.println("Número de concierto inválido.");
            return;
        }

        Concert selectedConcert = concerts.get(selectedConcertIndex - 1);

        System.out.print("Ingrese el nuevo nombre del concierto: ");
        String newName = scanner.nextLine();
        selectedConcert.setName(newName);

        System.out.print("Ingrese la nueva fecha del concierto (yyyy-MM-dd): ");
        String newDateString = scanner.nextLine();
        Date newDate = null;
        try {
            newDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDateString);
        } catch (ParseException e) {
            System.out.println("Fecha inválida. El concierto no se pudo editar.");
            return;
        }
        selectedConcert.setDate(newDate);

        System.out.print("Ingrese la nueva hora del concierto (HH:mm): ");
        String newTime = scanner.nextLine();
        selectedConcert.setTime(newTime);

        System.out.println("Secciones del concierto:");
        int sectionIndex = 1;
        for (Section section : selectedConcert.getSections()) {
            System.out.println(sectionIndex + ". " + section.getName());
            sectionIndex++;
        }

        System.out.print("Ingrese el número de sección a editar: ");
        int selectedSectionIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (selectedSectionIndex <= 0 || selectedSectionIndex > selectedConcert.getSections().size()) {
            System.out.println("Número de sección inválido.");
            return;
        }

        Section selectedSection = selectedConcert.getSections().get(selectedSectionIndex - 1);

        System.out.print("Ingrese el nuevo nombre de la sección: ");
        String newSectionName = scanner.nextLine();
        selectedSection.setName(newSectionName);

        System.out.print("Ingrese el nuevo número de filas de la sección: ");
        int newNumRows = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        selectedSection.setNumRows(newNumRows);

        System.out.print("Ingrese el nuevo número de asientos por fila de la sección: ");
        int newNumSeatsPerRow = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        selectedSection.setNumSeatsPerRow(newNumSeatsPerRow);

        System.out.print("Ingrese el nuevo precio de la sección: ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        selectedSection.setPrice(newPrice);

        System.out.println("La información del concierto ha sido actualizada exitosamente.");
    }

    private void deleteConcert() {
        System.out.print("Ingrese el número de concierto a eliminar: ");
        int selectedConcertIndex = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (selectedConcertIndex <= 0 || selectedConcertIndex > concerts.size()) {
            System.out.println("Número de concierto inválido.");
            return;
        }

        Concert selectedConcert = concerts.get(selectedConcertIndex - 1);
        concerts.remove(selectedConcert);

        System.out.println("El concierto ha sido eliminado exitosamente.");
    }

    private void login() {
        System.out.print("Ingrese el nombre de usuario: ");
        String username = scanner.nextLine();

        System.out.print("Ingrese la contraseña: ");
        String password = scanner.nextLine();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            // Iniciar sesión como administrador
            adminMenu();
        } else if (username.equals(CLIENT_USERNAME) && password.equals(CLIENT_PASSWORD)) {
            // Iniciar sesión como cliente
            clientMenu();
        } else if (validateCredentials(username, password)) {
            currentUser = username;
            System.out.println("Inicio de sesión exitoso. ¡Bienvenido, " + currentUser + "!");
            clientMenu();
        } else {
            System.out.println("Credenciales inválidas. Inténtelo de nuevo.");
        }
    }

    private boolean validateCredentials(String username, String password) {
        if (users.containsKey(username)) {
            String storedPassword = users.get(username);
            return storedPassword.equals(password);
        }
        return false;
    }

    public void register() {
        System.out.print("Ingrese el nombre de usuario: ");
        String username = scanner.nextLine();

        System.out.print("Ingrese la contraseña: ");
        String password = scanner.nextLine();

        // Verificar si el usuario ya existe
        if (userExists(username)) {
            System.out.println("El nombre de usuario ya está registrado. Inténtelo de nuevo.");
            return;
        }

        // Agregar el usuario a la lista de usuarios
        users.put(username, password);

        System.out.println("Registro exitoso. Ahora puede iniciar sesión.");
    }

    private boolean userExists(String username) {
        return users.containsKey(username);
    }

    private void adminMenu() {
        while (true) {
            System.out.println("=== Menú de Administrador ===");
            System.out.println("1. Agregar concierto");
            System.out.println("2. Editar concierto");
            System.out.println("3. Visualizar conciertos");
            System.out.println("4. Eliminar concierto");
            System.out.println("5. Ver estadísticas de venta");
            System.out.println("6. Ver estado de los asientos");
            System.out.println("7. Comprar entradas");
            System.out.println("8. Cerrar sesión");
            System.out.print("Elige una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    addConcert();
                    break;
                case 2:
                    editConcert();
                    break;
                case 3:
                    viewConcerts();
                    break;
                case 4:
                    deleteConcert();
                    break;
                case 5:
                    generateSalesStatistics();
                    break;
                case 6:
                    displayAvailableSeats();
                    break;
                case 7:
                    buyTicket();
                    break;
                case 8:
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
                    break;
            }
        }
    }

    private void clientMenu() {
        while (true) {
            System.out.println("=== Menú de Cliente ===");
            System.out.println("1. Comprar entradas");
            System.out.println("2. Visualizar conciertos disponibles");
            System.out.println("3. Ver asientos disponibles");
            System.out.println("4. Cerrar sesión");
            System.out.print("Elige una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    buyTicket();
                    break;
                case 2:
                    viewConcerts();
                    break;
                case 3:
                    displayAvailableSeats();
                    break;
                case 4:
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        ConcertApp concertApp = new ConcertApp();
        concertApp.run();
    }
}
