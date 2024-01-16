package application.bookstore.models;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Bill extends BaseModel implements Serializable {
    private Book soldBook;
    private float totalAmount = 0;
    private User user;
    private Date date;
    private static final String FILE_PATH = "./src/main/resources/data/bills.ser";   //file qe mban te dhenat per faturat e gjeneruara me pare
    private static final File DATA_FILE = new File(FILE_PATH);
    private static final ArrayList<Bill> bills = new ArrayList<>();

    public Bill(Book soldBooks, float totalAmount, User user, Date date) {
        this.soldBook = soldBooks;
        this.totalAmount = totalAmount;
        this.user = user;
        this.date = date;
    }

    public Bill() {
    }

    @Override
    public boolean saveInFile() {
        boolean saved = super.save(DATA_FILE);
        if (saved)
            bills.add(this);
        return saved;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean deleteFromFile() {
        return false;
    }

    @Override
    public boolean updateInFile() {
        return false;
    }

    public Book getSoldBook() {
        return soldBook;
    }

    public void setSoldBook(Book soldBooks) {
        this.soldBook = soldBooks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //funksioni qe lexon te dhenat e fatuarve te krijuar me pare
    public static ArrayList<Bill> getBills() {
        if (bills.size() == 0) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
                while (true) {
                    Bill temp = (Bill) inputStream.readObject();
                    if (temp != null)
                        bills.add(temp);
                    else
                        break;
                }
                inputStream.close();
            } catch (EOFException eofException) {
                System.out.println("End of Bills file reached!");
            }
            catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return bills;
    }

}