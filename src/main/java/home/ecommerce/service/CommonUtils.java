package home.ecommerce.service;

public class CommonUtils {
    public static String getErrorMessage(Integer errorNumb) {
        return switch(errorNumb) {
            case 403 -> "Доступ запрещён";
            case 404 -> "Страница не найдена";
            default -> null;
        };
    }

    public static String getTotalItemsString(long totalItems) {
        String msg = totalItems + " товар";

        if (totalItems >= 11 && totalItems <= 14)
            return msg + "ов";

        msg += switch ((int) totalItems % 10) {
            case 1 -> "";
            case 2, 3, 4 -> "a";
            default -> "ов";
        };

        return msg;
    }
}
