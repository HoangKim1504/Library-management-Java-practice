package validator;

public class UserValidator {
    // ================= VALID USER NEW INFO =================
    public static boolean isValidateInput(int choice, String newInfo) {
        switch (choice) {
            case 1:
                if (InputValidator.isValidUserName(newInfo)) return true;
                break;
            case 2:
                if (InputValidator.isValidName(newInfo)) return true;
                break;
            case 3:
                if (InputValidator.isValidDate(newInfo)) return true;
                break;
            case 4:
                if (InputValidator.isValidId(newInfo)) return true;
                break;
            case 5:
                if (InputValidator.isValidAddress(newInfo)) return true;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                break;
        }

        return false;
    }
}
