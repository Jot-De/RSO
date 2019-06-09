package pl.snz.pubweb.test.user.user

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import pl.snz.pubweb.test.user.UserServiceIntegrationTest
import pl.snz.pubweb.user.container.init.InitialConfiguration
import pl.snz.pubweb.user.module.friend.FriendshipRepository
import pl.snz.pubweb.user.module.friend_request.FriendshipRequestRepository
import pl.snz.pubweb.user.module.user.UserRepository
import pl.snz.pubweb.user.module.user.model.User
import pl.snz.pubweb.user.module.user.service.UserService

class UserServiceIT extends UserServiceIntegrationTest {

    @Autowired
    private UserService userService
    @Autowired
    private UserRepository userRepository
    @Autowired
    private FriendshipRepository friendshipRepository
    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository
    @SpringBean
    private InitialConfiguration config = Mock()



    @Transactional
    @Sql(scripts = '/sql/user-delete-spec-test.sql')
    def 'Test delete and cascadings'() {
        given:
        final User user = userRepository.findById(1l).get();

        when:
        userService.delete(user)

        then:
        !userRepository.findById(1l).isPresent()
        friendshipRepository.findAll().size() == 0
        friendshipRequestRepository.findAll().size() == 0

    }
}
