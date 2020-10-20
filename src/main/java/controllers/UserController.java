package controllers;

import config.AppConfig;
import dto.UserResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import model.User;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);
    UserService userService = context.getBean(UserService.class);

    @GetMapping(value = "/inject")
    public void inject() {
        log.info("Adding 4 users to DB");
        User shinoda = new User("mike@mail.com", "123er");
        User chester = new User("ches@mail.com", "123ch");
        User joe = new User("hann@mail.com", "123hann");
        User feniks = new User("fn@mail.com", "123fn");
        userService.add(shinoda);
        userService.add(chester);
        userService.add(joe);
        userService.add(feniks);
    }

    @GetMapping(value = "/get/{userId}")
    public UserResponseDto get(@PathVariable Long userId) {
        log.info("Getting userDto by id" + userId);
        return copyUserToUserDto(userService.get(userId));
    }

    @GetMapping(value = "/")
    public List<UserResponseDto> getAll() {
        log.info("Getting all userDtos");
        return userService.listUsers().stream()
        .map(x -> copyUserToUserDto(x))
        .collect(Collectors.toList());
    }

    private UserResponseDto copyUserToUserDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        Long userDtoId = user.getId();
        String userDtoName = user.getName();
        String userDtoEmail = user.getEmail();
        userResponseDto.setEmail(userDtoEmail);
        userResponseDto.setId(userDtoId);
        userResponseDto.setName(userDtoName);
        return userResponseDto;
    }
}
