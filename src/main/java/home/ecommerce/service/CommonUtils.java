package home.ecommerce.service;

public class CommonUtils {
    public static String getErrorMessage(Integer errorNumb) {
        return switch(errorNumb) {
            case 403 -> "Доступ запрещён";
            case 404 -> "Страница не найдена";
            default -> null;
        };
    }
}
