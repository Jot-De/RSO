package pl.snz.pubweb.review.module.review.model;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.snz.pubweb.commons.data.IdentifiableEntity;

import javax.persistence.Table;

@Table(name = "review")
public class Review extends IdentifiableEntity<Long> {


}
