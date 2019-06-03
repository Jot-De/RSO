package pl.snz.pubweb.pub.module.suggest;

import lombok.RequiredArgsConstructor;
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
    public List<PubSuggestion> getSuggestions(@RequestParam Long userId) {
        return suggestService.getForUser(userId);
    }
}
