package rick.n.morty.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rick.n.morty.dao.CartoonCharacterDao;
import rick.n.morty.dto.ApiDto;
import rick.n.morty.dto.ApiInfoDto;
import rick.n.morty.dto.CartoonCharacterDto;
import rick.n.morty.mapper.CartoonCharacterMapper;
import rick.n.morty.model.CartoonCharacter;
import rick.n.morty.service.HttpService;
import rick.n.morty.service.SyncService;

@Component
public class SyncCartoonCharactersServiceImpl implements SyncService {
    private static final Logger log = Logger.getLogger(SyncCartoonCharactersServiceImpl.class);
    private static final String URL = "https://rickandmortyapi.com/api/character";
    private final CartoonCharacterMapper mapper;
    private final HttpService httpService;
    private final CartoonCharacterDao cartoonCharacterDao;

    public SyncCartoonCharactersServiceImpl(HttpService httpService,
                                            CartoonCharacterMapper mapper,
                                            CartoonCharacterDao cartoonCharacterDao) {
        this.httpService = httpService;
        this.mapper = mapper;
        this.cartoonCharacterDao = cartoonCharacterDao;
    }

    @Override
    @Scheduled(cron = "0 */2 * * * *")
    public void syncData() {
        log.info("Trying to sync API and DB character data.");
        updateData(fetchData(URL));
        log.info("Successfully synced API and DB character data.");
    }

    private List<CartoonCharacterDto> fetchData(String url) {
        ApiDto apiDto = httpService.getData(url, ApiDto.class);
        ApiInfoDto apiInfoDto = apiDto.getInfo();
        List<CartoonCharacterDto> cartoonCharacterDtos = new ArrayList<>();
        int pages = apiInfoDto.getPages();
        int currentPage = 1;
        while (currentPage <= pages) {
            String nextPageUrl = apiInfoDto.getNext();
            cartoonCharacterDtos.addAll(apiDto.getResults());
            if (currentPage == pages) {
                break;
            }
            apiDto = httpService.getData(nextPageUrl, ApiDto.class);
            apiInfoDto = apiDto.getInfo();
            currentPage++;
        }
        log.info("Character data has been fetched from the API.");
        return cartoonCharacterDtos;
    }

    private void updateData(List<CartoonCharacterDto> dtosFromApi) {
        List<CartoonCharacter> cartoonCharactersFromDb = cartoonCharacterDao.getAll();
        List<CartoonCharacterDto> cartoonCharacterDtosFromDb = cartoonCharactersFromDb.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
        if (!cartoonCharacterDtosFromDb.equals(dtosFromApi)) {
            for (CartoonCharacterDto cartoonCharacterDto : cartoonCharacterDtosFromDb) {
                if (!dtosFromApi.contains(cartoonCharacterDto)) {
                    cartoonCharacterDao.remove(mapper.mapToCartoonCharacter(cartoonCharacterDto));
                }
            }
            for (CartoonCharacterDto cartoonCharacterDto : dtosFromApi) {
                if (!cartoonCharacterDtosFromDb.contains(cartoonCharacterDto)) {
                    cartoonCharacterDao.add(mapper.mapToCartoonCharacter(cartoonCharacterDto));
                }
            }
            log.info("DB has been updated as part of the character data sync.");
        }
    }
}
