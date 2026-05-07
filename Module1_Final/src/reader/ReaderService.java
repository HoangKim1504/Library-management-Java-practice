package reader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReaderService {
    // ================= STORE ALL USERS =================
    private final List<Reader> readerList = new ArrayList<>(); // Prevents accidental reassignment to the list

    // ================= CREATE NEW READER =================
    public boolean createReader(Reader reader) {
        // Check null
        if (reader == null) {
            System.out.println("Độc giả không hợp lệ!");
            return false;
        }

        // Check duplicate readerId
        if (findCurrentReader(reader.getReaderId()) != null) {
            System.out.println("Độc giả đã tồn tại!");
            return false;
        }

        readerList.add(reader);
        return true;
    }

    // ================= FIND CURRENT READER =================
    public Reader findCurrentReader(String readerId) {
        for (Reader reader : readerList) {
            // Find current reader
            if (reader.getReaderId().equals(readerId)) {
                return reader;
            }
        }
        return null; // not found
    }

    // ================= GET ALL READERS =================
    public List<Reader> getAllReaders() {
        // Check null
        if (readerList.isEmpty()) {
            System.out.println("Danh sách độc giả trống!");
            return null;
        }
        return readerList;
    }

    // ================= SHOW READER LIST =================
    public void showReaderList(@NotNull List<Reader> readerList) {
        System.out.println("Danh sách độc giả trong thư viện: ");
        for (Reader reader : readerList) {
            System.out.println(reader.toString());
        }
    }

    // ================= GENERATE NEW READERID =================
    public String generateNewReaderId() {
        // Check empty list
        if (readerList.isEmpty()) {
            return "0001";
        }

        // Get last reader
        Reader lastReader = readerList.getLast();

        // Get last readerId
        String lastReaderId = lastReader.getReaderId();

        // Create new readerId
        try {
            int id = Integer.parseInt(lastReaderId);
            return String.format("%04d", id + 1); // keep format: 0001, 0002, ...
        } catch (NumberFormatException e) {
            System.out.println("ReaderId không phải số!");
            return null;
        }
    }
}
