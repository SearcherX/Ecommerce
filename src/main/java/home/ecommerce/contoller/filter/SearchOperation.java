package home.ecommerce.contoller.filter;

public enum SearchOperation {
    CONTAINS, EQUAL, NULL, NOT_NULL, GREATER_THAN_EQUAL, LESS_THAN_EQUAL, ANY, ALL;
    public static final String[] SIMPLE_OPERATION_SET = {"cn",  "eq", "nu", "nn", "ge", "le" };

    public static SearchOperation getSimpleOperation(final String input) {
        return switch (input) {
            case "CONTAINS" -> CONTAINS;
            case "EQUAL" -> EQUAL;
            case "nu" -> NULL;
            case "nn" -> NOT_NULL;
            case "GREATER_THAN_EQUAL" -> GREATER_THAN_EQUAL;
            case "LESS_THAN_EQUAL" -> LESS_THAN_EQUAL;
            default -> null;
        };
    }
}