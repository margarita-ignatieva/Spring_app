import config.AppConfig;
import java.util.List;
import model.User;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.UserService;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);

        User shinoda = new User("mike@mail.com", "123er");
        User chester = new User("ches@mail.com", "123ch");
        userService.add(shinoda);
        userService.add(chester);
        List<User> allUsers = userService.listUsers();
        allUsers.forEach(log::info);
    }
}
