package pl.snz.pubweb.pub.module.suggest;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.snz.pubweb.pub.module.suggest.dto.PubSuggestion;
import pl.snz.pubweb.pub.module.suggest.service.SuggestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggest")
public class SuggestController {

    private final SuggestService suggestService;

    @GetMapping
    @Transactional
    public List<PubSuggestion> getSuggestions(@RequestParam Long userId,
                                              @RequestParam(required = false, defaultValue = "100") int size,
                                              @RequestParam(required = false, defaultValue = "0") int page) {
        return suggestService.getForUser(userId, page, size);
    }
}
