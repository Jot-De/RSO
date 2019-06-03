package pl.snz.pubweb.pub.module.suggest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.pub.module.common.data.Address;
import pl.snz.pubweb.pub.module.pub.PubRepository;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.pub.model.Pub_;
import pl.snz.pubweb.pub.module.pub.presentation.PubMapper;
import pl.snz.pubweb.pub.module.suggest.dto.PubSuggestion;
import pl.snz.pubweb.pub.module.tag.TagMapper;
import pl.snz.pubweb.pub.module.tag.TagSubscriptionRepo;
import pl.snz.pubweb.pub.module.tag.model.Tag;
import pl.snz.pubweb.pub.module.tag.model.Tag_;
import pl.snz.pubweb.pub.module.visit.VisitRepository;
import pl.snz.pubweb.pub.module.visit.model.Visit;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestService {

    private final VisitRepository visitRepository;
    private final TagSubscriptionRepo subscriptionRepo;
    private final PubRepository pubRepository;
    private final PubMapper pubMapper;
    private final TagMapper tagMapper;

    @Transactional
    public List<PubSuggestion> getForUser(Long userId) {
        final List<Tag> subscriptions = subscriptionRepo.findForUser(userId);

        return subscriptions.isEmpty() ? Collections.emptyList() : getForSubscriptionsAndUser(subscriptions, userId);
    }

    private List<PubSuggestion> getForSubscriptionsAndUser(List<Tag> subscriptions, Long userId) {
        final List<Visit> userVisits = visitRepository.findByUserId(userId);
        final Map<String, Long> visitsByCity = userVisits.stream()
                .map(Visit::getPub)
                .map(Pub::getAddress)
                .map(Address::getCity)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        final List<Long> visitedPubIds = Mappers.list(userVisits, uv -> uv.getPub().getAddress().getId());
        final List<Long> subscribedTagsIds = Mappers.list(subscriptions, Tag::getId);

        final Specification<Pub> suggestionSpecification = suggestionSpec(visitedPubIds, subscribedTagsIds);
        List<Pub> pubs = pubRepository.findAll(suggestionSpecification);

        return pubs.stream()
                .sorted(compareByCity(visitsByCity))
                .map(p -> mapToSuggestion(p, subscriptions))
                .collect(Collectors.toList());
    }

    private Comparator<Pub> compareByCity(Map<String, Long> visitsByCity) {
        ToLongFunction<Pub> extractor = p -> visitsByCity.getOrDefault(p.getAddress().getCity(), 0l);

        return Comparator.comparingLong(extractor);
    }

    private PubSuggestion mapToSuggestion(Pub pub, List<Tag> subscription) {
        Set<Tag> pubTags = pub.getTags();
        List<Tag> matches = subscription.stream().filter(pubTags::contains).collect(Collectors.toList());

        return PubSuggestion.builder()
                .brief(pubMapper.toBrief(pub))
                .tagMatches(Mappers.list(matches, tagMapper::toDto))
                .build();
    }

    private Specification<Pub> suggestionSpec(List<Long> visitedPubIds, List<Long> subscribedTagIds) {
        return (r,q,cb) -> {
            Predicate pubNotVisited = cb.not(r.get(Pub_.id).in(visitedPubIds));
            final SetJoin<Pub, Tag> tagJoin = r.join(Pub_.tags);
            Predicate tagMatch = cb.and(tagJoin.get(Tag_.id).in(subscribedTagIds));
            q.distinct(true);

            return cb.and(pubNotVisited, tagMatch);
        };
    }
}
