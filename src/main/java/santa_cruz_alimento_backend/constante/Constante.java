package santa_cruz_alimento_backend.constante;

public final class Constante {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String SEARCH_TEXT = "search_text";
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String ID_IN_PATH = "/{id}";

    //AUTH
    public static final String AUTH = "/auth";
    public static final String LOGIN = "/login";
    public static final String SIGNUP_USER = "/signup/user";
    public static final String SIGNUP_AUTH = "/signup";

    //API
    public static final String API = "/api";

    //USER
    public static final String USER = "/user";
    public static final String SIGNUP = "/signup";
    public static final String ALL_USER = "/user";
    public static final String BY_USER_ID = "/user/{id}";
    public static final String ALL_USER_FILTERS = "/user/list";

    //ROL
    public static final String ROL = "/rol";
    public static final String ALL_ROL = "/rol";
    public static final String BY_ROL_ID = "/rol/{id}";

    public static final String CATEGORY = "/category";
    public static final String ALL_CATEGORY = "/category";
    public static final String ALL_CATEGORY_FILTERS = "/category/list";
    public static final String BY_CATEGORY_ID = "/category/{id}";

    public static final String BUSINESS = "/business";
    public static final String ALL_BUSINESS = "/business";
    public static final String BY_BUSINESS_ID = "/business/{id}";

    public static final String INGREDIENTE = "/ingrediente";
    public static final String ALL_INGREDIENTE = "/ingrediente";
    public static final String BY_INGREDIENTE_ID = "/ingrediente/{id}";

    public static final String RECETA = "/receta";
    public static final String ALL_RECETA = "/receta";
    public static final String BY_RECETA_ID = "/receta/{id}";
    public static final String BY_NAME_RECETA = "/receta/{nombre}/ingrediente";
    public static final String RECETAS = "/recetas";

    public static final String PRODUCT = "/product";
    public static final String ALL_PRODUCT = "/product";
    public static final String BY_PRODUCT_ID = "/product/{id}";
    public static final String PRODUCTS = "/products";

    public static final String COMPRA = "/compra";
    public static final String ALL_COMPRA = "/compra";
    public static final String BY_COMPRA_ID = "/compra/{id}";
    public static final String COMPRAS_BY_INGREDIENTE_ID = "/compra/ingrediente/{ingredienteId}";

    public static final String INVENTARIO = "/inventario";
    public static final String INVENTARIO_INGREDIENTE_BY_ID = "/inventario/ingrediente/{id}";
    public static final String BY_INVENTARIO_ID = "/compra/{id}";

    public static final String PRODUCCION_BY_PRODUCTO_ID = "/produccion/{productoId}";
    public static final String PRODUCCION = "/produccion";
    public static final String BY_PRODUCCION_ID = "/produccion/{id}";
    public static final String BY_PRODUCCIONS_ID = "/produccions/{id}";

    public static final String PRODUCCION_INGREDIENTE = "/produccion-ingrediente";
    public static final String ALL_PRODUCCION_INGREDIENTE = "/produccion-ingrediente";


}