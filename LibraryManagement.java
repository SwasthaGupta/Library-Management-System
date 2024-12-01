import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class LibraryManagement extends JFrame implements ActionListener {
    private JLabel label1, label2, label3, label4, label5, label6, label7;
    private JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    private JButton addButton, viewButton, editButton, deleteButton, issueButton, returnButton, studentDetailsButton, clearButton, exitButton;
    private JPanel panel;
    private ArrayList<String[]> books = new ArrayList<String[]>(); // Book Data
    private HashMap<String, ArrayList<String>> issuedBooks = new HashMap<>(); // Stores issued books by student
    private HashMap<String, Integer> issuedBookCount = new HashMap<>(); // Stores count of issued books by student
    private HashMap<String, Integer> bookCopies = new HashMap<>(); // Tracks available copies of each book
    private HashMap<String, HashMap<String, String>> issueReturnDates = new HashMap<>(); // Stores issue and return dates

    public LibraryManagement() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label1 = new JLabel("Book ID");
        label2 = new JLabel("Book Title");
        label3 = new JLabel("Author");
        label4 = new JLabel("Publisher");
        label5 = new JLabel("Year of Publication");
        label6 = new JLabel("ISBN");
        label7 = new JLabel("Number of Copies");

        textField1 = new JTextField(10);
        textField2 = new JTextField(20);
        textField3 = new JTextField(20);
        textField4 = new JTextField(20);
        textField5 = new JTextField(10);
        textField6 = new JTextField(20);
        textField7 = new JTextField(10);

        addButton = new JButton("Add");
        viewButton = new JButton("View");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        issueButton = new JButton("Issue Book");
        returnButton = new JButton("Return Book");
        studentDetailsButton = new JButton("Student Details");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        issueButton.addActionListener(this);
        returnButton.addActionListener(this);
        studentDetailsButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel = new JPanel(new GridLayout(13, 2));
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(label4);
        panel.add(textField4);
        panel.add(label5);
        panel.add(textField5);
        panel.add(label6);
        panel.add(textField6);
        panel.add(label7);
        panel.add(textField7);
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(issueButton);
        panel.add(returnButton);
        panel.add(studentDetailsButton);
        panel.add(clearButton);
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addBook();
        } else if (e.getSource() == viewButton) {
            viewBooks();
        } else if (e.getSource() == editButton) {
            editBook();
        } else if (e.getSource() == deleteButton) {
            deleteBook();
        } else if (e.getSource() == issueButton) {
            issueBook();
        } else if (e.getSource() == returnButton) {
            returnBook();
        } else if (e.getSource() == studentDetailsButton) {
            viewStudentDetails();
        } else if (e.getSource() == clearButton) {
            clearFields();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private void addBook() {
        String[] book = new String[7];
        book[0] = textField1.getText();
        book[1] = textField2.getText();
        book[2] = textField3.getText();
        book[3] = textField4.getText();
        book[4] = textField5.getText();
        book[5] = textField6.getText();
        book[6] = textField7.getText();

        // Add book to the list
        books.add(book);
        // Initialize the bookCopies map
        bookCopies.put(book[0], Integer.parseInt(book[6]));

        JOptionPane.showMessageDialog(this, "Book added successfully");
        clearFields();
    }

    private void viewBooks() {
        String[] columns = {"Book ID", "Book Title", "Author", "Publisher", "Year of Publication", "ISBN", "Number of Copies"};
        Object[][] data = new Object[books.size()][7];
        for (int i = 0; i < books.size(); i++) {
            data[i][0] = books.get(i)[0];
            data[i][1] = books.get(i)[1];
            data[i][2] = books.get(i)[2];
            data[i][3] = books.get(i)[3];
            data[i][4] = books.get(i)[4];
            data[i][5] = books.get(i)[5];
            data[i][6] = bookCopies.get(books.get(i)[0]); // Get copies from bookCopies map
        }
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame frame = new JFrame("View Books");
        frame.add(scrollPane);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }

    private void editBook() {
        String bookID = JOptionPane.showInputDialog(this, "Enter book ID to edit:");
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i)[0].equals(bookID)) {
                String[] options = {"Book Title", "Author", "Publisher", "Year of Publication", "ISBN", "Number of Copies"};
                String fieldToEdit = (String) JOptionPane.showInputDialog(this,
                        "Which field would you like to edit?", "Edit Book",
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                switch (fieldToEdit) {
                    case "Book Title":
                        String newTitle = JOptionPane.showInputDialog(this, "Enter new Book Title:");
                        books.get(i)[1] = newTitle;
                        break;
                    case "Author":
                        String newAuthor = JOptionPane.showInputDialog(this, "Enter new Author:");
                        books.get(i)[2] = newAuthor;
                        break;
                    case "Publisher":
                        String newPublisher = JOptionPane.showInputDialog(this, "Enter new Publisher:");
                        books.get(i)[3] = newPublisher;
                        break;
                    case "Year of Publication":
                        String newYear = JOptionPane.showInputDialog(this, "Enter new Year of Publication:");
                        books.get(i)[4] = newYear;
                        break;
                    case "ISBN":
                        String newISBN = JOptionPane.showInputDialog(this, "Enter new ISBN:");
                        books.get(i)[5] = newISBN;
                        break;
                    case "Number of Copies":
                        String newCopies = JOptionPane.showInputDialog(this, "Enter new Number of Copies:");
                        books.get(i)[6] = newCopies;
                        bookCopies.put(bookID, Integer.parseInt(newCopies)); // Update bookCopies map
                        break;
                }
                JOptionPane.showMessageDialog(this, "Book edited successfully");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Book not found");
    }

    private void deleteBook() {
        String bookID = JOptionPane.showInputDialog(this, "Enter book ID to delete:");
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i)[0].equals(bookID)) {
                books.remove(i);
                bookCopies.remove(bookID); // Remove from bookCopies map
                JOptionPane.showMessageDialog(this, "Book deleted successfully");
                clearFields();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Book not found");
    }

    private void issueBook() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String studentRollNo = JOptionPane.showInputDialog(this, "Enter Student Roll No:");
        String bookIDs = JOptionPane.showInputDialog(this, "Enter Book IDs to issue (comma separated):");
        String[] bookIDArray = bookIDs.split("\\s*,\\s*");

        ArrayList<String> issuedBooksList = issuedBooks.getOrDefault(studentRollNo, new ArrayList<>());
        int count = issuedBookCount.getOrDefault(studentRollNo, 0);

        for (String bookID : bookIDArray) {
            boolean bookFound = false;
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i)[0].equals(bookID)) {
                    int copies = bookCopies.getOrDefault(bookID, 0);
                    if (copies > 0) {
                        bookCopies.put(bookID, copies - 1);
                        issuedBooksList.add(bookID);

                        HashMap<String, String> dates = issueReturnDates.getOrDefault(studentRollNo, new HashMap<>());
                        dates.put(bookID, dateFormat.format(new Date())); // Issue date
                        issueReturnDates.put(studentRollNo, dates);

                        count++;
                        bookFound = true;
                        JOptionPane.showMessageDialog(this, "Book with ID " + bookID + " issued successfully. Remaining copies: " + (copies - 1));
                    } else {
                        JOptionPane.showMessageDialog(this, "No copies left for Book ID " + bookID);
                    }
                    break;
                }
            }
            if (!bookFound) {
                JOptionPane.showMessageDialog(this, "Book ID " + bookID + " not found.");
            }
        }

        issuedBooks.put(studentRollNo, issuedBooksList);
        issuedBookCount.put(studentRollNo, count);
    }

    private void returnBook() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String studentRollNo = JOptionPane.showInputDialog(this, "Enter Student Roll No:");
        String bookIDs = JOptionPane.showInputDialog(this, "Enter Book IDs to return (comma separated):");
        String[] bookIDArray = bookIDs.split("\\s*,\\s*");

        ArrayList<String> issuedBooksList = issuedBooks.get(studentRollNo);

        if (issuedBooksList != null) {
            int count = issuedBookCount.getOrDefault(studentRollNo, 0);

            for (String bookID : bookIDArray) {
                boolean bookFound = false;
                if (issuedBooksList.remove(bookID)) {
                    bookFound = true;
                    for (int i = 0; i < books.size(); i++) {
                        if (books.get(i)[0].equals(bookID)) {
                            int copies = bookCopies.getOrDefault(bookID, 0);
                            copies++;
                            bookCopies.put(bookID, copies);

                            HashMap<String, String> dates = issueReturnDates.get(studentRollNo);
                            if (dates != null) {
                                dates.put(bookID, dates.get(bookID) + " - Returned on " + dateFormat.format(new Date())); // Return date
                                issueReturnDates.put(studentRollNo, dates);
                            }

                            JOptionPane.showMessageDialog(this, "Book with ID " + bookID + " returned successfully. Updated copies: " + copies);
                            break;
                        }
                    }
                    count--;
                }
                if (!bookFound) {
                    JOptionPane.showMessageDialog(this, "Book ID " + bookID + " was not issued to this student.");
                }
            }

            if (issuedBooksList.isEmpty()) {
                issuedBooks.remove(studentRollNo);
                issuedBookCount.remove(studentRollNo);
            } else {
                issuedBooks.put(studentRollNo, issuedBooksList);
                issuedBookCount.put(studentRollNo, count);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No books were issued to Student Roll No: " + studentRollNo);
        }
    }

    private void viewStudentDetails() {
        if (issuedBooks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No issued books to display.");
            return;
        }

        String[] columns = {"Student Roll No", "Book ID", "Issue Date", "Return Date"};
        ArrayList<Object[]> dataList = new ArrayList<>();

        for (Map.Entry<String, HashMap<String, String>> entry : issueReturnDates.entrySet()) {
            String studentRollNo = entry.getKey();
            HashMap<String, String> bookDates = entry.getValue();
            for (Map.Entry<String, String> bookEntry : bookDates.entrySet()) {
                String bookID = bookEntry.getKey();
                String issueReturnDate = bookEntry.getValue();
                dataList.add(new Object[]{studentRollNo, bookID, issueReturnDate.split(" - ")[0], issueReturnDate.split(" - ").length > 1 ? issueReturnDate.split(" - ")[1] : "Not Returned"});
            }
        }

        Object[][] data = dataList.toArray(new Object[0][]);
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame frame = new JFrame("Student Details");
        frame.add(scrollPane);
        frame.setSize(700, 400);
        frame.setVisible(true);
    }

    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textField7.setText("");
    }

    public static void main(String[] args) {
        new LibraryManagement();
    }
}

