package validator;

public class BookValidator {
    // ================= VALID BOOK NEW INFO =================
    public static boolean isValidInput(int choice, String newInfo) {
        switch (choice) {
            case 1:
                if (InputValidator.isValidBookTitle(newInfo)) return true;
                break;
            case 2:
                if (InputValidator.isValidName(newInfo)) return true;
                break;
            case 3:
                if (InputValidator.isValidPublisher(newInfo)) return true;
                break;
            case 4:
                if (InputValidator.isValidYear(Integer.parseInt(newInfo))) return true;
                break;
            case 6:
                if (InputValidator.isValidPrice(Long.parseLong(newInfo))) return true;
                break;
            case 7:
                if (InputValidator.isValidQuantity(Integer.parseInt(newInfo))) return true;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                break;
        }

        return false;
    }
}
