import java.io.*;
import java.util.*;

// Book Class
class Book implements Comparable<Book> {
    int bookId;
    String title;
    String author;
    String category;
    boolean isIssued;

    public Book(int bookId, String title, String author, String category, boolean isIssued) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = isIssued;
    }

    public void displayBookDetails() {
        System.out.println("-----------------------------------");
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("Status: " + (isIssued ? "Issued" : "Available"));
    }

    public void markAsIssued() {
        isIssued = true;
    }

    public void markAsReturned() {
        isIssued = false;
    }

    @Override
    public int compareTo(Book b) {
        return this.title.compareToIgnoreCase(b.title); // sort by title
    }

    @Override
    public String toString() {
        return bookId + "," + title + "," + author + "," + category + "," + isIssued;
    }
}


// Member Class
class Member {
    int memberId;
    String name;
    String email;
    List<Integer> issuedBooks = new ArrayList<>();

    public Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public void displayMemberDetails() {
        System.out.println("-----------------------------------");
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Issued Books: " + issuedBooks);
    }

    public void addIssuedBook(int bookId) {
        issuedBooks.add(bookId);
    }

    public void returnIssuedBook(int bookId) {
        issuedBooks.remove(Integer.valueOf(bookId));
    }

    @Override
    public String toString() {
        return memberId + "," + name + "," + email + "," + issuedBooks.toString();
    }
}


// LibraryManager Class (Main Logic)
public class LibraryManager {

    Map<Integer, Book> books = new HashMap<>();
    Map<Integer, Member> members = new HashMap<>();

    Scanner sc = new Scanner(System.in);

    File bookFile = new File("books.txt");
    File memberFile = new File("members.txt");

    // Load Data From File at Startup
    public void loadFromFile() {
        try {
            if (!bookFile.exists()) bookFile.createNewFile();
            if (!memberFile.exists()) memberFile.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(bookFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                Book b = new Book(
                    Integer.parseInt(arr[0]), arr[1], arr[2], arr[3], Boolean.parseBoolean(arr[4])
                );
                books.put(b.bookId, b);
            }
            br.close();

            BufferedReader br2 = new BufferedReader(new FileReader(memberFile));
            while ((line = br2.readLine()) != null) {
                String[] arr = line.split(",");
                Member m = new Member(Integer.parseInt(arr[0]), arr[1], arr[2]);
                members.put(m.memberId, m);
            }
            br2.close();
        } catch (Exception e) {
            System.out.println("Error loading files.");
        }
    }


    // Save Data To Files
    public void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(bookFile));
            for (Book b : books.values()) {
                bw.write(b.toString());
                bw.newLine();
            }
            bw.close();

            BufferedWriter bw2 = new BufferedWriter(new FileWriter(memberFile));
            for (Member m : members.values()) {
                bw2.write(m.toString());
                bw2.newLine();
            }
            bw2.close();
        } catch (Exception e) {
            System.out.println("Error saving to files.");
        }
    }


    // Add Book
    public void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        Book b = new Book(id, title, author, category, false);
        books.put(id, b);

        saveToFile();
        System.out.println("Book added successfully!");
    }


    // Add Member
    public void addMember() {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        Member m = new Member(id, name, email);
        members.put(id, m);

        saveToFile();
        System.out.println("Member added successfully!");
    }


    // Issue a Book
    public void issueBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();

        if (!books.containsKey(bookId)) {
            System.out.println("Book not found!");
            return;
        }
        if (!members.containsKey(memberId)) {
            System.out.println("Member not found!");
            return;
        }

        Book b = books.get(bookId);
        Member m = members.get(memberId);

        if (b.isIssued) {
            System.out.println("Book is already issued!");
            return;
        }

        b.markAsIssued();
        m.addIssuedBook(bookId);
        saveToFile();
        System.out.println("Book issued successfully!");
    }


    // Return Book
    public void returnBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();

        if (!books.containsKey(bookId) || !members.containsKey(memberId)) {
            System.out.println("Invalid IDs!");
            return;
        }

        Book b = books.get(bookId);
        Member m = members.get(memberId);

        if (!b.isIssued) {
            System.out.println("Book is not issued!");
            return;
        }

        b.markAsReturned();
        m.returnIssuedBook(bookId);

        saveToFile();
        System.out.println("Book returned successfully!");
    }


    // Search Books
    public void searchBooks() {
        sc.nextLine();
        System.out.print("Search by (title/author/category): ");
        String key = sc.nextLine().toLowerCase();

        for (Book b : books.values()) {
            if (b.title.toLowerCase().contains(key) ||
                b.author.toLowerCase().contains(key) ||
                b.category.toLowerCase().contains(key)) {
                b.displayBookDetails();
            }
        }
    }


    // Sort Books
    public void sortBooks() {
        List<Book> list = new ArrayList<>(books.values());

        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Author");
        int choice = sc.nextInt();

        if (choice == 1) {
            Collections.sort(list); // Comparable
        } else if (choice == 2) {
            list.sort(Comparator.comparing(b -> b.author)); // Comparator
        }

        for (Book b : list) b.displayBookDetails();
    }


    // MAIN MENU
    public void mainMenu() {
        loadFromFile();

        while (true) {
            System.out.println("\n===== City Library Digital Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1: addBook(); break;
                case 2: addMember(); break;
                case 3: issueBook(); break;
                case 4: returnBook(); break;
                case 5: searchBooks(); break;
                case 6: sortBooks(); break;
                case 7:
                    saveToFile();
                    System.out.println("Exiting System. Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }


    public static void main(String[] args) {
        new LibraryManager().mainMenu();
    }
}
