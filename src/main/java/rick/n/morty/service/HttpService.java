package rick.n.morty.service;

public interface HttpService {
    <T> T getData(String link, Class<? extends T> clazz);
}
