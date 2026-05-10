package validator;

public class ReaderValidator {
    // ================= VALID READER NEW INFO =================
    public static boolean isValidInput(int choice, String newInfo) {
        switch (choice) {
            case 1:
                if (InputValidator.isValidName(newInfo)) return true;
                break;
            case 2:
                if (InputValidator.isValidId(newInfo)) return true;
                break;
            case 3, 7:
                if (InputValidator.isValidDate(newInfo)) return true;
                break;
            case 5:
                if (InputValidator.isValidEmail(newInfo)) return true;
                break;
            case 6:
                if (InputValidator.isValidAddress(newInfo)) return true;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                break;
        }

        return false;
    }
}
